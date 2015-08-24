package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Matias Furszyfer on 2015.07.23..
 */

public class WalletNavigationStructure implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWallet{

    /**
     * WalletNavigationStructure identifiers
     */

    private String publicKey;

    /**
     * Wallet identifiers
     */
    private String walletCategory;
    private String walletType;


    /**
     * Screens in a WalletNavigationStructure
     */

    private Map<Activities, Activity> activities = new HashMap<Activities, Activity>();

    /**
     * Main screen of the wallet
     */

    private Activities startActivity;

    /**
     * Last active screen
     */

    private Activities lastActivity;

    private int size;

    /**
     * WalletNavigationStructure constructor
     */
    public WalletNavigationStructure() {
    }

    /**
     *
     * @param activities
     */
    public WalletNavigationStructure(Map<Activities, Activity> activities) {
        this.activities = activities;
    }


    /**
     * WalletNavigationStructure interface implementation.
     */

    @Override
    public String getPublicKey() {
        return publicKey;
    }


    /**
     * RuntimeWallet interface implementation.
     */
    @Override
    public Activity getActivity(Activities activities) {
        this.lastActivity=activities;
        return this.activities.get(activities);
    }

    /**
     *  Get main screen
     *
     * @return Activity
     */
    @Override
    public Activity getStartActivity() {
        return activities.get(startActivity);
    }

    /**
     *  Set the main screen
     *
     * @param activity
     */
    @Override
    public void setStartActivity(Activities activity) {
        this.startActivity=activity;
    }

    /**
     *  Get the last Screen used
     * @return Activity
     */
    @Override
    public Activity getLastActivity() {
        if (lastActivity == null) {
            return activities.get(startActivity);
        }
        return activities.get(lastActivity);
    }

    @Override
    public void setPublicKey(String publicKey) {
        this.publicKey=publicKey;
    }

    /**
     *  Screens in a WalletNavigationStructure
     *
     * @return Map<Activities, Activity>
     */

    public Map<Activities, Activity> getActivities() {
        return activities;
    }

    public void setActivities(Map<Activities, Activity> activities) {
        this.activities = activities;
    }

    /**
     *  Add Screen to WalletNavigationStructure
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.put(activity.getType(), activity);
    }

    public String getWalletCategory() {
        return walletCategory;
    }

    public void setWalletCategory(String walletCategory) {
        this.walletCategory = walletCategory;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
