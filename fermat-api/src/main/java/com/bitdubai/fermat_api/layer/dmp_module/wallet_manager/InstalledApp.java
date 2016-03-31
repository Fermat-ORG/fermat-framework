package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.interface_objects.FermatInterfaceObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eze on 2015.07.10..
 */
public interface InstalledApp extends Serializable, FermatInterfaceObject, FermatApp {

    /**
     * This method gives us the list of all the languages installed for this wallet
     *
     * @return the saud list of languages
     */
    List<InstalledLanguage> getLanguagesId();

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
     * @return the saud list of skins
     */
    List<InstalledSkin> getSkinsId();

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     *
     * @return the name of the said icon
     */
    String getIcon();


    /**
     * This method gives us the wallet name
     *
     * @return the name of the wallet
     */
    String getName();


    int getBanner();


    Platforms getPlatform();
}
