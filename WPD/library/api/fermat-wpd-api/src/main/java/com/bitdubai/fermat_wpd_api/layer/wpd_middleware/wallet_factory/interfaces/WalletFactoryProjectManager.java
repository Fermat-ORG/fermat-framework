package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantChangeProjectStateException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantExportWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions.CantCloneInstalledWalletException;


import java.util.List;

public interface WalletFactoryProjectManager extends FermatManager {

    // getters project
    WalletFactoryProject getWalletFactoryProjectByPublicKey(String publicKey) throws CantGetWalletFactoryProjectException;
    List<WalletFactoryProject> getWalletFactoryProjectByState (WalletFactoryProjectState walletFactoryProjectState) throws CantGetWalletFactoryProjectException;
    List<WalletFactoryProject> getAllWalletFactoryProjects() throws CantGetWalletFactoryProjectException;

    // Edition of wallet factory project
    WalletFactoryProject createEmptyWalletFactoryProject() throws CantCreateWalletFactoryProjectException;
    void saveWalletFactoryProjectChanges(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect;
    void uploadWalletFactoryProjectToRepository(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect;
    void deleteWalletProjectFactory(WalletFactoryProject walletFactoryProject) throws CantDeleteWalletFactoryProjectException;
    void markProkectAsPublished(WalletFactoryProject walletFactoryProject) throws CantChangeProjectStateException;

    //repository task
    void exportProjectToRepository(WalletFactoryProject walletFactoryProject, String githubRepository, String userName, String password) throws CantExportWalletFactoryProjectException;

    //cloning of installed wallets
    void cloneInstalledWallet (InstalledWallet wallet, String newName) throws CantCloneInstalledWalletException;

}