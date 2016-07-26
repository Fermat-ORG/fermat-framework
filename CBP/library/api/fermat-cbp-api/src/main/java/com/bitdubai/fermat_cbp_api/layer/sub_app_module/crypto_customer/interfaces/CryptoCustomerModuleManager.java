package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer.interfaces;


import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantLoginCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;

import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>CryptoCustomerModuleManager</code>
 * provides the methods for the Crypto Customer sub app, for managing contacts between a broker and its Customers.
 */
public interface CryptoCustomerModuleManager {


    /**
     * The method <code>searchCryptoCustomer</code> gives us an interface to manage a search for a particular
     * crypto customer
     *
     * @return a searching interface
     */
    CryptoCustomerSearch searchCryptoCustomer();


    /**
     * The method <code>acceptCryptoCustomer</code> takes the information of a connection request, accepts
     * the request and adds the crypto customer to the list managed by this plugin with ContactState CONTACT.
     *
     * @param cryptoCustomerToAddName      The name of the crypto customer to add
     * @param cryptoCustomerToAddPublicKey The public key of the crypto customer to add
     * @param profileImage                 The profile image that the crypto customer has
     * @throws CantAcceptRequestException
     */
    void acceptCryptoCustomer(String cryptoCustomerToAddName, String cryptoCustomerToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a connection request from other crypto Customer
     *
     * @param cryptoCustomerToRejectPublicKey the public key of the user to deny its connection request
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerConnectionRejectionFailedException
     */
    void denyConnection(String cryptoCustomerToRejectPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerConnectionRejectionFailedException;

    /**
     * The method <code>disconnectCryptoCustomerr</code> disconnect an crypto Customer from the list managed by this
     * plugin
     *
     * @param cryptoCustomerToDisconnectPublicKey the public key of the crypto Customer to disconnect
     * @throws CryptoCustomerDisconnectingFailedException
     */
    void disconnectCryptoCustomer(String cryptoCustomerToDisconnectPublicKey) throws CryptoCustomerDisconnectingFailedException;

    /**
     * The method <code>cancelCryptoCustomer</code> cancels an crypto Customer from the list managed by this
     *
     * @param cryptoCustomerToCancelPublicKey
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException
     */
    void cancelCryptoBroker(String cryptoCustomerToCancelPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerCancellingFailedException;

    /**
     * The method <code>getAllCryptoCustomers</code> returns the list of all crypto Customer registered by the
     * logged in crypto broker
     *
     * @return the list of crypto Customer connected to the logged in broker
     * @throws CantGetCryptoCustomerListException
     */
    List<com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer.interfaces.CryptoCustomerInformation> getAllCryptoCustomers(int max, int offset) throws CantGetCryptoCustomerListException;

    /**
     * The method <code>getCryptoCustomersWaitingYourAcceptance</code> returns the list of crypto Customer waiting to be accepted
     * or rejected by the logged in broker
     *
     * @return the list of crypto Customer waiting to be accepted or rejected by the  logged in broker
     * @throws CantGetCryptoCustomerListException
     */
    List<com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer.interfaces.CryptoCustomerInformation> getCryptoCustomersWaitingYourAcceptance(int max, int offset) throws CantGetCryptoCustomerListException;


    /**
     * The method <code>login</code> let an crypto broker log in
     *
     * @param customerPublicKey the public key of the crypto broker to log in
     */
    void login(String customerPublicKey) throws CantLoginCustomerException;


}