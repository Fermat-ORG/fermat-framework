package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;



import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.23..
 */
public class Wallet implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet{

    String walletPlatformIdentifier;

    Wallets type;

    WalletCategory walletCategory;

    Map<Activities, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity> activities = new HashMap<Activities, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity>();

    /**
     * Main screen of the wallet
     */
    private Activities startActivity;
    /**
     * Last screen used
     */
    private Activities lastActivity;


    /**
     * RuntimeWallet interface implementation.
     */

    public void setType(Wallets type) {
        this.type = type;
    }

    public void addActivity (com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity activity){
        activities.put(activity.getType(), activity);
    }



    /**
     * Wallet interface implementation.
     */

    @Override
    public Wallets getType() {
        return type;
    }

    @Override
    public Activity getActivity(Activities activities) {
        return this.activities.get(activities);
    }

    @Override
    public Activity getStartActivity() {
        return activities.get(startActivity);
    }

    @Override
    public void setStartActivity(Activities activity) {
        this.startActivity=activity;
    }

    @Override
    public Activities getLastActivity() {
        if(lastActivity==null){
            return startActivity;
        }
        return lastActivity;
    }
}
