package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import java.awt.Image;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatSearchView {
    public String getText();

    public Image getIcon() ;

    public void setIcon(Image icon);

    public void setLabel(String label);

    public String getLabel();
}
