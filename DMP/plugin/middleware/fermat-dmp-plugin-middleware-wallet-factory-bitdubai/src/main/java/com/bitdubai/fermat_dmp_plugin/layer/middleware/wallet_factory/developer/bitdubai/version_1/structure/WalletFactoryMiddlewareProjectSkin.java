package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectResourceException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
     * DealsWithPluginFileSystem interface variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    private UUID pluginId;

    private String name;

    private String hash;

    private List<WalletFactoryProjectResource> resources = new ArrayList<>();

    private WalletFactoryProjectProposal walletFactoryProjectProposal;

    public WalletFactoryMiddlewareProjectSkin() {
    }

    public WalletFactoryMiddlewareProjectSkin(String name, String hash, List<WalletFactoryProjectResource> resources) {
        this.name = name;
        this.hash = hash;
        this.resources = resources;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
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

    @Override
    public WalletFactoryProjectResource getResource(String name, ResourceType resourceType) throws CantGetWalletFactoryProjectResourceException {
        if (resources == null) {
            throw new CantGetWalletFactoryProjectResourceException(CantGetWalletFactoryProjectResourceException.DEFAULT_MESSAGE, null, "No resources available.", "");
        } else {
            for(WalletFactoryProjectResource res : resources) {
                if (res.getName().equals(name) && res.getResourceType().equals(resourceType))
                    return res;
            }
            throw new CantGetWalletFactoryProjectResourceException(CantGetWalletFactoryProjectResourceException.DEFAULT_MESSAGE, null, "Resource not found.", "");
        }
    }

    @Override
    public void addResource(String name, byte[] resource, ResourceType resourceType) throws CantAddWalletFactoryProjectResourceException {
        try {
            PluginBinaryFile newFile = pluginFileSystem.createBinaryFile(pluginId, getResourceTypePath(resourceType), name, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            newFile.setContent(resource);
            newFile.persistToMedia();
        } catch (CantPersistFileException |CantCreateFileException |CantLoadFileException e) {
            throw new CantAddWalletFactoryProjectResourceException(CantAddWalletFactoryProjectResourceException.DEFAULT_MESSAGE, e, "Can't add resource.", "");
        }
    }

    @Override
    public void updateResource(String name, byte[] resource, ResourceType resourceType) throws CantUpdateWalletFactoryProjectResourceException {
        try {
            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, getResourceTypePath(resourceType), name, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            newFile.loadFromMedia();
            newFile.setContent(resource);
            newFile.persistToMedia();
        } catch (FileNotFoundException |CantCreateFileException|CantPersistFileException|CantLoadFileException e) {
            throw new CantUpdateWalletFactoryProjectResourceException(CantUpdateWalletFactoryProjectResourceException.DEFAULT_MESSAGE, e, "Can't update resource.", "");
        }
    }

    @Override
    public void deleteResource(String name, byte[] resource, ResourceType resourceType) throws CantDeleteWalletFactoryProjectResourceException {
        try {
            PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, getResourceTypePath(resourceType), name, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            //newFile.deleteFile();
            // TODO DELETE FILE IN PLUGINFILESYSTEM
        } catch (FileNotFoundException|CantCreateFileException e) {
            throw new CantDeleteWalletFactoryProjectResourceException(CantDeleteWalletFactoryProjectResourceException.DEFAULT_MESSAGE, e, "Can't delete resource.", "");
        }
    }

    public String getResourceTypePath(ResourceType resourceType) {
        String initialPath = "wallet_factory_projects";
        String resourcePath = "";
        WalletFactoryProjectProposal projectProposal = getWalletFactoryProjectProposal();
        WalletFactoryProject project = projectProposal.getProject();

        switch(resourceType) {
            case VIDEO:
                resourcePath = "videos";
                break;
            case SOUND:
                resourcePath = "sounds";
                break;
            case FONT_STYLE:
                resourcePath = "font_styles";
                break;
            case IMAGE:
                resourcePath = "images";
                break;
            case LAYOUT:
                resourcePath = "layouts";
                break;
        }

        return initialPath + "/" +
               project.getName() + "/" +
               projectProposal.getAlias() + "/" +
               getName() + "/" +
               resourcePath;
    }


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
