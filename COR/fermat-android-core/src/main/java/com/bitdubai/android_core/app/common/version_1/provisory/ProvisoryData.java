package com.bitdubai.android_core.app.common.version_1.provisory;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.interface_objects.FermatFolder;
import com.bitdubai.sub_app.wallet_manager.structure.provisory_classes.InstalledSubApp;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2015.12.02..
 */
public class ProvisoryData {


    public static List<Item> getBottomNavigationProvisoryData(){
        List<Item> lst = new ArrayList<>();


        //settings
        InstalledSubApp installedSubApp;
        installedSubApp= new InstalledSubApp(
                SubApps.SETTINGS,
                null,
                null,
                "settings",
                "Settings",
                SubAppsPublicKeys.SETTINGS.getCode(),
                "settings",
                new Version(1,0,0),
                null, AppsStatus.DEV);

        Item item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.settings72);
        item2.setPosition(0);
        lst.add(item2);

//        installedSubApp = new InstalledSubApp(SubApps.Scanner, null, null, "intra_user_identity_sub_app", "Scanner", "public_key_ccp_intra_user_identity", "intra_user_identity_sub_app", new Version(1, 0, 0));
//        item2 = new Item(installedSubApp);
//        item2.setIconResource(R.drawable.ic_04);
//        item2.setPosition(5);
//        lst.add(item2);


        //Identities
        List<Item> lstIdentities = new ArrayList<>();

        installedSubApp = new InstalledSubApp(
                SubApps.CWP_INTRA_USER_IDENTITY,
                null,
                null,
                "intra_user_identity_sub_app",
                "Wallet Users",
                SubAppsPublicKeys.CCP_IDENTITY.getCode(),
                "intra_user_identity_sub_app",
                new Version(1,0,0),
                Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.ALPHA);

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.intra_user_identity_xxhdpi);
        item2.setPosition(0);
        lstIdentities.add(item2);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_IDENTITY_ISSUER,
                null,
                null,
                "sub-app-asset-identity-issuer",
                "Asset Issuers",
                SubAppsPublicKeys.DAP_IDENTITY_ISSUER.getCode(),
                "sub-app-asset-identity-issuer",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.ALPHA);

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.asset_issuer_identity);
        item2.setPosition(1);
        lstIdentities.add(item2);
        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_IDENTITY_USER,
                null,
                null,
                "sub-app-asset-identity-user",
                "Asset Users",
                SubAppsPublicKeys.DAP_IDENTITY_USER.getCode(),
                "sub-app-asset-identity-user",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.ALPHA);

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.asset_user_identity);
        item2.setPosition(2);
        lstIdentities.add(item2);
        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_REDEEM_POINT_IDENTITY,
                null,
                null,
                "sub-app-asset-identity-redeem-point",
                "Redeem Points",
                SubAppsPublicKeys.DAP_IDENTITY_REDEEM.getCode(),
                "sub-app-asset-identity-redeem-point",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.ALPHA);

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.redeem_point_identity);
        item2.setPosition(3);
        lstIdentities.add(item2);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                null,
                null,
                "sub_app_crypto_broker_identity",
                "Brokers",
                SubAppsPublicKeys.CBP_BROKER_IDENTITY.getCode(),
                "sub_app_crypto_broker_identity",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.ALPHA);

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.crypto_broker_identity);
        item2.setPosition(4);
        lstIdentities.add(item2);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY,
                null,
                null,
                "sub_app_crypto_customer_identity",
                "Customers",
                SubAppsPublicKeys.CBP_CUSTOMER_IDENTITY.getCode(),
                "sub_app_crypto_customer_identity",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.ALPHA);

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.crypto_customer_identity);
        item2.setPosition(5);
        lstIdentities.add(item2);
/**
 * Chat identity added by Lozadaa
 */

        installedSubApp = new InstalledSubApp(
                SubApps.CHT_CHAT_IDENTITY,
                null,
                null,
                "sub_app_chat_identity",
                "Chat",
                SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode(),
                "sub_app_chat_identity",
                new Version(1, 0, 0),
                Platforms.CHAT_PLATFORM, AppsStatus.ALPHA);
        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.chat_identity_subapp);
        item2.setPosition(6);
        lstIdentities.add(item2);



        //ART Identities
        installedSubApp = new InstalledSubApp(
                SubApps.ART_ARTIST_IDENTITY,
                null,
                null,
                "sub_app_art_artist_identity",
                "Artist Identity",
                SubAppsPublicKeys.ART_ARTIST_IDENTITY.getCode(),
                "sub_app_art_artist_identity",
                new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);

        item2 = new Item(installedSubApp);
