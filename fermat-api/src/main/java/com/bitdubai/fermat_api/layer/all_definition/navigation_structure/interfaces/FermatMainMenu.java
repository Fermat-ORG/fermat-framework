package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatMainMenu<M extends MenuItem> extends Serializable {
    List<M> getMenuItems();


    MenuItem getItem(int id);
}
