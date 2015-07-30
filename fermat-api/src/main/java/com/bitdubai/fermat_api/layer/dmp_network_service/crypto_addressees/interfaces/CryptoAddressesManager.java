package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums.AddressExchangeState;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.exceptions.CantGetCryptoAddessException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces.CryptoAddressesManager</code>
 * provide the methods to manage the exchange of crypto addresses between wallets.
 */
public interface CryptoAddressesManager {

    /**
     *
     * @param walletPublicKey
     * @param referenceWallet
     * @param cryptoAddressSent
     * @param intraUserToContactPublicKey
     * @param intraUserAskingAddressName
     */
    public void exchangeAddresses(String walletPublicKey, ReferenceWallet referenceWallet, CryptoAddress cryptoAddressSent, String intraUserToContactPublicKey, String intraUserAskingAddressName);

    /**
     *
     * @param walletPublicKey
     * @param referenceWallet
     * @param intraUserToContactPublicKey
     * @return
     */
    public AddressExchangeState getCurrentExchangeState(String walletPublicKey, ReferenceWallet referenceWallet, String intraUserToContactPublicKey);

    /**
     *
     * @param walletPublicKey
     * @param referenceWallet
     * @param intraUserToContactPublicKey
     * @return
     */
    public CryptoAddress getReceivedAddress(String walletPublicKey, ReferenceWallet referenceWallet, String intraUserToContactPublicKey) throws CantGetCryptoAddessException;
}
