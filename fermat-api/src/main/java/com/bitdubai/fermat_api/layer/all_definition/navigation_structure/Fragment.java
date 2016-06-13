package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2015.07.17..
 */
public class Fragment implements FermatFragment,Serializable {

    /**
     * Fragment type for FragmentFactory
     */
    String type;
    /**
     * Fragment back if the user press the back button
     */
    String fragmentBack;
    /**
     * Owner field
     */
    private Owner owner;

    public void setBack(String fragmentBack)
    {
        this.fragmentBack = fragmentBack;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Fragment interface implementation.
     */
    @Override
    public String getType() {
        return type;
    }

    public String getBack(){
        return this.fragmentBack;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
