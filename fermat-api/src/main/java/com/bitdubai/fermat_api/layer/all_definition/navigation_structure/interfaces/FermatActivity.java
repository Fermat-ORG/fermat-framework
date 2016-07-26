package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface FermatActivity extends Serializable {
    void setColor(String color);

    String getColor();

    Activities getType();

    String getActivityType();

    Map<String, FermatFragment> getFragments();

    FermatFragment getLastFragment();

    FermatFragment getFragment(String fragment);

    TitleBar getTitleBar();

    SideMenu getSideMenu();

    OptionsMenu getOptionsMenu();

    TabStrip getTabStrip();

    StatusBar getStatusBar();

    Activities getBackActivity();

    FermatHeader getHeader();

    FermatFooter getFooter();

    String getBackAppPublicKey();

    void changeBackActivity(String appPublicKeyback, String activityCode) throws InvalidParameterException;

    FermatWizard getWizard(String wizardCode);

    String getBackgroundColor();

    FermatBottomNavigation getBottomNavigationMenu();

}
