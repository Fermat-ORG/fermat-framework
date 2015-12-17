package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by franklin on 30/11/15.
 */
public interface CryptoBrokerWalletSettingSpread {
    //TODO: Documentar y manejo de excepciones
    UUID getId();
    void setId(UUID id);

    String getBrokerPublicKey();
    void   setBrokerPublicKey(String brokerPublicKey);

    float getSpread();
    void  setSpread(float spread);

    boolean getRestockAutomatic();
    void    setRestockAutomatic(boolean restockAutomatic);
}
