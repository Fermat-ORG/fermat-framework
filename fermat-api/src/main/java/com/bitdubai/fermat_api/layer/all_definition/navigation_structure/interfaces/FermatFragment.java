package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatFragment {

    public String getType();

    public String getBack();

    public void setContext(Object... objects);

    public Object[] getContext();
}
