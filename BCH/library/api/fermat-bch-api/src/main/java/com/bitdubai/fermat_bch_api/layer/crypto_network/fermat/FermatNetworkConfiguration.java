package com.bitdubai.fermat_bch_api.layer.crypto_network.fermat;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;


import org.bitcoinj.core.NetworkParameters;

/**
 * Created by rodrigo on 6/22/16.
 */
public interface FermatNetworkConfiguration {
    static final BlockchainNetworkType DEFAULT_NETWORK_TYPE = BlockchainNetworkType.PRODUCTION;
    static final CryptoCurrency CRYPTO_CURRENCY = CryptoCurrency.FERMAT;
    static final int IRREVERSIBLE_BLOCK_DEPTH = 3;
    static final int FIXED_FEE_VALUE = 30000;
}
