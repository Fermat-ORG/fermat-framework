package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface FermatTab extends Serializable {

    void setLabel(String texto);

    String getLabel();

    void setFragment(FermatFragment fragment);

    FermatFragment getFragment();

    byte[] getIcon();

    FermatDrawable getDrawable();

    void setFermatDrawable(FermatDrawable fermatDrawable);
}
