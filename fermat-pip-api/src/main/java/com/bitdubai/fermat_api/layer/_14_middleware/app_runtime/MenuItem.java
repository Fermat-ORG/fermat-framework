package com.bitdubai.fermat_api.layer._14_middleware.app_runtime;

import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.enums.Activities;

import java.awt.*;

/**
 * Created by ciencias on 2/14/15.
 */
public interface MenuItem {

    public String getLabel() ;

    public Image getIcon() ;

    public Activities getLinkToActivity() ;
}
