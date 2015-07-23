package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by mati on 2015.07.20..
 */
public interface SubAppSessionManager {


    public SubAppsSession openSubAppSession(SubApps subApps, ErrorManager addon, ToolManager plugin);
    public boolean closeSubAppSession(SubApps subApps);
    public Map<SubApps,SubAppsSession> listOpenSubApps();
    public boolean isSubAppOpen(SubApps subApps);

}
