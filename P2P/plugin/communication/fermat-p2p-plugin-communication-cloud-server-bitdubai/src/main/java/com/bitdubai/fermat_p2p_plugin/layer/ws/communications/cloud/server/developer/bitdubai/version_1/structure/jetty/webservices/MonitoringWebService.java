/*
 * @#MonitoringWebService.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.JsonAttNamesConstants;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ConfigurationManager;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MonitClient;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ShareMemoryCacheForVpnClientsConnections;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.VpnClientConnection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.MonitoringWebService</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/api/admin/monitoring")
public class MonitoringWebService {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(MonitoringWebService.class));

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Constructor
     */
    public MonitoringWebService() {
        super();
        this.gson = new Gson();
    }

    @GET
    public String isActive() {
        return "The Monitoring WebService is running ...";
    }


    @GET
    @Path("/current/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response monitoringData() {

        LOG.debug("Executing monitoringData()");

        JsonObject globalData = new JsonObject();
        globalData.addProperty("pendingClientConnection", MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().size());
        globalData.addProperty("registeredClientConnection",  MemoryCache.getInstance().getRegisteredClientConnectionsCache().size());

        Map<NetworkServiceType, Integer> networkServiceData = new HashMap<>();
        int totalNs = 0;
        for (NetworkServiceType networkServiceType : MemoryCache.getInstance().getRegisteredNetworkServicesCache().keySet()) {
            networkServiceData.put(networkServiceType, MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).size());
            totalNs = totalNs + MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).size();
        }

        globalData.addProperty("registeredNetworkServiceTotal", totalNs);
        globalData.addProperty("registeredNetworkServiceDetail", gson.toJson(networkServiceData, Map.class));

        Map<PlatformComponentType, Integer> otherComponentData = new HashMap<>();
        int totalOc = 0;
        for (PlatformComponentType platformComponentType : MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().keySet()) {
            otherComponentData.put(platformComponentType, MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).size());
            totalOc = totalOc + MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).size();
        }

        globalData.addProperty("registerOtherComponentTotal", totalOc);
        globalData.addProperty("registerOtherComponentDetail", gson.toJson(otherComponentData, Map.class));

        Map<NetworkServiceType, Map<String, VpnClientConnection>> vpnMap = ShareMemoryCacheForVpnClientsConnections.getConnectionMapCopy();


        int totalVpn = 0;
        JsonObject vpnNetworkServiceData = new JsonObject();

        for (NetworkServiceType networkServiceType : vpnMap.keySet()) {
            totalVpn = totalVpn + vpnMap.get(networkServiceType).size();
            vpnNetworkServiceData.addProperty(networkServiceType.toString(), vpnMap.get(networkServiceType).size());
        }

        globalData.addProperty("vpnTotal", totalVpn);
        globalData.addProperty("vpnByNetworkServiceDetails", gson.toJson(vpnNetworkServiceData));

        return Response.status(200).entity(gson.toJson(globalData)).build();

    }


    @GET
    @Path("/system/data")
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response getClientList(){
        LOG.info("Starting geClientList");
        JsonObject jsonObjectRespond = new JsonObject();
        List<PlatformComponentProfile> resultList = new ArrayList<>();
        try {
            if (!MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().isEmpty()){
                resultList = (List<PlatformComponentProfile>) new ArrayList<>(MemoryCache.getInstance().getRegisteredCommunicationsCloudClientCache().values()).clone();
            }
             /*
             * Convert the list to json representation
             */
            String jsonListRepresentation = gson.toJson(resultList, new TypeToken<List<PlatformComponentProfileCommunication>>(){ }.getType());
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
    public Response getClientComponentsDetails(@QueryParam(JsonAttNamesConstants.NAME_IDENTITY) String clientIdentityPublicKey){
        LOG.info("Starting getClientComponentsDetails");
        JsonObject jsonObjectRespond = new JsonObject();
        List<PlatformComponentProfile> resultList = new ArrayList<>();
        try {
            if (!MemoryCache.getInstance().getRegisteredNetworkServicesCache().isEmpty()){
                Iterator<NetworkServiceType> iteratorNetworkServiceType = MemoryCache.getInstance().getRegisteredNetworkServicesCache().keySet().iterator();
                while (iteratorNetworkServiceType.hasNext()){
                    NetworkServiceType networkServiceType = iteratorNetworkServiceType.next();
                    Iterator<PlatformComponentProfile> iterator = MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).iterator();
                    while (iterator.hasNext()){
                        PlatformComponentProfile platformComponentProfileRegistered = iterator.next();
                        LOG.info("clientIdentityPublicKey = "+clientIdentityPublicKey);
                        LOG.info("platformComponentProfileRegistered = "+platformComponentProfileRegistered.getCommunicationCloudClientIdentity());
                        if(platformComponentProfileRegistered.getCommunicationCloudClientIdentity().equals(clientIdentityPublicKey)){
                            resultList.add(platformComponentProfileRegistered);
                        }
                    }
                }
            }
            if (!MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().isEmpty()){
                Iterator<PlatformComponentType> iteratorNetworkServiceType = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().keySet().iterator();
                while (iteratorNetworkServiceType.hasNext()){
                    PlatformComponentType platformComponentType = iteratorNetworkServiceType.next();
                    Iterator<PlatformComponentProfile> iterator = MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).iterator();
                    while (iterator.hasNext()){
                        PlatformComponentProfile platformComponentProfileRegistered = iterator.next();
                        if(platformComponentProfileRegistered.getCommunicationCloudClientIdentity().equals(clientIdentityPublicKey)){
                            resultList.add(platformComponentProfileRegistered);
                        }
                    }
                }
            }
             /*
             * Convert the list to json representation
             */
            String jsonListRepresentation = gson.toJson(resultList, new TypeToken<List<PlatformComponentProfileCommunication>>(){ }.getType());
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
