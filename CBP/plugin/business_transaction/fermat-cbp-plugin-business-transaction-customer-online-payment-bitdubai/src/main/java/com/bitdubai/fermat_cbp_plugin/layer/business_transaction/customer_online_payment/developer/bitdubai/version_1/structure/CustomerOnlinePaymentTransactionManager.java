package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantGetCryptoAddressException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantgetCryptoAmountException;

import java.util.Collection;
import java.util.UUID;

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
     * Represents the customerBrokerPurchaseNegotiationManager
     */
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /**
     * Represents the TransactionTransmissionManager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public CustomerOnlinePaymentTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager){
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerOnlinePaymentBusinessTransactionDao=customerOnlinePaymentBusinessTransactionDao;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.customerBrokerPurchaseNegotiationManager=customerBrokerPurchaseNegotiationManager;
    }

    /**
     * This method returns a CustomerBrokerPurchaseNegotiation by negotiationId.
     * @param negotiationId
     * @return
     */
    private CustomerBrokerPurchaseNegotiation getCustomerBrokerPurchaseNegotiation(
            String negotiationId) throws
            CantGetListPurchaseNegotiationsException {
        UUID negotiationUUID=UUID.fromString(negotiationId);
        CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation=
                customerBrokerPurchaseNegotiationManager.getNegotiationsByNegotiationId(
                        negotiationUUID);
        return customerBrokerPurchaseNegotiation;
    }

    /**
     * This method returns the crypto address from a CustomerBrokerPurchaseNegotiation
     * @param customerBrokerPurchaseNegotiation
     * @return
     * @throws CantGetCryptoAddressException
     */
    private String getBrokerCryptoAddressString(
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)
            throws CantGetCryptoAddressException {
        try{

            Collection<Clause> negotiationClauses=customerBrokerPurchaseNegotiation.getClauses();
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.BROKER_CRYPTO_ADDRESS)){
                    return clause.getValue();
                }
            }
            throw new CantGetCryptoAddressException(
                    "The Negotiation clauses doesn't include the broker crypto address");
        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAddressException(
                    e,
                    "Getting the broker crypto address",
                    "Cannot get the clauses list");
        }

    }

    /**
     * This method returns a crypto amount (long) from a CustomerBrokerPurchaseNegotiation
     * @return
     */
    private long getCryptoAmount(
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws
            CantgetCryptoAmountException{
        try{
            long cryptoAmount;
            Collection<Clause> negotiationClauses=customerBrokerPurchaseNegotiation.getClauses();
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.CUSTOMER_CURRENCY_QUANTITY)){
                    cryptoAmount=parseToLong(clause.getValue());
                    return cryptoAmount;
                }
            }
            throw new CantgetCryptoAmountException(
                    "The Negotiation clauses doesn't include the broker crypto amount");
        } catch (CantGetListClauseException e) {
            throw new CantgetCryptoAmountException(
                    e,
                    "Getting the broker crypto amount",
                    "Cannot get the clauses list");
        } catch (InvalidParameterException e) {
            throw new CantgetCryptoAmountException(
                    e,
                    "Getting the broker crypto amount",
                    "There is an error parsing a String to long.");
        }
    }

    /**
     * This method parse a String object to a long object
     * @param stringValue
     * @return
     * @throws InvalidParameterException
     */
    public long parseToLong(String stringValue) throws InvalidParameterException {
        if(stringValue==null){
            throw new InvalidParameterException("Cannot parse a null string value to long");
        }else{
            try{
                return Long.valueOf(stringValue);
            }catch (Exception exception){
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Parsing String object to long",
                        "Cannot parse "+stringValue+" string value to long");
            }

        }
    }

    @Override
    public void sendPayment(String walletPublicKey, String contractHash) throws CantSendPaymentException {
        /**
         * TODO: Get contract, persist in database the base information, leave the send crypto to monitor agent
         */
        try{
            //Get contract
            CustomerBrokerContractPurchase customerBrokerContractPurchase=
                    customerBrokerContractPurchaseManager.getCustomerBrokerContractPurchaseForContractId(
                            contractHash);
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation=
                    getCustomerBrokerPurchaseNegotiation(
                            customerBrokerContractPurchase.getNegotiatiotId());
            String brokerCryptoAddress=getBrokerCryptoAddressString(
                    customerBrokerPurchaseNegotiation
            );
            long cryptoAmount=getCryptoAmount(customerBrokerPurchaseNegotiation);
            this.customerOnlinePaymentBusinessTransactionDao.persistContractInDatabase(
                    customerBrokerContractPurchase,
                    brokerCryptoAddress,
                    walletPublicKey,
                    cryptoAmount);
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
        } catch (CantGetCryptoAddressException e) {
            e.printStackTrace();
        } catch (CantGetListPurchaseNegotiationsException e) {
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot get the CustomerBrokerPurchaseNegotiation list");
        } catch (CantgetCryptoAmountException e) {
            e.printStackTrace();
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
