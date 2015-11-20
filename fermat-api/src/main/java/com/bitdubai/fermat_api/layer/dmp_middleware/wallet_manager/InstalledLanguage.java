package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.UUID;

/**
 * Created by eze on 2015.07.19..
 */
public interface InstalledLanguage {

    /**
     * This method gives us the language package identifier
     *
     * @return the identifier
     */
    public UUID getId();

    /**
     * This method gives us the language of the package
     *
     * @return the language
     */
    public Languages getLanguage();

    /**
     * This method gives us the label of the language package. </p></>
     * E.g: the language could be english and the label UK.
     *
     * @return the label of the language
     */
    public String getLabel();

    /**
     * This method gives us the version of the language package
     *
     * @return the version
     */
    public Version getVersion();

}
