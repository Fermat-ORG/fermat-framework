package com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.sub_app_manage.interfaces;



import com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.sub_app_manage.exceptions.CantGetCryptoSubAppListException;

import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.sub_app_manage.interfaces.CryptoDesktopSubAppModuleManager</code>
 * provides the methods for the Crypto Broker SubApps Installed sub app.
 */
public interface CryptoDesktopSubAppModuleManager {

    /**
     * The method <code>getAllSubAppInstalled</code> returns the list of all crypto subapp published
     *
     * @return the list of subapp published
     * @throws CantGetCryptoSubAppListException
     */
    public List<CryptoDesktopSubAppInformation> getAllSubAppInstalled(int max, int offset) throws CantGetCryptoSubAppListException;


}
