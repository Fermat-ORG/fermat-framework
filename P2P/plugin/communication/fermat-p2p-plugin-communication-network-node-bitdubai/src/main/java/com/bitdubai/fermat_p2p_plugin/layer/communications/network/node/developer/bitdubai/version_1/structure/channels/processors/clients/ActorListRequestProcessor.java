package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorCallMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.ActorListRequestProcessor</code>
 * process all packages received the type <code>MessageType.ACTOR_LIST_REQUEST</code><p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorListRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ActorListRequestProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public ActorListRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.ACTOR_LIST_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received " + packageReceived.getPackageType());

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        ActorListMsgRequest messageContent = ActorListMsgRequest.parseContent(packageReceived.getContent());

        try {

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getParameters())+getGson().toJson(messageContent.getNetworkServicePublicKey())+getGson().toJson(messageContent.getClientPublicKey()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON) {

                List<ActorProfile> actorsList = filterActors(messageContent.getParameters(), messageContent.getClientPublicKey());

                /*
                 * If all ok, respond whit success message
                 */
                ActorListMsgRespond actorListMsgRespond = new ActorListMsgRespond(ActorCallMsgRespond.STATUS.SUCCESS, ActorCallMsgRespond.STATUS.SUCCESS.toString(), actorsList, messageContent.getNetworkServicePublicKey(), messageContent.getQueryId());
                Package packageRespond = Package.createInstance(actorListMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ACTOR_LIST_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            }

        } catch (Exception exception){

            try {

                LOG.error(exception.getMessage());
                exception.printStackTrace();

                /*
                 * Respond whit fail message
                 */
                ActorListMsgRespond actorCallMsgRespond = new ActorListMsgRespond(
                        ActorListMsgRespond.STATUS.FAIL,
                        exception.getLocalizedMessage(),
                        null,
                        null,
                        (messageContent == null ? null : messageContent.getQueryId())
                );
                Package packageRespond = Package.createInstance(actorCallMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ACTOR_LIST_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

    }

    /**
     * Filter all actor component profiles from database that match with the given parameters.
     * We'll use the @clientIdentityPublicKey param to filter the actors who belongs to the client asking.
     *
     * @param discoveryQueryParameters parameters of the discovery done by the user.
     *
     * @return a list of actor profiles.
     */
    private List<ActorProfile> filterActors(final DiscoveryQueryParameters discoveryQueryParameters,
                                            final String                   clientIdentityPublicKey ) throws CantReadRecordDataBaseException, InvalidParameterException {

        Map<String, ActorProfile> profileList = new HashMap<>();

        List<ActorsCatalog> actorsList;

        int max    = 10;
        int offset =  0;

        if (discoveryQueryParameters.getMax() != null && discoveryQueryParameters.getMax() > 0)
            max = (discoveryQueryParameters.getMax() > 100) ? 100 : discoveryQueryParameters.getMax();

        if (discoveryQueryParameters.getOffset() != null && discoveryQueryParameters.getOffset() >= 0)
            offset = discoveryQueryParameters.getOffset();

        actorsList = getDaoFactory().getActorsCatalogDao().findAll(discoveryQueryParameters, clientIdentityPublicKey, max, offset);

        if (discoveryQueryParameters.isOnline())
            for (ActorsCatalog actorsCatalog : actorsList)
                profileList.put(actorsCatalog.getIdentityPublicKey(), buildActorProfileFromActorCatalogRecordAndSetStatus(actorsCatalog));
        else
            for (ActorsCatalog actorsCatalog : actorsList)
                profileList.put(actorsCatalog.getIdentityPublicKey(), buildActorProfileFromActorCatalogRecord(actorsCatalog));

        return new ArrayList<>(profileList.values());
    }

    /**
     * Build an Actor Profile from an Actor Catalog record.
     */
    private ActorProfile buildActorProfileFromActorCatalogRecord(final ActorsCatalog actor){

        ActorProfile actorProfile = new ActorProfile();

        actorProfile.setIdentityPublicKey(actor.getIdentityPublicKey());
        actorProfile.setAlias            (actor.getAlias());
        actorProfile.setName             (actor.getName());
        actorProfile.setActorType        (actor.getActorType());
        actorProfile.setPhoto            (actor.getPhoto());
        actorProfile.setExtraData        (actor.getExtraData());
        actorProfile.setLocation         (actor.getLastLocation());

        return actorProfile;
    }

    /**
     * Build an Actor Profile from an Actor Catalog record and set its status.
     */
    private ActorProfile buildActorProfileFromActorCatalogRecordAndSetStatus(final ActorsCatalog actor){

        ActorProfile actorProfile = new ActorProfile();

        actorProfile.setIdentityPublicKey(actor.getIdentityPublicKey());
        actorProfile.setAlias            (actor.getAlias());
        actorProfile.setName             (actor.getName());
        actorProfile.setActorType        (actor.getActorType());
        actorProfile.setPhoto            (actor.getPhoto());
        actorProfile.setExtraData        (actor.getExtraData());
        actorProfile.setLocation         (actor.getLastLocation());

        actorProfile.setStatus           (isActorOnline(actor));

        return actorProfile;
    }

    /**
     * Through this method we're going to determine a status for the actor profile.
     * First we'll check if the actor belongs to this node:
     *   if it belongs we'll check directly if he is online in the check-ins table
     *   if not we'll call to the other node.
     *
     * @param actorsCatalog  the record of the profile from the actors catalog table.
     *
     * @return an element of the ProfileStatus enum.
     */
    private ProfileStatus isActorOnline(ActorsCatalog actorsCatalog) {

        try {

            if(actorsCatalog.getNodeIdentityPublicKey().equals(getNetworkNodePluginRoot().getIdentity().getPublicKey())) {

                if (getDaoFactory().getCheckedInActorDao().exists(actorsCatalog.getIdentityPublicKey()))
                    return ProfileStatus.ONLINE;
                else
                    return ProfileStatus.OFFLINE;

            } else {

                return isActorOnlineInOtherNode(actorsCatalog);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ProfileStatus.UNKNOWN;
        }
    }

    /**
     * Through this method we're going to determine a status for the actor profile calling another node.
     *
     * @param actorsCatalog  the record of the profile from the actors catalog table.
     *
     * @return an element of the ProfileStatus enum.
     */
    private ProfileStatus isActorOnlineInOtherNode(final ActorsCatalog actorsCatalog) {

        try {

            String nodeUrl = getNodeUrl(actorsCatalog.getNodeIdentityPublicKey());

            URL url = new URL("http://" + nodeUrl + "/fermat/rest/api/v1/online/component/actor/" + actorsCatalog.getIdentityPublicKey());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String respond = reader.readLine();

            if (conn.getResponseCode() == 200 && respond != null && respond.contains("success")) {
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respond.trim());

                return respondJsonObject.get("isOnline").getAsBoolean() ? ProfileStatus.ONLINE : ProfileStatus.OFFLINE;

            } else {
                return ProfileStatus.UNKNOWN;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ProfileStatus.UNKNOWN;
        }
    }

    /**
     * Through this method we'll get the node url having in count its node catalog record.
     *
     * @param publicKey  of the node.
     *
     * @return node's url string.
     */
    private String getNodeUrl(final String publicKey) {

        try {

            NodesCatalog nodesCatalog = getDaoFactory().getNodesCatalogDao().findById(publicKey);
            return nodesCatalog.getIp()+":"+nodesCatalog.getDefaultPort();

        } catch (RecordNotFoundException exception) {
            throw new RuntimeException("Node not found in catalog: "+exception.getMessage());
        } catch (Exception exception) {
            throw new RuntimeException("Problem trying to find the node in the catalog: "+exception.getMessage());
        }
    }
}
