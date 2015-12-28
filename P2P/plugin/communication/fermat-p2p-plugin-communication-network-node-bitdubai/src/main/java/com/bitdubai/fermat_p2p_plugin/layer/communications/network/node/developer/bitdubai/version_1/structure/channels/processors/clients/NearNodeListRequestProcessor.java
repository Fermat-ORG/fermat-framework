/*
 * @#NearNodeListRequestProcessor.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;


import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationProvider;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.NearNodeListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.NearNodeListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.DistanceCalculator;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.WebSocketChannelServerEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;

import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.NearNodeListRequestProcessor</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NearNodeListRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(NearNodeListRequestProcessor.class.getName());

    /**
     * Constructor whit parameter
     *
     * @param webSocketChannelServerEndpoint register
     */
    public NearNodeListRequestProcessor(WebSocketChannelServerEndpoint webSocketChannelServerEndpoint) {
        super(webSocketChannelServerEndpoint, PackageType.CHECK_IN_ACTOR_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get("");

        try {

            NearNodeListMsgRequest messageContent = (NearNodeListMsgRequest) packageReceived.getContent();

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getClientLocation()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON){

                /*
                 * Get the client location
                 */
                Location clientLocation = messageContent.getClientLocation();

                /*
                 * Get the node catalog list
                 */
                List<NodesCatalog> nodesCatalogs = getDaoFactory().getNodesCatalogDao().findAll();

                /*
                 * Filter and order
                 */
                List<NodesCatalog> nodesCatalogsFiltered = applyGeoLocationFilter(clientLocation, nodesCatalogs);

                /*
                 * Create a node list
                 */
                List<NodeProfile> nodesProfileList = new ArrayList<>();
                for (final NodesCatalog node: nodesCatalogsFiltered.subList(0,50)) {

                    NodeProfile nodeProfile = new NodeProfile();
                    nodeProfile.setIdentityPublicKey(node.getIdentityPublicKey());
                    nodeProfile.setName(node.getName());
                    nodeProfile.setDefaultPort(node.getDefaultPort());
                    nodeProfile.setIp(node.getIp());

                    //TODO: SET THE LOCATION

                    nodesProfileList.add(nodeProfile);
                }

                /*
                 * If all ok, respond whit success message
                 */
                NearNodeListMsgRespond respondNearNodeListMsg = new NearNodeListMsgRespond(NearNodeListMsgRespond.STATUS.SUCCESS, NearNodeListMsgRespond.STATUS.SUCCESS.toString(),nodesProfileList);
                Package packageRespond = Package.createInstance(respondNearNodeListMsg, packageReceived.getNetworkServiceTypeSource(), PackageType.NEAR_NODE_LIST_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getBasicRemote().sendObject(packageRespond);

            }

        }catch (Exception exception){

            try {

                LOG.error(exception.getMessage());

                /*
                 * If all ok, respond whit success message
                 */
                NearNodeListMsgRespond respondNearNodeListMsg = new NearNodeListMsgRespond(NearNodeListMsgRespond.STATUS.FAIL, exception.getLocalizedMessage(), null);
                Package packageRespond = Package.createInstance(respondNearNodeListMsg, packageReceived.getNetworkServiceTypeSource(), PackageType.NEAR_NODE_LIST_RESPOND, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     *  Method that apply geo location filter to the list
     *
     * @param clientLocation
     * @param nodesCatalogs
     * @return List<NodesCatalog>
     */
    private List<NodesCatalog> applyGeoLocationFilter(Location clientLocation, List<NodesCatalog> nodesCatalogs) {

        /*
         * Hold the data ordered by distance
         */
        Map<Double, NodesCatalog> orderedByDistance = new TreeMap<>();

        /*
         * For each node
         */
        for (final NodesCatalog node: nodesCatalogs) {

            /*
             * If component have a geo location
             */
            if (node.getLastLatitude() != null &&
                    node.getLastLongitude() != null){


                Location nodeLocation = new Location() {
                    @Override
                    public Double getLatitude() {
                        return node.getLastLatitude();
                    }

                    @Override
                    public Double getLongitude() {
                        return node.getLastLongitude();
                    }

                    @Override
                    public Double getAltitude() {
                        return null;
                    }

                    @Override
                    public Long getTime() {
                        return null;
                    }

                    @Override
                    public LocationProvider getProvider() {
                        return null;
                    }
                };

                /*
                 * Calculate the distance between the two points
                 */
                Double componentDistance = DistanceCalculator.distance(clientLocation, nodeLocation, DistanceCalculator.KILOMETERS);

                /*
                 * Add to the list
                 */
                orderedByDistance.put(componentDistance, node);

            }

        }

        return new ArrayList<>(orderedByDistance.values());
    }
}
