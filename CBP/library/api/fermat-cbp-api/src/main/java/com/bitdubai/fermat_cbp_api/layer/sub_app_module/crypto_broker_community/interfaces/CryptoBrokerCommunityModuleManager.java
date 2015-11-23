package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;


import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */
/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerModuleManager</code>
 * provides the methods for the Crypto Broker Community sub app.
 */
public interface CryptoBrokerCommunityModuleManager extends ModuleManager{



    /**
     * The method <code>getSuggestionsToContact</code> searches for customer that the logged in
     * crypto broker could be interested to add.
     *
     * @return a list with information of crypto Broker
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException
     */
    public List<CryptoBrokerInformation> getSuggestionsToContact(int max,int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException;

    /**
     * The method <code>searchCryptoBroker</code> gives us an interface to manage a search for a particular
     * crypto broker
     *
     * @return a searching interface
     */
    public CryptoBrokerSearch searchCryptoBroker();

    /**
     * The method <code>askCryptoBrokerForAcceptance</code> initialize the request of contact between
     * two crypto broker.
     *
     * @param cryptoBrokerToAddName      The name of the crypto Broker to add
     * @param cryptoBrokerToAddPublicKey The public key of the crypto Broker to add
     * @param profileImage            The profile image that the crypto Broker has
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantStartRequestException
     */
    public void askCryptoBrokerForAcceptance(String cryptoBrokerToAddName, String cryptoBrokerToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantStartRequestException;

    /**
     * The method <code>acceptCryptoBroker</code> takes the information of a connection request, accepts
     * the request and adds the crypto Broker to the list managed by this plugin with ContactState CONTACT.
     *
     * @param cryptoBrokerToAddName      The name of the crypto Broker to add
     * @param cryptoBrokerToAddPublicKey The public key of the crypto Broker to add
     * @param profileImage            The profile image that the crypto Broker has
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException
     */
    public void acceptCryptoBroker(String cryptoBrokerToAddName, String cryptoBrokerToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a conection request from other crypto Broker
     *
     * @param cryptoBrokerToRejectPublicKey the public key of the user to deny its connection request
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionRejectionFailedException
     */
    public void denyConnection(String cryptoBrokerToRejectPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionRejectionFailedException;

    /**
     * The method <code>disconnectCryptoBroker</code> disconnect an crypto Broker from the list managed by this
     * plugin
     *
     * @param cryptoBrokerToDisconnectPublicKey the public key of the crypto Broker to disconnect
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException
     */
    public void disconnectCryptoBroker(String cryptoBrokerToDisconnectPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerDisconnectingFailedException;

    /**
     * The method <code>cancelCryptoBroker</code> cancels an crypto Broker from the list managed by this
     * @param cryptoBrokerToCancelPublicKey
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException
     */
    void cancelCryptoBroker(String cryptoBrokerToCancelPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;

    /**
     * The method <code>getAllCryptoBrokers</code> returns the list of all crypto Broker registered by the
     * logged in customer
     *
     * @return the list of crypto Broker connected to the logged in customer
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException
     */
    public List<CryptoBrokerInformation> getAllCryptoBrokers(int max,int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException;

    /**
     * The method <code>getCryptoBrokersWaitingYourAcceptance</code> returns the list of crypto Broker waiting to be accepted
     * or rejected by the logged in customer
     *
     * @return the list of crypto Broker waiting to be accepted or rejected by the  logged in customer
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException
     */
    public List<CryptoBrokerInformation> getCryptoBrokersWaitingYourAcceptance(int max,int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException;

    /**
     * The method <code>getCryptoBrokersWaitingTheirAcceptance</code> list the crypto Broker that haven't
     * answered to a sent connection request by the current logged in customer.
     *
     * @return the list of crypto Broker that haven't answered to a sent connection request by the current
     * logged in customer.
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException
     */
    public List<CryptoBrokerInformation> getCryptoBrokersWaitingTheirAcceptance(int max,int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantGetCryptoBrokerListException;


    /**
     * The method <code>login</code> let an Customer log in
     *
     * @param customerPublicKey the public key of the Customer to log in
     */
    public void login(String customerPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantLoginCustomerException;


}