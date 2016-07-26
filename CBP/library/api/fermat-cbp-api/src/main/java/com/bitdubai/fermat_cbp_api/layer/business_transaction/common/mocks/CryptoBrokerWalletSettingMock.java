package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantClearCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class CryptoBrokerWalletSettingMock implements CryptoBrokerWalletSetting {
    @Override
    public void saveCryptoBrokerWalletSpreadSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws CantSaveCryptoBrokerWalletSettingException {

    }

    @Override
    public void clearCryptoBrokerWalletSpreadSetting() throws CantClearCryptoBrokerWalletSettingException {

    }

    @Override
    public CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting() throws CantGetCryptoBrokerWalletSettingException {
        return null;
    }

    @Override
    public void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException {

    }

    @Override
    public void clearCryptoBrokerWalletAssociatedSetting(Platforms platform) throws CantClearCryptoBrokerWalletSettingException {

    }

    @Override
    public List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException {
        List<CryptoBrokerWalletAssociatedSetting> cryptoBrokerWalletAssociatedSettingList = new ArrayList<>();
        cryptoBrokerWalletAssociatedSettingList.add(new CryptoBrokerWalletAssociatedSettingMock("CashOnHandWalletPublicKey", MoneyType.CASH_ON_HAND));
        cryptoBrokerWalletAssociatedSettingList.add(new CryptoBrokerWalletAssociatedSettingMock("CryptoWalletPublicKey", MoneyType.CRYPTO));
        cryptoBrokerWalletAssociatedSettingList.add(new CryptoBrokerWalletAssociatedSettingMock("BankWalletPublicKey", MoneyType.BANK));
        cryptoBrokerWalletAssociatedSettingList.add(new CryptoBrokerWalletAssociatedSettingMock("CashWalletPublicKey", MoneyType.CASH_DELIVERY));
        return cryptoBrokerWalletAssociatedSettingList;
    }

    @Override
    public void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws CantSaveCryptoBrokerWalletSettingException {

    }

    @Override
    public void clearCryptoBrokerWalletProviderSetting() throws CantClearCryptoBrokerWalletSettingException {

    }

    @Override
    public List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException {
        return null;
    }
}
