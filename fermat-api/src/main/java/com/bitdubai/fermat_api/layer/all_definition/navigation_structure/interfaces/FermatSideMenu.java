package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface FermatSideMenu extends Serializable {

    List<MenuItem> getMenuItems();

    boolean hasFooter();

    void clearSelected();

    String getBackgroudColor();

    String getNavigationIconColor();

    FermatDrawable getBackgroundDrawable();
}
