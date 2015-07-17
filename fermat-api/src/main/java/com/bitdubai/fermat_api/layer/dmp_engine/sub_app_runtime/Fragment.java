package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;

/**
 * Created by ciencias on 2/14/15.
 */
public interface Fragment {

    public Fragments getType();

    public Fragments getBack();

    public void setContext(Object... objects);

    public Object[] getContext();
}
