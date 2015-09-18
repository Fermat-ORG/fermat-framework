package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException;

import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager</code>
 * provides the methods for the Crypto Customer ActorIdentity sub app.
 */

public interface CryptoCustomerIdentityModuleManager {



    /**
     * The method <code>createCryptoCustomerIdentity</code> is used to create a new crypto Customer identity
     *
     * @param cryptoBrokerName the name of the crypto Customer to create
     * @param profileImage  the profile image of the crypto Customer to create
     * @return the crypto Customer identity generated.
     * @throws CouldNotCreateCryptoCustomerException
     */
    public CryptoCustomerIdentityInformation createCryptoCustomerIdentity(String cryptoBrokerName, byte[] profileImage) throws CouldNotCreateCryptoCustomerException;




    /**
     * The method <code>getAllCryptoCustomerIdentities</code> returns the list of all crypto Customer
     *
     * @return the list of crypto Customer
     * @throws CantGetCryptoCustomerListException
     */
    public List<CryptoCustomerIdentityInformation> getAllCryptoCustomersIdentities(int max, int offset) throws CantGetCryptoCustomerListException;



}