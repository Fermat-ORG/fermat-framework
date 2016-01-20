package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndex;


/**
 * Created by francisco on 04/12/15.
 */
public class CryptoIndexImp implements CryptoIndex {

    private CryptoCurrency currency;
    private CryptoCurrency referenceCurrency;
    double salePrice;
    double purchasePrice;
    long timestamp;
    String providerDescription;

    public CryptoIndexImp(CryptoCurrency currency, CryptoCurrency referenceCurrency, double salePrice, double purchasePrice, long timestamp, String providerDescription) {
        this.currency = currency;
        this.referenceCurrency = referenceCurrency;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.timestamp = timestamp;
        this.providerDescription = providerDescription;
    }

    @Override
    public CryptoCurrency getCurrency() {
        return currency;
    }

    @Override
    public CryptoCurrency getReferenceCurrency() {
        return referenceCurrency;
    }

    @Override
    public double getSalePrice() {
        return salePrice;
    }

    @Override
    public double getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getProviderDescription() {
        return providerDescription;
    }
}
