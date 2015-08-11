package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatTitleBar {

    String getLabel();

    SearchView getRuntimeSearchView();

    String getBackgroundImage();

    String getColor();

    void setLabel(String label);

    void setRuntimeSearchView(SearchView runtimeSearchView);

    void setBackgroundImage(String backgroundImage);

    void setColor(String color);

}
