package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;

import java.io.Serializable;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatTab  extends Serializable {
    void setLabel(String texto);
    String getLabel();
    void setFragment(Fragments fragment);
    Fragments getFragment();
    byte[] getIcon();
}
