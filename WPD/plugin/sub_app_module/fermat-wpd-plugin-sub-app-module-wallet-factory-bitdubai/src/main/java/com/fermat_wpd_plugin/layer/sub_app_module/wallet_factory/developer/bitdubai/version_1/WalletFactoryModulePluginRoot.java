package com.fermat_wpd_plugin.layer.sub_app_module.wallet_factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantCreateWalletDescriptorFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantRemoveWalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantCloneInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantGetAvailableProjectsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantGetInstalledWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryDeveloper;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;
import com.fermat_wpd_plugin.layer.sub_app_module.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryModuleManager;

import java.util.List;

/**
 * Created by Matias Furszyfer on 07/08/15.
 */
public class WalletFactoryModulePluginRoot extends AbstractPlugin implements
        WalletFactoryManager {

    @Override
    public SettingsManager getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION   , layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_FACTORY         )
    private WalletFactoryProjectManager walletFactoryProjectManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM   , layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER         )
    private WalletManagerManager walletManagerManager;

    // TODO MAKE USE OF THE ERROR MANAGER.


    WalletFactoryModuleManager  walletFactoryModuleManager;

    public WalletFactoryModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
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
