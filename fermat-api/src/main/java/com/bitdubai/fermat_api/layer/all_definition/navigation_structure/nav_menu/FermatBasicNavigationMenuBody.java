package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.nav_menu;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatLayout;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class FermatBasicNavigationMenuBody implements Serializable {

    private List<MenuItem> menuItems = new ArrayList<>();
    private String backgroundColor;
    private FermatLayout rowLayout;


    public FermatBasicNavigationMenuBody() {
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public FermatLayout getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(FermatLayout rowLayout) {
        this.rowLayout = rowLayout;
    }
}
