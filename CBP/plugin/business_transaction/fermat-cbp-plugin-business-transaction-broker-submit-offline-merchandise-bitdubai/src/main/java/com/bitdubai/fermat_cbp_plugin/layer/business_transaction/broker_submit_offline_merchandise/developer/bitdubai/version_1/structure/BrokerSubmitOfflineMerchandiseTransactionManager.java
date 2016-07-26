package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
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
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.BrokerSubmitOfflineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOfflineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.exceptions.CantGetBrokerMerchandiseException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/12/15.
 * Updated by Nelson Ramirez on 08/04/16
 */
public class BrokerSubmitOfflineMerchandiseTransactionManager implements BrokerSubmitOfflineMerchandiseManager {

    /**
     * Represents the database Dao
     */
    private BrokerSubmitOfflineMerchandiseBusinessTransactionDao dao;

    /**
     * Represents the CustomerBrokerContractSaleManager
     */
    private CustomerBrokerContractSaleManager saleContractManager;

    /**
     * Represents the CustomerBrokerSaleNegotiationManager
     */
    CustomerBrokerSaleNegotiationManager saleNegotiationManager;

    /**
     * Represents the CryptoBrokerWalletModuleManager
     */
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    /**
     * Represents the error manager
     */
    BrokerSubmitOfflineMerchandisePluginRoot pluginRoot;

    public BrokerSubmitOfflineMerchandiseTransactionManager(BrokerSubmitOfflineMerchandiseBusinessTransactionDao dao,
                                                            CustomerBrokerContractSaleManager saleContractManager,
                                                            CustomerBrokerSaleNegotiationManager saleNegotiationManager,
                                                            CryptoBrokerWalletManager cryptoBrokerWalletManager,
                                                            BrokerSubmitOfflineMerchandisePluginRoot pluginRoot) {

        this.dao = dao;
        this.saleContractManager = saleContractManager;
        this.saleNegotiationManager = saleNegotiationManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.pluginRoot = pluginRoot;

    }

    /**
     * This method send a payment according the contract clauses.
     * In this case, this method submit merchandise and not requires the cbpWalletPublicKey,
     * this public key can be obtained from the crypto broker wallet
     *
     * @param referencePrice     reference price
     * @param cbpWalletPublicKey broker wallet public key
     * @param contractHash       contract Hash also known as contract ID
     * @throws CantSubmitMerchandiseException
     */
    @Override
    public void submitMerchandise(BigDecimal referencePrice, String cbpWalletPublicKey, String contractHash) throws CantSubmitMerchandiseException {
        try {
            //Checking the arguments
            Object[] arguments = {referencePrice, cbpWalletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);

            final CustomerBrokerContractSale saleContract = saleContractManager.getCustomerBrokerContractSaleForContractId(contractHash);
            final CustomerBrokerSaleNegotiation saleNegotiation = getCustomerBrokerSaleNegotiation(saleContract.getNegotiatiotId());

            final Collection<Clause> clauses = saleNegotiation.getClauses();
            final String moneyTypeCode = NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.BROKER_PAYMENT_METHOD);
            final MoneyType moneyType = MoneyType.getByCode(moneyTypeCode);
            final String merchandiseCurrencyCode = NegotiationClauseHelper.getNegotiationClauseValue(clauses, ClauseType.CUSTOMER_CURRENCY);
            final FiatCurrency merchandiseCurrency = FiatCurrency.getByCode(merchandiseCurrencyCode);


            Platforms merchandiseWalletPlatform;
            switch (moneyType) {
                case CRYPTO:
                    throw new CantSubmitMerchandiseException("The Payment type is CRYPTO. Need to be BANK, CASH_ON_HAND or CASH_DELIVERY");
                case BANK:
                    merchandiseWalletPlatform = Platforms.BANKING_PLATFORM;
                    break;
                default:
                    merchandiseWalletPlatform = Platforms.CASH_PLATFORM;
                    break;
            }

            final CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cbpWalletPublicKey);
            final CryptoBrokerWalletSetting cryptoBrokerWalletSetting = cryptoBrokerWallet.getCryptoWalletSetting();
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings();

