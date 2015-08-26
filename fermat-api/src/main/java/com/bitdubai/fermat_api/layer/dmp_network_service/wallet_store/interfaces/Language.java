package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.*;

import java.net.URL;
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
    public UUID getLanguageId();
    public UUID getWalletId();
    public Languages getLanguageName();
    public String getLanguageLabel();

    /**
     * Language file information
     */
    public int getLanguagePackageSizeInBytes();
    //public URL getFileURL(); //Eliminar

    /**
     * Version information, current, Initial and Final.
     */
    public Version getVersion();
    public Version getInitialWalletVersion();
    public Version getFinalWalletVersion();

    /**
     * Translator information
     */
    public com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces.Translator getTranslator();

    public boolean isDefault();


}
