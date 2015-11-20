package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;

import java.util.Objects;


/**
 * Created by Furszyfer Matias on 2015.07.23..
 */
public interface FermatScreenSwapper {

    public void changeScreen(String screen,int idContainer,Object[] objects);

    public void selectWallet(InstalledWallet installedWallet);

    public void changeActivity(String activityName,String appBackPublicKey, Object... objects);

    public void selectSubApp(InstalledSubApp installedSubApp);

    public void changeWalletFragment(String walletCategory, String walletType,String walletPublicKey, String fragmentType);

    public void onCallbackViewObserver(FermatCallback fermatCallback);

    void connectWithOtherApp(Engine emgine,Object[] objectses);

    Object[] connectBetweenAppsData();
}
