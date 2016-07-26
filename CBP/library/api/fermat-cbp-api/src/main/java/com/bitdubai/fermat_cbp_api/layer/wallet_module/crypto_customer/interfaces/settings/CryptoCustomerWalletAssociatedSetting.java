package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public interface CryptoCustomerWalletAssociatedSetting extends Serializable {
    //TODO: Documentar y manejo de excepciones
    UUID getId();

    void setId(UUID id);

    String getCustomerPublicKey();

    void setCustomerPublicKey(String customerPublicKey);

    Platforms getPlatform();

    void setPlatform(Platforms platform);

    String getWalletPublicKey();

    void setWalletPublicKey(String walletPublicKey);

    FermatEnum getMerchandise();

    void setMerchandise(FermatEnum merchandise);

    MoneyType getMoneyType();

    void setMoneyType(MoneyType moneyType);

    String getBankAccount();

    void setBankAccount(String bankAccount);
}
