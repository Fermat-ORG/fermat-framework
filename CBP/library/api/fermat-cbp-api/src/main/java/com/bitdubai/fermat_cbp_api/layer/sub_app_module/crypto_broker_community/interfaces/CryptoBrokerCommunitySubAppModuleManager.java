package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetSelectedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionDenialFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;

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
public interface CryptoBrokerCommunitySubAppModuleManager extends ModuleManager<FermatSettings, CryptoBrokerCommunitySelectableIdentity> {

    /**
     * The method <code>listSelectableIdentities</code> lists the login identities that can be used
     * to log in as an Crypto Broker for the current Device User.
     *
     * @return the list of identities the current Device User can use to log in
     *
     * @throws CantListIdentitiesToSelectException if something goes wrong.
     */
    List<CryptoBrokerCommunitySelectableIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException;

    /**
     * The method <code>searchCryptoBroker</code> gives us an interface to manage a search for a particular
     * crypto broker
     *
     * @return a searching interface
     */
    CryptoBrokerSearch searchCryptoBroker();

    /**
     * The method <code>requestConnectionToCryptoBroker</code> initialize the request of contact between
     * two crypto brokers.
     *
     * @param selectedIdentity       The identity selected to work with.
     * @param cryptoBrokerToContact  the information of the broker to add.
     *
     * @throws CantRequestConnectionException           if something goes wrong.
     * @throws ActorConnectionAlreadyRequestedException if the connection already exists.
     * @throws ActorTypeNotSupportedException           if the actor type is not supported.
     */
    void requestConnectionToCryptoBroker(CryptoBrokerCommunitySelectableIdentity selectedIdentity   ,
                                         CryptoBrokerInformation                 cryptoBrokerToContact) throws CantRequestConnectionException          ,
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
     * The method <code>getAllCryptoBrokers</code> returns the list of all crypto brokers registered by the
     * logged in crypto broker
     *
     * @return the list of crypto brokers connected to the logged in crypto broker
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerInformation> getAllCryptoBrokers(String identityPublicKey,
                                                      int    max              ,
                                                      int    offset           ) throws CantListCryptoBrokersException;

    /**
     * The method <code>getCryptoBrokersWaitingYourAcceptance</code> returns the list of crypto brokers waiting to be accepted
     * or rejected by the logged in crypto broker
     *
     * @return the list of crypto brokers waiting to be accepted or rejected by the  logged in crypto broker
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerInformation> getCryptoBrokersWaitingYourAcceptance(String identityPublicKey,
                                                                        int    max              ,
                                                                        int    offset           ) throws CantListCryptoBrokersException;

    /**
     * The method <code>getCryptoBrokersWaitingTheirAcceptance</code> list the crypto brokers that haven't
     * answered to a sent connection request by the current logged in crypto broker.
     *
     * @return the list of crypto brokers that haven't answered to a sent connection request by the current
     *         logged in crypto broker.
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerInformation> getCryptoBrokersWaitingTheirAcceptance(String identityPublicKey,
                                                                         int    max              ,
                                                                         int    offset           ) throws CantListCryptoBrokersException;

    /**
     *
     * @return active CryptoBrokerCommunitySelectableIdentity
     *
     * @throws CantGetSelectedIdentityException if something goes wrong.
     */
    CryptoBrokerCommunitySelectableIdentity getActiveCryptoBrokerIdentity() throws CantGetSelectedIdentityException;

    /**
     * Count crypto broker waiting
     * @return
     */
    int getCryptoBrokersWaitingYourAcceptanceCount();

    /**
     * The method <code>updateCryptoBrokerIdentity</code> change a identity information data
     *
     * @param identityPublicKey
     * @param identityAlias
     * @param identityPhrase
     * @param profileImage
     *
     * @throws CantUpdateIdentityException if something goes wrong.
     */
    void updateCryptoBrokerIdentity(String identityPublicKey,
                                    String identityAlias    ,
                                    String identityPhrase   ,
                                    byte[] profileImage     ) throws CantUpdateIdentityException;


    /**
     *The method <code>deleteCryptoBrokerIdentity</code> change identity status to inactive
     *
     * @param identityPublicKey
     *
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    void  deleteCryptoBrokerIdentity(String identityPublicKey) throws CantListCryptoBrokersException;

    /**
     *
     * @param publicKey
     *
     * @return
     *
     * @throws CantValidateConnectionStateException if something goes wrong.
     */
    boolean isActorConnected(String publicKey) throws CantValidateConnectionStateException;

}