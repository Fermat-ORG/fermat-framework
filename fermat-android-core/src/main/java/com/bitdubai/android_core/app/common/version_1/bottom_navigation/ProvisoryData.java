package com.bitdubai.android_core.app.common.version_1.bottom_navigation;

import com.bitdubai.fermat.R;
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

        InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.Scanner, null, null, "intra_user_identity_sub_app", "Scanner", "public_key_ccp_intra_user_identity", "intra_user_identity_sub_app", new Version(1, 0, 0));
        Item item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.ic_04);
        item2.setPosition(1);
        lst.add(item2);


        //Identities
        List<Item> lstIdentities = new ArrayList<>();

        installedSubApp = new InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Wallet Users","public_key_ccp_intra_user_identity","intra_user_identity_sub_app",new Version(1,0,0));
        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.intra_user_image);
        item2.setPosition(0);
        lstIdentities.add(item2);

        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_IDENTITY_ISSUER, null, null, "sub-app-asset-identity-issuer", "Asset Issuers", "public_key_dap_asset_issuer_identity", "sub-app-asset-identity-issuer", new Version(1, 0, 0));
        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.asset_identity_issuer);
        item2.setPosition(1);
        lstIdentities.add(item2);
        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_IDENTITY_USER, null, null, "sub-app-asset-identity-user", "Asset Users", "public_key_dap_asset_user_identity", "sub-app-asset-identity-user", new Version(1, 0, 0));
        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.asset_user_identity);
        item2.setPosition(2);
        lstIdentities.add(item2);
        installedSubApp = new InstalledSubApp(SubApps.DAP_REDEEM_POINT_IDENTITY, null, null, "sub-app-asset-identity-redeem-point", "Redeem Points", "public_key_dap_redeem_point_identity", "sub-app-asset-identity-redeem-point", new Version(1, 0, 0));
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
                "sub_app_crypto_broker_identity",
                "sub_app_crypto_broker_identity",
                new Version(1, 0, 0));
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
                "sub_app_crypto_customer_identity",
                "sub_app_crypto_customer_identity",
                new Version(1, 0, 0));

        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.crypto_broker_identity);
        item2.setPosition(5);
        lstIdentities.add(item2);

        FermatFolder fermatFolder = new FermatFolder("Identities",lstIdentities,2);
        Item identityFolder = new Item(fermatFolder);
        identityFolder.setIconResource(R.drawable.ic_01);
        identityFolder.setPosition(2);
        lst.add(identityFolder);





        //communities
        List<Item> lstCommunities = new ArrayList<>();

        installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Wallet Users","public_key_intra_user_commmunity","intra_user_community_sub_app",new Version(1,0,0));
        Item item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.intra_user_2);
        item1.setPosition(0);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_ISSUER, null, null, "sub-app-asset-community-issuer", "Asset Issuers", "public_key_dap_issuer_community", "sub-app-asset-community-issuer", new Version(1, 0, 0));
        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.intra_user_2);
        item1.setPosition(1);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_USER, null, null, "sub-app-asset-community-user", "Asset Users", "public_key_dap_user_community", "sub-app-asset-community-user", new Version(1, 0, 0));
        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.intra_user_2);
        item1.setPosition(2);
        lstCommunities.add(item1);
        installedSubApp = new InstalledSubApp(SubApps.DAP_ASSETS_COMMUNITY_REDEEM_POINT, null, null, "sub-app-asset-community-redeem-point", "Redeem Points", "public_key_dap_reedem_point_community", "sub-app-asset-community-redeem-point", new Version(1, 0, 0));
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
                "sub_app_crypto_broker_community",
                "sub_app_crypto_broker_community",
                new Version(1, 0, 0));
        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.crypto_broker_community1);
        item1.setPosition(4);
        lstCommunities.add(item1);




        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_customer_community",
                "Customers",
                "sub_app_crypto_customer_community",
                "sub_app_crypto_customer_community",
                new Version(1, 0, 0));

        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.crypto_broker_community1);
        item1.setPosition(5);
        lstCommunities.add(item1);

        installedSubApp = new InstalledSubApp(
                SubApps.CBP_CUSTOMERS,
                null,
                null,
                "sub_app_customers",
                "Customers",
                "sub_app_customers",
                "sub_app_customers",
                new Version(1, 0, 0));
        item1 = new Item(installedSubApp);
        item1.setIconResource(R.drawable.customer_icon);
        item1.setPosition(6);
        lstCommunities.add(item1);



        fermatFolder = new FermatFolder("Communities",lstCommunities,1);
        item2 = new Item(fermatFolder);
        item2.setIconResource(R.drawable.ic_002);
        item2.setPosition(3);
        lst.add(item2);

        //store
        installedSubApp = new InstalledSubApp(SubApps.CWP_WALLET_STORE,null,null,"wallet_store","Wallet Store","public_key_store","wallet_store",new Version(1,0,0));
        item2 = new Item(installedSubApp);
        item2.setIconResource(R.drawable.ic_03);
        item2.setPosition(4);
        lst.add(item2);

        return lst;
    }
}
