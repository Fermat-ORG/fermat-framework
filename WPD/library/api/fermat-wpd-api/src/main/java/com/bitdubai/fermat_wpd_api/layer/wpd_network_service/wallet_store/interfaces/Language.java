package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.TranslatorIdentity;

import java.util.UUID;

/**
 * This class gives us a language package information
 *
 * @author Ezequiel Postan - (ezequiel.postan@gmail.com)
 */
public interface Language {

    /**
     * Language identifiers
     */
    UUID getLanguageId();

    UUID getWalletId(); //Todo: Refactor a String para que acepte PublicKey

    Languages getLanguageName();

    String getLanguageLabel();

    /**
     * Language file information
     */
    int getLanguagePackageSizeInBytes();
    //public URL getFileURL(); //Eliminar

    /**
     * Version information, current, Initial and Final.
     */
    Version getVersion();

    Version getInitialWalletVersion();

    Version getFinalWalletVersion();

    /**
     * Translator information
     */
    TranslatorIdentity getTranslator();

    boolean isDefault();


}
