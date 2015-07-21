package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import java.util.Set;

/**
 * Created by mati on 2015.07.20..
 */
public interface SubAppSessionManager {

    public Set<SubAppsSession> listOpenWallets();
    public boolean openSubAppSession(SubApps subApps);
    public boolean closeSubAppSession(SubApps subApps);
    public boolean isSubAppOpen(SubApps subApps);

}
