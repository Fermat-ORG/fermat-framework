package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import java.awt.Image;

/**
 * Created by Matias
 */
public class StatusBar implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStatusBar {

    String color;
    boolean isVisible;

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setColor(String color) {
        this.color=color;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.isVisible=isVisible;
    }
}
