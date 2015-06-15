package com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Activity;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Wallet;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Wallets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeWallet implements Wallet {

    Wallets type;
    Map<Activities, Activity> activities = new HashMap<Activities, Activity>();


    /**
     * RuntimeWallet interface implementation.
     */

    public void setType(Wallets type) {
        this.type = type;
    }
    
    public void addActivity (Activity activity){
        activities.put(activity.getType(), activity);
    }



    /**
     * Wallet interface implementation.
     */

    @Override
    public Wallets getType() {
        return type;
    }


}
