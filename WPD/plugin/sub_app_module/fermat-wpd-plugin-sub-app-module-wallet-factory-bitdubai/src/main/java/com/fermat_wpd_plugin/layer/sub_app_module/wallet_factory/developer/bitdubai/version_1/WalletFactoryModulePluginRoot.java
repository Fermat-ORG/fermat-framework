package com.fermat_wpd_plugin.layer.sub_app_module.wallet_factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantCreateWalletDescriptorFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantRemoveWalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.DealsWithWalletFactory;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantCloneInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantGetAvailableProjectsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantGetInstalledWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryDeveloper;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.fermat_wpd_plugin.layer.sub_app_module.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 07/08/15.
 */
public class WalletFactoryModulePluginRoot implements DealsWithLogger, DealsWithWalletFactory, DealsWithWalletManager, LogManagerForDevelopers,WalletFactoryManager, Service, Plugin {

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
     * DealsWithWalletManager interface variable and implementation
     */
    WalletManagerManager walletManagerManager;
    @Override
    public void setWalletManagerManager(WalletManagerManager walletManagerManager) {
        this.walletManagerManager = walletManagerManager;
    }

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();

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
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
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

    @Override
    public List<InstalledWallet> getInstalledWallets() throws CantGetInstalledWalletsException {
        try {
            return walletManagerManager.getInstalledWallets();
        } catch (CantListWalletsException e) {
            throw new CantGetInstalledWalletsException(CantGetInstalledWalletsException.DEFAULT_MESSAGE, e, "there was an error listing the installed wallets from the Factory.", null);
        }
    }

    /**
     * Clones a previously installed wallet under the new assigned name.
     * @param walletToClone
     * @param newName
     * @throws CantCloneInstalledWalletException
     */
    @Override
    public void cloneInstalledWallets(InstalledWallet walletToClone, String newName) throws CantCloneInstalledWalletException {
        walletFactoryModuleManager.cloneInstalledWallets(walletToClone, newName);
    }
}
