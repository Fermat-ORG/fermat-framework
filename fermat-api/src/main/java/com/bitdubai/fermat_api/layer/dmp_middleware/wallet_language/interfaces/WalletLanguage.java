package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.enums.LanguageState;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.middleware.wallet_language.interfaces.WalletLanguage</code>
 * indicates the functionality of a WalletLanguage
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletLanguage {

    /**
     * @return the unique id of the language
     */
    UUID getId();

    /**
     * @return the id of the language (can be repeated with different version)
     */
    UUID getLanguageId();

    /**
     * @return the name of the language
     */
    String getName();

    /**
     * @return the type of the language
     */
    Languages getType();

    /**
     * @return the state of the language
     */
    LanguageState getState();

    /**
     * @return the translator public key of the translator who is working with this language
     */
    String getTranslatorPublicKey();

    /**
     * @return the version of the language
     */
    Version getVersion();

}
