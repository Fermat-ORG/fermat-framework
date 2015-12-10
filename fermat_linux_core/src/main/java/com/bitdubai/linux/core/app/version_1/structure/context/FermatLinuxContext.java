/*
 * @#FermatLinuxContext.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.linux.core.app.version_1.structure.context;


import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.linux.core.app.version_1.structure.context.FermatLinuxContext</code> create a context to
 * holds the references
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatLinuxContext {

    /**
     * Represent the cache to holds all references
     */
    private static Map<String, Object> references;

    /**
     * Represent the unique instance
     */
    private static FermatLinuxContext instance = new FermatLinuxContext();

    /**
     * Constructor
     */
    private FermatLinuxContext() {
        super();
        references = new HashMap<>();
    }

    /**
     * Return the instance
     *
     * @return FermatLinuxContext
     */
    public static FermatLinuxContext getInstance() {
        return instance;
    }

    /**
     * Add new reference to the context
     * @param key of the reference
     * @param value of the reference
     */
    public void add(String key, Object value){
        references.put(key, value);
    }

    /**
     * Obtain a reference from the context
     * @param key of the reference
     * @return reference
     */
    public Object get(String key){
        return references.get(key);
    }

    /**
     * Remove a reference from the conext
     * @param key of the reference
     * @return reference remove
     */
    public Object remove(String key){
        return references.remove(key);
    }

}
