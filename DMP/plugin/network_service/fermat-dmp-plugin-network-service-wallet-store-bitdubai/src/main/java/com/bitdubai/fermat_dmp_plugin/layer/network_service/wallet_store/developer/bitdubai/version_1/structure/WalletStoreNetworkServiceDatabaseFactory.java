package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class WalletStoreNetworkServiceDatabaseFactory implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem{

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variables
     */
    LogManager logManager;


    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     */
    public WalletStoreNetworkServiceDatabaseFactory(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem) {
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithErrors interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithLogger interface implementation
     * @param logManager
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * Creates the database
     * @param ownerId
     * @param databaseName
     * @return
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         * **************************************************************WALLETSKIN_TABLE****************************************************
         */
        try {
            createWalletSkinDatabaseTable(database, ownerId);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "OwnerId: " + ownerId.toString(), "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
        }


        /**
         * Next, I will add the needed tables.
         * ***************************************************************DESIGNER_TABLE****************************************************
         */
        try {
            createDesignerDatabaseTable(database, ownerId);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "OwnerId: " + ownerId.toString(), "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreNetworkServiceDatabaseConstants.DESIGNER_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
        }



        /**
         * Next, I will add the needed tables.
         * *************************************************************WALLETLANGUAGE_TABLE****************************************************
         */
        try {
            createWalletLanguageDatabaseTable(database, ownerId);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "OwnerId: " + ownerId.toString(), "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
        }


        /**
         * Next, I will add the needed tables.
         * *************************************************************TRANSLATOR_TABLE****************************************************
         */
        try {
            createTranslatorDatabaseTable(database, ownerId);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "OwnerId: " + ownerId.toString(), "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
        }


        /**
         * Next, I will add the needed tables.
         * *************************************************************ITEM_TABLE****************************************************
         */
        try {
            createItemDatabaseTable(database, ownerId);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "OwnerId: " + ownerId.toString(), "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
        }


        /**
         * Next, I will add the needed tables.
         * ***************************************************************DEVELOPER_TABLE****************************************************
         */
        try {
            createDeveloperDatabaseTable(database, ownerId);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "OwnerId: " + ownerId.toString(), "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
        }


        return database;
    }

    /**
     * Creates the Developer table
     * @param database
     * @param ownerId
     */
    private void createDeveloperDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_TABLE_NAME);

        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

        table.addIndex(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_FIRST_KEY_COLUMN);

        //Create the table
        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creates the Item table
     * @param database
     * @param ownerId
     */
    private void createItemDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME);

        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_SIZE_COLUMN_NAME, DatabaseDataType.INTEGER, 12, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

        table.addIndex(WalletStoreNetworkServiceDatabaseConstants.ITEM_FIRST_KEY_COLUMN);

        //Create the table
        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creates Translator table
     * @param database
     * @param ownerId
     */
    private void createTranslatorDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_TABLE_NAME);

        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

        table.addIndex(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_FIRST_KEY_COLUMN);

        //Create the table
        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creation of Wallet Language table
     * @param database
     * @param ownerId
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    private void createWalletLanguageDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);

        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_URL_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME, DatabaseDataType.INTEGER, 12, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

        table.addIndex(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_FIRST_KEY_COLUMN);

        //Create the table
        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creation of the Designer table
     * @param database
     * @param ownerId
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    private void createDesignerDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.DESIGNER_TABLE_NAME);

        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

        table.addIndex(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_FIRST_KEY_COLUMN);

        //Create the table
        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creation of WalletSkin table
     * @param database
     * @param ownerId
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    private void createWalletSkinDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME);

        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_URL_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_SIZE_COLUMN_NAME, DatabaseDataType.INTEGER, 12, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ISDEFAULT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

        table.addIndex(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_FIRST_KEY_COLUMN);

        //Create the table
        ((DatabaseFactory) database).createTable(ownerId, table);
    }
}
