package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;

import java.util.List;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatTabStrip {

    public List<Tab> getTabs();

    public void setDividerColor(int color);

    public int getDividerColor();

    public void setIndicatorColor(int color);

    public int getIndicatorColor();

    public void setIndicatorHeight(int color);

    public int getIndicatorHeight();

    public void setBackgroundColor(int color);

    public int getBackgroundColor();

    public void setTextColor(int color);

    public int getTextColor();

    public void setBackgroundResource(int id);

    public int getBackgroundResource();

    public void setTabsColor(String color);

    public String getTabsColor();

    public void setTabsTextColor(String color);

    public String getTabsTextColor();

    public void setTabsIndicateColor(String color);

    public String getTabsIndicateColor();

}
