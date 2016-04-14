package com.bitdubai.fermat_api.layer.all_definition.callback;

import com.bitdubai.fermat_api.AppsStatus;

/**
 * Created by mati on 2016.02.10..
 */
public interface AppStatusCallbackChanges {

    void appSoftwareStatusChanges(AppsStatus appsStatus);

    void clear();

}
