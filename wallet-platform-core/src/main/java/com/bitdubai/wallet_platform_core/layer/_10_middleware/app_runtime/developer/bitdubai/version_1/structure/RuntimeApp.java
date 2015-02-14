package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.App;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.Apps;
import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.SubApp;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeApp implements App {

    Apps type;
    



    /**
     * RuntimeApp interface implementation.
     */
    
    public void addSubApp (SubApp subApp){
            
     }


    /**
     * App interface implementation.
     */

    public Apps getType() {
        return type;
    }

    public void setType(Apps type) {
        this.type = type;
    }

    
    
}
