package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.IdentityBrokerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantHideCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantUnHideCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CryptoBrokerNotFoundException;

import java.io.Serializable;
import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoCustomerIdentityModuleManager</code>
 * provides the methods for the Crypto Broker Identity sub app.
 * <p/>
 * Created by natalia on 16/09/15.
 */
public interface CryptoBrokerIdentityModuleManager extends ModuleManager<IdentityBrokerPreferenceSettings, ActiveActorIdentityInformation>,
        ModuleSettingsImpl<IdentityBrokerPreferenceSettings>, Serializable {

    /**
     * The method <code>createCryptoBrokerIdentity</code> is used to create a new crypto Broker identity
     *
     * @param alias the name of the crypto Broker to create
     * @param image the profile image of the crypto Broker to create
     * @return the crypto broker identity generated.
     * @throws CantCreateCryptoBrokerException if something goes wrong.
     */
    CryptoBrokerIdentityInformation createCryptoBrokerIdentity(String alias, byte[] image, long accuracy, GeoFrequency frequency) throws CantCreateCryptoBrokerException;

    /**
     * @param cryptoBrokerIdentity
     */
    void updateCryptoBrokerIdentity(CryptoBrokerIdentityInformation cryptoBrokerIdentity) throws CantUpdateBrokerIdentityException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     * @throws CantPublishCryptoBrokerException if something goes wrong.
     */
    void publishIdentity(String publicKey) throws CantPublishCryptoBrokerException, CryptoBrokerNotFoundException;

    /**
     * The method <code>unhideidentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     * @throws CantUnHideCryptoBrokerException if something goes wrong.
     */
    void unHideIdentity(String publicKey) throws CantUnHideCryptoBrokerException, CryptoBrokerNotFoundException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     * @throws CantHideCryptoBrokerException if something goes wrong.
     */
    void hideIdentity(String publicKey) throws CantHideCryptoBrokerException, CryptoBrokerNotFoundException;

    /**
     * The method <code>listIdentities</code> returns the list of all crypto Broker published
     *
     * @return the list of crypto Broker published
     * @throws CantListCryptoBrokersException if something goes wrong.
     */
    List<CryptoBrokerIdentityInformation> listIdentities(int max, int offset) throws CantListCryptoBrokersException;

    /**
     * The method <code>getLocation</code> get location coordinates of the user
     *
     * @throws CantGetDeviceLocationException
     */
    Location getLocation() throws CantGetDeviceLocationException;

    /**
     * The method <code>itHasAssociatedWallet</code> It lets you know if an identity has an associated broker wallet
     *
     * @param brokerPublicKey
     * @return
     */
    Boolean itHasAssociatedWallet(String brokerPublicKey);
}