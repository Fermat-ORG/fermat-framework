package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantCreateCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantGetListCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.exceptions.CantUpdateStatusCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNewManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerNewNegotiationTransactionException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 23.11.15.
 */

public class NegotiationTransactionCustomerBrokerNewPluginRoot  extends AbstractPlugin implements CustomerBrokerNewManager{

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    public NegotiationTransactionCustomerBrokerNewPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*Variables.*/
    private CustomerBrokerNewNegotiationTransactionDatabaseDao databaseDao;

    /*CustomerBrokerNewManager Interface implementation.*/
    public CustomerBrokerNew createCustomerBrokerNewNegotiationTranasction(String publicKeyBroker, String publicKeyCustomer, Collection<Clause> clauses) throws CantCreateCustomerBrokerNewNegotiationTransactionException{
        CustomerBrokerNew negotiationTransaction = null;

        try {
            UUID negotiationId = UUID.randomUUID();
            //GESTIONAR LA CREACION DE LA NEGOCIACION EN LA CAPA NEGOTIATION
            //ENVIAR POR EL NETWORK SERVICE LA INFORMACION. AL BROKER
            negotiationTransaction = databaseDao.createRegisterCustomerBrokerNewNegotiationTranasction(publicKeyBroker, publicKeyCustomer, negotiationId);
        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T CREATE NEW CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", "INSERT FAILURE");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T CREATE NEW CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", "UNKNOWN FAILURE");
        }

        return negotiationTransaction;
    }

    public void updateStatusCustomerBrokerNewNegotiationTranasction(UUID transactionId, NegotiationStatus statusTransaction) throws CantUpdateStatusCustomerBrokerNewNegotiationTransactionException{

        try {
            databaseDao.updateStatusRegisterCustomerBrokerNewNegotiationTranasction(transactionId, statusTransaction);
        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            throw new CantUpdateStatusCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T UPDATE STATUS CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", "UPDATE FAILURE");
        } catch (Exception e){
            throw new CantUpdateStatusCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T UPDATE STATUS CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", "UNKNOWN FAILURE");
        }

    }

    public CustomerBrokerNew getCustomerBrokerNewNegotiationTranasction(UUID transactionId) throws CantGetCustomerBrokerNewNegotiationTransactionException{
        CustomerBrokerNew negotiationTransaction = null;

        try {
            negotiationTransaction = databaseDao.getRegisterCustomerBrokerNewNegotiationTranasction(transactionId);
        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            throw new CantGetCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "GET FAILURE");
        } catch (Exception e){
            throw new CantGetCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "UNKNOWN FAILURE");
        }

        return negotiationTransaction;
    }

    public List<CustomerBrokerNew> getAllCustomerBrokerNewNegotiationTranasction() throws CantGetListCustomerBrokerNewNegotiationTransactionException{
        List<CustomerBrokerNew> negotiationTransactions = null;

        try {
            negotiationTransactions = databaseDao.getAllRegisterCustomerBrokerNewNegotiationTranasction();
        } catch (CantRegisterCustomerBrokerNewNegotiationTransactionException e){
            throw new CantGetListCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "GET LIST FAILURE");
        } catch (Exception e){
            throw new CantGetListCustomerBrokerNewNegotiationTransactionException("CUSTOMER BROKER NEW NEGOTIATION TRANSACTION", e, "CAN'T CREATE NEW CRYPTO CUSTOMER ACTOR", "UNKNOWN FAILURE");
        }

        return negotiationTransactions;
    }

    /*

    public void sendCustomerBrokerNewNegotiationTranasction(CustomerBrokerNegotiation negotiation) throws CantSendCustomerBrokerNewNegotiationTransactionException{

    }

    public void receiveCustomerBrokerNewNegotiationTranasction(CustomerBrokerNegotiation negotiation) throws CantReceiveCustomerBrokerNewNegotiationTransactionException{

    }
    */

    /*Services Interface implementation.*/
    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.databaseDao = new CustomerBrokerNewNegotiationTransactionDatabaseDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
            this.databaseDao.initialize();
        } catch (CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException cantInitializeExtraUserRegistryException) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
            throw new CantStartPluginException(cantInitializeExtraUserRegistryException, this.getPluginVersionReference());
        }
    }

}