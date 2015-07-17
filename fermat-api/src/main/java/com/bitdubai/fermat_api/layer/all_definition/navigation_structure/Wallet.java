package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.*;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Wallets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class Wallet implements com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Wallet {

    Wallets type;
    Map<Activities, com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Activity> activities = new HashMap<Activities, com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Activity>();


    /**
     * RuntimeWallet interface implementation.
     */

    public void setType(Wallets type) {
        this.type = type;
    }

    public void addActivity (com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Activity activity){
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
