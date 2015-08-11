package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.util.UUID;

/**
 * Created by eze on 2015.07.14..
 */
public interface WalletFactoryProject {

    /**
     * @return the developerPublicKey of the developer who is working with this project
     */
    String getDeveloperPublicKey();

    /**
     * @return the id of the project
     */
    UUID getId();

    /**
     * @return the name of the project
     */
    String getName();

    /**
     * @return the walletType of the project
     */
    Wallets getType();

    String getPath();






}
