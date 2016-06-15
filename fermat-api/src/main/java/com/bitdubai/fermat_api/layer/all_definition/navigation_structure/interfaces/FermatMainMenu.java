package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatMainMenu  extends Serializable {
    List<OptionMenuItem> getMenuItems();


}
