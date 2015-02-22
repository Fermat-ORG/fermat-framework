package com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.Fragments;

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
