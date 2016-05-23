package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.MerchandiseExchangeRate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nelson on 24/11/15.
 */
public interface BrokerIdentityBusinessInfo extends CryptoBrokerIdentity, Serializable {

    /**
     * @return the merchandise Currency the broker want to sell
     */
    FermatEnum getMerchandise();

    /**
     * @return The info about the quotes for the different payment currencies:
     * the currency tu sell (merchandise), the currency to pay, and the price for this pair.
     */
    List<MerchandiseExchangeRate> getQuotes();
}
