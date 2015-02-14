package com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime;

import java.awt.*;

/**
 * Created by ciencias on 2/14/15.
 */
public interface MenuItem {

    public String getLabel() ;

    public Image getIcon() ;

    public Activities getLinkToActivity() ;
}
