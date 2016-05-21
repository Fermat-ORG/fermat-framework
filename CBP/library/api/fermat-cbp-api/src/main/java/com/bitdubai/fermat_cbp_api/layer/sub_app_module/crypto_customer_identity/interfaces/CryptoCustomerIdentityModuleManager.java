package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.IdentityCustomerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>CryptoCustomerIdentityModuleManager</code>
 * provides the methods for the Crypto Customer Identity sub app.
 */

public interface CryptoCustomerIdentityModuleManager extends ModuleManager<IdentityCustomerPreferenceSettings, ActiveActorIdentityInformation>,
        ModuleSettingsImpl<IdentityCustomerPreferenceSettings>, Serializable {

    /**
     * The method <code>createCryptoCustomerIdentity</code> is used to create a new crypto Customer identity
     *
     * @param cryptoBrokerName the name of the crypto Customer to create
     * @param profileImage  the profile image of the crypto Customer to create
     * @return the crypto Customer identity generated.
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException
     */
    CryptoCustomerIdentityInformation createCryptoCustomerIdentity(String cryptoBrokerName, byte[] profileImage) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException;

    /**
     *
     * @param cryptoBrokerIdentity
     */
    void updateCryptoCustomerIdentity(CryptoCustomerIdentityInformation cryptoBrokerIdentity) throws CantUpdateCustomerIdentityException;


    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param cryptoCustomerPublicKey the public key of the crypto Broker to publish
     *
     * @throws CantPublishCryptoBrokerException
     */
    void publishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotPublishCryptoCustomerException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param cryptoCustomerPublicKey the public key of the crypto Broker to publish
     *
     * @throws CantPublishCryptoBrokerException
     */
    void unPublishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotUnPublishCryptoCustomerException;

    /**
     * The method <code>getAllCryptoCustomerIdentities</code> returns the list of all crypto Customer
     *
     * @return the list of crypto Customer
     * @throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException
     */
    List<CryptoCustomerIdentityInformation> getAllCryptoCustomersIdentities(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;

}