package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by lnacosta on 2015.07.17..
 */
public class WalletFactoryMiddlewareProjectResource {

    private String name;

    private ResourceType resourceType;

    public WalletFactoryMiddlewareProjectResource() {}

    public WalletFactoryMiddlewareProjectResource(String name, ResourceType resourceType) {
        this.name = name;
        this.resourceType = resourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    @XmlJavaTypeAdapter( ResourceTypeAdapter.class )
    @XmlElement( name = "resourcetype" )
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

}