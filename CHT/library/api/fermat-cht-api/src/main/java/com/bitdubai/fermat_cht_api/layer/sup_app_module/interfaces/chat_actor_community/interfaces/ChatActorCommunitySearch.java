package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetChtActorSearchResult;

import java.io.Serializable;

/**
 * Created by Eleazar Oroño (eorono@protonmail.com) on 1/04/16.
 */
public interface ChatActorCommunitySearch extends Serializable {

    void getResult(String publicKey, DeviceLocation deviceLocation, double distance, String alias, Integer offSet, Integer max, String requesterPublicKey) throws CantGetChtActorSearchResult;

}
