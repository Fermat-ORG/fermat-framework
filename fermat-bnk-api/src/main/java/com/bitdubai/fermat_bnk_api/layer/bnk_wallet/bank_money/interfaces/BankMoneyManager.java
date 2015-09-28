package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCreateBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetBankMoneyException;

import java.util.List;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface BankMoneyManager {

    List<BankMoney> getAllBankMoneyFromCurrentDeviceUser() throws CantGetBankMoneyException;

    BankMoney createBankMoney(
         final String publicKeyBroker
        ,final String walletId
    ) throws CantCreateBankMoneyException;
}
