package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantExportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletFactoryManager</code>
 * indicates the functionality of a WalletFactoryManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletFactoryManager {

    /**
     * This method creates an empty project
     *
     * @param name is the name of the new project
     * @param walletType is the walletType of the new project
     * @return WalletFactoryProject instance of the new project
     * @throws CantCreateWalletFactoryProjectException i case i can't create the project
     */
    WalletFactoryProject createEmptyWalletFactoryProject(String name, Wallets walletType) throws CantCreateWalletFactoryProjectException;

    /**
     * Throw this method you can import a wallet factory project from the device which we are working with
     *
     * The method insert in database the correspondan information and copy the necessary structure
     *
     * TODO see the params i need to send to to this
     * @param newName
     * @param resourcesId
     * @param navigationStructureId
     * @throws CantImportWalletFactoryProjectException
     */
    void importWalletFactoryProjectFromDevice(String newName, UUID resourcesId, UUID navigationStructureId) throws CantImportWalletFactoryProjectException;

    /**
     * Imports a Wallet Factory Project from an extern repository maybe git, maybe another maybe both
     *
     * The method insert in database the correspondan information and copy the necessary structure
     *
     * @param newName
     * @param repository
     * @throws CantImportWalletFactoryProjectException
     */
    void importWalletFactoryProjectFromRepository(String newName, String repository) throws CantImportWalletFactoryProjectException;

    /**
     * Modified by Manuel Perez on 07/08/2015
     * Exports a Wallet Factory Project to an external repository.
     * This method upload an XML file with the Wallet Factory Project information.
     */
    void exportWalletFactoryProjectToRepository(String user, String password, String repository, WalletFactoryProject walletFactoryProject) throws CantExportWalletFactoryProjectException;

    /**
     * This method returns all the Wallet Factory Projects that exists in the device for the current logged developer user
     *
     * @return list of wallet factory projects
     * @throws CantGetWalletFactoryProjectsException if something goes wrong
     */
    List<WalletFactoryProject> getAllWalletFactoryProjects() throws CantGetWalletFactoryProjectsException;

    /**
     * This method return a Wallet Factory Project instance looking by name, and the current developer logged in
     *
     * @param name of the project to search
     * @return the WalletFactoryProject instance
     * @throws CantGetWalletFactoryProjectException if something goes wrong
     * @throws ProjectNotFoundException if the project cannot be found
     */
    WalletFactoryProject getWalletFactoryProject(String name) throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    /**
     * This method returns an instance of the WalletFactoryProjectProposalManager, who has the methods to interact with a proposal
     *
     * @param walletFactoryProject the walletFactoryProject we're working with
     * @return the WalletFactoryProjectProposalManager we need to do the right job
     */
    WalletFactoryProjectProposalManager getWalletFactoryProjectProposalManager(WalletFactoryProject walletFactoryProject);

}