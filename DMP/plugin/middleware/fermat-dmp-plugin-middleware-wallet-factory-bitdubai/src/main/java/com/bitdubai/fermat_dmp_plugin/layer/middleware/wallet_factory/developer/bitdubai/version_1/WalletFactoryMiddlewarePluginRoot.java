package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCopyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCopyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantImportWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProposalNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.SkinNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.CantInitializeWalletFactoryMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProject;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectProposal;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.JAXBException;
import ae.javax.xml.bind.Marshaller;
import ae.javax.xml.bind.Unmarshaller;

/**
 * Allows to create a Wallet Factory Project and associate it to a DeveloperIdentity
 * Manage the relation Developer / Projects
 * Each project generates different versions of the new Wallet while the developer exports it in a format readable by the wallet publisher.
 *
 * You can create a project since nothing.
 * You can clone an existing project.
 * If the project is cloned or forked, its structure is copied from the other project and do a local copy.
 *
 * One project consist in a navigation structure and multimedia resources (images, etc.).
 *
 * Can delivery the list of the projects associated to the current logged developer.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewarePluginRoot implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, WalletFactoryManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    public static String NAVIGATION_STRUCTURE_FILE_NAME = "navigation-structure.xml";

    public static String WALLET_FACTORY_PROJECTS_PATH = "wallet_factory_projects";

    public static String WALLET_FACTORY_SKINS_PATH = "skins";

    public static String WALLET_FACTORY_LANGUAGES_PATH = "languages";


    WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao;

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        walletFactoryMiddlewareProjectDao = new WalletFactoryMiddlewareDao(pluginDatabaseSystem);
        try {
            walletFactoryMiddlewareProjectDao.initializeDatabase(pluginId, pluginId.toString());
        } catch (CantInitializeWalletFactoryMiddlewareDatabaseException e) {
            this.serviceStatus = ServiceStatus.STOPPED;
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "", "");
        }

    }
    @Override
    public void pause(){
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume(){
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    @Override
    public WalletFactoryMiddlewareProject createEmptyWalletFactoryProject(String name, Wallets walletType) throws CantCreateWalletFactoryProjectException {
        try {
            // TODO GET CURRENT LOGGED DEVELOPER
            String developerPublicKey = "";

            WalletFactoryMiddlewareProject walletFactoryMiddlewareProject = new WalletFactoryMiddlewareProject(name, developerPublicKey, walletType);
            walletFactoryMiddlewareProjectDao.create(walletFactoryMiddlewareProject);

            return walletFactoryMiddlewareProject;
        } catch (CantCreateWalletFactoryProjectException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void importWalletFactoryProjectFromDevice(String newName, UUID resourcesId, UUID navigationStructureId) throws CantImportWalletFactoryProjectException {
        // TODO LOOK FOR A WAY TO TO THIS
    }

    @Override
    public void importWalletFactoryProjectFromRepository(String newName, String repository) throws CantImportWalletFactoryProjectException {
        // TODO LOOK FOR A WAY TO TO THIS
    }

    @Override
    public List<WalletFactoryProject> getAllWalletFactoryProjects() throws CantGetWalletFactoryProjectsException {
        try {
            // TODO GET CURRENT LOGGED DEVELOPER
            String developerPublicKey = "";
            return walletFactoryMiddlewareProjectDao.findAll(developerPublicKey);
        } catch (CantGetWalletFactoryProjectsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProject getWalletFactoryProject(String name) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
        try {
            // TODO GET CURRENT LOGGED DEVELOPER
            String developerPublicKey = "";
            return walletFactoryMiddlewareProjectDao.findByName(name, developerPublicKey);
        } catch (CantGetWalletFactoryProjectException|ProjectNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public List<WalletFactoryProjectProposal> getProposals(WalletFactoryProject walletFactoryProject) throws CantGetWalletFactoryProjectProposalsException {
        try {
            return walletFactoryMiddlewareProjectDao.findAllProposalByProject(walletFactoryProject);
        } catch (CantGetWalletFactoryProjectProposalsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectProposal getProposalByName(String proposal, WalletFactoryProject walletFactoryProject) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException {
        try {
            return walletFactoryMiddlewareProjectDao.findProposalByNameAndProject(proposal, walletFactoryProject);
        } catch (CantGetWalletFactoryProjectProposalException|ProposalNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectProposal getProposalById(UUID id) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException {
        try {
            return walletFactoryMiddlewareProjectDao.findProposalById(id);
        } catch (CantGetWalletFactoryProjectProposalException|ProposalNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectProposal createProposal(String alias, Wallets walletType, WalletFactoryProject walletFactoryProject) throws CantCreateWalletFactoryProjectProposalException {
        try {
            WalletFactoryProjectProposal walletFactoryProjectProposal = new WalletFactoryMiddlewareProjectProposal(alias, FactoryProjectState.DRAFT, walletFactoryProject);
            Wallet navigationStructure = new Wallet();
            navigationStructure.setType(walletType);
            setNavigationStructureXml(navigationStructure, walletFactoryProjectProposal);
            try {
                walletFactoryMiddlewareProjectDao.createProposal(walletFactoryProjectProposal);
                return walletFactoryProjectProposal;
            } catch (CantCreateWalletFactoryProjectProposalException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantSetWalletFactoryProjectNavigationStructureException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateWalletFactoryProjectProposalException(CantCreateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "Cant create navigation structure directory", "");
        }
    }

    @Override
    public WalletFactoryProjectProposal copyProposal(String newAlias, WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCreateWalletFactoryProjectProposalException {
        // TODO FIND PROPOSAL TO COPY
        // TODO COPY NAVIGATION STRUCTURE
        // TODO COPY RESOURCES STRUCTURE
        // TODO INSERT NEW PROPOSAL IN DATABASE
        return null;
    }

    @Override
    public void updateProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantUpdateWalletFactoryProjectProposalException, ProposalNotFoundException {
        try {
            walletFactoryMiddlewareProjectDao.updateProposal(walletFactoryProjectProposal);
        } catch (CantUpdateWalletFactoryProjectProposalException|ProposalNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public void deleteProposal(UUID id) throws CantDeleteWalletFactoryProjectProposalException, ProposalNotFoundException {
        // TODO DELETE STRUCTURE
        try {
            walletFactoryMiddlewareProjectDao.deleteProposal(id);
        } catch (CantDeleteWalletFactoryProjectProposalException|ProposalNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public Wallet getNavigationStructure(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantGetWalletFactoryProjectNavigationStructureException {
        String path = createProposalPath(walletFactoryProjectProposal);
        if (path != null) {
            try {
                PluginTextFile newFile = pluginFileSystem.getTextFile(pluginId, path, NAVIGATION_STRUCTURE_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                try {
                    return getNavigationStructure(newFile.getContent());
                } catch (CantGetWalletFactoryProjectNavigationStructureException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantGetWalletFactoryProjectNavigationStructureException(CantGetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't convert the xml to the navigation structure.", "");
                }
            } catch (FileNotFoundException |CantCreateFileException |CantLoadFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetWalletFactoryProjectNavigationStructureException(CantGetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't get navigation structure file.", "");
            }
        }
        throw new CantGetWalletFactoryProjectNavigationStructureException(CantGetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, null, "Can't create path, check the proposal", "");

    }

    @Override
    public Wallet getNavigationStructure(String navigationStructure) throws CantGetWalletFactoryProjectNavigationStructureException {
        if (navigationStructure != null) {
            try {
                RuntimeInlineAnnotationReader.cachePackageAnnotation(Wallet.class.getPackage(), new XmlSchemaMine(""));

                JAXBContext jaxbContext = JAXBContext.newInstance(Wallet.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(navigationStructure);

                return (Wallet) jaxbUnmarshaller.unmarshal(reader);
            } catch (JAXBException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetWalletFactoryProjectNavigationStructureException(CantGetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't get navigation structure XML.", "");
            }
        }
        throw new CantGetWalletFactoryProjectNavigationStructureException(CantGetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, null, "Navigation Structure is null", "");
    }

    @Override
    public String getNavigationStructureXml(Wallet wallet) throws CantGetWalletFactoryProjectNavigationStructureException {
        if (wallet != null) {
            try {
                RuntimeInlineAnnotationReader.cachePackageAnnotation(Wallet.class.getPackage(), new XmlSchemaMine(""));

                JAXBContext jaxbContext = JAXBContext.newInstance(Wallet.class);

                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

                Writer outputStream = new StringWriter();
                jaxbMarshaller.marshal(wallet, outputStream);

                return outputStream.toString();
            } catch (JAXBException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetWalletFactoryProjectNavigationStructureException(CantGetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't get navigation structure XML.", "");
            }
        }
        throw new CantGetWalletFactoryProjectNavigationStructureException(CantGetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, null, "Navigation Structure is null", "");
    }

    @Override
    public void setNavigationStructureXml(Wallet wallet, WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantSetWalletFactoryProjectNavigationStructureException {
        String path = createProposalPath(walletFactoryProjectProposal);
        if (path != null) {
            try {
                String navigationStructureXml = getNavigationStructureXml(wallet);
                try {
                    PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, path, NAVIGATION_STRUCTURE_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    newFile.loadFromMedia();
                    newFile.setContent(navigationStructureXml);
                    newFile.persistToMedia();
                } catch (CantPersistFileException |CantCreateFileException |CantLoadFileException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantSetWalletFactoryProjectNavigationStructureException(CantSetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't create or overwrite navigation structure file.", "");
                }
            } catch (CantGetWalletFactoryProjectNavigationStructureException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSetWalletFactoryProjectNavigationStructureException(CantSetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't convert navigation structure to xml format", "");
            }
        }
        throw new CantSetWalletFactoryProjectNavigationStructureException(CantSetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, null, "Can't create path, check the proposal", "");
    }

    private String createProposalPath(WalletFactoryProjectProposal walletFactoryProjectProposal) {
        if (walletFactoryProjectProposal != null &&
            walletFactoryProjectProposal.getAlias() != null &&
            walletFactoryProjectProposal.getProject() != null &&
            walletFactoryProjectProposal.getProject().getName() != null) {

            String initialPath = WALLET_FACTORY_PROJECTS_PATH;
            String projectPath = walletFactoryProjectProposal.getProject().getName();
            String proposalPath = walletFactoryProjectProposal.getAlias();
            return initialPath + "/" +
                    projectPath + "/" +
                    proposalPath;
        } else {
            return null;
        }
    }

    @Override
    public List<WalletFactoryProjectSkin> getSkins(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantGetWalletFactoryProjectSkinsException {
        try {
            return walletFactoryMiddlewareProjectDao.findAllSkinsByProposal(walletFactoryProjectProposal);
        } catch (CantGetWalletFactoryProjectSkinsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectSkin getSkinById(UUID id) throws CantGetWalletFactoryProjectSkinException, SkinNotFoundException {
        try {
            return walletFactoryMiddlewareProjectDao.findSkinById(id);
        } catch (CantGetWalletFactoryProjectSkinException|SkinNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectSkin createEmptySkin(String name, WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCreateEmptyWalletFactoryProjectSkinException {
        // TODO CREATE EMPTY SKIN FILE (NAME+VERSION)
        // TODO INSERT A NEW SKIN IN DATABASE
        return null;
    }

    @Override
    public WalletFactoryProjectSkin copySkin(String newName, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantCopyWalletFactoryProjectSkinException, SkinNotFoundException {
        // TODO SEARCH BY ID EXISTENT SKIN
        // TODO INSERT A NEW SKIN IN DATABASE
        // TODO COPY THE STRUCTURE AND FILES
        return null;
    }

    @Override
    public void deleteSkin(UUID id) throws CantDeleteWalletFactoryProjectSkinException, SkinNotFoundException {
        // TODO SEARCH BY EXISTENT ID
        // TODO DELETE FILE STRUCTURE
        // TODO DELETE RECORD FROM DATABASE
    }

    @Override
    public Skin getSkinStructure(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantGetWalletFactoryProjectSkinStructureException {
        String path = createSkinPath(walletFactoryProjectSkin);
        String skinFileName = createSkinFileName(walletFactoryProjectSkin);
        if (path != null) {
            try {
                PluginTextFile newFile = pluginFileSystem.getTextFile(pluginId, path, skinFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                try {
                    return getSkinStructure(newFile.getContent());
                } catch (CantGetWalletFactoryProjectSkinStructureException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantGetWalletFactoryProjectSkinStructureException(CantGetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, e, "Can't convert the xml to the Skin structure.", "");
                }
            } catch (FileNotFoundException |CantCreateFileException |CantLoadFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetWalletFactoryProjectSkinStructureException(CantGetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, e, "Can't get skin structure file.", "");
            }
        }
        throw new CantGetWalletFactoryProjectSkinStructureException(CantGetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, null, "Can't create path, check the proposal", "");
    }

    @Override
    public Skin getSkinStructure(String skinStructure) throws CantGetWalletFactoryProjectSkinStructureException {
        if (skinStructure != null) {
            try {
                RuntimeInlineAnnotationReader.cachePackageAnnotation(Skin.class.getPackage(), new XmlSchemaMine(""));

                JAXBContext jaxbContext = JAXBContext.newInstance(Skin.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(skinStructure);

                return (Skin) jaxbUnmarshaller.unmarshal(reader);
            } catch (JAXBException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetWalletFactoryProjectSkinStructureException(CantGetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, e, "Can't get skin structure XML.", "");
            }
        }
        throw new CantGetWalletFactoryProjectSkinStructureException(CantGetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, null, "Skin Structure is null", "");
    }

    @Override
    public String getSkinStructureXml(Skin skin) throws CantGetWalletFactoryProjectSkinStructureException {
        if (skin != null) {
            try {
                RuntimeInlineAnnotationReader.cachePackageAnnotation(Skin.class.getPackage(), new XmlSchemaMine(""));

                JAXBContext jaxbContext = JAXBContext.newInstance(Skin.class);

                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

                Writer outputStream = new StringWriter();
                jaxbMarshaller.marshal(skin, outputStream);

                return outputStream.toString();
            } catch (JAXBException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetWalletFactoryProjectSkinStructureException(CantGetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, e, "Can't get skin structure XML.", "");
            }
        }
        throw new CantGetWalletFactoryProjectSkinStructureException(CantGetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, null, "skin Structure is null", "");
    }

    @Override
    public void setSkinStructureXml(Skin skin, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantSetWalletFactoryProjectSkinStructureException {
        String path = createSkinPath(walletFactoryProjectSkin);
        String skinFileName = createSkinFileName(walletFactoryProjectSkin);
        if (path != null) {
            try {
                String skinStructureXml = getSkinStructureXml(skin);
                try {
                    PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, path, skinFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    newFile.loadFromMedia();
                    newFile.setContent(skinStructureXml);
                    newFile.persistToMedia();
                } catch (CantPersistFileException |CantCreateFileException |CantLoadFileException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantSetWalletFactoryProjectSkinStructureException(CantSetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, e, "Can't create or overwrite skin structure file.", "");
                }
            } catch (CantGetWalletFactoryProjectSkinStructureException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSetWalletFactoryProjectSkinStructureException(CantSetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, e, "Can't convert skin structure to xml format", "");
            }
        }
        throw new CantSetWalletFactoryProjectSkinStructureException(CantSetWalletFactoryProjectSkinStructureException.DEFAULT_MESSAGE, null, "Can't create path, check the proposal", "");
    }

    private String createSkinPath(WalletFactoryProjectSkin walletFactoryProjectSkin) {
        if (walletFactoryProjectSkin != null &&
                walletFactoryProjectSkin.getName() != null &&
                walletFactoryProjectSkin.getVersion() != null &&
                walletFactoryProjectSkin.getWalletFactoryProjectProposal() != null) {

            String proposalPath = createProposalPath(walletFactoryProjectSkin.getWalletFactoryProjectProposal());
            String skinsPath = WALLET_FACTORY_SKINS_PATH;
            return proposalPath + "/" +
                    skinsPath + "/" +
                    walletFactoryProjectSkin.getName() + "/" +
                    walletFactoryProjectSkin.getVersion();
        }
        return null;
    }

    private String createSkinFileName(WalletFactoryProjectSkin walletFactoryProjectSkin) {
        if (walletFactoryProjectSkin != null &&
                walletFactoryProjectSkin.getName() != null) {
            return walletFactoryProjectSkin.getName()+".xml";
        }
        return null;
    }

    @Override
    public List<WalletFactoryProjectLanguage> getLanguages(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantGetWalletFactoryProjectLanguagesException {
        try {
            return walletFactoryMiddlewareProjectDao.findAllLanguagesByProposal(walletFactoryProjectProposal);
        } catch (CantGetWalletFactoryProjectLanguagesException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectLanguage getLanguageById(UUID id) throws CantGetWalletFactoryProjectLanguageException, LanguageNotFoundException {
        try {
            return walletFactoryMiddlewareProjectDao.findLanguageById(id);
        } catch (CantGetWalletFactoryProjectLanguageException|LanguageNotFoundException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectLanguage createEmptyLanguage(String name, Languages type, WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCreateEmptyWalletFactoryProjectSkinException {
        // TODO CREATE EMPTY LANGUAGE FILE (NAME+VERSION)
        // TODO INSERT A NEW LANGUAGE IN DATABASE
        return null;
    }

    @Override
    public WalletFactoryProjectLanguage copyLanguage(String newName, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantCopyWalletFactoryProjectLanguageException, LanguageNotFoundException {
        // TODO SEARCH BY ID EXISTENT LANGUAGE
        // TODO INSERT A NEW LANGUAGE IN DATABASE
        // TODO COPY THE FILE TO THE NEW NAME
        return null;
    }

    @Override
    public void deleteLanguage(UUID id) throws CantDeleteWalletFactoryProjectLanguageException, LanguageNotFoundException {
        // TODO SEARCH BY EXISTENT ID
        // TODO DELETE FILE STRUCTURE
        // TODO DELETE RECORD FROM DATABASE
    }

    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.WalletFactoryMiddlewarePluginRoot");

        /**
         * I return the values.
         */
        return returnedClasses;
    }


    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletFactoryMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletFactoryMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletFactoryMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletFactoryMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }


    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Plugin methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
