package org.neo4j.ogm;

import org.neo4j.ogm.exception.MappingException;
import org.neo4j.ogm.metadata.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Neo4JOSGI {

    public static String modelPackagePath = null;

    public static ClassInfo getClassInfo(String fullOrPartialClassName, Boolean queryForARealClass, Map<String, ClassInfo> infos) {
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
            //System.out.println("Class found for :" + fullOrPartialClassName + " as " + classFound);
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
