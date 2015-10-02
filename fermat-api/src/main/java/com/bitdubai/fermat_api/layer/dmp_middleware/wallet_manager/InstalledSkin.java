package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import java.util.UUID;

/**
 * Created by eze on 2015.07.19..
 */
public interface InstalledSkin {

    /**
     * This method gives us the name (alias) of an skin
     *
     * @return the skin name
     */
    public String getAlias();

    /**
     * This method gives us the identifier of an skin
     *
     * @return the skin identifier
     */
    public UUID getId();

    /**
     * This method gives us the name of the preview image of the skin
     *
     * @return the name of the image used to reach it in the wallet resources plug-in
     */
    public String getPreview();

    /**
     * This method gives us the version of the skin
     *
     * @return the skin version
     */
    public Version getVersion();
}
