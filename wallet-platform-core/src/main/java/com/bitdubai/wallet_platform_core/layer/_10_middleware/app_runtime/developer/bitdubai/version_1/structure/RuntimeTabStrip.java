package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.TabStrip;

import java.util.List;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeTabStrip implements TabStrip {
    
    List<String> tabs;
    
    public void addTab (String tab) {
        tabs.add(tab);
    }

    @Override
    public List<String> getTabs(){
        return this.tabs;
    }
    
}
