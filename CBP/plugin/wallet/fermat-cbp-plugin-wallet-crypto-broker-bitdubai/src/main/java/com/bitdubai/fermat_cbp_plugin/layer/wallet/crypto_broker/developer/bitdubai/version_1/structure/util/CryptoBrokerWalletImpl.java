package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerMarketRateException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerQuoteException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerStockTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetTransactionCryptoBrokerWalletMatchingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantMarkAsSeenException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantCreateNewCryptoBrokerWalletException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 * Modified by Yordin Alayn 27.10.15
 * Modified by Franklin Marcano 30.11.2015
 */
public class CryptoBrokerWalletImpl implements CryptoBrokerWallet {


    public static final String PATH_DIRECTORY = "cryptobrokerwallet-swap/";
    private static final String CRYPTO_BROKER_WALLET_FILE_NAME = "walletsIds";
    private Database database;
    private Map<String, UUID> wallet = new HashMap<>();
    private UUID pluginId;
    private CryptoBrokerWalletPluginRoot pluginRoot;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private PluginFileSystem pluginFileSystem;
    private CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDao;
    private CurrencyExchangeProviderFilterManager providerFilter;
    private Broadcaster broadcaster;

    /**
     * Constructor
     */
    public CryptoBrokerWalletImpl(CryptoBrokerWalletPluginRoot pluginRoot, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, CurrencyExchangeProviderFilterManager providerFilter, Broadcaster broadcaster) {
        this.pluginRoot = pluginRoot;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.providerFilter = providerFilter;
        this.broadcaster = broadcaster;
    }

    /**
     * This method load the instance the StockBalance
     *
     * @return StockBalance
     * @throws CantGetStockCryptoBrokerWalletException
     */
    @Override
    public StockBalance getStockBalance() throws CantGetStockCryptoBrokerWalletException {
        return new StockBalanceImpl(database, pluginId, pluginFileSystem, pluginRoot, broadcaster);
    }

