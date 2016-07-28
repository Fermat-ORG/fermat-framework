package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantCreateCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantGetCryptoBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUnHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CryptoBrokerIdentityAlreadyExistsException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.IdentityNotFoundException;

import java.util.List;

/**
 * Created by jorgegonzalez on 2015.09.15..
 * Updated by lnacosta (laion.cj91@gmail.com) on 25/11/2015.
 */
public interface CryptoBrokerIdentityManager extends FermatManager {

    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the crypto broker identities
     * linked to the current logged device user.
     *
     * @return a list of instance of the crypto broker identities linked to the current logged device user.
     * @throws CantListCryptoBrokerIdentitiesException if something goes wrong.
     */
    List<CryptoBrokerIdentity> listIdentitiesFromCurrentDeviceUser() throws CantListCryptoBrokerIdentitiesException;

    /**
     * Through the method <code>createCryptoBrokerIdentity</code> you can create a new crypto broker identity.
     *
     * @param alias the alias of the crypto broker that we want to create.
     * @param image an image that represents the crypto broker. it will be shown to other actors when they try to connect.
     * @return an instance of the recent created crypto broker identity.
     * @throws CantCreateCryptoBrokerIdentityException if something goes wrong.
     */
    CryptoBrokerIdentity createCryptoBrokerIdentity(final String alias,
                                                    final byte[] image,
                                                    long accuracy,
                                                    GeoFrequency frequency) throws CantCreateCryptoBrokerIdentityException,
            CryptoBrokerIdentityAlreadyExistsException;

    /**
     * @param alias
     * @param publicKey
     * @param imageProfile
     */
    void updateCryptoBrokerIdentity(String alias, String publicKey, byte[] imageProfile,
                                    long accuracy,
                                    GeoFrequency frequency) throws CantUpdateBrokerIdentityException;

    /**
     * This method updates the crypto broker identity stored in database plugin.
     *
     * @param cryptoBrokerIdentity
     * @throws CantUpdateBrokerIdentityException
     */
    void updateCryptoBrokerIdentity(CryptoBrokerIdentity cryptoBrokerIdentity)
            throws CantUpdateBrokerIdentityException;

    CryptoBrokerIdentity getCryptoBrokerIdentity(String publicKey) throws CantGetCryptoBrokerIdentityException, IdentityNotFoundException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     * @throws CantPublishIdentityException if something goes wrong.
     * @throws IdentityNotFoundException    if we can't find an identity with the given public key.
     */
    void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException;


    /**
     * The method <code>UnHideIdentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     * @throws CantUnHideIdentityException if something goes wrong.
     * @throws IdentityNotFoundException   if we can't find an identity with the given public key.
     */
    void unHideIdentity(String publicKey) throws CantUnHideIdentityException, IdentityNotFoundException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     * @throws CantHideIdentityException if something goes wrong.
     * @throws IdentityNotFoundException if we can't find an identity with the given public key.
     */
    void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException;

}
