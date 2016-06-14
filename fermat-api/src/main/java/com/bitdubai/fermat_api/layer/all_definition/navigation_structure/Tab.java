package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
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

    FermatFragment fragment;

    /**
     * Tab class constructors
     */
    public Tab() {
    }

    public Tab(String label, FermatRuntimeFragment fragment) {
        this.label = label;
        this.fragment = fragment;
    }

    /**
     * Tab class getters
     */

    public String getLabel(){
        return this.label;
    }

    @Override
    public void setFragment(FermatFragment fragment) {
        this.fragment = fragment;

    }


    public FermatFragment getFragment(){
        return fragment;
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

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}
