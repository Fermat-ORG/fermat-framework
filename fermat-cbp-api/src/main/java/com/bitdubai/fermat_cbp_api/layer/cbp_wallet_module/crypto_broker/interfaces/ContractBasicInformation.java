package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces;

import java.util.Date;
import java.util.UUID;

/**
 * Created by nelson on 29/09/15.
 */
public interface ContractBasicInformation {

    /**
     * @return the image of the crypto customer has a byte array
     */
    byte[] getCryptoCustomerImage();

    /**
     * @return the crypto customer name (or alias)
     */
    String getCryptoCustomerName();

    /**
     * @return the contract ID
     */
    UUID getContractId();

    /**
     * @return the date of the last update made to the contract
     */
    Date getDateOfLastUpdate();

    /**
     * @return String with the merchandise and the amount of it. Ej: 1.1254 BTC
     */
    String getAmountAndMerchandise();
}
