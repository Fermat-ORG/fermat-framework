package com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer._11_world.crypto_index.DealsWithCryptoIndex;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.*;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.*;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.*;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.exceptions.CantStartAccountException;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.exceptions.CantStartWalletException;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.interfaces.AccountService;
import com.bitdubai.fermat_core.layer._12_middleware.wallet.developer.bitdubai.version_1.interfaces.WalletService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 2/15/15.
 */

/**
 * This Wallet provides fiat Accounts over crypto.
 *
 * * * * * *
 * * * 
 */

public class MiddlewareWallet implements DealsWithCryptoIndex, DealsWithEvents, DealsWithPluginDatabaseSystem, Wallet, WalletService {

    /**
     * DealsWithCryptoIndex Interface member variables.
     */
    private CryptoIndexManager cryptoIndexManager;
    
    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;
    
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    
    /**
     * Wallet Interface member variables.
     */
    private UUID walletId;
    private UUID ownerId;
    private Database database;

    private Map<UUID, Account> fiatAccounts = new HashMap<>();

    /**
     * Constructor.
     */
    public MiddlewareWallet (UUID ownerId){

        /**
         * The only one who can set the ownerId is the Plugin Root.
         */
        this.ownerId = ownerId;
        
        /**
         * I will get a wallet id.
         */
        this.walletId = UUID.randomUUID();
    }


    /**
     * MiddlewareWallet Interface implementation.
     */
    
    public void initialize() throws CantInitializeWalletException {

        /**
         * I will try to open the wallets' database..
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.walletId.toString());
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            
            MiddlewareDatabaseFactory databaseFactory = new MiddlewareDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */
            try {

                this.database =  databaseFactory.createDatabase(this.ownerId, this.walletId);
                
            }
            catch (CantCreateDatabaseException cantCreateDatabaseException){
    
                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
                cantCreateDatabaseException.printStackTrace();
                throw new CantInitializeWalletException();
            }
        }
        catch (CantOpenDatabaseException cantOpenDatabaseException){

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            System.err.println("CantOpenDatabaseException: " + cantOpenDatabaseException.getMessage());
            cantOpenDatabaseException.printStackTrace();
            throw new CantInitializeWalletException();
        }

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
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
    
    /**
     * Wallet Interface implementation.
     */
    @Override
    public UUID getWalletId() {
        return this.walletId;
    }

    @Override
    public Account[] getFiatAccounts() {
        
        Account[] accountsArray = {};

        Iterator iterator = fiatAccounts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            accountsArray[accountsArray.length] = (Account) pair.getValue();
            iterator.remove();
        }
        
