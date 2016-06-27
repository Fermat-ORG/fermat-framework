package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_ccp_api.all_definition.ExchangeRateProvider;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by natalia on 19/05/16.
 */
public class LossProtectedExchangeRateProvider implements ExchangeRateProvider, Serializable {

    private UUID providerId;
    private String providerName;
    public LossProtectedExchangeRateProvider (UUID providerId,String providerName)
    {
        this.providerId = providerId;
        this.providerName  = providerName;
    }
    @Override
    public UUID getProviderId() {
        return this.providerId;
    }

    @Override
    public String getProviderName() {
        return this.providerName;
    }
}
