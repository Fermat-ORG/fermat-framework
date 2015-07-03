package com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime;

import java.util.List;

/**
 * Created by ciencias on 2/14/15.
 */
public interface TabStrip {

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
