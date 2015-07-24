package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatMainMenu;
import java.util.ArrayList;
import java.util.List;

import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlElementWrapper;
import ae.javax.xml.bind.annotation.XmlElements;
import ae.javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rodrigo on 2015.07.17..
 */
@XmlRootElement(name = "mainMenu")
public class MainMenu implements FermatMainMenu {

    /**
     * MainMenu class member variables
     */
    List<MenuItem> menuItems = new ArrayList<>();

    /**
     * MainMenu class constructors
     */
    public MainMenu() {
    }

    public MainMenu(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem (MenuItem menuItem){
        menuItems.add(menuItem);
    }

    /**
     * MainMenu class getters
     */
    @XmlElements({
            @XmlElement(name="menuItem", type=MenuItem.class),
    })
    @XmlElementWrapper
    public List<MenuItem> getMenuItems () {
        return menuItems;
    }

    /**
     * MainMenu class setters
     */
    public void setMenuItems (List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
