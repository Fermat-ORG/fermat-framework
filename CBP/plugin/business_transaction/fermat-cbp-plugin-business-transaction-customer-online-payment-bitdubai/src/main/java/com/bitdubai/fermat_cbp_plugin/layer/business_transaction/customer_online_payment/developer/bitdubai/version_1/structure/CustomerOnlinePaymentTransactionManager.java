package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCryptoAmountException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
     * Represents the ErrorManager
     */
    ErrorManager errorManager;

    /**
     * Represents the TransactionTransmissionManager
     */
    private TransactionTransmissionManager transactionTransmissionManager;

    public CustomerOnlinePaymentTransactionManager(
            CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager,
            CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao,
            TransactionTransmissionManager transactionTransmissionManager,
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            ErrorManager errorManager){
        this.customerBrokerContractPurchaseManager=customerBrokerContractPurchaseManager;
        this.customerOnlinePaymentBusinessTransactionDao=customerOnlinePaymentBusinessTransactionDao;
        this.transactionTransmissionManager=transactionTransmissionManager;
        this.customerBrokerPurchaseNegotiationManager=customerBrokerPurchaseNegotiationManager;
        this.errorManager = errorManager;
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
    private String getBrokerCryptoAddressString(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantGetCryptoAddressException {
        try{
            Collection<Clause> negotiationClauses=customerBrokerPurchaseNegotiation.getClauses();
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.BROKER_CRYPTO_ADDRESS))
                    return clause.getValue();
            }
            throw new CantGetCryptoAddressException("The Negotiation clauses doesn't include the broker crypto address");
        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAddressException(e, "Getting the broker crypto address", "Cannot get the clauses list");
        }
    }

    /**
     * This method returns a crypto amount (long) from a CustomerBrokerPurchaseNegotiation
     * @return
     */
    private long getCryptoAmount(
            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws
            CantGetCryptoAmountException {
        try{
            long cryptoAmount;
            Collection<Clause> negotiationClauses=customerBrokerPurchaseNegotiation.getClauses();
            for(Clause clause : negotiationClauses){
                if(clause.getType().getCode().equals(ClauseType.BROKER_CURRENCY_QUANTITY.getCode())){
                    cryptoAmount= parseToCryptoAmountFormat(clause.getValue());
                    return cryptoAmount;
                }
            }
            throw new CantGetCryptoAmountException(
                    "The Negotiation clauses doesn't include the broker crypto amount");
        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAmountException(
                    e,
                    "Getting the broker crypto amount",
                    "Cannot get the clauses list");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoAmountException(
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
    public long parseToCryptoAmountFormat(String stringValue) throws InvalidParameterException {
        if(stringValue==null){
            throw new InvalidParameterException("Cannot parse a null string value to long");
        }else{
            try{
                double aux = Float.valueOf(stringValue)*100000000;
                return (long) aux;
            }catch (Exception exception){
                errorManager.reportUnexpectedPluginException(
                        Plugins.CUSTOMER_ONLINE_PAYMENT,
                        UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                        exception);
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Parsing String object to long",
                        "Cannot parse "+stringValue+" string value to long");
            }

        }
    }

    @Override
    public void sendPayment(
            String walletPublicKey,
            String contractHash) throws
            CantSendPaymentException {
        /**
         * TODO: Get contract, persist in database the base information, leave the send crypto to monitor agent
         */
        sendPayment(
                walletPublicKey,
                contractHash,
                BlockchainNetworkType.getDefaultBlockchainNetworkType());

    }

    @Override
    public void sendPayment(String walletPublicKey, String contractHash, BlockchainNetworkType blockchainNetworkType) throws CantSendPaymentException {

        try{
            //Checking the arguments
            Object[] arguments={walletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);

            //Get contract
            CustomerBrokerContractPurchase contractPurchase= customerBrokerContractPurchaseManager.
                    getCustomerBrokerContractPurchaseForContractId(contractHash);

            CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation = getCustomerBrokerPurchaseNegotiation(contractPurchase.getNegotiatiotId());
            String brokerCryptoAddress = getBrokerCryptoAddressString(customerBrokerPurchaseNegotiation);
            String aux[] = brokerCryptoAddress.split(":");
            String address,intraActorPK;
            address=aux[0];
            intraActorPK = aux[1];
            long cryptoAmount = getCryptoAmount(customerBrokerPurchaseNegotiation);

            this.customerOnlinePaymentBusinessTransactionDao.persistContractInDatabase(
                    contractPurchase,
                    address,
                    walletPublicKey,
                    cryptoAmount,
                    blockchainNetworkType,intraActorPK);

        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot get the CustomerBrokerContractPurchase");
        } catch (CantInsertRecordException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot insert a database record.");
        } catch (CantGetCryptoAddressException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot get the Broker Crypto Address");
        } catch (CantGetListPurchaseNegotiationsException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot get the CustomerBrokerPurchaseNegotiation list");
        } catch (CantGetCryptoAmountException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "Cannot get the Crypto Amount");
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantSendPaymentException(
                    e,
                    "Sending online payment",
                    "An argument is null");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    e);
            throw new CantSendPaymentException(e,
                    "Sending online payment",
                    "Unexpected error");
        }

    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try{
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.customerOnlinePaymentBusinessTransactionDao.getContractTransactionStatus(
                    contractHash);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected Result",
                    "Check the cause");
        }
    }

    //TODO: define if this is necessary in this plugin
    /*@Override
    public ContractStatus getContractStatus(String contractHash) {
        return null;
    }*/
    /**
     * This method returns the transaction completion date.
     * If returns 0 the transaction is processing.
     * @param contractHash
     * @return
     * @throws CantGetCompletionDateException
     */
    @Override
    public long getCompletionDate(String contractHash) throws CantGetCompletionDateException {
        try{
            ObjectChecker.checkArgument(contractHash, "The contract hash argument is null");
            return this.customerOnlinePaymentBusinessTransactionDao.getCompletionDateByContractHash(
                    contractHash);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "Unexpected exception from database");
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CUSTOMER_ONLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "The contract hash argument is null");
        }
    }
}
