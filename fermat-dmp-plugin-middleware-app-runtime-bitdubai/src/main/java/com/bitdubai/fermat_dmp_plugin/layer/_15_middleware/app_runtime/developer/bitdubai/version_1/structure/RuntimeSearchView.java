package com.bitdubai.fermat_dmp_plugin.layer._15_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.SearchView;

import java.awt.Image;
//import java.awt.TextField;


/**
 * Created by MATIAS Furszyfer.
 */
public class RuntimeSearchView implements SearchView {

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
