package com.bitdubai.desktop.sub_app_manager.util;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;

import java.util.List;

/**
 * Created by nelson on 22/09/15.
 */
public interface SubAppListGenerator {
    List<InstalledSubApp> createSubAppsList();
}
