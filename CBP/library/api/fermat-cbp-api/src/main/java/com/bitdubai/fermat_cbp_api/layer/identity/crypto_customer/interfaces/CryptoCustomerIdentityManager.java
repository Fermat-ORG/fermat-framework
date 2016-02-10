package com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantListCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.IdentityNotFoundException;

import java.util.List;

/**
 * Created by jorgegonzalez on 2015.09.15..
 * Modified by Leon Acosta - lnacosta (laion.cj91@gmail.com) on 05/02/2016.
 */
public interface CryptoCustomerIdentityManager extends FermatManager {

    List<CryptoCustomerIdentity> listAllCryptoCustomerFromCurrentDeviceUser() throws CantListCryptoCustomerIdentityException;

    CryptoCustomerIdentity createCryptoCustomerIdentity(final String alias, final byte[] profileImage) throws CantCreateCryptoCustomerIdentityException;
    /**
     *
     * @param alias
     * @param publicKey
     * @param imageProfile
     */
    void updateCryptoCustomerIdentity(String alias, String publicKey, byte[] imageProfile) throws CantUpdateCustomerIdentityException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     *
     * @throws CantPublishIdentityException if something goes wrong.
     * @throws IdentityNotFoundException    if we can't find an identity with the given public key.
     */
    void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Broker identity
     *
     * @param publicKey the public key of the crypto Broker to publish
     *
     * @throws CantHideIdentityException if something goes wrong.
     * @throws IdentityNotFoundException    if we can't find an identity with the given public key.
     */
    void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException;

}
