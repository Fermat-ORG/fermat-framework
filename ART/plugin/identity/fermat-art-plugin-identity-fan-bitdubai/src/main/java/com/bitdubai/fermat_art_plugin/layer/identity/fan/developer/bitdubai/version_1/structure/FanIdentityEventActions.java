package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHandleConnectionRequestAcceptedException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHandleNewsEventException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ArtActorInfo;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtArtistExtraData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions.CantAddNewArtistConnectedException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
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
     * Represents the Artist Actor Network Service manager.
     */
    private FanManager fanNetworkService;

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
            TokenlyFanIdentityManager tokenlyFanIdentityManager,
            FanManager fanManager) {
        this.artistNetworkService = artistNetworkService;
        this.fanaticIdentityManager = fanaticIdentityManager;
        this.tokenlyFanIdentityManager = tokenlyFanIdentityManager;
        this.fanNetworkService = fanManager;
    }

    /**
     * This method handles with artist news event exception
     * @throws CantHandleNewsEventException
     */
    public void handleArtistUpdateEvent() throws CantHandleNewsEventException {
        try {
            final List<ArtistConnectionRequest> list = artistNetworkService.
                    listCompletedConnections();
            for (final ArtistConnectionRequest request : list) {
                if (request.getRequestType() == RequestType.SENT  &&
                        request.getSenderActorType() == PlatformComponentType.ART_FAN) {
                    switch (request.getRequestAction()) {
                        case NONE:
                            this.handleConnection(
                                    request.getDestinationPublicKey(),
                                    EventSource.ACTOR_NETWORK_SERVICE_ARTIST);
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
    private void handleConnection(String senderPublicKey, EventSource eventSource) throws
            CantListFanIdentitiesException,
            CantListArtistsException,
            CantAddNewArtistConnectedException {
        //First, we get the Identities from this device
        List<Fanatic> fanaticList = fanaticIdentityManager.listIdentitiesFromCurrentDeviceUser();
        ArtExternalPlatform externalPlatform;
        switch (eventSource){
            case ACTOR_NETWORK_SERVICE_ARTIST:
                ActorSearch<ArtistExposingData> actorSearch;
                List<ArtistExposingData> artistExposingDataList;
                for(Fanatic fanatic : fanaticList){
                    //We get the fanatic external platform
                    externalPlatform = fanatic.getExternalPlatform();
                    actorSearch = artistNetworkService.getSearch();
                    if(actorSearch==null){
                        //TODO: throw an exception
                        return;
                    }
                    //TODO: to improve
                    artistExposingDataList = actorSearch.getResult(100,0);
                    switch (externalPlatform){
                        case TOKENLY:
                            updateTKYIdentity(senderPublicKey,fanatic, artistExposingDataList);
                            break;
                        case UNDEFINED:
                            //TODO: throw an exception
                    }

                }
                break;
            case ACTOR_NETWORK_SERVICE_FAN:
                ActorSearch<ArtistExposingData> fanSearch;
                //List<FanExposingData> fanExposingDataList;
                List<ArtistExposingData> fanExposingDataList;
                for(Fanatic fanatic : fanaticList){
                    //We get the fanatic external platform
                    externalPlatform = fanatic.getExternalPlatform();
                    fanSearch = artistNetworkService.getSearch();
                    if(fanSearch==null){
                        //TODO: throw an exception
                        return;
                    }
                    //TODO: to improve
                    fanExposingDataList = fanSearch.getResult(100,0);
                    switch (externalPlatform){
                        case TOKENLY:
                            updateFanTKYIdentity(senderPublicKey, fanatic, fanExposingDataList);
                            break;
                        case UNDEFINED:
                            //TODO: throw an exception
                    }

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
            String senderPublicKey,
            Fanatic fanatic,
            List<ArtistExposingData> artistExposingDataList) throws
            CantAddNewArtistConnectedException {
        try{
            UUID tkyId = fanatic.getExternalIdentityID();
            if(tkyId==null){
                //If tkyId is null, the artist doesn't have a TKY Identity
                return;
            }
            Fan tkyFan = this.tokenlyFanIdentityManager.getFanIdentity(tkyId);

            String artistUsername;
            String remoteArtistPublicKey;
            for(ArtistExposingData artistExposingData : artistExposingDataList){
                remoteArtistPublicKey = artistExposingData.getPublicKey();
                //If the public key from the search is equals to the actor requester, we going to proceed
                if(!senderPublicKey.equals(remoteArtistPublicKey)){
                    continue;
                }
                    //verify if we got Tokenly information
                ArtistExternalPlatformInformation artistExternalPlatformInformation =
                        artistExposingData.getArtistExternalPlatformInformation();
                HashMap<ArtExternalPlatform,String> artExternalPlatformStringHashMap =
                        artistExternalPlatformInformation.getExternalPlatformInformationMap();
                if(artExternalPlatformStringHashMap.containsKey(ArtExternalPlatform.TOKENLY)){
                    artistUsername = artExternalPlatformStringHashMap.get(
                            ArtExternalPlatform.TOKENLY);
                    //Finally, we can update the TKY identity with the artist username
                    tkyFan.addNewArtistConnected(artistUsername);
                    tokenlyFanIdentityManager.updateFanIdentity(tkyFan);

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
        } catch (CantGetFanIdentityException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "Cannot get a Fan identity" );
        } catch (CantUpdateFanIdentityException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "Cannot update a Fan identity" );
        }
    }

    /**
     * This method updates the TKY identity with the remote artist information.
     * We get this information through the Artist Actor Network Service.
     * @param fanatic
     * @param artistExposingDataList
     * @throws CantAddNewArtistConnectedException
     */
    private void updateFanTKYIdentity(
            String senderPublicKey,
            Fanatic fanatic,
            List<ArtistExposingData> artistExposingDataList) throws
            CantAddNewArtistConnectedException {
        try{
            UUID tkyId = fanatic.getExternalIdentityID();
            Fan tkyFan = this.tokenlyFanIdentityManager.getFanIdentity(tkyId);
            if(tkyId==null){
                //If tkyId is null, the artist doesn't have a TKY Identity
                return;
            }
            String artistUsername;
            String remoteArtistPublicKey;
            for(ArtistExposingData artistExposingData : artistExposingDataList){
                remoteArtistPublicKey = artistExposingData.getPublicKey();
                //If the public key from the search is equals to the actor requester, we going to proceed
                if(!senderPublicKey.equals(remoteArtistPublicKey)){
                    continue;
                }
                //verify if we got Tokenly information
                ArtistExternalPlatformInformation artistExternalPlatformInformation =
                        artistExposingData.getArtistExternalPlatformInformation();
                HashMap<ArtExternalPlatform,String> artExternalPlatformStringHashMap =
                        artistExternalPlatformInformation.getExternalPlatformInformationMap();
                if(artExternalPlatformStringHashMap.containsKey(ArtExternalPlatform.TOKENLY)){
                    artistUsername = artExternalPlatformStringHashMap.get(
                            ArtExternalPlatform.TOKENLY);
                    //Finally, we can update the TKY identity with the artist username
                    tkyFan.addNewArtistConnected(artistUsername);
                    tokenlyFanIdentityManager.updateFanIdentity(tkyFan);

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
        } catch (CantGetFanIdentityException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "Cannot get a Fan identity" );
        } catch (CantUpdateFanIdentityException e) {
            throw new CantAddNewArtistConnectedException(
                    e,
                    "Updating TKY identity",
                    "Cannot update a Fan identity" );
        }
    }

    /**
     * This method handles the Connection Request Accepted event
     * @param artistAcceptedPublicKey
     * @throws CantHandleConnectionRequestAcceptedException
     */
    public void handleArtistRequestConnectionAccepted(String artistAcceptedPublicKey) throws
            CantHandleConnectionRequestAcceptedException {
        try {
            this.handleConnection(artistAcceptedPublicKey, EventSource.ACTOR_NETWORK_SERVICE_FAN);
        } catch (CantListFanIdentitiesException e) {
            throw new CantHandleConnectionRequestAcceptedException(
                    e,
                    "Handling Artist Connection Request Accepted event",
                    "Cannot list the fan identities.");
        } catch (CantAddNewArtistConnectedException e) {
            throw new CantHandleConnectionRequestAcceptedException(
                    e,
                    "Handling Artist Connection Request Accepted event",
                    "Cannot add new artist to external platform identity.");
        } catch (CantListArtistsException e) {
            throw new CantHandleConnectionRequestAcceptedException(
                    e,
                    "Handling Artist Connection Request Accepted event",
                    "Cannot list the artist.");
        }
    }

    /**
     * This method check if any new connection to add to the Identities.
     * @throws CantHandleNewsEventException
     */
    public void checkAllConnections() throws CantHandleNewsEventException {
        //In this version I will to use the following method
        handleArtistUpdateEvent();
    }

}
