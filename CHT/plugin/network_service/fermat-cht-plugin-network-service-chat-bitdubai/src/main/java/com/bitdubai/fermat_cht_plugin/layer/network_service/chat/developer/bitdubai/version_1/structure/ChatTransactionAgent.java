package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.Map;

/** TODO: Gabriel, please, fix this class, I'm gonna comment all the lines that had instances from Transaction Transmission (CBP) because is not correct and your code is not compiling
 * Created by root on 07/01/16.
 */
public class ChatTransactionAgent {
    /*
   * Represent the sleep time for the read or send (2000 milliseconds)
   */
    private static final long SLEEP_TIME = 15000;
    private static final long RECEIVE_SLEEP_TIME = 15000;


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
    //CryptoTransmissionNetworkServiceLocal communicationNetworkServiceLocal;

    /**
     * Communication manager, Class to obtain the connections
     */
    CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     *
     */
    WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * plugin root
     */
    //TODO: to fix
    //TransactionTransmissionPluginRoot transactionTransmissionPluginRoot;

    /**
     * DAO TransactionTransmission
     */
    //TODO: to fix
    //TransactionTransmissionContractHashDao transactionTransmissionContractHashDao;
    //TransactionTransmissionConnectionsDAO transactionTransmissionConnectionsDAO;

    /**
     *  Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * PlatformComponentProfile platformComponentProfile
     */
    PlatformComponentProfile platformComponentProfile;


    /**
     * Represent the send cycle tread of this NetworkService
     */
    private Thread toSend;

    /**
     * Represent the send messages tread of this TemplateNetworkServiceRemoteAgent
     */
    private Thread toReceive;

    /**
     * Cache de metadata con conexions leidas anteriormente
     * ActorPublicKey, metadata de respuesta
     */
    //TODO: to fix
    //Map<String, TransactionTransmissionStates> cacheResponseMetadataFromRemotes;

    /**
     * Map contains publicKey from componentProfile to connect
     *  and the number of connections intents.
     */
    Map<String,Integer> connectionsCounters;

    /**
     * Cola de espera, a la cual van pasando las conecciones que no se pudieron conectar para que se hagan con más tiempo y no saturen el server
     */
    //TODO: to fix
    //Map<String, TransactionTransmissionPlatformComponentProfilePlusWaitTime> waitingPlatformComponentProfile;

    /**
     * Pool connections requested waiting for peer or server response
     *
     * publicKey  and transaccion metadata waiting to be a response
     */
    //TODO: to fix
    //Map<String,BusinessTransactionMetadata> poolConnectionsWaitingForResponse;


    Map<String, FermatMessage> receiveMessage;
    private boolean flag=true;


    private EventManager eventManager;
}
