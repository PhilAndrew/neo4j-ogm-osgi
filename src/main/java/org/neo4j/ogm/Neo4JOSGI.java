package org.neo4j.ogm;

import org.neo4j.ogm.exception.MappingException;
import org.neo4j.ogm.metadata.*;
import org.osgi.framework.BundleContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Neo4JOSGI {

    public static ClassInfo getClassInfo(String fullOrPartialClassName, Map<String, ClassInfo> infos) {
        if (Neo4JOGM.getContextList().size() == 0) {
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
            return match;
        } else {
            ClassInfo info = null;
            // We just want to return simple class information
            try {
                List<BundleContext> listOf = Neo4JOGM.getContextList();
                Class<?> classFound = null;
                breakOut:
                for (BundleContext bundleContext : listOf) {
                    //
                    Class<?> ccc = bundleContext.getBundle().loadClass(fullOrPartialClassName);
                    if (ccc != null) {
                        classFound = ccc;
                        break breakOut;
                    }
                }
                info = new ClassInfo(classFound);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                    a.add(annotationInfo);
                }
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
