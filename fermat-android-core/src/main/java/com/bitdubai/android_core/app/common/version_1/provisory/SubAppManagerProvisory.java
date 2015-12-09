package com.bitdubai.android_core.app.common.version_1.provisory;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.CantGetUserSubAppException;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.SubAppManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mati on 2015.12.06..
 */
public class SubAppManagerProvisory implements SubAppManager{

    Map<String,InstalledSubApp> installedSubApps;

    public SubAppManagerProvisory() {
        installedSubApps = new HashMap<>();
        loadMap(installedSubApps);

    }

    private void loadMap(Map<String,InstalledSubApp> lstInstalledSubApps){
        InstalledSubApp installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Wallet Users","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0));
        lstInstalledSubApps.put(installedSubApp.getSubAppType().getCode(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Wallet Users","public_key_ccp_intra_user_identity","intra_user_identity_sub_app",new Version(1,0,0));
        lstInstalledSubApps.put(installedSubApp.getSubAppType().getCode(), installedSubApp);
    }

    @Override
    public Collection<InstalledSubApp> getUserSubApps() throws CantGetUserSubAppException {
        return installedSubApps.values();
    }

    @Override
    public InstalledSubApp getSubApp(String subAppCode) {
        return installedSubApps.get(subAppCode);
    }
}
