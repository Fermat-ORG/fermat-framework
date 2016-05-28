package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext</code> create a context
 * holds the reference to other instance in the node
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class NodeContext {

    /**
     * Represent the cache to holds all references
     */
    private static final ConcurrentHashMap<NodeContextItem, Object> references = new ConcurrentHashMap<>();

    /**
     * Add new reference to the context
     * @param key of the reference
     * @param value of the reference
     */
    public static void add(final NodeContextItem key  ,
                           final Object          value){

        references.put(key, value);
    }

    /**
     * Obtain a reference from the context
     * @param key of the reference
     * @return reference
     */
    public static Object get(NodeContextItem key){

        return references.get(key);
    }

    /**
     * Remove a reference from the conext
     * @param key of the reference
     * @return reference remove
     */
    public static Object remove(NodeContextItem key){
        return references.remove(key);
    }

}
