package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetCompletionDateException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
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
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.BrokerSubmitOnlineMerchandisePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantGetCryptoAddressException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantGetCryptoAmountException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/12/15.
 */
public class BrokerSubmitOnlineMerchandiseTransactionManager implements BrokerSubmitOnlineMerchandiseManager {

    /**
     * Represents the database Dao
     */
    BrokerSubmitOnlineMerchandiseBusinessTransactionDao brokerSubmitOnlineMerchandiseBusinessTransactionDao;

    /**
     * Represents the CustomerBrokerContractSaleManager
     */
    private CustomerBrokerContractSaleManager contractSaleManager;

    /**
     * Represents the CustomerBrokerSaleNegotiationManager
     */
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Represents the CryptoBrokerWalletModuleManager
     */
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    BrokerSubmitOnlineMerchandisePluginRoot pluginRoot;

    //TODO: included crypto broker wallet manager.
    public BrokerSubmitOnlineMerchandiseTransactionManager(
            BrokerSubmitOnlineMerchandiseBusinessTransactionDao brokerSubmitOnlineMerchandiseBusinessTransactionDao,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            CryptoBrokerWalletManager cryptoBrokerWalletManager,
            BrokerSubmitOnlineMerchandisePluginRoot pluginRoot) {
        this.brokerSubmitOnlineMerchandiseBusinessTransactionDao = brokerSubmitOnlineMerchandiseBusinessTransactionDao;
        this.contractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.pluginRoot = pluginRoot;
    }

