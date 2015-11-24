package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantCreateNewCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerWalletDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantExecuteCryptoBrokerTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetCryptoBrokerWalletPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantListCryptoBrokerWalletTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetCryptoBrokerWalletPublicKeyException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerStockTransactionRecordImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletImpl;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 17.10.15.
 */
public class CryptoBrokerWalletDatabaseDao {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    public CryptoBrokerWalletDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    Database database;

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCryptoBrokerWalletDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CryptoBrokerWalletDatabaseFactory databaseFactory = new CryptoBrokerWalletDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCryptoBrokerWalletDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCryptoBrokerWalletDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCryptoBrokerWalletDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCryptoBrokerWalletDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE WALLET*/
    public CryptoBrokerWallet createCryptoBrokerWallet(final KeyPair walletKeys, final String ownerPublicKey) throws CantCreateNewCryptoBrokerWalletException{
        //TODO agregar exception
        //TODO almacenar clave privada en un archivo, crear un registro que asocie las claves publicas
        try {
            if (walletExists(walletKeys.getPublicKey(),ownerPublicKey))
                throw new CantCreateNewCryptoBrokerWalletException ("Cant create new Crypto Broker Wallet, It exists.", "Crypto Broker Wallet", "Cant create new Crypto Broker Wallet, alias exists.");

            persistNewCryptoBrokerWalletPrivateKeysFile(walletKeys.getPublicKey(), walletKeys.getPrivateKey());
            DatabaseTable table = this.database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_WALLET_PUBLIC_KEY_COLUMN_NAME, ownerPublicKey);
            record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_BROKER_PUBLIC_KEY_COLUMN_NAME, walletKeys.getPublicKey());
            table.insertRecord(record);

