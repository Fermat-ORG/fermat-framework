package com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public interface InstalledSubApp extends Serializable, FermatApp {


    /**
     * This method gives us the list of all the languages installed for this subApp
     *
     * @return the saud list of languages
     */
    List<InstalledLanguage> getLanguagesId();

    /**
     * This method gives us the list of all the skins installed for this subApp
     *
     * @return the saud list of skins
     */
    List<InstalledSkin> getSkinsId();

    /**
     * This method tell us the type of the subApp
     *
     * @return the subApp type
     */
    SubApps getSubAppType();

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the subApp)
     *
     * @return an string that is result of the method getCode of an enum that can be inferred by the
     * subApp
     */
    String getSubAppPlatformIdentifier();

    /**
     * This method gives us the name of the wallet icon used to identify the image in the subApp resources plug-in
     *
     * @return the name of the said icon
     */
    String getSubAppIcon();


    /**
     * This method gives us the subApp name
     *
     * @return the name of the subApp
     */
    String getSubAppName();

    /**
     * This method gives us the version of the subApp
     *
     * @return the version of the subApp
     */
    Version getSubAppVersion();
}
