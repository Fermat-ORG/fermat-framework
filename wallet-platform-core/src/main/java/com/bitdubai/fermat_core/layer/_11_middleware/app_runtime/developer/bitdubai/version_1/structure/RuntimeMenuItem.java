package com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.Activities;
import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.MenuItem;

import java.awt.*;

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
