package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;

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

    UUID getId();

    // name of the resource
    String getName();

    // resource file
    byte[] getResource() throws CantGetWalletFactoryProjectResourceException;

    // resource type
    ResourceType getResourceType();

    // skin to which belongs
    WalletFactoryProjectSkin getWalletFactoryProjectSkin();

}
