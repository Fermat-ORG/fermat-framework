package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces;

import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanSearchResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public interface FanCommunitySearch extends Serializable {

    /**
     * This method add the alias.
     * @param alias
     */
    void addAlias(String alias);

    /**
     * This method returns a FanCommunityInformation list.
     * @return
     * @throws CantGetFanSearchResult
     */
    List<FanCommunityInformation> getResult(ActorSearch actorSearch) throws CantGetFanSearchResult;
}
