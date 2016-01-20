package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;

import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public interface CryptoBrokerWalletAssociatedSetting {
    //TODO: Documentar y manejo de excepciones
    UUID getId();
    void setId(UUID id);

    String getBrokerPublicKey();
    void   setBrokerPublicKey(String brokerPublicKey);

    Platforms getPlatform();
    void setPlatform(Platforms platform);

    String getWalletPublicKey();
    void setWalletPublicKey(String walletPublicKey);

    FermatEnum getMerchandise();
    void setMerchandise(FermatEnum merchandise);

    CurrencyType getCurrencyType();
    void setCurrencyType(CurrencyType currencyType);

    String getBankAccount();
    void setBankAccount(String bankAccount);
}
