package com.bitdubai.fermat_api.layer._14_middleware.app_runtime;

import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.enums.Fragments;

/**
 * Created by toshiba on 23/02/2015.
 */
public interface Tab {
    public void setLabel(String texto);
    public String getLabel();
    public void setFragment(Fragments fragment);
    public Fragments getFragment();
}
