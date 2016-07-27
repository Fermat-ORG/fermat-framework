package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rodrigo on 2015.07.20..
 * Update by Miguel Payarez on 2016.04.08
 */
public interface FermatTabStrip extends Serializable {

    List<Tab> getTabs();

    void setDividerColor(int color);

    int getDividerColor();

    void setIndicatorColor(int color);

    int getIndicatorColor();

    void setIndicatorHeight(int color);

    int getIndicatorHeight();

    void setBackgroundColor(int color);

    int getBackgroundColor();

    void setTextColor(int color);

    int getTextColor();

    void setSelectTabTextColor(String selectTabTextColor);

    void setBackgroundResource(int id);

    int getBackgroundResource();

    void setTabsColor(String color);

    String getTabsColor();

    void setTabsTextColor(String color);

    String getTabsTextColor();

    void setTabsIndicateColor(String color);

    String getTabsIndicateColor();

    int getStartItem();

    boolean isHasIcon();

    boolean isHasText();

    FermatDrawable getBackgroundDrawable();

    void setBackgroundDrawable(FermatDrawable backgroundDrawable);


}
