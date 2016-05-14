package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.ClientsSessionMemoryCache</code>
 * is responsible the manage the cache of the client session connected with the <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.servers.FermatWebSocketClientChannelServerEndpoint</code><p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientsSessionMemoryCache {

    /**
     * Represent the singleton instance
     */
    private static ClientsSessionMemoryCache instance;

    /**
     * Holds all client sessions
     */
    private final Map<String , Session> clientSessionsByPk;
    private final Map<Session, String > clientSessionsBySession;

    /**
     * Constructor
     */
    private ClientsSessionMemoryCache(){
        super();
        clientSessionsByPk      = Collections.synchronizedMap(new HashMap<String , Session>());
        clientSessionsBySession = Collections.synchronizedMap(new HashMap<Session, String >());
    }

    /**
     * Return the singleton instance
     *
     * @return ClientsSessionMemoryCache
     */
    public static ClientsSessionMemoryCache getInstance(){

        /*
         * If no exist create a new one
         */
        if (instance == null){
            instance = new ClientsSessionMemoryCache();
        }

        return instance;
    }

    /**
     * Get the session client
     *
     * @param clientPublicKeyIdentity the client identity
     * @return the session of the client
     */
    public Session get(String clientPublicKeyIdentity){

        /*
         * Return the session of this client
         */
        return instance.clientSessionsByPk.get(clientPublicKeyIdentity);
    }

    /**
     * Get the session client
     *
     * @param session the session of the connection
     * @return the session of the client
     */
    public String get(Session session){

        /*
         * Return the session of this client
         */
        return instance.clientSessionsBySession.get(session);
    }

    /**
     * Add a new session to the memory cache
     *
     * @param clientPublicKeyIdentity the client identity
     * @param session the client session
     */
    public void add(final String  clientPublicKeyIdentity,
                    final Session session                ){

        /*
         * Add to the cache
         */
        instance.clientSessionsByPk     .put(clientPublicKeyIdentity, session);
        instance.clientSessionsBySession.put(session                , clientPublicKeyIdentity);
    }

    /**
     * Remove the session client
     *
     * @param clientPublicKeyIdentity the client identity
     * @return the session of the client
     */
    public Session remove(String clientPublicKeyIdentity){

        /*
         * remove the session of this client
         */
        Session session = instance.clientSessionsByPk.remove(clientPublicKeyIdentity);

        instance.clientSessionsBySession.remove(session);

        return session;
    }

    /**
     * Remove the session client
     *
     * @param session the session of the connection
     * @return the public key of the client
     */
    public String remove(Session session){

        /*
         * remove the session of this client
         */
        String clientPublicKeyIdentity = instance.clientSessionsBySession.remove(session);

        instance.clientSessionsByPk.remove(clientPublicKeyIdentity);

        return clientPublicKeyIdentity;
    }

    /**
     * Verify is exist a session for a client
     *
     * @param clientPublicKeyIdentity the client identity
     * @return (TRUE or FALSE)
     */
    public boolean exist(String clientPublicKeyIdentity){

        return instance.clientSessionsByPk.containsKey(clientPublicKeyIdentity);
    }

    /**
     * Verify is exist a session for a client
     *
     * @param session the session of the connection
     * @return (TRUE or FALSE)
     */
    public boolean exist(Session session){

        return instance.clientSessionsBySession.containsKey(session);
    }

    /**
     * Get Client Sessions By Pk
     * @return Map<String, Session>
     */
    public static Map<String, Session> getClientSessionsByPk() {
        return instance.clientSessionsByPk;
    }
}
