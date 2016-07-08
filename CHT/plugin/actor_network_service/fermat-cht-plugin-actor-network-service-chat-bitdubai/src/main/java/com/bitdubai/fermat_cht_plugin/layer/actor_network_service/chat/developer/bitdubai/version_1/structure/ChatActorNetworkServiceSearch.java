package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.ChatActorNetworkServicePluginRoot;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ChatActorNetworkServiceSearch extends ChatSearch {

    private final ChatActorNetworkServicePluginRoot pluginRoot;

    public ChatActorNetworkServiceSearch(final ChatActorNetworkServicePluginRoot pluginRoot) {

        this.pluginRoot = pluginRoot;
    }

    @Override
    public List<ChatExposingData> getResult() throws CantListChatException {

        try {

            System.out.println("Chat Actor Network Service Search Test Entering getResult() method...");

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CHAT.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.ACTOR_CHAT
            );

            System.out.println("Chat Actor Network Service Search Test Listing through communication layer...");

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            System.out.println("Chat Actor Network Service Search Test Listing through communication layer... SUCCESS: "+list.size()+" actors found.");

            final List<ChatExposingData> chatExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                JsonParser parser = new JsonParser();

                Gson gson = new Gson();

                JsonObject extraData = parser.parse(actorProfile.getExtraData()).getAsJsonObject();

                String country = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.COUNTRY), String.class);

                String state = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATE), String.class);

                String status= gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATUS),String.class);

                String city = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.CITY),String.class);

                chatExposingDataArrayList.add(new ChatExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), country, state, city, status, actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            System.out.println("Chat Actor Network Service Search Test RETURNING LIST OF ACTORS.");

            return chatExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ChatExposingData> getResult(final Integer max) throws CantListChatException {
        return null;
    }

    @Override
    public ChatExposingData getResult(String publicKey) throws CantListChatException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    //TODO:Hay que pasarle null porque no esta implementado de esa forma en p2p
                    //Actors.CHAT.getCode(),
                    null,
                    null,
                    null,
                    null,
                    publicKey,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.ACTOR_CHAT
            );

            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            ActorProfile actorProfile;

            if(list !=null && !list.isEmpty()) {
                actorProfile = list.get(0);
            }
            else return null;

            JsonParser parser = new JsonParser();

            Gson gson = new Gson();

            JsonObject extraData = parser.parse(actorProfile.getExtraData()).getAsJsonObject();

            String country = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.COUNTRY), String.class);

            String state = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATE), String.class);

            String status= gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATUS),String.class);

            String city = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.CITY), String.class);

            return new ChatExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), country, state, city, status, actorProfile.getLocation(), 0, 0, actorProfile.getStatus());

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }

    /**
     * Send null the unused variable.
     *
     * @param publicKey
     * @param deviceLocation
     * @param distance
     * @param alias
     * @return List<ChatExposingData>
     * @throws CantListChatException
     */
    @Override
    public List<ChatExposingData> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max) throws CantListChatException {

        try {

            /**
             * Constructor with params
             *
             * @param identityPublicKey    represents the identity public key of the component to discover.
             * @param networkServiceType   if we're looking for network services we'll set this value with the type of network service.
             * @param actorType            if we're looking for actors we'll set this value with the type of the actor.
             * @param name                 we can set here the name of the component to search or discover.
             * @param alias                we can set here the alias of the component to search or discover.
             * @param extraData            we can set here the extraData of the actor component to search or discover.
             * @param location             this param indicates a point for doing the discovery near it.
             * @param distance             this param indicates the distance to the point to look around.
             * @param isOnline             with this param we ask to the node the status of the profiles to discover.
             * @param lastConnectionTime   with this param we'll ask to the node only the profiles connected after the long timestamp.
             * @param max                  this param will be used with the pagination stuff.
             * @param offset               this param will be used with the pagination stuff.*/
            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    null,
                    NetworkServiceType.UNDEFINED,
                    Actors.CHAT.getCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    true,
                    //TODO: Se coloco null ya que leon necesita que esta valor null ya que solo esto se usa solo para buscar por publicKey del Actor
                    null, //publicKey,
                    max,
                    offSet
            );

//            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
//                    Actors.CHAT.getCode(),
//                    alias,
//                    distance,
//                    null,
//                    //TODO: Se coloco null ya que leon necesita que esta valor null ya que solo esto se usa solo para buscar por publicKey del Actor
//                    null,//publicKey,
//                    deviceLocation,
//                    max,
//                    null,
//                    NetworkServiceType.UNDEFINED,
//                    offSet,
//                    NetworkServiceType.ACTOR_CHAT
//            );


            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ChatExposingData> chatExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                JsonParser parser = new JsonParser();

                Gson gson = new Gson();

                JsonObject extraData = parser.parse(actorProfile.getExtraData()).getAsJsonObject();

                String country = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.COUNTRY), String.class);

                String state = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATE), String.class);

                String status= gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATUS),String.class);

                String city = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.CITY),String.class);

                System.out.println("************** I\'m a crypto Chat: " + actorProfile.getAlias() + " - " + actorProfile.getStatus());

                chatExposingDataArrayList.add(new ChatExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), country, state, city, status, actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return chatExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ChatExposingData> getResultLocation(DeviceLocation deviceLocation) throws CantListChatException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CHAT.getCode(),
                    null,
                    null,
                    null,
                    null,
                    deviceLocation,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.ACTOR_CHAT
            );


            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ChatExposingData> chatExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                JsonParser parser = new JsonParser();

                Gson gson = new Gson();

                JsonObject extraData = parser.parse(actorProfile.getExtraData()).getAsJsonObject();

                String country = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.COUNTRY), String.class);

                String state = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATE), String.class);

                String status= gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATUS),String.class);

                String city = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.CITY),String.class);

                chatExposingDataArrayList.add(new ChatExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), country, state, city, status, actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return chatExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ChatExposingData> getResultDistance(double distance) throws CantListChatException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CHAT.getCode(),
                    null,
                    distance,
                    null,
                    null,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.ACTOR_CHAT
            );


            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ChatExposingData> chatExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                JsonParser parser = new JsonParser();

                Gson gson = new Gson();

                JsonObject extraData = parser.parse(actorProfile.getExtraData()).getAsJsonObject();

                String country = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.COUNTRY), String.class);

                String state = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATE), String.class);

                String status= gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATUS),String.class);

                String city = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.CITY),String.class);

                chatExposingDataArrayList.add(new ChatExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), country, state, city, status, actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return chatExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<ChatExposingData> getResultAlias(String alias) throws CantListChatException {

        try {

            DiscoveryQueryParameters discoveryQueryParameters = new DiscoveryQueryParameters(
                    Actors.CHAT.getCode(),
                    alias,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    NetworkServiceType.UNDEFINED,
                    null,
                    NetworkServiceType.ACTOR_CHAT
            );


            final List<ActorProfile> list = pluginRoot.getConnection().listRegisteredActorProfiles(discoveryQueryParameters);

            final List<ChatExposingData> chatExposingDataArrayList = new ArrayList<>();

            for (final ActorProfile actorProfile : list) {

                JsonParser parser = new JsonParser();

                Gson gson = new Gson();

                JsonObject extraData = parser.parse(actorProfile.getExtraData()).getAsJsonObject();

                String country = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.COUNTRY), String.class);

                String state = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATE), String.class);

                String status= gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.STATUS),String.class);

                String city = gson.fromJson(extraData.get(ChatExtraDataJsonAttNames.CITY),String.class);

                chatExposingDataArrayList.add(new ChatExposingData(actorProfile.getIdentityPublicKey(), actorProfile.getAlias(), actorProfile.getPhoto(), country, state, city, status, actorProfile.getLocation(), 0, 0, actorProfile.getStatus()));
            }

            return chatExposingDataArrayList;

        } catch (final CantRequestProfileListException e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Problem trying to request list of registered components in communication layer.");

        } catch (final Exception e) {

            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListChatException(e, "", "Unhandled error.");
        }
    }
}
