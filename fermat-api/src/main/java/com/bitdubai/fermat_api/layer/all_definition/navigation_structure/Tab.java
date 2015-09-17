package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatTab;



/**
 * Created by Furszyfer Matias on 2015.07.17..
 */
public class Tab implements FermatTab {

    /**
     * Tab class member variables
     */
    String label;

    byte[] icon;

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

    public String getLabel(){
        return this.label;
    }


    public Fragments getFragment(){
        return this.fragment;
    }

    public byte[] getIcon() {
        return icon;
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

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}
