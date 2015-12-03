package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;
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
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletBalanceRecordImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletImpl;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 17.10.15.
 * Modified by Franklin Marcano
 */
public class CryptoBrokerWalletDatabaseDao implements DealsWithPluginFileSystem {
    //TODO: Documentar y Manejo de excepciones
    public static final String PATH_DIRECTORY = "cryptobrokerwallet-swap/";
    PluginFileSystem pluginFileSystem;
    UUID plugin;

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setPlugin(UUID plugin){
        this.plugin = plugin;
    }

    private Database database;

    public CryptoBrokerWalletDatabaseDao(Database database){
        this.database = database;
    }

    public List<CryptoBrokerWalletBalanceRecord> getAvailableBalanceByMerchandise() throws CantCalculateBalanceException {
        return null;
    }

    public List<CryptoBrokerWalletBalanceRecord> getBookBalanceByMerchandise() throws CantCalculateBalanceException, CantGetBalanceRecordException {
        return getCurrentBookBalanceByMerchandise();
    }

    public List<CryptoBrokerWalletBalanceRecord> getAvailableBalanceByMerchandiseFrozen() throws CantCalculateBalanceException {
        return null;
    }

    public List<CryptoBrokerWalletBalanceRecord> getBookBalanceByMerchandiseFrozen() throws CantCalculateBalanceException {
        return null;
    }

    //TODO Implementar:
    // Este metodo se utilizara para devolver al Broker que se le puede vender, recibira como argumento que mercaderia quiere comprar, la cantidad a comprar, como paga
    // el metodo le devolvera Cuanto se le puede vender, el precio de la venta en la moneda que piden, y el precio en dolar, podria ser un mapa que devuelva dicha informacion
    public void getQuote(){

    }

