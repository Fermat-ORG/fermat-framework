package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.UUID;

/**
 * This class gives us a language package information
 *
 * @author Ezequiel Postan - (ezequiel.postan@gmail.com)
 */
public interface Language {

    public Version getVersion();

    /**
     * This method gives us the identifier of the language package
     *
     * @return the language id
     */
    public UUID getLanguageId();

    /**
     * This method gives us the name of the language
     *
     * @return an enum representing the language
     */
    public Languages getLanguageName();

    /**
     * This method gives us the size of the language package
     *
     * @return an int representing the language package size
     */
    public int getLanguagePackageSizeInBytes();

    /**
     * This method gives us the name of the author of this translation
     *
     * @return the alias of the translator
     */
    public String getTranslatorName();

    /**
     * This method gives us the public key of the author of this translation
     *
     * @return the public key of the translator
     */
    public String getTranslatorPublicKey();
}
