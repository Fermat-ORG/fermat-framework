package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;

import java.util.UUID;

/**
 * Created by nelson on 29/09/15.
 */
public interface ContractBasicInformation {

    /**
     * @return the amount of merchandise the customer want to buy
     */
    float getAmount();

    /**
     * @return the contract ID
     */
    UUID getContractId();

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
     * @return the payment currency
     */
    String getPaymentCurrency();

    /**
     * @return the contract status
     */
    ContractStatus getStatus();

    /**
     * @return the type of payment in a human readable way
     */
    String getTypeOfPayment();
}
