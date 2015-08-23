package com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletDescriptorFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantRemoveWalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableDevelopersException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableProjectsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.FactoryProject;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryDeveloper;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 07/08/15.
 */
public class WalletFactoryModulePluginRoot implements Service, DealsWithEvents, DealsWithErrors,DealsWithLogger, DealsWithPluginFileSystem,LogManagerForDevelopers,Plugin,WalletFactoryManager {


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

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

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }



    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
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
        returnedClasses.add("com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.WalletFactoryModulePluginRoot");
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
            if (WalletFactoryModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletFactoryModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletFactoryModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletFactoryModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    @Override
    public WalletFactoryDeveloper getLoggedDeveloper() {
        return null;
    }

    @Override
    public List<WalletFactoryDeveloper> getAvailableDevelopers() throws CantGetAvailableDevelopersException {
        return null;
    }

    @Override
    public List<FactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException {
        return null;
    }

    @Override
    public FactoryProject createEmptyProject(String name, WalletCategory walletCategory, WalletType walletType) throws CantCreateWalletDescriptorFactoryProjectException {
        return null;
    }


    @Override
    public void saveProject(FactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {

    }

    @Override
    public void removeyProject(FactoryProject walletFactoryProject) throws CantRemoveWalletFactoryProject {

    }



    @Override
    public void importProjectFromRepository(String newName, String repository) throws CantImportWalletFactoryProjectException {

    }

    @Override
    public FactoryProject getProject(String name) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
        return null;
    }

    @Override
    public List<FactoryProject> getClosedProjects(WalletFactoryProjectState state) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
        return null;
    }

    @Override
    public FactoryProject closeProject(String name) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
        return null;
    }


    @Override
    public Skin getSkin(UUID skinId) throws CantGetSkinException {
        return null;
    }

    @Override
    public Language getLanguage(UUID languageId) throws CantGetLanguageException {
        return null;
    }
}
