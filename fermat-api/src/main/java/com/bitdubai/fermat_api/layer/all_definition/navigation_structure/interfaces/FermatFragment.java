package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface FermatFragment {

    String getType();

    String getBack();

    Owner getOwner();

    OptionsMenu getOptionsMenu();

    boolean hasOptionMenu();
}
