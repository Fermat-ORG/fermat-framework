package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatTitleBar {
    public String getLabel();

    public void setLabel(String label);

    public void setRuntimeSearchView(SearchView runtimeSearchView);
    public SearchView getRuntimeSearchView();

}
