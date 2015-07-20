package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class MainMenu implements com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MainMenu {

    List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem> menuItems = new ArrayList<>();

    public void addMenuItem (com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem menuItem){
        menuItems.add(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
