
package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.ActivitiesAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.util.HashMap;
import java.util.Map;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by rodrigo on 2015.07.17..
 */
@XmlRootElement(name = "activity")
public class Activity implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatActivity{
    /**
     * Activity class member variables
     */
    Activities type;

    Map<WalletFragments, Fragment> fragments = new HashMap<WalletFragments, Fragment>();

    TitleBar titleBar;

    SideMenu sideMenu;

    MainMenu mainMenu;

    TabStrip tabStrip;

    String color;

    StatusBar statusBar;

    public Activity() {
    }

    /**
     * Activity class implementation.
     */

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(Activities type) {
        this.type = type;
    }

    public void addFragment (Fragment fragment){
        fragments.put(fragment.getType(), fragment);
    }

    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
    }

    public void setSideMenu(SideMenu sideMenu) {
        this.sideMenu = sideMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setTabStrip(TabStrip tabStrip) {
        this.tabStrip = tabStrip;
    }
    public void setStatusBar(StatusBar statusBar){
        this.statusBar=statusBar;
    }
    /**
     * Activity  interface implementation.
     */

    public String getColor()  {
        return this.color;
    }

    @XmlJavaTypeAdapter(ActivitiesAdapter.class)
    @XmlAttribute(name = "type", required = true)
    @Override
    public Activities getType() {
        return type;
    }

    @Override
    public Map<WalletFragments, Fragment> getFragments() {
        return fragments;
    }

    @Override
    public TitleBar getTitleBar() {
        return titleBar;
    }

    @Override
    public SideMenu getSideMenu() {
        return sideMenu;
    }

    @Override
    public MainMenu getMainMenu() {
        return mainMenu;
    }

    @Override
    public TabStrip getTabStrip() {
        return tabStrip;
    }
    @Override
    public StatusBar getStatusBar() {
        return statusBar;
    }
}

