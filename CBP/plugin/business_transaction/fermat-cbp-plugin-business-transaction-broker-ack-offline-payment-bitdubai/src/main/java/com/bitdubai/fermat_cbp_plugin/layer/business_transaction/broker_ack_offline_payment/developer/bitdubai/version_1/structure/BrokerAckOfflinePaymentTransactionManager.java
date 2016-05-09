package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantAckPaymentException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Collection;
import java.util.UUID;


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

    /**
     * Represents the customerBrokerSaleNegotiationManager
     */
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    //TODO: I need to define if this manager needs others arguments in constructor
    public BrokerAckOfflinePaymentTransactionManager(
            BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            ErrorManager errorManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager){
        this.brokerAckOfflinePaymentBusinessTransactionDao = brokerAckOfflinePaymentBusinessTransactionDao;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        this.errorManager=errorManager;
        this.customerBrokerSaleNegotiationManager=customerBrokerSaleNegotiationManager;
    }

    @Override
    public void ackPayment(String walletPublicKey, String contractHash, String actorPublicKey, String customerAlias) throws CantAckPaymentException {
        CustomerBrokerContractSale customerBrokerContractSale;

        try{
            //Checking the arguments
            Object[] arguments={walletPublicKey, contractHash, actorPublicKey, customerAlias};
            ObjectChecker.checkArguments(arguments);

            //First we check if the contract exits in this plugin database
            boolean contractExists = brokerAckOfflinePaymentBusinessTransactionDao.isContractHashInDatabase(contractHash);
            if(!contractExists){
                /**
                 * If the contract is not in database, we are going to check if exists in contract Layer,
                 * in theory this won't happen, when a contract is open is created in contract layer
                 * and is raised an event that build a record in this plugin database. In this case we
                 * will suppose that the agent in this plugin has not created the contract, but exists in
                 * contract layer.
                 */
                customerBrokerContractSale = customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                if(customerBrokerContractSale == null)
                    throw new CantAckPaymentException("The CustomerBrokerContractSale with the hash: \n" + contractHash+ "\nis null");

                final MoneyType paymentType = getMoneyTypeFromContract(customerBrokerContractSale);
                final FiatCurrency fiatCurrency = getCurrencyToDeliverFromContract(customerBrokerContractSale);

                brokerAckOfflinePaymentBusinessTransactionDao.persistContractInDatabase(customerBrokerContractSale, paymentType, fiatCurrency,
                        actorPublicKey, customerAlias);
            } else{
                /**
                 * The contract exists in database, we are going to check the contract status.
                 * We are going to get the record from this contract and
                 * update the status to indicate the agent to send a ack notification to a Crypto Customer.
                 */
                ContractTransactionStatus contractTransactionStatus=getContractTransactionStatus(contractHash);

                //If the status is different to PENDING_OFFLINE_PAYMENT_CONFIRMATION the ack process was started.
                if(contractTransactionStatus == ContractTransactionStatus.PENDING_ACK_OFFLINE_PAYMENT){

                    customerBrokerContractSale = this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);
                    if(customerBrokerContractSale == null)
                        throw new CantAckPaymentException("The CustomerBrokerContractSale with the hash \n" + contractHash + "\nis null");

                    final MoneyType paymentType = getMoneyTypeFromContract(customerBrokerContractSale);
                    switch (paymentType){
                        case BANK:
                            contractTransactionStatus=ContractTransactionStatus.PENDING_CREDIT_BANK_WALLET;
                            break;
                        case CASH_DELIVERY:
                            contractTransactionStatus=ContractTransactionStatus.PENDING_CREDIT_CASH_WALLET;
                            break;
                        case CASH_ON_HAND:
                            contractTransactionStatus=ContractTransactionStatus.PENDING_CREDIT_CASH_WALLET;
                            break;
                        default:
                            throw new InvalidParameterException(paymentType + " value from MoneyType is not valid in this plugin");
                    }

                    final FiatCurrency currencyType = getCurrencyToDeliverFromContract(customerBrokerContractSale);

                    //Update the contract in database.
                    this.brokerAckOfflinePaymentBusinessTransactionDao.updateRecordCurrencyByContractHash(contractHash, currencyType);
                    this.brokerAckOfflinePaymentBusinessTransactionDao.updateRecordCBPWalletPublicKeyByContractHash(contractHash, walletPublicKey);
                    this.brokerAckOfflinePaymentBusinessTransactionDao.updateRecordPaymentTypeByContractHash(contractHash, paymentType);
                    this.brokerAckOfflinePaymentBusinessTransactionDao.updateCustomerAliasByContractHash(contractHash, customerAlias);
                    this.brokerAckOfflinePaymentBusinessTransactionDao.updateContractTransactionStatus(contractHash, contractTransactionStatus);

                } else{
                    final CantAckPaymentException exception = new CantAckPaymentException("The Ack offline payment with the contract ID \n" + contractHash + "\n process has begun");
                    errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                }
            }

        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Unexpected result from database");
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Cannot get the contract from customerBrokerContractSaleManager");
        } catch (CantInsertRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Cannot insert the contract record in database");
        } catch (CantUpdateRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Cannot update the contract status in database");
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Invalid input to this manager");
        } catch (CantGetListSaleNegotiationsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Cannot get the payment type");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Invalid Parameter");
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BROKER_ACK_OFFLINE_PAYMENT, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            throw new CantAckPaymentException(e, "Creating Broker Ack Offline Payment Business Transaction", "Unexpected Error");
        }
    }

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
            return this.brokerAckOfflinePaymentBusinessTransactionDao.getCompletionDateByContractHash(
                    contractHash);
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "Unexpected exception from database");
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCompletionDateException(
                    e,
                    "Getting completion date",
                    "The contract hash argument is null");
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
        try{
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.brokerAckOfflinePaymentBusinessTransactionDao.getContractTransactionStatus(
                    contractHash);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        }catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception,
                    "Unexpected Result",
                    "Check the cause");
        }
    }
    /**
     * This method returns the currency type from a contract
     *
     * @param customerBrokerContractSale
     * @return
     * @throws CantGetListSaleNegotiationsException
     */
    public MoneyType getMoneyTypeFromContract(
            CustomerBrokerContractSale customerBrokerContractSale) throws
            CantGetListSaleNegotiationsException {
        try {
            String negotiationId = customerBrokerContractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation =
                    customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(
                            UUID.fromString(negotiationId));
            ObjectChecker.checkArgument(customerBrokerSaleNegotiation,
                    "The customerBrokerSaleNegotiation is null");
            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            ClauseType clauseType;
            for (Clause clause : clauses) {
                clauseType = clause.getType();
                if (clauseType.getCode().equals(
                        ClauseType.CUSTOMER_PAYMENT_METHOD.getCode())) {
                    return MoneyType.getByCode(clause.getValue());
                }
            }
            throw new CantGetListSaleNegotiationsException(
                    "Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "Cannot get the negotiation list",
                    e);
        } catch (CantGetListClauseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "Cannot find clauses list");
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "The customerBrokerSaleNegotiation is null",
                    e);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "Unexpected Error",
                    e);
        }

    }
    /**
     * This method returns the currency type from a contract
     *
     * @param customerBrokerContractSale
     * @return
     * @throws CantGetListSaleNegotiationsException
     */
    public FiatCurrency getCurrencyToDeliverFromContract(CustomerBrokerContractSale customerBrokerContractSale) throws CantGetListSaleNegotiationsException {
        try {
            final String negotiationId = customerBrokerContractSale.getNegotiatiotId();

            final CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation = customerBrokerSaleNegotiationManager.
                    getNegotiationsByNegotiationId(UUID.fromString(negotiationId));

            ObjectChecker.checkArgument(customerBrokerSaleNegotiation, "The customerBrokerSaleNegotiation is null");

            Collection<Clause> clauses = customerBrokerSaleNegotiation.getClauses();
            for (Clause clause : clauses) {
                ClauseType clauseType = clause.getType();
                if (clauseType == ClauseType.BROKER_CURRENCY) {
                    return FiatCurrency.getByCode(clause.getValue());
                }
            }

            throw new CantGetListSaleNegotiationsException(
                    "Cannot find the proper clause");
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "Cannot get the negotiation list",
                    e);
        } catch (CantGetListClauseException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "Cannot find clauses list");
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "The customerBrokerSaleNegotiation is null",
                    e);
        }catch (Exception e){
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_ACK_OFFLINE_PAYMENT,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetListSaleNegotiationsException(
                    "Unexpected Error",
                    e);
        }

    }
}
