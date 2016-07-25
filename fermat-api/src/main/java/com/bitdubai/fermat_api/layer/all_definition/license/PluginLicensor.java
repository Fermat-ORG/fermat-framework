package com.bitdubai.fermat_api.layer.all_definition.license;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;

/**
 * Created by ciencias on 21.01.15.
 */
public interface PluginLicensor {

    int getAmountToPay();

    CryptoCurrency getCryptoCurrency();

    String getAddress();

    TimeFrequency getTimePeriod();

}
