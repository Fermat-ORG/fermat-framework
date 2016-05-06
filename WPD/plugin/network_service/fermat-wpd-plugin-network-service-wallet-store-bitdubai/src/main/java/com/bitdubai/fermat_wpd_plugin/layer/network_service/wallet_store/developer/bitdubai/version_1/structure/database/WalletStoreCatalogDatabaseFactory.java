package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by rodrigo on 7/22/15.
 */
public class WalletStoreCatalogDatabaseFactory implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem{

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
    public WalletStoreCatalogDatabaseFactory(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem) {
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreCatalogDatabaseConstants.DESIGNER_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreCatalogDatabaseConstants.TRANSLATOR_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "TableName: " + WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME.toString(), "Exception not handled by the plugin, There is a problem and i cannot create the table.");
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
        //DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME);

        table.addColumn(WalletStoreCatalogDatabaseConstants.DEVELOPER_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);

        table.addIndex(WalletStoreCatalogDatabaseConstants.DEVELOPER_FIRST_KEY_COLUMN);

        //Create the table
        databaseFactory.createTable(ownerId, table);
//        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creates the Item table
     * @param database
     * @param ownerId
     */
    private void createItemDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        //DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME);

        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_SIZE_COLUMN_NAME, DatabaseDataType.INTEGER, 12, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.ITEM_PUBLISHER_WEB_SITE_URL_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

        table.addIndex(WalletStoreCatalogDatabaseConstants.ITEM_FIRST_KEY_COLUMN);

        //Create the table
        databaseFactory.createTable(ownerId, table);
//        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creates Translator table
     * @param database
     * @param ownerId
     */
    private void createTranslatorDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
       // DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.TRANSLATOR_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.TRANSLATOR_TABLE_NAME);

        table.addColumn(WalletStoreCatalogDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.TRANSLATOR_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.TRANSLATOR_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);

        table.addIndex(WalletStoreCatalogDatabaseConstants.TRANSLATOR_FIRST_KEY_COLUMN);

        //Create the table
        databaseFactory.createTable(ownerId, table);
//        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creation of Wallet Language table
     * @param database
     * @param ownerId
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    private void createWalletLanguageDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
       // DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME);

        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME, DatabaseDataType.INTEGER, 12, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

        table.addIndex(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_FIRST_KEY_COLUMN);

        //Create the table
        databaseFactory.createTable(ownerId, table);
//        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creation of the Designer table
     * @param database
     * @param ownerId
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    private void createDesignerDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        //DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.DESIGNER_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.DESIGNER_TABLE_NAME);

        table.addColumn(WalletStoreCatalogDatabaseConstants.DESIGNER_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.DESIGNER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.DESIGNER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);

        table.addIndex(WalletStoreCatalogDatabaseConstants.DESIGNER_FIRST_KEY_COLUMN);

        //Create the table
        databaseFactory.createTable(ownerId, table);
//        ((DatabaseFactory) database).createTable(ownerId, table);
    }

    /**
     * Creation of WalletSkin table
     * @param database
     * @param ownerId
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    private void createWalletSkinDatabaseTable(Database database, UUID ownerId) throws InvalidOwnerIdException, CantCreateTableException {
        //DatabaseTableFactory table;

        /**
         * Create Wallet Contact Address Book table.
         */
        DatabaseTableFactory table;
        DatabaseFactory databaseFactory = database.getDatabaseFactory();

        table = databaseFactory.newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME);
//        table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME);

        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME, DatabaseDataType.STRING, 8, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SIZE_COLUMN_NAME, DatabaseDataType.INTEGER, 12, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ISDEFAULT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
        //New column on 06/08/2015
        table.addColumn(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SCREEN_SIZE, DatabaseDataType.STRING,10,Boolean.FALSE);

        table.addIndex(WalletStoreCatalogDatabaseConstants.WALLETSKIN_FIRST_KEY_COLUMN);

        //Create the table
        databaseFactory.createTable(ownerId, table);
//        ((DatabaseFactory) database).createTable(ownerId, table);
    }
}
