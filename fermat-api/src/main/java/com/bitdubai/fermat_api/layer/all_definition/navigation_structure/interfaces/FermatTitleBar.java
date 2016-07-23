package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface FermatTitleBar extends Serializable {

    String getLabel();

    SearchView getRuntimeSearchView();

    String getBackgroundImage();

    String getColor();

    void setLabel(String label);

    void setRuntimeSearchView(SearchView runtimeSearchView);

    void setBackgroundImage(String backgroundImage);

    void setColor(String color);

    boolean isTitleTextStatic();

    MenuItem getNavItem();

    FermatDrawable getBackgroundDrawable();

}