    /*
    * Add a new debit transaction.
    */
    public void addDebit(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, final BalanceType balanceType) throws CantAddDebitException{
        System.out.println("Agregando Debito-----------------------------------------------------------");
        try {
            if (isTransactionInTable(cryptoBrokerStockTransactionRecord.getTransactionId().toString(), TransactionType.DEBIT, balanceType))
                throw new CantAddDebitException(CantAddDebitException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            float availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? cryptoBrokerStockTransactionRecord.getAmount() : 0L;
            float bookAmount = balanceType.equals(BalanceType.BOOK) ? cryptoBrokerStockTransactionRecord.getAmount() : 0L;

            float availableRunningBalance = calculateAvailableRunningBalanceByMerchandise(-availableAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
            float bookRunningBalance = calculateBookRunningBalanceByMerchandise(-bookAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());

            executeTransaction(cryptoBrokerStockTransactionRecord, TransactionType.DEBIT, balanceType, availableRunningBalance, bookRunningBalance);
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (CantGetBalanceRecordException e) {
            e.printStackTrace();
        } catch (CantExecuteCryptoBrokerTransactionException e) {
            e.printStackTrace();
        }
    }

    /*
    * Add a new credit transaction.
    */
    public void addCredit(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, final BalanceType balanceType) throws CantAddCreditException{
        System.out.println("Agregando Credito-----------------------------------------------------------");
        try {
            if (isTransactionInTable(cryptoBrokerStockTransactionRecord.getTransactionId().toString(), TransactionType.CREDIT, balanceType))
                throw new CantAddCreditException(CantAddCreditException.DEFAULT_MESSAGE, null, null, "The transaction is already in the database");

            float availableAmount = balanceType.equals(BalanceType.AVAILABLE) ? cryptoBrokerStockTransactionRecord.getAmount() : 0L;
            float bookAmount = balanceType.equals(BalanceType.BOOK) ? cryptoBrokerStockTransactionRecord.getAmount() : 0L;

            float availableRunningBalance = calculateAvailableRunningBalanceByMerchandise(availableAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
            float bookRunningBalance = calculateBookRunningBalanceByMerchandise(bookAmount, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());

            executeTransaction(cryptoBrokerStockTransactionRecord, TransactionType.CREDIT, balanceType, availableRunningBalance, bookRunningBalance);
        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (CantGetBalanceRecordException e) {
            e.printStackTrace();
        } catch (CantExecuteCryptoBrokerTransactionException e) {
            e.printStackTrace();
        }
    }

    private void executeTransaction(final CryptoBrokerStockTransactionRecord stockTransaction, final TransactionType transactionType, final BalanceType balanceType, final float runningBookBalance, final float runningAvailableBalance) throws CantExecuteCryptoBrokerTransactionException {
        try {
            DatabaseTableRecord stockWalletTransactionRecord = constructStockWalletTransactionRecord(stockTransaction, transactionType, balanceType, runningAvailableBalance, runningBookBalance);
            DatabaseTableRecord stockWalletBalance = constructStockWalletBalanceRecord(stockTransaction, runningAvailableBalance, runningAvailableBalance);
            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(getStockWalletTransactionTable(), stockWalletTransactionRecord);

            DatabaseTable databaseTable = getBalancesTable();
            databaseTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME, stockTransaction.getMerchandise().toString(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            if (databaseTable.getRecords().isEmpty()){
                transaction.addRecordToInsert(databaseTable, stockWalletBalance);
            }else{
                transaction.addRecordToUpdate(databaseTable, stockWalletBalance);
            }

            database.executeTransaction(transaction);

        } catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (DatabaseTransactionFailedException e) {
            e.printStackTrace();
        }
    }

    private DatabaseTableRecord constructStockWalletTransactionRecord(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, final TransactionType transactionType, final BalanceType balanceType, final float availableRunningBalance, final float bookRunningBalance){
        DatabaseTableRecord record = getStockWalletTransactionTable().getEmptyRecord();
        record.setUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTransactionId());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getBalanceType().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTransactionType().getCode());
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getAmount());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_CURRENCY_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getCurrencyType().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTransactionType().getCode());
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);
        record.setLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getTimestamp());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMemo());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getOriginTransaction().getCode());
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getPriceReference());

        return record;
    }

    private DatabaseTableRecord constructStockWalletBalanceRecord(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, float  availableRunningBalance, float bookRunningBalance)
    {

        DatabaseTableRecord record = getBalancesTable().getEmptyRecord();

        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
        record.setStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME, cryptoBrokerStockTransactionRecord.getCurrencyType().getCode());
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME, availableRunningBalance);
        record.setFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME, bookRunningBalance);

        return record;

    }

    private float calculateAvailableRunningBalanceByMerchandise(final float transactionAmount, String merchandise) throws CantGetBalanceRecordException{
        return  getCurrentAvailableBalanceByMerchandise(merchandise) + transactionAmount;
    }

    private float calculateBookRunningBalanceByMerchandise(final float transactionAmount, String merchandise) throws CantGetBalanceRecordException{
        return  getCurrentBookBalanceByMerchandise(merchandise) + transactionAmount;
    }

    private float getCurrentAvailableBalanceByMerchandise(String merchandise) throws CantGetBalanceRecordException{
        return getCurrentBalanceByMerchandise(BalanceType.AVAILABLE, merchandise);
    }

    private float getCurrentBookBalanceByMerchandise(String merchandise) throws CantGetBalanceRecordException{
        return getCurrentBalanceByMerchandise(BalanceType.BOOK, merchandise);
    }

    private float getCurrentBalanceByMerchandise(BalanceType balanceType, String merchandise)
    {
        float balanceAmount = 0;
        try {

            if (balanceType == BalanceType.AVAILABLE)
                balanceAmount = getBalancesByMerchandiseRecord(merchandise).getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME);
            else
                balanceAmount = getBalancesByMerchandiseRecord(merchandise).getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME);

            return balanceAmount;
        }
        catch (Exception exception){
            return balanceAmount;
        }
    }

    private DatabaseTableRecord getBalancesByMerchandiseRecord(String merchandise) throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME, merchandise, DatabaseFilterType.EQUAL);
            balancesTable.loadToMemory();
            if (!balancesTable.getRecords().isEmpty() ) {
                return balancesTable.getRecords().get(0);
            }
            else
            {
                return balancesTable.getRecords().get(0);
            }
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private boolean isTransactionInTable(final String transactionId, final TransactionType transactionType, final BalanceType balanceType) throws CantLoadTableToMemoryException {
        DatabaseTable assetIssuerWalletTable = getStockWalletTransactionTable();
        assetIssuerWalletTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, transactionType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.setStringFilter(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, balanceType.getCode(), DatabaseFilterType.EQUAL);
        assetIssuerWalletTable.loadToMemory();
        return !assetIssuerWalletTable.getRecords().isEmpty();
    }

    private DatabaseTable getStockWalletTransactionTable(){
        DatabaseTable databaseTable = database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);
        return databaseTable;
    }

    private List<CryptoBrokerWalletBalanceRecord> getCurrentBookBalanceByMerchandise() throws CantGetBalanceRecordException{
        return getCurrentBalanceByMerchandise(BalanceType.BOOK);
    }

    private List<CryptoBrokerWalletBalanceRecord> getCurrentAvailableBalanceByMerchandise() throws CantGetBalanceRecordException{
        return getCurrentBalanceByMerchandise(BalanceType.AVAILABLE);
    }

    private List<CryptoBrokerWalletBalanceRecord> getCurrentBalanceByMerchandise(final BalanceType balanceType) throws CantGetBalanceRecordException {
        List<CryptoBrokerWalletBalanceRecord> stockWalletBalances= new ArrayList<>();
        CryptoBrokerWalletBalanceRecordImpl cryptoBrokerWalletBalanceRecord = new CryptoBrokerWalletBalanceRecordImpl();
        if (balanceType == BalanceType.AVAILABLE){
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                try {
                    if (CurrencyType.CRYPTO_MONEY != CurrencyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME)))
                    {
                        cryptoBrokerWalletBalanceRecord.setMerchandise(FiatCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    }else{
                        cryptoBrokerWalletBalanceRecord.setMerchandise(CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    }
                    cryptoBrokerWalletBalanceRecord.setAvilableBalance(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME));
                    cryptoBrokerWalletBalanceRecord.setBookBalance(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME));
                    cryptoBrokerWalletBalanceRecord.setCurrencyType(CurrencyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME)));
                    stockWalletBalances.add(cryptoBrokerWalletBalanceRecord);

                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            for (DatabaseTableRecord record : getBalancesRecord())
            {
                try {
                    if (CurrencyType.CRYPTO_MONEY != CurrencyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME)))
                    {
                        cryptoBrokerWalletBalanceRecord.setMerchandise(FiatCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    }else{
                        cryptoBrokerWalletBalanceRecord.setMerchandise(CryptoCurrency.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME)));
                    }
                    cryptoBrokerWalletBalanceRecord.setAvilableBalance(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME));
                    cryptoBrokerWalletBalanceRecord.setBookBalance(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME));
                    cryptoBrokerWalletBalanceRecord.setCurrencyType(CurrencyType.getByCode(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME)));
                    stockWalletBalances.add(cryptoBrokerWalletBalanceRecord);

                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
            }

        }
        return stockWalletBalances;
    }

    private List<DatabaseTableRecord> getBalancesRecord() throws CantGetBalanceRecordException{
        try {
            DatabaseTable balancesTable = getBalancesTable();
            balancesTable.loadToMemory();
            return balancesTable.getRecords();
        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetBalanceRecordException("Error to get balances record",exception,"Can't load balance table" , "");
        }
    }

    private DatabaseTable getBalancesTable(){
        DatabaseTable databaseTable = database.getTable(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
        return databaseTable;
    }
}