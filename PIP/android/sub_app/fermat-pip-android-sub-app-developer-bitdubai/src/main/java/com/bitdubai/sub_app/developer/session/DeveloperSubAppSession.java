package com.bitdubai.sub_app.developer.session;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class DeveloperSubAppSession implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession{


    /**
     * SubApps type
     */
    SubApps subApps;

    /**
     * Active objects in wallet session
     */
    Map<String,Object> data;

    private ErrorManager errorManager;

    private ToolManager toolManager;


    public DeveloperSubAppSession(final SubApps subApps, final ErrorManager errorManager, final ToolManager toolManager){
        this.subApps=subApps;
        data= new HashMap<>();
        this.errorManager=errorManager;
        this.toolManager=toolManager;
    }

    public DeveloperSubAppSession(SubApps subApps) {
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

        DeveloperSubAppSession that = (DeveloperSubAppSession) o;

        return subApps == that.subApps;

    }

    @Override
    public int hashCode() {
        return subApps.hashCode();
    }
}
