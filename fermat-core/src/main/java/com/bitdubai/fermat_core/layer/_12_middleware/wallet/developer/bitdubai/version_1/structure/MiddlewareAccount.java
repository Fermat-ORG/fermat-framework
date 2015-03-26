package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer._11_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_api.layer._11_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer._11_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountLockStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.AccountStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.CryptoValueChunkStatus;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.Account;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.OperationFailed;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantUpdateRecord;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.exceptions.CantStartAccountException;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.interfaces.AccountService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

class MiddlewareAccount implements  Account, AccountService, DealsWithCryptoIndex,  DealsWithEvents {
    
    /**
     * MiddlewareFiatAccount Interface member variables.
     */
    
    private Database database;
    private DatabaseTable table;
    private DatabaseTableRecord record;

    /**
     * Account Interface member variables.
     */
    private UUID id;
    private String label = "";
    private String name = "";
    private long balance = 0;
    private long availableBalance = 0;
    private long previousAvailableBalance = 0;
    private FiatCurrency fiatCurrency;
    private AccountStatus status;
    private AccountLockStatus lockStatus = AccountLockStatus.UNLOCKED;
    
    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;
    
    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;


    
    
    
    /**
     * Class constructor.
     */
    MiddlewareAccount(UUID id){
        this.id = id;
    }
    
    
    
    
    
    /**
     * MiddlewareFiatAccount interface implementation.
     * 
     * Note that the following methods are package-private.
     * * * * 
     */
    UUID getId(){
        return this.id;
    }

    public AccountLockStatus getLockStatus() {
        return lockStatus;
    }
    
    void setBalance(long balance){
        this.balance = balance;
    }

    void setFiatCurrency(FiatCurrency fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }

    void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
    
    void setTable (DatabaseTable table) {
        this.table = table;
    }
    
    DatabaseTable getTable(){
        return this.table;
    }
    
