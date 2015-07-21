package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.common.ResourceTypeAdapter;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.util.UUID;

import ae.javax.xml.bind.Unmarshaller;
import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.XmlTransient;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource</code>
 * implementation of WalletFactoryProjectResource.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement( name = "resource" )
public class WalletFactoryMiddlewareProjectResource implements DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProjectResource {

    private String name;

    private byte[] resource;

    private ResourceType resourceType;

    private WalletFactoryProjectSkin walletFactoryProjectSkin;

    public WalletFactoryMiddlewareProjectResource() {
    }

    public WalletFactoryMiddlewareProjectResource(String name, ResourceType resourceType) {
        this.name = name;
        this.resourceType = resourceType;
    }

    public WalletFactoryMiddlewareProjectResource(String name, byte[] resource, ResourceType resourceType) {
        this.name = name;
        this.resource = resource;
        this.resourceType = resourceType;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public byte[] getResource() throws CantGetWalletFactoryProjectResourceException {
        if (resource != null) {
            return resource;
        } else {
            try {
                PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, walletFactoryProjectSkin.getResourceTypePath(resourceType), name, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                return newFile.getContent();
            } catch (CantCreateFileException | FileNotFoundException | CantLoadFileException e) {
                throw new CantGetWalletFactoryProjectResourceException(CantGetWalletFactoryProjectResourceException.DEFAULT_MESSAGE, e, "Can't get file.", "");
            }
        }
    }

    @XmlJavaTypeAdapter( ResourceTypeAdapter.class )
    @XmlAttribute( name = "type" )
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public WalletFactoryProjectSkin getWalletFactoryProjectSkin() {
        return walletFactoryProjectSkin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void afterUnmarshal(Unmarshaller u, Object parent) {
        WalletFactoryMiddlewareProjectSkin walletFactoryMiddlewareProjectSkin = (WalletFactoryMiddlewareProjectSkin) parent;
        walletFactoryProjectSkin = walletFactoryMiddlewareProjectSkin;
        setPluginFileSystem(walletFactoryMiddlewareProjectSkin.getPluginFileSystem());
        setPluginId(walletFactoryMiddlewareProjectSkin.getPluginId());
    }

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private UUID pluginId;

    /**
     * DealsWithPluginFileSystem interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
