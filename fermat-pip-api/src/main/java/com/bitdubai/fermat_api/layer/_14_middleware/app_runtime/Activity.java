package com.bitdubai.fermat_api.layer._14_middleware.app_runtime;

import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.enums.Fragments;

import java.util.Map;

/**
 * Created by ciencias on 2/14/15.
 */

/**
 * Created by ciencias on 2/14/15.
 */
public interface Activity {

    public void setColor(String color);

    public String getColor();

    public Activities getType();

    public Map<Fragments, Fragment> getFragments();

    public TitleBar getTitleBar() ;

    public SideMenu getSideMenu() ;

    public MainMenu getMainMenu() ;

    public TabStrip getTabStrip() ;

}
