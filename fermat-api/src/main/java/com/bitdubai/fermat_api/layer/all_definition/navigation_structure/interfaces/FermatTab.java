package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatTab {
    public void setLabel(String texto);
    public String getLabel();
    public void setFragment(Fragments fragment);
    public Fragments getFragment();
    public byte[] getIcon();
}
