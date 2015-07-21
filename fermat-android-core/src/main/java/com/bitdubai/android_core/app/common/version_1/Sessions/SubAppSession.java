package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by mati on 2015.07.20..
 */
public class SubAppSession implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession{

    SubApps subApps;
    Map<String,Object> data;



    public SubAppSession(SubApps subApps){
        this.subApps=subApps;
        data= new HashMap<String,Object>();
    }


    @Override
    public SubApps getSubAppSessionType() {
        return subApps;
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key,object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }
}
