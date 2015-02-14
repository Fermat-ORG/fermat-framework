package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.*;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeActivity implements Activity {

    Activities type;
    Map<Fragments, Fragment> fragments;
    TitleBar titleBar;
    SideMenu sideMenu;
    MainMenu mainMenu;
    TabStrip tabStrip;

    /**
     * RuntimeActivity interface implementation.
     */

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
    
    /**
     * SubApp interface implementation.
     */

    @Override
    public Activities getType() {
        return type;
    }

    @Override
    public Map<Fragments, Fragment> getFragments() {
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
}
