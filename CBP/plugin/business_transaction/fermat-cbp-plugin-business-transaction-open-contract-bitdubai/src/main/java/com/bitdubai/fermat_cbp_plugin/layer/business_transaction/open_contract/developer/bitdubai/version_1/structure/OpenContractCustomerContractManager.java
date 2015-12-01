package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.AbstractOpenContract;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractPurchaseRecord;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces.ContractSaleRecord;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.database.OpenContractBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions.CannotFindContractHashException;

import java.util.Collection;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class OpenContractCustomerContractManager extends AbstractOpenContract {

    /**
     * Represents the purchase contract
     */
    private CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;

    OpenContractBusinessTransactionDao openContractBusinessTransactionDao;

    /**
     * Represents the purchase negotiation
     */
    //private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the Fiat index.
     */
    //private FiatIndexManager fiatIndexManager;

    /**
     * Represents the transaction transmission manager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public OpenContractCustomerContractManager(CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
                                               TransactionTransmissionManager transactionTransmissionManager,
                                               OpenContractBusinessTransactionDao openContractBusinessTransactionDao) {

        this.customerBrokerContractPurchaseManager = customerBrokerContractPurchaseManager;
        this.transactionTransmissionManager = transactionTransmissionManager;
        this.openContractBusinessTransactionDao = openContractBusinessTransactionDao;
    }


    /*private CustomerBrokerPurchaseNegotiation findPurchaseNegotiation(String negotiationId) throws CantGetNegotiationStatusException {

        try{
            Collection<CustomerBrokerPurchaseNegotiation> negotiationCollection= customerBrokerPurchaseNegotiationManager.getNegotiations(NegotiationStatus.CLOSED);
            for(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation : negotiationCollection){
                String negotiationUUID=customerBrokerPurchaseNegotiation.getNegotiationId().toString();
                if(negotiationId.equals(negotiationUUID)){
                    return customerBrokerPurchaseNegotiation;
                }
            }
            throw new CantGetNegotiationStatusException("Cannot find the Negotiation Id \n"+
                    negotiationId+"\n" +
                    "in the Purchase Negotiation Database in CLOSED status");
        } catch (CantGetListPurchaseNegotiationsException exception) {
            throw new CantGetNegotiationStatusException(exception,
                    "Checking if Negotiation is closed",
                    "Cannot get the Purchase Negotiation list");
        }

    }*/

    public void openContract(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
                             FiatIndex fiatIndex) throws
            CantOpenContractException,
            UnexpectedResultReturnedFromDatabaseException {

        contractType= ContractType.PURCHASE;
        try{
            //CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation= findPurchaseNegotiation(negotiationId);
            Collection<Clause> negotiationClauses=customerBrokerPurchaseNegotiation.getClauses();
            ContractPurchaseRecord contractRecord=createPurchaseContractRecord(
                    negotiationClauses,
                    customerBrokerPurchaseNegotiation,
                    fiatIndex);
            contractRecord.setStatus(ContractStatus.PENDING_PAYMENT);
            this.openContractBusinessTransactionDao.persistContractRecord(
                    contractRecord,
                    contractType);
            customerBrokerContractPurchaseManager.createCustomerBrokerContractPurchase(contractRecord);
            this.openContractBusinessTransactionDao.updateContractTransactionStatus(
                    contractRecord.getContractId(),
                    ContractTransactionStatus.PENDING_SUBMIT);
        } catch (CantGetListClauseException exception) {
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot get the negotiation clauses list");
        }  catch (InvalidParameterException exception) {
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "An invalid parameter has detected");
        } catch (CantGetIndexException exception) {
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot get the fiat index");
        } catch (CantInsertRecordException exception) {
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot insert the contract record in database");
        } catch (CantCreateCustomerBrokerContractPurchaseException exception) {
            throw new CantOpenContractException(exception,
                    "Opening a new contract",
                    "Cannot create the CustomerBrokerContractPurchase");
        }  catch (CantUpdateRecordException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Opening a new contract",
                    "Cannot update ContractTransactionStatus");
        } catch (CannotFindContractHashException exception) {
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Opening a new contract",
                    "Cannot find the contract status in database");
        }

    }

}
