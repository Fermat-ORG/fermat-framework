package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import java.awt.Color;
import java.awt.Image;

import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rodrigo on 2015.07.17..
 */
@XmlRootElement(name = "tittleBar")
public class TitleBar implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatTitleBar {

    /**
     * class private attributes
     */
    private String label;

    private String color;

    private String backgroundImage;

    private SearchView runtimeSearchView;

    /**
     * Class Constructors
     */
    public TitleBar() {
    }

    public TitleBar(String label, String color, String backgroundImage, SearchView runtimeSearchView) {
        this.label = label;
        this.color = color;
        this.backgroundImage = backgroundImage;
        this.runtimeSearchView = runtimeSearchView;
    }

    /**
     * Class getters
     */
    @XmlElement
    public String getLabel() {
        return label;
    }

    @XmlElement
    public String getColor() {
        return color;
    }

    @XmlElement
    public String getBackgroundImage() {
        return backgroundImage;
    }

    @XmlElement
    public SearchView getRuntimeSearchView() {
        if (runtimeSearchView != null) {
            return runtimeSearchView;
        }
        return null;
    }

    /**
     * Class setters
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setRuntimeSearchView(SearchView runtimeSearchView) {
        this.runtimeSearchView = runtimeSearchView;
    }
}
