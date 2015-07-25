package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCopyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteLanguageStringException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguageManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
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
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 5/207/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProjectLanguageManager implements DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProjectLanguageManager {

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

    public static String WALLET_FACTORY_PROJECTS_PATH = "wallet_factory_projects";

    public static String WALLET_FACTORY_LANGUAGES_PATH = "languages";

    WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao;

    WalletFactoryProjectProposal walletFactoryProjectProposal;

    public WalletFactoryMiddlewareProjectLanguageManager(ErrorManager errorManager, PluginFileSystem pluginFileSystem, UUID pluginId, WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.errorManager = errorManager;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.walletFactoryMiddlewareProjectDao = walletFactoryMiddlewareProjectDao;
        this.walletFactoryProjectProposal = walletFactoryProjectProposal;
    }

    @Override
    public List<WalletFactoryProjectLanguage> getLanguages() throws CantGetWalletFactoryProjectLanguagesException {
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
    public WalletFactoryProjectLanguage createEmptyLanguage(String name, Languages type) throws CantCreateEmptyWalletFactoryProjectLanguageException {
        try {
            // TODO FIND TRANSLATOR PUBLIC KEY
            String translatorPublicKey = "";
            WalletFactoryProjectLanguage walletFactoryProjectLanguage = new WalletFactoryMiddlewareProjectLanguage(name, type, new Version("1.0.0"), translatorPublicKey, walletFactoryProjectProposal);
            Language language = new Language(name, type, new Version("1.0.0"));
            setLanguageXml(language, walletFactoryProjectLanguage);
            try {
                walletFactoryMiddlewareProjectDao.createLanguage(walletFactoryProjectLanguage);
                return walletFactoryProjectLanguage;
            } catch (CantCreateEmptyWalletFactoryProjectLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantSetLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateEmptyWalletFactoryProjectLanguageException(CantCreateEmptyWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "Cant create language", "");
        }
    }

    @Override
    public WalletFactoryProjectLanguage copyLanguage(String newName, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantCopyWalletFactoryProjectLanguageException, LanguageNotFoundException {
        // TODO SEARCH BY ID EXISTENT LANGUAGE
        // TODO INSERT A NEW LANGUAGE IN DATABASE
        // TODO COPY THE FILE TO THE NEW NAME
        return null;
    }

    @Override
    public void deleteLanguage(WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantDeleteWalletFactoryProjectLanguageException, LanguageNotFoundException {
        try {
            String languagePath = createLanguagesPath(walletFactoryProjectLanguage);

            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, languagePath, walletFactoryProjectLanguage.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            //newFile.deleteFile();
            // TODO DELETE FILE IN PLUGIN FILE SYSTEM
            try {
                walletFactoryMiddlewareProjectDao.deleteLanguage(walletFactoryProjectLanguage.getId());
            } catch (CantDeleteWalletFactoryProjectLanguageException|LanguageNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (FileNotFoundException |CantCreateFileException |CantLoadFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteWalletFactoryProjectLanguageException(CantDeleteWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "Can't delete file from the structure.", "");
        }
    }

    @Override
    public Language getLanguage(WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantGetLanguageException {
        String path = createLanguagesPath(walletFactoryProjectLanguage);
        String languageFileName = createLanguageFileName(walletFactoryProjectLanguage);
        if (path != null) {
            try {
                PluginTextFile newFile = pluginFileSystem.getTextFile(pluginId, path, languageFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                try {
                    return getLanguage(newFile.getContent());
                } catch (CantGetLanguageException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, e, "Can't convert the xml to the language.", "");
                }
            } catch (FileNotFoundException |CantCreateFileException |CantLoadFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, e, "Can't get language file.", "");
            }
        }
        throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, null, "Can't create path, check the proposal", "");
    }

    @Override
    public Language getLanguage(String language) throws CantGetLanguageException {
        if (language != null) {
            try {
                RuntimeInlineAnnotationReader.cachePackageAnnotation(Language.class.getPackage(), new XmlSchemaMine(""));

                JAXBContext jaxbContext = JAXBContext.newInstance(Language.class);

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader reader = new StringReader(language);

                return (Language) jaxbUnmarshaller.unmarshal(reader);
            } catch (JAXBException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, e, "Can't get language XML.", "");
            }
        }
        throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, null, "language is null", "");
    }

    @Override
    public String getLanguageXml(Language language) throws CantGetLanguageException {
        if (language != null) {
            try {
                RuntimeInlineAnnotationReader.cachePackageAnnotation(Language.class.getPackage(), new XmlSchemaMine(""));

                JAXBContext jaxbContext = JAXBContext.newInstance(Language.class);

                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

                Writer outputStream = new StringWriter();
                jaxbMarshaller.marshal(language, outputStream);

                return outputStream.toString();
            } catch (JAXBException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, e, "Can't get language XML.", "");
            }
        }
        throw new CantGetLanguageException(CantGetLanguageException.DEFAULT_MESSAGE, null, "language is null", "");
    }

    @Override
    public void setLanguageXml(Language language, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantSetLanguageException {
        String path = createLanguagesPath(walletFactoryProjectLanguage);
        String languageFileName = createLanguageFileName(walletFactoryProjectLanguage);
        if (path != null) {
            try {
                String languageStructureXml = getLanguageXml(language);
                try {
                    PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, path, languageFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    newFile.loadFromMedia();
                    newFile.setContent(languageStructureXml);
                    newFile.persistToMedia();
                } catch (CantPersistFileException |CantCreateFileException |CantLoadFileException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantSetLanguageException(CantSetLanguageException.DEFAULT_MESSAGE, e, "Can't create or overwrite language file.", "");
                }
            } catch (CantGetLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantSetLanguageException(CantSetLanguageException.DEFAULT_MESSAGE, e, "Can't convert language to xml format", "");
            }
        }
        throw new CantSetLanguageException(CantSetLanguageException.DEFAULT_MESSAGE, null, "Can't create path, check the proposal", "");
    }

    private String createLanguagesPath(WalletFactoryProjectLanguage walletFactoryProjectLanguage) {
        if (walletFactoryProjectLanguage != null &&
                walletFactoryProjectLanguage.getName() != null &&
                walletFactoryProjectLanguage.getVersion() != null &&
                walletFactoryProjectLanguage.getWalletFactoryProjectProposal() != null) {

            String proposalPath = createProposalPath();
            String languagesPath = WALLET_FACTORY_LANGUAGES_PATH;
            return proposalPath + "/" +
                    languagesPath + "/" +
                    walletFactoryProjectLanguage.getName() + "/" +
                    walletFactoryProjectLanguage.getVersion();
        }
        return null;
    }

    private String createLanguageFileName(WalletFactoryProjectLanguage walletFactoryProjectLanguage) {
        if (walletFactoryProjectLanguage != null &&
                walletFactoryProjectLanguage.getName() != null) {
            return walletFactoryProjectLanguage.getName()+".xml";
        }
        return null;
    }

    private String createProposalPath() {
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
    public void addLanguageString(String name, String value, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantAddLanguageStringException {
        try {
            Language language = getLanguage(walletFactoryProjectLanguage);
            language.addString(name, value);
            try {
                setLanguageXml(language, walletFactoryProjectLanguage);
            } catch (CantSetLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantAddLanguageStringException(CantAddLanguageStringException.DEFAULT_MESSAGE, e, "Can't set language xml file", "");
            }
        } catch (CantGetLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAddLanguageStringException(CantAddLanguageStringException.DEFAULT_MESSAGE, e, "Can't get language xml file", "");
        }
    }

    @Override
    public void deleteLanguageString(String name, WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantDeleteLanguageStringException {
        try {
            Language language = getLanguage(walletFactoryProjectLanguage);
            language.deleteString(name);
            try {
                setLanguageXml(language, walletFactoryProjectLanguage);
            } catch (CantSetLanguageException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantDeleteLanguageStringException(CantDeleteLanguageStringException.DEFAULT_MESSAGE, e, "Can't set language xml file", "");
            }
        } catch (CantGetLanguageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteLanguageStringException(CantDeleteLanguageStringException.DEFAULT_MESSAGE, e, "Can't get language xml file", "");
        }
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
