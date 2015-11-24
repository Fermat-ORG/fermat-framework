package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.UnexpectedProtocolStateException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionInformation;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionRequest;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager</code>
 * provides the methods to interact with the crypto broker actor network service.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public interface CryptoBrokerManager extends FermatManager {

    /**
     * Through the method <code>exposeIdentity</code> we can expose the crypto identities created in the device.
     * The information given will be shown to all the crypto customers.
     *
     * @param cryptoBrokerExposingData  crypto broker exposing information.
     *
     * @throws CantExposeIdentityException   if something goes wrong.
     */
    void exposeIdentity(final CryptoBrokerExposingData cryptoBrokerExposingData) throws CantExposeIdentityException;

    /**
     * Through the method <code>exposeIdentities</code> we can expose the crypto identities created in the device.
     * The information given will be shown to all the crypto customers.
     *
     * @param cryptoBrokerExposingDataList  list of crypto broker exposing information.
     *
     * @throws CantExposeIdentitiesException   if something goes wrong.
     */
    void exposeIdentities(final List<CryptoBrokerExposingData> cryptoBrokerExposingDataList) throws CantExposeIdentitiesException;

    /**
     * Through the method <code>getSearch</code> we can get a new instance of Crypto Broker Search.
     * This Crypto Broker search provides all the necessary functionality to make a Crypto Broker Search.
     *
     * @return a CryptoBrokerSearch instance.
     */
    CryptoBrokerSearch getSearch();

    /**
     * Through the method <code>requestConnection</code> we can request to a crypto broker a connection.
     * When we're connected with a crypto broker, we're enabled to make negotiations with him.
     *
     * @param cryptoBrokerConnectionInformation an instance of CryptoBrokerConnectionInformation with the information of the Crypto Broker and the counterpart.
     *
     * @throws CantRequestConnectionException if something goes wrong.
     */
    void requestConnection(final CryptoBrokerConnectionInformation cryptoBrokerConnectionInformation) throws CantRequestConnectionException;

    /**
     * Through the method <code>disconnect</code> we can disconnect of a crypto broker.
     * If we don't want to negotiate anymore or the reason that you want with a broker, you can disconnect of him.
     *
     * @param actorIdentityPublicKey the public key of the actor identity who is trying to disconnect.
     * @param actorIdentityActorType the actor type of the actor identity who is trying to disconnect.
     * @param cryptoBrokerPublicKey  the public key of the crypto broker with who we're trying to disconnect.
     *
     * @throws CantDisconnectException if something goes wrong.
     */
    void disconnect(final String actorIdentityPublicKey,
                    final Actors actorIdentityActorType,
                    final String cryptoBrokerPublicKey ) throws CantDisconnectException;

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
    void cancelConnection(final UUID requestId) throws CantCancelConnectionRequestException, ConnectionRequestNotFoundException, UnexpectedProtocolStateException;

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
     * @return a list of instance of CryptoBrokerConnectionNews
     *
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    List<CryptoBrokerConnectionRequest> listPendingConnectionNews() throws CantListPendingConnectionRequestsException;

    /**
     * Through the method <code>listPendingConnectionUpdates</code> we can list all the connection news
     * with a pending local action.
     *
     * @return a list of instance of CryptoBrokerConnectionNews
     *
     * @throws CantListPendingConnectionRequestsException if something goes wrong.
     */
    List<CryptoBrokerConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException;

}
