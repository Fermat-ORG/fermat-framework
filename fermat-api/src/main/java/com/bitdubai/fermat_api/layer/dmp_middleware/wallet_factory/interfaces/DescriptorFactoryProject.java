package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;

import java.util.UUID;

/**
 * Created by eze on 2015.07.14..
 */
public interface DescriptorFactoryProject {

    /**
     * @return the developerPublicKey of the developer who is working with this project
     */
    String getDeveloperPublicKey();

    /**
     * @return the getPublisherIdentityKey of the developer who is working with this project
     */
    String getPublisherIdentityKey();

    /**
     * @return the id of the project
     */
    UUID getId();
    UUID getWalletsId();
    /**
     * @return the name of the project
     */
    String getName();

    /**
     * @return the walletType of the project
     */
    Wallets getWallestType();

    String getPath();

    DescriptorFactoryProjectType getDescriptorProjectType();

    String getDescription();
}
