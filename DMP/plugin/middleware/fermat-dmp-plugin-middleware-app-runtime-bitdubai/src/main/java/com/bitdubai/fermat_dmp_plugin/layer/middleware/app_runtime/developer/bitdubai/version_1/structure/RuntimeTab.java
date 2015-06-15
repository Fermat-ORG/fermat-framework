package com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Fragments;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Tab;

/**
 * Created by natalia on 23/02/2015.
 */
public class RuntimeTab implements Tab {

    String label;
    Fragments fragment;

    public void setLabel(String texto){
        this.label = texto;
    }

    public String getLabel(){
        return this.label;
    }

    public void setFragment(Fragments fragment){
        this.fragment = fragment;
    }

    public Fragments getFragment(){
        return this.fragment;
    }


}
