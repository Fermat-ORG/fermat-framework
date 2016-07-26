package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.interface_objects.FermatInterfaceObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eze on 2015.07.10..
 */
public interface InstalledWallet extends Serializable, FermatInterfaceObject, FermatApp {

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
     * This method tell us the category of the wallet
     *
     * @return the category of the wallet
     */
    WalletCategory getWalletCategory();

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the wallet)
     *
     * @return an string that is result of the method getCode of an enum that can be inferred by the
     * WalletCategory of the wallet.
     */
    String getWalletPlatformIdentifier();

    /**
     * This method gives us the name of the wallet icon used to identify the image in the wallet resources plug-in
     *
     * @return the name of the said icon
     */
    String getIcon();

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     *
     * @return the public key represented as a string
     */
    String getWalletPublicKey();

    /**
     * This method gives us the wallet name
     *
     * @return the name of the wallet
     */
    String getName();

    /**
     * This method gives us the version of the wallet
     *
     * @return the version of the wallet
     */
    Version getWalletVersion();

    WalletType getWalletType();

    void setAppStatus(AppsStatus appsStatus);
}
