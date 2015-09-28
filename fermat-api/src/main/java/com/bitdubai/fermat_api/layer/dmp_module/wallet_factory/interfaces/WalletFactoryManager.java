package com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces;

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
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantCloneInstalledWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableDevelopersException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableProjectsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetInstalledWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;


import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryProjectManager</code>
 * indicates the functionality of a WalletFactoryProjectManager
 * <p/>
 *
 *  Created by Matias Furszyfer
 *
 */
public interface WalletFactoryManager {

    WalletFactoryDeveloper getLoggedDeveloper();

    List<WalletFactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException;

    public WalletFactoryProject createEmptyProject()throws CantCreateWalletDescriptorFactoryProjectException;

    public void saveProject(WalletFactoryProject walletFactoryProject)throws CantSaveWalletFactoryProyect;

    public void removeProject(WalletFactoryProject walletFactoryProject) throws CantRemoveWalletFactoryProject;

    public WalletFactoryProject getProject(String publicKey)throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    public List<WalletFactoryProject> getClosedProjects() throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    public void closeProject(WalletFactoryProject walletFactoryProject)throws CantGetWalletFactoryProjectException;

    public List<com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet> getInstalledWallets() throws CantGetInstalledWalletsException;

    public void cloneInstalledWallets (InstalledWallet walletToClone, String newName) throws CantCloneInstalledWalletException;

}
