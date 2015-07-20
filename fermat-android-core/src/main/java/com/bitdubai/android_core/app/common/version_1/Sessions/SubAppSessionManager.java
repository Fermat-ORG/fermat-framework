package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class SubAppSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppSessionManager{

    private Set<SubAppsSession> lstSubAppSession;

    public SubAppSessionManager(){
        lstSubAppSession= new HashSet<SubAppsSession>();
    }


    @Override
    public Set<SubAppsSession> listOpenWallets() {
        return lstSubAppSession;
    }

    @Override
    public boolean openSubAppSession(SubApps subApps) {
        return lstSubAppSession.add(new SubAppSession(subApps));
    }

    @Override
    public boolean closeSubAppSession(SubApps subApps) {
        return lstSubAppSession.remove(new SubAppSession(subApps));
    }

    @Override
    public boolean isSubAppOpen(SubApps subApps) {
        return lstSubAppSession.contains(subApps);
    }


}
