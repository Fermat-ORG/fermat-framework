package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;

import java.util.UUID;

/**
 * Created by franklin on 31/12/15.
 */
public class CryptoCustomerWalletAssociatedSettingImpl implements CryptoCustomerWalletAssociatedSetting {

    UUID id;
    String customerPublicKey;
    Platforms platforms;
    String walletPublicKey;
    FermatEnum merchandise;
    String bankAccount;
    MoneyType moneyType;

    public CryptoCustomerWalletAssociatedSettingImpl() {
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getCustomerPublicKey() {
        return customerPublicKey;
    }

    @Override
    public void setCustomerPublicKey(String customerPublicKey) {
        this.customerPublicKey = customerPublicKey;
    }

    @Override
    public Platforms getPlatform() {
        return platforms;
    }

    @Override
    public void setPlatform(Platforms platform) {
        this.platforms = platform;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    @Override
    public FermatEnum getMerchandise() {
        return merchandise;
    }

    @Override
    public void setMerchandise(FermatEnum merchandise) {
        this.merchandise = merchandise;
    }

    @Override
    public MoneyType getMoneyType() {
        return moneyType;
    }

    @Override
    public void setMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }

    @Override
    public String getBankAccount() {
        return bankAccount;
    }

    @Override
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
