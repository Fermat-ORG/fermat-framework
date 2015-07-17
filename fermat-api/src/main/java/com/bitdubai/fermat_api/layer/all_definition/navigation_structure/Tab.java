package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class Tab implements com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Tab {

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
