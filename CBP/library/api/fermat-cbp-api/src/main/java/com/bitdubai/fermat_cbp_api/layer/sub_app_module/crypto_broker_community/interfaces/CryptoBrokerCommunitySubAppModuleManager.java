package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionDenialFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerModuleManager</code>
 * provides all the necessary methods for the Crypto Broker Community sub app.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *par
 * @author lnacosta
 * @version 1.0.0
 */
public interface CryptoBrokerCommunitySubAppModuleManager
        extends ModuleManager<CryptoBrokerCommunitySettings, CryptoBrokerCommunitySelectableIdentity>,
        ModuleSettingsImpl<CryptoBrokerCommunitySettings>, Serializable {

    /**
     * The method <code>listWorldCryptoBrokers</code> returns the list of all crypto brokers in the world,
     * setting their status (CONNECTED, for example) with respect to the selectedIdentity parameter
     * logged in crypto broker
     *
     * @return a list of all crypto brokers in the world
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerCommunityInformation> listWorldCryptoBrokers(CryptoBrokerCommunitySelectableIdentity selectedIdentity, final int max, final int offset) throws CantListCryptoBrokersException;


    /**
     * The method <code>listSelectableIdentities</code> lists all the Crypto Broker and Crypto Customer identities
     * stored locally in the device.
     *
     * @return a list of broker and customer identities the current device the user can use to log in.
     *
     * @throws CantListIdentitiesToSelectException if something goes wrong.
     */
    List<CryptoBrokerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException;


    /**
     * Through the method <code>setSelectedActorIdentity</code> we can set the selected actor identity.
     */
    void setSelectedActorIdentity(CryptoBrokerCommunitySelectableIdentity identity);


    /**
     * The method <code>getCryptoBrokerSearch</code> returns an interface that allows searching for remote
     * Crypto Brokers that are not linked to the local selectedIdentity
     *
     * @return a searching interface
     */
    CryptoBrokerCommunitySearch getCryptoBrokerSearch();

    /**
     * The method <code>getCryptoBrokerSearch</code> returns an interface that allows searching for remote
     * Crypto Brokers that are linked to the local selectedIdentity
     *
     * @return a searching interface
     */
    CryptoBrokerCommunitySearch searchConnectedCryptoBroker(CryptoBrokerCommunitySelectableIdentity selectedIdentity);

    /**
     * The method <code>requestConnectionToCryptoBroker</code> initialises a contact request between
     * two crypto brokers.
     *
     * @param selectedIdentity       The selected local broker identity.
     * @param cryptoBrokerToContact  The information of the remote broker to connect to.
     *
     * @throws CantRequestConnectionException           if something goes wrong.
     * @throws ActorConnectionAlreadyRequestedException if the connection already exists.
     * @throws ActorTypeNotSupportedException           if the actor type is not supported.
     */
    void requestConnectionToCryptoBroker(CryptoBrokerCommunitySelectableIdentity selectedIdentity     ,
                                         CryptoBrokerCommunityInformation        cryptoBrokerToContact) throws CantRequestConnectionException          ,
            ActorConnectionAlreadyRequestedException,
            ActorTypeNotSupportedException          ;

    /**
     * The method <code>acceptCryptoBroker</code> takes the information of a connection request, accepts
     * the request and adds the crypto broker to the list managed by this plugin with ContactState CONTACT.
     *
     * @param requestId      The request id of te connection to accept.
     *
     * @throws CantAcceptRequestException           if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request..
     */
    void acceptCryptoBroker(UUID requestId) throws CantAcceptRequestException, ConnectionRequestNotFoundException;


    /**
     * The method <code>denyConnection</code> denies a conection request from other crypto broker
     *
     * @param requestId      The request id of te connection to deny.
     *
     * @throws CryptoBrokerConnectionDenialFailedException if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request.
     */
    void denyConnection(UUID requestId) throws CryptoBrokerConnectionDenialFailedException, ConnectionRequestNotFoundException;

    /**
     * The method <code>disconnectCryptoBroker</code> disconnect an crypto broker from the list managed by this
     * plugin
     *
     * @param requestId      The request id of te connection to disconnect.
     *
     * @throws CryptoBrokerDisconnectingFailedException if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request.
     */
    void disconnectCryptoBroker(UUID requestId) throws CryptoBrokerDisconnectingFailedException, ConnectionRequestNotFoundException;

    /**
     * The method <code>cancelCryptoBroker</code> cancels an crypto broker from the list managed by this
     *
     * @param requestId      The request id of te connection to cancel.
     *
     * @throws CryptoBrokerCancellingFailedException if something goes wrong.
     * @throws ConnectionRequestNotFoundException   if we cant find the connection request.
     */
    void cancelCryptoBroker(UUID requestId) throws CryptoBrokerCancellingFailedException, ConnectionRequestNotFoundException;

    /**
     * The method <code>listAllConnectedCryptoBrokers</code> returns the list of all crypto brokers registered by the
     * logged in crypto broker
     *
     * @return the list of crypto brokers connected to the logged in crypto broker
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerCommunityInformation> listAllConnectedCryptoBrokers(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                         final int                                     max             ,
                                                                         final int                                     offset          ) throws CantListCryptoBrokersException;

    /**
     * The method <code>listCryptoBrokersPendingLocalAction</code> returns the list of crypto brokers waiting to be accepted
     * or rejected by the logged in crypto broker
     *
     * @return the list of crypto brokers waiting to be accepted or rejected by the  logged in crypto broker
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerCommunityInformation> listCryptoBrokersPendingLocalAction(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                               final int max,
                                                                               final int offset) throws CantListCryptoBrokersException;

    /**
     * The method <code>listCryptoBrokersPendingRemoteAction</code> list the crypto brokers that haven't
     * answered to a sent connection request by the current logged in crypto broker.
     *
     * @return the list of crypto brokers that haven't answered to a sent connection request by the current
     *         logged in crypto broker.
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerCommunityInformation> listCryptoBrokersPendingRemoteAction(final CryptoBrokerCommunitySelectableIdentity selectedIdentity,
                                                                                final int max,
                                                                                final int offset) throws CantListCryptoBrokersException;

    /**
     * Count crypto broker waiting
     * @return
     */
    int getCryptoBrokersWaitingYourAcceptanceCount();

    /**
     * The method <code>getActorConnectionState</code> returns the ConnectionState of a given actor
     * with respect to the selected actor
     * @param publicKey
     *
     * @return
     *
     * @throws CantValidateConnectionStateException if something goes wrong.
     */
    ConnectionState getActorConnectionState(String publicKey) throws CantValidateConnectionStateException;

}