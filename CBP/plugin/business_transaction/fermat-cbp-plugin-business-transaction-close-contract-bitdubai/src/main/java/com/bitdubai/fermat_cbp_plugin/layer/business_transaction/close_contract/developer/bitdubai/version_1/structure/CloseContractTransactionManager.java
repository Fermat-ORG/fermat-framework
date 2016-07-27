package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.exceptions.CantCloseContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.close_contract.interfaces.CloseContractManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.database.CloseContractBusinessTransactionDao;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/12/15.
 */
public class CloseContractTransactionManager implements CloseContractManager {
    ErrorManager errorManager;

    /**
     * Represents the CloseContractBusinessTransactionDao
     */
    CloseContractBusinessTransactionDao closeContractBusinessTransactionDao;

    /**
     * Represents the CustomerBrokerContractPurchaseManager
     */
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    /**
     * Represents the CustomerBrokerContractSaleManager
     */
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    /**
     * Represents the TransactionTransmissionManager
     */
    TransactionTransmissionManager transactionTransmissionManager;

    public CloseContractTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            TransactionTransmissionManager transactionTransmissionManager,
            CloseContractBusinessTransactionDao closeContractBusinessTransactionDao) {

        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.closeContractBusinessTransactionDao = closeContractBusinessTransactionDao;

    }

    @Override
    public void closeSaleContract(String contractHash) throws CantCloseContractException {

        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            CustomerBrokerContractSale customerBrokerContractSale = this.customerBrokerContractSaleManager.
                    getCustomerBrokerContractSaleForContractId(contractHash);

            System.out.print("\nTEST CONTRACT - CLOSE CONTRACT - MANAGER - closeSaleContract()\n");
            this.closeContractBusinessTransactionDao.persistContractRecord(customerBrokerContractSale, ContractType.SALE);

        } catch (CantGetListCustomerBrokerContractSaleException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCloseContractException(e, "Closing Sale Contract", "Cannot get the Sale contract");

        } catch (CantInsertRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCloseContractException(e, "Closing Sale Contract", "Cannot insert the contract record in database");

        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCloseContractException(e, "Closing Sale Contract", "The contract hash/Id is null");
        }
    }

    @Override
    public void closePurchaseContract(String contractHash) throws CantCloseContractException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            CustomerBrokerContractPurchase customerBrokerContractPurchase = this.customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(contractHash);

            ContractStatus contractStatus = customerBrokerContractPurchase.getStatus();

            System.out.print("\nTEST CONTRACT - CLOSE CONTRACT - MANAGER - closeSaleContract()\n");
            if (contractStatus.getCode().equals(ContractStatus.READY_TO_CLOSE.getCode())) {
                System.out.print("\nTEST CONTRACT - CLOSE CONTRACT - MANAGER - closeSaleContract() - STATUS VAL\n");
//            if (contractStatus.getCode().equals(ContractStatus.MERCHANDISE_SUBMIT.getCode()))
                this.closeContractBusinessTransactionDao.persistContractRecord(customerBrokerContractPurchase, ContractType.PURCHASE);
            } else {
                throw new CantCloseContractException(new StringBuilder().append("The contract with the hash\n").append(contractHash).append("\n cannot be closed, because the ContractStatus is ").append(contractStatus).toString());
            }

        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCloseContractException(e, "Closing Purchase Contract", "Cannot get the Purchase contract");

        } catch (CantInsertRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCloseContractException(e, "Closing Purchase Contract", "Cannot insert the contract record in database");

        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCloseContractException(e, "Closing Purchase Contract", "The contract hash/Id is null");
        }
    }

    @Override
    public ContractTransactionStatus getCloseContractStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.closeContractBusinessTransactionDao.getContractTransactionStatus(contractHash);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CLOSE_CONTRACT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the contract transaction status", "The contract hash/Id is null");
        }
    }

    @Override
    public long getCompletionDate(String contractHash) throws CantGetCompletionDateException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contract hash argument is null");
            return this.closeContractBusinessTransactionDao.getCompletionDateByContractHash(contractHash);

        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "Unexpected exception from database");

        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CUSTOMER_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "The contract hash argument is null");
        }
    }
}
