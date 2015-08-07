package com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantRemoveWalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableDevelopersException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableProjectsException;


import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager</code>
 * indicates the functionality of a WalletFactoryManager
 * <p/>
 *
 *  Matias Furszyfer
 *
 */
public interface WalletFactoryManager {

    WalletFactoryDeveloper getLoggedDeveloper();

    List<WalletFactoryDeveloper> getAvailableDevelopers() throws CantGetAvailableDevelopersException;

    List<WalletFactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException;

    public WalletFactoryProject createEmptyProject(String name, WalletCategory walletCategory,WalletType walletType)throws CantCreateWalletFactoryProjectException;

    public void saveProject(WalletFactoryProject walletFactoryProject)throws CantSaveWalletFactoryProyect;

    public void removeyProject(WalletFactoryProject walletFactoryProject) throws CantRemoveWalletFactoryProject;

    public void importProjectFromRepository(String newName, String repository) throws CantImportWalletFactoryProjectException;

    public WalletFactoryProject getProject(String name)throws CantGetWalletFactoryProjectException, ProjectNotFoundException;


}
