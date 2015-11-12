package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FermatFragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface FermatActivity extends Serializable{
    public void setColor(String color);

    public String getColor();

    public Activities getType();

    public String getActivityType();

    public Map<String, Fragment> getFragments();

    public Fragment getLastFragment();

    public Fragment getFragment(String fragment);

    public TitleBar getTitleBar() ;

    public SideMenu getSideMenu() ;

    public MainMenu getMainMenu() ;

    public TabStrip getTabStrip() ;

    public StatusBar getStatusBar();

    public Activities getBackActivity();

    public FermatHeader getHeader();

}
