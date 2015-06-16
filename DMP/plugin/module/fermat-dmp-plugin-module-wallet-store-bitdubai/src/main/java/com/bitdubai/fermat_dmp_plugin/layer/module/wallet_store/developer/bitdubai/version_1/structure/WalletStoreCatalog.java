package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.Wallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.Catalog;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.exceptions.CantRemoveRecordException;

import java.util.List;
import java.util.UUID;

/**
 * @author rodrigo
 * Created by rodrigo on 01/05/15.
 */
public class WalletStoreCatalog implements DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem, Catalog {

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private ErrorManager errorManager;

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

    /**
     * Constructor
     * @param ownerId
     */
    public WalletStoreCatalog(UUID ownerId, UUID walletId){
        /**
         * The only one who can set the ownerId is the Plugin Root.
         */
        this.ownerId = ownerId;

        this.walletId = walletId;
    }

    /**
     * Create the database
     * @throws CantInitializeWalletException
     */
    public void initialize() throws CantInitializeWalletException{
        /**
         * I will try to open the wallets' database..
         */
        try {

            this.database = this.pluginDatabaseSystem.openDatabase(this.ownerId, this.walletId.toString());

        }
        catch (DatabaseNotFoundException databaseNotFoundException) {

            WalletStoreDatabaseFactory databaseFactory = new WalletStoreDatabaseFactory();
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
     * Record the Installed Wallet
     */
    public void recordInstalledWallet(){
        /**
         * Initialize the table
         */
        DatabaseTable table;

        table = this.database.getTable(WalletStoreDatabaseConstants.WALLET_STORE_TABLE_NAME);

        /**
         * Initialize the record
         */
        DatabaseTableRecord record = table.getEmptyRecord();

        /**
         * I put the walletId in the record and insert it in the table
         */
        record.setStringValue(WalletStoreDatabaseConstants.WALLET_STORE_TABLE_ID_COLUMN_NAME, this.walletId.toString());

        try {

            table.insertRecord(record);

        } catch (CantInsertRecord cantInsertRecord) {
            /**
             * I cant insert record
             */
            System.err.println("CantInsertRecord: " + cantInsertRecord.getMessage());
            cantInsertRecord.printStackTrace();
        }
    }

    /**
     * Record the Uninstalled Wallet
     */
    public void recordUninstalledWallet() throws CantRemoveRecordException{
        /**
         * Initialize the table and load the record
         */
        DatabaseTable table;

        table = this.database.getTable(WalletStoreDatabaseConstants.WALLET_STORE_TABLE_NAME);

        for (DatabaseTableRecord record: table.getRecords()){
            if(record.getStringValue(WalletStoreDatabaseConstants.WALLET_STORE_TABLE_ID_COLUMN_NAME)==this.walletId.toString()){
                /**
                 * delete record
                 */
                /**
                    Habria que implementar el metodo para eliminar un registro en el AndroidDatabaseTable del core

                 table.deleteRecord(record)
                 */
            }
        }


    }

    /**
     * DealWithEvents Interface implementation
     * @param eventManager
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealWithErrors Interface implementation.
     * @param errorManager
     */
    @Override
    public void setErrorManager (ErrorManager errorManager){ this.errorManager = errorManager; };

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     * @param pluginDatabaseSystem
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database getDatabase(){
        return this.database;
    }

    /**
     * Catalog Interface implementation.
     * @return wallet list with state
     */
    //COMENTADO PORQUE EL NETWORK SERVICE NO ESTA COMPLETO
    @Override
    public List<Wallet> getWallets() throws CantGetWalletsException {
        /**
         * Initialize the WalletStoreNetworkService

        WalletStoreManager networkService;
        List<Wallet> wallets;

        /**
         * load the wallet list


        wallets = networkService.getWallets();

        for (Wallet wallet: wallets){
            /**
             * Verify if the wallet is installed or not

            this.walletId = wallet.getWalletId();
            if (isInstalled()==true){
                /**
                 * I set the attribute Installed

                wallet.setInstalled(true);

            } else{

                wallet.setInstalled(false);

            }
        }


        return wallets;
         */
        return null;
    }


    /**
     * Verify if the wallet is installed or not
     * @return true if the wallet is installed
     */
    private boolean isInstalled(){
       DatabaseTable table = this.database.getTable(WalletStoreDatabaseConstants.WALLET_STORE_TABLE_NAME);

       List<DatabaseTableRecord> records = table.getRecords();

        for(DatabaseTableRecord record: records){
            if(record.getStringValue(WalletStoreDatabaseConstants.WALLET_STORE_TABLE_ID_COLUMN_NAME)==this.walletId.toString()){

                return true;

            }else{
                return false;
            }
        }
        return false;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }
}
