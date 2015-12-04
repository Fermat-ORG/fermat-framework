/*
 * @#NodeContext.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure;



import java.util.HashMap;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.NodeContext</code> create a context
 * holds the reference to other instance in the node
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeContext {

    /**
     * Represent the cache to holds all references
     */
    private static Map<String, Object> references = new HashMap<>();

    /**
     * Add new reference to the context
     * @param key of the reference
     * @param value of the reference
     */
    public static void add(String key, Object value){
        references.put(key, value);
    }

    /**
     * Obtain a reference from the context
     * @param key of the reference
     * @return reference
     */
    public static Object get(String key){
        return references.get(key);
    }

    /**
     * Remove a reference from the conext
     * @param key of the reference
     * @return reference remove
     */
    public static Object remove(String key){
        return references.remove(key);
    }

}
