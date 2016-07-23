package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatTabStrip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Matias Furszyfer on 2015.07.17..
 * Update by Miguel Payarez on 2016.04.08
 */

public class TabStrip implements FermatTabStrip, Serializable {

    /**
     * WIZARD
     * <p/>
     * Wizard type enum
     */
    Map<String, Wizard> wizards;

    /**
     * TabStrip class member variables
     */
    int dividerColor;

    int indicatorColor;

    int indicatorHeight = -1;

    int backgroundColor;

    int textColor;

    int backgroundResource;

    String selectedTabTextColor;

    String tabsColor;

    String tabsTextColor;

    String tabsIndicateColor;

    boolean hasIcon = false;

    boolean hasText;

    int startItem;

    boolean reduceTabHeight;

    private List<Tab> tabs = new ArrayList<>();

    /**
     * Custom view to put as background
     */
    private FermatView fermatView;

    /**
     * Custom drawable
     */
    private FermatDrawable backgroundDrawable;


    public void addTab(Tab tab) {
        tabs.add(tab);
    }

    /**
     * TabStrip class Constructors
     */
    public TabStrip() {
    }

    public TabStrip(int dividerColor, int indicatorColor, int indicatorHeight, int backgroundColor, int textColor, int backgroundResource, String tabsColor, String tabsTextColor, String tabsIndicateColor, List<Tab> tabs, String selectedTabTextColor) {
        this.dividerColor = dividerColor;
        this.indicatorColor = indicatorColor;
        this.indicatorHeight = indicatorHeight;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.backgroundResource = backgroundResource;
        this.tabsColor = tabsColor;
        this.tabsTextColor = tabsTextColor;
        this.tabsIndicateColor = tabsIndicateColor;
        this.tabs = tabs;
        this.selectedTabTextColor = selectedTabTextColor;
    }

    /**
     * TabStrip class getters
     */

    @Override
    public int getDividerColor() {
        return dividerColor;
    }


    @Override
    public int getIndicatorColor() {
        return indicatorColor;
    }

    @Override
    public int getIndicatorHeight() {
        return indicatorHeight;
    }


    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }


    @Override
    public int getTextColor() {
        return textColor;
    }


    @Override
    public int getBackgroundResource() {
        return backgroundResource;
    }


    @Override
    public String getTabsColor() {
        return tabsColor;
    }


    @Override
    public String getTabsTextColor() {
        return tabsTextColor;
    }


    @Override
    public String getTabsIndicateColor() {
        return tabsIndicateColor;
    }

    public String getSelectedTabTextColor() {
        return selectedTabTextColor;
    }

    @Override
    public List<Tab> getTabs() {
        return tabs;
    }

    public boolean isReduceTabHeight() {
        return reduceTabHeight;
    }

    /**
     * TabStrip class setters
     */
    @Override
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    @Override
    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
    }

    @Override
    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    public void setSelectTabTextColor(String selectTabTextColor) {
        this.selectedTabTextColor = selectTabTextColor;
    }

    @Override
    public void setBackgroundResource(int backgroundResource) {
        this.backgroundResource = backgroundResource;
    }

    @Override
    public void setTabsColor(String tabsColor) {
        this.tabsColor = tabsColor;
    }

    @Override
    public void setTabsTextColor(String tabsTextColor) {
        this.tabsTextColor = tabsTextColor;
    }

    @Override
    public void setTabsIndicateColor(String tabsIndicateColor) {
        this.tabsIndicateColor = tabsIndicateColor;
    }

    public int getStartItem() {
        return startItem;
    }

    public void setStartItem(int startItem) {
        this.startItem = startItem;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public boolean isHasText() {
        return hasText;
    }

    public void setHasText(boolean hasText) {
        this.hasText = hasText;
    }

    public void setReduceTabHeight(boolean reduceTabHeight) {
        this.reduceTabHeight = reduceTabHeight;
    }

    /**
     * Add wizard to attach to this Tab
     *
     * @param type   Wizard type enumerable
     * @param wizard Wizard runtime
     * @throws Exception
     */
    public void addWizard(String type, Wizard wizard) throws Exception {
        if (wizards == null)
            wizards = new HashMap<>();
        if (wizard == null || type == null)
            throw new NullPointerException("arguments cannot be null");
        if (wizards.get(type) != null) {
            throw new Exception(String.format("Type %s is already registered.", type));
        }
        wizards.put(type, wizard);
    }


    /**
     * Custom View for the tab
     *
     * @return
     */
    public FermatView getFermatView() {
        return fermatView;
    }

    public void setFermatView(FermatView fermatView) {
        this.fermatView = fermatView;
    }

    public Map<String, Wizard> getWizards() {
        return wizards;
    }

    public FermatDrawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(FermatDrawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }
}
