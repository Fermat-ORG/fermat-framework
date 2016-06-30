package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantGetArtistSearchResult;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySearch;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.utils.ArtistCommunityInformationImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

            //TODO: to improve
            final List<ArtistExposingData> artistExposingDataList = artistSearch.getResult(100,0);

            final List<ArtistCommunityInformation> artistCommunityInformationList = new ArrayList<>();

            ArtistCommunityInformation artistCommunityInformation;
            for(ArtistExposingData aed : artistExposingDataList){
                artistCommunityInformation = new ArtistCommunityInformationImpl(aed);
                HashMap<ArtExternalPlatform,String> platformStringHashMap=
                        aed.getArtistExternalPlatformInformation()
                                .getExternalPlatformInformationMap();
                Set<ArtExternalPlatform> keySet = platformStringHashMap.keySet();
                for(ArtExternalPlatform artExternalPlatform: keySet){
                    artistCommunityInformation.setArtExternalPlatform(artExternalPlatform);
                    break;
                }
                artistCommunityInformationList.add(artistCommunityInformation);
            }


            return artistCommunityInformationList;

        } catch (final Exception exception) {

            throw new CantGetArtistSearchResult(exception, "", "Unhandled Error.");
        }    }
}
