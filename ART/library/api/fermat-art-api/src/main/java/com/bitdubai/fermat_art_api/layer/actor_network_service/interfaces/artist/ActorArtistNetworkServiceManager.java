package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ARTNetworkService;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAskConnectionActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconectActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRegisterActorArtistNetworkServiceException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestListActorArtistNetworkServiceRegisteredException;

import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public interface ActorArtistNetworkServiceManager extends FermatManager, ARTNetworkService{

    /**
     * Register the Actor Artist in the cloud server like online
     *
     * @param actorArtistNetworkService
     * @throws CantRegisterActorArtistNetworkServiceException
     */
    void registerActorArtist(ArtistActor actorArtistNetworkService) throws CantRegisterActorArtistNetworkServiceException;

    /**
     * Update the v in the cloud server like online
     *
     * @param actorArtistNetworkService
     * @throws CantRegisterActorArtistNetworkServiceException
     */
    void updateActorArtist(ArtistActor actorArtistNetworkService) throws CantRegisterActorArtistNetworkServiceException;

    /**
     * Get the content of the list previously requested, this method have to call after the
     * the notification event is receive <code>Nombre del Evento</>
     *
     * @return List<ActorAssetRedeemPoint>
     */
    List<ArtistActor> getListActorArtistRegistered() throws CantRequestListActorArtistNetworkServiceRegisteredException;


    /**
     * The method <code>askConnectionActorArtist</code> sends a connection request to another Actor.
     *
     * @param senderPublicKey               The logged public key of the
     * @param senderName                    The logged name
     * @param senderType                    The senderType
     * @param receiverPublicKey             The receiverPublicKey
     * @param receiverName                  The receiverName
     * @param destinationType               The public key of the
     * @param profileImage                  The profile image of the user sending the request
     */
    void askConnectionActorArtist(String senderPublicKey,
                                 String senderName,
                                  PlatformComponentType senderType,
                                 String receiverPublicKey,
                                 String receiverName,
                                  PlatformComponentType destinationType,
                                 byte[] profileImage) throws CantAskConnectionActorArtistNetworkServiceException;

    /**
     * The method <code>acceptConnectionActorArtist</code> send an acceptance message of a connection request.
     *
     * @param actorLoggedInPublicKey The public key of the actor asset accepting the connection request.
     * @param ActorToAddPublicKey    The public key of the actor asset to add
     */
    void acceptConnectionActorArtist(String actorLoggedInPublicKey, String ActorToAddPublicKey) throws CantAcceptActorArtistNetworkServiceException;

    /**
     * The method <code>denyConnectionActorArtist</code> send an rejection message of a connection request.
     *
     * @param actorLoggedInPublicKey The public key of the actor asset accepting the connection request.
     * @param actorToRejectPublicKey The public key of the actor asset to add
     */
    void denyConnectionActorArtist(String actorLoggedInPublicKey, String actorToRejectPublicKey) throws CantDenyActorArtistNetworkServiceException;

    /**
     * The method <coda>disconnectConnectionActorArtist</coda> disconnects and informs the other intra user the disconnecting
     *
     * @param actorLoggedInPublicKey The public key of the actor asset disconnecting the connection
     * @param actorToDisconnectPublicKey The public key of the user to disconnect
     * @throws CantDisconectActorArtistNetworkServiceException
     */
    void disconnectConnectionActorArtist(String actorLoggedInPublicKey, String actorToDisconnectPublicKey) throws CantDisconectActorArtistNetworkServiceException;

    /**
     * The method <coda>cancelConnectionActorArtist</coda> cancels and informs the other intra user the cancelling
     *
     * @param actorLoggedInPublicKey The public key of the actor asset cancelling the connection
     * @param actorToCancelPublicKey The public key of the user to cancel
     * @throws CantCancelActorArtistNetworkServiceException
     */
    void cancelConnectionActorArtist(String actorLoggedInPublicKey, String actorToCancelPublicKey) throws CantCancelActorArtistNetworkServiceException;

}
