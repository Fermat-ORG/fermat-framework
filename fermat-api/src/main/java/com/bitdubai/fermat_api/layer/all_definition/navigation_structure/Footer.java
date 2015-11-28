package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFooter;

/**
 * Created by Matias Furszyfer on 2015.11.27..
 */
public class Footer implements FermatFooter {

    private String backgroundColor;
    private String fragmentCode;

    public Footer() {
    }

    @Override
    public String getFragment() {
        return fragmentCode;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setFragmentCode(String fragmentCode) {
        this.fragmentCode = fragmentCode;
    }
}
