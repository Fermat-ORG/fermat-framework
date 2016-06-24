package com.bitdubai.fermat_ccp_api.all_definition;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by natalia on 19/05/16.
 */
public interface ExchangeRateProvider extends Serializable {

    UUID  getProviderId();

    String getProviderName();

}
