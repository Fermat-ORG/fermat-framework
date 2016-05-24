package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantGetArtistSearchResult;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySearch;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.utils.ArtistCommunityInformationImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public class ArtistCommunitySearchImpl implements ArtistCommunitySearch, Serializable {

    /*private final ArtistManager artistActorNetworkServiceManager;

    public ArtistCommunitySearchImpl(final ArtistManager artistActorNetworkServiceManager) {

        this.artistActorNetworkServiceManager = artistActorNetworkServiceManager;
    }*/


    @Override
    public void addAlias(String alias) {

    }

    @Override
    public List<ArtistCommunityInformation> getResult(ActorSearch artistSearch) throws CantGetArtistSearchResult {
        try {

            //ActorSearch artistSearch = artistActorNetworkServiceManager.getSearch();

            final List<ArtistExposingData> artistExposingDataList = artistSearch.getResult();

            final List<ArtistCommunityInformation> artistCommunityInformationList = new ArrayList<>();

            for(ArtistExposingData aed : artistExposingDataList)
                artistCommunityInformationList.add(new ArtistCommunityInformationImpl(aed));

            return artistCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetArtistSearchResult(exception, "", "Unhandled Error.");
        }    }
}
