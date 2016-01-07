package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.exceptions.CantInitializeChatMiddlewareDaoException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import java.util.UUID;

/**
 * Created by miguel payarez miguel_payarez@hotmail.com on 06/01/16.
 */
public class ChatMiddlewareDatabaseDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;


    //Constructor

    public ChatMiddlewareDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    public void initialize(UUID pluginId) throws CantInitializeChatMiddlewareDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId,ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeChatMiddlewareDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + ChatMiddlewareDatabaseConstants.CHATS_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeChatMiddlewareDaoException(CantInitializeChatMiddlewareDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

}