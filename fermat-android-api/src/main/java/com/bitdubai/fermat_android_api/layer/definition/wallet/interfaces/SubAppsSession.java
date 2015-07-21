package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

import java.util.List;

/**
 * Created by mati on 2015.07.20..
 */
public interface SubAppsSession {

    public SubApps getSubAppSessionType();
    public void setData (String key,Object object);
    public Object getData (String key);
}
