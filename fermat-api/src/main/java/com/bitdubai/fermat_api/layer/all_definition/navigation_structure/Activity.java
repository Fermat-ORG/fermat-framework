package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatBottomNavigation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFooter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatHeader;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatWizard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by rodrigo on 2015.07.17.
 */

public class Activity implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatActivity,Serializable {
    /**
     * Activity class member variables
     *
     * TODO:  se debe cambiar y empezar a usar el String de activity type de abajo cuando esté hecho el activity factory
     */
    @Deprecated
    Activities type;

    /**
     *  Activity type in String format
     */
    String activityType;

    /**
     *  the String is the fragments enum value corresponding to each plugin
     */

    Map<String, Fragment> fragments = new HashMap<String, Fragment>();

    String lastFragment;

    String startFragment;

    TitleBar titleBar;

    SideMenu sideMenu;

    Footer footer;

    MainMenu mainMenu;

    TabStrip tabStrip;

    String color;

    StatusBar statusBar;

    /**
     * String wizard types enum
     */
    Map<String, Wizard> wizards;

    // esto es para ver a que wallet o subApp hay que hacer el back
    String backPublicKey;

    Activities backActivity;

    Header header;
    private boolean fullScreen;
    private String backgroundColor;
    private FermatBottomNavigation bottomNavigationMenu;

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

    public void addFragment(String fragmentsType, Fragment fragment) {
        fragments.put(fragmentsType, fragment);
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


    public void setLastFragment(String lastFragment) {
        this.lastFragment = lastFragment;
    }

    public void setStartFragment(String startFragment) {
        this.startFragment = startFragment;
    }

    public void setBackActivity(Activities activity){
        this.backActivity=activity;
    }

    public void setBackPublicKey(String backPublicKey) {
        this.backPublicKey = backPublicKey;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
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

    @Override
    public Activities getBackActivity() {
        return this.backActivity;
    }

    @Override
    public FermatHeader getHeader() {
        return header;
    }

    @Override
    public FermatFooter getFooter() {
        return footer;
    }

    @Override
    public FermatBottomNavigation getBottomNavigationMenu() {
        return bottomNavigationMenu;
    }

    @Override
    public String getBackAppPublicKey() {
        return backPublicKey;
    }

    @Override
    public void changeBackActivity(String appPublicKeyback,String activityCode) throws InvalidParameterException {
        this.backPublicKey = appPublicKeyback;
        this.backActivity = Activities.getValueFromString(activityCode);
    }

    @Override
    public FermatWizard getWizard(String wizardCode) {
        return wizards.get(wizardCode);
    }

    // TODO VER COMO HACER ESTO
    @Override
    public Map<String, Fragment> getFragments() {
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
    public Fragment getFragment(String fragment) {
        Iterator<Map.Entry<String, Fragment>> eSubApp = fragments.entrySet().iterator();
        while (eSubApp.hasNext()) {
            Map.Entry<String, Fragment> fragmentEntryEntry = eSubApp.next();
            Fragment subApp = fragmentEntryEntry.getValue();
            if (subApp.getType().equals(fragment)) {
                lastFragment = fragment;
                return subApp;
            }
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * Add runtime Wizard to this Activity
     *
     * @param wizardTypeCode WizardType enumerable
     * @param wizard     runtime wizard object to attach to this activity
     */
    public void addWizard(String wizardTypeCode, Wizard wizard) {
        if (wizards == null)
            wizards = new HashMap<>();
        wizards.put(wizardTypeCode, wizard);
    }

    /***
     * Get Wizards attached to this Activity
     *
     * @return HasMap Wizards
     */
    public Map<String, Wizard> getWizards() {
        return wizards;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBottomNavigationMenu(FermatBottomNavigation bottomNavigationMenu) {
        this.bottomNavigationMenu = bottomNavigationMenu;
    }
}

