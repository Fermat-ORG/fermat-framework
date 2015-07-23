package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureFromXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceAlreadyExistsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ResourceNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.JAXBException;
import ae.javax.xml.bind.Marshaller;
import ae.javax.xml.bind.Unmarshaller;
import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlElementWrapper;
import ae.javax.xml.bind.annotation.XmlElements;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.XmlTransient;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectSkin</code>
 * implementation of WalletFactoryProjectResource.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement( name = "skin" )
public class WalletFactoryMiddlewareProjectSkin implements DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProjectSkin {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private String hash;

    private List<WalletFactoryProjectResource> resources = new ArrayList<>();

    private WalletFactoryProjectProposal walletFactoryProjectProposal;


    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProjectSkin() {
    }

    public WalletFactoryMiddlewareProjectSkin(String name, String hash, List<WalletFactoryProjectResource> resources) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.hash = hash;
        this.resources = resources;
    }

    /**
     * private Class getters
     */
    @XmlAttribute( required=true )
    @Override
    public UUID getId() {
        return id;
    }

    @XmlElement( required=true )
    public String getName() {
        return name;
    }

    @XmlElement( required=true )
    public String getHash() {
        return hash;
    }

    @XmlElements({
            @XmlElement(name="resource", type=WalletFactoryMiddlewareProjectResource.class),
    })
    @XmlElementWrapper
        public List<WalletFactoryProjectResource> getResources() {
        return resources;
    }

    @Override
    public WalletFactoryProjectProposal getWalletFactoryProjectProposal() {
        return walletFactoryProjectProposal;
    }

    /**
     * custom getter
     * @param resourceType
     * @return al the resources of an specific type, if there isn't returns an empty arraylist
     */
    @Override
    public List<WalletFactoryProjectResource> getAllResourcesByResourceType(ResourceType resourceType) {
        if (resources == null) {
            return new ArrayList<>();
        } else {
            List<WalletFactoryProjectResource> collected = new ArrayList<>();
            for(WalletFactoryProjectResource res : resources) {
                if (res.getResourceType().equals(resourceType)) collected.add(res);
            }

            return collected;
        }
    }

    /**
     * get an specific resource
     * @param fileName
     * @param resourceType
     * @return an instance of the specific resource
     * @throws CantGetWalletFactoryProjectResourceException if cant get the resource
     */
    @Override
    public WalletFactoryProjectResource getResource(String fileName, ResourceType resourceType) throws CantGetWalletFactoryProjectResourceException, ResourceNotFoundException {
        if (resources == null) {
            throw new CantGetWalletFactoryProjectResourceException(CantGetWalletFactoryProjectResourceException.DEFAULT_MESSAGE, null, "No resources available.", "");
        } else {
            for(WalletFactoryProjectResource res : resources) {
                if (res.getFileName().equals(fileName) && res.getResourceType().equals(resourceType))
                    return res;
            }
            throw new ResourceNotFoundException(ResourceNotFoundException.DEFAULT_MESSAGE, null, "Resource not found.", "");
        }
    }

    /**
     * WalletFactoryProjectSkin implementation methods
     */

    /**
     * add a resource to the current skin
     * @param name
     * @param resource
     * @param resourceType
     * @throws CantAddWalletFactoryProjectResourceException
     */
    @Override
    public void addResource(String name, String fileName, byte[] resource, ResourceType resourceType) throws CantAddWalletFactoryProjectResourceException, ResourceAlreadyExistsException {
        try {
            getResource(fileName, resourceType);
            throw new ResourceAlreadyExistsException(ResourceAlreadyExistsException.DEFAULT_MESSAGE, null, "Can't add resource.", "Resource Already exists.");
        } catch (CantGetWalletFactoryProjectResourceException|ResourceNotFoundException e) {
            // nothing to do
        }
        try {
            PluginBinaryFile newFile = pluginFileSystem.createBinaryFile(pluginId, getResourceTypePath(resourceType), fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            newFile.setContent(resource);
            newFile.persistToMedia();
            // TODO ADD TO RESOURCES LIST THE NEW RESOURCE
        } catch (CantPersistFileException |CantCreateFileException |CantLoadFileException e) {
            throw new CantAddWalletFactoryProjectResourceException(CantAddWalletFactoryProjectResourceException.DEFAULT_MESSAGE, e, "Can't add resource.", "");
        }
    }

    /**
     * UPDATE a resource from the current skin
     * @param fileName
     * @param resource
     * @param resourceType
     * @throws CantUpdateWalletFactoryProjectResourceException
     */
    @Override
    public void updateResource(String fileName, byte[] resource, ResourceType resourceType) throws CantUpdateWalletFactoryProjectResourceException {
        // TODO CHANGE ID BEHAVIOR
        try {
            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, getResourceTypePath(resourceType), fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            newFile.setContent(resource);
            newFile.persistToMedia();
        } catch (FileNotFoundException |CantCreateFileException|CantPersistFileException|CantLoadFileException e) {
            throw new CantUpdateWalletFactoryProjectResourceException(CantUpdateWalletFactoryProjectResourceException.DEFAULT_MESSAGE, e, "Can't update resource.", "");
        }
    }

    /**
     * delete a resource from the current skin
     * @param fileName
     * @param resource
     * @param resourceType
     * @throws CantDeleteWalletFactoryProjectResourceException
     */
    @Override
    public void deleteResource(String fileName, byte[] resource, ResourceType resourceType) throws CantDeleteWalletFactoryProjectResourceException {
        try {
            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, getResourceTypePath(resourceType), fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            //newFile.deleteFile();
            // TODO DELETE FILE IN PLUGINFILESYSTEM
            // TODO DELETE FROM THE CURRENT RESOURCES LIST
        } catch (FileNotFoundException|CantCreateFileException e) {
            throw new CantDeleteWalletFactoryProjectResourceException(CantDeleteWalletFactoryProjectResourceException.DEFAULT_MESSAGE, e, "Can't delete resource.", "");
        }
    }

    /**
     * construct the path of the project skins
     * @param resourceType
     * @return project skins path
     */
    public String getResourceTypePath(ResourceType resourceType) {
        String initialPath = "wallet_factory_projects";
        String skinPath = "skins";

        WalletFactoryProjectProposal projectProposal = getWalletFactoryProjectProposal();
        WalletFactoryProject project = projectProposal.getProject();

        return initialPath + "/" +
                project.getName() + "/" +
                projectProposal.getAlias() + "/" +
                skinPath + "/" +
                getName() + "/" +
                resourceType.value();
    }

    @Override
    public String getSkinXml(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantGetObjectStructureXmlException {
        try {
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Writer outputStream = new StringWriter();
            jaxbMarshaller.marshal(walletFactoryProjectSkin, outputStream);

            return outputStream.toString();
        } catch (JAXBException e) {
            throw new CantGetObjectStructureXmlException(CantGetObjectStructureXmlException.DEFAULT_MESSAGE, e, "Can't get Skin XML.", "");
        }
    }

    @Override
    public WalletFactoryProjectSkin getSkinFromXml(String stringXml) throws CantGetObjectStructureFromXmlException {
        try {
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(stringXml);
            return (WalletFactoryMiddlewareProjectSkin) jaxbUnmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new CantGetObjectStructureFromXmlException(CantGetObjectStructureFromXmlException.DEFAULT_MESSAGE, e, "Can't get Skin from XML.", "");
        }
    }

    /**
     * private Class setters
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setResources(List<WalletFactoryProjectResource> resources) {
        this.resources = resources;
    }

    /**
     * set parent after unmarshal (xml conversion)
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        if (parent != null) {
            WalletFactoryMiddlewareProjectProposal walletFactoryMiddlewareProjectProposal = (WalletFactoryMiddlewareProjectProposal) parent;
            walletFactoryProjectProposal = walletFactoryMiddlewareProjectProposal;
            setPluginFileSystem(walletFactoryMiddlewareProjectProposal.getPluginFileSystem());
            setPluginId(walletFactoryMiddlewareProjectProposal.getPluginId());
        }
    }


    /**
     * DealsWithPluginFileSystem interface variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    private UUID pluginId;

    /**
     * DealsWithPluginFileSystem interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @XmlTransient
    public PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @XmlTransient
    public UUID getPluginId() {
        return pluginId;
    }
}
