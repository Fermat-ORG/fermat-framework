package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseCryptoAddressRequest;

/**
 * Created by Yordin Alayn on 28.12.15.
 */
public class CustomerBrokerCloseCryptoAddressRequestImpl implements CustomerBrokerCloseCryptoAddressRequest {

    private Actors identityTypeRequesting;
    private Actors identityTypeResponding;
    private String identityPublicKeyRequesting;
    private String identityPublicKeyResponding;
    private CryptoCurrency cryptoCurrency;
    private BlockchainNetworkType blockchainNetworkType;

    public CustomerBrokerCloseCryptoAddressRequestImpl(
            Actors identityTypeRequesting,
            Actors identityTypeResponding,
            String identityPublicKeyRequesting,
            String identityPublicKeyResponding,
            CryptoCurrency cryptoCurrency,
            BlockchainNetworkType blockchainNetworkType
    ) {
        this.identityTypeRequesting = identityTypeRequesting;
        this.identityTypeResponding = identityTypeResponding;
        this.identityPublicKeyRequesting = identityPublicKeyRequesting;
        this.identityPublicKeyResponding = identityPublicKeyResponding;
        this.cryptoCurrency = cryptoCurrency;
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public Actors getIdentityTypeRequesting() {
        return this.identityTypeRequesting;
    }

    public Actors getIdentityTypeResponding() {
        return this.identityTypeResponding;
    }

    public String getIdentityPublicKeyRequesting() {
        return this.identityPublicKeyRequesting;
    }

    public String getIdentityPublicKeyResponding() {
        return this.identityPublicKeyResponding;
    }

    public CryptoCurrency getCryptoCurrency() {
        return this.cryptoCurrency;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return this.blockchainNetworkType;
    }
}
