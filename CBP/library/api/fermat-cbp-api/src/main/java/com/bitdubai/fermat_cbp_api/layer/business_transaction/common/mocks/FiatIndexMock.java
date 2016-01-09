package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;

/**
 * This mock is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/12/15.
 */
public class FiatIndexMock implements FiatIndex {
    @Override
    public FiatCurrency getCurrency() {
        return null;
    }

    @Override
    public FiatCurrency getReferenceCurrency() {
        return null;
    }

    @Override
    public double getSalePrice() {
        return 303;
    }

    @Override
    public double getPurchasePrice() {
        return 300;
    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public String getProviderDescription() {
        return null;
    }
}