            return new CryptoBrokerWalletImpl(walletKeys, ownerPublicKey, this);
        } catch (CantInsertRecordException e){
            throw new CantCreateNewCryptoBrokerWalletException (e.getMessage(), e, "Crypto Broker Wallet", "Cant create new Crypto Broker Wallet, insert database problems.");
        } catch (CantPersistPrivateKeyException e){
            throw new CantCreateNewCryptoBrokerWalletException (e.getMessage(), e, "Crypto Broker Wallet", "Cant create new Crypto Broker Wallet,persist private key error.");
        } catch (Exception e) {
            throw new CantCreateNewCryptoBrokerWalletException (e.getMessage(), FermatException.wrapException(e), "Crypto Broker Wallet", "Cant create new Crypto Broker Wallet, unknown failure.");
        }
    }

    /*GET WALLET*/
    public CryptoBrokerWallet getCryptoBrokerWallet(final String ownerPublicKey) throws CantGetCryptoBrokerWalletException{
        //TODO agregar exception
        //TODO recupera la clave privada segun la clave publica de la wallet que esta en el record da la tabla de wallet
        try {
            //esto tiene que cambiar
//            String privateKey = AsymmetricCryptography.createPrivateKey();
            //esto tiene que cambiar
            String publicKey    = getCryptoBrokerWalletPublicKey(ownerPublicKey);
            String privateKey   = getCryptoBrokerWalletPrivateKey(publicKey);
            KeyPair walletKeys  = AsymmetricCryptography.createKeyPair(privateKey);
            return new CryptoBrokerWalletImpl(walletKeys, ownerPublicKey, this);
        } catch (CantGetCryptoBrokerWalletPublicKeyException e){
            throw new CantGetCryptoBrokerWalletException (e.getMessage(), e, "Crypto Broker Wallet", "Cant Get Wallet Public Key");
        } catch (CantGetCryptoBrokerWalletPrivateKeyException e){
            throw new CantGetCryptoBrokerWalletException (e.getMessage(), e, "Crypto Broker Wallet", "Cant Get Wallet Private Key");
        } catch (Exception e) {
            throw new CantGetCryptoBrokerWalletException (e.getMessage(), FermatException.wrapException(e), "Crypto Broker Wallet", "Cant Get Crypto Broker Wallet, unknown failure.");
        }
    }

    /*GET BALANCE BOOKED*/
    public float getCalculateBookBalance(final CurrencyType currencyType, final String walletPublicKey) throws CantCalculateBalanceException {
        try {
            return getCurrentBalance(BalanceType.BOOK,currencyType,walletPublicKey);
        } catch (CantGetBalanceRecordException exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");

        }
    }

    /*GET BALANCE AVAILABLE*/
    public float getCalculateAvailableBalance(final CurrencyType currencyType, final String walletPublicKey) throws CantCalculateBalanceException {
        try{
            return getCurrentBalance(BalanceType.AVAILABLE,currencyType,walletPublicKey);
        } catch (CantGetBalanceRecordException exception){
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantCalculateBalanceException(CantCalculateBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*LIST TRANSACTION*/
    public List<StockTransaction> getTransactionsList(DeviceUser deviceUser) throws CantListCryptoBrokerWalletTransactionException {
        List<StockTransaction> list = new ArrayList<StockTransaction>();
        DatabaseTable cryptoBrokerTable;
        try {
            cryptoBrokerTable = this.database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);
            if (cryptoBrokerTable == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get crypto broker wallet Transaction list, table not found.", "Crypto Broker Wallet", "Cant get Crypto Broker Wallet Transaction list, table not found.");
            }
            cryptoBrokerTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_WALLET_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            cryptoBrokerTable.loadToMemory();

            for (DatabaseTableRecord record : cryptoBrokerTable.getRecords()) {
                list.add(getStockTransactionFromRecord(record));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListCryptoBrokerWalletTransactionException(e.getMessage(), e, "Crypto Broker Wallet", "Cant load " + CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantListCryptoBrokerWalletTransactionException(e.getMessage(), FermatException.wrapException(e), "Crypto Broker Wallet", "Cant get Crypto Broker Wallet list, unknown failure.");
        }
        return list;
    }

    /*ADD CREDIT*/
    public void addDebit(final StockTransaction transaction) throws CantAddDebitException {
        try {
            if (isTransactionInTable(transaction.getTransactionId(), TransactionType.DEBIT, transaction.getBalanceType()))
                throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            float availableAmount = transaction.getBalanceType().equals(BalanceType.AVAILABLE) ? transaction.getAmount() : 0L;
            float bookAmount = transaction.getBalanceType().equals(BalanceType.BOOK) ? transaction.getAmount() : 0L;
            float runningBookBalance = calculateBookRunningBalance(-bookAmount, transaction.getWalletPublicKey());
            float runningAvailableBalance = calculateAvailableRunningBalance(-availableAmount, transaction.getWalletPublicKey());

            executeTransaction(transaction, TransactionType.DEBIT, runningBookBalance, runningAvailableBalance);
        } catch (CantLoadTableToMemoryException | CantExecuteCryptoBrokerTransactionException exception) {
            throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception) {
            throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    /*ADD DEBIT*/
    public void addCredit(final StockTransaction transaction) throws CantAddCreditException {
        try {
            if (isTransactionInTable(transaction.getTransactionId(), TransactionType.CREDIT, transaction.getBalanceType()))
                throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            float availableAmount = transaction.getBalanceType().equals(BalanceType.AVAILABLE) ? transaction.getAmount() : 0L;
            float bookAmount = transaction.getBalanceType().equals(BalanceType.BOOK) ? transaction.getAmount() : 0L;
            float runningBookBalance = calculateBookRunningBalance(-bookAmount, transaction.getWalletPublicKey());
            float runningAvailableBalance = calculateAvailableRunningBalance(-availableAmount, transaction.getWalletPublicKey());

            executeTransaction(transaction, TransactionType.CREDIT, runningBookBalance, runningAvailableBalance);
        } catch (CantExecuteCryptoBrokerTransactionException exception) {
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (Exception exception){
            throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause");
        }
    }

    //PRIVATE
    private float calculateBookRunningBalance(final float transactionAmount, String publicKeyWallet) throws CantGetBalanceRecordException {
        return getCurrentBalance(BalanceType.BOOK, publicKeyWallet) + transactionAmount;
    }

    private float calculateAvailableRunningBalance(final float transactionAmount, String publicKeyWallet) throws CantGetBalanceRecordException {
        return getCurrentBalance(BalanceType.AVAILABLE, publicKeyWallet) + transactionAmount;
    }

    private List<DatabaseTableRecord> getBalancesRecord(final CurrencyType currencyType, final String walletPublicKey) throws CantGetBalanceRecordException{
        try {
            DatabaseTable totalBalancesTable = this.database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
            totalBalancesTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME, currencyType.getCode(), DatabaseFilterType.EQUAL);
            totalBalancesTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey, DatabaseFilterType.EQUAL);
            totalBalancesTable.loadToMemory();
            return totalBalancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    private boolean isTransactionInTable(final UUID transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable cryptoBrokerTable = this.database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
        cryptoBrokerTable.setUUIDFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        cryptoBrokerTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        cryptoBrokerTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        cryptoBrokerTable.loadToMemory();
        return !cryptoBrokerTable.getRecords().isEmpty();
    }

    private float getCurrentBalance(final BalanceType balanceType, final CurrencyType currencyType, final String walletPublicKey) throws CantGetBalanceRecordException {
        long balanceAmount = 0;
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord(currencyType, walletPublicKey)){
                balanceAmount += record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME);
            }
        } else {
            for (DatabaseTableRecord record : getBalancesRecord(currencyType, walletPublicKey)){
                balanceAmount += record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME);
            }
        }
        return balanceAmount;
    }

    private float getCurrentBalance(BalanceType balanceType, String publicKeyWallet) {
        try {
            long balanceAmount = 0;
            if (balanceType == BalanceType.AVAILABLE) {
                balanceAmount = getBalancesTotalBalances(publicKeyWallet).getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME);
            } else {
                balanceAmount = getBalancesTotalBalances(publicKeyWallet).getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME);
            }
            return balanceAmount;
        } catch (Exception exception) {
            return 0;
        }
    }

    private DatabaseTableRecord getBalancesTotalBalances(String publicKeyWallet) throws CantGetBalanceRecordException {
        try {
            DatabaseTable totalBalancesTable = database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);;
            ;
            totalBalancesTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_WALLET_PUBLIC_KEY_COLUMN_NAME, publicKeyWallet, DatabaseFilterType.EQUAL);
            totalBalancesTable.loadToMemory();
            if (!totalBalancesTable.getRecords().isEmpty()) {
                return totalBalancesTable.getRecords().get(0);
            } else {
                return totalBalancesTable.getEmptyRecord();
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record", exception, "Can't load balance table", "");
        }
    }

    //    private void executeTransaction(final WalletTransaction transaction, final TransactionType transactionType, final float runningBookBalance,  final float runningAvailableBalance) throws CantExecuteCryptoBrokerTransactionException {
    private void executeTransaction(final StockTransaction transaction, final TransactionType transactionType, final float runningBookBalance,  final float runningAvailableBalance) throws CantExecuteCryptoBrokerTransactionException {
        try {
            DatabaseTable cryptoBrokerTable = this.database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);
            DatabaseTableRecord cryptoBrokerRecord = cryptoBrokerTable.getEmptyRecord();
            loadCryptoBrokersRecordAsNew(cryptoBrokerRecord, transaction, transactionType, transaction.getBalanceType(), runningBookBalance, runningAvailableBalance);
            cryptoBrokerTable.insertRecord(cryptoBrokerRecord);

            DatabaseTable totalBalancesTable = this.database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
            DatabaseTableRecord totalBalancesRecord = totalBalancesTable.getEmptyRecord();

            totalBalancesTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_WALLET_PUBLIC_KEY_COLUMN_NAME, transaction.getWalletPublicKey(), DatabaseFilterType.EQUAL );
            totalBalancesTable.loadToMemory();
            String description = "STOCK: " +transaction.getCurrencyType();
            loadtotalBalancesRecordAsNew(totalBalancesRecord, transaction, description, runningBookBalance, runningAvailableBalance);
            if (totalBalancesTable.getRecords().isEmpty()) {
                totalBalancesTable.insertRecord(totalBalancesRecord);
            } else {
                totalBalancesTable.updateRecord(totalBalancesRecord);
            }
        } catch (CantInsertRecordException e) {
            throw new CantExecuteCryptoBrokerTransactionException(e.getMessage(), e, "Crypto Broker Wallet", "Cant Add new Transaction in Crypto Broker Wallet, insert database problems.");
        } catch (Exception e) {
            throw new CantExecuteCryptoBrokerTransactionException(e.getMessage(), FermatException.wrapException(e), "Crypto Broker Wallet", "Cant Add new Transaction in Crypto Broker Wallet, unknown failure.");
        }
    }

    private void loadCryptoBrokersRecordAsNew(
            DatabaseTableRecord databaseTableRecord,
            StockTransaction transaction,
            TransactionType transactionType,
            BalanceType balanceType,
            float runningBookBalance,
            float runningAvailableBalance
    ) {
        databaseTableRecord.setUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, transaction.getTransactionId());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME, transaction.getWalletPublicKey());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BROKER_PUBLIC_KEY_COLUMN_NAME, transaction.getOwnerPublicKey());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_CURRENCY_TYPE_COLUMN_NAME, transaction.getCurrencyType().getCode());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME, transaction.getMerchandise().getCode());
        databaseTableRecord.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME, transaction.getAmount());
        databaseTableRecord.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME, runningBookBalance);
        databaseTableRecord.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, runningAvailableBalance);
        databaseTableRecord.setLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME, transaction.getTimestamp());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME, transaction.getMemo());
    }

    private void loadtotalBalancesRecordAsNew(
            DatabaseTableRecord databaseTableRecord,
            StockTransaction transaction,
            String description,
            float runningBookBalance,
            float runningAvailableBalance
    ) {
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_WALLET_PUBLIC_KEY_COLUMN_NAME, transaction.getWalletPublicKey());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_DESCRIPTION_COLUMN_NAME, transaction.getCurrencyType().getCode());
        databaseTableRecord.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_DESCRIPTION_COLUMN_NAME, description);
        databaseTableRecord.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME, runningBookBalance);
        databaseTableRecord.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, runningAvailableBalance);
    }

    private StockTransaction getStockTransactionFromRecord(final DatabaseTableRecord record) throws InvalidParameterException {
        UUID  transactionId                 = record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME);
        String walletPublicKey              = record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_WALLET_PUBLIC_KEY_COLUMN_NAME);
        String ownerPublicKey               = record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BROKER_PUBLIC_KEY_COLUMN_NAME);
        BalanceType  balanceType            = BalanceType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME));
        TransactionType  transactionType    = TransactionType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME));
        CurrencyType currencyType           = CurrencyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_CURRENCY_TYPE_COLUMN_NAME));
        FermatEnum merchadise               = CurrencyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME));
        float   amount                      = record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME);
        float   runningBookBalance          = record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME);
        float   runningAvailableBalance     = record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        long    timeStamp                   = record.getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME);
        String  memo                        = record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME);
        KeyPair walletKeyPair = AsymmetricCryptography.createKeyPair(walletPublicKey);

        return new CryptoBrokerStockTransactionRecordImpl(
                transactionId,
                walletKeyPair,
                ownerPublicKey,
                balanceType,
                transactionType,
                currencyType,
                merchadise,
                amount,
                runningBookBalance,
                runningAvailableBalance,
                timeStamp,
                memo
        );
    }

    private void  persistNewCryptoBrokerWalletPrivateKeysFile(String publicKey,String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CryptoBrokerWalletPluginRoot.CRYPTO_BROKER_WALLET_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(privateKey);
            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw  new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }
    }

    private boolean walletExists (String walletKeys, final String ownerPublicKey) throws CantCreateNewCryptoBrokerWalletException {
        DatabaseTable table;
        try {
            table = this.database.getTable (CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Crypto Broker Identity", "");
            }
            table.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_WALLET_PUBLIC_KEY_COLUMN_NAME, walletKeys, DatabaseFilterType.EQUAL);
            table.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_BROKER_PUBLIC_KEY_COLUMN_NAME, ownerPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords ().size () > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewCryptoBrokerWalletException (em.getMessage(), em, "Crypto Broker Wallet", "Cant load " + CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantCreateNewCryptoBrokerWalletException (e.getMessage(), FermatException.wrapException(e), "Crypto Broker Wallet", "unknown failure.");
        }
    }

    private String getCryptoBrokerWalletPrivateKey(String publicKey) throws CantGetCryptoBrokerWalletPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    CryptoBrokerWalletPluginRoot.CRYPTO_BROKER_WALLET_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.loadFromMedia();
            privateKey = file.getContent();
        } catch (CantLoadFileException e) {
            throw new CantGetCryptoBrokerWalletPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (CantCreateFileException e) {
            throw new CantGetCryptoBrokerWalletPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer wallet private keys file.", null);
        } catch (Exception e) {
            throw  new CantGetCryptoBrokerWalletPrivateKeyException("CAN'T GET PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }
        return privateKey;
    }

    private String getCryptoBrokerWalletPublicKey(String ownerPublicKey) throws CantGetCryptoBrokerWalletPublicKeyException{
        try {
            DatabaseTable totalBalancesTable = database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_TABLE_NAME);;
            ;
            totalBalancesTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_BROKER_PUBLIC_KEY_COLUMN_NAME, ownerPublicKey, DatabaseFilterType.EQUAL);
            totalBalancesTable.loadToMemory();
            if (!totalBalancesTable.getRecords().isEmpty() ) {
                return totalBalancesTable.getRecords().get(0).getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_WALLET_WALLET_PUBLIC_KEY_COLUMN_NAME);
            } else {
                return "";
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetCryptoBrokerWalletPublicKeyException("CANT GET PUBLIC KEY",exception,"Can't load balance table" , "");
        }
    }
}