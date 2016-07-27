package com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager;

import com.bitdubai.fermat_api.layer.dmp_module.AppManager;

import java.util.Collection;

/**
 * Created by Matias Furszyfer on 2015.12.06..
 */
public interface SubAppManager extends AppManager {

    Collection<InstalledSubApp> getUserSubApps() throws CantGetUserSubAppException;

    InstalledSubApp getSubApp(String subAppCode);

}
