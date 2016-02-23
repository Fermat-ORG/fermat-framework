package com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface DesktopObject{


    /**
     * Desktop enum type in sString form
     *
     * @return
     */
    String getType();

    /**
     *  screens for desktop
     *
     * @return
     */

    Map<Activities, Activity> getActivities();

    /**
     *  get specific screen
     *
     * @param activities
     * @return
     */

    Activity getActivity(Activities activities);

    /**
     * get last used screen
     *
     * @return
     */

    Activity getLastActivity();


    /**
     *  set languajes
     *
     * @return
     */

    Map<String,LanguagePackage> getLanguagePackages();
}
