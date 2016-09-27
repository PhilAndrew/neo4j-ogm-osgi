/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 *  conditions of the subcomponent's license, as noted in the LICENSE file.
 */

package org.neo4j.ogm.classloader;

import org.neo4j.ogm.Neo4JOGM;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The public API for metadata class loading in the OGM.
 *
 * Do not use Class.forName() because it may not work when frameworks
 * such as Play reconfigure the ClassLoader hierarchies. Instead, replace
 * all such calls with MetaDataClassLoader.loadClass()
 *
 * The principles behind this class loading technique are due to Vladimir Roubtsov,
 * as documented here: &lt;a href="http://www.javaworld.com/columns/jw-qna-index.shtml"&gt;
 *
 * If needed we can extend this class to load other types of resources apart from class files
 *
 * @author vince
 *
 */

public abstract class MetaDataClassLoader {

    private static final ClassLoader classLoader = ClassLoaderResolver.resolve();

    public static Class loadClass(final String name) throws ClassNotFoundException {
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