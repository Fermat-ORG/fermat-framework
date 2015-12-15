package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;

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
     * Represents the customerBrokerPurchaseNegotiationManager
     */
    //private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the TransactionTransmissionManager
     */
    //private TransactionTransmissionManager transactionTransmissionManager;

    public CustomerOfflinePaymentTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao){
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerOfflinePaymentBusinessTransactionDao=customerOfflinePaymentBusinessTransactionDao;
        //this.transactionTransmissionManager=transactionTransmissionManager;
        //this.customerBrokerPurchaseNegotiationManager=customerBrokerPurchaseNegotiationManager;
    }

    @Override
    public void sendPayment(String contractHash) throws CantSendPaymentException {

        try{
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
        }
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(
            String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        return this.customerOfflinePaymentBusinessTransactionDao.getContractTransactionStatus(
                contractHash);
    }
}
