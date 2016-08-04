package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eleazar (eorono@protonmail.com) on 9/4/2016.
 * Edited by Miguel Rincon on 18/04/2016
 */


public class ChatActorCommunitySubAppModuleSearch implements ChatActorCommunitySearch {

    private final ChatManager chatActorNetworkServiceManager;
    private String alias;

    public ChatActorCommunitySubAppModuleSearch(final ChatManager chatActorNetworkServiceManager) {

        this.chatActorNetworkServiceManager = chatActorNetworkServiceManager;
    }


    @Override
    public void addActorAlias(String alias) {

        this.alias = alias;
    }

    @Override
    public List<ChatActorCommunityInformation> getResult() throws CantGetChtActorSearchResult {

        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final List<ChatExposingData> chatActorConnections = chatActorSearch.getResult();

            final List<ChatActorCommunityInformation> chatActorLocalCommunityInformationList = new ArrayList<>();

            for (ChatExposingData ced : chatActorConnections)
                chatActorLocalCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(ced));

            return chatActorLocalCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetChtActorSearchResult(CantGetChtActorSearchResult.CONTEXT_CONTENT_SEPARATOR, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<ChatActorCommunityInformation> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max) throws CantGetChtActorSearchResult {
        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final List<ChatExposingData> chatActorConnections = chatActorSearch.getResult(publicKey, deviceLocation, distance, alias, offSet, max);

            final List<ChatActorCommunityInformation> chatActorLocalCommunityInformationList = new ArrayList<>();

            for (ChatExposingData ced : chatActorConnections)
                chatActorLocalCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(ced));

            return chatActorLocalCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetChtActorSearchResult(CantGetChtActorSearchResult.CONTEXT_CONTENT_SEPARATOR, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<ChatActorCommunityInformation> getResultLocation(DeviceLocation deviceLocation) throws CantGetChtActorSearchResult {
        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final List<ChatExposingData> chatActorConnections = chatActorSearch.getResultLocation(deviceLocation);

            final List<ChatActorCommunityInformation> chatActorLocalCommunityInformationList = new ArrayList<>();

            for (ChatExposingData ced : chatActorConnections)
                chatActorLocalCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(ced));

            return chatActorLocalCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetChtActorSearchResult(CantGetChtActorSearchResult.CONTEXT_CONTENT_SEPARATOR, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<ChatActorCommunityInformation> getResultDistance(double distance) throws CantGetChtActorSearchResult {
        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final List<ChatExposingData> chatActorConnections = chatActorSearch.getResultDistance(distance);

            final List<ChatActorCommunityInformation> chatActorLocalCommunityInformationList = new ArrayList<>();

            for (ChatExposingData ced : chatActorConnections)
                chatActorLocalCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(ced));

            return chatActorLocalCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetChtActorSearchResult(CantGetChtActorSearchResult.CONTEXT_CONTENT_SEPARATOR, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<ChatActorCommunityInformation> getResultAlias(String alias) throws CantGetChtActorSearchResult {
        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final List<ChatExposingData> chatActorConnections = chatActorSearch.getResultAlias(alias);

            final List<ChatActorCommunityInformation> chatActorLocalCommunityInformationList = new ArrayList<>();

            for (ChatExposingData ced : chatActorConnections)
                chatActorLocalCommunityInformationList.add(new ChatActorCommunitySubAppModuleInformationImpl(ced));

            return chatActorLocalCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetChtActorSearchResult(CantGetChtActorSearchResult.CONTEXT_CONTENT_SEPARATOR, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public ChatExposingData getResult(String publicKey) throws CantListChatException {
        try {

            ChatSearch chatActorSearch = chatActorNetworkServiceManager.getSearch();

            final ChatExposingData chatActorConnections = chatActorSearch.getResult(publicKey);

            return chatActorConnections;

        } catch (final Exception exception) {

            throw new CantListChatException(CantGetChtActorSearchResult.CONTEXT_CONTENT_SEPARATOR, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getPublicKey() {
        return null;
    }
}
