package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletFactoryProjectResource</code>
 * indicates the methods of a WalletFactoryProjectResource
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletFactoryProjectResource {

    // name of the resource
    String getName();

    // resource file
    byte[] getResource();

    // resource type
    ResourceType getResourceType();

    // project skin to which it belongs
    WalletFactoryProjectSkin getWalletFactoryProjectSkin();

}
