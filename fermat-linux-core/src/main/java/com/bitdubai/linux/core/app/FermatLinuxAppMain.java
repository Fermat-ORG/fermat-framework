/*
 * @#FermatLinuxAppMain.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.linux.core.app;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_linux_core.OSAPlatform;
import com.bitdubai.linux.core.app.version_1.structure.context.FermatLinuxContext;

import java.util.List;

/**
 * The Class <code>com.bitdubai.linux.core.app.FermatLinuxAppMain</code> initialize
 * all fermat system
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatLinuxAppMain {

    /**
     * Represent the fermatContext instance
     */
    private static final FermatLinuxContext fermatLinuxContext = FermatLinuxContext.getInstance();

    /**
     * Represent the fermatSystem instance
     */
    private static final FermatSystem fermatSystem = FermatSystem.getInstance(fermatLinuxContext, new OSAPlatform());

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {

        try {

            System.out.println("***********************************************************************");
            System.out.println("* FERMAT - Linux Core - Version 1.0 (2016)                            *");
            System.out.println("* www.fermat.org                                                      *");
            System.out.println("***********************************************************************");
            System.out.println("");
            System.out.println("- Starting process ...");

            /*
             * Start the system
             */

            fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.COMMUNICATION_PLATFORM, Layers.COMMUNICATION, Plugins.NETWORK_NODE  , Developers.BITDUBAI, new Version()));
            /* TODO TESTING PURPOSE

            fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.COMMUNICATION_PLATFORM, Layers.COMMUNICATION, Plugins.NETWORK_CLIENT, Developers.BITDUBAI, new Version()));
            CryptoBrokerIdentityModuleManager cryptoBrokerIdentity = (CryptoBrokerIdentityModuleManager) fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.CRYPTO_BROKER_PLATFORM, Layers.SUB_APP_MODULE, Plugins.CRYPTO_BROKER_IDENTITY, Developers.BITDUBAI, new Version()));
            CryptoBrokerCommunitySubAppModuleManager cryptoBrokerCommunity = (CryptoBrokerCommunitySubAppModuleManager) fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.CRYPTO_BROKER_PLATFORM, Layers.SUB_APP_MODULE, Plugins.CRYPTO_BROKER_COMMUNITY, Developers.BITDUBAI, new Version()));

            List<CryptoBrokerIdentityInformation> listActores = cryptoBrokerIdentity.listIdentities(10, 0);

            if (listActores == null || listActores.isEmpty()) {
                CryptoBrokerIdentityInformation actor1 = cryptoBrokerIdentity.createCryptoBrokerIdentity("actor 1 created", new byte[0]);

                cryptoBrokerIdentity.publishIdentity(actor1.getPublicKey());

                CryptoBrokerIdentityInformation actor2 = cryptoBrokerIdentity.createCryptoBrokerIdentity("actor 2 created", new byte[0]);

                cryptoBrokerIdentity.publishIdentity(actor2.getPublicKey());
            }*/

           // fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.CRYPTO_BROKER_PLATFORM, Layers.ACTOR_NETWORK_SERVICE, Plugins.CRYPTO_CUSTOMER, Developers.BITDUBAI, new Version()));

            System.out.println("FERMAT - Linux Core - started satisfactory...");

        } catch (Exception e) {

            System.out.println("***********************************************************************");
            System.out.println("* FERMAT - ERROR                                                      *");
            System.out.println("***********************************************************************");
            e.printStackTrace();
            System.exit(1);
        }

    }
}
