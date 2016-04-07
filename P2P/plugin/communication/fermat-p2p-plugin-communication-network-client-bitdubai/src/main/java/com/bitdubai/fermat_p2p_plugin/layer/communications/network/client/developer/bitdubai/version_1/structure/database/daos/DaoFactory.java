package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.database.daos.DaoFactory</code>
 * is a factory class to manage the construction of the dao classes
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DaoFactory {

    /**
     * Represent the clientConnectionHistoryDao instance
     */
    private ClientConnectionHistoryDao clientConnectionHistoryDao;

    /**
     * Represent the nodeConnectionHistoryDao instance
     */
    private NodeConnectionHistoryDao nodeConnectionHistoryDao;

    /**
     * Constructor
     * @param database
     */
    public DaoFactory(final Database database){
;
        this.clientConnectionHistoryDao = new ClientConnectionHistoryDao(database);
        this.nodeConnectionHistoryDao   = new NodeConnectionHistoryDao  (database);

    }

    public ClientConnectionHistoryDao getClientConnectionHistoryDao() {
        return clientConnectionHistoryDao;
    }

    public NodeConnectionHistoryDao getNodeConnectionHistoryDao() {
        return nodeConnectionHistoryDao;
    }

}
