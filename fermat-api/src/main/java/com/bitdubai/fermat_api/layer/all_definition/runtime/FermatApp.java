package com.bitdubai.fermat_api.layer.all_definition.runtime;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;

/**
 * Created by mati on 2015.11.19..
 */

public interface FermatApp {

    String getAppName();

    String getAppPublicKey();

    AppsStatus getAppStatus();

    FermatAppType getAppType();
}
