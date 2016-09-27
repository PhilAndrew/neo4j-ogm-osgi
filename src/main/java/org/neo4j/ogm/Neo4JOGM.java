package org.neo4j.ogm;

//: ----------------------------------------------------------------------------------
//: Copyright © 2016 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: ----------------------------------------------------------------------------------

import org.osgi.framework.BundleContext;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Neo4JOGM {
    private static final Lock lock = new ReentrantLock();

    public static java.util.Set<org.osgi.framework.BundleContext> _contextSet = null;

    public static java.util.List<BundleContext> getContextList() {
        return new ArrayList<>(_contextSet);
    }

    public static void setBundleContext(BundleContext c) {
        lock.lock();
        if (_contextSet==null) {
            _contextSet = new java.util.HashSet<BundleContext>();
        }
        _contextSet.add(c);
        lock.unlock();
    }
}
