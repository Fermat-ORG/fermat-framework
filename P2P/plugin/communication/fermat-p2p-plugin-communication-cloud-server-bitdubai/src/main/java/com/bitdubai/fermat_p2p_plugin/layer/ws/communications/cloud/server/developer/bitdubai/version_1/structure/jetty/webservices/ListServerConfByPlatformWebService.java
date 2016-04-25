/*
* @#ListServerConfByPlatformWebService.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.MemoryCache;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.ListServerConfByPlatformWebService</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 11/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/api/serverplatform")
public class ListServerConfByPlatformWebService {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ListServerConfByPlatformWebService.class));

    /**
     * Represent the gson
     */
    private Gson gson;

    private Map<NetworkServiceType,String> listServerConfByPlatform;



    /**
     * Constructor
     */
    public ListServerConfByPlatformWebService(){
        super();
        this.gson = new Gson();
        this.listServerConfByPlatform = new HashMap<>();
        //putInAlllist();
    }

    @GET
    @Path("/listserverconfbyplatform")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listserverconfbyplatform() {

        JsonObject respond = new JsonObject();

        if(listServerConfByPlatform != null){
            respond.addProperty("success",Boolean.TRUE);
            respond.addProperty("data", gson.toJson(listServerConfByPlatform));
        }else{
            respond.addProperty("success",Boolean.FALSE);
        }

       return Response.status(200).entity(gson.toJson(respond)).type(MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/saveserverconfbyplatform")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveserverconfbyplatform(@FormParam("networkservicetype") String networkservicetypeReceive, @FormParam("ipserver") String ipServer) {

        JsonObject respond = new JsonObject();

        try {
            if(networkservicetypeReceive != null && ipServer != null) {
                NetworkServiceType networkServiceType = NetworkServiceType.valueOf(networkservicetypeReceive);
                listServerConfByPlatform.put(networkServiceType, ipServer);
                MemoryCache.getInstance().sendMessageToAll(networkServiceType, ipServer);
                respond.addProperty("success", Boolean.TRUE);
                respond.addProperty("data", "Successfully was update the Platform Cloud Server!");
            }else{
                throw new Exception("networkservicetype && ipserver not must be empty");
            }
        } catch (Exception e) {
            respond.addProperty("success", Boolean.FALSE);
            respond.addProperty("data", gson.toJson(e));
        }


        return Response.status(200).entity(gson.toJson(respond)).build();

    }


    private void putInAlllist(){

        /* ARTIST */
        listServerConfByPlatform.put(NetworkServiceType.ARTIST_ACTOR,"192.168.1.4");
        /* ARTIST */

        /* CBP */
        listServerConfByPlatform.put(NetworkServiceType.CRYPTO_BROKER,"192.168.1.15");
        /* CBP */

        /* CCP */
        listServerConfByPlatform.put(NetworkServiceType.INTRA_USER,"192.168.1.6");
        /* CCP */

        /* CHT */
        listServerConfByPlatform.put(NetworkServiceType.CHAT,"192.168.1.7");
        /* CHT */

        /* DAP */
        listServerConfByPlatform.put(NetworkServiceType.ASSET_USER_ACTOR,"192.168.1.8");
        /* DAP */

        /* MONITOR */
        listServerConfByPlatform.put(NetworkServiceType.FERMAT_MONITOR,"192.168.1.9");
        /* MONITOR */
    }



}
