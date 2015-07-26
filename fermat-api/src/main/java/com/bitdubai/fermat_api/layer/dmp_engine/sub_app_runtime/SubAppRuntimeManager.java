package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Apps;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


/**
 * Created by Matias Furszyfer on 2/14/15.
 */
public interface SubAppRuntimeManager {


    /**
     *  Get the last active SubApp
     *
     * @return Wallet in use
     */

    public SubApp getLastSubApp ();

    /**
     *  Search SubApp in the subApp installed list
     *
     * @param subApps type of SubApps
     * @return  The installed SubApps
     */

    public SubApp getSubApp(SubApps subApps);

    /**
     *  Search HomeScreen SubApp
     *
     * @return SubApp HomeScreen
     */

    public SubApp getHomeScreen();



}
