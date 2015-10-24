/*
 * @#WsCommunicationVpnServerManagerAgent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.vpn;


import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.WsCommunicationCloudServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.vpn.WsCommunicationVpnServerManagerAgent</code> this
 * agent manage all the WsCommunicationVpnServer created
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationVpnServerManagerAgent extends Thread{

    /**
     * Represent the SLEEP_TIME
     */
    private static long SLEEP_TIME = 60000;

    /**
     * Holds the vpnServersActivesCache
     */
    private List<WsCommunicationVPNServer> vpnServersActivesCache;

    /**
     * Represent the lastPortAssigned
     */
    private Integer lastPortAssigned;

    /**
     * Represent the hostIp
     */
    private String hostIp;

    /**
     * Represent the isRunning
     */
    private boolean isRunning;

    /**
     * Constructor whit parameter
     *
     * @param serverIp
     * @param cloudServerIp
     */
    public WsCommunicationVpnServerManagerAgent(String serverIp, Integer cloudServerIp){
        this.vpnServersActivesCache = new ArrayList<>();
        this.lastPortAssigned = cloudServerIp;
        this.hostIp = serverIp;
        this.isRunning = Boolean.FALSE;
    }

    /**
     * Create a new WsCommunicationVPNServer
     *
     * @param participants
     */
    public WsCommunicationVPNServer createNewWsCommunicationVPNServer(List<PlatformComponentProfile> participants, WsCommunicationCloudServer wsCommunicationCloudServer, NetworkServiceType networkServiceTypeApplicant) {

        InetSocketAddress inetSocketAddress = new InetSocketAddress(hostIp, (lastPortAssigned+=1));
        WsCommunicationVPNServer vpnServer = new WsCommunicationVPNServer(inetSocketAddress, participants, wsCommunicationCloudServer, networkServiceTypeApplicant);
        vpnServersActivesCache.add(vpnServer);
        vpnServer.start();

        return vpnServer;
    }


    /**
     * (non-javadoc)
     * @see Thread#run()
     */
    @Override
    public void run() {

        try {

            //While is running
            while (isRunning){

                //If empty
                if (vpnServersActivesCache.isEmpty()){
                    //Auto stop
                    isRunning = Boolean.FALSE;
                }

            /*    for (WsCommunicationVPNServer wsCommunicationVPNServer : vpnServersActivesCache) {

                    //Verified is this vpn is active
                    if (!wsCommunicationVPNServer.isActive()){

                        //If no active stop it
                        wsCommunicationVPNServer.stop();
                        vpnServersActivesCache.remove(wsCommunicationVPNServer);

                    }
                } */

                sleep(WsCommunicationVpnServerManagerAgent.SLEEP_TIME);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * (non-javadoc)
     * @see Thread#start()
     */
    @Override
    public synchronized void start() {
        isRunning = Boolean.TRUE;
        super.start();
    }

    /**
     * Get the is running
     * @return boolean
     */
    public boolean isRunning() {
        return isRunning;
    }
}
