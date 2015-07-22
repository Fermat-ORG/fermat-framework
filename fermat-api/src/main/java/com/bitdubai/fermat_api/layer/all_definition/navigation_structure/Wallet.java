package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;



import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.WalletsAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.util.HashMap;
import java.util.Map;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by rodrigo on 2015.07.17..
 */
@XmlRootElement( name = "navigationStructure" )
public class Wallet implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet {

    Wallets type;

    Map<Activities, Activity> activities = new HashMap<Activities, com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity>();

    public Wallet() {
    }

    public Wallet(Wallets type, Map<Activities, Activity> activities) {
        this.type = type;
        this.activities = activities;
    }

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
    @XmlJavaTypeAdapter( WalletsAdapter.class )
    @XmlAttribute( name = "wallettype", required=true )
    @Override
    public Wallets getType() {
        return type;
    }

    public Map<Activities, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Activities, Activity> activities) {
        this.activities = activities;
    }
}
