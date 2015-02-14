package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activities;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activity;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Wallet;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Wallets;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeWallet implements Wallet {

    Wallets type;
    Map<Activities, Activity> activities;


    /**
     * RuntimeWallet interface implementation.
     */

    public void addActivity (Activity activity){
        activities.put(activity.getType(), activity);
    }



    /**
     * Wallet interface implementation.
     */

    public Wallets getType() {
        return type;
    }

    public void setType(Wallets type) {
        this.type = type;
    }
}
