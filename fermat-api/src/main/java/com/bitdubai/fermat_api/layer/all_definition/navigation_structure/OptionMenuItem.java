package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

/**
 * Created by mati on 2016.06.07..
 */
public class OptionMenuItem extends MenuItem {

    private int visibility;

    public OptionMenuItem(int id) {
        super(id);
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
