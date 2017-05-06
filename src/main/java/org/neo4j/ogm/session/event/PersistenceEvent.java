/*
 * Copyright (c) 2002-2017 "Neo Technology,"
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

package org.neo4j.ogm.session.event;

/**
 * Represents a PersistenceEvent in the OGM.
 *
 * @author Vince Bickers
 */
public class PersistenceEvent implements Event {

    private Object affectedObject;
    private TYPE type;

    public PersistenceEvent(Object affectedObject, TYPE lifecycle) {
        this.affectedObject = affectedObject;
        this.type = lifecycle;
    }

    public Object getObject() {
        return affectedObject;
    }

    public TYPE getLifeCycle() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Event: %s, %s", type, affectedObject);
    }
}
