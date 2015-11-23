package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */
public class CustomerBrokerNewNegotiationTransactionDatabaseDao {
    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    public CustomerBrokerNewNegotiationTransactionDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    Database database;

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerNewNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerNewNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    //CREATE NEW NEGOTIATION TRANSACTION
    public CustomerBrokerNew createRegisterCustomerBrokerNewNegotiationTranasction(String publicKeyCustomer, String publicKeyBroker) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        return null;
    }

    public CustomerBrokerNew updateStatusRegisterCustomerBrokerNewNegotiationTranasction(UUID transactionId, NegotiationStatus statusTransaction) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        return null;
    }

    public CustomerBrokerNew getRegisterCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        return null;
    }

    public List<CustomerBrokerNew> getAllRegisterCustomerBrokerNewNegotiationTranasction() throws CantRegisterCustomerBrokerNewNegotiationTransactionException{
        return null;
    }

    //UPDATE STATUS NEW NEGOTIATION TRANSACTION


    //GET NEW NEGOTIATION TRANSACTION


    //GET LIST NEW NEGOTIATION TRANSACTION


    //OTHER

}
