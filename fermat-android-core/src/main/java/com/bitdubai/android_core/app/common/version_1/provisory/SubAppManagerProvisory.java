package com.bitdubai.android_core.app.common.version_1.provisory;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.DesktopManagerSettings;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.CantGetUserSubAppException;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.SubAppManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mati on 2015.12.06..
 */
public class SubAppManagerProvisory implements SubAppManager {

    Map<String, InstalledSubApp> installedSubApps;

    public SubAppManagerProvisory() {
        installedSubApps = new HashMap<>();
        loadMap(installedSubApps);

    }

    private void loadMap(Map<String, InstalledSubApp> lstInstalledSubApps) {
        //TODO - CCP Platform
        InstalledSubApp installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY, null, null, "intra_user_community_sub_app", "Wallet Users", SubAppsPublicKeys.CCP_COMMUNITY.getCode(), "intra_user_community_sub_app", new Version(1, 0, 0), Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY, null, null, "intra_user_identity_sub_app", "Wallet Users", SubAppsPublicKeys.CCP_IDENTITY.getCode(), "intra_user_identity_sub_app", new Version(1, 0, 0), Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - DAP Platform
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_ISSUER, null, null, "sub-app-asset-community-issuer", "Asset Community Issuer", SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode(), "sub-app-asset-community-issuer", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_USER, null, null, "sub-app-asset-community-user", "Asset Community User", SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode(), "sub-app-asset-community-user", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT, null, null, "sub-app-asset-community-redeem-point", "Asset Community Redeem Point",SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode(), "sub-app-asset-community-redeem-point", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_FACTORY, null, null, "sub-app-asset-factory", "Asset Factory", SubAppsPublicKeys.DAP_FACTORY.getCode(), "sub-app-asset-factory", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_IDENTITY_ISSUER, null, null, "sub-app-asset-identity-issuer", "Asset Identity Issuer",SubAppsPublicKeys.DAP_IDENTITY_ISSUER.getCode(), "sub-app-asset-identity-issuer", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_IDENTITY_USER, null, null, "sub-app-asset-identity-user", "Asset Identity User",SubAppsPublicKeys.DAP_IDENTITY_USER.getCode(), "sub-app-asset-identity-user", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.DAP_ASSETS_REDEEM_POINT_IDENTITY, null, null, "sub-app-asset-identity-redeem-point", "Asset Redeem Point Identity", "SubAppsPublicKeys.DAP_IDENTITY_REDEEM.getCode()", "sub-app-asset-identity-redeem-point", new Version(1, 0, 0),Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - CBP Platform
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_BROKER_IDENTITY, null, null, "sub_app_crypto_broker_identity", "Brokers",SubAppsPublicKeys.CBP_BROKER_IDENTITY.getCode(), "sub_app_crypto_broker_identity", new Version(1, 0, 0),Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY, null, null, "sub_app_crypto_customer_identity", "Customers",SubAppsPublicKeys.CBP_CUSTOMER_IDENTITY.getCode(), "sub_app_crypto_customer_identity", new Version(1, 0, 0),Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_BROKER_COMMUNITY, null, null, "sub_app_crypto_broker_community", "Brokers", SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode(), "sub_app_crypto_broker_community", new Version(1, 0, 0),Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY, null, null, "sub_app_crypto_customer_community", "Customers", SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode(), "sub_app_crypto_customer_community", new Version(1, 0, 0),Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - CHT Platform
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CHT_CHAT, null, null, "chat_sub_app", "Chat", SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), "chat_sub_app", new Version(1, 0, 0),Platforms.CHAT_PLATFORM, AppsStatus.ALPHA);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CHT_CHAT_IDENTITY, null, null, "public_key_cht_identity_chat", "Chat Identity", SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode(), "public_key_cht_identity_chat", new Version(1, 0, 0),Platforms.CHAT_PLATFORM, AppsStatus.ALPHA);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.CHT_COMMUNITY, null, null, "sub_app_cht_community", "Chat Community", SubAppsPublicKeys.CHT_COMMUNITY.getCode(), "sub_app_cht_community", new Version(1, 0, 0),Platforms.CHAT_PLATFORM, AppsStatus.ALPHA);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - TKY Platform
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(SubApps.TKY_ARTIST_IDENTITY_SUB_APP, null, null, "sub_app_tky_artist_identity", "Tonkenly", SubAppsPublicKeys.TKY_ARTIST_IDENTITY.getCode(), "sub_app_tky_artist_identity", new Version(1, 0, 0),Platforms.TOKENLY, AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        //TODO - ART Platform

        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(
                SubApps.ART_ARTIST_IDENTITY,
                null,
                null,
                "sub_app_art_artist_identity",
                "Artist Identity",
                SubAppsPublicKeys.ART_ARTIST_IDENTITY.getCode(),
                "sub_app_art_artist_identity", new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(
                SubApps.ART_FAN_COMMUNITY,
                null,
                null,
                "sub_app_art_fan_community",
                "Tonkenly",
                SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode(),
                "sub_app_art_fan_community",
                new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);
        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(
                SubApps.ART_FAN_COMMUNITY, null, null, "sub_app_art_fan_community", "Tonkenly", SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode(), "sub_app_art_fan_community", new Version(1, 0, 0),Platforms.ART_PLATFORM,
                AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);

        installedSubApp = new com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp(
                SubApps.ART_MUSIC_PLAYER,
                null,
                null,
                "music_player_sub_app",
                "Music Player",
                SubAppsPublicKeys.ART_MUSIC_PLAYER.getCode(),
                "music_player_sub_app", new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);
        lstInstalledSubApps.put(installedSubApp.getAppPublicKey(), installedSubApp);

        //TODO - Add Others SubApps


    }

    @Override
    public Collection<InstalledSubApp> getUserSubApps() throws CantGetUserSubAppException {
        return installedSubApps.values();
    }

    @Override
    public InstalledSubApp getSubApp(String subAppCode) {
        return installedSubApps.get(subAppCode);
    }


    @Override
    public FermatApp getApp(String publicKey) throws Exception {
        return installedSubApps.get(publicKey);
    }

    @Override
    public SettingsManager<DesktopManagerSettings> getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
