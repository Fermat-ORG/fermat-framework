package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by franklin on 30/11/15.
 */
public interface CryptoBrokerWalletSettingSpread extends Serializable {
    //TODO: Documentar y manejo de excepciones

    /**
     * The method <code>getId</code> returns the id of the CryptoBrokerWalletSettingSpread
     *
     * @return an UUID of the id of CryptoBrokerWalletSettingSpread
     */
    UUID getId();

    /**
     * The method <code>setId</code> sets the id of the CryptoBrokerWalletSettingSpread
     *
     * @param id
     */
    void setId(UUID id);

    /**
     * The method <code>getBrokerPublicKey</code> returns the broker public key of the CryptoBrokerWalletSettingSpread
     *
     * @return an String of the broker public key
     */
    String getBrokerPublicKey();

    /**
     * The method <code>setBrokerPublicKey</code> sets the broker public key of the CryptoBrokerWalletSettingSpread
     *
     * @param brokerPublicKey
     */
    void setBrokerPublicKey(String brokerPublicKey);

    /**
     * The method <code>getSpread</code> returns the Spread of the CryptoBrokerWalletSettingSpread
     *
     * @return a float of the Spread
     */
    float getSpread();


    /**
     * The method <code>setSpread</code> sets the spread of the CryptoBrokerWalletSettingSpread
     *
     * @param spread
     */
    void setSpread(float spread);

    /**
     * The method <code>getRestockAutomatic</code> returns the Restock Automatic of the CryptoBrokerWalletSettingSpread
     *
     * @return a boolean of the restock automatic
     */
    boolean getRestockAutomatic();

    /**
     * The method <code>setRestockAutomatic</code> sets the restock automatic of the CryptoBrokerWalletSettingSpread
     *
     * @param restockAutomatic
     */
    void setRestockAutomatic(boolean restockAutomatic);
}
