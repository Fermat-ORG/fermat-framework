package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ActorSearch;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionRequest;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 09/03/16.
 */
public interface FanManager extends FermatManager{

    /**
     * Through the method <code>exposeIdentity</code> we can expose the crypto identities created in the device.
     * The information given will be shown to all the crypto customers.
     *
     * @param fanExposingData  crypto broker exposing information.
     *
     * @throws CantExposeIdentityException   if something goes wrong.
     */
    void exposeIdentity(final FanExposingData fanExposingData) throws CantExposeIdentityException;

    void updateIdentity(final FanExposingData fanExposingData) throws CantExposeIdentityException;

    /**
     * Through the method <code>exposeIdentities</code> we can expose the crypto identities created in the device.
     * The information given will be shown to all the crypto customers.
     *
     * @param fanExposingDataList  list of crypto broker exposing information.
     *
     * @throws CantExposeIdentitiesException   if something goes wrong.
     */
    void exposeIdentities(final Collection<FanExposingData> fanExposingDataList) throws CantExposeIdentitiesException;

    /**
     * Through the method <code>getSearch</code> we can get a new instance of Crypto Broker Search.
     * This Crypto Broker search provides all the necessary functionality to make a Crypto Broker Search.
     *
     * @return a CryptoBrokerCommunitySearch instance.
     */
    ActorSearch<FanExposingData> getSearch();

    /**
     * Through the method <code>requestConnection</code> we can request to a crypto broker a connection.
     * When we're connected with a crypto broker, we're enabled to make negotiations with him.
     *
     * @param fanConnectionInformation an instance of FanConnectionInformation with the information of the Crypto Broker and the counterpart.
     *
     * @throws CantRequestConnectionException if something goes wrong.
     */
    void requestConnection(final FanConnectionInformation fanConnectionInformation) throws CantRequestConnectionException;

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
    List<FanConnectionRequest> listPendingConnectionNews(PlatformComponentType actorType) throws CantListPendingConnectionRequestsException;

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
    List<FanConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException;
    
    void confirm(final UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException;

    /**
     * This method returns all the request persisted in database
     * @return
     * @throws CantListPendingConnectionRequestsException
     */
    List<FanConnectionRequest> listAllRequest() throws CantListPendingConnectionRequestsException;

}
