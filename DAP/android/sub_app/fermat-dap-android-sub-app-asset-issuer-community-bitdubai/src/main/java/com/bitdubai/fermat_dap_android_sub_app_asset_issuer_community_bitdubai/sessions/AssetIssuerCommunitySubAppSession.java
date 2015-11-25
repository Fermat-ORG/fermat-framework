package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by francisco on 21/10/15.
 */
public class AssetIssuerCommunitySubAppSession extends AbstractFermatSession<InstalledSubApp,AssetIssuerCommunitySubAppModuleManager,SubAppResourcesProviderManager> implements SubAppsSession {

    private AssetIssuerCommunitySubAppModuleManager manager;

    private ErrorManager errorManager;
    private SubApps subAppType;
    private Map<String, Object> data;

    public AssetIssuerCommunitySubAppSession(InstalledSubApp subApp, ErrorManager errorManager, AssetIssuerCommunitySubAppModuleManager manager) {
        super(subApp.getAppPublicKey(),subApp,errorManager,manager,null);
        this.errorManager = errorManager;
        this.manager = manager;
    }


    @Override
    public InstalledSubApp getSubAppSessionType() {
        return getFermatApp();
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
