package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatSearchView;

import java.io.Serializable;


/**
 * Created by rodrigo on 2015.07.17..
 */

public class SearchView implements FermatSearchView, Serializable {

    /**
     * Class private attributes
     */
    String label;
    String icon;
    String text;

    /**
     * Class constructors
     */
    public SearchView() {
    }

    public SearchView(String label, String icon, String text) {
        this.label = label;
        this.icon = icon;
        this.text = text;
    }

    /**
     * Class getters
     */
    public String getLabel() {
        return label;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    /**
     * Class setters
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setText(String text) {
        this.text = text;
    }
}
