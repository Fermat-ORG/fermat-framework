package com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Fragments;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeFragment implements Fragment {

    Fragments type;

    /**
     * RuntimeFragment interface implementation.
     */
    public void setType(Fragments type) {
        this.type = type;
    }


    /**
     * SubApp interface implementation.
     */

    @Override
    public Fragments getType() {
        return type;
    }


}
