/*
* @#NetworkData.java - 2016
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInClient;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInNetworkService;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.NetworkData</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 16/06/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/network")
public class NetworkData {


    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(NetworkData.class));

    /**
     * Represent the daoFactory
     */
    private DaoFactory daoFactory;

    /*
     * Represent the pluginRoot
     */
    private NetworkNodePluginRoot pluginRoot;

    /**
     * Constructor
     */
    public NetworkData(){
        daoFactory = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        pluginRoot = (NetworkNodePluginRoot) NodeContext.get(NodeContextItem.PLUGIN_ROOT);
        gson = new Gson();
    }


    @GET
    @Path("/catalog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNodes(){


        try {
            /*
             * Get the node catalog list
             */
            List<NodesCatalog> nodesCatalogs = daoFactory.getNodesCatalogDao().findAll();
            List<String> nodes = new ArrayList<>();

            if(nodesCatalogs != null){
                for(NodesCatalog node : nodesCatalogs){
                    nodes.add(node.getIp());
                }
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nodes", gson.toJson(nodes));

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        } catch (Exception e) {
            //e.printStackTrace();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code",e.hashCode());
            jsonObject.addProperty("message",e.getMessage());
            jsonObject.addProperty("details",gson.toJson(e.getCause()));


            JsonObject jsonObjectError = new JsonObject();
            jsonObjectError.addProperty("error",gson.toJson(jsonObject));

            return Response.status(200).entity(gson.toJson(jsonObjectError)).build();

        }


    }

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getServerData(){

        try{

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("hash",pluginRoot.getIdentity().getPublicKey());

            if(pluginRoot.getLocationManager() != null && pluginRoot.getLocationManager().getLocation() != null){
                jsonObject.addProperty("location", gson.toJson(pluginRoot.getLocationManager().getLocation()));
            }else{

                Location location = new NetworkNodeCommunicationDeviceLocation(
                        0.0 ,
                        0.0,
                        0.0     ,
                        0        ,
                        0.0     ,
                        System.currentTimeMillis(),
                        LocationSource.UNKNOWN
                );

                jsonObject.addProperty("location", gson.toJson(location));
            }

            jsonObject.addProperty("os","");
            jsonObject.addProperty("networkServices",gson.toJson(getNetworkServicesCount()));

            return Response.status(200).entity(gson.toJson(jsonObject)).build();

        }catch (Exception e){

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code",e.hashCode());
            jsonObject.addProperty("message",e.getMessage());
            jsonObject.addProperty("details",gson.toJson(e.getCause()));


            JsonObject jsonObjectError = new JsonObject();
            jsonObjectError.addProperty("error",gson.toJson(jsonObject));

            return Response.status(200).entity(gson.toJson(jsonObjectError)).build();

        }

    }

    @GET
    @Path("/clients")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClients(){

        try{

            List<String> listOfClients = new ArrayList<>();

            List<CheckedInClient>  listCheckedInClientS = daoFactory.getCheckedInClientDao().findAll();

            if(listCheckedInClientS != null){

                for(CheckedInClient CheckedInClient : listCheckedInClientS){

                    Location location = new NetworkNodeCommunicationDeviceLocation(
                                CheckedInClient.getLatitude() ,
                                CheckedInClient.getLongitude(),
                                0.0     ,
                                0        ,
                                0.0     ,
                                System.currentTimeMillis(),
                                LocationSource.UNKNOWN
                    );



                    JsonObject jsonObjectClient = new JsonObject();
                    jsonObjectClient.addProperty("hash", CheckedInClient.getIdentityPublicKey());
                    jsonObjectClient.addProperty("location", gson.toJson(location));
                    jsonObjectClient.addProperty("networkServices",gson.toJson(getListOfNetworkServiceOfClientSpecific(CheckedInClient.getIdentityPublicKey())));

                    listOfClients.add(gson.toJson(jsonObjectClient));

                }
            }

            return Response.status(200).entity(gson.toJson(listOfClients)).build();

        }catch (Exception e){

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code",e.hashCode());
            jsonObject.addProperty("message",e.getMessage());
            jsonObject.addProperty("details",gson.toJson(e.getCause()));


            JsonObject jsonObjectError = new JsonObject();
            jsonObjectError.addProperty("error",gson.toJson(jsonObject));

            return Response.status(200).entity(gson.toJson(jsonObjectError)).build();
        }
    }

    @GET
    @Path("/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActors(){

        try{

            List<String> actors = new ArrayList<>();

            List<CheckedInActor> listOfCheckedInActor = daoFactory.getCheckedInActorDao().findAll();

            if(listOfCheckedInActor != null){

                for(CheckedInActor CheckedInActor :listOfCheckedInActor){

                    JsonObject jsonObjectActor = new JsonObject();
                    jsonObjectActor.addProperty("hash",CheckedInActor.getIdentityPublicKey());
                    jsonObjectActor.addProperty("type",CheckedInActor.getActorType());
                    jsonObjectActor.addProperty("links",gson.toJson(new ArrayList<>()));

                    Location location = new NetworkNodeCommunicationDeviceLocation(
                            CheckedInActor.getLatitude() ,
                            CheckedInActor.getLongitude(),
                            0.0     ,
                            0        ,
                            0.0     ,
                            System.currentTimeMillis(),
                            LocationSource.UNKNOWN
                    );

                    jsonObjectActor.addProperty("location", gson.toJson(location));

                    JsonObject jsonObjectActorProfile = new JsonObject();
                    jsonObjectActorProfile.addProperty("phrase", "There is not Phrase");
                    jsonObjectActorProfile.addProperty("picture", Base64.encodeBase64String(CheckedInActor.getPhoto()));
                    jsonObjectActorProfile.addProperty("name", CheckedInActor.getName());

                    jsonObjectActor.addProperty("profile", gson.toJson(jsonObjectActorProfile));


                    actors.add(gson.toJson(jsonObjectActor));
                }

            }

            return Response.status(200).entity(gson.toJson(actors)).build();

        }catch (Exception e){

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code",e.hashCode());
            jsonObject.addProperty("message",e.getMessage());
            jsonObject.addProperty("details",gson.toJson(e.getCause()));


            JsonObject jsonObjectError = new JsonObject();
            jsonObjectError.addProperty("error",gson.toJson(jsonObject));

            return Response.status(200).entity(gson.toJson(jsonObjectError)).build();
        }

    }


    private Map<NetworkServiceType,Long> getNetworkServicesCount(){

        Map<NetworkServiceType,Long> listNetworkServicesCount = new HashMap<>();

        List<NetworkServiceType> listOfNetworkService = getListOfNetworkService();

        if(listOfNetworkService != null){

            for(NetworkServiceType networkServiceType : listOfNetworkService){

                try {
                    Long count = daoFactory.getCheckedInNetworkServiceDao().getAllCount(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_NETWORK_SERVICE_TYPE_COLUMN_NAME,networkServiceType.getCode());
                    listNetworkServicesCount.put(networkServiceType,count);
                } catch (CantReadRecordDataBaseException e) {
                    e.printStackTrace();
                }

            }

        }

        return listNetworkServicesCount;
    }

    private List<NetworkServiceType> getListOfNetworkService(){

        Map<NetworkServiceType, NetworkServiceType> listNetworkServices = new HashMap<>();

        try {
            List<CheckedInNetworkService> checkedInNetworkServiceList = daoFactory.getCheckedInNetworkServiceDao().findAll();

            if(checkedInNetworkServiceList != null){

                for(CheckedInNetworkService CheckedInNetworkService : checkedInNetworkServiceList){

                    if(!listNetworkServices.containsKey(NetworkServiceType.getByCode(CheckedInNetworkService.getNetworkServiceType())))
                        listNetworkServices.put(NetworkServiceType.getByCode(CheckedInNetworkService.getNetworkServiceType()),NetworkServiceType.getByCode(CheckedInNetworkService.getNetworkServiceType()));

                }

                return new ArrayList<>(listNetworkServices.values());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    private List<NetworkServiceType> getListOfNetworkServiceOfClientSpecific(String publicKeyClient){

        Map<NetworkServiceType, NetworkServiceType> listNetworkServices = new HashMap<>();

        try {

            List<CheckedInNetworkService> checkedInNetworkServiceList = daoFactory.getCheckedInNetworkServiceDao().findAll(
                    CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                    publicKeyClient);

            if(checkedInNetworkServiceList != null){

                for(CheckedInNetworkService CheckedInNetworkService : checkedInNetworkServiceList){

                    if(!listNetworkServices.containsKey(NetworkServiceType.getByCode(CheckedInNetworkService.getNetworkServiceType())))
                        listNetworkServices.put(NetworkServiceType.getByCode(CheckedInNetworkService.getNetworkServiceType()),NetworkServiceType.getByCode(CheckedInNetworkService.getNetworkServiceType()));

                }

                return new ArrayList<>(listNetworkServices.values());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }



}
