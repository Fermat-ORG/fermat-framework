
package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.ActivitiesAdapter;
import com.bitdubai.fermat_api.layer.all_definition.enums.FermatFragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
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

    Map<FermatFragments, Fragment> fragments = new HashMap<FermatFragments, Fragment>();

    Fragments lastFragment;

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
    @XmlElement
    public String getColor()  {
        return this.color;
    }

    @XmlJavaTypeAdapter(ActivitiesAdapter.class)
    @XmlAttribute(name = "type", required = true)
    @Override
    public Activities getType() {
        return type;
    }

    @XmlElement
    @Override
    public TitleBar getTitleBar() {
        return titleBar;
    }

    @XmlElement
    @Override
    public SideMenu getSideMenu() {
        return sideMenu;
    }

    @XmlElement
    @Override
    public MainMenu getMainMenu() {
        return mainMenu;
    }

    @XmlElement
    @Override
    public TabStrip getTabStrip() {
        return tabStrip;
    }

    @XmlElement
    @Override
    public StatusBar getStatusBar() {
        return statusBar;
    }

    // TODO VER COMO HACER ESTO
    @Override
    public Map<FermatFragments, Fragment> getFragments() {
        return fragments;
    }

    @Override
    public Fragment getLastFragment() {
        return fragments.get(lastFragment);
    }

    @Override
    public Fragment getFragment(Fragments fragment) {
        Iterator<Map.Entry<FermatFragments, Fragment>> eSubApp = fragments.entrySet().iterator();
        while (eSubApp.hasNext()) {
            Map.Entry<FermatFragments, Fragment> fragmentEntryEntry = eSubApp.next();
            Fragment subApp = (Fragment) fragmentEntryEntry.getValue();
            if(subApp.getType().equals(fragment)){
                lastFragment=fragment;
                return subApp;
            }
        }
        return null;
    }


}

