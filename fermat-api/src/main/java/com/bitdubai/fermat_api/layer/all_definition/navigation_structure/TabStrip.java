package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class TabStrip implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatTabStrip {
    int dividercolor;
    int indicatorcolor;
    int indicatorheight;
    int backgroundcolor;
    int textcolor;
    int backgroundresource;
    String tabsColor;
    String tabsTextColor;
    String tabsIndicateColor;

    List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab> tabs = new ArrayList<>();

    public void addTab (Tab tab) {
        tabs.add(tab);
    }

    @Override
    public List<com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab> getTabs(){
        return this.tabs;
    }
    @Override
    public void setDividerColor(int color) {
        this.dividercolor = color;
    }

    @Override
    public int getDividerColor() {
        return this.dividercolor;
    }
    @Override
    public void setIndicatorColor(int color){
        this.indicatorcolor = color;
    }

    @Override
    public int getIndicatorColor(){
        return this.indicatorcolor;
    }
    @Override
    public void setIndicatorHeight(int height){
        this.indicatorheight = height;
    }
    @Override
    public int getIndicatorHeight(){
        return this.indicatorheight;

    }
    @Override
    public void setBackgroundColor(int color){
        this.backgroundcolor = color;
    }
    @Override
    public int getBackgroundColor(){
        return this.backgroundcolor;
    }
    @Override
    public void setTextColor(int color){
        this.textcolor = color;
    }
    @Override
    public int getTextColor(){
        return this.textcolor;
    }
    @Override
    public void setBackgroundResource(int id){
        this.backgroundresource = id;
    }

    @Override
    public int getBackgroundResource(){
        return this.backgroundresource;
    }

    @Override
    public void setTabsColor(String color) {
        this.tabsColor=color;
    }

    @Override
    public String getTabsColor() {
        return tabsColor;
    }

    @Override
    public void setTabsTextColor(String color) {
        this.tabsTextColor=color;
    }

    @Override
    public String getTabsTextColor() {
        return tabsTextColor;
    }

    @Override
    public void setTabsIndicateColor(String color) {
        this.tabsIndicateColor= color;
    }

    @Override
    public String getTabsIndicateColor() {
        return tabsIndicateColor;
    }
}
