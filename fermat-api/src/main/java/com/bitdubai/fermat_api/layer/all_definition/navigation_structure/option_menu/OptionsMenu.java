package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatMainMenu;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matias Furszyfer on 2015.07.17..
 */

public class OptionsMenu implements FermatMainMenu {

    /**
     * OptionsMenu class member variables
     */
    List<OptionMenuItem> menuItems = new ArrayList<>();

    /**
     * OptionsMenu class constructors
     */
    public OptionsMenu() {
    }

    public OptionsMenu(List<OptionMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem (OptionMenuItem menuItem){
        menuItems.add(menuItem);
    }

    /**
     * OptionsMenu class getters
     */

    public List<OptionMenuItem> getMenuItems () {
        return menuItems;
    }

    /**
     * OptionsMenu class setters
     */

    public void addMenuItems(List<? extends OptionMenuItem> menuItems) {
        this.menuItems.addAll(menuItems);
    }
}
