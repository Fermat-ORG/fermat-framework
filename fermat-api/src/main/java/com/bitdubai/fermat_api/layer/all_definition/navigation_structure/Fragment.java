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

    /**
     * OptionsMenu to add or change to the Activity options
     */
    private com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu optionsMenu;

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

    @Override
    public com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu getOptionsMenu() {
        return optionsMenu;
    }

    public void setOptionsMenu(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu optionsMenu) {
        this.optionsMenu = optionsMenu;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
