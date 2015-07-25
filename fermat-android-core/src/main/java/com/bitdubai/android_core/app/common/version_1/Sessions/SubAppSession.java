package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class SubAppSession implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession{


    /**
     * SubApps type
     */
    SubApps subApps;

    /**
     * Active objects in wallet session
     */
    Map<String,Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    /**
     * Tool Manager
     */
    private ToolManager toolManager;

    /**
     * Event manager.
     */
    // Ver si esto va acá
    //private EventManager eventManager;



    public SubAppSession(SubApps subApps,ErrorManager errorManager,ToolManager toolManager){
        this.subApps=subApps;
        data= new HashMap<String,Object>();
        this.errorManager=errorManager;
        this.toolManager=toolManager;
    }

    public SubAppSession(SubApps subApps) {
        this.subApps = subApps;
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
    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public ToolManager getToolManager() {
        return toolManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubAppSession that = (SubAppSession) o;

        return subApps == that.subApps;

    }

    @Override
    public int hashCode() {
        return subApps.hashCode();
    }
}
