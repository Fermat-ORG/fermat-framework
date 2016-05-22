package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingInformationRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestExternalPlatformInformationException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public interface ArtistManager extends FermatManager{

    /**
     * Through the method <code>exposeIdentity</code> we can expose the crypto identities created in the device.
     * The information given will be shown to all the crypto customers.
     *
     * @param artistExposingData  crypto broker exposing information.
     *
     * @throws CantExposeIdentityException   if something goes wrong.
     */
    void exposeIdentity(final ArtistExposingData artistExposingData) throws CantExposeIdentityException;

    void updateIdentity(final ArtistExposingData artistExposingData) throws CantExposeIdentityException;

    /**
     * Through the method <code>exposeIdentities</code> we can expose the crypto identities created in the device.
     * The information given will be shown to all the crypto customers.
     *
     * @param artistExposingDataList  list of crypto broker exposing information.
     *
     * @throws CantExposeIdentitiesException   if something goes wrong.
     */
    void exposeIdentities(final Collection<ArtistExposingData> artistExposingDataList) throws CantExposeIdentitiesException;

    /**
     * Through the method <code>getSearch</code> we can get a new instance of Crypto Broker Search.
     * This Crypto Broker search provides all the necessary functionality to make a Crypto Broker Search.
     *
     * @return a CryptoBrokerCommunitySearch instance.
     */
    ActorSearch<ArtistExposingData> getSearch();

    /**
     * Through the method <code>requestConnection</code> we can request to a crypto broker a connection.
     * When we're connected with a crypto broker, we're enabled to make negotiations with him.
     *
     * @param artistConnectionInformation an instance of ArtistConnectionInformation with the information of the Crypto Broker and the counterpart.
     *
     * @throws CantRequestConnectionException if something goes wrong.
     */
    void requestConnection(final ArtistConnectionInformation artistConnectionInformation) throws CantRequestConnectionException;

    /**
     * Through the method <code>disconnect</code> we can disconnect of a crypto broker.
     * If we don't want to negotiate anymore or the reason that you want with a broker, you can disconnect of him.
     *
     * @param requestId   id of the connection request to disconnect.
     *
     * @throws CantDisconnectException if something goes wrong.
     */
    void disconnect(final UUID requestId) throws CantDisconnectException, ConnectionRequestNotFoundException;

    /**
     * Through the method <code>denyConnection</code> we can deny a connection request.
     * The broker can deny a connection request if he doesn't trust in the customer data.
     *
     * @param requestId   id of the connection request to deny.
     *
     * @throws CantDenyConnectionRequestException   if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if the connection request cannot be found.
     */
    void denyConnection(final UUID requestId) throws CantDenyConnectionRequestException, ConnectionRequestNotFoundException;

    /**
     * Through the method <code>cancelConnection</code> we can cancel a connection request sent.
     * The Customer can cancel a connection request previously sent.
     *
     * @param requestId   id of the connection request to cancel.
     *
     * @throws CantCancelConnectionRequestException   if something goes wrong.
     * @throws ConnectionRequestNotFoundException     if the connection request cannot be found.
     */
    void cancelConnection(final UUID requestId) throws CantCancelConnectionRequestException, ConnectionRequestNotFoundException;

    /**
     * Through the method <code>acceptConnection</code> we can accept a received connection request.
     * The Broker can accept a connection request. In the near future maybe, when a customer is connection we can send him offers.
     *
     * @param requestId  id of the connection request to accept.
     *
     * @throws CantAcceptConnectionRequestException   if something goes wrong.
     * @throws ConnectionRequestNotFoundException     if the connection request cannot be found.
     */
    void acceptConnection(final UUID requestId) throws CantAcceptConnectionRequestException, ConnectionRequestNotFoundException;

    /**
     * Through the method <code>listPendingConnectionNews</code> we can list all the connection news
     * with a pending local action.
     *
     * This method is exposed for the crypto broker actor connection plug-in. Here we'll return all the new requests that arrive to him.
     *
     * @param actorType type of the actor whom wants to be new notifications
     *
     * @return a list of instance of CryptoBrokerConnectionNews
     *
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    List<ArtistConnectionRequest> listPendingConnectionNews(PlatformComponentType actorType) throws CantListPendingConnectionRequestsException;

    /**
     * Through the method <code>listPendingConnectionUpdates</code> we can list all the connection news
     * with a pending local action.
     *
     * This method is exposed for all the actors that try to connect with a crypto broker. Here we'll return all the updates of the requests that arrive to them.
     *
     * @return a list of instance of CryptoBrokerConnectionNews
     *
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    List<ArtistConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException;
    
    void confirm(final UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException;

    //Request external platform information methods
    /**
     * This method request the ArtArtistExtraData managed by the Artist.
     * @param requesterPublicKey
     * @param requesterActorType
     * @param artistPublicKey
     * @return
     * @throws CantRequestExternalPlatformInformationException
     */
    ArtArtistExtraData<ArtistExternalPlatformInformation> requestExternalPlatformInformation(
            UUID requestId,
            String requesterPublicKey,
            PlatformComponentType requesterActorType,
            String artistPublicKey) throws CantRequestExternalPlatformInformationException;

    /**
     * This method returns the pending information request list.
     * @param requestType SENT or RECEIVED
     * @return
     * @throws CantListPendingInformationRequestsException
     */
    List<ArtArtistExtraData<ArtistExternalPlatformInformation>> listPendingInformationRequests(
            RequestType requestType) throws CantListPendingInformationRequestsException;

    /**
     * This method returns all completed request connections.
     * @return
     * @throws CantListPendingConnectionRequestsException
     */
    List<ArtistConnectionRequest> listCompletedConnections() throws
            CantListPendingConnectionRequestsException;


    /**
     * This method returns all the request persisted in database
     * @return
     * @throws CantListPendingConnectionRequestsException
     */
    List<ArtistConnectionRequest> listAllRequest() throws CantListPendingConnectionRequestsException;
}
