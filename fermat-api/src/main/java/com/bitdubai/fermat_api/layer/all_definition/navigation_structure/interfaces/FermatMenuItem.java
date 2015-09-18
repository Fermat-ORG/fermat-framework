package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatMenuItem {

    String getLabel() ;

    String getIcon() ;

    Activities getLinkToActivity() ;
}
