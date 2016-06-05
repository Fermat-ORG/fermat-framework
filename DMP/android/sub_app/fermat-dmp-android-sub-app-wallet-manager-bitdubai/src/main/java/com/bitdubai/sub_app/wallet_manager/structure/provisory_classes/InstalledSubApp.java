package com.bitdubai.sub_app.wallet_manager.structure.provisory_classes;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.FermatAppType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp;
import com.bitdubai.fermat_api.layer.interface_objects.FermatInterfaceObject;
import com.bitdubai.fermat_api.layer.interface_objects.InterfaceType;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

public class InstalledSubApp implements com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp,FermatInterfaceObject,InstalledApp {

    private SubApps subApps;
    private List<InstalledSkin> skinsId;
    private List<InstalledLanguage> languajesId;
    private String walletIcon;
    private String walletName;
    private String publicKey;
    private Version version;
    private int iconResource;
    private int position;
    //TODO: completar
    private AppsStatus appStatus;

    private int banner = 0;
    private Platforms platform;
//    private AppStructureType appStructureType = AppStructureType.REFERENCE;


    public InstalledSubApp(SubApps subApps, List<InstalledSkin> skinsId, List<InstalledLanguage> languajesId, String walletIcon, String walletName, String publicKey, String walletPlatformIdentifier, Version version, Platforms platform, AppsStatus appsStatus) {
        this.subApps = subApps;
        this.skinsId = skinsId;
        this.languajesId = languajesId;
        this.walletIcon = walletIcon;
        this.walletName = walletName;
        this.publicKey = publicKey;
        this.version = version;
        this.platform = platform;
        this.appStatus = appsStatus;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
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
    public int getBanner() {
        return 0;
    }

    @Override
    public Platforms getPlatform() {
        return platform;
    }

    @Override
    public String getIcon() {
        return walletIcon;
    }

    @Override
    public int getIconResource() {
        return iconResource;
    }

    @Override
    public String getAppName() {
        return subApps.getCode();
    }

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }

    @Override
    public AppsStatus getAppStatus() {
        return appStatus;
    }

    @Override
    public FermatAppType getAppType() {
        return FermatAppType.SUB_APP;
    }

//    @Override
//    public AppStructureType getAppStructureType() {
//        return appStructureType;
//    }

    @Override
    public byte[] getAppIcon() {
        return new byte[0];
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getNotifications() {
        return banner;
    }

    public void setBanner(int banner) {
        this.banner = banner;
    }

    @Override
    public int getBannerRes() {
        return banner;
    }

    public void setAppStatus(AppsStatus appStatus) {
        this.appStatus = appStatus;
    }
}
