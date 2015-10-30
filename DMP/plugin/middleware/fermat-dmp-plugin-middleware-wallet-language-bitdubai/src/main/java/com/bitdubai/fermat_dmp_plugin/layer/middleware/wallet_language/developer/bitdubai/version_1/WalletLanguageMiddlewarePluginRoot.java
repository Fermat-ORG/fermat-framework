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

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.enums.LanguageState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCloseWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantUpdateLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCopyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantCreateEmptyWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantDeleteWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetWalletLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantSaveLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces.WalletLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces.WalletLanguageManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.database.WalletLanguageMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.database.WalletLanguageMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.exceptions.CantInitializeWalletLanguageMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.structure.WalletLanguageMiddlewareWalletLanguage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manage the Wallet Language Entities that are created by a Translator.
 * Keeps a registry of the Wallet Languages in the device.
 * <p/>
 * Each Wallet Language can have different versions to export in a format readable by the wallet publisher.
 * <p/>
 * You can create a wallet language since nothing.
 * You can clone an existing wallet language
 * <p/>
 * Can delivery the list of the wallet languages associated to the current logged translator..
 * <p/>
 * <p/>
 * Created by Leon Acosta on 29/07/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletLanguageMiddlewarePluginRoot implements DatabaseManagerForDevelopers, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, WalletLanguageManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

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

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();


    private static final String WALLET_LANGUAGES_PATH = "wallet_languages";

    private static final String WALLET_LANGUAGES_FILE_NAME_SEPARATOR = "_";

    private static final String WALLET_LANGUAGES_EXTENSION = ".xml";


    private WalletLanguageMiddlewareDao walletLanguageMiddlewareDao;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        walletLanguageMiddlewareDao = new WalletLanguageMiddlewareDao(pluginDatabaseSystem);
        try {
            walletLanguageMiddlewareDao.initializeDatabase(pluginId, pluginId.toString());
        } catch (CantInitializeWalletLanguageMiddlewareDatabaseException e) {
            this.serviceStatus = ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "", "");
        }

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



    @Override
    public List<WalletLanguage> getLanguages(String translatorPublicKey) throws CantGetWalletLanguagesException {
        try {
            return walletLanguageMiddlewareDao.findAllLanguagesByTranslator(translatorPublicKey);
        } catch (CantGetWalletLanguagesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidParameterException e) {
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            e.printStackTrace();
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public WalletLanguage getLanguageById(UUID id) throws CantGetWalletLanguageException, LanguageNotFoundException {
        try {
            return walletLanguageMiddlewareDao.findLanguageById(id);
        } catch (CantGetWalletLanguageException | LanguageNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidParameterException e) {
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            e.printStackTrace();
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * first i insert the new language in database
     * later i create the xml file
     *
     * @param name of the new WalletLanguage
     * @param type of the new WalletLanguage
     * @param translatorPublicKey like the name says
     * @return WalletLanguage instance
     * @throws CantCreateEmptyWalletLanguageException
     */
    @Override
    public WalletLanguage createEmptyLanguage(String name, Languages type, String translatorPublicKey) throws CantCreateEmptyWalletLanguageException {
        UUID languageId = UUID.randomUUID();
        LanguageState state = LanguageState.DRAFT;
        Version version = new Version("1.0.0");

        WalletLanguage walletLanguage = new WalletLanguageMiddlewareWalletLanguage(languageId, languageId, name, type, state, translatorPublicKey, version);
        try {
            Language language = new Language(name, type, new Version("1.0.0"));
            saveLanguage(language, walletLanguage);
            try {
                walletLanguageMiddlewareDao.createLanguage(walletLanguage);
                return walletLanguage;
            } catch (CantCreateEmptyWalletLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantSaveLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateEmptyWalletLanguageException(CantCreateEmptyWalletLanguageException.DEFAULT_MESSAGE, e, "Cant create language", "");
        }
    }


    @Override
    public WalletLanguage createNewVersion(WalletLanguage walletLanguage) throws CantCopyWalletLanguageException {
        UUID id = UUID.randomUUID();
        LanguageState state = LanguageState.DRAFT;

        WalletLanguage newWalletLanguage = new WalletLanguageMiddlewareWalletLanguage(id, walletLanguage.getLanguageId(), walletLanguage.getName(), walletLanguage.getType(), state, walletLanguage.getTranslatorPublicKey(), walletLanguage.getVersion());
        try {
            Language language = getLanguage(walletLanguage);
            saveLanguage(language, newWalletLanguage);
            try {
                walletLanguageMiddlewareDao.createLanguage(newWalletLanguage);
                return newWalletLanguage;
            } catch (CantCreateEmptyWalletLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCopyWalletLanguageException(CantCopyWalletLanguageException.DEFAULT_MESSAGE, e, "Cant insert new wallet language", "");
            }
        } catch (CantSaveLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCopyWalletLanguageException(CantCopyWalletLanguageException.DEFAULT_MESSAGE, e, "Cant create language", "");
        } catch (CantGetLanguageException | LanguageNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCopyWalletLanguageException(CantCopyWalletLanguageException.DEFAULT_MESSAGE, e, "Language not found", "");
        }
    }

    /**
     * first i insert the new language in database
     * later i copy the xml file
     *
     * @param newName of the walletFactoryProjectLanguage
     * @param walletLanguage you want to clone
     * @param translatorPublicKey like the name says
     * @return WalletLanguage instance
     * @throws CantCopyWalletLanguageException
     * @throws LanguageNotFoundException
     */
    @Override
    public WalletLanguage copyLanguage(String newName, WalletLanguage walletLanguage, String translatorPublicKey) throws CantCopyWalletLanguageException, LanguageNotFoundException {
        UUID id = UUID.randomUUID();
        LanguageState state = LanguageState.DRAFT;
        Version version = new Version("1.0.0");

        WalletLanguage newWalletLanguage = new WalletLanguageMiddlewareWalletLanguage(id, id, newName,  walletLanguage.getType(), state, translatorPublicKey, version);
        try {
            Language language = getLanguage(walletLanguage);
            saveLanguage(language, newWalletLanguage);
            try {
                walletLanguageMiddlewareDao.createLanguage(newWalletLanguage);
                return newWalletLanguage;
            } catch (CantCreateEmptyWalletLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCopyWalletLanguageException(CantCopyWalletLanguageException.DEFAULT_MESSAGE, e, "Cant insert new wallet language", "");
            }
        } catch (CantSaveLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCopyWalletLanguageException(CantCopyWalletLanguageException.DEFAULT_MESSAGE, e, "Cant create language", "");
        } catch (CantGetLanguageException | LanguageNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCopyWalletLanguageException(CantCopyWalletLanguageException.DEFAULT_MESSAGE, e, "Language not found", "");
        }
    }

    @Override
    public void updateLanguage(WalletLanguage walletLanguage) throws CantUpdateLanguageException, LanguageNotFoundException {
        try {
            walletLanguageMiddlewareDao.updateLanguage(walletLanguage);
        } catch (CantUpdateLanguageException | LanguageNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidParameterException e) {
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            e.printStackTrace();
        }
    }

    /**
     * first i delete the file
     * later i delete the record in database
     *
     * @param walletLanguage that you're trying to delete
     * @throws CantDeleteWalletLanguageException
     * @throws LanguageNotFoundException
     */
    @Override
    public void deleteLanguage(WalletLanguage walletLanguage) throws CantDeleteWalletLanguageException, LanguageNotFoundException {
        try {
            String languageFileName = getLanguageFileName(walletLanguage);
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, WALLET_LANGUAGES_PATH, languageFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.delete();
            try {
                walletLanguageMiddlewareDao.deleteLanguage(walletLanguage.getId());
            } catch (CantDeleteWalletLanguageException | LanguageNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantCreateFileException e){
            throw new CantDeleteWalletLanguageException(CantDeleteWalletLanguageException.DEFAULT_MESSAGE, e, "Cant delete language file", "");
        } catch (FileNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, e, "Language not found", "");
        }
    }

    @Override
    public void closeLanguage(WalletLanguage walletLanguage) throws CantCloseWalletLanguageException, LanguageNotFoundException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public Language getLanguageFromXmlString(String languageStructure) throws CantGetLanguageException {
        try {
            Language language = new Language();
            language = (Language) XMLParser.parseXML(languageStructure, language);
            return language;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, e, "Cant get language", "");
        }
    }

    @Override
    public String getLanguageXmlFromClassStructure(Language language) throws CantGetLanguageException {
        try {
            String xml = null;
            if (language != null) {
                xml = XMLParser.parseObject(language);
            }
            return xml;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, e, "Cant get language", "");
        }
    }

    /**
     * i get the path and name of the file and i load it
     *
     * @param walletLanguage of the language you're trying to get
     * @return language class structure
     * @throws CantGetLanguageException
     * @throws LanguageNotFoundException
     */
    @Override
    public Language getLanguage(WalletLanguage walletLanguage) throws CantGetLanguageException, LanguageNotFoundException {
        if (walletLanguage != null) {
            try {
                String languageFileName = getLanguageFileName(walletLanguage);
                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, WALLET_LANGUAGES_PATH, languageFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.loadFromMedia();
                String xml = pluginTextFile.getContent();
                Language language = new Language();
                language = (Language) XMLParser.parseXML(xml, language);
                return language;
            } catch (CantCreateFileException | CantLoadFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, e, "Cant get language", "");
            } catch (FileNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, e, "Language not found.", "");
            }
        } else {
            throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, null, "Wallet Language is null.", "");
        }
    }

    /**
     * first i try to get the file and update it
     * if i found it, i update it, if not, i create it.
     *
     * @param language class structure that you're trying to save
     * @param walletLanguage to wich belongs
     * @throws CantSaveLanguageException
     */
    @Override
    public void saveLanguage(Language language, WalletLanguage walletLanguage) throws CantSaveLanguageException {
        try {
            String languageXml = getLanguageXmlFromClassStructure(language);
            String languageFileName = getLanguageFileName(walletLanguage);
            try {
                PluginTextFile newFile = pluginFileSystem.getTextFile(pluginId, WALLET_LANGUAGES_PATH, languageFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                newFile.setContent(languageXml);
                newFile.persistToMedia();

            } catch (CantLoadFileException | CantPersistFileException | CantCreateFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSaveLanguageException(CantSaveLanguageException.DEFAULT_MESSAGE, e, "Can't save language xml file.", "");
            } catch (FileNotFoundException fileNotFoundException) {
                try {
                    PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, WALLET_LANGUAGES_PATH, languageFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    newFile.setContent(languageXml);
                    newFile.persistToMedia();
                } catch (CantPersistFileException | CantCreateFileException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantSaveLanguageException(CantSaveLanguageException.DEFAULT_MESSAGE, e, "Can't save language xml file.", "");
                }
            }
        } catch (CantGetLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSaveLanguageException(CantSaveLanguageException.DEFAULT_MESSAGE, e, "Can't create language xml string.", "");
        }
    }

    private String getLanguageFileName(WalletLanguage walletLanguage) {
        return walletLanguage.getLanguageId() + WALLET_LANGUAGES_FILE_NAME_SEPARATOR +
                walletLanguage.getId() + WALLET_LANGUAGES_EXTENSION;
    }


    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        WalletLanguageMiddlewareDeveloperDatabaseFactory dbFactory = new WalletLanguageMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        WalletLanguageMiddlewareDeveloperDatabaseFactory dbFactory = new WalletLanguageMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            WalletLanguageMiddlewareDeveloperDatabaseFactory dbFactory = new WalletLanguageMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Wallet Factory");
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        }
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
        List<String> returnedClasses = new ArrayList<>();
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

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
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
}
