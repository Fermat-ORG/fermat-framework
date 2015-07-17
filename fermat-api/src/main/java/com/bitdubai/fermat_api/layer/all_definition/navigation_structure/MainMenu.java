package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class MainMenu implements com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MainMenu {

    List<com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MenuItem> menuItems = new ArrayList<>();

    public void addMenuItem (com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MenuItem menuItem){
        menuItems.add(menuItem);
    }

    public List<com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MenuItem> getMenuItems () {
        return menuItems;
    }
}
