package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.enums.SkinState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantAddResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantCloseWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantCopyWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantCreateEmptyWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantDeleteResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantDeleteWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantGetWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantGetWalletSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantListWalletSkinsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantSaveWalletSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantUpdateResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.ResourceAlreadyExistsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.ResourceNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.SkinNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces.WalletSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces.WalletSkinManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database.WalletSkinMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database.WalletSkinMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.exceptions.CantInitializeWalletSkinMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.structure.WalletSkinMiddlewareWalletSkin;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 * <p/>
 * Created by Leon Acosta on 29-07-2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletSkinMiddlewarePluginRoot implements DatabaseManagerForDevelopers, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, WalletSkinManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
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
    Map<String, Resource> lstResources;
    Map<String, Layout> lstPortraitLayouts;
    Map<String, Layout> lstLandscapeLayouts;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();


    private WalletSkinMiddlewareDao walletSkinMiddlewareDao;

    private static final String WALLET_SKINS_PATH = "wallet_skins";
    private static final String SKIN_MANIFEST_FILE_NAME = "manifest.xml";

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        walletSkinMiddlewareDao = new WalletSkinMiddlewareDao(pluginDatabaseSystem);
        try {
            walletSkinMiddlewareDao.initializeDatabase(pluginId, pluginId.toString());
        } catch (CantInitializeWalletSkinMiddlewareDatabaseException e) {
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
    public List<WalletSkin> listSkins(String designerPublicKey) throws CantListWalletSkinsException {
        try {
            return walletSkinMiddlewareDao.findAllSkinsByDesigner(designerPublicKey);
        } catch (CantListWalletSkinsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public WalletSkin getSkinBySkinIdAndVersion(UUID skinId, Version version) throws CantGetWalletSkinException, SkinNotFoundException {
        try {
            return walletSkinMiddlewareDao.findSkinBySkinIdAndVersion(skinId, version);
        } catch (CantGetWalletSkinException | SkinNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public WalletSkin getSkinById(UUID id) throws CantGetWalletSkinException, SkinNotFoundException {
        try {
            return walletSkinMiddlewareDao.findSkinById(id);
        } catch (CantGetWalletSkinException | SkinNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public List<WalletSkin> getSkinsBySkinId(UUID skinId) throws CantListWalletSkinsException {
        try {
            return walletSkinMiddlewareDao.findAllSkinsBySkinId(skinId);
        } catch (CantListWalletSkinsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public WalletSkin createEmptySkin(String name, String designerPublicKey) throws CantCreateEmptyWalletSkinException {
        UUID skinId = UUID.randomUUID();
        SkinState state = SkinState.DRAFT;
        Version version = new Version("1.0.0");

        try {
            VersionCompatibility versionCompatibility = new VersionCompatibility(version, version);
            WalletSkin walletSkin = new WalletSkinMiddlewareWalletSkin(pluginId, skinId, name, "alias", WALLET_SKINS_PATH, state, designerPublicKey, version, versionCompatibility);

            Skin skin = new Skin(walletSkin.getId(), walletSkin.getName(), walletSkin.getVersion(), walletSkin.getVersionCompatibility(),lstResources, lstPortraitLayouts, lstLandscapeLayouts);
//            Skin skin = new Skin(name, type, new Version("1.0.0"));
            saveSkinStructureXml(skin, walletSkin);
            try {
                walletSkinMiddlewareDao.createSkin(walletSkin);
                return walletSkin;
            } catch (CantCreateEmptyWalletSkinException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantSaveWalletSkinStructureException | InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateEmptyWalletSkinException(CantCreateEmptyWalletSkinException.DEFAULT_MESSAGE, e, "Cant create Skin", "");
        }
    }

    @Override
    public WalletSkin copySkin(String newName, String designerPublicKey, WalletSkin walletSkin) throws CantCopyWalletSkinException, SkinNotFoundException {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public WalletSkin createNewVersion(String alias, WalletSkin walletSkin) throws CantCopyWalletSkinException {
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public void closeSkin(WalletSkin walletSkin) throws CantCloseWalletSkinException, SkinNotFoundException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public void deleteSkin(WalletSkin walletSkin) throws CantDeleteWalletSkinException, SkinNotFoundException {
        try {
            String skinManifestPath = getSkinManifestPath(walletSkin);
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, skinManifestPath, SKIN_MANIFEST_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.delete();
            // TODO delete skin structure and resources too
            try {
                walletSkinMiddlewareDao.deleteSkin(walletSkin.getId());
            } catch (CantDeleteWalletSkinException | SkinNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantCreateFileException e) {
            throw new CantDeleteWalletSkinException(CantDeleteWalletSkinException.DEFAULT_MESSAGE, e, "Cant delete skin file", "");
        } catch (FileNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteWalletSkinException(CantDeleteWalletSkinException.DEFAULT_MESSAGE, e, "skin not found", "");
        }
    }

    @Override
    public Skin getSkinStructure(WalletSkin walletSkin) throws CantGetWalletSkinStructureException, SkinNotFoundException {
        if (walletSkin != null) {
            try {
                String skinManifestPath = getSkinManifestPath(walletSkin);
                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, skinManifestPath, SKIN_MANIFEST_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.loadFromMedia();
                String xml = pluginTextFile.getContent();
                Skin skin = new Skin();
                skin = (Skin) XMLParser.parseXML(xml, skin);
                return skin;
            } catch (CantCreateFileException | CantLoadFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetWalletSkinStructureException(CantGetWalletSkinStructureException.DEFAULT_MESSAGE, e, "Cant get Skin", "");
            } catch (FileNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new SkinNotFoundException(SkinNotFoundException.DEFAULT_MESSAGE, e, "Skin not found.", "");
            }
        } else {
            throw new CantGetWalletSkinStructureException(CantGetWalletSkinStructureException.DEFAULT_MESSAGE, null, "Wallet Skin is null.", "");
        }
    }

    @Override
    public Skin getSkinFromXmlString(String skinStructure) throws CantGetWalletSkinStructureException {
        try {
            Skin skin = new Skin();
            skin = (Skin) XMLParser.parseXML(skinStructure, skin);
            return skin;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletSkinStructureException(CantGetWalletSkinStructureException.DEFAULT_MESSAGE, e, "Cant get skin", "");
        }
    }

    @Override
    public String getSkinXmlFromClassStructure(Skin skin) throws CantGetWalletSkinStructureException {
        try {
            String xml = null;
            if (skin != null) {
                xml = XMLParser.parseObject(skin);
            }
            return xml;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetWalletSkinStructureException(CantGetWalletSkinStructureException.DEFAULT_MESSAGE, e, "Cant get skin", "");
        }
    }

    @Override
    public void saveSkinStructureXml(Skin skin, WalletSkin walletSkin) throws CantSaveWalletSkinStructureException {
        try {
            String skinXml = getSkinXmlFromClassStructure(skin);
            String skinManifestPath = getSkinManifestPath(walletSkin);
            try {
                PluginTextFile newFile = pluginFileSystem.getTextFile(pluginId, skinManifestPath, SKIN_MANIFEST_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                newFile.setContent(skinXml);
                newFile.persistToMedia();

            } catch (CantLoadFileException | CantPersistFileException | CantCreateFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSaveWalletSkinStructureException(CantSaveWalletSkinStructureException.DEFAULT_MESSAGE, e, "Can't save skin xml file.", "");
            } catch (FileNotFoundException fileNotFoundException) {
                try {
                    PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, skinManifestPath, SKIN_MANIFEST_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    newFile.setContent(skinXml);
                    newFile.persistToMedia();
                } catch (CantPersistFileException | CantCreateFileException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantSaveWalletSkinStructureException(CantSaveWalletSkinStructureException.DEFAULT_MESSAGE, e, "Can't save skin xml file.", "");
                }
            }
        } catch (CantGetWalletSkinStructureException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSaveWalletSkinStructureException(CantSaveWalletSkinStructureException.DEFAULT_MESSAGE, e, "Can't create skin xml string.", "");
        }
    }

    private String getSkinManifestPath(WalletSkin walletSkin) {
        return WALLET_SKINS_PATH + "/" +
                walletSkin.getId();
    }

    private String createResourcePath(ResourceType resourceType, String path) {
        return path + "/" + resourceType.value();
    }

    @Override
    public void addResource(Resource resource, byte[] file, WalletSkin walletSkin) throws CantAddResourceException, ResourceAlreadyExistsException {
        try {
            Skin skin = getSkinStructure(walletSkin);
            //skin.addResource(resource);
            try {
                // TODO CHECK IF FILE ALREADY EXIST THROW EXCEPTION ResourceAlreadyExistsException
                String resourcePath = createResourcePath(resource.getResourceType(), SKIN_MANIFEST_FILE_NAME);

                PluginBinaryFile newFile = pluginFileSystem.createBinaryFile(pluginId, resourcePath, resource.getFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                newFile.setContent(file);
                newFile.persistToMedia();
                try {
                    saveSkinStructureXml(skin, walletSkin);
                } catch (CantSaveWalletSkinStructureException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantAddResourceException(CantAddResourceException.DEFAULT_MESSAGE, e, "Can't save xml file", "");
                }
            } catch (CantCreateFileException | CantLoadFileException | CantPersistFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantAddResourceException(CantAddResourceException.DEFAULT_MESSAGE, e, "Can't Create/Load/Persistit file to the structure.", "");
            }
        } catch (CantGetWalletSkinStructureException | SkinNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_SKIN_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAddResourceException(CantAddResourceException.DEFAULT_MESSAGE, e, "Can't get Structure Skin xml file", "");
        }
    }

    @Override
    public void updateResource(Resource resource, byte[] file, WalletSkin walletSkin) throws CantUpdateResourceException, ResourceNotFoundException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }

    @Override
    public void deleteResource(Resource resource, WalletSkin walletSkin) throws CantDeleteResourceException, ResourceNotFoundException {
        //TODO METODO NO IMPLEMENTADO AUN - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
    }


    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        WalletSkinMiddlewareDeveloperDatabaseFactory dbFactory = new WalletSkinMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        WalletSkinMiddlewareDeveloperDatabaseFactory dbFactory = new WalletSkinMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            WalletSkinMiddlewareDeveloperDatabaseFactory dbFactory = new WalletSkinMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Wallet Skin");
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
            if (WalletSkinMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletSkinMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletSkinMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletSkinMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
