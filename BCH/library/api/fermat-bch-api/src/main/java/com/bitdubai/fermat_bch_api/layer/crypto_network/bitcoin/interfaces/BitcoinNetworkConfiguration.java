package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;


/**
 * Created by rodrigo on 9/19/15.
 */
public interface BitcoinNetworkConfiguration {
    static final BlockchainNetworkType DEFAULT_NETWORK_TYPE = BlockchainNetworkType.REG_TEST;
    static final CryptoCurrency CRYPTO_CURRENCY = CryptoCurrency.BITCOIN;
}
