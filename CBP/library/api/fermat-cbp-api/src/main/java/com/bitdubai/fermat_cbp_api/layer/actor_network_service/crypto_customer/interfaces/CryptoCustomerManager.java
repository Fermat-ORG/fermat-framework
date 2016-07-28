package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.utils.CryptoCustomerExposingData;

import java.util.Collection;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoCustomerManager</code>
 * provides the methods to interact with the crypto customer actor network service.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 */
public interface CryptoCustomerManager extends FermatManager {

    /**
     * Through the method <code>exposeIdentity</code> we can expose the crypto identities created in the device..
     *
     * @param cryptoCustomerExposingData crypto broker exposing information.
     * @throws CantExposeIdentityException if something goes wrong.
     */
    void exposeIdentity(final CryptoCustomerExposingData cryptoCustomerExposingData) throws CantExposeIdentityException;

    /**
     * Through the method <code>exposeIdentity</code> we can expose the crypto identities created in the device..
     *
     * @param CryptoCustomerExposingData crypto customer exposing information.
     * @throws CantExposeIdentityException if something goes wrong.
     */
    void updateIdentity(CryptoCustomerExposingData CryptoCustomerExposingData) throws CantExposeIdentityException;

    /**
     * Through the method <code>exposeIdentities</code> we can expose the crypto identities created in the device.
     * The information given will be shown to all the crypto customers.
     *
     * @param cryptoCustomerExposingDataList list of crypto broker exposing information.
     * @throws CantExposeIdentitiesException if something goes wrong.
     */
    void exposeIdentities(final Collection<CryptoCustomerExposingData> cryptoCustomerExposingDataList) throws CantExposeIdentitiesException;

    /**
     * Through the method <code>getSearch</code> we can get a new instance of Crypto Customer Search.
     * This Crypto Customer search provides all the necessary functionality to make a Crypto Customer Search.
     *
     * @return a CryptoCustomerCommunitySearch instance.
     */
    CryptoCustomerSearch getSearch();

}
