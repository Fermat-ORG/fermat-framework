package com.bitdubai.wallet_platform_api.layer._1_definition.license;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.TimeFrequency;

/**
 * Created by ciencias on 21.01.15.
 */
public interface Licensor {
    
    public int getAmountToPay();
    public CryptoCurrency getCryptoCurrency();
    public String getAddress();
    public TimeFrequency getTimePeriod();

}
