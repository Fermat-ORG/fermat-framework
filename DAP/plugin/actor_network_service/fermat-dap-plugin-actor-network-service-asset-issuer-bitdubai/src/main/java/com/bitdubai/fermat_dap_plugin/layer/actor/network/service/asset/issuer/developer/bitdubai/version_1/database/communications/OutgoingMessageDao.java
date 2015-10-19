package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.issuer.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;

/**
 * Created by franklin on 17/10/15.
 */
public class OutgoingMessageDao {
    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public OutgoingMessageDao(Database dataBase) {
        super();
        this.dataBase = dataBase;
    }
}
