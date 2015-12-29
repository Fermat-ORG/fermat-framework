package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 12/12/15.
 */
public class CustomerOfflinePaymentTransactionManager implements CustomerOfflinePaymentManager {

    /**
     * Represents the CustomerBrokerContractPurchaseManager
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    /**
     * Represents the CustomerOnlinePaymentBusinessTransactionDao
     */
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;

    /**
     * Represents the ErrorManager
     */
    ErrorManager errorManager;

    public CustomerOfflinePaymentTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao,
            ErrorManager errorManager){
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerOfflinePaymentBusinessTransactionDao=customerOfflinePaymentBusinessTransactionDao;
        this.errorManager=errorManager;
    }

    @Override
    public void sendPayment(String contractHash) throws CantSendPaymentException {

        try{
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            //Get contract
            CustomerBrokerContractPurchase customerBrokerContractPurchase=
                    customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(
                            contractHash);

            this.customerOfflinePaymentBusinessTransactionDao.persistContractInDatabase(
                    customerBrokerContractPurchase);
        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot get the CustomerBrokerContractPurchase");
        } catch (CantInsertRecordException e) {
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot insert a database record.");
        } catch (ObjectNotSetException e) {
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "The contract hash/Id is null.");
        }

    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(
            String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.customerOfflinePaymentBusinessTransactionDao.getContractTransactionStatus(
                    contractHash);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        }

    }
}
