package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProposalNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguageManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposalManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkinManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.UUID;

import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.JAXBException;
import ae.javax.xml.bind.Marshaller;
import ae.javax.xml.bind.Unmarshaller;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectLanguageManager</code>
 * implementation of WalletFactoryProjectLanguageManager.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProjectProposalManager implements DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProjectProposalManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    /**
     * WalletFactoryProjectLanguageManager Interface member variables
     */

    private final String NAVIGATION_STRUCTURE_FILE_NAME = "navigation-structure.xml";

    WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao;

    WalletFactoryProject walletFactoryProject;

    public WalletFactoryMiddlewareProjectProposalManager(ErrorManager errorManager, PluginFileSystem pluginFileSystem, UUID pluginId, WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao, WalletFactoryProject walletFactoryProject) {
        this.errorManager = errorManager;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.walletFactoryMiddlewareProjectDao = walletFactoryMiddlewareProjectDao;
        this.walletFactoryProject = walletFactoryProject;
    }

    @Override
    public List<WalletFactoryProjectProposal> getProposals() throws CantGetWalletFactoryProjectProposalsException {
        try {
            return walletFactoryMiddlewareProjectDao.findAllProposalByProject(walletFactoryProject);
        } catch (CantGetWalletFactoryProjectProposalsException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public WalletFactoryProjectProposal getProposalByName(String proposal) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException {
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
    public WalletFactoryProjectProposal createProposal(String alias, Wallets walletType) throws CantCreateWalletFactoryProjectProposalException {
        try {
            WalletFactoryProjectProposal walletFactoryProjectProposal = new WalletFactoryMiddlewareProjectProposal(alias, FactoryProjectState.DRAFT, walletFactoryProject.getPath());
            Wallet navigationStructure = new Wallet();
            navigationStructure.setType(walletType);
            setNavigationStructureXml(navigationStructure, walletFactoryProjectProposal);
            try {
                walletFactoryMiddlewareProjectDao.createProposal(walletFactoryProjectProposal, walletFactoryProject);
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
    public void deleteProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantDeleteWalletFactoryProjectProposalException, ProposalNotFoundException {
        // TODO DELETE STRUCTURE
        try {
            walletFactoryMiddlewareProjectDao.deleteProposal(walletFactoryProjectProposal.getId());
        } catch (CantDeleteWalletFactoryProjectProposalException|ProposalNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public Wallet getNavigationStructure(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantGetWalletFactoryProjectNavigationStructureException {
        try {
            PluginTextFile newFile = pluginFileSystem.getTextFile(pluginId, walletFactoryProjectProposal.getPath(), NAVIGATION_STRUCTURE_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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
        try {
            String navigationStructureXml = getNavigationStructureXml(wallet);
            try {
                PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, walletFactoryProjectProposal.getPath(), NAVIGATION_STRUCTURE_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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

    @Override
    public WalletFactoryProjectSkinManager getWalletFactoryProjectSkinManager(WalletFactoryProjectProposal walletFactoryProjectProposal) {
        return new WalletFactoryMiddlewareProjectSkinManager(errorManager, pluginFileSystem, pluginId, walletFactoryMiddlewareProjectDao, walletFactoryProjectProposal);
    }

    @Override
    public WalletFactoryProjectLanguageManager getWalletFactoryProjectLanguageManager(WalletFactoryProjectProposal walletFactoryProjectProposal) {
        return new WalletFactoryMiddlewareProjectLanguageManager(errorManager, pluginFileSystem, pluginId, walletFactoryMiddlewareProjectDao, walletFactoryProjectProposal);
    }

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
