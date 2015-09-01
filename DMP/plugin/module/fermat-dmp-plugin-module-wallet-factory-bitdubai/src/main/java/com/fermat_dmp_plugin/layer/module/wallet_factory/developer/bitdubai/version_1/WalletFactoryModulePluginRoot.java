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
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DealsWithWalletFactory;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
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
import com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 07/08/15.
 */
public class WalletFactoryModulePluginRoot implements DealsWithLogger, DealsWithWalletFactory, LogManagerForDevelopers,WalletFactoryManager, Service, Plugin {

    WalletFactoryModuleManager  walletFactoryModuleManager ;
    UUID pluginId;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithWalletFactory interface variable and implementation
     */
    WalletFactoryProjectManager walletFactoryProjectManager;
    @Override
    public void setWalletFactoryProjectManager(WalletFactoryProjectManager walletFactoryProjectManager) {
        this.walletFactoryProjectManager = walletFactoryProjectManager;
    }

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * Plugin interface implementation
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
        walletFactoryModuleManager = new WalletFactoryModuleManager(walletFactoryProjectManager);
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
    public List<WalletFactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException {
        try{
            return walletFactoryModuleManager.getAllFactoryProjects();
        } catch (Exception e){
            throw new CantGetAvailableProjectsException(CantGetAvailableProjectsException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public WalletFactoryProject createEmptyProject() throws CantCreateWalletDescriptorFactoryProjectException {
        try{
            return walletFactoryModuleManager.createEmptyProject();
        } catch (Exception e){
            throw new CantCreateWalletDescriptorFactoryProjectException(CantCreateWalletDescriptorFactoryProjectException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public void saveProject(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        try{
             walletFactoryModuleManager.saveProject(walletFactoryProject);
        } catch (Exception e){
            throw new CantSaveWalletFactoryProyect(CantSaveWalletFactoryProyect.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public void removeProject(WalletFactoryProject walletFactoryProject) throws CantRemoveWalletFactoryProject {

    }

    @Override
    public WalletFactoryProject getProject(String publicKey) throws CantGetWalletFactoryProjectException {
        try{
            return walletFactoryModuleManager.getProject(publicKey);
        } catch (Exception e){
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public List<WalletFactoryProject> getClosedProjects() throws CantGetWalletFactoryProjectException {
        try{
            return walletFactoryModuleManager.getClosedProjects();
        } catch (Exception e){
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public void closeProject(WalletFactoryProject walletFactoryProject) throws CantGetWalletFactoryProjectException {
        try{
             walletFactoryModuleManager.closeProject(walletFactoryProject);
        } catch (Exception e){
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    private void test(){
        try {
            this.getClosedProjects().size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
