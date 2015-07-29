package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.UUID;

/**
 * Created by ciencias on 7/15/15.
 */
public interface WalletFactoryProjectSkin {

    /**
     * @return the id of the project skin
     */
    UUID getId();

    /**
     * @return the name of the project skin
     */
    String getName();

    /**
     * @return the designer public key of the translator who is working with this skin
     */
    String getDesignerPublicKey();

    /**
     * @return the version of the project skin
     */
    Version getVersion();

    String getPath();

}
