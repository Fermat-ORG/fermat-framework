package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;



import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.ActivitiesMapAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.WalletsAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import java.util.HashMap;
import java.util.Map;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Matias Furszyfer on 2015.07.23..
 */

@XmlRootElement(name = "navigationStructure")
public class Wallet implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet{

    private String walletPlatformIdentifier;

    private Wallets type;

    private Map<Activities, Activity> activities = new HashMap<Activities, Activity>();

    private WalletCategory walletCategory;

    /**
     * Main screen of the wallet
     */
    private Activities startActivity;
    /**
     * Last screen used
     */
    private Activities lastActivity;


    public Wallet() {
    }

    public Wallet(Wallets type, Map<Activities, Activity> activities) {
        this.type = type;
        this.activities = activities;
    }



    public void setType(Wallets type) {
        this.type = type;
    }

    public void addActivity(Activity activity) {
        activities.put(activity.getType(), activity);
    }

    /**
     * Wallet interface implementation.
     */
    @XmlJavaTypeAdapter(WalletsAdapter.class)
    @XmlAttribute(name = "wallettype", required = true)
    @Override
    public Wallets getType() {
        return type;
    }


    /**
     * RuntimeWallet interface implementation.
     */
    @Override
    public Activity getActivity(Activities activities) {
        this.lastActivity=activities;
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
    public Activity getLastActivity() {
        if (lastActivity == null) {
            return activities.get(startActivity);
        }
        return activities.get(lastActivity);
    }

    @XmlJavaTypeAdapter(ActivitiesMapAdapter.class)
    @XmlElement(name = "activities")
    public Map<Activities, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Activities, Activity> activities) {
        this.activities = activities;
    }
}
