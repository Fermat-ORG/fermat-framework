package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.CustomerAckOfflineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_offline_merchandise.developer.bitdubai.version_1.database.CustomerAckOfflineMerchandiseBusinessTransactionDao;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.NOT_IMPORTANT;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/12/15.
 */
public class CustomerAckOfflineMerchandiseTransactionManager implements CustomerAckOfflineMerchandiseManager {

    /**
     * Represents the plugin database dao.
     */
    private CustomerAckOfflineMerchandiseBusinessTransactionDao dao;

    /**
     * Represents the CustomerBrokerContractSaleManager
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    CustomerAckOfflineMerchandisePluginRoot pluginRoot;

    public CustomerAckOfflineMerchandiseTransactionManager(
            CustomerAckOfflineMerchandiseBusinessTransactionDao dao,
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerAckOfflineMerchandisePluginRoot pluginRoot) {
        this.dao = dao;
        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.pluginRoot = pluginRoot;
    }

    @Override
    public void ackMerchandise(String contractHash) throws CantAckMerchandiseException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            //First we check if the contract exits in this plugin database
            boolean contractExists = dao.isContractHashInDatabase(contractHash);
            CustomerBrokerContractPurchase customerBrokerContractPurchase;
            if (!contractExists) {
                /**
                 * If the contract is not in database, we are going to check if exists in contract Layer,
                 * in theory this won't happen, when a contract is open is created in contract layer
                 * and is raised an event that build a record in this plugin database. In this case we
                 * will suppose that the agent in this plugin has not created the contract, but exists in
                 * contract layer.
                 */
                customerBrokerContractPurchase = customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);
                if (customerBrokerContractPurchase == null) {
                    throw new CantAckMerchandiseException(new StringBuilder().append("The CustomerBrokerContractPurchase with the hash \n").append(contractHash).append("\nis null").toString());
                }

                System.out.println("ACK_OFFLINE_MERCHANDISE - ackMerchandise() - persistContractInDatabase()");

                this.dao.persistContractInDatabase(customerBrokerContractPurchase);

            } else {
                /**
                 * The contract exists in database, we are going to check the contract status.
                 * We are going to get the record from this contract and
                 * update the status to indicate the agent to send a ack notification to a Crypto Broker.
                 */
                ContractTransactionStatus contractTransactionStatus = getContractTransactionStatus(contractHash);
                //If the status is different to PENDING_ACK_OFFLINE_MERCHANDISE the ack process was started.
                if (contractTransactionStatus != ContractTransactionStatus.PENDING_ACK_OFFLINE_MERCHANDISE) {
                    dao.updateContractTransactionStatus(contractHash,
                            ContractTransactionStatus.PENDING_OFFLINE_MERCHANDISE_CONFIRMATION);

                } else {
                    try {
                        throw new CantAckMerchandiseException(new StringBuilder().append("The Ack offline merchandise with the contract ID ").append(contractHash).append(" process has begun").toString());
                    } catch (CantAckMerchandiseException e) {
                        pluginRoot.reportError(NOT_IMPORTANT, e);
                    }
                }
            }

        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAckMerchandiseException(e,
                    "Creating Customer Ack Offline Merchandise Business Transaction",
                    "Unexpected result from database");

        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAckMerchandiseException(e,
                    "Creating Customer Ack Offline Merchandise Business Transaction",
                    "Cannot get the contract from customerBrokerContractSaleManager");

        } catch (CantInsertRecordException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAckMerchandiseException(e,
                    "Creating Customer Ack Offline Merchandise Business Transaction",
                    "Cannot insert the contract record in database");

        } catch (CantUpdateRecordException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAckMerchandiseException(e,
                    "Creating Customer Ack Offline Merchandise Business Transaction",
                    "Cannot update the contract status in database");

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAckMerchandiseException(e,
                    "Creating Customer Ack Offline Merchandise Business Transaction",
                    "The contract hash/Id is null");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAckMerchandiseException(e, "Unexpected Result", "Check the cause");
        }

    }

    /**
     * This method returns the contract transaction status
     *
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.dao.getContractTransactionStatus(contractHash);

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException("Cannot check a null contractHash/Id");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Unexpected Result", "Check the cause");
        }
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
        try {
            ObjectChecker.checkArgument(contractHash, "The contract hash argument is null");
            return this.dao.getCompletionDateByContractHash(contractHash);

        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "Unexpected exception from database");

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "The contract hash argument is null");
        }
    }
}
