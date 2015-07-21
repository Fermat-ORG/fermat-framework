package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Apps;


/**
 * Created by ciencias on 2/14/15.
 */
public interface AppRuntimeManager {

    public App getApp (Apps app);

    public App getLastApp ();

    public SubApp getLastSubApp ();

    public Activity getLasActivity ();

    public Fragment getLastFragment ();

    public Activity getActivity(Activities app);

    public Fragment getFragment(Fragments frag);

}
