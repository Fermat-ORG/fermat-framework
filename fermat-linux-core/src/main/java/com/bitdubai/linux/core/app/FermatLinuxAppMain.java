package com.bitdubai.linux.core.app;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_linux_core.OSAPlatform;
import com.bitdubai.linux.core.app.version_1.structure.context.FermatLinuxContext;

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
    private static final FermatSystem fermatSystem = FermatSystem.getInstance();

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {

        try {

            fermatSystem.start(fermatLinuxContext, new OSAPlatform());

            System.out.println("***********************************************************************");
            System.out.println("* FERMAT - Linux Core - Version 1.0 (2016)                            *");
            System.out.println("* www.fermat.org                                                      *");
            System.out.println("***********************************************************************");
            System.out.println("");
            System.out.println("- Starting process ...");

            /*
             * Start the network node.
             */

            fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.COMMUNICATION_PLATFORM, Layers.COMMUNICATION, Plugins.NETWORK_NODE, Developers.BITDUBAI, new Version()));
/*
            final NetworkClientManager clientManager = (NetworkClientManager) fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.COMMUNICATION_PLATFORM, Layers.COMMUNICATION, Plugins.NETWORK_CLIENT, Developers.BITDUBAI, new Version()));
            final ChatIdentityManager chatIdentityManager = (ChatIdentityManager) fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.CHAT_PLATFORM, Layers.IDENTITY, Plugins.CHAT_IDENTITY, Developers.BITDUBAI, new Version()));

            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);

            scheduledThreadPool.scheduleAtFixedRate(
                    new Thread() {
                        @Override
                        public void run() {

                            try {
                                if (!(chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser().size() > 0)) {
                                    chatIdentityManager.createNewIdentityChat("toreto", new byte[0], " ", " ", " ", " ", 0, Frecuency.HIGH);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                clientManager.getConnection().listRegisteredActorProfiles(
                                        new DiscoveryQueryParameters(
                                                null, //identityPublicKey
                                                null,//networkServiceType
                                                "CHT",//actorType
                                                null,//name
                                                null,//alias
                                                null,//extraData
                                                null,//location
                                                null,//distance
                                                Boolean.TRUE,//isOnline
                                                Long.valueOf(0),//lastConnectionTime
                                                10,//max
                                                0//offset
                                        )
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    15,
                    5,
                    TimeUnit.SECONDS
            );*/

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
