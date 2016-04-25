/*
 * @#Test.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.util.ip_address.IPAddressHelper;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils.LocationProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.FermatEmbeddedNodeServer;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.clients.FermatWebSocketClientNodeChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.AddNodeToCatalogMsgRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConstantAttNames;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.SeedServerConf;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.Test</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Test {

    public static void main(String [ ] args) throws IOException, ServletException {

        try {



            FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel = new FermatWebSocketClientNodeChannel("192.168.1.8", 9090);

            ECCKeyPair eccKeyPair = new ECCKeyPair();

            NodeProfile nodeProfile = new NodeProfile();
            nodeProfile = new NodeProfile();
            nodeProfile.setIp(IPAddressHelper.getCurrentIPAddress());
            nodeProfile.setDefaultPort(8080);
            nodeProfile.setIdentityPublicKey(eccKeyPair.getPublicKey());
            nodeProfile.setName(ConfigurationManager.getValue(ConfigurationManager.NODE_NAME));
            nodeProfile.setLocation(LocationProvider.acquireLocationThroughIP());


            AddNodeToCatalogMsgRequest addNodeToCatalogMsgRequest = new AddNodeToCatalogMsgRequest(nodeProfile);
            fermatWebSocketClientNodeChannel.sendMessage(addNodeToCatalogMsgRequest.toJson(), PackageType.ADD_NODE_TO_CATALOG_REQUEST);

           Thread.currentThread().sleep(30000);


            System.out.println("FIN");





           /* System.out.println("***********************************************************************");
            System.out.println("* FERMAT - Plugin Network Node - Version 1.0 (2015)                   *");
            System.out.println("* www.fermat.org                                                      *");
            System.out.println("* www.bitDubai.com                                                    *");
            System.out.println("* NETWORK NODE STARTING                                               *");
            System.out.println("***********************************************************************");

            FermatEmbeddedNodeServer fermatEmbeddedNodeServer = new FermatEmbeddedNodeServer();
            fermatEmbeddedNodeServer.start();

            openUri();*/

        }catch (Exception e){

            System.out.println("***********************************************************************");
            System.out.println("*FERMAT - ERROR NETWORK NODE                                          *");
            System.out.println("***********************************************************************");
            e.printStackTrace();
            System.exit(1);
        }

       /* System.out.println("Network node started satisfactory..."); */
    }

    /**
     * Open the node uri on the browser
     * @throws IOException
     */
    private static void openUri() throws IOException, URISyntaxException {

        if( !java.awt.Desktop.isDesktopSupported() ) {
            System.out.println("Desktop is not supported.");
            System.out.println("Go To --> http://localhost:8080/");
            return;
        }

        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

        if( !desktop.isSupported(java.awt.Desktop.Action.BROWSE) ) {
            System.out.println("Desktop doesn't support the browse action.");
            System.out.println("Go To --> http://localhost:8080/");
            return;
        }

        System.out.println("Opening in the browser --> http://localhost:8080/");

        java.net.URI uri = new java.net.URI("http://localhost:8080/");
        desktop.browse( uri );

    }
}

