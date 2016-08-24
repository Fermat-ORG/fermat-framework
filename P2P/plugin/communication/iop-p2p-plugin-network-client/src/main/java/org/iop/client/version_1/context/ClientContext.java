package org.iop.client.version_1.context;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class <code>ClientContext</code>
 * create a context which holds the reference to other instance in the node
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class ClientContext {

    /**
     * Represent the cache to holds all references
     */
    private static final ConcurrentHashMap<ClientContextItem, Object> references = new ConcurrentHashMap<>();

    /**
     * Add new reference to the context
     *
     * @param key of the reference
     * @param value of the reference
     */
    public static void add(final ClientContextItem key  ,
                           final Object            value){

        references.put(key, value);
    }

    /**
     * Obtain a reference from the context
     * @param key of the reference
     * @return reference
     */
    public static Object get(final ClientContextItem key){

        return references.get(key);
    }

    /**
     * Remove a reference from the conext
     *
     * @param key of the reference
     *
     * @return reference remove
     */
    public static Object remove(final ClientContextItem key){
        return references.remove(key);
    }

}
