package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.utils.DatabaseTransactionStatementPair</code>
 * its a wrapper class to be used in transactions statements.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class DatabaseTransactionStatementPair {

    private final DatabaseTable       table ;
    private final DatabaseTableRecord record;

    public DatabaseTransactionStatementPair(final DatabaseTable       table ,
                                            final DatabaseTableRecord record) {

        this.table  = table ;
        this.record = record;
    }

    public DatabaseTable getTable() {

        return table;
    }

    public DatabaseTableRecord getRecord() {

        return record;
    }

}
