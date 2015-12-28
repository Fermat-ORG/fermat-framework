package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

/**
 * Created by yordin on 28/12/15.
 */
public interface CustomerBrokerCloseCryptoAddressRequest {

    String getWalletPublicKey();

    Actors getIdentityTypeRequesting();

    Actors getIdentityTypeResponding();

    String getIdentityPublicKeyRequesting();

    String getIdentityPublicKeyResponding();

    CryptoCurrency getCryptoCurrency();

    BlockchainNetworkType getBlockchainNetworkType();
}
