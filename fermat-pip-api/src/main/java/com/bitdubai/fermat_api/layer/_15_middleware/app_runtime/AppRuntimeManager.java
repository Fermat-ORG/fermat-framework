package com.bitdubai.fermat_api.layer._15_middleware.app_runtime;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Apps;

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

}
