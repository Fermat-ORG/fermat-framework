/*
 * @#MonitoringWebService.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ShareMemoryCacheForVpnClientsConnections;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.VpnClientConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
@Path("/api/monitoring")
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

        LOG.info("Executing monitoringData()");

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

        globalData.addProperty("vpnTotal", vpnMap.size());

        JsonObject vpnNetworkServiceData = new JsonObject();

        for (NetworkServiceType networkServiceType : vpnMap.keySet()) {
            vpnNetworkServiceData.addProperty(networkServiceType.toString(), vpnMap.get(networkServiceType).size());
        }

        globalData.addProperty("vpnByNetworkServiceDetails", gson.toJson(vpnNetworkServiceData));

        //return Response.status(200).entity("angular.callbacks._0 (" + gson.toJson(globalData)+")").build();
        return Response.status(200).entity(gson.toJson(globalData)).build();

    }

}
