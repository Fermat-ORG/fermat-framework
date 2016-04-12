package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHandleNewsEventException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestExternalPlatformInformationException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtArtistExtraData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions.CantAddNewArtistConnectedException;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/04/16.
 */
public class FanIdentityEventActions {

    /**
     * Represents the Artist Actor Network Service manager.
     */
    private ArtistManager artistNetworkService;

    /**
     * Represents the Fanatic Identity Manager.
     */
    private FanaticIdentityManager fanaticIdentityManager;

    /**
     * Represents the Tokenly Fan Identity Manager.
     */
    private TokenlyFanIdentityManager tokenlyFanIdentityManager;

    /**
     * Default constructor with parameters.
     * @param artistNetworkService
     * @param fanaticIdentityManager
     * @param tokenlyFanIdentityManager
     */
    public FanIdentityEventActions(
            ArtistManager artistNetworkService,
            FanaticIdentityManager fanaticIdentityManager,
            TokenlyFanIdentityManager tokenlyFanIdentityManager) {
        this.artistNetworkService = artistNetworkService;
        this.fanaticIdentityManager = fanaticIdentityManager;
        this.tokenlyFanIdentityManager = tokenlyFanIdentityManager;
    }

    /**
     * This method handles with artist news event exception
     * @throws CantHandleNewsEventException
     */
    public void handleArtistUpdateEvent() throws CantHandleNewsEventException {
        try {
            final List<ArtistConnectionRequest> list = artistNetworkService.
                    listPendingConnectionUpdates();
            for (final ArtistConnectionRequest request : list) {
                if (request.getRequestType() == RequestType.RECEIVED  &&
                        request.getSenderActorType() == PlatformComponentType.ART_FAN) {
                    switch (request.getRequestAction()) {
                        case ACCEPT:
                            this.handleConnection();
                            break;
                        //Other cases do nothing
                    }
                }
            }
        } catch(CantListPendingConnectionRequestsException e) {
            throw new CantHandleNewsEventException(
                    e,
                    "Handling Artist News Event",
                    "Cannot list the pending connection request.");
        } catch (CantListFanIdentitiesException e) {
            throw new CantHandleNewsEventException(
                    e,
                    "Handling Artist News Event",
                    "Cannot list the fan identities.");
        } catch (CantAddNewArtistConnectedException e) {
            throw new CantHandleNewsEventException(
                    e,
                    "Handling Artist News Event",
                    "Cannot add new artist to external platform identity.");
        } catch (CantListArtistsException e) {
            throw new CantHandleNewsEventException(
                    e,
                    "Handling Artist News Event",
                    "Cannot list the artist.");
        }
    }

    /**
     * This method handles when the Fan identity got a new artist connected and checks if we got
     * new information, updates the external platforms.
     * @throws CantListFanIdentitiesException
     * @throws CantListArtistsException
     * @throws CantAddNewArtistConnectedException
     */
    private void handleConnection() throws
            CantListFanIdentitiesException,
            CantListArtistsException,
            CantAddNewArtistConnectedException {
        //First, we get the Identities from this device
        List<Fanatic> fanaticList = fanaticIdentityManager.listIdentitiesFromCurrentDeviceUser();
        ExternalPlatform externalPlatform;
        ActorSearch<ArtistExposingData> actorSearch;
        List<ArtistExposingData> artistExposingDataList;
        for(Fanatic fanatic : fanaticList){
            //We get the fanatic external platform
            externalPlatform = fanatic.getExternalPlatform();
            actorSearch = artistNetworkService.getSearch();
            artistExposingDataList = actorSearch.getResult(PlatformComponentType.ART_ARTIST);
            switch (externalPlatform){
                case TOKENLY:
                    updateTKYIdentity(fanatic, artistExposingDataList);
                    break;
                case UNDEFINED:
                    //TODO: throw an exception
            }

        }
    }

    /**
     * This method updates the TKY identity with the remote artist information.
     * We get this information through the Artist Actor Network Service.
     * @param fanatic
     * @param artistExposingDataList
     * @throws CantAddNewArtistConnectedException
     */
    private void updateTKYIdentity(
            Fanatic fanatic,
            List<ArtistExposingData> artistExposingDataList) throws
            CantAddNewArtistConnectedException {
        try{
            UUID tkyId = fanatic.getExternalIdentityID();
            Fan tkyFan = this.tokenlyFanIdentityManager.getFanIdentity(tkyId);

            String artistUsername;
            String remoteArtistPublicKey;
            ArtArtistExtraData<ArtistExternalPlatformInformation> artistExternalData;
            for(ArtistExposingData artistExposingData : artistExposingDataList){
                //Get the remote artist public key
                remoteArtistPublicKey = artistExposingData.getPublicKey();
                //Request the remote artist information
                artistExternalData=artistNetworkService.requestExternalPlatformInformation(
                        fanatic.getPublicKey(),
                        PlatformComponentType.ART_FAN,
                        remoteArtistPublicKey
                );
                //Process the information obtained
                List<ArtistExternalPlatformInformation> listInformation=
                        artistExternalData.listInformation();
                for(ArtistExternalPlatformInformation artistExternalPlatformInformation :
                        listInformation){
                    HashMap<ArtExternalPlatform,String> artExternalPlatformStringHashMap=
                            artistExternalPlatformInformation.getExternalPlatformInformationMap();
                    //verify if we got Tokenly information
                    if(artExternalPlatformStringHashMap.containsKey(ArtExternalPlatform.TOKENLY)){
                        artistUsername = artExternalPlatformStringHashMap.get(
                                ArtExternalPlatform.TOKENLY);
                        //Finally, we can update the TKY identity with the artist username
                        tkyFan.addNewArtistConnected(artistUsername);
                    }
                }
            }
        } catch (ObjectNotSetException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "A method argument is null" );
        } catch (IdentityNotFoundException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "Identity cannot be found" );
        } catch (CantRequestExternalPlatformInformationException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "Cannot request information to a remote device" );
        } catch (CantGetFanIdentityException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "Cannot get a Fan identity" );
        }
    }

}
