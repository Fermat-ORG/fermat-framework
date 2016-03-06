package com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager;

import java.util.Collection;

/**
 * Created by Matias Furszyfer on 2015.12.06..
 */
public interface SubAppManager {

    Collection<InstalledSubApp> getUserSubApps() throws CantGetUserSubAppException;

    InstalledSubApp getSubApp(String subAppCode);

    InstalledSubApp getInstalledSubApp(String platformIdentifier);
}
