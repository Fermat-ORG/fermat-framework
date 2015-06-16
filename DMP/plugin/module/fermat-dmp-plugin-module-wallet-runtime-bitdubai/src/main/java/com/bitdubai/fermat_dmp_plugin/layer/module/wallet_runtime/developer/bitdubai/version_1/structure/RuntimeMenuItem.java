package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.MenuItem;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Activities;

import java.awt.Image;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeMenuItem implements MenuItem {

    String label;
    Image icon;
    Activities linkToActivity;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public void setLinkToActivity(Activities linkToActivity) {
        this.linkToActivity = linkToActivity;
    }

    public String getLabel() {
        return label;
    }

    public Image getIcon() {
        return icon;
    }

    public Activities getLinkToActivity() {
        return linkToActivity;
    }


    
}
