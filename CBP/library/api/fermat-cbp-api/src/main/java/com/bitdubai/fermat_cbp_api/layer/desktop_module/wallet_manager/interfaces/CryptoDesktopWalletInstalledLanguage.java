package com.bitdubai.fermat_cbp_api.layer.desktop_module.wallet_manager.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.UUID;

/**
 * Created by natalia on 16/09/15.
 */
public interface CryptoDesktopWalletInstalledLanguage {
    /**
     * This method gives us the language wallet identifier
     *
     * @return the identifier
     */
    public UUID getId();

    /**
     * This method gives us the language of the wallet
     *
     * @return the language
     */
    public Languages getLanguage();

    /**
     * This method gives us the label of the language wallet. </p></>
     * E.g: the language could be english and the label UK.
     *
     * @return the label of the language
     */
    public String getLabel();

    /**
     * This method gives us the version of the language wallet
     *
     * @return the version
     */
    public Version getVersion();

}
