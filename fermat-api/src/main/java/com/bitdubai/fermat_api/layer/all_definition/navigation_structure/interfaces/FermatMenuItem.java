package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.awt.Image;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatMenuItem {

    public String getLabel() ;

    public Image getIcon() ;

    public Activities getLinkToActivity() ;
}
