package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetSelectedIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSaveProfileImageException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantStartRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantUpdateIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantValidateConnectionStateException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionDenialFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerModuleManager</code>
 * provides all the necessary methods for the Crypto Broker Community sub app.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public interface CryptoBrokerCommunityModuleManager extends ModuleManager{

    /**
     * The method <code>createCryptoBroker</code> is used to create a new crypto broker
     *
     * @param cryptoBrokerName the name of the crypto broker to create
     * @param phrase the phrase of the crypto broker to create
     * @param profileImage  the profile image of the crypto broker to create
     *
     * @return the login identity generated for the said crypto broker.
     *
     * @throws CantCreateCryptoBrokerException if something goes wrong.
     */
    CryptoBrokerLoginIdentity createCryptoBroker(String cryptoBrokerName,
                                                 String phrase          ,
                                                 byte[] profileImage    ) throws CantCreateCryptoBrokerException;

    /**
     * The method <code>setNewProfileImage</code> let the current logged in crypto broker set its profile
     * picture.
     *
     * @param image the profile picture to set
     *
     * @throws CantSaveProfileImageException if something goes wrong.
     */
    void setNewProfileImage(byte[] image                ,
                            String cryptoBrokerPublicKey) throws CantSaveProfileImageException;

    /**
     * The method <code>listSelectableIdentities</code> lists the login identities that can be used
     * to log in as an Crypto Broker for the current Device User.
     *
     * @return the list of identities the current Device User can use to log in
     *
     * @throws CantListIdentitiesToSelectException if something goes wrong.
     */
    List<CryptoBrokerLoginIdentity> listSelectableIdentities() throws CantListIdentitiesToSelectException;

    /**
     * The method <code>select</code> allows as to select a crypto broker to work with.
     *
     * @param cryptoBrokerPublicKey the public key of the crypto broker to log in
     *
     * @throws CantSelectCryptoBrokerException if something goes wrong.
     */
    void select(String cryptoBrokerPublicKey) throws CantSelectCryptoBrokerException;

    /**
     * The method <code>getSuggestionsToContact</code> searches for crypto brokers that the logged in
     * crypto broker could be interested to add.
     *
     * @return a list with information of crypto brokers
     *
     * @throws CantGetCryptoBrokerListException if something goes wrong.
     */
    List<CryptoBrokerInformation> getSuggestionsToContact(int max   ,
                                                          int offset) throws CantGetCryptoBrokerListException;

    /**
     * The method <code>searchCryptoBroker</code> gives us an interface to manage a search for a particular
     * crypto broker
     *
     * @return a searching interface
     */
    CryptoBrokerSearch searchCryptoBroker();

    /**
     * The method <code>askCryptoBrokerForAcceptance</code> initialize the request of contact between
     * two crypto brokers.
     *
     * @param cryptoBrokerToAddName      The name of the crypto broker to add
     * @param cryptoBrokerToAddPublicKey The public key of the crypto broker to add
     * @param profileImage               The profile image that the crypto broker has
     *
     * @throws CantStartRequestException if something goes wrong.
     */
    void askCryptoBrokerForAcceptance(String cryptoBrokerToAddName     ,
                                      String cryptoBrokerToAddPublicKey,
                                      byte[] profileImage              ,
                                      String identityPublicKey         ,
                                      String identityAlias             ) throws CantStartRequestException;

    /**
     * The method <code>acceptCryptoBroker</code> takes the information of a connection request, accepts
     * the request and adds the crypto broker to the list managed by this plugin with ContactState CONTACT.
     *
     * @param cryptoBrokerToAddName      The name of the crypto broker to add
     * @param cryptoBrokerToAddPublicKey The public key of the crypto broker to add
     * @param profileImage               The profile image that the crypto broker has
     *
     * @throws CantAcceptRequestException if something goes wrong.
     */
    void acceptCryptoBroker(String identityPublicKey         ,
                            String cryptoBrokerToAddName     ,
                            String cryptoBrokerToAddPublicKey,
                            byte[] profileImage              ) throws CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a conection request from other crypto broker
     *
     * @param cryptoBrokerToRejectPublicKey the public key of the user to deny its connection request
     *
     * @throws CryptoBrokerConnectionDenialFailedException if something goes wrong.
     */
    void denyConnection(String cryptoBrokerLoggedPublicKey  ,
                        String cryptoBrokerToRejectPublicKey) throws CryptoBrokerConnectionDenialFailedException;

    /**
     * The method <code>disconnectCryptoBroker</code> disconnect an crypto broker from the list managed by this
     * plugin
     *
     * @param cryptoBrokerToDisconnectPublicKey the public key of the crypto broker to disconnect
     *
     * @throws CryptoBrokerDisconnectingFailedException if something goes wrong.
     */
    void disconnectCryptoBroker(String cryptoBrokerToDisconnectPublicKey) throws CryptoBrokerDisconnectingFailedException;

    /**
     * The method <code>cancelCryptoBroker</code> cancels an crypto broker from the list managed by this
     *
     * @param cryptoBrokerToCancelPublicKey
     *
     * @throws CryptoBrokerCancellingFailedException if something goes wrong.
     */
    void cancelCryptoBroker(String cryptoBrokerToCancelPublicKey) throws CryptoBrokerCancellingFailedException;

    /**
     * The method <code>getAllCryptoBrokers</code> returns the list of all crypto brokers registered by the
     * logged in crypto broker
     *
     * @return the list of crypto brokers connected to the logged in crypto broker
     *
     * @throws CantGetCryptoBrokerListException if something goes wrong.
     */
    List<CryptoBrokerInformation> getAllCryptoBrokers(String identityPublicKey,
                                                      int    max              ,
                                                      int    offset           ) throws CantGetCryptoBrokerListException;

    /**
     * The method <code>getCryptoBrokersWaitingYourAcceptance</code> returns the list of crypto brokers waiting to be accepted
     * or rejected by the logged in crypto broker
     *
     * @return the list of crypto brokers waiting to be accepted or rejected by the  logged in crypto broker
     *
     * @throws CantGetCryptoBrokerListException if something goes wrong.
     */
    List<CryptoBrokerInformation> getCryptoBrokersWaitingYourAcceptance(String identityPublicKey,
                                                                        int    max              ,
                                                                        int    offset           ) throws CantGetCryptoBrokerListException;

    /**
     * The method <code>getCryptoBrokersWaitingTheirAcceptance</code> list the crypto brokers that haven't
     * answered to a sent connection request by the current logged in crypto broker.
     *
     * @return the list of crypto brokers that haven't answered to a sent connection request by the current
     *         logged in crypto broker.
     *
     * @throws CantGetCryptoBrokerListException if something goes wrong.
     */
    List<CryptoBrokerInformation> getCryptoBrokersWaitingTheirAcceptance(String identityPublicKey,
                                                                         int    max              ,
                                                                         int    offset           ) throws CantGetCryptoBrokerListException;

    /**
     *
     * @return active CryptoBrokerLoginIdentity
     *
     * @throws CantGetSelectedIdentityException if something goes wrong.
     */
    CryptoBrokerLoginIdentity getActiveCryptoBrokerIdentity() throws CantGetSelectedIdentityException;

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
     * @throws CantGetCryptoBrokerListException if something goes wrong.
     */
    void  deleteCryptoBrokerIdentity(String identityPublicKey) throws CantGetCryptoBrokerListException;

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