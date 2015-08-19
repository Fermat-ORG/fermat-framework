package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatFragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by rodrigo on 2015.07.17.
 */

public class Activity implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatActivity {
    /**
     * Activity class member variables
     */
    Activities type;

    Map<FermatFragments, Fragment> fragments = new HashMap<FermatFragments, Fragment>();

    Fragments lastFragment;

    Fragments startFragment;

    TitleBar titleBar;

    SideMenu sideMenu;

    MainMenu mainMenu;

    TabStrip tabStrip;

    String color;

    StatusBar statusBar;

    Map<WizardTypes, Wizard> wizards;

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

    public void addFragment(FermatFragments fermatFragments, Fragment fragment) {
        fragments.put(fermatFragments, fragment);
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

    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }


    public void setLastFragment(Fragments lastFragment) {
        this.lastFragment = lastFragment;
    }

    public void setStartFragment(Fragments startFragment) {
        this.startFragment = startFragment;
    }

    /**
     * Activity  interface implementation.
     */
    public String getColor() {
        return this.color;
    }

    @Override
    public Activities getType() {
        return type;
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

    // TODO VER COMO HACER ESTO
    @Override
    public Map<FermatFragments, Fragment> getFragments() {
        return fragments;
    }

    @Override
    public Fragment getLastFragment() {
        if(lastFragment==null){
            lastFragment = startFragment;
        }
        return fragments.get(lastFragment);
    }

    @Override
    public Fragment getFragment(Fragments fragment) {
        Iterator<Map.Entry<FermatFragments, Fragment>> eSubApp = fragments.entrySet().iterator();
        while (eSubApp.hasNext()) {
            Map.Entry<FermatFragments, Fragment> fragmentEntryEntry = eSubApp.next();
            Fragment subApp = (Fragment) fragmentEntryEntry.getValue();
            if (subApp.getType().equals(fragment)) {
                lastFragment = fragment;
                return subApp;
            }
        }
        return null;
    }

    /**
     * Add runtime Wizard to this Activity
     *
     * @param wizardType WizardType enumerable
     * @param wizard     runtime wizard object to attach to this activity
     */
    public void addWizard(WizardTypes wizardType, Wizard wizard) {
        if (wizards == null)
            wizards = new HashMap<>();
        wizards.put(wizardType, wizard);
    }

    /***
     * Get Wizards attached to this Activity
     *
     * @return HasMap Wizards
     */
    public Map<WizardTypes, Wizard> getWizards() {
        return wizards;
    }
}

