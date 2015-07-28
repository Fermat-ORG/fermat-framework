package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddResourceToSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCopyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteResourceFromSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectSkinStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateResourceToSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceAlreadyExistsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.SkinNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkinManager;
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
public class WalletFactoryMiddlewareProjectSkinManager implements DealsWithErrors, DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProjectSkinManager {

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

    private final String WALLET_FACTORY_SKINS_PATH = "skins";

    WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao;

    WalletFactoryProjectProposal walletFactoryProjectProposal;

    public WalletFactoryMiddlewareProjectSkinManager(ErrorManager errorManager, PluginFileSystem pluginFileSystem, UUID pluginId, WalletFactoryMiddlewareDao walletFactoryMiddlewareProjectDao, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.errorManager = errorManager;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.walletFactoryMiddlewareProjectDao = walletFactoryMiddlewareProjectDao;
        this.walletFactoryProjectProposal = walletFactoryProjectProposal;
    }

    @Override
    public List<WalletFactoryProjectSkin> getSkins() throws CantGetWalletFactoryProjectSkinsException {
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
    public WalletFactoryProjectSkin createEmptySkin(String name) throws CantCreateEmptyWalletFactoryProjectSkinException {
        try {
            // TODO FIND DESIGNER PUBLIC KEY
            String designerPublicKey = "";
            WalletFactoryProjectSkin walletFactoryProjectSkin = new WalletFactoryMiddlewareProjectSkin(name, designerPublicKey, new Version("1.0.0"), walletFactoryProjectProposal.getPath() + WALLET_FACTORY_SKINS_PATH);
            Skin skin = new Skin(name, new Version("1.0.0"));
            setSkinStructureXml(skin, walletFactoryProjectSkin);
            try {
                walletFactoryMiddlewareProjectDao.createSkin(walletFactoryProjectSkin, walletFactoryProjectProposal);
                return walletFactoryProjectSkin;
            } catch (CantCreateEmptyWalletFactoryProjectSkinException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw e;
            }
        } catch (CantSetWalletFactoryProjectSkinStructureException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateEmptyWalletFactoryProjectSkinException(CantCreateEmptyWalletFactoryProjectSkinException.DEFAULT_MESSAGE, e, "Cant create skin structure directory", "");
        }
    }

    @Override
    public WalletFactoryProjectSkin copySkin(String newName, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantCopyWalletFactoryProjectSkinException, SkinNotFoundException {
        // TODO SEARCH BY ID EXISTENT SKIN
        // TODO INSERT A NEW SKIN IN DATABASE
        // TODO COPY THE STRUCTURE AND FILES
        return null;
    }

    @Override
    public void deleteSkin(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantDeleteWalletFactoryProjectSkinException, SkinNotFoundException {
        // TODO DELETE STRUCTURE
        try {
            walletFactoryMiddlewareProjectDao.deleteSkin(walletFactoryProjectSkin.getId());
        } catch (CantDeleteWalletFactoryProjectSkinException|SkinNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        }
    }

    @Override
    public Skin getSkinStructure(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantGetWalletFactoryProjectSkinStructureException {
        String skinFileName = createSkinFileName(walletFactoryProjectSkin);
        try {
            PluginTextFile newFile = pluginFileSystem.getTextFile(pluginId, walletFactoryProjectSkin.getPath(), skinFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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
        String skinFileName = createSkinFileName(walletFactoryProjectSkin);
        try {
            String skinStructureXml = getSkinStructureXml(skin);
            try {
                PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, walletFactoryProjectSkin.getPath(), skinFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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

    private String createSkinFileName(WalletFactoryProjectSkin walletFactoryProjectSkin) {
        if (walletFactoryProjectSkin != null &&
                walletFactoryProjectSkin.getName() != null) {
            return walletFactoryProjectSkin.getName()+".xml";
        }
        return null;
    }

    private String createResourcePath(ResourceType resourceType, String path) {
        return path + "/" + resourceType.value();
    }

    @Override
    public void addResourceToSkin(Resource resource, byte[] file, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantAddResourceToSkinException, ResourceAlreadyExistsException {
        try {
            Skin skin = getSkinStructure(walletFactoryProjectSkin);
            skin.addResource(resource);
            try {
                // TODO CHECK IF FILE ALREADY EXIST THROW EXCEPTION ResourceAlreadyExistsException
                String resourcePath = createResourcePath(resource.getResourceType(), walletFactoryProjectSkin.getPath());

                PluginBinaryFile newFile = pluginFileSystem.createBinaryFile(pluginId, resourcePath, resource.getFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                newFile.setContent(file);
                newFile.persistToMedia();
                try {
                    setSkinStructureXml(skin, walletFactoryProjectSkin);
                } catch (CantSetWalletFactoryProjectSkinStructureException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantAddResourceToSkinException(CantAddResourceToSkinException.DEFAULT_MESSAGE, e, "Can't set language xml file", "");
                }
            } catch (CantCreateFileException|CantLoadFileException|CantPersistFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantAddResourceToSkinException(CantAddResourceToSkinException.DEFAULT_MESSAGE, e, "Can't add file to the structure.", "");
            }
        } catch (CantGetWalletFactoryProjectSkinStructureException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAddResourceToSkinException(CantAddResourceToSkinException.DEFAULT_MESSAGE, e, "Can't get language xml file", "");
        }
    }

    @Override
    public void updateResourceToSkin(Resource resource, byte[] file, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantUpdateResourceToSkinException, ResourceNotFoundException {
        try {
            String resourcePath = createResourcePath(resource.getResourceType(), walletFactoryProjectSkin.getPath());

            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, resourcePath, resource.getFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            newFile.setContent(file);
            newFile.persistToMedia();
        } catch (CantCreateFileException|CantLoadFileException|CantPersistFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateResourceToSkinException(CantUpdateResourceToSkinException.DEFAULT_MESSAGE, e, "Can't update file in the structure.", "");
        } catch (FileNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE, e, "Can't find resource in the structure.", "");
        }
    }

    @Override
    public void deleteResourceFromSkin(Resource resource, WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantDeleteResourceFromSkinException, ResourceNotFoundException {
        try {
            Skin skin = getSkinStructure(walletFactoryProjectSkin);
            skin.deleteResource(resource);
            try {
                String resourcePath = createResourcePath(resource.getResourceType(), walletFactoryProjectSkin.getPath());


                PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, resourcePath, resource.getFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                //newFile.deleteFile();
                // TODO DELETE FILE IN PLUGIN FILE SYSTEM
                try {
                    setSkinStructureXml(skin, walletFactoryProjectSkin);
                } catch (CantSetWalletFactoryProjectSkinStructureException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantDeleteResourceFromSkinException(CantDeleteResourceFromSkinException.DEFAULT_MESSAGE, e, "Can't set language xml file", "");
                }
            } catch (CantCreateFileException|CantLoadFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantDeleteResourceFromSkinException(CantDeleteResourceFromSkinException.DEFAULT_MESSAGE, e, "Can't delete file from the structure.", "");
            } catch (FileNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE, e, "Can't find resource in the structure.", "");
            }
        } catch (CantGetWalletFactoryProjectSkinStructureException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDeleteResourceFromSkinException(CantDeleteResourceFromSkinException.DEFAULT_MESSAGE, e, "Can't get language xml file", "");
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
