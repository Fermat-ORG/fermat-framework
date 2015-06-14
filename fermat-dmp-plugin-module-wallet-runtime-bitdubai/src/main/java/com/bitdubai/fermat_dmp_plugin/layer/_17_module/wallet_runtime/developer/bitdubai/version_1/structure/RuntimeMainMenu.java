package com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.MainMenu;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeMainMenu  implements MainMenu{

    List<MenuItem> menuItems = new ArrayList<>();

    public void addMenuItem (MenuItem menuItem){
        menuItems.add(menuItem);
    }

    public List<MenuItem> getMenuItems () {
        return menuItems;
    }
    
}