    /**
     * This method load the instance the CryptoBrokerWalletSetting
     *
     * @return StockBalance
     * @throws CantGetCryptoBrokerWalletSettingException
     */
    @Override
    public CryptoBrokerWalletSetting getCryptoWalletSetting() throws CantGetCryptoBrokerWalletSettingException {
        return new CryptoBrokerWalletSettingImpl(database, pluginId, pluginFileSystem, pluginRoot);
    }

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param moneyType
     * @param transactionType
     * @param balanceType
     * @return List<CryptoBrokerStockTransaction>
     * @throws CantGetCryptoBrokerStockTransactionException
     */
    @Override
    public List<CryptoBrokerStockTransaction> getCryptoBrokerStockTransactionsByMerchandise(Currency merchandise, MoneyType moneyType, TransactionType transactionType, BalanceType balanceType) throws CantGetCryptoBrokerStockTransactionException {

        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, pluginRoot);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.pluginId);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerStockTransactionsByMerchandise(merchandise, moneyType, transactionType, balanceType);
    }

    @Override
    public List<CryptoBrokerStockTransaction> getStockHistory(Currency merchandise, MoneyType moneyType, int offset, long timeStamp) throws CantGetCryptoBrokerStockTransactionException {
        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, pluginRoot);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.pluginId);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
        return cryptoBrokerWalletDatabaseDao.getStockHistory(merchandise, moneyType, offset, timeStamp);
    }

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param fiatCurrency
     * @param moneyType
     * @return FiatIndex
     * @throws CantGetCryptoBrokerMarketRateException
     */
    @Override
    public FiatIndex getMarketRate(Currency merchandise, FiatCurrency fiatCurrency, MoneyType moneyType) throws CantGetCryptoBrokerMarketRateException {
        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, pluginRoot);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.pluginId);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
        return cryptoBrokerWalletDatabaseDao.getMarketRate(merchandise, fiatCurrency, moneyType);
    }

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param merchandise
     * @param quantity
     * @param payment
     * @return Quote
     * @throws CantGetCryptoBrokerQuoteException
     */
    @Override
    public Quote getQuote(Currency merchandise, float quantity, Currency payment) throws CantGetCryptoBrokerQuoteException {
        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, pluginRoot);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.pluginId);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
        cryptoBrokerWalletDatabaseDao.setProviderFilter(this.providerFilter);
        return cryptoBrokerWalletDatabaseDao.getQuote(merchandise, payment);
    }

    /**
     * This method load the list CryptoBrokerStockTransaction
     *
     * @param transactionIds
     * @return void
     * @throws CantGetTransactionCryptoBrokerWalletMatchingException
     */
    @Override
    public void markAsSeen(List<String> transactionIds) throws CantMarkAsSeenException {
        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, pluginRoot);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.pluginId);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
        cryptoBrokerWalletDatabaseDao.markAsSeen(transactionIds);
    }

    /**
     * This method load the list CurrencyMatching
     *
     * @return CurrencyMatching
     * @throws CantGetTransactionCryptoBrokerWalletMatchingException
     */
    @Override
    public List<CurrencyMatching> getCryptoBrokerTransactionCurrencyInputs() throws CantGetTransactionCryptoBrokerWalletMatchingException {
        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, pluginRoot);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.pluginId);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerTransactionCurrencyMatchings();
    }

    /**
     * This method initializes the crypto broker wallet
     *
     * @param walletId
     * @throws CryptoBrokerWalletNotFoundException
     */
    public void initialize(UUID walletId) throws CryptoBrokerWalletNotFoundException {
        if (walletId == null)
            throw new CryptoBrokerWalletNotFoundException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);

            throw new CryptoBrokerWalletNotFoundException("I can't open database", cantOpenDatabaseException, new StringBuilder().append("WalletId: ").append(walletId.toString()).toString(), "");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseNotFoundException);
            throw new CryptoBrokerWalletNotFoundException("Database does not exists", databaseNotFoundException, new StringBuilder().append("WalletId: ").append(walletId.toString()).toString(), "");
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CryptoBrokerWalletNotFoundException(CryptoBrokerWalletNotFoundException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * This method creates the crypto broker wallet
     *
     * @param walletId
     * @return an UUID of the created wallet
     * @throws CantCreateCryptoBrokerWalletException
     */
    public UUID create(String walletId) throws CantCreateCryptoBrokerWalletException {
        try {
            // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
            //       We need to change this in the near future
            UUID internalWalletId = UUID.randomUUID();
            createWalletDatabase(internalWalletId);
            PluginTextFile walletFile = createWalletFile();
            loadWalletMap(walletFile);
            wallet.put(walletId, internalWalletId);
            persistWallet(walletFile);
            return internalWalletId;
        } catch (CantCreateCryptoBrokerWalletException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantCreateCryptoBrokerWalletException(CantCreateCryptoBrokerWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private PluginTextFile createWalletFile() throws CantCreateCryptoBrokerWalletException {
        try {
            return pluginFileSystem.getTextFile(pluginId, "", CRYPTO_BROKER_WALLET_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantCreateCryptoBrokerWalletException("File could not be created (?)", cantCreateFileException, new StringBuilder().append("File Name: ").append(CRYPTO_BROKER_WALLET_FILE_NAME).toString(), "");
        } catch (FileNotFoundException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateCryptoBrokerWalletException("File could not be found", e, new StringBuilder().append("File Name: ").append(CRYPTO_BROKER_WALLET_FILE_NAME).toString(), "");
        }
    }

    private void loadWalletMap(final PluginTextFile loadWalletMap) throws CantCreateNewCryptoBrokerWalletException {
        try {
            loadWalletMap.loadFromMedia();
            String[] stringWallet = loadWalletMap.getContent().split(";", -1);

            for (String stringWalletId : stringWallet) {

                if (!stringWalletId.equals("")) {
                    String[] idPair = stringWalletId.split(",", -1);
                    wallet.put(idPair[0], UUID.fromString(idPair[1]));
                }
            }
        } catch (CantLoadFileException exception) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantCreateNewCryptoBrokerWalletException("Can't load file content from media", exception, "", "");
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateNewCryptoBrokerWalletException {
        try {
            CryptoBrokerWalletDatabaseFactory databaseFactory = new CryptoBrokerWalletDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
            throw new CantCreateNewCryptoBrokerWalletException("Database could not be created", cantCreateDatabaseException, new StringBuilder().append("internalWalletId: ").append(internalWalletId.toString()).toString(), "");
        }
    }

    private void persistWallet(final PluginTextFile pluginTextFile) throws CantCreateNewCryptoBrokerWalletException {
        StringBuilder stringBuilder = new StringBuilder(wallet.size() * 72);
        Iterator iterator = wallet.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();

            stringBuilder
                    .append(pair.getKey().toString())
                    .append(",")
                    .append(pair.getValue().toString())
                    .append(";");

            iterator.remove();
        }

        pluginTextFile.setContent(stringBuilder.toString());

        try {
            pluginTextFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantPersistFileException);
            throw new CantCreateNewCryptoBrokerWalletException("Could not persist in file", cantPersistFileException, new StringBuilder().append("stringBuilder: ").append(stringBuilder.toString()).toString(), "");
        }
    }

}
