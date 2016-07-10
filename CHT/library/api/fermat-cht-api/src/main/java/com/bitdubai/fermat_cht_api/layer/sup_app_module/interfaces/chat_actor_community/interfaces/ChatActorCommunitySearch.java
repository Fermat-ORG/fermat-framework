package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eleazar Oroño (eorono@protonmail.com) on 1/04/16.
 */
public interface ChatActorCommunitySearch extends Serializable {

    void addActorAlias(String alias);

    List<ChatActorCommunityInformation> getResult() throws CantGetChtActorSearchResult;

    public List<ChatActorCommunityInformation> getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max) throws CantGetChtActorSearchResult;

    List<ChatActorCommunityInformation> getResultLocation(DeviceLocation deviceLocation) throws CantGetChtActorSearchResult;

    List<ChatActorCommunityInformation> getResultDistance(double distance) throws CantGetChtActorSearchResult;

    List<ChatActorCommunityInformation> getResultAlias(String alias) throws CantGetChtActorSearchResult;

    ChatExposingData getResult(final String publicKey) throws CantListChatException;

    String getAlias();

    String getPublicKey();
}
