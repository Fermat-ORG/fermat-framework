package com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.LanguagePackage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.09.16..
 */
public interface DesktopObject extends FermatStructure {


    /**
     * Desktop enum type in sString form
     *
     * @return
     */
    public String getType();

    /**
     * screens for desktop
     *
     * @return
     */

    public Map<Activities, Activity> getActivities();

    /**
     * get specific screen
     *
     * @param activities
     * @return
     */

    public Activity getActivity(Activities activities);

    /**
     * get last used screen
     *
     * @return
     */

    public Activity getLastActivity();


    /**
     * set languajes
     *
     * @return
     */

    public Map<String, LanguagePackage> getLanguagePackages();

}
