package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.CheckInProfileDiscoveryQueryMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.respond.CheckInProfileListMsgRespond;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.DistanceCalculator;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.PackageType;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.endpoinsts.FermatWebSocketChannelEndpoint;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.PackageProcessor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;

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
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.channels.processors.clients.CheckInProfileDiscoveryQueryRequestProcessor</code>
 * process all packages received the type <code>MessageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_REQUEST</code><p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckInProfileDiscoveryQueryRequestProcessor extends PackageProcessor {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(CheckInProfileDiscoveryQueryRequestProcessor.class));

    /**
     * Constructor whit parameter
     *
     * @param fermatWebSocketChannelEndpoint register
     */
    public CheckInProfileDiscoveryQueryRequestProcessor(FermatWebSocketChannelEndpoint fermatWebSocketChannelEndpoint) {
        super(fermatWebSocketChannelEndpoint, PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_REQUEST);
    }

    /**
     * (non-javadoc)
     * @see PackageProcessor#processingPackage(Session, com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.Package)
     */
    @Override
    public void processingPackage(Session session, Package packageReceived) {

        LOG.info("Processing new package received");

        String channelIdentityPrivateKey = getChannel().getChannelIdentity().getPrivateKey();
        String destinationIdentityPublicKey = (String) session.getUserProperties().get(HeadersAttName.CPKI_ATT_HEADER_NAME);
        List<Profile> profileList = null;
        DiscoveryQueryParameters discoveryQueryParameters = null;

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
                discoveryQueryParameters = messageContent.getDiscoveryQueryParameters();
                LOG.info(getGson().toJson(discoveryQueryParameters));

                /*
                 * Validate if a network service search
                 */
                if (discoveryQueryParameters.getNetworkServiceType() != null && discoveryQueryParameters.getNetworkServiceType() !=  NetworkServiceType.UNDEFINED){

                    /*
                     * Find in the data base
                     */
                    profileList = filterNetworkServices(discoveryQueryParameters);

                }else{

                    /*
                     * Find in the data base
                     */
                    profileList = filterActors(discoveryQueryParameters);
                }

                if(profileList != null && profileList.size() == 0)
                    throw new Exception("Not Found row in the Table");

                /*
                 * Apply geolocation
                 */
                if(discoveryQueryParameters.getLocation() != null)
                    profileList = applyGeoLocationFilter(discoveryQueryParameters.getLocation(), profileList, discoveryQueryParameters.getDistance());

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
                CheckInProfileListMsgRespond checkInProfileListMsgRespond = new CheckInProfileListMsgRespond(CheckInProfileListMsgRespond.STATUS.SUCCESS, CheckInProfileListMsgRespond.STATUS.SUCCESS.toString(), profileList, discoveryQueryParameters);
                Package packageRespond = Package.createInstance(checkInProfileListMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

                /*
                 * Send the respond
                 */
                session.getAsyncRemote().sendObject(packageRespond);

            }

        }catch (Exception exception){
            exception.printStackTrace();
            try {

                //LOG.error(exception.getMessage());

                /*
                 * Respond whit fail message
                 */
                CheckInProfileListMsgRespond checkInProfileListMsgRespond = new CheckInProfileListMsgRespond(CheckInProfileListMsgRespond.STATUS.FAIL, exception.getLocalizedMessage(), profileList, discoveryQueryParameters);
                Package packageRespond = Package.createInstance(checkInProfileListMsgRespond.toJson(), packageReceived.getNetworkServiceTypeSource(), PackageType.CHECK_IN_PROFILE_DISCOVERY_QUERY_RESPONSE, channelIdentityPrivateKey, destinationIdentityPublicKey);

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
     * Filter all network service from data base that mach
     * with the parameters
     *
     * @param discoveryQueryParameters
     * @return List<Profile>
     */
    private List<Profile> filterNetworkServices(DiscoveryQueryParameters discoveryQueryParameters) throws CantReadRecordDataBaseException, InvalidParameterException {

        List<Profile> profileList = new ArrayList<>();

        Map<String, Object> filters = constructFiltersNetworkServiceTable(discoveryQueryParameters);
        List<CheckedInNetworkService> networkServices = getDaoFactory().getCheckedInNetworkServiceDao().findAll(filters);

        for (CheckedInNetworkService checkedInNetworkService : networkServices) {

            NetworkServiceProfile networkServiceProfile = new NetworkServiceProfile();
            networkServiceProfile.setIdentityPublicKey(checkedInNetworkService.getIdentityPublicKey());
            networkServiceProfile.setClientIdentityPublicKey(checkedInNetworkService.getClientIdentityPublicKey());
            networkServiceProfile.setNetworkServiceType(NetworkServiceType.getByCode(checkedInNetworkService.getNetworkServiceType()));

            //TODO: SET THE LOCATION
            //networkServiceProfile.setLocation();

            profileList.add(networkServiceProfile);

        }

        return profileList;
    }

    /**
     * Filter all network service from data base that mach
     * with the parameters
     *
     * @param discoveryQueryParameters
     * @return List<Profile>
     */
    private List<Profile> filterActors(DiscoveryQueryParameters discoveryQueryParameters) throws CantReadRecordDataBaseException, InvalidParameterException {

        List<Profile> profileList = new ArrayList<>();

        Map<String, Object> filters = constructFiltersActorTable(discoveryQueryParameters);
        List<CheckedInActor> actores = getDaoFactory().getCheckedInActorDao().findAll(filters);

        if(actores != null) {
            for (CheckedInActor checkedInActor : actores) {

                ActorProfile actorProfile = new ActorProfile();
                actorProfile.setIdentityPublicKey(checkedInActor.getIdentityPublicKey());
                actorProfile.setAlias(checkedInActor.getAlias());
                actorProfile.setName(checkedInActor.getName());
                actorProfile.setActorType(checkedInActor.getActorType());
                actorProfile.setPhoto(checkedInActor.getPhoto());
                actorProfile.setExtraData(checkedInActor.getExtraData());
                actorProfile.setNsIdentityPublicKey(checkedInActor.getNsIdentityPublicKey());
                actorProfile.setClientIdentityPublicKey(checkedInActor.getClientIdentityPublicKey());

                //TODO: SET THE LOCATION
                //actorProfile.setLocation();

                profileList.add(actorProfile);

            }
        }

        return profileList;
    }


    /**
     * Construct data base filter from discovery query parameters
     *
     * @param discoveryQueryParameters
     * @return Map<String, Object> filters
     */
    private Map<String, Object> constructFiltersNetworkServiceTable(DiscoveryQueryParameters discoveryQueryParameters){

        Map<String, Object> filters = new HashMap<>();

        if (discoveryQueryParameters.getIdentityPublicKey() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_IDENTITY_PUBLIC_KEY_COLUMN_NAME, discoveryQueryParameters.getIdentityPublicKey());
        }

        if (discoveryQueryParameters.getNetworkServiceType() != null){
            filters.put(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_NETWORK_SERVICE_TYPE_COLUMN_NAME, discoveryQueryParameters.getNetworkServiceType().toString());
        }

        return filters;
    }


    /**
     * Method that apply geo location filter to the list
     *
     * @param filterLocation
     * @param profileList
     * @param distance
     * @return List<Profile>
     */
    private List<Profile> applyGeoLocationFilter(Location filterLocation, List<Profile> profileList, Double distance) {

        /*
         * Hold the data ordered by distance
         */
        Map<Double, Profile> orderedByDistance = new TreeMap<>();

        /*
         * For each node
         */
        for (final Profile profile: profileList) {

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
