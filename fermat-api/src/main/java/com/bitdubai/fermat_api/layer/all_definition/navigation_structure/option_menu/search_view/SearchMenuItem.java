package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.search_view;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuItem;

/**
 * Created by Matias Furszyfer on 2016.06.29..
 */
public class SearchMenuItem extends OptionMenuItem {

    private String hint;

    /**
     * Set the ID og this {@link OptionMenuItem}. This is the ID your going to search in the method
     * <code>onOptionsItemSelected</code> of a fragment
     *
     * @param id the ID
     */
    public SearchMenuItem(int id) {
        super(id);
    }


}
