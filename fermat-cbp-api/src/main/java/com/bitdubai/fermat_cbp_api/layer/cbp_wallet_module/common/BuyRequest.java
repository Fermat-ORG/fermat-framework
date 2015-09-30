package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common;

import java.util.UUID;

/**
 * Created by nelson on 29/09/15.
 */
public interface BuyRequest {

    float getMerchandiseAmount();

    void setMerchandiseAmount(float amount);

    String getCustomerName();

    void setCustomerName(String customerName);

    byte[] getCryptoCustomerImage();

    void setCryptoCustomerImage(byte[] imageInBytes);

    UUID getBuyRequestId();
}
