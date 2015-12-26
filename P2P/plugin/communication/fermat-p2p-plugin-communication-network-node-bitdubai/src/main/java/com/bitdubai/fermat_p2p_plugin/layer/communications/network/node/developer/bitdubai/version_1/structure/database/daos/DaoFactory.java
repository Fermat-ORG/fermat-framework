/*
 * @#DaoFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory</code> is
 * a factory class to manage the construction of the dao's classes
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DaoFactory {

    /**
     * Represent the actorsCatalogDao instance
     */
    private ActorsCatalogDao actorsCatalogDao;

    /**
     * Represent the actorsCatalogTransactionDao instance
     */
    private ActorsCatalogTransactionDao actorsCatalogTransactionDao;

    /**
     * Represent the actorsCatalogTransactionsPendingForPropagationDao instance
     */
    private ActorsCatalogTransactionsPendingForPropagationDao actorsCatalogTransactionsPendingForPropagationDao;

    /**
     * Represent the callsLogDao instance
     */
    private CallsLogDao callsLogDao;

    /**
     * Represent the checkedActorsHistoryDao instance
     */
    private CheckedActorsHistoryDao checkedActorsHistoryDao;

    /**
     * Represent the checkedClientsHistoryDao instance
     */
    private CheckedClientsHistoryDao checkedClientsHistoryDao;

    /**
     * Represent the checkedInActorDao instance
     */
    private CheckedInActorDao checkedInActorDao;

    /**
     * Represent the checkedInClientDao instance
     */
    private CheckedInClientDao checkedInClientDao;

    /**
     * Represent the checkedInNetworkServiceDao instance
     */
    private CheckedInNetworkServiceDao checkedInNetworkServiceDao;

    /**
     * Represent the checkedNetworkServicesHistoryDao instance
     */
    private CheckedNetworkServicesHistoryDao checkedNetworkServicesHistoryDao;

    /**
     * Represent the clientsConnectionHistoryDao instance
     */
    private ClientsConnectionHistoryDao clientsConnectionHistoryDao;

    /**
     * Represent the methodCallsHistoryDao instance
     */
    private MethodCallsHistoryDao methodCallsHistoryDao;

    /**
     * Represent the nodeConnectionHistoryDao instance
     */
    private NodeConnectionHistoryDao nodeConnectionHistoryDao;

    /**
     * Represent the nodesCatalogDao instance
     */
    private NodesCatalogDao nodesCatalogDao;

    /**
     * Represent the nodesCatalogTransactionDao instance
     */
    private NodesCatalogTransactionDao nodesCatalogTransactionDao;

    /**
     * Represent the nodesCatalogTransactionsPendingForPropagationDao instance
     */
    private NodesCatalogTransactionsPendingForPropagationDao nodesCatalogTransactionsPendingForPropagationDao;

    /**
     * Constructor
     * @param database
     */
    public DaoFactory(Database database){

        this.actorsCatalogDao                                  = new ActorsCatalogDao(database);
        this.actorsCatalogTransactionDao                       = new ActorsCatalogTransactionDao(database);
        this.actorsCatalogTransactionsPendingForPropagationDao = new ActorsCatalogTransactionsPendingForPropagationDao(database);
        this.callsLogDao                                       = new CallsLogDao(database);
        this.checkedActorsHistoryDao                           = new CheckedActorsHistoryDao(database);
        this.checkedClientsHistoryDao                          = new CheckedClientsHistoryDao(database);
        this.checkedInActorDao                                 = new CheckedInActorDao(database);
        this.checkedInClientDao                                = new CheckedInClientDao(database);
        this.checkedInNetworkServiceDao                        = new CheckedInNetworkServiceDao(database);
        this.checkedNetworkServicesHistoryDao                  = new CheckedNetworkServicesHistoryDao(database);
        this.clientsConnectionHistoryDao                       = new ClientsConnectionHistoryDao(database);
        this.methodCallsHistoryDao                             = new MethodCallsHistoryDao(database);
        this.nodeConnectionHistoryDao                          = new NodeConnectionHistoryDao(database);
        this.nodesCatalogDao                                   = new NodesCatalogDao(database);
        this.nodesCatalogTransactionDao                        = new NodesCatalogTransactionDao(database);
        this.nodesCatalogTransactionsPendingForPropagationDao  = new NodesCatalogTransactionsPendingForPropagationDao(database);

    }

    public ActorsCatalogDao getActorsCatalogDao() {
        return actorsCatalogDao;
    }

    public ActorsCatalogTransactionDao getActorsCatalogTransactionDao() {
        return actorsCatalogTransactionDao;
    }

    public ActorsCatalogTransactionsPendingForPropagationDao getActorsCatalogTransactionsPendingForPropagationDao() {
        return actorsCatalogTransactionsPendingForPropagationDao;
    }

    public CallsLogDao getCallsLogDao() {
        return callsLogDao;
    }

    public CheckedActorsHistoryDao getCheckedActorsHistoryDao() {
        return checkedActorsHistoryDao;
    }

    public CheckedClientsHistoryDao getCheckedClientsHistoryDao() {
        return checkedClientsHistoryDao;
    }

    public CheckedInActorDao getCheckedInActorDao() {
        return checkedInActorDao;
    }

    public CheckedInClientDao getCheckedInClientDao() {
        return checkedInClientDao;
    }

    public CheckedInNetworkServiceDao getCheckedInNetworkServiceDao() {
        return checkedInNetworkServiceDao;
    }

    public CheckedNetworkServicesHistoryDao getCheckedNetworkServicesHistoryDao() {
        return checkedNetworkServicesHistoryDao;
    }

    public ClientsConnectionHistoryDao getClientsConnectionHistoryDao() {
        return clientsConnectionHistoryDao;
    }

    public MethodCallsHistoryDao getMethodCallsHistoryDao() {
        return methodCallsHistoryDao;
    }

    public NodeConnectionHistoryDao getNodeConnectionHistoryDao() {
        return nodeConnectionHistoryDao;
    }

    public NodesCatalogDao getNodesCatalogDao() {
        return nodesCatalogDao;
    }

    public NodesCatalogTransactionDao getNodesCatalogTransactionDao() {
        return nodesCatalogTransactionDao;
    }

    public NodesCatalogTransactionsPendingForPropagationDao getNodesCatalogTransactionsPendingForPropagationDao() {
        return nodesCatalogTransactionsPendingForPropagationDao;
    }
}
