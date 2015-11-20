package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantCreateWalletDescriptorFactoryProjectException ;
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
 *
 *  Created by Matias Furszyfer
 *
 */
public interface WalletFactoryManager extends ModuleManager {

    WalletFactoryDeveloper getLoggedDeveloper();

    List<WalletFactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException;

    public WalletFactoryProject createEmptyProject()throws CantCreateWalletDescriptorFactoryProjectException;

    public void saveProject(WalletFactoryProject walletFactoryProject)throws CantSaveWalletFactoryProyect;

    public void removeProject(WalletFactoryProject walletFactoryProject) throws CantRemoveWalletFactoryProject;

    public WalletFactoryProject getProject(String publicKey)throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    public List<WalletFactoryProject> getClosedProjects() throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    public void closeProject(WalletFactoryProject walletFactoryProject)throws CantGetWalletFactoryProjectException;

    public List<InstalledWallet> getInstalledWallets() throws CantGetInstalledWalletsException;

    public void cloneInstalledWallets (InstalledWallet walletToClone, String newName) throws CantCloneInstalledWalletException;

}
