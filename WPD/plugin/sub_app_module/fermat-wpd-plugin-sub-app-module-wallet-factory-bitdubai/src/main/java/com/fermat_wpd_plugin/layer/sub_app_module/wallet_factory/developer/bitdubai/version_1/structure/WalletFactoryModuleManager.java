package com.fermat_wpd_plugin.layer.sub_app_module.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantCloneInstalledWalletException;

import java.util.List;

/**
 * Created by rodrigo on 8/23/15.
 */
public class WalletFactoryModuleManager {

    WalletFactoryProjectManager walletFactoryProjectManager;

    /**
     * constructor
     * @param walletFactoryProjectManager
     */
    public WalletFactoryModuleManager(WalletFactoryProjectManager walletFactoryProjectManager) {
        this.walletFactoryProjectManager = walletFactoryProjectManager;
    }

    public List<WalletFactoryProject> getAllFactoryProjects() throws CantGetWalletFactoryProjectException {
        return walletFactoryProjectManager.getAllWalletFactoryProjects();
    }

    public WalletFactoryProject getProject(String publicKey) throws CantGetWalletFactoryProjectException {
        return walletFactoryProjectManager.getWalletFactoryProjectByPublicKey(publicKey);
    }

    public WalletFactoryProject createEmptyProject() throws CantCreateWalletFactoryProjectException {
        return walletFactoryProjectManager.createEmptyWalletFactoryProject();
    }

    public void saveProject (WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        walletFactoryProjectManager.saveWalletFactoryProjectChanges(walletFactoryProject);
    }

    public List<WalletFactoryProject> getClosedProjects () throws CantGetWalletFactoryProjectException {
        return walletFactoryProjectManager.getWalletFactoryProjectByState(WalletFactoryProjectState.CLOSED);
    }

    public void closeProject(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        walletFactoryProject.setProjectState(WalletFactoryProjectState.CLOSED);
        walletFactoryProjectManager.saveWalletFactoryProjectChanges(walletFactoryProject);
    }

    public void cloneInstalledWallets(InstalledWallet walletToClone, String newName) throws CantCloneInstalledWalletException {
        walletFactoryProjectManager.cloneInstalledWallet(walletToClone, newName);
    }

}
