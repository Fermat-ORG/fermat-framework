package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;

/**
 * Created by nelson on 24/11/15.
 */
public interface BrokerIdentityBusinessInfo extends CryptoBrokerIdentity {

    FermatEnum getMerchandiseCurrency();
}
