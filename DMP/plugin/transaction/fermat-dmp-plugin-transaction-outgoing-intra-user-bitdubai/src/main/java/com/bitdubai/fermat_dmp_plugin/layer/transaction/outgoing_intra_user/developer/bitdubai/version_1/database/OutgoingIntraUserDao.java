package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraUserDaoException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraUserDao {

    private Database             database;
    private ErrorManager         errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;

    public OutgoingIntraUserDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem) {
        this.errorManager         = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize(UUID pluginId) throws CantInitializeOutgoingIntraUserDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {

            OutgoingIntraUserTransactionDatabaseFactory databaseFactory = new OutgoingIntraUserTransactionDatabaseFactory(this.pluginDatabaseSystem);
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeOutgoingIntraUserDaoException("I couldn't create the database", cantCreateDatabaseException, "Database Name: " + OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME, "");
            } catch (Exception exception) {
                throw new CantInitializeOutgoingIntraUserDaoException(CantInitializeOutgoingIntraUserDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
            }

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeOutgoingIntraUserDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + OutgoingIntraUserTransactionDatabaseConstants.OUTGOING_INTRA_USER_DATABASE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeOutgoingIntraUserDaoException(CantInitializeOutgoingIntraUserDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
