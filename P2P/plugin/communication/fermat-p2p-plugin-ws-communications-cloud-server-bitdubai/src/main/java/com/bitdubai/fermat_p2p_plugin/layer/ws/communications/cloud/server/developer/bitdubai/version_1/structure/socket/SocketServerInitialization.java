/*
* @#SocketServerInitialization.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.socket;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.socket.SocketServerInitialization</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 20/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SocketServerInitialization {

    private static final int PORT_SOCKET = 9001;

    /**
     * Represent the wsCommunicationCloudServer
     */
    private WsCommunicationCloudServer wsCommunicationCloudServer;

    public SocketServerInitialization(WsCommunicationCloudServer wsCommunicationCloudServer) throws IOException{

        this.wsCommunicationCloudServer = wsCommunicationCloudServer;
        ServerSocket serverSocket = new ServerSocket(PORT_SOCKET);
        Socket socket = null;

        System.out.println("************************* INIT SOCKET TO REQUEST LIST **************************");

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new SocketMultiThreadHandle(socket,wsCommunicationCloudServer).start();
        }


    }

}
