package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.UUID;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectLanguage {

    /**
     * @return the id of the project language
     */
    UUID getId();

    /**
     * @return the name of the project language
     */
    String getName();

    /**
     * @return the type of the project language
     */
    Languages getType();

    /**
     * @return the version of the project language
     */
    Version getVersion();

    /**
     * @return the translator public key of the translator who is working with this language
     */
    String getTranslatorPublicKey();

    String getPath();

}
