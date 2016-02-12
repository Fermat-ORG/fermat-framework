package com.bitdubai.fermat_api.layer.all_definition.runtime;

import com.bitdubai.fermat_api.AppsStatus;

/**
 * Created by mati on 2015.11.19..
 */

public interface FermatApp {

    String getAppName();

    String getAppPublicKey();

    AppsStatus getAppStatus();
}
