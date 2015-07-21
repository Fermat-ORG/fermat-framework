package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;



import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class Wallet implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet {

    Wallets type;
    Map<Activities, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity> activities = new HashMap<Activities, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity>();


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
}
