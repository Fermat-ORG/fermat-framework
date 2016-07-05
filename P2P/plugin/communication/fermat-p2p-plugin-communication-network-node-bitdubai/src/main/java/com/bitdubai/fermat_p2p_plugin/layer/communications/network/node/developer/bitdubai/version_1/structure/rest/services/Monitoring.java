/*
 * @#MonitoringWebService.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ClientProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInClient;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.MonitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.GZIP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.Monitoring</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/admin/monitoring")
public class Monitoring {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(Monitoring.class));

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the daoFactory
     */
    private DaoFactory daoFactory;

    /**
     * Constructor
     */
    public Monitoring() {
        super();
        this.daoFactory  = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        this.gson = GsonProvider.getGson();
    }

    @GET
    @GZIP
    public String isActive() {
        return "The Monitoring WebService is running ...";
    }


    @GET
    @Path("/current/data")
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response monitoringData() {

        LOG.debug("Executing monitoringData()");

        JsonObject globalData = new JsonObject();

        try {

            globalData.addProperty("registeredClientConnection", daoFactory.getCheckedInClientDao().getAllCount() );

            Map<NetworkServiceType, Long> networkServiceData = new HashMap<>();
            for (NetworkServiceType networkServiceType : NetworkServiceType.values()) {

                if (networkServiceType != NetworkServiceType.UNDEFINED){
                    networkServiceData.put(networkServiceType, daoFactory.getCheckedInNetworkServiceDao().getAllCount(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_NETWORK_SERVICE_TYPE_COLUMN_NAME, networkServiceType.getCode()));
                }

            }
            globalData.addProperty("registeredNetworkServiceTotal", daoFactory.getCheckedInNetworkServiceDao().getAllCount());
            globalData.addProperty("registeredNetworkServiceDetail", gson.toJson(networkServiceData, Map.class));

            Map<Actors, Long> otherComponentData = new HashMap<>();
            for (Actors actorsType : Actors.values()) {
                otherComponentData.put(actorsType, daoFactory.getCheckedInActorDao().getAllCount(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME, actorsType.getCode()));
            }

            globalData.addProperty("registerActorsTotal", daoFactory.getCheckedInActorDao().getAllCount());
            globalData.addProperty("registerActorsDetail", gson.toJson(otherComponentData, Map.class));
            globalData.addProperty("success", Boolean.TRUE);

        }catch (Exception e){
            LOG.error(e);
            globalData = new JsonObject();
            globalData.addProperty("success", Boolean.FALSE);
            globalData.addProperty("description",e.getMessage());
        }

        return Response.status(200).entity(gson.toJson(globalData)).build();

    }


    @GET
    @Path("/system/data")
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response systemData() {

        LOG.debug("Executing systemData()");
        JsonObject respond = new JsonObject();

        if (Boolean.valueOf(ConfigurationManager.getValue(ConfigurationManager.MONIT_INSTALLED))){

            MonitClient monitClient = new MonitClient(ConfigurationManager.getValue(ConfigurationManager.MONIT_URL), ConfigurationManager.getValue(ConfigurationManager.MONIT_USER), ConfigurationManager.getValue(ConfigurationManager.MONIT_PASSWORD));
            Map<String, JsonArray> data = null;
            try {

                data = monitClient.getComponents();

                LOG.debug("data = "+data);

                respond.addProperty("success", Boolean.TRUE);
                respond.addProperty("data", gson.toJson(data));

            } catch (IOException e) {
                respond.addProperty("success", Boolean.FALSE);
                respond.addProperty("data", "Error: "+e.getMessage());
            }

        }else {

            respond.addProperty("success", Boolean.FALSE);
            respond.addProperty("data", "Error: Monit is no installed and configured.");
        }

        return Response.status(200).entity(gson.toJson(respond)).build();

    }

    @GET
    @Path("/clients/list")
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response getClientList(){
        LOG.info("Starting geClientList");
        JsonObject jsonObjectRespond = new JsonObject();
        List<ClientProfile> resultList = new ArrayList<>();
        try {

            if (daoFactory.getCheckedInClientDao().getAllCount() > 0){

                for (CheckedInClient checkedInClient: daoFactory.getCheckedInClientDao().findAll()) {
                    ClientProfile clientProfile = new ClientProfile();
                    clientProfile.setIdentityPublicKey(checkedInClient.getIdentityPublicKey());

                    /*
                     * Get the locationSource
                     */
                    Location locationSource = new NetworkNodeCommunicationDeviceLocation(
                            checkedInClient.getLatitude() ,
                            checkedInClient.getLongitude(),
                            null     ,
                            0        ,
                            null     ,
                            System.currentTimeMillis(),
                            LocationSource.UNKNOWN
                    );


                    clientProfile.setLocation(locationSource);
                    clientProfile.setDeviceType(checkedInClient.getDeviceType());

                    resultList.add(clientProfile);
                }

            }
             /*
             * Convert the list to json representation
             */
            String jsonListRepresentation = gson.toJson(resultList, new TypeToken<List<ClientProfile>>(){ }.getType());
            /*
             * Create the respond
             */
            jsonObjectRespond.addProperty(JsonAttNamesConstants.RESULT_LIST, jsonListRepresentation);
        }catch (Exception e){
            LOG.warn("requested list is not available");
            jsonObjectRespond.addProperty(JsonAttNamesConstants.FAILURE, "Requested list is not available");
            e.printStackTrace();
        }
        String jsonString = gson.toJson(jsonObjectRespond);
        return Response.status(200).entity(jsonString).build();
    }


    @GET
    @Path("/client/components/details")
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response getClientComponentsDetails(@QueryParam(JsonAttNamesConstants.NAME_IDENTITY) String clientIdentityPublicKey){

        LOG.info("Starting getClientComponentsDetails");
        LOG.info("clientIdentityPublicKey = "+clientIdentityPublicKey);

        JsonObject jsonObjectRespond = new JsonObject();

        try {

            List<NetworkServiceProfile> nsList = new ArrayList<>();
            for (CheckedInNetworkService checkInNetworkService: daoFactory.getCheckedInNetworkServiceDao().findAll(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, clientIdentityPublicKey)){

                try {

                    NetworkServiceProfile networkServiceProfile = new NetworkServiceProfile();
                    networkServiceProfile.setIdentityPublicKey(checkInNetworkService.getIdentityPublicKey());
                    networkServiceProfile.setNetworkServiceType(NetworkServiceType.getByCode(checkInNetworkService.getNetworkServiceType()));

                    /*
                     * Get the locationSource
                     */
                    Location locationSource = new NetworkNodeCommunicationDeviceLocation(
                            checkInNetworkService.getLatitude() ,
                            checkInNetworkService.getLongitude(),
                            null     ,
                            0        ,
                            null     ,
                            System.currentTimeMillis(),
                            LocationSource.UNKNOWN
                    );

                    networkServiceProfile.setLocation(locationSource);
                    nsList.add(networkServiceProfile);

                }catch (Exception e){
                    LOG.error("Cant parse de checked in network service = "+e.getMessage());
                }

            }

            List<ActorProfile> actorList = new ArrayList<>();
            for (CheckedInActor checkedInActor: daoFactory.getCheckedInActorDao().findAll(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, clientIdentityPublicKey)){

                try {

                    ActorProfile actorProfile = new ActorProfile();
                    actorProfile.setIdentityPublicKey(checkedInActor.getIdentityPublicKey());
                    actorProfile.setAlias(checkedInActor.getAlias());
                    actorProfile.setName(checkedInActor.getName());
                    actorProfile.setActorType(checkedInActor.getActorType());
                    actorProfile.setPhoto(checkedInActor.getPhoto());
                    actorProfile.setExtraData(checkedInActor.getExtraData());
                    actorProfile.setNsIdentityPublicKey(checkedInActor.getNsIdentityPublicKey());

                    /*
                     * Get the locationSource
                     */
                    Location locationSource = new NetworkNodeCommunicationDeviceLocation(
                            checkedInActor.getLatitude() ,
                            checkedInActor.getLongitude(),
                            null     ,
                            0        ,
                            null     ,
                            System.currentTimeMillis(),
                            LocationSource.UNKNOWN
                    );

                    actorProfile.setLocation(locationSource);
                    actorList.add(actorProfile);

                }catch (Exception e){
                    LOG.error("Cant parse de checked in actor = "+e.getMessage());
                }
            }


            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("ns",     gson.toJson(nsList, new TypeToken<List<NetworkServiceProfile>>(){ }.getType()));
            resultMap.put("actors", gson.toJson(actorList, new TypeToken<List<ActorProfile>>(){ }.getType()));

            /*
             * Convert the list to json representation
             */
            String jsonListRepresentation = gson.toJson(resultMap, Map.class);

            /*
             * Create the respond
             */
            jsonObjectRespond.addProperty(JsonAttNamesConstants.RESULT_LIST, jsonListRepresentation);
        }catch (Exception e){
            LOG.warn("requested list is not available");
            jsonObjectRespond.addProperty(JsonAttNamesConstants.FAILURE, "Requested list is not available");
            e.printStackTrace();
        }
        String jsonString = gson.toJson(jsonObjectRespond);
        return Response.status(200).entity(jsonString).build();
    }

}