    /**
     * This method returns the crypto address from a CustomerBrokerPurchaseNegotiation
     *
     * @param customerBrokerSaleNegotiation
     * @return
     * @throws CantGetCryptoAddressException
     */
    private String getBrokerCryptoAddressString(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantGetCryptoAddressException {
        try {
            Collection<Clause> negotiationClauses = customerBrokerSaleNegotiation.getClauses();
            String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(negotiationClauses, ClauseType.CUSTOMER_CRYPTO_ADDRESS);

            if (clauseValue == null)
                throw new CantGetCryptoAddressException("The Negotiation clauses doesn't include the broker crypto address");

            return clauseValue;

        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAddressException(e, "Getting the broker crypto address", "Cannot get the clauses list");
        }
    }

    /**
     * This method returns a crypto amount (long) from a CustomerBrokerPurchaseNegotiation
     *
     * @return
     */
    private long getAmount(CustomerBrokerSaleNegotiation saleNegotiation, CryptoCurrency merchandiseCurrency) throws CantGetCryptoAmountException {
        try {
            Collection<Clause> negotiationClauses = saleNegotiation.getClauses();
            final String clauseValue = NegotiationClauseHelper.getNegotiationClauseValue(negotiationClauses, ClauseType.CUSTOMER_CURRENCY_QUANTITY);

            if (clauseValue != null) {
                final Number number = DecimalFormat.getInstance().parse(clauseValue);
                if (merchandiseCurrency == CryptoCurrency.BITCOIN) {
                    return (long) BitcoinConverter.convert(number.doubleValue(), BitcoinConverter.Currency.BITCOIN, BitcoinConverter.Currency.SATOSHI);

                } else if (merchandiseCurrency == CryptoCurrency.FERMAT) {
                    return (long) BitcoinConverter.convert(number.doubleValue(), BitcoinConverter.Currency.FERMAT, BitcoinConverter.Currency.SATOSHI);
                } else {
                    return number.longValue();
                }
            }

            throw new CantGetCryptoAmountException("The Negotiation clauses doesn't include the broker crypto amount");

        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAmountException(e, "Getting the broker crypto amount", "Cannot get the clauses list");
        } catch (ParseException e) {
            throw new CantGetCryptoAmountException(e, "Getting the broker crypto amount", "Cannot parse the clauseValue using DecimalFormat.parse()");
        }
    }

    /**
     * This method returns a CustomerBrokerPurchaseNegotiation by negotiationId.
     *
     * @param negotiationId
     * @return
     */
    private CustomerBrokerSaleNegotiation getCustomerBrokerSaleNegotiation(String negotiationId) throws CantGetListSaleNegotiationsException {
        UUID negotiationUUID = UUID.fromString(negotiationId);
        return customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(negotiationUUID);
    }


    @Override
    public void submitMerchandise(BigDecimal referencePrice,
                                  String cbpWalletPublicKey,
                                  String walletPublicKey,
                                  CryptoCurrency merchandiseCurrency,
                                  String contractHash,
                                  FeeOrigin feeOrigin,
                                  long fee) throws CantSubmitMerchandiseException {

        submitMerchandise(
                referencePrice,
                cbpWalletPublicKey,
                walletPublicKey,
                contractHash,
                merchandiseCurrency,
                BlockchainNetworkType.getDefaultBlockchainNetworkType(),
                feeOrigin,
                fee);
    }

    @Override
    public void submitMerchandise(BigDecimal referencePrice,
                                  String cbpWalletPublicKey,
                                  String walletPublicKey,
                                  String contractHash,
                                  CryptoCurrency merchandiseCurrency,
                                  BlockchainNetworkType blockchainNetworkType,
                                  FeeOrigin feeOrigin,
                                  long fee) throws CantSubmitMerchandiseException {
        try {
            //Checking the arguments
            Object[] arguments = {referencePrice, cbpWalletPublicKey, walletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);
            CustomerBrokerContractSale saleContract = this.contractSaleManager.getCustomerBrokerContractSaleForContractId(contractHash);

            if (saleContract == null) {
                throw new CantSubmitMerchandiseException(new StringBuilder().append("The CustomerBrokerContractSale with the contractHash\n").append(contractHash).append("\nis null").toString());
            }

            final CustomerBrokerSaleNegotiation saleNegotiation = getCustomerBrokerSaleNegotiation(saleContract.getNegotiatiotId());

            final String customerCryptoAddress = getBrokerCryptoAddressString(saleNegotiation);
            final String[] split = customerCryptoAddress.split(":");

            final String cryptoAddress = split[0];
            final String intraActorPk = split[1];

            long cryptoAmount = getAmount(saleNegotiation, merchandiseCurrency);

            this.brokerSubmitOnlineMerchandiseBusinessTransactionDao.persistContractInDatabase(
                    saleContract,
                    cryptoAddress,
                    walletPublicKey,
                    cryptoAmount,
                    cbpWalletPublicKey,
                    referencePrice,
                    merchandiseCurrency,
                    blockchainNetworkType,
                    intraActorPk,
                    feeOrigin,
                    fee);

        } catch (CantGetListCustomerBrokerContractSaleException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerContractSale");

        } catch (CantGetListSaleNegotiationsException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerSaleNegotiation list");

        } catch (CantGetCryptoAmountException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the Crypto amount");

        } catch (CantGetCryptoAddressException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the Customer Crypto Address");

        } catch (CantInsertRecordException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot insert a record in database");

        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Invalid input to this manager");

        } catch (Exception e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSubmitMerchandiseException(e, "Unexpected Result", "Check the cause");
        }
    }

    @Override
    public void submitMerchandise(BigDecimal referencePrice,
                                  String cbpWalletPublicKey,
                                  String contractHash,
                                  CryptoCurrency merchandiseCurrency,
                                  FeeOrigin feeOrigin,
                                  long fee) throws CantSubmitMerchandiseException {

        submitMerchandise(
                referencePrice,
                cbpWalletPublicKey,
                contractHash,
                merchandiseCurrency,
                BlockchainNetworkType.getDefaultBlockchainNetworkType(),
                feeOrigin,
                fee);
    }

    @Override
    public void submitMerchandise(BigDecimal referencePrice,
                                  String cbpWalletPublicKey,
                                  String contractHash,
                                  CryptoCurrency merchandiseCurrency,
                                  BlockchainNetworkType blockchainNetworkType,
                                  FeeOrigin feeOrigin,
                                  long fee) throws CantSubmitMerchandiseException {
        try {
            //Checking the arguments
            Object[] arguments = {referencePrice, cbpWalletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);

            CryptoBrokerWallet cryptoBrokerWallet = cryptoBrokerWalletManager.loadCryptoBrokerWallet(cbpWalletPublicKey);
            CryptoBrokerWalletSetting brokerWalletSettings = cryptoBrokerWallet.getCryptoWalletSetting();
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = brokerWalletSettings.getCryptoBrokerWalletAssociatedSettings();

            if (associatedWallets.isEmpty()) {
                //Cannot handle this situation, throw an exception
                throw new CantSubmitMerchandiseException("Cannot get the crypto Wallet Associates Setting because the list is null");
            }

            boolean isCryptoWalletSets = false;
            String cryptoWalletPublicKey = "WalletNotSet";
            for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {

                if (associatedWallet.getMoneyType() == MoneyType.CRYPTO && associatedWallet.getMerchandise() == merchandiseCurrency) {
                    cryptoWalletPublicKey = associatedWallet.getWalletPublicKey();
                    isCryptoWalletSets = true;
                    break;
                }
            }

            if (!isCryptoWalletSets) {
                // In this case the crypto wallet is not set in Crypto Broker wallet, I cannot handle this situation.
                throw new CantSubmitMerchandiseException("There is not crypto wallet associated in Crypto Broker Wallet Settings");
            }

            //Overload the original method
            submitMerchandise(
                    referencePrice,
                    cbpWalletPublicKey,
                    cryptoWalletPublicKey,
                    contractHash,
                    merchandiseCurrency,
                    blockchainNetworkType,
                    feeOrigin,
                    fee);

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

        } catch (Exception exception) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantSubmitMerchandiseException(exception, "Unexpected Result", "Check the cause");
        }
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash) throws UnexpectedResultReturnedFromDatabaseException {
        try {
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.brokerSubmitOnlineMerchandiseBusinessTransactionDao.getContractTransactionStatus(contractHash);

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
            return this.brokerSubmitOnlineMerchandiseBusinessTransactionDao.getCompletionDateByContractHash(contractHash);

        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "Unexpected exception from database");
        } catch (ObjectNotSetException e) {
            pluginRoot.reportError(DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCompletionDateException(e, "Getting completion date", "The contract hash argument is null");
        }
    }
}
