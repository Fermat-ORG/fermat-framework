package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantAddLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantChangeVersionCompatibilityException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCopyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCreateEmptyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantDeleteLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantDeleteWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantSaveLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantUpdateLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces.WalletLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces.WalletLanguageManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.database.WalletLanguageMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.structure.WalletLanguageMiddlewareWalletLanguage;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manage the Wallet Language Entities that are created by a Translator.
 * Keeps a registry of the Wallet Languages in the device.
 *
 * Each Wallet Language can have different versions to export in a format readable by the wallet publisher.
 *
 * You can create a wallet language since nothing.
 * You can clone an existing wallet language
 *
 * Can delivery the list of the wallet languages associated to the current logged translator..
 *
 * <p>
 * Created by Leon Acosta on 29/07/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletLanguageMiddlewarePluginRoot implements DatabaseManagerForDevelopers, DealsWithErrors, DealsWithLogger, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, WalletLanguageManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */

    PluginFileSystem pluginFileSystem;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    private static final String WALLET_FACTORY_LANGUAGES_PATH = "wallet_languages";

    private WalletLanguageMiddlewareDao walletLanguageMiddlewareDao;

    @Override
    public List<WalletLanguage> getLanguages(String translatorPublicKey) throws CantGetWalletLanguagesException {
        // TODO GET LOGGED TRANSLATOR KEY
        try {
            return walletLanguageMiddlewareDao.findAllLanguagesByTranslator(translatorPublicKey);
        } catch (CantGetWalletLanguagesException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletLanguage getLanguageById(UUID id) throws CantGetWalletLanguageException, LanguageNotFoundException {
        try {
            return walletLanguageMiddlewareDao.findLanguageById(id);
        } catch (CantGetWalletLanguageException | LanguageNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletLanguage createEmptyLanguage(String name, Languages type, String translatorPublicKey) throws CantCreateEmptyWalletLanguageException {
       /* try {
            WalletLanguageMiddlewareWalletLanguage walletFactoryProjectLanguage = new WalletLanguageMiddlewareWalletLanguage();
            Language language = new Language(name, type, new Version("1.0.0"));
           // setLanguageXml(language, walletFactoryProjectLanguage);
            try {
               // walletFactoryMiddlewareProjectDao.createLanguage(walletFactoryProjectLanguage, walletFactoryProjectProposal);
                return walletFactoryProjectLanguage;
            } catch (CantCreateEmptyWalletLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantSaveLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateEmptyWalletLanguageException(CantCreateEmptyWalletLanguageException.DEFAULT_MESSAGE, e, "Cant create language", "");
        }*/
        return null;
    }

    @Override
    public WalletLanguage createNewVersion(String alias, WalletLanguage walletLanguage) throws CantCopyWalletLanguageException, LanguageNotFoundException {
        return null;
    }

    @Override
    public WalletLanguage copyLanguage(String newName, WalletLanguage walletLanguage, String translatorPublicKey) throws CantCopyWalletLanguageException, LanguageNotFoundException {
        return null;
    }

    @Override
    public WalletLanguage changeVersionCompatibility(VersionCompatibility versionCompatibility, WalletLanguage walletLanguage) throws CantChangeVersionCompatibilityException, LanguageNotFoundException {
        return null;
    }

    @Override
    public void deleteLanguage(WalletLanguage walletLanguage) throws CantDeleteWalletLanguageException, LanguageNotFoundException {

    }

    @Override
    public Language getLanguageFromXmlString(String languageStructure) throws CantGetLanguageException {
        return null;
    }

    @Override
    public String getLanguageXmlFromClassStructure(Language language) throws CantGetLanguageException {
        return null;
    }

    @Override
    public Language getLanguage(WalletLanguage walletLanguage) throws CantGetLanguageException {
        return null;
    }

    @Override
    public void saveLanguage(Language language, WalletLanguage walletLanguage) throws CantSaveLanguageException {

    }

    @Override
    public void addLanguageString(String name, String value, WalletLanguage walletLanguage) throws CantAddLanguageStringException {

    }

    @Override
    public void updateLanguageString(String name, String value, WalletLanguage walletLanguage) throws CantUpdateLanguageStringException {

    }

    @Override
    public void deleteLanguageString(String name, WalletLanguage walletLanguage) throws CantDeleteLanguageStringException {

    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Plugin methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }


    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.WalletSkinMiddlewarePluginRoot");
        /**
         * I return the values.
         */
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
            if (WalletLanguageMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletLanguageMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletLanguageMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletLanguageMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }
}
