package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activities;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Activity;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Fragment;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeActivity implements Activity {

    Activities type;

    /**
     * RuntimeActivity interface implementation.
     */

    public void addFragment (Fragment activity){


    }

    /**
     * SubApp interface implementation.
     */

    public Activities getType() {
        return type;
    }

    public void setType(Activities type) {
        this.type = type;
    }
}
