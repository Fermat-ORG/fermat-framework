package com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.TabStrip;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeTabStrip implements TabStrip {
    
    List<String> tabs = new ArrayList<String>();
    
    public void addTab (String tab) {
        tabs.add(tab);
    }

    @Override
    public List<String> getTabs(){
        return this.tabs;
    }
    
}
