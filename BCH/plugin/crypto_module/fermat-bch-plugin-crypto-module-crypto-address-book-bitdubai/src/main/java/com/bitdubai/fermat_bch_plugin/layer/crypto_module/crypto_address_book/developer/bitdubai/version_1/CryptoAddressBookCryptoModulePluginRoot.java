package com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantListCryptoAddressBookRecordsException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressBookCryptoModuleDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.InvalidCryptoAddressBookRecordParametersException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.structure.CryptoAddressBookCryptoModuleRecord;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_bch_api.layer.crypto_module.Crypto;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This Plug-in has the responsibility to manage the relationship between crypto addresses and its:
 * - Delivered By Actor
 * - Delivered To Actor
 * - Wallet
 * - Vault
 * - Platform
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 02/09/2015.
 */
public class CryptoAddressBookCryptoModulePluginRoot extends AbstractPlugin implements
        Crypto,
        CryptoAddressBookManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER           )
    private LogManager logManager;


    public CryptoAddressBookCryptoModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * CryptoAddressBook Interface member variables.
     */
    private CryptoAddressBookCryptoModuleDao cryptoAddressBookCryptoModuleDao;

    /**
     * Crypto Address Book Manager implementation.
     */
    @Override
    public CryptoAddressBookRecord getCryptoAddressBookRecordByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetCryptoAddressBookRecordException, CryptoAddressBookRecordNotFoundException {
        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Trying to get an specific Crypto Addresses Book...", null, null);

        try {
            return cryptoAddressBookCryptoModuleDao.getCryptoAddressBookRecordByCryptoAddress(cryptoAddress);
        } catch (CantGetCryptoAddressBookRecordException | CryptoAddressBookRecordNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }
    }

    @Override
    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByWalletPublicKey(String walletPublicKey) throws CantListCryptoAddressBookRecordsException {
        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Listing Crypto Addresses Book...", null, null);

        try {
            return cryptoAddressBookCryptoModuleDao.listCryptoAddressBookRecordsByWalletPublicKey(walletPublicKey);
        } catch (CantListCryptoAddressBookRecordsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }
    }

    @Override
    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredByActorPublicKey(String deliveredByActorPublicKey) throws CantListCryptoAddressBookRecordsException {
        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Listing Crypto Addresses Book...", null, null);

        try {
            return cryptoAddressBookCryptoModuleDao.listCryptoAddressBookRecordsByDeliveredByActorPublicKey(deliveredByActorPublicKey);
        } catch (CantListCryptoAddressBookRecordsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }
    }

    @Override
    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredToActorPublicKey(String deliveredToActorPublicKey) throws CantListCryptoAddressBookRecordsException {
        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Listing Crypto Addresses Book...", null, null);

        try {
            return cryptoAddressBookCryptoModuleDao.listCryptoAddressBookRecordsByDeliveredToActorPublicKey(deliveredToActorPublicKey);
        } catch (CantListCryptoAddressBookRecordsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListCryptoAddressBookRecordsException(CantListCryptoAddressBookRecordsException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }
    }

    @Override
    public void registerCryptoAddress(CryptoAddress cryptoAddress,
                                      String deliveredByActorPublicKey,
                                      Actors deliveredByType,
                                      String deliveredToActorPublicKey,
                                      Actors deliveredToType,
                                      Platforms platform,
                                      VaultType vaultType,
                                      String vaultIdentifier,
                                      String walletPublicKey,
                                      ReferenceWallet walletType) throws CantRegisterCryptoAddressBookRecordException {
        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Registering Crypto Address...", null, null);

        try {
            CryptoAddressBookRecord cryptoAddressBookRecord = new CryptoAddressBookCryptoModuleRecord(
                    cryptoAddress,
                    deliveredByActorPublicKey,
                    deliveredByType,
                    deliveredToActorPublicKey,
                    deliveredToType,
                    platform,
                    vaultType,
                    vaultIdentifier,
                    walletPublicKey,
                    walletType
            );
            cryptoAddressBookCryptoModuleDao.registerCryptoAddress(cryptoAddressBookRecord);
        } catch (CantRegisterCryptoAddressBookRecordException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidCryptoAddressBookRecordParametersException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterCryptoAddressBookRecordException(CantRegisterCryptoAddressBookRecordException.DEFAULT_MESSAGE, e, "You must to send all params.", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterCryptoAddressBookRecordException(CantRegisterCryptoAddressBookRecordException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }

        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Register Crypto Address Book Successfully.", null, null);
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Crypto Address Book Plugin Initializing...", null, null);

        try {
            cryptoAddressBookCryptoModuleDao = new CryptoAddressBookCryptoModuleDao(pluginDatabaseSystem, pluginId);
            cryptoAddressBookCryptoModuleDao.initialize();
        } catch (CantInitializeCryptoAddressBookCryptoModuleDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "There is a problem when trying to initialize CryptoAddressBook DAO", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "There is a problem I can't identify.", null);
        }

        logManager.log(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Crypto Address Book Plugin Successfully initialized...", null, null);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        cryptoAddressBookCryptoModuleDao = null;
        this.serviceStatus = ServiceStatus.STOPPED;
    }


    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CryptoAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new CryptoAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        try {
            return dbFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    /**
     * returns the list of tables for the given database
     *
     * @param developerObjectFactory instance
     * @param developerDatabase      of
     * @return a list of instances of DeveloperDatabaseTable
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        try {
            CryptoAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new CryptoAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            return dbFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            return new ArrayList<>();
        }
    }

    /**
     * returns the list of records for the passed table
     *
     * @param developerObjectFactory instance
     * @param developerDatabase      of
     * @param developerDatabaseTable of
     * @return a list of instances of DeveloperDatabaseTableRecord
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        CryptoAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new CryptoAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_ADDRESS_BOOK, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseTableRecordList;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("CryptoAddressBookCryptoModulePluginRoot");
//        returnedClasses.add("com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDao");
//        returnedClasses.add("com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseFactory");
//        returnedClasses.add("com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDeveloperDatabaseFactory");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (CryptoAddressBookCryptoModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoAddressBookCryptoModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoAddressBookCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoAddressBookCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className we are looking for
     * @return the log level
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split(Pattern.quote("$"));
            return CryptoAddressBookCryptoModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            return DEFAULT_LOG_LEVEL;
        }
    }

    /**
     * Gets the list of CryptoAddresses registered to an specific actor type
     * @param actorType
     * @return
     * @throws CantRegisterCryptoAddressBookRecordException
     */
    @Override
    public List<CryptoAddressBookRecord> listCryptoAddressBookRecordsByDeliveredToActorType(Actors actorType) throws CantRegisterCryptoAddressBookRecordException {
        try {
            return cryptoAddressBookCryptoModuleDao.listCryptoAddressBookRecordsByDeliveredToActorType(actorType);
        } catch (CantListCryptoAddressBookRecordsException e) {
            throw new CantRegisterCryptoAddressBookRecordException(CantRegisterCryptoAddressBookRecordException.DEFAULT_MESSAGE, e, "Can't get list of cryptpo addresses from database", "database issue");
        }
    }
}