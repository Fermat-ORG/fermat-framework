/*
 * @#Test.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils.LocationProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.clients.FermatWebSocketClientNodeChannel;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.AddNodeToCatalogMsgRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.ReceiveActorCatalogTransactionsMsjRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.data.node.request.UpdateNodeInCatalogMsgRequest;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;


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

            ECCKeyPair eccKeyPair = new ECCKeyPair("ba68c5945287f8f318773bff4cfc72136eb4d7f7c56de3125f6584b3554e25af");

            NodeProfile nodeProfile = new NodeProfile();
            nodeProfile.setIp("1.1.1.1");
            nodeProfile.setDefaultPort(8080);
            nodeProfile.setIdentityPublicKey(eccKeyPair.getPublicKey());
            nodeProfile.setName("Fermat Node (black)");
            nodeProfile.setLocation(LocationProvider.acquireLocationThroughIP());

            System.out.println(nodeProfile.toJson());

            FermatWebSocketClientNodeChannel fermatWebSocketClientNodeChannel = new FermatWebSocketClientNodeChannel("localhost", 8080);

          // AddNodeToCatalogMsgRequest addNodeToCatalogMsgRequest = new AddNodeToCatalogMsgRequest(nodeProfile);
           //fermatWebSocketClientNodeChannel.sendMessage(addNodeToCatalogMsgRequest.toJson(), PackageType.ADD_NODE_TO_CATALOG_REQUEST);


          //   nodeProfile.setDefaultPort(9090);
          //   UpdateNodeInCatalogMsgRequest updateNodeInCatalogMsgRequest = new UpdateNodeInCatalogMsgRequest(nodeProfile);
          //   fermatWebSocketClientNodeChannel.sendMessage(updateNodeInCatalogMsgRequest.toJson(), PackageType.UPDATE_NODE_IN_CATALOG_REQUEST);


            List<ActorsCatalogTransaction> actors = new ArrayList<>();
            Location location = LocationProvider.acquireLocationThroughIP();


            for (int i = 0; i < 5; i++) {

                ECCKeyPair id = new ECCKeyPair();

                ActorsCatalogTransaction actorProfile = new ActorsCatalogTransaction();
                actorProfile.setActorType("Actor type " + i);
                actorProfile.setAlias("Alias " + i);
                actorProfile.setName("Actor " + i);
                actorProfile.setIdentityPublicKey(id.getPublicKey());
                actorProfile.setExtraData("Extra " + i);
                actorProfile.setPhoto(new byte[0]);
                actorProfile.setLastLatitude(location.getLatitude());
                actorProfile.setLastLongitude(location.getLongitude());
                actorProfile.setHostedTimestamp(new Timestamp(System.currentTimeMillis()+i));
                actorProfile.setNodeIdentityPublicKey(nodeProfile.getIdentityPublicKey());
                actorProfile.setTransactionType(ActorsCatalogTransaction.ADD_TRANSACTION_TYPE);
                actorProfile.setHashId(String.valueOf(actorProfile.hashCode()));

                actors.add(actorProfile);
            }

            ReceiveActorCatalogTransactionsMsjRequest receiveActorCatalogTransactionsMsjRequest = new ReceiveActorCatalogTransactionsMsjRequest(actors);

            System.out.println(receiveActorCatalogTransactionsMsjRequest.toJson());

            fermatWebSocketClientNodeChannel.sendMessage(receiveActorCatalogTransactionsMsjRequest.toJson(), PackageType.RECEIVE_ACTOR_CATALOG_TRANSACTIONS_REQUEST);


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