    void setRecord (DatabaseTableRecord record){
        
        this.record = record;

        this.label= record.getStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_LABEL_COLUMN_NAME);
        this.name = record.getStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME_COLUMN_NAME);
        this.fiatCurrency = FiatCurrency.getByCode(record.getStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME));
        this.status = AccountStatus.getByCode(record.getStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_NAME));
    
    }
    
    DatabaseTableRecord getRecord(){
        return this.record;
    }


    public void cryptoMarketPricesChanged() {
        // LOUI: TODO: este metodo se tiene que ejecutar cuando detecta el evento disparado por CryptoIndex plugIn llamado: CryptoMarketPricesChanged  

        try {
            calculateBalances();
            
            this.lockStatus = AccountLockStatus.UNLOCKED;
        }
        catch (CantCalculateBalanceException cantCalculateBalanceException){
            /**
             * There is no point to throw another exception from here since this call only comes from an Event Handler 
             * that is going to do nothing with it. I lock the account in order to not let more transactions come in.
             * * * 
             */
            System.err.println("CantCalculateBalanceException: " + cantCalculateBalanceException.getMessage());
            cantCalculateBalanceException.printStackTrace();

            this.lockStatus = AccountLockStatus.LOCKED;
        }
    }

    void updateBalance() {

        try {
            calculateBalances();
        }
        catch (CantCalculateBalanceException cantCalculateBalanceException){
            /**
             * I lock the account in order to not let more transactions come in.
             * * * 
             */
            System.err.println("CantCalculateBalanceException: " + cantCalculateBalanceException.getMessage());
            cantCalculateBalanceException.printStackTrace();
            
            this.lockStatus = AccountLockStatus.LOCKED;
        }
    }



    /**
     * Account interface implementation.
     */
    public long getBalance() {
        return balance;
    }

    public double getAvailableBalance (){

        return this.availableBalance;
    }

    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public void setLabel(String label) throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_LABEL_COLUMN_NAME, label);
            this.table.updateRecord(this.record);
            this.label = label;
        }
        catch (CantUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    public void setName(String name) throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME_COLUMN_NAME, name);
            this.table.updateRecord(this.record);
            this.name = name;
        }
        catch (CantUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    @Override
    public void openAccount() throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_NAME, AccountStatus.OPEN.getCode());
            this.table.updateRecord(this.record);
            this.status = AccountStatus.OPEN;
        }
        catch (CantUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    @Override
    public void closeAccount() throws OperationFailed {

        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_NAME, AccountStatus.CLOSED.getCode());
            this.table.updateRecord(this.record);
            this.status = AccountStatus.CLOSED;
        }
        catch (CantUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    @Override
    public void deleteAccount() throws OperationFailed {
        try {
            this.record.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_NAME, AccountStatus.DELETED.getCode());
            this.table.updateRecord(this.record);
            this.status = AccountStatus.DELETED;
        }
        catch (CantUpdateRecord cantUpdateRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantUpdateRecord: " + cantUpdateRecord.getMessage());
            cantUpdateRecord.printStackTrace();
            throw new OperationFailed();
        }
    }

    
    
    /**
     * AccountService interface implementation.
     */
    @Override
    public void start() throws CantStartAccountException {

        /**
         * Check if the Account state is Open.
         */
        if (AccountStatus.getByCode(record.getStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_NAME)) != AccountStatus.OPEN) {
            /**
             * An account that is not open can not be started.
             */
            throw new CantStartAccountException();
        }

        try {
            calculateBalances();
        }
        catch (CantCalculateBalanceException cantCalculateBalanceException){
            /**
             * If I cant calculate the balance then the account cannot start.
             * * * 
             */
            System.err.println("CantCalculateBalanceException: " + cantCalculateBalanceException.getMessage());
            cantCalculateBalanceException.printStackTrace();
            throw new CantStartAccountException();
        }
        
        // LOUI: TODO: Aca tiene que escuchar un evento disparado por CryptoIndex plugIn llamado: CryptoMarketPricesChanged  
        
    }

    @Override
    public void stop() {

        // LOUI: TODO: Aca se tiene que desuscribir del evento disparado por CryptoIndex plugIn llamado: CryptoMarketPricesChanged  
        
    }


    
    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    @Override
    public void setCryptoIndexManager(CryptoIndexManager cryptoIndexManager) {
        this.cryptoIndexManager = cryptoIndexManager;
    }

    
    
    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
    
    
    
    
    
    
    /**
     * Private Methods.
     */
    private void calculateBalances() throws CantCalculateBalanceException {

        /**
         * First we will calculate the account balance and then de available balance.
         */
        
        
        /**
         * Load all the unspent Crypto Value Chunks pointing to this account.
         */
        DatabaseTable table;

        table = this.database.getTable(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);

        table.setStringFilter(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, CryptoValueChunkStatus.UNSPENT.getCode(), DatabaseFilterType.EQUAL);
        table.setUUIDFilter(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME, this.id, DatabaseFilterType.EQUAL);

        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantCalculateBalanceException();
        }

        /**
         * Calculate the balance of the Account.
         */
        this.balance = 0;

        for (DatabaseTableRecord record : table.getRecords()) {

            this.balance =+ record.getlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);

        }
        
        /**
         * Calculate the available balance of the Account.
         *
         * 1) Make a list of all crypto currency involved
         *
         * 2) Ask the for the last price of each one
         *
         * * * * * * * 
         */

        Map<CryptoCurrency, Double> cryptoMarketPrice = new HashMap();
        CryptoCurrency cryptoCurrency;
        long cryptoAmount;
        long fiatAmount;
        double marketPrice;
        double pricePaid;

        for (DatabaseTableRecord record : table.getRecords()) {

            cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME));

            if (cryptoMarketPrice.containsValue(cryptoCurrency) == false) {

                try {
                    marketPrice = cryptoIndexManager.getMarketPrice(this.fiatCurrency, cryptoCurrency);
                }
                catch (FiatCurrencyNotSupportedException | CryptoCurrencyNotSupportedException currencyNotSupportedException) {
                    /**
                     * This shouldn't happen ,but if it does, I can not recover from this situation.
                     */
                    System.err.println("currencyNotSupportedException: " + currencyNotSupportedException.getMessage());
                    currencyNotSupportedException.printStackTrace();
                    throw new CantCalculateBalanceException();
                }
                

                cryptoMarketPrice.put(cryptoCurrency, Double.valueOf(marketPrice));

            }
        }

        /**
         * The available balance is formed by all the balances in the crypto value chunks where the price paid is below
         * the current market price.
         * * * 
         */

        this.availableBalance = 0;

        for (DatabaseTableRecord record : table.getRecords()) {

            cryptoCurrency = CryptoCurrency.getByCode(record.getStringValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME));
            cryptoAmount = record.getlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME);
            fiatAmount = record.getlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME);

            pricePaid = fiatAmount / cryptoAmount;
            marketPrice = cryptoMarketPrice.get(cryptoCurrency).doubleValue();

            if (pricePaid <= marketPrice) {
                this.availableBalance =+ record.getlongValue(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME);
            }
        }
        
        if ( this.previousAvailableBalance != this.availableBalance) {
            // TODO: LOUI ; Aca se debe disparar un evento llamado ACCOUNT_AVAILABLE_BALANCE_CHANGED .
        }

        this.previousAvailableBalance = this.availableBalance;
        
    }
}
