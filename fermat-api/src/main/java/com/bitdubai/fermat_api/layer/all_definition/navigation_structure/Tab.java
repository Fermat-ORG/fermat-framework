package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters.FragmentsAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatTab;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by rodrigo on 2015.07.17..
 */
@XmlRootElement(name = "tab")
public class Tab implements FermatTab {

    /**
     * Tab class member variables
     */
    String label;

    Fragments fragment;

    /**
     * Tab class constructors
     */
    public Tab() {
    }

    public Tab(String label, Fragments fragment) {
        this.label = label;
        this.fragment = fragment;
    }

    /**
     * Tab class getters
     */
    @XmlElement
    public String getLabel(){
        return this.label;
    }

    @XmlJavaTypeAdapter(FragmentsAdapter.class)
    @XmlElement(name = "fragment", required = true)
    public Fragments getFragment(){
        return this.fragment;
    }

    /**
     * Tab class setters
     */
    public void setLabel(String texto){
        this.label = texto;
    }

    public void setFragment(Fragments fragment){
        this.fragment = fragment;
    }
}
