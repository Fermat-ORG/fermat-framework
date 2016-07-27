package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatSideMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matias Furszyfer on 2015.07.17..
 */

public class SideMenu implements FermatSideMenu, Serializable {

    /**
     * SideMenu class member variables
     */
    List<MenuItem> menuItems = new ArrayList<>();
    private String backgroundColor;
    private FermatDrawable backgroundDrawable;
    private String navigationIconColor;
    private boolean hasFooter = false;

    /**
     * SideMenu class constructors
     */
    public SideMenu() {
    }

    public SideMenu(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    /**
     * SideMenu class getters
     */

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * SideMenu class setters
     */
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroudColor() {
        return backgroundColor;
    }

    public void setNavigationIconColor(String navigationIconColor) {
        this.navigationIconColor = navigationIconColor;
    }

    @Override
    public boolean hasFooter() {
        return hasFooter;
    }

    @Override
    public void clearSelected() {
        for (MenuItem item : menuItems) {
            item.setSelected(false);
        }
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    public String getNavigationIconColor() {
        return navigationIconColor;
    }

    public void setNotifications(int[] notifications) {
        try {
            if (notifications != null && notifications.length != 0)
                for (int i = 0; i < menuItems.size(); i++) {
                    menuItems.get(i).setNotifications(notifications[i]);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FermatDrawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(FermatDrawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }
}
