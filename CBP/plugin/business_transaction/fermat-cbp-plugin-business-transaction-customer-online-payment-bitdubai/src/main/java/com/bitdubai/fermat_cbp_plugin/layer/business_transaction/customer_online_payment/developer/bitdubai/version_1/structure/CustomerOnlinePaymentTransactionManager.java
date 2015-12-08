package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOnlinePaymentTransactionManager implements CustomerOnlinePaymentManager {

    /**
     * Represents the CustomerBrokerContractPurchaseManager
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    /**
     * Represents the CustomerOnlinePaymentBusinessTransactionDao
     */
    private CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;

    /**
     * Represents the TransactionTransmissionManager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public CustomerOnlinePaymentTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao,
            TransactionTransmissionManager transactionTransmissionManager){
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerOnlinePaymentBusinessTransactionDao=customerOnlinePaymentBusinessTransactionDao;
        this.transactionTransmissionManager=transactionTransmissionManager;
    }

    @Override
    public void sendPayment(String contractHash) throws CantSendPaymentException {
        /**
         * TODO: Get contract, persist in database the base information, leave the send crypto to monitor agent
         */
        try{
            //Get contract
            CustomerBrokerContractPurchase customerBrokerContractPurchase=
                    customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(
                            contractHash);
            this.customerOnlinePaymentBusinessTransactionDao.persistContractInDatabase(
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
        }

    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        return this.customerOnlinePaymentBusinessTransactionDao.getContractTransactionStatus(
                contractHash);
    }

    //TODO: define if this is necessary in this plugin
    /*@Override
    public ContractStatus getContractStatus(String contractHash) {
        return null;
    }*/
}
