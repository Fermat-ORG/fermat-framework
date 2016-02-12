package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.close_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
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
            CloseContractBusinessTransactionDao closeContractBusinessTransactionDao){

        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.closeContractBusinessTransactionDao=closeContractBusinessTransactionDao;

    }

    @Override
    public void closeSaleContract(String contractHash) throws CantCloseContractException {
        /*CloseContractBrokerContractManager closeContractBrokerContractManager=
                new CloseContractBrokerContractManager(
                        this.customerBrokerContractSaleManager,
                        this.transactionTransmissionManager,
                        this.closeContractBusinessTransactionDao);*/
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            CustomerBrokerContractSale customerBrokerContractSale=
                    this.customerBrokerContractSaleManager.
                            getCustomerBrokerContractSaleForContractId(contractHash);
            this.closeContractBusinessTransactionDao.persistContractRecord(
                    customerBrokerContractSale,
                    ContractType.SALE);
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantCloseContractException(e,
                    "Closing Sale Contract",
                    "Cannot get the Sale contract");
        } catch (CantInsertRecordException e) {
            throw new CantCloseContractException(e,
                    "Closing Sale Contract",
                    "Cannot insert the contract record in database");
        } catch (ObjectNotSetException e) {
            throw new CantCloseContractException(e,
                    "Closing Sale Contract",
                    "The contract hash/Id is null");
        }
    }

    @Override
    public void closePurchaseContract(String contractHash) throws CantCloseContractException {
        /*CloseContractCustomerContractManager closeContractCustomerContractManager=
                new CloseContractCustomerContractManager(
                        this.customerBrokerContractPurchaseManager,
                        this.transactionTransmissionManager,
                        this.closeContractBusinessTransactionDao);*/
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            CustomerBrokerContractPurchase customerBrokerContractPurchase=
                    this.customerBrokerContractPurchaseManager.
                            getCustomerBrokerContractPurchaseForContractId(contractHash);
            ContractStatus contractStatus=customerBrokerContractPurchase.getStatus();
            if(contractStatus.getCode().equals(ContractStatus.MERCHANDISE_SUBMIT)){
                this.closeContractBusinessTransactionDao.persistContractRecord(
                        customerBrokerContractPurchase,
                        ContractType.PURCHASE);
            }else{
                throw new CantCloseContractException("The contract with the hash\n"+
                        contractHash+"\n cannot be closed, because the ContractStatus is "+
                        contractStatus);
            }

        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            throw new CantCloseContractException(e,
                    "Closing Purchase Contract",
                    "Cannot get the Purchase contract");
        } catch (CantInsertRecordException e) {
            throw new CantCloseContractException(e,
                    "Closing Purchase Contract",
                    "Cannot insert the contract record in database");
        } catch (ObjectNotSetException e) {
            throw new CantCloseContractException(e,
                    "Closing Purchase Contract",
                    "The contract hash/Id is null");
        }

    }

    @Override
    public ContractTransactionStatus getCloseContractStatus(
            String contractHash) throws
            UnexpectedResultReturnedFromDatabaseException {
        try{
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.closeContractBusinessTransactionDao.getContractTransactionStatus(contractHash);
        } catch (ObjectNotSetException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting the contract transaction status",
                    "The contract hash/Id is null");
        }
    }
}
