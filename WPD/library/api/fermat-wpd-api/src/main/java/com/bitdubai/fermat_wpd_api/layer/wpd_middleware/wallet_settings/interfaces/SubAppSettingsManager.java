package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadSubAppSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSaveSubAppSettings;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public interface SubAppSettingsManager {

    /**
     * This method gives us the settings of a wallet
     *
     * @param subAppType the identifier of the wallet we want to work with
     * @return the settings of the specified wallet
     */
    public SubAppSettings getSettings(String subAppType) throws CantLoadSubAppSettings;


    /**
     * This method gives us the settings of a wallet
     *
     * @param subAppType the identifier of the wallet we want to work with
     * @return the settings of the specified wallet
     */
    public void setSettings(String xmlSubAppSetting,String subAppType)throws CantSaveSubAppSettings;

}
