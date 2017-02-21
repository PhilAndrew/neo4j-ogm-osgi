package org.neo4j.ogm.classloader;//: ----------------------------------------------------------------------------------
//: Copyright © 2017 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: Released under the MIT License, refer to the project website for licence information.
//: ----------------------------------------------------------------------------------

import org.neo4j.ogm.Neo4JOSGI;

public class MetaDataClassLoaderOsgi {
    public static Class loadClass(ClassLoader classLoader, final String name) throws ClassNotFoundException {
        return Neo4JOSGI.loadClass(classLoader, name);
    }
}
