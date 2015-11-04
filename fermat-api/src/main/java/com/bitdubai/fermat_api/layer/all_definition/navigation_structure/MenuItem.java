package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatMenuItem;



/**
 * Created by Matias Furszyfer on 2015.07.17..
 */
public class MenuItem implements FermatMenuItem {

    /**
     * MenuItem class member variables
     */
    String label;

    String icon;

    Activities linkToActivity;

    /**
     * SideMenu class constructors
     */
    public MenuItem() {
    }

    public MenuItem(String label, String icon, Activities linkToActivity) {
        this.label = label;
        this.icon = icon;
        this.linkToActivity = linkToActivity;
    }

    /**
     * SideMenu class setters
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLinkToActivity(Activities linkToActivity) {
        this.linkToActivity = linkToActivity;
    }

    /**
     * SideMenu class getters
     */
    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }


    @Override
    public Activities getLinkToActivity() {
        return linkToActivity;
    }
}
