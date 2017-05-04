package org.neo4j.ogm;

import org.neo4j.ogm.metadata.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Neo4JOSGI {
    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4JOSGI.class);

    public static String modelPackagePath = null;

    public static ClassInfo classInfo(MetaData metaData, String s) {
        return metaData.classInfoNeo4JOSGI(s);
    }

    public static ClassInfo classInfo(MetaData metaData, Object o) {
        return metaData.classInfoNeo4JOSGI(o);
    }

    public static ClassInfo getClassInfo(String fullOrPartialClassName, Boolean queryForARealClass, Map<String, ClassInfo> infos) {

        if (fullOrPartialClassName.equalsIgnoreCase("PERSON_IN_CHARGE"))
            fullOrPartialClassName = "jumpmicro.shared.model.MMLPerson";
        if (fullOrPartialClassName.equalsIgnoreCase("REGISTERED_COUNTRY"))
            fullOrPartialClassName = "jumpmicro.shared.model.MMLCountry";
        if (fullOrPartialClassName.equalsIgnoreCase("OPERATING_COUNTRY"))
            fullOrPartialClassName = "jumpmicro.shared.model.MMLCountry";
        if (fullOrPartialClassName.equalsIgnoreCase("BUSINESS_ADDRESS"))
            fullOrPartialClassName = "jumpmicro.shared.model.MMLAddress";
        if (fullOrPartialClassName.equalsIgnoreCase("ADDRESS_COUNTRY"))
            fullOrPartialClassName = "jumpmicro.shared.model.MMLCountry";

        if (fullOrPartialClassName.equalsIgnoreCase("BRANDS")) {
            Iterator<String> keySet = infos.keySet().iterator();
            while (keySet.hasNext()) {
                String key = keySet.next();
                LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@#$#$#$");
                LOGGER.info("key is: " + key);
                LOGGER.info("vlaue is: " + infos.get(key));
            }
            fullOrPartialClassName = "jumpmicro.shared.model.MMLBrand";
        }

        if ((modelPackagePath!=null) && (!queryForARealClass))
            fullOrPartialClassName = modelPackagePath + "." + fullOrPartialClassName;

        if (Neo4JOGM.getContextList().size() == 0) {

            ClassInfo info = null;
            try {
                info = new ClassInfo(Class.forName(fullOrPartialClassName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return info;
            /*
            ClassInfo match = null;
            for (String fqn : infos.keySet()) {
                if (fqn.endsWith("." + fullOrPartialClassName) || fqn.equals(fullOrPartialClassName)) {
                    if (match == null) {
                        match = infos.get(fqn);
                    } else {
                        throw new MappingException("More than one class has simple name: " + fullOrPartialClassName);
                    }
                }
            }
            return match;*/
        } else {

            // @todo PHILIP THIS IS A BAD HACK!!!!!!!!!!!!!!!!!


            ClassInfo info = null;
            // We just want to return simple class information

            System.out.println("Searching for class : " + fullOrPartialClassName);
            List<BundleContext> listOf = Neo4JOGM.getContextList();
            Class<?> classFound = null;
            breakOut:
            for (BundleContext bundleContext : listOf) {
                //
                try {
                    Class<?> ccc = bundleContext.getBundle().loadClass(fullOrPartialClassName);
                    if (ccc != null) {
                        System.out.println("Class found in normal bundle context for : " + bundleContext.getBundle().getLocation());
                        classFound = ccc;
                        break breakOut;
                    }

                    Bundle[] bundles = bundleContext.getBundles();
                    for (Bundle bundle : bundles) {
                        Class<?> c = bundle.loadClass(fullOrPartialClassName);
                        if (c != null) {
                            System.out.println("Bundle where class is found is : " + bundle.getLocation());
                            classFound = c;
                            break breakOut;
                        }
                    }

                } catch (ClassNotFoundException e) {
                    //e.printStackTrace();
                    //System.out.println("Class not found but that should be ok");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Class found for :" + fullOrPartialClassName + " as " + classFound);
            info = new ClassInfo(classFound);

            return info;
        }
    }

    public static String getDescriptorForClass(final Class c)
    {
        if(c.isPrimitive())
        {
            if(c==byte.class)
                return "B";
            if(c==char.class)
                return "C";
            if(c==double.class)
                return "D";
            if(c==float.class)
                return "F";
            if(c==int.class)
                return "I";
            if(c==long.class)
                return "J";
            if(c==short.class)
                return "S";
            if(c==boolean.class)
                return "Z";
            if(c==void.class)
                return "V";
            throw new RuntimeException("Unrecognized primitive "+c);
        }
        if(c.isArray()) return c.getName().replace('.', '/');
        return ('L'+c.getName()+';').replace('.', '/');
    }

    public static void copyFieldsAndAnnotatonsTo(FieldsInfo fieldsInfo, Set<FieldInfo> fieldInfos, AnnotationsInfo annotationsInfo, FieldsInfo f, Set<FieldInfo> f2, AnnotationsInfo a) {
        try {
            f.getFieldsHashMap().putAll(fieldsInfo.getFieldsHashMap());
            if (fieldInfos!=null) {
                for (FieldInfo fInfo : fieldInfos) {
                    f2.add(fInfo);
                }
            }
            if ((annotationsInfo!=null) && (annotationsInfo.list()!=null)) {
                for (AnnotationInfo annotationInfo : annotationsInfo.list()) {
                    a.addPublic(annotationInfo);
                }
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Class loadClass(ClassLoader classLoader, final String name) throws ClassNotFoundException {
        if (Neo4JOGM.getContextList().size() == 0) {
            return Class.forName(name, false, classLoader);
        } else {
            // Using the OSGi classloader and searching through the loaded bundles
            Class foundClass = null;
            java.util.List<BundleContext> contextList = Neo4JOGM.getContextList();

            start:
            for (BundleContext bundleContext : contextList) {
                Bundle[] bundles = bundleContext.getBundles();
                for (Bundle bundle : bundles) {
                    try {
                        Class c = bundle.loadClass(name);
                        if (c != null) {
                            foundClass = c;
                            break start;
                        }
                    } catch (java.lang.ClassNotFoundException ex) {
                    }
                }
            }

            return foundClass;
        }
    }


}
