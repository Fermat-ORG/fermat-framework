package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotUnPublishCryptoBrokerException;

import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoCustomerIdentityModuleManager</code>
 * provides the methods for the Crypto Broker Identity sub app.
 */

public interface CryptoBrokerIdentityModuleManager extends ModuleManager {

    /**
     * The method <code>createCryptoBrokerIdentity</code> is used to create a new crypto Broker identity
     *
     * @param cryptoBrokerName the name of the crypto Broker to create
     * @param profileImage  the profile image of the crypto Broker to create
     * @return the crypto broker identity generated.
     * @throws CouldNotCreateCryptoBrokerException
     */
    public CryptoBrokerIdentityInformation createCryptoBrokerIdentity(String cryptoBrokerName, byte[] profileImage) throws CouldNotCreateCryptoBrokerException;


    /**
     * The method <code>publishCryptoBrokerIdentity</code> is used to publish a Broker identity
     *
     * @param cryptoBrokerPublicKey the public key of the crypto Broker to publish
     *
     * @throws CouldNotPublishCryptoBrokerException
     */
    public void publishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotPublishCryptoBrokerException;

    /**
     * The method <code>publishCryptoBrokerIdentity</code> is used to publish a Broker identity
     *
     * @param cryptoBrokerPublicKey the public key of the crypto Broker to publish
     *
     * @throws CouldNotPublishCryptoBrokerException
     */
    public void unPublishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotUnPublishCryptoBrokerException;

    /**
     * The method <code>getAllCryptoBrokersIdentities</code> returns the list of all crypto Broker published
     *
     * @return the list of crypto Broker published
     * @throws CantGetCryptoBrokerListException
     */
    public List<CryptoBrokerIdentityInformation> getAllCryptoBrokersIdentities(int max, int offset) throws CantGetCryptoBrokerListException;



}