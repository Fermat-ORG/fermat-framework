package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorCallMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
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

        LOG.info("Processing new package received "+packageReceived.getPackageType());

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);

        ActorListMsgRequest messageContent = null;
        try {

            messageContent = ActorListMsgRequest.parseContent(packageReceived.getContent());

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

        }catch (Exception exception){

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
                        messageContent == null ? null : messageContent.getQueryId()
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

    private List<ActorsCatalog> filterActorsOnline(List<ActorsCatalog>  actorsCatalogs){

        List<ActorsCatalog> actors = new ArrayList<>();

        for(ActorsCatalog actorsCatalog : actorsCatalogs){

            try {

                if(actorsCatalog.getNodeIdentityPublicKey().equals(getNetworkNodePluginRoot().getIdentity().getPublicKey())) {

                    if (getDaoFactory().getCheckedInActorDao().exists(actorsCatalog.getIdentityPublicKey()))
                        actors.add(actorsCatalog);

                } else if(isActorOnline(actorsCatalog))
                    actors.add(actorsCatalog);

            } catch (CantReadRecordDataBaseException e) {
                e.printStackTrace();
            }

        }

        return actors;
    }

    /**
     * Filter all network service from data base that mach
     * with the parameters
     *
     * @param discoveryQueryParameters
     * @return List<ActorProfile>
     */
    private List<ActorProfile> filterActors(DiscoveryQueryParameters discoveryQueryParameters, String clientIdentityPublicKey) throws CantReadRecordDataBaseException, InvalidParameterException {

        List<ActorProfile> profileList = new ArrayList<>();

        Map<String, Object> filters = constructFiltersActorTable(discoveryQueryParameters);
        List<ActorsCatalog> actorsList;

        int max    = 10;
        int offset =  0;

        if( discoveryQueryParameters.getMax() != null &&
                discoveryQueryParameters.getOffset() != null &&
                discoveryQueryParameters.getMax() > 0 &&
                discoveryQueryParameters.getOffset() >= 0) {
            max = (discoveryQueryParameters.getMax() > 100) ? 100 : discoveryQueryParameters.getMax();
            offset = discoveryQueryParameters.getOffset();
        }

        if (discoveryQueryParameters.getLocation() != null)
            actorsList = getDaoFactory().getActorsCatalogDao().findAllNearestTo(filters, max, offset, discoveryQueryParameters.getLocation());
        else
            actorsList = getDaoFactory().getActorsCatalogDao().findAll(filters, max, offset);

        List<ActorsCatalog> actors = filterActorsOnline(actorsList);

        for (ActorsCatalog actorsCatalog : actors) {

            if (clientIdentityPublicKey == null || actorsCatalog.getClientIdentityPublicKey() == null || !actorsCatalog.getClientIdentityPublicKey().equals(clientIdentityPublicKey)) {
                ActorProfile actorProfile = new ActorProfile();
                actorProfile.setIdentityPublicKey(actorsCatalog.getIdentityPublicKey());
                actorProfile.setAlias(actorsCatalog.getAlias());
                actorProfile.setName(actorsCatalog.getName());
                actorProfile.setActorType(actorsCatalog.getActorType());
                actorProfile.setPhoto(actorsCatalog.getPhoto());
                actorProfile.setExtraData(actorsCatalog.getExtraData());
                actorProfile.setLocation(actorsCatalog.getLastLocation());

                profileList.add(actorProfile);
            }
        }

        return profileList;

    }

    /**
     * Construct data base filter from discovery query parameters
     *
     * @param discoveryQueryParameters
     *
     * @return Map<String, Object> filters
     */
    private Map<String, Object> constructFiltersActorTable(DiscoveryQueryParameters discoveryQueryParameters){

        Map<String, Object> filters = new HashMap<>();

        if (discoveryQueryParameters.getIdentityPublicKey() != null)
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME, discoveryQueryParameters.getIdentityPublicKey());

        if (discoveryQueryParameters.getName() != null)
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NAME_COLUMN_NAME, discoveryQueryParameters.getName());

        if (discoveryQueryParameters.getAlias() != null)
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ALIAS_COLUMN_NAME, discoveryQueryParameters.getAlias());

        if (discoveryQueryParameters.getActorType() != null)
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ACTOR_TYPE_COLUMN_NAME, discoveryQueryParameters.getActorType());

        if (discoveryQueryParameters.getExtraData() != null)
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME, discoveryQueryParameters.getExtraData());

        return filters;
    }

    private Boolean isActorOnline(ActorsCatalog actorsCatalog){

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

                return respondJsonObject.get("isOnline").getAsBoolean();

            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

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
