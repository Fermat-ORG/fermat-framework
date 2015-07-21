package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class SideMenu implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatSideMenu {

    List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> menuItems = new ArrayList<>();

    public void addMenuItem (com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem menuItem){
        menuItems.add(menuItem);
    }

    public List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> getMenuItems () {
        return menuItems;
    }
}
