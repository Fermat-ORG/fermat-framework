package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by nelson on 29/09/15.
 */
public interface NegotiationBasicInformation {

    /**
     * @return the amount of merchandise the customer want to buy
     */
    float getAmount();

    /**
     * @return the crypto customer name (or alias)
     */
    String getCryptoCustomerAlias();

    /**
     * @return the image of the crypto customer has a byte array
     */
    byte[] getCryptoCustomerImage();

    /**
     * @return the exchange rate amount for the merchandise
     */
    float getExchangeRateAmount();

    /**
     * @return the date of the negotiation's last update in long format
     */
    long getLastUpdate();

    /**
     * @return the merchandise to buy
     */
    String getMerchandise();

    /**
     * @return the contract ID
     */
    UUID getNegotiationId();

    /**
     * @return the payment currency
     */
    String getPaymentCurrency();

    /**
     * @return the type of payment in a human readable way
     */
    String getTypeOfPayment();

    /**
     * @return the negotiation's status
     */
    NegotiationStatus getStatus();
}
