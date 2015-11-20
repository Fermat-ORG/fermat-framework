package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface SubApp extends FermatApp {

    /**
     * SubApp type
     *
     * @return SubApps
     */

    public SubApps getType();

    /**
     * SubApp publicKey
     * @return publicKey
     */
    public String getPublicKey();
    /**
     * Screens in a SubApp
     */

    public Map<Activities, Activity> getActivities();

    /**
     *  Search screen in the SubApp activities
     *
     * @param activities
     * @return Activity
     */

    public Activity getActivity(Activities activities);

    /**
     * Last screen active
     */

    public Activity getLastActivity();

    /**
     * Main screen of a SubApp
     *
     * @param activity
     */

    public void setStartActivity(Activities activity);

    /**
     *  Available languages
     *
     * @return Map of languages
     */
    
    public Map<String,LanguagePackage> getLanguagePackages();

}
