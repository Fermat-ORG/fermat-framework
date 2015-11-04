package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import java.io.Serializable;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatStatusBar extends Serializable {
    public String getColor();

    public boolean isVisible() ;

    public void setColor(String color);

    public void setVisible(boolean visible);
}
