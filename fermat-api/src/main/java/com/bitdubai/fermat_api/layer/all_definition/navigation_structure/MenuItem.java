package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Activities;

import java.awt.Image;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class MenuItem implements com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.MenuItem {

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
