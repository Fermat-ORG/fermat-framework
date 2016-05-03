package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;

/**
 * Created by MAtias Furszyfer on 2016.03.09..
 */
public interface DesktopAppSelector {

    void selectWallet(InstalledWallet installedWallet);

    void selectSubApp(InstalledSubApp installedSubApp);

    void selectApp(FermatApp installedApp);

}
