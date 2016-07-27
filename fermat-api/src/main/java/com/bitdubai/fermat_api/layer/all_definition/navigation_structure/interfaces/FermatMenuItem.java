package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.io.Serializable;

/**
 * Created by rodrigo on 2015.07.20..
 */
public interface FermatMenuItem extends Serializable {

    String getLabel();

    String getIcon();

    Activities getLinkToActivity();

    String getAppLinkPublicKey();
}
