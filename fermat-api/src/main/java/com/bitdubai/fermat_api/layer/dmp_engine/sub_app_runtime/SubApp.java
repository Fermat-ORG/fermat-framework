package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface SubApp extends FermatApp,FermatStructure {

    /**
     * SubApp type
     *
     * @return SubApps
     */

    SubApps getType();

    /**
     * SubApp publicKey
     * @return publicKey
     */
    String getPublicKey();
    /**
     * Screens in a SubApp
     */

    Map<Activities, Activity> getActivities();

    /**
     *  Search screen in the SubApp activities
     *
     * @param activities
     * @return Activity
     */

    Activity getActivity(Activities activities);

    /**
     * Last screen active
     */

    Activity getLastActivity() throws InvalidParameterException;


    /**
     *  Available languages
     *
     * @return Map of languages
     */

    Map<String,LanguagePackage> getLanguagePackages();

    void clear();
}