        return accountsArray;
    }


    public Account createAccount(FiatCurrency fiatCurrency) throws CantCreateAccountException {

        /**
         * First I get a new id for the new account.
         */
        UUID id = UUID.randomUUID();

        /**
         * Then I add the new record to the database.
         */
        DatabaseTable table;
        table = this.database.getTable(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME);

        DatabaseTableRecord newRecord;
        newRecord = table.getEmptyRecord();
        
        newRecord.setUUIDValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_ID_COLUMN_NAME, id);
        newRecord.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_LABEL_COLUMN_NAME, MiddlewareDatabaseConstants.ACCOUNTS_TABLE_LABEL_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME_COLUMN_NAME, MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME_COLUMN_DEFAULT_VALUE);
        newRecord.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME, fiatCurrency.getCode());
        newRecord.setStringValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_NAME, MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_DEFAULT_VALUE);

        try {
            table.insertRecord(newRecord);
        }
        catch (CantInsertRecord cantInsertRecord) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantInsertRecord: " + cantInsertRecord.getMessage());
            cantInsertRecord.printStackTrace();
            throw new CantCreateAccountException();
        }
        
        /**
         * Finally I update the information in memory.
         */
        Account account = new MiddlewareAccount(id);

        ((MiddlewareAccount) account).setTable(table);
        ((MiddlewareAccount) account).setRecord(newRecord);

        this.fiatAccounts.put (id, account);

        return account;
    }

    /**
     * Following there comes the transactional functionality of the wallet.
     */
    
    @Override
    public void transfer(Account accountFrom, Account accountTo, long amountFrom, long amountTo, String memo) throws TransferFailedException {

        MiddlewareTransferTransaction transferTransaction = new MiddlewareTransferTransaction();

        transferTransaction.setDatabase(this.database);
        transferTransaction.setFiatAccounts(this.fiatAccounts);

        transferTransaction.transfer(accountFrom, accountTo, amountFrom, amountTo, memo);
    }


    /**
     * A debit transaction represents an amount of crypto currency going out of the wallet. What we actually register is the crypto 
     * currency together with the fiat currency that represents at the moment the transaction was done.
     * * *
     */
    @Override
    public void debit(Account account, long fiatAmount, CryptoCurrency cryptoCurrency, long cryptoAmount) throws DebitFailedException {

        
    }

    /**
     * A credit transaction represents an amount of crypto currency received. What we actually register is the crypto 
     * currency together with the fiat currency that represents at the moment the transaction was done.
     * * *
     */
    @Override
    public void credit(Account account, long fiatAmount, CryptoCurrency cryptoCurrency, long cryptoAmount) throws CreditFailedException {

        MiddlewareCreditTransaction creditTransaction = new MiddlewareCreditTransaction();

        creditTransaction.setDatabase(this.database);
        creditTransaction.setFiatAccounts(this.fiatAccounts);


        creditTransaction.credit(account, fiatAmount, cryptoCurrency, cryptoAmount);

    }


    /**
     * Wallet Service Interface implementation.
     */
    
    /**
     * The Start method loads the information of the wallet from the Database, and create an in memory structure.
     */
    @Override
    public void start() throws CantStartWalletException {
        /**
         * Will try to open the wallets' database.
         */
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.walletId.toString());
        }
        catch (DatabaseNotFoundException  | CantOpenDatabaseException exception ) {
            /**
             * I can not solve this situation.
             */
            System.err.println("DatabaseNotFoundException or CantOpenDatabaseException: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantStartWalletException();
        }

        DatabaseTable table;

        /**
         * Now I will load the information into a memory structure. Firstly the fiat accounts.
         */
        table = this.database.getTable(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME);

        try {
            table.loadToMemory();
        }
        catch (CantLoadTableToMemory cantLoadTableToMemory) {
            /**
             * I can not solve this situation.
             */
            System.err.println("CantLoadTableToMemory: " + cantLoadTableToMemory.getMessage());
            cantLoadTableToMemory.printStackTrace();
            throw new CantStartWalletException();
        }


        /**
         * Will go through the records getting each fiat account.
         */
        for (DatabaseTableRecord record : table.getRecords()) {

            UUID accountId;
            accountId = record.getUUIDValue(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_ID_COLUMN_NAME);

            Account account;
            account = new MiddlewareAccount(accountId);

            ((MiddlewareAccount) account).setDatabase(this.database);
            ((MiddlewareAccount) account).setTable(table);
            ((MiddlewareAccount) account).setRecord(record);

            ((DealsWithEvents) account).setEventManager(this.eventManager);
            ((DealsWithCryptoIndex) account).setCryptoIndexManager(this.cryptoIndexManager);

            try {
                ((AccountService) account).start();
            }
            catch (CantStartAccountException cantStartAccountException){
                /**
                 * If an Account can not be started is not critical for not starting the wallet. Although something should
                 * be done, I will leave it like this for the moment.
                 * * * 
                 */
                System.err.println("CantStartAccountException: " + cantStartAccountException.getMessage());
                cantStartAccountException.printStackTrace();
            }

            fiatAccounts.put(accountId, account);
        }

    }

    @Override
    public void stop() {

    }
}
