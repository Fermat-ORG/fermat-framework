package com.bitdubai.desktop.sub_app_manager.util;

import com.bitdubai.desktop.sub_app_manager.provisory_classes.CbpInstalledSubApp;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 22/09/15.
 */
public enum CbpSubAppListGenerator implements SubAppListGenerator{
    instance;

    @Override
    public List<InstalledSubApp> createSubAppsList() {
        ArrayList<InstalledSubApp> list = new ArrayList<>();

        InstalledSubApp installedSubApp = new CbpInstalledSubApp(
                SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                null,
                null,
                "sub_app_crypto_broker_identity",
                "Crypto Broker Identity",
                "sub_app_crypto_broker_identity",
                "sub_app_crypto_broker_identity",
                new Version(1, 0, 0));
        list.add(installedSubApp);

        installedSubApp = new CbpInstalledSubApp(
                SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_broker_community",
                "Crypto Broker Community",
                "sub_app_crypto_broker_community",
                "sub_app_crypto_broker_community",
                new Version(1, 0, 0));
        list.add(installedSubApp);

        installedSubApp = new CbpInstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY,
                null,
                null,
                "sub_app_crypto_customer_identity",
                "Crypto Customer Identity",
                "sub_app_crypto_customer_identity",
                "sub_app_crypto_customer_identity",
                new Version(1, 0, 0));
        list.add(installedSubApp);

        installedSubApp = new CbpInstalledSubApp(
                SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                null,
                null,
                "sub_app_crypto_customer_community",
                "Crypto Customer Community",
                "sub_app_crypto_customer_community",
                "sub_app_crypto_customer_community",
                new Version(1, 0, 0));
        list.add(installedSubApp);

        installedSubApp = new CbpInstalledSubApp(
                SubApps.CBP_CUSTOMERS,
                null,
                null,
                "sub_app_customers",
                "Customers",
                "sub_app_customers",
                "sub_app_customers",
                new Version(1, 0, 0));
        list.add(installedSubApp);

        return list;
    }
}
