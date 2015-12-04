package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;

/**
 * Created by franklin on 30/11/15.
 */
public interface CryptoBrokerWalletSetting {
    //TODO: Documentar y manejo de excepciones
    void saveCryptoBrokerWalletSetting() throws CantSaveCryptoBrokerWalletSettingException;
    CryptoBrokerWalletSettingRecord getCryptoBrokerWalletSetting();
}
