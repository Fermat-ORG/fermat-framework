package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by francisco on 21/10/15.
 */
public class AssetIssuerCommunitySubAppSession implements SubAppsSession {

    private Map<String, Object> data;
    private SubApps subAppType;
    private ErrorManager errorManager;

    private AssetIssuerCommunitySubAppModuleManager manager;


    public AssetIssuerCommunitySubAppSession(SubApps type, ErrorManager errorManager, AssetIssuerCommunitySubAppModuleManager manager) {
        this.subAppType = type;
        this.errorManager = errorManager;
        this.manager = manager;
    }


    @Override
    public SubApps getSubAppSessionType() {
        return subAppType;
    }

    @Override
    public void setData(String key, Object object) {
        if (data == null)
            data = new HashMap<>();
        data.put(key, object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public AssetIssuerCommunitySubAppModuleManager getManager() {
        return manager;
    }
}
