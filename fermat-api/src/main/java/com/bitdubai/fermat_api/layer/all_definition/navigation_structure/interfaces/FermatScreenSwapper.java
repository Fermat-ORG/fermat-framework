package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;

/**
 * Created by Furszyfer Matias on 2015.07.23..
 */
public interface FermatScreenSwapper {

    public void changeScreen(String screen,Object[] objects);

    public void selectWallet(InstalledWallet installedWallet);

    public void changeActivity(String activity,Object... objects);

}
