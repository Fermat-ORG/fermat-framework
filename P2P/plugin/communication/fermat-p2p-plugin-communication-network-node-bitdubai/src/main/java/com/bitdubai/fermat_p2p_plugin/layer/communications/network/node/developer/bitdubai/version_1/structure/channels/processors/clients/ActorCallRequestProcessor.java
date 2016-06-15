package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorCallMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorCallMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorsProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ResultDiscoveryTraceActor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.ActorCallRequestProcessor</code>
 * process all packages received the type <code>MessageType.ACTOR_CALL_REQUEST</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorCallRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ActorCallRequestProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public ActorCallRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.ACTOR_CALL_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received "+packageReceived.getPackageType());

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        try {

            System.out.println("***** ACTOR CALL REQUEST PROCESSOR: ENTERING IN TRY");

            ActorCallMsgRequest messageContent = ActorCallMsgRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getActorTo())+getGson().toJson(messageContent.getNetworkServiceType()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON) {

                System.out.println("***** ACTOR CALL REQUEST PROCESSOR: MESSAGE IS JSON TYPE");

                ResultDiscoveryTraceActor traceActor = getActor(messageContent.getActorTo().getIdentityPublicKey());

                /*
                 * If all ok, respond whit success message
                 */
                ActorCallMsgRespond actorCallMsgRespond = new ActorCallMsgRespond(messageContent.getNetworkServiceType(), traceActor, ActorCallMsgRespond.STATUS.SUCCESS, ActorCallMsgRespond.STATUS.SUCCESS.toString());
                Package packageRespond = Package.createInstance(actorCallMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ACTOR_CALL_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getBasicRemote().sendObject(packageRespond);

            }

        }catch (Exception exception){

            try {

                LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                ActorCallMsgRespond actorCallMsgRespond = new ActorCallMsgRespond(null, null, ActorsProfileListMsgRespond.STATUS.FAIL, exception.getLocalizedMessage());
                Package packageRespond = Package.createInstance(actorCallMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ACTOR_CALL_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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

    /**
     * Filter all network service from data base that mach
     * with the parameters
     *
     * @param publicKey
     * @return ActorProfile
     */
    private ResultDiscoveryTraceActor getActor(String publicKey) throws CantReadRecordDataBaseException, RecordNotFoundException, InvalidParameterException {

        ActorsCatalog actorsCatalog = getDaoFactory().getActorsCatalogDao().findById(publicKey);

            ActorProfile actorProfile = new ActorProfile();
            actorProfile.setIdentityPublicKey(actorsCatalog.getIdentityPublicKey());
            actorProfile.setAlias(actorsCatalog.getAlias());
            actorProfile.setName(actorsCatalog.getName());
            actorProfile.setActorType(actorsCatalog.getActorType());
            actorProfile.setPhoto(actorsCatalog.getPhoto());
            actorProfile.setExtraData(actorsCatalog.getExtraData());
            actorProfile.setClientIdentityPublicKey(actorsCatalog.getClientIdentityPublicKey());

            //TODO: SET THE LOCATION
            //actorProfile.setLocation();

            NodesCatalog nodesCatalog = null;

            try {
                nodesCatalog = getDaoFactory().getNodesCatalogDao().findById(actorsCatalog.getNodeIdentityPublicKey());
            } catch (RecordNotFoundException e) {
                e.printStackTrace();
            }

            if(nodesCatalog != null) {

                NodeProfile nodeProfile = new NodeProfile();
                nodeProfile.setIdentityPublicKey(nodesCatalog.getIdentityPublicKey());
                nodeProfile.setName(nodesCatalog.getName());
                nodeProfile.setIp(nodesCatalog.getIp());
                nodeProfile.setDefaultPort(nodesCatalog.getDefaultPort());

                return new ResultDiscoveryTraceActor(nodeProfile, actorProfile);
            }

        return null;
    }

}