//        item2.setIconResource(R.drawable.subapp_art_artist_icon);
        item2.setPosition(7);
        lstIdentities.add(item2);

        installedSubApp = new InstalledSubApp(
                SubApps.ART_FAN_IDENTITY,
                null,
                null,
                "sub_app_art_fan_identity",
                "Art Fan Identity",
                SubAppsPublicKeys.ART_FAN_IDENTITY.getCode(),
                "sub_app_art_fan_identity",
                new Version(1,0,0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);



        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.subapp_art_fan_icon);
        item2.setPosition(8);
        lstIdentities.add(item2);


        installedSubApp = new InstalledSubApp(
                SubApps.TKY_ARTIST_IDENTITY_SUB_APP,
                null,
                null,
                "sub_app_tky_artist_identity",
                "Tokenly Artist",
                SubAppsPublicKeys.TKY_ARTIST_IDENTITY.getCode(),
                "sub_app_tky_artist_identity",
                new Version(1, 0, 0),
                Platforms.TOKENLY,
                AppsStatus.DEV);

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.subapp_tky_artist_icon);
        item2.setPosition(9);
        lstIdentities.add(item2);



        installedSubApp = new InstalledSubApp(
                SubApps.TKY_FAN_IDENTITY_SUB_APP,
                null,
                null,
                "tky_fan_sub_app",
                "Tokenly Fan",
                "sub_app_tky_fan_create_identity",
                "tky_fan_sub_app",
                new Version(1,0,0),
                Platforms.TOKENLY,
                AppsStatus.DEV);
        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.subapp_tky_fan_icon);
        item2.setPosition(10);
        lstIdentities.add(item2);











        FermatFolder fermatFolder = new FermatFolder("Profiles",lstIdentities,2);
        Item identityFolder = new Item(fermatFolder);
        identityFolder.setIconResource(R.drawable.identities72);
        identityFolder.setPosition(2);
        lst.add(identityFolder);

        //communities
        List<Item> lstCommunities = new ArrayList<>();

        installedSubApp = new InstalledSubApp(
                SubApps.CCP_INTRA_USER_COMMUNITY,
                null,
                null,
                "intra_user_community_sub_app",
                "Wallet Users",
                SubAppsPublicKeys.CCP_COMMUNITY.getCode(),
                "intra_user_community_sub_app",
                new Version(1,0,0),
                Platforms.CRYPTO_CURRENCY_PLATFORM, AppsStatus.DEV);

        Item item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.intra_user_community_xxhdpi);
        item1.setPosition(0);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_ISSUER,
                null,
                null,
                "sub-app-asset-community-issuer",
                "Asset Issuers",
                SubAppsPublicKeys.DAP_COMMUNITY_ISSUER.getCode(),
                "sub-app-asset-community-issuer",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.asset_issuer_comunity);
        item1.setPosition(1);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_USER,
                null,
                null,
                "sub-app-asset-community-user",
                "Asset Users",
                SubAppsPublicKeys.DAP_COMMUNITY_USER.getCode(),
                "sub-app-asset-community-user",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.asset_user_comunity);
        item1.setPosition(2);
        lstCommunities.add(item1);
        installedSubApp = new InstalledSubApp(
                SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT,
                null,
                null,
                "sub-app-asset-community-redeem-point",
                "Redeem Points",
                SubAppsPublicKeys.DAP_COMMUNITY_REDEEM.getCode(),
                "sub-app-asset-community-redeem-point",
                new Version(1, 0, 0),
                Platforms.DIGITAL_ASSET_PLATFORM, AppsStatus.DEV);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.reddem_point_community);
        item1.setPosition(3);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_broker_community",
                "Brokers",
                SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode(),
                "sub_app_crypto_broker_community",
                new Version(1, 0, 0)
                ,Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.DEV);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.broker_community);
        item1.setPosition(4);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_customer_community",
                "Customers",
                SubAppsPublicKeys.CBP_CUSTOMER_COMMUNITY.getCode(),
                "sub_app_crypto_customer_community",
                new Version(1, 0, 0),
                Platforms.CRYPTO_BROKER_PLATFORM, AppsStatus.DEV);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.customer_community);
        item1.setPosition(5);
        lstCommunities.add(item1);

        //ART communities
        installedSubApp = new InstalledSubApp(
                SubApps.ART_FAN_COMMUNITY,
                null,
                null,
                "sub_app_art_fan_community",
                "Fans",
                SubAppsPublicKeys.ART_FAN_COMMUNITY.getCode(),
                "sub_app_art_fan_community",
                new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.DEV);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.communities_bar);
        item1.setPosition(6);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(
                SubApps.ART_ARTIST_COMMUNITY,
                null,
                null,
                "sub_app_art_artist_community",
                "Artist",
                SubAppsPublicKeys.ART_ARTIST_COMMUNITY.getCode(),
                "sub_app_art_artist_community",
                new Version(1, 0, 0),
                Platforms.ART_PLATFORM,
                AppsStatus.ALPHA);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.banner_artist_community);
        item1.setPosition(6);
        lstCommunities.add(item1);


        //CHT communities
        installedSubApp = new InstalledSubApp(
                SubApps.CHT_COMMUNITY,
                null,
                null,
                "sub_app_cht_community",
                "Chat Community",
                SubAppsPublicKeys.CHT_COMMUNITY.getCode(),
                "sub_app_cht_community",
                new Version(1, 0, 0),
                Platforms.CHAT_PLATFORM, AppsStatus.ALPHA);

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.chat_banner_community);
        item1.setPosition(7);
        lstCommunities.add(item1);

//        installedSubApp = new InstalledSubApp(
//                SubApps.CBP_CUSTOMERS,
//                null,
//                null,
//                "sub_app_customers",
//                "Customers",
//                "sub_app_customers",
//                "sub_app_customers",
//                new Version(1, 0, 0),Platforms.CRYPTO_BROKER_PLATFORM);
//        item1 = new Item(installedSubApp);
//        item1.setIconResource(R.drawable.customer);
//        item1.setPosition(6);
//        lstCommunities.add(item1);








        fermatFolder = new FermatFolder("Communities",lstCommunities,1);
        item2 = new Item(fermatFolder);
        item2.setIconResource(R.drawable.communities72);
        item2.setPosition(3);
        lst.add(item2);

        //store
        installedSubApp = new InstalledSubApp(SubApps.CWP_WALLET_STORE,
                null,
                null,
                "wallet_store",
                "App Store",
                SubAppsPublicKeys.CWP_STORE.getCode(),
                "wallet_store",new Version(1,0,0),
                null, AppsStatus.DEV);
        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.store72);
        item2.setPosition(4);
        lst.add(item2);




        return lst;
    }
}