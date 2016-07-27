package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCryptoAddressException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantGetCryptoAmountException;
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
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/12/15.
 */
public class CustomerOnlinePaymentTransactionManager implements CustomerOnlinePaymentManager {

    private CustomerBrokerContractPurchaseManager contractPurchaseManager;
    private CustomerOnlinePaymentBusinessTransactionDao dao;
    private CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager;
    private CustomerOnlinePaymentPluginRoot pluginRoot;


    public CustomerOnlinePaymentTransactionManager(CustomerBrokerContractPurchaseManager contractPurchaseManager,
                                                   CustomerOnlinePaymentBusinessTransactionDao dao,
                                                   CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager,
                                                   CustomerOnlinePaymentPluginRoot pluginRoot) {

        this.contractPurchaseManager = contractPurchaseManager;
        this.purchaseNegotiationManager = purchaseNegotiationManager;
        this.pluginRoot = pluginRoot;
        this.dao = dao;
    }

    @Override
    public void sendPayment(
            String walletPublicKey,
            String contractHash,
            CryptoCurrency paymentCurrency,
            FeeOrigin feeOrigin,
            long fee) throws CantSendPaymentException {
        sendPayment(
                walletPublicKey,
                contractHash,
                paymentCurrency,
                BlockchainNetworkType.getDefaultBlockchainNetworkType(),
                feeOrigin,
                fee);
    }

    @Override
    public void sendPayment(
            String walletPublicKey,
            String contractHash,
            CryptoCurrency paymentCurrency,
            BlockchainNetworkType blockchainNetworkType,
            FeeOrigin feeOrigin,
            long fee)
            throws CantSendPaymentException {

        try {
            //Checking the arguments
            Object[] arguments = {walletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);

            //Get contract
            CustomerBrokerContractPurchase contractPurchase = contractPurchaseManager.
                    getCustomerBrokerContractPurchaseForContractId(contractHash);

            CustomerBrokerPurchaseNegotiation purchaseNegotiation = getCustomerBrokerPurchaseNegotiation(contractPurchase.getNegotiatiotId());
            String brokerCryptoAddress = getBrokerCryptoAddressString(purchaseNegotiation);
            String split[] = brokerCryptoAddress.split(":");
            String address = split[0];
            String intraActorPK = split[1];

            long cryptoAmount = getCryptoAmount(purchaseNegotiation, paymentCurrency);

//            TODO: Manuel revisar esto para el issue
//            if(dao.isContractHashInDatabase(contractHash)) throw new CantSendPaymentException();

            dao.persistContractInDatabase(
                    contractPurchase,
                    address,
                    walletPublicKey,
                    cryptoAmount,
                    paymentCurrency,
                    blockchainNetworkType,
                    intraActorPK,
                    feeOrigin,
                    fee);

        } catch (CantGetListCustomerBrokerContractPurchaseException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantSendPaymentException(e, "Sending online payment", "Cannot get the CustomerBrokerContractPurchase");
        } catch (CantInsertRecordException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantSendPaymentException(e, "Sending online payment", "Cannot insert a database record.");
        } catch (CantGetCryptoAddressException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantSendPaymentException(e, "Sending online payment", "Cannot get the Broker Crypto Address");
        } catch (CantGetListPurchaseNegotiationsException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantSendPaymentException(e, "Sending online payment", "Cannot get the CustomerBrokerPurchaseNegotiation list");
        } catch (CantGetCryptoAmountException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantSendPaymentException(e, "Sending online payment", "Cannot get the Crypto Amount");
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantSendPaymentException(e, "Sending online payment", "An argument is null");
        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_THIS_PLUGIN, e);
            throw new CantSendPaymentException(e, "Sending online payment", "Unexpected error");
        }
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");

            return this.dao.getContractTransactionStatus(contractHash);

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException("Cannot check a null contractHash/Id");
        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected Result", "Check the cause");
        }
    }

    @Override
    public long getCompletionDate(String contractHash) throws CantGetCompletionDateException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contract hash argument is null");

            return dao.getCompletionDateByContractHash(contractHash);

        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "Unexpected exception from database");
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "The contract hash argument is null");
        }
    }

    /**
     * This method parse a String value representing a crypto amount to a long representation in Satoshi
     *
     * @param stringValue the value
     * @param currency    the crypto currency which is going to be parsed to Satoshi
     * @return the value in long representation
     * @throws InvalidParameterException
     */
    public long parseToCryptoAmountFormat(String stringValue, CryptoCurrency currency) throws InvalidParameterException {
        if (stringValue == null)
            throw new InvalidParameterException("Cannot parse a null string value to long");

        try {
            double amount = DecimalFormat.getInstance().parse(stringValue).doubleValue();

            switch (currency) {
                case BITCOIN:
                    amount = BitcoinConverter.convert(amount, BitcoinConverter.Currency.BITCOIN, BitcoinConverter.Currency.SATOSHI);
                    break;
                case FERMAT:
                    amount = BitcoinConverter.convert(amount, BitcoinConverter.Currency.FERMAT, BitcoinConverter.Currency.SATOSHI);
                    break;
            }

            return (long) amount;

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                    "Parsing String object to long", new StringBuilder().append("Cannot parse ").append(stringValue).append(" string value to long").toString());
        }
    }

    /**
     * This method returns a CustomerBrokerPurchaseNegotiation by negotiationId.
     *
     * @param negotiationId the negotiation ID
     * @return the purchase negotiation object
     */
    private CustomerBrokerPurchaseNegotiation getCustomerBrokerPurchaseNegotiation(String negotiationId)
            throws CantGetListPurchaseNegotiationsException {

        UUID negotiationUUID = UUID.fromString(negotiationId);
        return purchaseNegotiationManager.getNegotiationsByNegotiationId(negotiationUUID);
    }

    /**
     * This method returns the crypto address from a CustomerBrokerPurchaseNegotiation
     *
     * @param purchaseNegotiation the purchase negotiation
     * @return the crypto address
     * @throws CantGetCryptoAddressException
     */
    private String getBrokerCryptoAddressString(CustomerBrokerPurchaseNegotiation purchaseNegotiation) throws CantGetCryptoAddressException {
        try {
            Collection<Clause> negotiationClauses = purchaseNegotiation.getClauses();
            for (Clause clause : negotiationClauses) {
                if (clause.getType().equals(ClauseType.BROKER_CRYPTO_ADDRESS))
                    return clause.getValue();
            }

            throw new CantGetCryptoAddressException("The Negotiation clauses doesn't include the broker crypto address");

        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAddressException(e, "Getting the broker crypto address", "Cannot get the clauses list");
        }
    }

    /**
     * This method returns a crypto amount (long) from a CustomerBrokerPurchaseNegotiation
     *
     * @return the crypto amount
     */
    private long getCryptoAmount(CustomerBrokerPurchaseNegotiation purchaseNegotiation, CryptoCurrency currency) throws CantGetCryptoAmountException {
        try {
            Collection<Clause> negotiationClauses = purchaseNegotiation.getClauses();
            String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(negotiationClauses, ClauseType.BROKER_CURRENCY_QUANTITY);

            if (clauseValue == null)
                throw new CantGetCryptoAmountException("The Negotiation clauses doesn't include the broker crypto amount");

            return parseToCryptoAmountFormat(clauseValue, currency);

        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAmountException(e, "Getting the broker crypto amount", "Cannot get the clauses list");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoAmountException(e, "Getting the broker crypto amount", "There is an error parsing a String to long.");
        }
    }
}
