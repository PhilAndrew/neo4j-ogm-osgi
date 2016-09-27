package org.neo4j.ogm;

//: ----------------------------------------------------------------------------------
//: Copyright © 2016 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: ----------------------------------------------------------------------------------

import org.osgi.framework.BundleContext;

public class Neo4JOGM {
    public static org.osgi.framework.BundleContext _context = null;

    public static void setBundleContext(BundleContext c) {
        _context = c;
    }
}
