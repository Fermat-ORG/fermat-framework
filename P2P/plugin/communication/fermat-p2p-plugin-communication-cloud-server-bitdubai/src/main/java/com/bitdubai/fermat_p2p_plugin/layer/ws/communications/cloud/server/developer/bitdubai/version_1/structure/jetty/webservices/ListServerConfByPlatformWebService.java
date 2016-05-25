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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    /**
     * Constructor
     */
    public ListServerConfByPlatformWebService(){
        super();
        this.gson = new Gson();
    }

    @GET
    @Path("/listserverconfbyplatform")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listserverconfbyplatform() {

        JsonObject respond = new JsonObject();

        if(MemoryCache.getInstance().getListServerConfByPlatform() != null){
            respond.addProperty("success",Boolean.TRUE);
            respond.addProperty("data", gson.toJson(MemoryCache.getInstance().getListServerConfByPlatform()));
        }else{
            respond.addProperty("success",Boolean.FALSE);
        }

       return Response.status(200).entity(gson.toJson(respond)).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/listplatforms")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listplatforms() {

        JsonObject respond = new JsonObject();

        if(MemoryCache.getInstance().getListServerConfByPlatform() != null){

            List<NetworkServiceType> listServerActive = new ArrayList<>();

            for(NetworkServiceType networkServiceType : MemoryCache.getInstance().getListServerConfByPlatform().keySet()){

                String platform = null;
                switch (networkServiceType){
                    case ARTIST_ACTOR:
                        platform = "ARTIST";
                        break;
                    case CRYPTO_BROKER:
                        platform = "CBP";
                        break;
                    case INTRA_USER:
                        platform = "CCP";
                        break;
                    case CHAT:
                        platform = "CHAT";
                        break;
                    case ASSET_USER_ACTOR:
                        platform = "DAP";
                        break;
                    case FERMAT_MONITOR:
                        platform = "FERMAT-MONITOR";
                        break;
                }

                listServerActive.add(networkServiceType);

            }


            respond.addProperty("success", Boolean.TRUE);
            respond.addProperty("data", gson.toJson(listServerActive));

        }else {

            respond.addProperty("success", Boolean.FALSE);

        }

        return Response.status(200).entity(gson.toJson(respond)).type(MediaType.APPLICATION_JSON).build();

    }


    @POST
    @Path("/deleteserver")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteserver(@FormParam("idserver") String idserver) {

        JsonObject respond = new JsonObject();

        try {

            if(idserver != null){

                NetworkServiceType networkServiceType = NetworkServiceType.valueOf(idserver);

                if(MemoryCache.getInstance().getListServerConfByPlatform().containsKey(networkServiceType)) {

                    MemoryCache.getInstance().getListServerConfByPlatform().remove(networkServiceType);
                    //System.out.println("DELETE NetworkServiceType " + networkServiceType);

                    respond.addProperty("success", Boolean.TRUE);
                    respond.addProperty("data", "Successfully was delete the Platform Cloud Server!");
                }

            }else{
                throw new Exception("networkservicetype not must be empty");
            }
        } catch (Exception e) {
            respond.addProperty("success", Boolean.FALSE);
            respond.addProperty("data", gson.toJson(e));
        }

        return Response.status(200).entity(gson.toJson(respond)).build();

    }


    @POST
    @Path("/saveserverconfbyplatform")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveserverconfbyplatform(@FormParam("networkservicetype") String networkservicetypeReceive, @FormParam("ipserver") String ipServer) {

        JsonObject respond = new JsonObject();

        try {
            if(networkservicetypeReceive != null && ipServer != null) {
                NetworkServiceType networkServiceType = NetworkServiceType.valueOf(networkservicetypeReceive);
                MemoryCache.getInstance().getListServerConfByPlatform().put(networkServiceType, ipServer);
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


}
