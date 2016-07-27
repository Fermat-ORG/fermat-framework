package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.OpenContractManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.OpenContractPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;

import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public class OpenContractTransactionManager implements OpenContractManager {

    /**
     * Represents the purchase contract
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    /**
     * Represents the sale contract
     */
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    private OpenContractBusinessTransactionDao openContractBusinessTransactionDao;

    /**
     * Represents the purchase negotiation
     */
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the sale negotiation
     */
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    //FiatIndexManager fiatIndexManager;

    /**
     * Represents the negotiation ID.
     */
    //private String negotiationId;

    /**
     * Represents the transaction transmission manager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    /**
     * Represents the pluginRoot
     */
    private OpenContractPluginRoot pluginRoot;

    public OpenContractTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            TransactionTransmissionManager transactionTransmissionManager,
            OpenContractBusinessTransactionDao openContractBusinessTransactionDao,
            OpenContractPluginRoot pluginRoot) {

        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
        this.pluginRoot = pluginRoot;
    }


    @Override
    public ContractTransactionStatus getOpenContractStatus(String negotiationId) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            ObjectChecker.checkArgument(negotiationId, "The negotiationId argument is null");
            return this.openContractBusinessTransactionDao.getContractTransactionStatusByNegotiationId(negotiationId);

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException("Cannot check a null contractHash/Id");

        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected Result", "Check the cause");
        }
    }

    @Override
    public void openSaleContract(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation, float referencePrice) throws CantOpenContractException {
        OpenContractBrokerContractManager openContractCustomerContractManager = new OpenContractBrokerContractManager(
                customerBrokerContractSaleManager,
                transactionTransmissionManager,
                openContractBusinessTransactionDao,
                pluginRoot);
        try {
            Object[] arguments = {customerBrokerSaleNegotiation, referencePrice};
            ObjectChecker.checkArguments(arguments);
            openContractCustomerContractManager.openContract(customerBrokerSaleNegotiation, referencePrice);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantOpenContractException(e, "Creating a new contract", "Unexpected result from database");
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantOpenContractException(e,
                    "Creating Open Contract Business Transaction",
                    "Invalid input to this manager");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantOpenContractException(exception,
                    "Unexpected Result",
                    "Check the cause");
        }
        //openContract(negotiationId);
    }

    @Override
    public void openPurchaseContract(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
                                     float referencePrice) throws CantOpenContractException {
        OpenContractCustomerContractManager openContractCustomerContractManager = new OpenContractCustomerContractManager(
                customerBrokerContractPurchaseManager,
                transactionTransmissionManager,
                openContractBusinessTransactionDao,
                pluginRoot);
        try {
            Object[] arguments = {customerBrokerPurchaseNegotiation, referencePrice};
            ObjectChecker.checkArguments(arguments);
            openContractCustomerContractManager.openContract(customerBrokerPurchaseNegotiation, referencePrice);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantOpenContractException(e, "Creating a new contract", "Unexpected result from database");
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantOpenContractException(e,
                    "Creating Open Contract Business Transaction",
                    "Invalid input to this manager");
        } catch (Exception exception) {
            pluginRoot.reportError(

                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantOpenContractException(exception,
                    "Unexpected Result",
                    "Check the cause");
        }

        //openContract(negotiationId);
    }

    /**
     * This method returns the transaction completion date.
     * If returns 0 the transaction is processing.
     *
     * @param contractHash
     * @return
     * @throws CantGetCompletionDateException
     */
    @Override
    public long getCompletionDate(String contractHash) throws CantGetCompletionDateException {
        //TODO to implement
        return 0;
    }

    @Override
    public boolean isOpenContract(String negotiationId)
            throws UnexpectedResultReturnedFromDatabaseException {

        return openContractBusinessTransactionDao.contractOfNegotiationExists(UUID.fromString(negotiationId));

    }

}
