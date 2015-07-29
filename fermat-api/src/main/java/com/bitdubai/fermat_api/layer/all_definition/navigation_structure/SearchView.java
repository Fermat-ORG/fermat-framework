package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatSearchView;

import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rodrigo on 2015.07.17..
 */
@XmlRootElement(name = "searchView")
public class SearchView implements FermatSearchView {

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
    @XmlElement
    public String getLabel() {
        return label;
    }

    @XmlElement
    public String getText() {
        return text;
    }

    @XmlElement
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
