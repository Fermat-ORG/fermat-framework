package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import java.io.Serializable;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatSearchView extends Serializable {

    String getText();

    String getIcon();

    String getLabel();

    void setText(String text);

    void setIcon(String icon);

    void setLabel(String label);
}
