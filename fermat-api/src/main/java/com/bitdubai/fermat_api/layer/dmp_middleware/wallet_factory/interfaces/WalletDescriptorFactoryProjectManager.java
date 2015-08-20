package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletDescriptorFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantExportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletDescriptorFactoryProjectManager</code>
 * indicates the functionality of a WalletDescriptorFactoryProjectManager
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletDescriptorFactoryProjectManager {

    /**
     * This method creates an empty project
     *
     * @param name       is the name of the new project
     * @param walletType is the walletType of the new project
     * @return DescriptorFactoryProject instance of the new project
     * @throws CantCreateWalletDescriptorFactoryProjectException i case i can't create the project
     */
    WalletDescriptorFactoryProject createEmptyWalletFactoryProject(String name, Wallets walletType, String description, String publisherIdentityKey, DescriptorFactoryProjectType descriptorFactoryProjectType, String developerPublicKey) throws CantCreateWalletDescriptorFactoryProjectException;

    /**
     * Throw this method you can import a wallet factory project from the device which we are working with
     * <p/>
     * The method insert in database the correspondan information and copy the necessary structure
     * <p/>
     * TODO see the params i need to send to to this
     *
     * @param newName
     * @param resourcesId
     * @param navigationStructureId
     * @throws CantImportWalletFactoryProjectException
     */
    void importDescriptorFactoryProjectFromDevice(String newName, UUID resourcesId, UUID navigationStructureId) throws CantImportWalletFactoryProjectException;

    /**
     * Imports a Wallet Factory Project from an extern repository maybe git, maybe another maybe both
     * Modified by Manuel Perez on 07/08/2015
     * The method insert in database the correspondan information and copy the necessary structure
     *
     * @param user
     * @param repository
     * @throws CantImportWalletFactoryProjectException
     */
    void importDescriptorFactoryProjectFromRepository(String user, String password, String repository, String fileRepositoryLink) throws CantImportWalletFactoryProjectException;

    /**
     * Modified by Manuel Perez on 07/08/2015
     * Exports a Wallet Factory Project to an external repository.
     * This method upload an XML file with the Wallet Factory Project information.
     */
    void exportDescriptorFactoryProjectToRepository(String user, String password, String repository, DescriptorFactoryProject walletFactoryProject) throws CantExportWalletFactoryProjectException;

    /**
     * This method returns all the Wallet Factory Projects that exists in the device for the current logged developer user
     *
     * @return list of wallet factory projects
     * @throws CantGetWalletFactoryProjectsException if something goes wrong
     */
    List<WalletDescriptorFactoryProject> getAllDescriptorFactoryProjects() throws CantGetWalletFactoryProjectsException;

    /**
     * This method return a Wallet Factory Project instance looking by name, and the current developer logged in
     *
     * @param name of the project to search
     * @return the DescriptorFactoryProject instance
     * @throws CantGetWalletFactoryProjectException if something goes wrong
     * @throws ProjectNotFoundException             if the project cannot be found
     */
    WalletDescriptorFactoryProject getDescriptorFactoryProject(String name) throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    List<WalletDescriptorFactoryProject> getClosedDescriptorFactoryProject(FactoryProjectState state) throws CantGetWalletFactoryProjectException, ProjectNotFoundException;

    void closeProject(String name) throws CantUpdateWalletFactoryProjectException, ProjectNotFoundException;

    void setProjectState(UUID projectId, FactoryProjectState state) throws CantUpdateWalletFactoryProjectException, ProjectNotFoundException;

}