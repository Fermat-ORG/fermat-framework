/*
 * @#ExampleResourceImpl.java - 2016
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

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.ExampleResourceImpl</code>
 * </p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 10/01/16.
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

        JsonObject globalData = new JsonObject();
        globalData.addProperty("PENDING_CLIENT_CONNECTION", MemoryCache.getInstance().getPendingRegisterClientConnectionsCache().size());
        globalData.addProperty("REGISTERED_CLIENT_CONNECTION",  MemoryCache.getInstance().getRegisteredClientConnectionsCache().size());

        JsonObject networkServiceData = new JsonObject();

        for (NetworkServiceType networkServiceType : MemoryCache.getInstance().getRegisteredNetworkServicesCache().keySet()) {
            networkServiceData.addProperty(networkServiceType.toString(), MemoryCache.getInstance().getRegisteredNetworkServicesCache().get(networkServiceType).size());
        }

        globalData.addProperty("REGISTERED_NETWORK_SERVICE", gson.toJson(networkServiceData));

        JsonObject otherComponentData = new JsonObject();

        for (PlatformComponentType platformComponentType : MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().keySet()) {
            otherComponentData.addProperty(platformComponentType.toString(), MemoryCache.getInstance().getRegisteredOtherPlatformComponentProfileCache().get(platformComponentType).size());
        }

        globalData.addProperty("OCR", gson.toJson(otherComponentData));

        return Response.status(200).entity(gson.toJson(globalData)).build();
    }

    @GET
    @Path("/current/vpn/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response monitoringVpnData() {

        Map<NetworkServiceType, Map<String, VpnClientConnection>> vpnMap = ShareMemoryCacheForVpnClientsConnections.getConnectionMapCopy();

        JsonObject globalData = new JsonObject();
        globalData.addProperty("VPN_COUNT", vpnMap.size());

        JsonObject networkServiceData = new JsonObject();

        for (NetworkServiceType networkServiceType : vpnMap.keySet()) {
            networkServiceData.addProperty(networkServiceType.toString(), vpnMap.get(networkServiceType).size());
        }

        globalData.addProperty("VPN_BY_NETWORK_SERVICE", gson.toJson(networkServiceData));

        return Response.status(200).entity(gson.toJson(globalData)).build();
    }

}
