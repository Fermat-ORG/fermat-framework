package com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.AgentStatus;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mati on 2015.10.30..
 */
public abstract class AbstractNetworkServiceProcessorAgent<N extends NetworkService> extends FermatAgent implements NetworkServiceAgent{


    private long sendThreadSleepTime = 15000;
    private long receiveThreadSleepTime = 15000;


    private Thread toSend   ;
    private Thread toReceive;

    // network services registered
    protected Map<String, ActorNetworkServiceRecord> poolConnectionsWaitingForResponse;

    // counter and wait time
    protected Map<String, ActorNetworkServiceConnectionIncubation> waitingPlatformComponentProfile;


    protected final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    protected final N networkServicePluginRoot;

    protected final ErrorManager errorManager;

    protected final EventManager eventManager;

    protected final WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    public AbstractNetworkServiceProcessorAgent(
                                            final CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager,
                                            final N networkServicePluginRoot,
                                            final ErrorManager                                 errorManager                                ,
                                            final EventManager                                 eventManager                                ,
                                            final WsCommunicationsCloudClientManager           wsCommunicationsCloudClientManager) {

        this.networkServicePluginRoot = networkServicePluginRoot;
        this.communicationNetworkServiceConnectionManager = communicationNetworkServiceConnectionManager;
        this.errorManager                                 = errorManager                                ;
        this.eventManager                                 = eventManager                                ;
        this.wsCommunicationsCloudClientManager           = wsCommunicationsCloudClientManager          ;
        this.status                                       = AgentStatus.CREATED                         ;

        waitingPlatformComponentProfile   = new HashMap<>();
        poolConnectionsWaitingForResponse = new HashMap<>();


        sendThreadSleepTime = sendThreadSleepTime();

        receiveThreadSleepTime = receiveThreadSleepTime();

        createThreads();

    }

    private void createThreads(){
        this.toSend = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    sendCycle();
            }
        });

        //Create a thread to receive the messages
        this.toReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning())
                    receiveCycle();
            }
        });
    }


    @Override
    public void start(){
    }

    private void sendCycle() {
        try {
            if(networkServicePluginRoot.isRegister()) processSend();
            toSend.sleep(sendThreadSleepTime);
        } catch (InterruptedException e) {
            reportUnexpectedError(FermatException.wrapException(e));
        } catch(Exception e) {
            reportUnexpectedError(FermatException.wrapException(e));
        }
    }

    private void receiveCycle() {
        try {
            if(networkServicePluginRoot.isRegister()) processReceive();
            toReceive.sleep(receiveThreadSleepTime);
        } catch (InterruptedException e) {
            reportUnexpectedError(FermatException.wrapException(e));
        } catch(Exception e) {
            reportUnexpectedError(FermatException.wrapException(e));
        }
    }

    abstract long sendThreadSleepTime();

    abstract long receiveThreadSleepTime();

    abstract void processSend();

    abstract void processReceive();

    abstract void reportUnexpectedError(FermatException e);


}
