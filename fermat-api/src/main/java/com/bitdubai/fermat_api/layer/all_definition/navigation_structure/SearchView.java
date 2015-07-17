package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import java.awt.Image;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class SearchView implements com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SearchView {

    String label;
    Image searchIcon;

    public String getLabel() {
        return label;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public Image getIcon() {
        return searchIcon;
    }

    @Override
    public void setIcon(Image icon) {
        searchIcon=icon;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
