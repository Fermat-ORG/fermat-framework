/*
 * @#FermatLinuxAppMain.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.linux.core.app;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_core.FermatSystem;
import com.bitdubai.fermat_osa_linux_core.OSAPlatform;
import com.bitdubai.linux.core.app.version_1.structure.context.FermatLinuxContext;

/**
 * The Class <code>com.bitdubai.linux.core.app.FermatLinuxAppMain</code> initialize
 * all fermat system
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatLinuxAppMain {

    /**
     *  Represent the fermatSystem instance
     */
    private static final FermatSystem fermatSystem = new FermatSystem();

    /**
     * Represent the fermatContext instance
     */
    private static final FermatLinuxContext fermatLinuxContext = FermatLinuxContext.getInstance();

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String [ ] args){

        try {

            System.out.println("***********************************************************************");
            System.out.println("* FERMAT - Linux Core - Version 1.0 (2015)                            *");
            System.out.println("* www.fermat.org                                                      *");
            System.out.println("* www.bitDubai.com                                                    *");
            System.out.println("***********************************************************************");
            System.out.println("");
            System.out.println("- Starting process ...");

            /*
             * Start the system
             */
            fermatSystem.start(fermatLinuxContext, new OSAPlatform());
            fermatSystem.startAndGetPluginVersion(new PluginVersionReference(Platforms.COMMUNICATION_PLATFORM, Layers.COMMUNICATION, Plugins.BITDUBAI_COMMUNICATIONS_NETWORK_NODE, Developers.BITDUBAI, new Version()) );

        }catch (Exception e){

            System.out.println("***********************************************************************");
            System.out.println("*FERMAT - ERROR                                                       *");
            System.out.println("***********************************************************************");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("FERMAT - Linux Core - started satisfactory...");
    }
}
