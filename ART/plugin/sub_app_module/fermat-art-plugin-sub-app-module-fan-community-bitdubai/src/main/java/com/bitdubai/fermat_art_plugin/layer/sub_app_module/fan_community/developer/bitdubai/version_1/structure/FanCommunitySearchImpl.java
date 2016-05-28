package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantGetFanSearchResult;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/5/16.
 */
public class FanCommunitySearchImpl implements FanCommunitySearch, Serializable {

    /*private final FanManager artistManagerActorNetworkServiceManager;

    public FanCommunitySearchImpl(final FanManager artistManagerActorNetworkServiceManager) {

        this.artistManagerActorNetworkServiceManager = artistManagerActorNetworkServiceManager;
    }*/

    @Override
    public void addAlias(String alias) {

    }

    @Override
    public List<FanCommunityInformation> getResult(ActorSearch actorSearch) throws CantGetFanSearchResult {
        try {

            //ActorSearch actorSearch = artistManagerActorNetworkServiceManager.getSearch();

            final List<FanExposingData> fanExposingDataList = actorSearch.getResult();

            final List<FanCommunityInformation> fanCommunityInformationList = new ArrayList<>();

            for(FanExposingData fed : fanExposingDataList)
                fanCommunityInformationList.add(new com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.utils.FanCommunityInformationImpl(fed));

            return fanCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetFanSearchResult("", exception, "", "Unhandled Error.");
        }    }
}
