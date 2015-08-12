package com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantRemoveWalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableDevelopersException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableProjectsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;


import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager</code>
 * indicates the functionality of a WalletFactoryManager
 * <p/>
 *
 *  Created by Matias Furszyfer
 *
 */
public interface WalletFactoryManager {

    WalletFactoryDeveloper getLoggedDeveloper();

    List<WalletFactoryDeveloper> getAvailableDevelopers() throws CantGetAvailableDevelopersException;

    List<FactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException;

    public FactoryProject createEmptyProject(String name, WalletCategory walletCategory,WalletType walletType)throws CantCreateWalletFactoryProjectException;

    public void saveProject(FactoryProject walletFactoryProject)throws CantSaveWalletFactoryProyect;

    public void removeyProject(FactoryProject walletFactoryProject) throws CantRemoveWalletFactoryProject;

    public void importProjectFromRepository(String newName, String repository) throws CantImportWalletFactoryProjectException;

    public FactoryProject getProject(String name)throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    public List<FactoryProject> getClosedProjects(FactoryProjectState state)throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    public FactoryProject closeProject(String name)throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    public Skin getSkin(UUID skinId) throws CantGetSkinException;

    public Language getLanguage(UUID languageId) throws CantGetLanguageException;

}
