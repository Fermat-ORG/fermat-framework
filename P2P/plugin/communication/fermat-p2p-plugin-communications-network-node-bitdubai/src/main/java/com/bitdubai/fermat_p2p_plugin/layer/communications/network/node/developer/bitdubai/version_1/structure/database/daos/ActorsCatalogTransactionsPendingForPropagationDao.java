/*
 * @#ActorsCatalogTransactionsPendingForPropagation.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities.ActorsCatalogTransactionsPendingForPropagation;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.ActorsCatalogTransactionsPendingForPropagationDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorsCatalogTransactionsPendingForPropagationDao extends AbstractBaseDao<ActorsCatalogTransactionsPendingForPropagation> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public ActorsCatalogTransactionsPendingForPropagationDao(Database dataBase) {
        super(dataBase, "", "");
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected ActorsCatalogTransactionsPendingForPropagation getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {
        return null;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(ActorsCatalogTransactionsPendingForPropagation entity) {
        return null;
    }
}
