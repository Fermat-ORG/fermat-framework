package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.NetworkNodePluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodesCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.RecordNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang.ClassUtils;
import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.Profiles</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
@Path("/profiles")
public class Profiles implements RestFulServices {

    /**
     * Represent the LOG
     */
    private final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(Profiles.class));

    /**
     * Represent the daoFactory
     */
    private DaoFactory daoFactory;

    /**
     * Represent the gson
     */
    private Gson gson;

    private NetworkNodePluginRoot pluginRoot;

    /**
     * Constructor
     */
    public Profiles(){
        daoFactory = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        pluginRoot = (NetworkNodePluginRoot) NodeContext.get(NodeContextItem.PLUGIN_ROOT);
    }

    @POST
    @Path("/actors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getList(@FormParam("client_public_key") String clientIdentityPublicKey, @FormParam("discovery_params") String discoveryParam){

        LOG.info(" --------------------------------------------------------------------- ");
        LOG.info("Profiles - Starting listActors");
        JsonObject jsonObjectRespond = new JsonObject();

        try{

            /*
             * Construct the json object
             */
            DiscoveryQueryParameters discoveryQueryParameters = DiscoveryQueryParameters.parseContent(discoveryParam);

            LOG.info("clientIdentityPublicKey  = " + clientIdentityPublicKey);
            LOG.info("discoveryQueryParameters = " + discoveryQueryParameters.toJson());

            /*
             * hold the result list
             */
            List<ActorProfile> resultList = filterActors(discoveryQueryParameters, clientIdentityPublicKey);

            LOG.info("filteredLis.size() =" + resultList.size());

            /*
             * Convert the list to json representation
             */
            String jsonListRepresentation = GsonProvider.getGson().toJson(resultList, new TypeToken<List<ActorProfile>>() {
            }.getType());

            System.out.println(jsonListRepresentation);

            /*
             * Create the respond
             */
            jsonObjectRespond.addProperty("data", jsonListRepresentation);


        }catch (Exception e){

            LOG.warn("requested list is not available");
            jsonObjectRespond.addProperty("failure", "Requested list is not available");
            e.printStackTrace();
        }

        String jsonString = GsonProvider.getGson().toJson(jsonObjectRespond);

        LOG.debug("jsonString.length() = " + jsonString.length());

        return Response.status(200).entity(jsonString).build();

    }

    private  List<ActorsCatalog> filterActorsOnline(List<ActorsCatalog>  actorsCatalogs){

        List<ActorsCatalog> actors = new ArrayList<>();

        for(ActorsCatalog actorsCatalog : actorsCatalogs){

            try {

                if(actorsCatalog.getNodeIdentityPublicKey().equals(pluginRoot.getIdentity().getPublicKey())) {

                    if (daoFactory.getCheckedInActorDao().exists(actorsCatalog.getIdentityPublicKey()))
                        actors.add(actorsCatalog);

                } else if(isActorOnline(actorsCatalog)) {
                    actors.add(actorsCatalog);
                }

            } catch (CantReadRecordDataBaseException e) {
                e.printStackTrace();
            }

        }

        return actors;
    }

    /**
     * Filter all network service from data base that mach
     * with the parameters
     *
     * @param discoveryQueryParameters
     * @return List<ActorProfile>
     */
    private List<ActorProfile> filterActors(DiscoveryQueryParameters discoveryQueryParameters, String clientIdentityPublicKey) throws CantReadRecordDataBaseException, InvalidParameterException {

        Map<String, ActorProfile> profileList = new HashMap<>();

        Map<String, Object> filters = constructFiltersActorTable(discoveryQueryParameters);
        List<ActorsCatalog> actorsList;

        int max    = 10;
        int offset =  0;

        if (discoveryQueryParameters.getMax() != null && discoveryQueryParameters.getMax() > 0)
            max = (discoveryQueryParameters.getMax() > 100) ? 100 : discoveryQueryParameters.getMax();

        if (discoveryQueryParameters.getOffset() != null && discoveryQueryParameters.getOffset() >= 0)
            offset = discoveryQueryParameters.getOffset();

        while (profileList.size() < max && getDaoFactory().getActorsCatalogDao().getAllCount(filters) > offset) {

            if (discoveryQueryParameters.getLocation() != null)
                actorsList = getDaoFactory().getActorsCatalogDao().findAllNearestTo(filters, max, offset, discoveryQueryParameters.getLocation());
            else
                actorsList = getDaoFactory().getActorsCatalogDao().findAll(filters, max, offset);

            List<ActorsCatalog> actors = filterActorsOnline(actorsList);

            for (ActorsCatalog actorsCatalog : actors) {

                if (clientIdentityPublicKey == null ||
                        actorsCatalog.getClientIdentityPublicKey() == null ||
                        !actorsCatalog.getClientIdentityPublicKey().equals(clientIdentityPublicKey)) {

                    profileList.put(actorsCatalog.getIdentityPublicKey(), buildActorProfileFromActorCatalogRecord(actorsCatalog));
                }
            }

            offset += max;
        }

        return new ArrayList<>(profileList.values());

    }

    /*
     * get ActorProfile From ActorsCatalog
     */
    private ActorProfile buildActorProfileFromActorCatalogRecord(ActorsCatalog actor){

        ActorProfile actorProfile = new ActorProfile();
        actorProfile.setIdentityPublicKey(actor.getIdentityPublicKey());
        actorProfile.setAlias(actor.getAlias());
        actorProfile.setName(actor.getName());
        actorProfile.setActorType(actor.getActorType());
        actorProfile.setPhoto(actor.getPhoto());
        actorProfile.setExtraData(actor.getExtraData());
        actorProfile.setLocation(actor.getLastLocation());

        return actorProfile;
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

    private Boolean isActorOnline(ActorsCatalog actorsCatalog){

        try {

            String nodeUrl = getNodeUrl(actorsCatalog.getNodeIdentityPublicKey());

            URL url = new URL("http://" + nodeUrl + "/fermat/rest/api/v1/online/component/actor/" + actorsCatalog.getIdentityPublicKey());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String respond = reader.readLine();

            if (conn.getResponseCode() == 200 && respond != null && respond.contains("success")) {
                JsonParser parser = new JsonParser();
                JsonObject respondJsonObject = (JsonObject) parser.parse(respond.trim());

                return respondJsonObject.get("isOnline").getAsBoolean();

            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

    private String getNodeUrl(final String publicKey) {

        try {

            NodesCatalog nodesCatalog = daoFactory.getNodesCatalogDao().findById(publicKey);
            return nodesCatalog.getIp()+":"+nodesCatalog.getDefaultPort();

        } catch (RecordNotFoundException exception) {
            throw new RuntimeException("Node not found in catalog: "+exception.getMessage());
        } catch (Exception exception) {
            throw new RuntimeException("Problem trying to find the node in the catalog: "+exception.getMessage());
        }
    }

    private DaoFactory getDaoFactory() {

        if (daoFactory == null)
            daoFactory = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);

        return daoFactory;

    }

}
