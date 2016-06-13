package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileDiscoveryQueryMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ActorsProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.ResultDiscoveryTraceActor;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.DistanceCalculator;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.ActorTraceDiscoveryQueryRequestProcessor</code>
 * process all packages received the type <code>MessageType.ACTOR_TRACE_DISCOVERY_QUERY_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorTraceDiscoveryQueryRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ActorTraceDiscoveryQueryRequestProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public ActorTraceDiscoveryQueryRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.ACTOR_TRACE_DISCOVERY_QUERY_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        List<ResultDiscoveryTraceActor> profileList = null;
        NetworkServiceType networkServiceTypeIntermediate = null;

        try {

            CheckInProfileDiscoveryQueryMsgRequest messageContent = CheckInProfileDiscoveryQueryMsgRequest.parseContent(packageReceived.getContent());

            /*
             * Create the method call history
             */
            methodCallsHistory(getGson().toJson(messageContent.getDiscoveryQueryParameters()), destinationIdentityPublicKey);

            /*
             * Validate if content type is the correct
             */
            if (messageContent.getMessageContentType() == MessageContentType.JSON) {

                /*
                 * Get the parameters to filters
                 */
                DiscoveryQueryParameters discoveryQueryParameters = messageContent.getDiscoveryQueryParameters();

                /*
                 * get the NetworkServiceIntermediate
                 */
                networkServiceTypeIntermediate = discoveryQueryParameters.getNetworkServiceTypeIntermediate();

                /*
                 * Validate if a network service search
                 */
                if (discoveryQueryParameters.getNetworkServiceType() == null){

                    /*
                     * Find in the data base
                     */
                    profileList = filterActors(discoveryQueryParameters);

                    if(profileList != null && profileList.size() == 0)
                        throw new Exception("Not Found row in the Table");

                }

                /*
                 * Apply geolocation
                 */
                 //profileList = applyGeoLocationFilter(discoveryQueryParameters.getLocation(), profileList, discoveryQueryParameters.getDistance());

                /*
                 * Apply pagination
                 */
                if ((discoveryQueryParameters.getMax() != 0) && (discoveryQueryParameters.getOffset() != 0)){

                    /*
                     * Apply pagination
                     */
                    if (profileList.size() > discoveryQueryParameters.getMax() &&
                            profileList.size() > discoveryQueryParameters.getOffset()){
                        profileList =  profileList.subList(discoveryQueryParameters.getOffset(), discoveryQueryParameters.getMax());
                    }else if (profileList.size() > 100) {
                        profileList = profileList.subList(discoveryQueryParameters.getOffset(), 100);
                    }

                }else if (profileList.size() > 100) {
                    profileList = profileList.subList(0, 100);
                }

                /*
                 * If all ok, respond whit success message
                 */
                ActorsProfileListMsgRespond actorsProfileListMsgRespond = new ActorsProfileListMsgRespond(ActorsProfileListMsgRespond.STATUS.SUCCESS, ActorsProfileListMsgRespond.STATUS.SUCCESS.toString(), profileList, networkServiceTypeIntermediate);
                Package packageRespond = Package.createInstance(actorsProfileListMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ACTOR_TRACE_DISCOVERY_QUERY_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
                ActorsProfileListMsgRespond actorsProfileListMsgRespond = new ActorsProfileListMsgRespond(ActorsProfileListMsgRespond.STATUS.FAIL, exception.getLocalizedMessage(), profileList, networkServiceTypeIntermediate);
                Package packageRespond = Package.createInstance(actorsProfileListMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.ACTOR_TRACE_DISCOVERY_QUERY_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * @param discoveryQueryParameters
     * @return List<ActorProfile>
     */
    private List<ResultDiscoveryTraceActor> filterActors(DiscoveryQueryParameters discoveryQueryParameters) throws CantReadRecordDataBaseException, InvalidParameterException {

        List<ResultDiscoveryTraceActor> profileList = new ArrayList<>();

        Map<String, Object> filters = constructFiltersActorTable(discoveryQueryParameters);
        List<ActorsCatalog> actors = getDaoFactory().getActorsCatalogDao().findAll(filters);

        for (ActorsCatalog actorsCatalog : actors) {

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

                ResultDiscoveryTraceActor resultDiscoveryTraceActor = new ResultDiscoveryTraceActor(nodeProfile, actorProfile);
                profileList.add(resultDiscoveryTraceActor);

            }

        }

        return profileList;
    }

    /**
     * Method that apply geo location filter to the list
     *
     * @param filterLocation
     * @param profileList
     * @param distance
     * @return List<ActorProfile>
     */
    private List<ActorProfile> applyGeoLocationFilter(Location filterLocation, List<ActorProfile> profileList, Double distance) {

        /*
         * Hold the data ordered by distance
         */
        Map<Double, ActorProfile> orderedByDistance = new TreeMap<>();

        /*
         * For each node
         */
        for (final ActorProfile profile: profileList) {

            /*
             * If component have a geo location
             */
            if (profile.getLocation() != null){

                /*
                 * Calculate the distance between the two points
                 */
                Double componentDistance = DistanceCalculator.distance(filterLocation, profile.getLocation(), DistanceCalculator.KILOMETERS);

                if (distance != null){

                    if (componentDistance <= distance){

                        /*
                         * Add to the list
                         */
                        orderedByDistance.put(componentDistance, profile);
                    }

                }else {

                    /*
                     * Add to the list
                     */
                    orderedByDistance.put(componentDistance, profile);

                }

            }

        }

        return new ArrayList<>(orderedByDistance.values());
    }



    /**
     * Construct data base filter from discovery query parameters
     *
     * @param discoveryQueryParameters
     * @return Map<String, Object> filters
     */
    private Map<String, Object> constructFiltersActorTable(DiscoveryQueryParameters discoveryQueryParameters){

        Map<String, Object> filters = new HashMap<>();

        if (discoveryQueryParameters.getIdentityPublicKey() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME, discoveryQueryParameters.getIdentityPublicKey());
        }

        if (discoveryQueryParameters.getName() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NAME_COLUMN_NAME, discoveryQueryParameters.getName());
        }

        if (discoveryQueryParameters.getAlias() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME, discoveryQueryParameters.getAlias());
        }

        if (discoveryQueryParameters.getActorType() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME, discoveryQueryParameters.getActorType());
        }

        if (discoveryQueryParameters.getExtraData() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME, discoveryQueryParameters.getExtraData());
        }

        return filters;
    }

}
