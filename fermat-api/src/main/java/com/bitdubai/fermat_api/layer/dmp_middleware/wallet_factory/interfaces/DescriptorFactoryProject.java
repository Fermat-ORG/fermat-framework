package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;

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
    /**
     * @return the walletid of the project
     */
    UUID getWalletId();
    /**
     * @return the name of the project
     */
    String getName();

    /**
     * @return the walletType of the project
     */
    Wallets getWalletType();
    /**
     * @return the path of the project
     */
    String getPath();
    /**
     * @return the projectstate of the project
     */
    FactoryProjectState getState();
    /**
     * @return the descriptor type of the project
     */
    DescriptorFactoryProjectType getDescriptorProjectType();
    /**
     * @return the description of the project
     */
    String getDescription();


    public int getDefaultSizeInBytes();
}
