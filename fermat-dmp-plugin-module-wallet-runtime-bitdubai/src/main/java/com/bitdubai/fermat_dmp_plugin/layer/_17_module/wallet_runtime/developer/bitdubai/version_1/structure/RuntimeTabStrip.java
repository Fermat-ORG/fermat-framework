package com.bitdubai.fermat_dmp_plugin.layer._17_module.wallet_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Tab;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.TabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeTabStrip implements TabStrip {
    int dividercolor;
    int indicatorcolor;
    int indicatorheight;
    int backgroundcolor;
    int textcolor;
    int backgroundresource;

    List<Tab> tabs = new ArrayList<>();
    
    public void addTab (RuntimeTab tab) {
        tabs.add(tab);
    }

    @Override
    public List<Tab> getTabs(){
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
    
}
