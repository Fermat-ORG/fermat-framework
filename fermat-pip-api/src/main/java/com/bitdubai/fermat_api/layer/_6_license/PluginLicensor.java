package com.bitdubai.fermat_api.layer._6_license;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.TimeFrequency;

/**
 * Created by ciencias on 21.01.15.
 */
public interface PluginLicensor {
    
    public int getAmountToPay();
    public CryptoCurrency getCryptoCurrency();
    public String getAddress();
    public TimeFrequency getTimePeriod();

}
