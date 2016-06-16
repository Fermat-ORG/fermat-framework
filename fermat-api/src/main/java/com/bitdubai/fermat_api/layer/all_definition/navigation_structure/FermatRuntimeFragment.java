package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;

/**
 * Created by Matias Furszyfer on 2016.06.08..
 */
public class FermatRuntimeFragment extends Artifact implements FermatFragment {

    private String fragmentCode;
    private String backCode;

    // OptionMenu to add or change to the activity OptionsMenu
    private com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu optionsMenu;


    public FermatRuntimeFragment(int id, Owner owner, SourceLocation sourceLocation, String fragmentCode) {
        super(id, owner, sourceLocation);
        this.fragmentCode = fragmentCode;
    }

    public com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu getOptionsMenu() {
        return optionsMenu;
    }

    public void setOptionsMenu(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu optionsMenu) {
        this.optionsMenu = optionsMenu;
    }

    public void setFragmentCode(String fragmentCode) {
        this.fragmentCode = fragmentCode;
    }

    public void setBackCode(String backCode) {
        this.backCode = backCode;
    }

    @Override
    public String getType() {
        return fragmentCode;
    }

    @Override
    public String getBack() {
        return backCode;
    }
}
