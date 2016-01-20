package com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 11/3/15.
 */
public interface PlatformCryptoVault {
    /**
     * Generates a Crypto Address for the specified Network.
     * @param blockchainNetworkType DEFAULT if null value is passed.
     * @return the newly generated crypto Address.
     */
    CryptoAddress getCryptoAddress(@Nullable BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException;

    /**
     * Gets the serving platform this vault is working on.
     * @return a Fermat platform
     */
    Platforms getPlatform();
}
