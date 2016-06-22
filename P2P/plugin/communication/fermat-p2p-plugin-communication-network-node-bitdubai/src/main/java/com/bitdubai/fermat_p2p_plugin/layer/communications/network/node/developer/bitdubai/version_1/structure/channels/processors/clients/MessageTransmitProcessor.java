package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MessageTransmitRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.MsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.caches.ClientsSessionMemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.MessageTransmitProcessor</code>
 * process all packages received the type <code>PackageType.MESSAGE_TRANSMIT</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 30/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MessageTransmitProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(MessageTransmitProcessor.class));

    /**
     * Represent the clientsSessionMemoryCache instance
     */
    private ClientsSessionMemoryCache clientsSessionMemoryCache;

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public MessageTransmitProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.MESSAGE_TRANSMIT);
        this.clientsSessionMemoryCache = ClientsSessionMemoryCache.getInstance();
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String senderIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        MessageTransmitRespond messageTransmitRespond = null;
        NetworkServiceMessage messageContent = null;

        try {

            /*
             * Get the content
             */
            messageContent = NetworkServiceMessage.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent), senderIdentityPublicKey);

            /*
             * Get the destination
             */
            String destinationIdentityPublicKey = packageReceived.getDestinationPublicKey();

            /*
             * Get the connection to the destination
             */
            Session clientDestination =  clientsSessionMemoryCache.get(destinationIdentityPublicKey);


            if (clientDestination == null) {
                try {
                    CheckedInActor checkedInActor = getDaoFactory().getCheckedInActorDao().findById(destinationIdentityPublicKey);
                    clientDestination = clientsSessionMemoryCache.get(checkedInActor.getClientIdentityPublicKey());
                } catch (CantReadRecordDataBaseException| RecordNotFoundException e) {
                    System.out.println("i suppose that the actor is no longer connected");
                    e.printStackTrace();
                }
            }

            if (clientDestination != null){

                /*
                 * Redirect the content and send
                 */
                clientDestination.getBasicRemote().sendObject(packageReceived);

                /*
                 * Notify to de sender the message was transmitted
                 */
                messageTransmitRespond = new MessageTransmitRespond(MsgRespond.STATUS.SUCCESS, MsgRespond.STATUS.SUCCESS.toString(), messageContent.getId());
                Package packageRespond = Package.createInstance(messageTransmitRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.MESSAGE_TRANSMIT_RESPONSE, channelIdentityPrivateKey, senderIdentityPublicKey);
                session.getBasicRemote().sendObject(packageRespond);

            }else {

                /*
                 * Notify to de sender the message can not transmit
                 */
                messageTransmitRespond = new MessageTransmitRespond(MsgRespond.STATUS.FAIL, "The destination is not more available", messageContent.getId());
                Package packageRespond = Package.createInstance(messageTransmitRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.MESSAGE_TRANSMIT_RESPONSE, channelIdentityPrivateKey, senderIdentityPublicKey);
                session.getBasicRemote().sendObject(packageRespond);
            }


        }catch (Exception exception){

            try {

                exception.printStackTrace();
                //LOG.error(exception.getMessage());

                messageTransmitRespond = new MessageTransmitRespond(MsgRespond.STATUS.FAIL, exception.getMessage(), messageContent.getId());
                Package packageRespond = Package.createInstance(messageTransmitRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.MESSAGE_TRANSMIT_RESPONSE, channelIdentityPrivateKey, senderIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getBasicRemote().sendObject(packageRespond);

            } catch (IOException iOException) {
                LOG.error(iOException.getMessage());
            } catch (EncodeException encodeException) {
                LOG.error(encodeException.getMessage());
            }

        }

    }

}
