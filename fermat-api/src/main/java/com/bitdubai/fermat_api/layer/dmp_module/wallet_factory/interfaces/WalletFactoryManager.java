package com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposalManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableDevelopersException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableProjectsException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager</code>
 * indicates the functionality of a WalletFactoryManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletFactoryManager {

    WalletFactoryDeveloper getLoggedDeveloper();

    List<WalletFactoryDeveloper> getAvailableDevelopers() throws CantGetAvailableDevelopersException;

    List<WalletFactoryProject> getAvailableProjects() throws CantGetAvailableProjectsException;

    public WalletFactoryProject createEmptyProject(String name, WalletCategory walletCategory,WalletType walletType)throws CantCreateWalletFactoryProjectException;

    public void importProjectFromRepository(String newName, String repository) throws CantImportWalletFactoryProjectException;

    public WalletFactoryProject getProject(String name)throws CantGetWalletFactoryProjectException, ProjectNotFoundException;



    // metodos para el walletFactoryProject

    // cambiar el nombre de WalletFactoryProjectProposalManager a WalletFactoryProjectVersion o algo como para decir eso
    public WalletFactoryProjectProposalManager getProjectVersion(UUID projectId,UUID versionId);

    // para la version en uso
    public WalletFactoryProjectProposal getProjectWorkingVersion(UUID projectId);

    //public List<WalletFactoryProjectProposal>  getAllProjectVersion(List<>);



}
