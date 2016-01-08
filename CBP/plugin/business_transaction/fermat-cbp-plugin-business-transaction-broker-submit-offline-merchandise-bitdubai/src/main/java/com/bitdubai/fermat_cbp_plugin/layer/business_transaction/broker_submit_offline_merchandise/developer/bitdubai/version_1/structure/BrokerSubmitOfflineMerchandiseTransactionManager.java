package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOfflineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.exceptions.CantGetAmountException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.exceptions.CantGetBrokerMerchandiseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/12/15.
 */
public class BrokerSubmitOfflineMerchandiseTransactionManager implements BrokerSubmitOfflineMerchandiseManager {

    /**
     * Represents the database Dao
     */
    private BrokerSubmitOfflineMerchandiseBusinessTransactionDao brokerSubmitOfflineMerchandiseBusinessTransactionDao;

    /**
     * Represents the CustomerBrokerContractSaleManager
     */
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    /**
     * Represents the CustomerBrokerSaleNegotiationManager
     */
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Represents the error manager
     */
    ErrorManager errorManager;

    public BrokerSubmitOfflineMerchandiseTransactionManager(
            BrokerSubmitOfflineMerchandiseBusinessTransactionDao brokerSubmitOfflineMerchandiseBusinessTransactionDao,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            ErrorManager errorManager){
        this.brokerSubmitOfflineMerchandiseBusinessTransactionDao=brokerSubmitOfflineMerchandiseBusinessTransactionDao;
        this.customerBrokerContractSaleManager=customerBrokerContractSaleManager;
        this.customerBrokerSaleNegotiationManager=customerBrokerSaleNegotiationManager;
        this.errorManager=errorManager;

    }

    @Override
    public void submitMerchandise(
            BigDecimal referencePrice,
            String cbpWalletPublicKey,
            String offlineWalletPublicKey,
            String contractHash) throws CantSubmitMerchandiseException {
        try {
            //Checking the arguments
            Object[] arguments={referencePrice, cbpWalletPublicKey, offlineWalletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);
            CustomerBrokerContractSale customerBrokerContractSale=
                    this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(
                            contractHash);
            String negotiationId=customerBrokerContractSale.getNegotiatiotId();
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation=
                    getCustomerBrokerSaleNegotiation(
                            negotiationId);
            long amount = getCryptoAmount(customerBrokerSaleNegotiation);
            CurrencyType merchandiseType = getMerchandiseType(customerBrokerSaleNegotiation);
            FiatCurrency fiatCurrencyType = getFiatCurrency(customerBrokerSaleNegotiation);
            this.brokerSubmitOfflineMerchandiseBusinessTransactionDao.persistContractInDatabase(
                    customerBrokerContractSale,
                    offlineWalletPublicKey,
                    amount,
                    cbpWalletPublicKey,
                    referencePrice,
                    fiatCurrencyType,
                    merchandiseType);
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerContractSale");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerSaleNegotiation list");
        } catch (CantGetAmountException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot get the Crypto amount");
        } catch (CantGetBrokerMerchandiseException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot get the Customer Crypto Address");
        } catch (CantInsertRecordException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot insert the record in database");
        } catch (ObjectNotSetException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Invalid input to this manager");
        }
    }

    /**
     * This method returns a CustomerBrokerPurchaseNegotiation by negotiationId.
     * @param negotiationId
     * @return
     */
    private CustomerBrokerSaleNegotiation getCustomerBrokerSaleNegotiation(
            String negotiationId) throws
            CantGetListSaleNegotiationsException {
        UUID negotiationUUID=UUID.fromString(negotiationId);
        CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation=
                customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(
                        negotiationUUID);
        return customerBrokerSaleNegotiation;
    }

    /**
     * This method gets the Merchandise type from Negotiation Clauses.
     * @param customerBrokerSaleNegotiation
     * @return
     * @throws CantGetBrokerMerchandiseException
     */
    private CurrencyType getMerchandiseType(
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantGetBrokerMerchandiseException {
        try{
            Collection<Clause> negotiationClauses=customerBrokerSaleNegotiation.getClauses();
            String clauseValue;
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.BROKER_PAYMENT_METHOD)){
                    clauseValue=clause.getValue();
                    if(clauseValue.equals(CurrencyType.CRYPTO_MONEY)){
                        throw new CantGetBrokerMerchandiseException(
                                "The Broker merchandise is crypto.");
                    }

                        return CurrencyType.getByCode(clauseValue);
                    }
                }
            throw new CantGetBrokerMerchandiseException(
                    "The Negotiation clauses doesn't include the broker payment method");
            } catch (InvalidParameterException e) {
                throw new CantGetBrokerMerchandiseException(
                    e,
                    "Getting the merchandise type",
                    "Invalid parameter Clause value");
            } catch (CantGetListClauseException e) {
            throw new CantGetBrokerMerchandiseException(
                    e,
                    "Getting the merchandise type",
                    "Cannot get the clauses list");
            }
    }

    /**
     * This method gets the currency type from Negotiation Clauses.
     * @param customerBrokerSaleNegotiation
     * @return
     * @throws CantGetBrokerMerchandiseException
     */
    private FiatCurrency getFiatCurrency(
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantGetBrokerMerchandiseException {
        try{
            Collection<Clause> negotiationClauses=customerBrokerSaleNegotiation.getClauses();
            String clauseValue;
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.BROKER_CURRENCY)){
                    clauseValue=clause.getValue();
                    return FiatCurrency.getByCode(clauseValue);
                }
            }
            throw new CantGetBrokerMerchandiseException(
                    "The Negotiation clauses doesn't include the broker payment method");
        } catch (InvalidParameterException e) {
            throw new CantGetBrokerMerchandiseException(
                    e,
                    "Getting the merchandise type",
                    "Invalid parameter Clause value");
        } catch (CantGetListClauseException e) {
            throw new CantGetBrokerMerchandiseException(
                    e,
                    "Getting the merchandise type",
                    "Cannot get the clauses list");
        }
    }

    /**
     * This method returns a crypto amount (long) from a CustomerBrokerPurchaseNegotiation
     * @return
     */
    private long getCryptoAmount(
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws
            CantGetAmountException {
        try{
            long cryptoAmount;
            Collection<Clause> negotiationClauses=customerBrokerSaleNegotiation.getClauses();
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.BROKER_CURRENCY_QUANTITY)){
                    cryptoAmount=parseToLong(clause.getValue());
                    return cryptoAmount;
                }
            }
            throw new CantGetAmountException(
                    "The Negotiation clauses doesn't include the broker crypto amount");
        } catch (CantGetListClauseException e) {
            throw new CantGetAmountException(
                    e,
                    "Getting the broker amount",
                    "Cannot get the clauses list");
        } catch (InvalidParameterException e) {
            throw new CantGetAmountException(
                    e,
                    "Getting the broker amount",
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

    /**
     * This method returns the actual ContractTransactionStatus by a contract hash/id
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
            return this.brokerSubmitOfflineMerchandiseBusinessTransactionDao.getContractTransactionStatus(
                    contractHash);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        }
    }
}
