package com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */
public interface Activity {

    public Activities getType();

    public Map<Fragments, Fragment> getFragments();

    public TitleBar getTitleBar() ;

    public SideMenu getSideMenu() ;

    public MainMenu getMainMenu() ;

    public TabStrip getTabStrip() ;

}
