package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;


/**
 * Created by Furszyfer Matias on 2015.07.23..
 */
public interface FermatScreenSwapper {

    void changeScreen(String screen, int idContainer, Object[] objects);

    void selectWallet(InstalledWallet installedWallet);

    void changeActivity(String activityName, String appBackPublicKey, Object... objects);

    void selectSubApp(InstalledSubApp installedSubApp);

    void changeWalletFragment(String walletCategory, String walletType, String walletPublicKey, String fragmentType);

    void onCallbackViewObserver(FermatCallback fermatCallback);

    void connectWithOtherApp(Engine emgine,String fermatAppPublicKey,Object[] objectses);

    Object[] connectBetweenAppsData();

    void onControlledActivityBack(String activityCodeBack);

    void setChangeBackActivity(Activities activityCodeBack);
}
