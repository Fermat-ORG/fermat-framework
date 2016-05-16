package com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.ArtCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistCancellingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistConnectionDenialFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ArtistDisconnectingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.settings.ArtistCommunitySettings;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantListIdentitiesToSelectException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public interface ArtistCommunitySubAppModuleManager extends
        ModuleManager<
                ArtistCommunitySettings,
                ArtistCommunitySelectableIdentity>,ModuleSettingsImpl<ArtistCommunitySettings> {

    /**
     * The method <code>listWorldArtists</code> returns the list of all Artist in the world,
     * setting their status (CONNECTED, for example) with respect to the selectedIdentity parameter
     * logged in Artist
     *
     * @return a list of all Artists in the world
     *
     * @throws CantListArtistsException if something goes wrong.
     */
    List<ArtistCommunityInformation> listWorldArtists(
            ArtistCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws CantListArtistsException;


    /**
     * The method <code>listSelectableIdentities</code> lists all the Artists and Fanatics identities
     * stored locally in the device.
     *
     * @return a list of artist and fanatics identities the current device the user can use to log in.
     *
     * @throws CantListIdentitiesToSelectException if something goes wrong.
     */
    List<ArtistCommunitySelectableIdentity> listSelectableIdentities() throws
            CantListIdentitiesToSelectException;


    /**
     * Through the method <code>setSelectedActorIdentity</code> we can set the selected actor identity.
     */
    void setSelectedActorIdentity(ArtistCommunitySelectableIdentity identity);


    /**
     * The method <code>getArtistSearch</code> returns an interface that allows searching for remote
     * Artist that are not linked to the local selectedIdentity
     *
     * @return a searching interface
     */
    ArtistCommunitySearch getArtistSearch();

    /**
     * The method <code>getArtistSearch</code> returns an interface that allows searching for remote
     * Artist that are linked to the local selectedIdentity
     *
     * @return a searching interface
     */
    ArtistCommunitySearch searchConnectedArtist(ArtistCommunitySelectableIdentity selectedIdentity);

    /**
     * The method <code>requestConnectionToArtist</code> initialises a contact request between
     * two Artists.
     *
     * @param selectedIdentity       The selected local artist identity.
     * @param artistToContact  The information of the remote artist to connect to.
     *
     * @throws CantRequestConnectionException           if something goes wrong.
     * @throws ActorConnectionAlreadyRequestedException if the connection already exists.
     * @throws ActorTypeNotSupportedException           if the actor type is not supported.
     */
    void requestConnectionToArtist(
            ArtistCommunitySelectableIdentity selectedIdentity,
            ArtistCommunityInformation artistToContact) throws
            CantRequestConnectionException,
            ActorConnectionAlreadyRequestedException,
            ActorTypeNotSupportedException;

    /**
     * The method <code>acceptArtist</code> takes the information of a connection request, accepts
     * the request and adds the Artist to the list managed by this plugin with ContactState CONTACT.
     *
     * @param requestId      The request id of te connection to accept.
     *
     * @throws CantAcceptRequestException           if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request..
     */
    void acceptArtist(UUID requestId) throws
            CantAcceptRequestException,
            ConnectionRequestNotFoundException;


    /**
     * The method <code>denyConnection</code> denies a conection request from other Artist
     *
     * @param requestId      The request id of te connection to deny.
     *
     * @throws ArtistConnectionDenialFailedException if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request.
     */
    void denyConnection(UUID requestId) throws
            ArtistConnectionDenialFailedException,
            ConnectionRequestNotFoundException;

    /**
     * The method <code>disconnectArtist</code> disconnect an Artist from the list managed by this
     * plugin
     *
     * @param requestId      The request id of te connection to disconnect.
     *
     * @throws ArtistDisconnectingFailedException if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request.
     */
    void disconnectArtist(UUID requestId) throws
            ArtistDisconnectingFailedException,
            ConnectionRequestNotFoundException;

    /**
     * The method <code>cancelArtist</code> cancels an Artist from the list managed by this
     *
     * @param requestId      The request id of te connection to cancel.
     *
     * @throws ArtistCancellingFailedException if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request.
     */
    void cancelArtist(UUID requestId) throws
            ArtistCancellingFailedException,
            ConnectionRequestNotFoundException;

    /**
     * The method <code>listAllConnectedArtists</code> returns the list of all artist registered by the
     * logged in artist
     *
     * @return the list of artists connected to the logged in artist
     *
     * @throws CantListArtistsException if something goes wrong.
     */
    List<ArtCommunityInformation> listAllConnectedArtists(
            final ArtistCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws CantListArtistsException;

    /**
     * The method <code>listArtistsPendingLocalAction</code> returns the list of artist waiting to be accepted
     * or rejected by the logged in artist
     *
     * @return the list of artists waiting to be accepted or rejected by the  logged in artist
     *
     * @throws CantListArtistsException if something goes wrong.
     */
    List<ArtistCommunityInformation> listArtistsPendingLocalAction(
            final ArtistCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws CantListArtistsException;

    /**
     * The method <code>listArtistsPendingRemoteAction</code> list the artist that haven't
     * answered to a sent connection request by the current logged in artist.
     *
     * @return the list of artists that haven't answered to a sent connection request by the current
     *         logged in artist.
     *
     * @throws CantListArtistsException if something goes wrong.
     */
    List<ArtistCommunityInformation> listArtistsPendingRemoteAction(
            final ArtistCommunitySelectableIdentity selectedIdentity,
            final int max,
            final int offset) throws CantListArtistsException;

    /**
     * Count Artist waiting
     * @return
     */
    int getArtistsWaitingYourAcceptanceCount();

    /**
     * The method <code>getActorConnectionState</code> returns the ConnectionState of a given actor
     * with respect to the selected actor
     * @param publicKey
     *
     * @return
     *
     * @throws CantValidateConnectionStateException if something goes wrong.
     */
    ConnectionState getActorConnectionState(String publicKey) throws
            CantValidateConnectionStateException;

    /**
     * This method checks if an actor connection exists.
     * @param linkedIdentityPublicKey
     * @param linkedIdentityActorType
     * @param actorPublicKey
     * @return
     * @throws CantGetActorConnectionException
     */
    List<ArtistActorConnection> getRequestActorConnections(
            String linkedIdentityPublicKey,
            Actors linkedIdentityActorType,
            String actorPublicKey) throws CantGetActorConnectionException;

}