            if (associatedWallets.isEmpty()) {
                //Cannot handle this situation, throw an exception
                throw new CantSubmitMerchandiseException("There is not associated wallets to the Crypto Broker Wallet");
            }

            String offlineWalletPublicKey = null;
            for (CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting : associatedWallets) {
                Platforms associatedWalletPlatform = cryptoBrokerWalletAssociatedSetting.getPlatform();
                Currency associatedWalletMerchandise = cryptoBrokerWalletAssociatedSetting.getMerchandise();

                if (associatedWalletPlatform == merchandiseWalletPlatform && associatedWalletMerchandise == merchandiseCurrency) {
                    offlineWalletPublicKey = cryptoBrokerWalletAssociatedSetting.getWalletPublicKey();

                    System.out.println(new StringBuilder().append("SUBMIT_OFFLINE_MERCHANDISE_MANAGER - merchandise wallet public key found: ").append(offlineWalletPublicKey).toString());
                    break;
                }
            }

            if (offlineWalletPublicKey == null) {
                // In this case there is no associated wallet, I can't handle this situation.
                throw new CantSubmitMerchandiseException(new StringBuilder().append("None of the associated wallets is from the platform ").append(merchandiseWalletPlatform).toString());
            }

            //Overload the original method
            submitMerchandise(referencePrice, cbpWalletPublicKey, offlineWalletPublicKey, contractHash, moneyType, merchandiseCurrency);

        } catch (CryptoBrokerWalletNotFoundException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the crypto broker wallet");

        } catch (CantGetCryptoBrokerWalletSettingException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the Crypto Wallet Setting");

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Creating Broker Submit Online Merchandise Business Transaction",
                    "Invalid input to this manager");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Unexpected Result", "Check the cause");
        }
    }

    @Override
    public void submitMerchandise(BigDecimal referencePrice, String cbpWalletPublicKey, String offlineWalletPublicKey, String contractHash, MoneyType merchandiseMoneyType, FiatCurrency merchandiseCurrency) throws CantSubmitMerchandiseException {
        try {
            //Checking the arguments
            final Object[] arguments = {referencePrice, cbpWalletPublicKey, offlineWalletPublicKey, contractHash, merchandiseCurrency, merchandiseMoneyType};
            ObjectChecker.checkArguments(arguments);

            final CustomerBrokerContractSale saleContract = saleContractManager.getCustomerBrokerContractSaleForContractId(contractHash);

            final String negotiationId = saleContract.getNegotiatiotId();
            final CustomerBrokerSaleNegotiation saleNegotiation = getCustomerBrokerSaleNegotiation(negotiationId);

            final double amount = getAmount(saleNegotiation);

            dao.persistContractInDatabase(saleContract, offlineWalletPublicKey, amount, cbpWalletPublicKey, referencePrice,
                    merchandiseCurrency, merchandiseMoneyType);

            System.out.println("SUBMIT_OFFLINE_MERCHANDISE_MANAGER - persistContractInDatabase(...) called");

        } catch (CantGetListCustomerBrokerContractSaleException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerContractSale");
        } catch (CantGetListSaleNegotiationsException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerSaleNegotiation list");
        } catch (CantInsertRecordException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Cannot insert the record in database");
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Creating Broker Submit Offline Merchandise Business Transaction",
                    "Invalid input to this manager");
        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Unexpected Result", "Check the cause");
        }
    }

    /**
     * This method returns a {@link CustomerBrokerSaleNegotiation} by the negotiation ID.
     *
     * @param negotiationId the negotiation ID
     * @return the {@link CustomerBrokerSaleNegotiation} object
     */
    private CustomerBrokerSaleNegotiation getCustomerBrokerSaleNegotiation(String negotiationId) throws CantGetListSaleNegotiationsException {
        UUID negotiationUUID = UUID.fromString(negotiationId);
        return saleNegotiationManager.getNegotiationsByNegotiationId(negotiationUUID);
    }

    /**
     * This method gets the Merchandise type from Negotiation Clauses.
     *
     * @param saleNegotiation the negotiation object with all the info of the negotiation
     * @return the merchandise money type
     * @throws CantGetBrokerMerchandiseException
     */
    private MoneyType getMerchandiseMoneyType(CustomerBrokerSaleNegotiation saleNegotiation) throws CantGetBrokerMerchandiseException {
        try {
            final Collection<Clause> negotiationClauses = saleNegotiation.getClauses();

            for (Clause clause : negotiationClauses) {
                if (clause.getType() == ClauseType.BROKER_PAYMENT_METHOD) {
                    final String clauseValue = clause.getValue();

                    if (MoneyType.CRYPTO.getCode().equals(clauseValue))
                        throw new CantGetBrokerMerchandiseException("The Broker Merchandise is Crypto.");

                    return MoneyType.getByCode(clauseValue);
                }
            }

            throw new CantGetBrokerMerchandiseException("The Negotiation clauses doesn't include the broker payment method");

        } catch (InvalidParameterException e) {
            throw new CantGetBrokerMerchandiseException(e, "Getting the merchandise type", "Invalid parameter Clause value");
        } catch (CantGetListClauseException e) {
            throw new CantGetBrokerMerchandiseException(e, "Getting the merchandise type", "Cannot get the clauses list");
        }
    }

    /**
     * This method gets the currency type from Negotiation Clauses.
     *
     * @param saleNegotiation the negotiation object with all the info of the negotiation
     * @return the merchandise fiat currency
     * @throws CantGetBrokerMerchandiseException
     */
    private FiatCurrency getFiatCurrency(
            CustomerBrokerSaleNegotiation saleNegotiation) throws CantGetBrokerMerchandiseException {
        try {
            Collection<Clause> negotiationClauses = saleNegotiation.getClauses();
            String clauseValue;
            for (Clause clause : negotiationClauses) {
                if (clause.getType().getCode().equals(ClauseType.CUSTOMER_CURRENCY.getCode())) {
                    clauseValue = clause.getValue();
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

    private double getAmount(CustomerBrokerSaleNegotiation negotiation) throws UnexpectedResultReturnedFromDatabaseException {

        try {
            Collection<Clause> clauses = negotiation.getClauses();
            ClauseType clauseType;
            double brokerAmountDouble = 0;

            for (Clause clause : clauses) {
                clauseType = clause.getType();

                if (clauseType == ClauseType.CUSTOMER_CURRENCY_QUANTITY)
                    brokerAmountDouble = parseToDouble(clause.getValue());
            }

            return brokerAmountDouble;

        } catch (CantGetListClauseException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "Cant Get Clause");
        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "An invalid parameter is detected");
        } catch (Exception e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting the amount merchandise", "N/A");
        }

    }

    /**
     * This method returns the actual ContractTransactionStatus by a contract hash/id
     *
     * @param contractHash the contract hash/id
     * @return the {@link ContractTransactionStatus} object
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return dao.getContractTransactionStatus(contractHash);

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new UnexpectedResultReturnedFromDatabaseException("Cannot check a null contractHash/Id");

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new UnexpectedResultReturnedFromDatabaseException(exception, "Unexpected Result", "Check the cause");
        }
    }

    /**
     * This method returns the transaction completion date. If returns 0 the transaction is processing.
     *
     * @param contractHash the contract hash/id
     * @return the time in millis
     * @throws CantGetCompletionDateException
     */
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
     * This method parse a String object to a double value
     *
     * @param stringValue the string value
     * @return the double value
     * @throws InvalidParameterException
     */
    public double parseToDouble(String stringValue) throws InvalidParameterException {
        if (stringValue == null) {
            throw new InvalidParameterException("Cannot parse a null string value to long");
        } else {
            try {
                return NumberFormat.getInstance().parse(stringValue).doubleValue();
            } catch (Exception exception) {
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                        "Parsing String object to long", new StringBuilder().append("Cannot parse ").append(stringValue).append(" string value to long").toString());
            }
        }
    }
}
