package com.fermat_p2p_layer.version_1.structure;

import com.bitdubai.fermat_api.AbstractAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.fermat_p2p_layer.version_1.P2PLayerPluginRoot;
import com.fermat_p2p_layer.version_1.structure.database.P2PLayerDao;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by mati on 17/08/16.
 *  //todo: manuel ando cansado, no creo que esto esté bien pero igual lo dejo a ver si te dá una idea. mañana lo veo bien
 */
public class PendingMessagesSupervisorAgent extends AbstractAgent {

    private static final int MINIMUM_COUNT_TO_SEND_FULL_MESSAGE = 3;
    private static final int MEDIUM_COUNT = 15;
    private static final int SLEEP_COUNT = 40;



    private static final int SLEEP_TIME = 45;

    private int count;

    private final P2PLayerDao p2PLayerDao;
    private final P2PLayerPluginRoot p2PLayerPluginRoot;

    public PendingMessagesSupervisorAgent(P2PLayerDao p2PLayerDao,P2PLayerPluginRoot p2PLayerPluginRoot) {
        super("PendingMessageSupervisor", SLEEP_TIME, TimeUnit.SECONDS, SLEEP_TIME);
        this.p2PLayerDao = p2PLayerDao;
        this.p2PLayerPluginRoot = p2PLayerPluginRoot;
    }

    @Override
    protected void agentJob() {

        count++;

        boolean haveToSearchMedium = false;
        boolean haveToSearchLarge = false;

        if (count==MEDIUM_COUNT){
            haveToSearchMedium = true;
        } else if (count==SLEEP_COUNT){
            haveToSearchMedium = true;
            haveToSearchLarge = true;
        }


        //obtener los datos de la db para enviar dependiendo de la cantidad de veces que se trató de enviar.
        // acordarse que ahora va a venir por el onMessageSentFail y no por acá ya que no hay un envio de paquete sincrono.
        if(haveToSearchMedium){
            //I'll set medium search as 3 and 9
            try {
                List<NetworkServiceMessage> messageList = p2PLayerDao.listMessagesByFailCount(MINIMUM_COUNT_TO_SEND_FULL_MESSAGE, 9);
                for(NetworkServiceMessage networkServiceMessage : messageList){
                    p2PLayerPluginRoot.sendMessage(networkServiceMessage, networkServiceMessage.getNetworkServiceType(),null,false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(haveToSearchLarge){
            //10 fails and upper
            try {
                //null represents that I need a list with value 10 and upper
                List<NetworkServiceMessage> messageList = p2PLayerDao.listMessagesByFailCount(10, null);
                for(NetworkServiceMessage networkServiceMessage : messageList){
                    p2PLayerPluginRoot.sendMessage(networkServiceMessage, networkServiceMessage.getNetworkServiceType(),null,false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onErrorOccur(Exception e) {

    }


}
