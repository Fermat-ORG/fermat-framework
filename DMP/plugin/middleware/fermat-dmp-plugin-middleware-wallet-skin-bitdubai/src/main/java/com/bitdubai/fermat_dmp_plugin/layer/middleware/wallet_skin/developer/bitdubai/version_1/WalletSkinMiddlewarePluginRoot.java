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

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database.WalletSkinMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database.WalletSkinMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.exceptions.CantInitializeWalletSkinMiddlewareDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

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
 *
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


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    private WalletSkinMiddlewareDao walletSkinMiddlewareDao;

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
    public void pause(){
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume(){
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop(){
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletSkin getSkinBySkinIdAndVersion(UUID skinId, Version version) throws CantGetWalletSkinException, SkinNotFoundException {
        try {
            return walletSkinMiddlewareDao.findSkinBySkinIdAndVersion(skinId, version);
        } catch (CantGetWalletSkinException|SkinNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletSkin getSkinById(UUID id) throws CantGetWalletSkinException, SkinNotFoundException {
        try {
            return walletSkinMiddlewareDao.findSkinById(id);
        } catch (CantGetWalletSkinException|SkinNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<WalletSkin> getSkinsBySkinId(UUID skinId) throws CantListWalletSkinsException {
        try {
            return walletSkinMiddlewareDao.findAllSkinsBySkinId(skinId);
        } catch (CantListWalletSkinsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_LANGUAGE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletSkin createEmptySkin(String name, String designerPublicKey) throws CantCreateEmptyWalletSkinException {
        return null;
    }

    @Override
    public WalletSkin copySkin(String newName, String designerPublicKey, WalletSkin walletSkin) throws CantCopyWalletSkinException, SkinNotFoundException {
        return null;
    }

    @Override
    public WalletSkin createNewVersion(String alias, WalletSkin walletSkin) throws CantCopyWalletSkinException {
        return null;
    }

    @Override
    public void closeSkin(WalletSkin walletSkin) throws CantCloseWalletSkinException, SkinNotFoundException {

    }

    @Override
    public void deleteSkin(WalletSkin walletSkin) throws CantDeleteWalletSkinException, SkinNotFoundException {

    }

    @Override
    public Skin getSkinStructure(WalletSkin walletSkin) throws CantGetWalletSkinStructureException {
        return null;
    }

    @Override
    public Skin getSkinFromXmlString(String skinStructure) throws CantGetWalletSkinStructureException {
        return null;
    }

    @Override
    public String getSkinXmlFromClassStructure(Skin skin) throws CantGetWalletSkinStructureException {
        return null;
    }

    @Override
    public void saveSkinStructureXml(Skin skin, WalletSkin walletSkin) throws CantSaveWalletSkinStructureException {

    }

    @Override
    public void addResource(Resource resource, byte[] file, WalletSkin walletSkin) throws CantAddResourceException, ResourceAlreadyExistsException {

    }

    @Override
    public void updateResource(Resource resource, byte[] file, WalletSkin walletSkin) throws CantUpdateResourceException, ResourceNotFoundException {

    }

    @Override
    public void deleteResource(Resource resource, WalletSkin walletSkin) throws CantDeleteResourceException, ResourceNotFoundException {

    }


    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory){
        WalletSkinMiddlewareDeveloperDatabaseFactory dbFactory = new WalletSkinMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase){
        WalletSkinMiddlewareDeveloperDatabaseFactory dbFactory = new WalletSkinMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable){
        try {
            WalletSkinMiddlewareDeveloperDatabaseFactory dbFactory = new WalletSkinMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Wallet Skin");
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
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem){
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem){
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
