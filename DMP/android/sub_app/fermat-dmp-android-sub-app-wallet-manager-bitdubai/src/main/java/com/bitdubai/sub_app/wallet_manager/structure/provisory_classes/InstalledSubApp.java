package com.bitdubai.sub_app.wallet_manager.structure.provisory_classes;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.interface_objects.FermatInterfaceObject;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public class InstalledSubApp implements com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp,FermatInterfaceObject {

    private SubApps subApps;
    private List<InstalledSkin> skinsId;
    private List<InstalledLanguage> languajesId;
    private String walletIcon;
    private String walletName;
    private String publicKey;
    private String walletPlatformIdentifier;
    private Version version;

    public InstalledSubApp(SubApps subApps, List<InstalledSkin> skinsId, List<InstalledLanguage> languajesId, String walletIcon, String walletName, String publicKey, String walletPlatformIdentifier, Version version) {
        this.subApps = subApps;
        this.skinsId = skinsId;
        this.languajesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.version = version;
    }

    /**
     * InstalledWallet Interface implementation.
     */

    /**
     * This method gives us the list of all the languages installed for this wallet
     *
     */
    public List<InstalledLanguage> getLanguagesId(){
        return languajesId;
    }

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
     */
    public List<InstalledSkin> getSkinsId(){
        return skinsId;
    }

    /**
     * This method tell us the type of the subApp
     *
     * @return the subApp type
     */
    @Override
    public SubApps getSubAppType() {
        return subApps;
    }

    /**
     * This method gives us a codification of the wallet identifier (the identifier is an enum that
     * registers the subApp)
     *
     * @return an string that is result of the method getCode of an enum that can be inferred by the
     * subApp
     */
    @Override
    public String getSubAppPlatformIdentifier() {
        return "Method: getSubAppPlatformIdentifier - NO TIENE valor ASIGNADO para RETURN";
    }

    /**
     * This method gives us the name of the wallet icon used to identify the image in the subApp resources plug-in
     *
     * @return the name of the said icon
     */
    @Override
    public String getSubAppIcon() {
        return walletIcon;
    }

    /**
     * This method gives us the public key of the wallet in this device. It is used as identifier of
     * the wallet
     *
     * @return the public key represented as a string
     */

    /**
     * This method gives us the subApp name
     *
     * @return the name of the subApp
     */
    @Override
    public String getSubAppName() {
        return walletName;
    }

    /**
     * This method gives us the version of the subApp
     *
     * @return the version of the subApp
     */
    @Override
    public Version getSubAppVersion() {
        return version;
    }


    @Override
    public InterfaceType getType() {
        return InterfaceType.SUB_APP;
    }

    @Override
    public String getName() {
        return walletName;
    }

    @Override
    public String getIcon() {
        return walletIcon;
    }

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }
}
