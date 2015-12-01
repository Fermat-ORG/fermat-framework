package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by franklin on 30/11/15.
 */
public interface CryptoBrokerWalletBalanceRecord {
    //TODO: Documentar y excepciones
    String getWalletPublicKey();
    void   setWalletPublicKey(String walletPublicKey);

    String getBrokerPublicKey();
    void   setBrokerPublicKey(String brokerPublicKey);

    FiatCurrency getFiatCurrency();
    void         setFiatCurrency(FiatCurrency fiatCurrency);

    FermatEnum getMerchandise();
    void       setMerchandise(FermatEnum merchandise);

    float getBookBalance();
    void  setBookBalance(float bookBalance);

    float getAvailableBalance();
    void  setAvilableBalance(float bookBalance);
}
