/*
* @#AssetUserActorNetworkServiceAgent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.agents;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.AssetRedeemPointActorNetworkServicePluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantEstablishConnectionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.agents.AssetUserActorNetworkServiceAgent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 15/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetRedeemPointActorNetworkServiceAgent {

    /*
    * Represent the sleep time for the  send (15000 milliseconds)
    */
    private static final long SLEEP_TIME = 15000;


    private AssetRedeemPointActorNetworkServicePluginRoot assetRedeemPointActorNetworkServicePluginRoot;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * Represent is the tread is running
     */
    private Boolean running;

    /**
     * Represent the identity
     */
    private ECCKeyPair identity;

    /**
     * Communication Service, Class to send the message
     */

    /**
     * Communication manager, Class to obtain the connections
     */
    CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     *
     */
    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    /**
     * PlatformComponentProfile platformComponentProfile
     */
    PlatformComponentProfile platformComponentProfile;


    /**
     * Represent the send cycle tread of this NetworkService
     */
    private Thread toSend;

    private boolean flag=true;

    private OutgoingMessageDao outgoingMessageDao;

    private Database dataBase;

    private List<FermatMessage> listRecorMessageToSend;

    /**
     * Pool connections requested waiting for peer or server response
     *
     * publicKey  and transaccion metadata waiting to be a response
     */
    Map<String,FermatMessage> poolConnectionsWaitingForResponse;


    public AssetRedeemPointActorNetworkServiceAgent(AssetRedeemPointActorNetworkServicePluginRoot assetRedeemPointActorNetworkServicePluginRoot,
                                                    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager,
                                                    CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
                                                    PlatformComponentProfile platformComponentProfile,
                                                    ErrorManager errorManager,
                                                    ECCKeyPair identity,
                                                    Database dataBase){


        this.assetRedeemPointActorNetworkServicePluginRoot = assetRedeemPointActorNetworkServicePluginRoot;
        this.wsCommunicationsCloudClientManager = wsCommunicationsCloudClientManager;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.platformComponentProfile = platformComponentProfile;
        this.errorManager = errorManager;
        this.identity = identity;
        this.dataBase =dataBase;

        outgoingMessageDao = new OutgoingMessageDao(this.dataBase);

        poolConnectionsWaitingForResponse = new HashMap<>();


        //Create a thread to send the messages
        this.toSend = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running){
                    sendCycle();
                }
            }
        });


    }

    /**
     * Start the internal threads to make the job
     */
    public void start(){

        //Set to running
        this.running  = Boolean.TRUE;

        System.out.println("START READ IN THE TABLE TO SEND MESSAGE WITH STATE PENDING_TO_SEND ");

        //Start the Thread
        toSend.start();

    }


    /**
     * Pause the internal threads
     */
    public void pause(){ this.running  = Boolean.FALSE;  }

    /**
     * Resume the internal threads
     */
    public void resume(){  this.running  = Boolean.TRUE; }


    /**
     * Stop the internal threads
     */
    public void stop(){  toSend.interrupt(); }


    /**
     *
     * Lifeclycle of the actornetworkService
     *
     */
    public void sendCycle(){

        try{

            if(this.assetRedeemPointActorNetworkServicePluginRoot.isRegister()){

                processMetadata();
            }

            if(toSend.isInterrupted() == Boolean.FALSE){
                //Sleep for a time
                Thread.sleep(AssetRedeemPointActorNetworkServiceAgent.SLEEP_TIME);
            }

        }catch(InterruptedException e) {
            toSend.interrupt();
            System.out.println("CommunicationNetworkServiceRemoteAgent - Thread Interrupted stopped ...  ");
            return;
        }

    }


    private void processMetadata() {

        try {

            listRecorMessageToSend = outgoingMessageDao.findAll(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, FermatMessagesStatus.PENDING_TO_SEND.getCode());

            if(listRecorMessageToSend != null && !listRecorMessageToSend.isEmpty()){


                    for (FermatMessage fm : listRecorMessageToSend) {

                        if(!poolConnectionsWaitingForResponse.containsKey(fm.getReceiver())) {



                            /*
                            * Create the sender basic profile
                            */
                                PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(fm.getSender(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_ASSET_REDEEM_POINT);

                            /*
                             * Create the receiver basic profile
                             */
                                PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(fm.getReceiver(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_ASSET_REDEEM_POINT);


                            try {
                                communicationNetworkServiceConnectionManager.connectTo(sender, platformComponentProfile, receiver);
                            } catch (CantEstablishConnectionException e) {
                                e.printStackTrace();
                            }


                            // pass the metada to a pool wainting for the response of the other peer or server failure
                                poolConnectionsWaitingForResponse.put(fm.getReceiver(), fm);
                        }

                    }

            }


        } catch (CantReadRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not send Message PENDING_TO_SEND"));
        }



    }

    public void connectionFailure(String identityPublicKey){
        this.poolConnectionsWaitingForResponse.remove(identityPublicKey);
    }

}
