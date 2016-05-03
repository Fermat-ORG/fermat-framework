package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatFragment {

    String getType();

    String getBack();

    void setContext(Object... objects);

    Object[] getContext();
}
