package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantCreateWalletDescriptorFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantRemoveWalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantCloneInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantGetAvailableProjectsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantGetInstalledWalletsException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryProjectManager</code>
 * indicates the functionality of a WalletFactoryProjectManager
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
 */
public interface WalletFactoryManager extends ModuleManager {

    WalletFactoryDeveloper getLoggedDeveloper();

    List<WalletFactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException;

    WalletFactoryProject createEmptyProject() throws CantCreateWalletDescriptorFactoryProjectException;

    void saveProject(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect;

    void removeProject(WalletFactoryProject walletFactoryProject) throws CantRemoveWalletFactoryProject;

    WalletFactoryProject getProject(String publicKey) throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    List<WalletFactoryProject> getClosedProjects() throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    void closeProject(WalletFactoryProject walletFactoryProject) throws CantGetWalletFactoryProjectException;

    List<InstalledWallet> getInstalledWallets() throws CantGetInstalledWalletsException;

    void cloneInstalledWallets(InstalledWallet walletToClone, String newName) throws CantCloneInstalledWalletException;

}
