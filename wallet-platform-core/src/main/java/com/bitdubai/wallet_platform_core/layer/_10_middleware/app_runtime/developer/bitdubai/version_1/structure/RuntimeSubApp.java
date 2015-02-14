package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activities;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activity;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.SubApp;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.SubApps;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeSubApp implements SubApp {

    SubApps type;
    Map<Activities, Activity> activities;

    /**
     * RuntimeSubApp interface implementation.
     */

    public void addActivity (Activity activity){
        activities.put(activity.getType(), activity);
    }

    /**
     * SubApp interface implementation.
     */

    public SubApps getType() {
        return type;
    }

    public void setType(SubApps type) {
        this.type = type;
    }
}
