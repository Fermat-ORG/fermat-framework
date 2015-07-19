package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource</code>
 * implementation of WalletFactoryProjectResource.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProjectResource implements WalletFactoryProjectResource {

    private String name;

    private byte[] resource;

    private ResourceType resourceType;

    private WalletFactoryProjectProposal walletFactoryProjectProposal;


    public WalletFactoryMiddlewareProjectResource(String name, byte[] resource, ResourceType resourceType, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.name = name;
        this.resource = resource;
        this.resourceType = resourceType;
        this.walletFactoryProjectProposal = walletFactoryProjectProposal;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] getResource() {
        return resource;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public WalletFactoryProjectProposal getWalletFactoryProjectProposal() {
        return walletFactoryProjectProposal;
    }
}
