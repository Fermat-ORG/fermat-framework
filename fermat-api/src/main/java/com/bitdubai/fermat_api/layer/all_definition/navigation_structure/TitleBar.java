package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import java.awt.Color;
import java.awt.Image;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class TitleBar implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatTitleBar {

    private String label;

    private Color color;

    private Image backgroundImage;

    private SearchView runtimeSearchView;


    public Color getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public SearchView getRuntimeSearchView() {
        if (runtimeSearchView != null) {
            return runtimeSearchView;
        }
        return null;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setRuntimeSearchView(SearchView runtimeSearchView) {
        this.runtimeSearchView = runtimeSearchView;
    }
}
