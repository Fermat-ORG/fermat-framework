package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckPaymentException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/12/15.
 */
public class BrokerAckOfflinePaymentTransactionManager implements BrokerAckOfflinePaymentManager {

    /**
     * Represents the plugin database DAO
     */
    BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao;

    /**
     * Represents the CustomerBrokerContractSaleManager
     */
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    /**
     * Represents the error manager
     */
    ErrorManager errorManager;

    //TODO: I need to define if this manager needs others arguments in constructor
    public BrokerAckOfflinePaymentTransactionManager(
            BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            ErrorManager errorManager){
        this.brokerAckOfflinePaymentBusinessTransactionDao = brokerAckOfflinePaymentBusinessTransactionDao;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        this.errorManager=errorManager;

    }

    /**
     * This method creates an ack offline payment by a contract hash given
     * @param walletPublicKey
     * @param contractHash
     * @throws CantAckPaymentException
     */
    @Override
    public void ackPayment(String walletPublicKey,
                           String contractHash) throws
            CantAckPaymentException {
        try{
            //First we check if the contract exits in this plugin database
            boolean contractExists=
                    this.brokerAckOfflinePaymentBusinessTransactionDao.isContractHashInDatabase(
                            contractHash);
            CustomerBrokerContractSale customerBrokerContractSale;
            if(!contractExists){
                /**
                 * If the contract is not in database, we going to check if exists in contract Layer,
                 * in theory this won't happen, when a contract is open is created in contract layer
                 * and is raised an event that build a record in this plugin database. In this case we
                 * will suppose that the agent in this plugin is not created the contract, but exists in
                 * contract layer.
                 */
                customerBrokerContractSale=
                        this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(
                                contractHash);
                if(customerBrokerContractSale==null){
                    throw new CantAckPaymentException("The CustomerBrokerContractSale with the hash \n" +
                            contractHash+"\n" +
                            "is null");
                }
                this.brokerAckOfflinePaymentBusinessTransactionDao.persistContractInDatabase(
                        customerBrokerContractSale);

            } else{
                /**
                 * The contract exists in database, we are going to check the contract status.
                 * We going to get the record from this contract and
                 * update the status to indicate the agent to send a ack notification to a Crypto Customer.
                 */
                ContractTransactionStatus contractTransactionStatus=getContractTransactionStatus(
                    contractHash);
                //If the status is different to PENDING_OFFLINE_PAYMENT_CONFIRMATION the ack process was started.
                if(!contractTransactionStatus.getCode()
                        .equals(ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT.getCode())){
                    this.brokerAckOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(
                            contractHash,
                            ContractTransactionStatus.PENDING_OFFLINE_PAYMENT_CONFIRMATION);

                } else{
                    try{
                        throw new CantAckPaymentException(
                                "The Ack offline payment with the contract ID "+
                                        contractHash +
                                        " process has begun");
                    }catch (CantAckPaymentException e){
                        errorManager.reportUnexpectedPluginException(
                                Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                                UnexpectedPluginExceptionSeverity.NOT_IMPORTANT,
                                e);
                    }
                }

            }
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CantAckPaymentException(e,
                    "Creating Broker Ack Offline Payment Business Transaction",
                    "Unexpected result from database");
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantAckPaymentException(e,
                    "Creating Broker Ack Offline Payment Business Transaction",
                    "Cannot get the contract from customerBrokerContractSaleManager");
        } catch (CantInsertRecordException e) {
            throw new CantAckPaymentException(e,
                    "Creating Broker Ack Offline Payment Business Transaction",
                    "Cannot insert the contract record in database");
        } catch (CantUpdateRecordException e) {
            throw new CantAckPaymentException(e,
                    "Creating Broker Ack Offline Payment Business Transaction",
                    "Cannot update the contract status in database");
        }

    }

    /**
     * This method returns the actual ContractTransactionStatus by the contract Id/hash
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    @Override
    public ContractTransactionStatus getContractTransactionStatus(
            String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        return this.brokerAckOfflinePaymentBusinessTransactionDao.getContractTransactionStatus(
                contractHash);
    }
}
