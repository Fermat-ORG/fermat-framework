package com.bitdubai.fermat_api.layer.all_definition.runtime;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;

import java.io.Serializable;

/**
 * Created by mati on 2015.11.19..
 */

public interface FermatApp extends Serializable {

    String getAppName();

    String getAppPublicKey();

    AppsStatus getAppStatus();

    FermatAppType getAppType();

//    AppStructureType getAppStructureType();

    byte[] getAppIcon();

    int getIconResource();

    void setBanner(int res);

    public int getBannerRes();
}
