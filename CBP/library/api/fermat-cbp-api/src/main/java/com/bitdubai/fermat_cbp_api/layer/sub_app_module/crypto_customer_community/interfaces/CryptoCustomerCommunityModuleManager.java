package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;

import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_community.interfaces.CryptoCustomerModuleManager</code>
 * provides the methods for the Crypto Customer Community sub app, to Identity Management Customers and the relationship with other Customers.
 */
public interface CryptoCustomerCommunityModuleManager extends ModuleManager{



    /**
     * The method <code>getSuggestionsToContact</code> searches for crypto Customer that the logged in
     * crypto customer could be interested to add.
     *
     * @return a list with information of crypto customer
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    public List<CryptoCustomerInformation> getSuggestionsToContact(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;

    /**
     * The method <code>searchCryptoCustomer</code> gives us an interface to manage a search for a particular
     * crypto customer
     *
     * @return a searching interface
     */
    public CryptoCustomerSearch searchCryptoCustomer();

    /**
     * The method <code>askCryptoBrokerForAcceptance</code> initialize the request of contact between
     * a crypto Customer and a other crypto customer.
     *
     * @param cryptoCustomerToAddName      The name of the crypto customer to add
     * @param cryptoCustomerToAddPublicKey The public key of the crypto customer to add
     * @param profileImage            The profile image that the crypto customer has
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException
     */
    public void askCryptoCustomerForAcceptance(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantStartRequestException;

    /**
     * The method <code>acceptCryptoCustomer</code> takes the information of a connection request, accepts
     * the request and adds the crypto customer to the list managed by this plugin with ContactState CONTACT.
     *
     * @param cryptoCustomerToAddName      The name of the crypto customer to add
     * @param cryptoCustomerToAddPublicKey The public key of the crypto customer to add
     * @param profileImage            The profile image that the crypto customer has
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException
     */
    public void acceptCryptoCustomer(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a connection request from other crypto Customer
     *
     * @param cryptoCustomerToRejectPublicKey the public key of the user to deny its connection request
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerConnectionRejectionFailedException
     */
    public void denyConnection(String cryptoCustomerToRejectPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerConnectionRejectionFailedException;

    /**
     * The method <code>disconnectCryptoCustomerr</code> disconnect an crypto Customer from the list managed by this
     * plugin
     *
     * @param cryptoCustomerToDisconnectPublicKey the public key of the crypto Customer to disconnect
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException
     */
    public void disconnectCryptoCustomer(String cryptoCustomerToDisconnectPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;

    /**
     * The method <code>cancelCryptoCustomer</code> cancels an crypto Customer from the list managed by this
     * @param cryptoCustomerToCancelPublicKey
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException
     */
    void cancelCryptoBroker(String cryptoCustomerToCancelPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException;

    /**
     * The method <code>getAllCryptoCustomers</code> returns the list of all crypto Customer registered by the
     * logged in crypto Customer
     *
     * @return the list of crypto Customer connected to the logged in broker
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    public List<CryptoCustomerInformation> getAllCryptoCustomers(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;

    /**
     * The method <code>getCryptoCustomersWaitingYourAcceptance</code> returns the list of crypto Customer waiting to be accepted
     * or rejected by the logged in Customer
     *
     * @return the list of crypto Customer waiting to be accepted or rejected by the  logged in Customer
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    public List<CryptoCustomerInformation> getCryptoCustomersWaitingYourAcceptance(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;

    /**
     * The method <code>getCryptoBrokersWaitingTheirAcceptance</code> list the crypto Customer that haven't
     * answered to a sent connection request by the current logged in Customer.
     *
     * @return the list of crypto Customer that haven't answered to a sent connection request by the current
     * logged in Customer.
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException
     */
    public List<CryptoCustomerInformation> getCryptoCustomersWaitingTheirAcceptance(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;


    /**
     * The method <code>login</code> let an crypto Customer log in
     *
     * @param customerPublicKey the public key of the crypto Customer to log in
     */
    public void login(String customerPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantLoginCustomerException;


}