package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantCreateCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantLoadCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.*;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces.CashMoney;

import java.util.List;

/**
 * Created by francisco on 21/10/15.
 */
public class ImplementCashMoneyManager implements CashMoneyManager {

    /**
     *
     * @return
     * @throws CantTransactionCashMoneyException
     */
    @Override
    public List<CashMoney> getTransactionsCashMoney() throws CantTransactionCashMoneyException {
        return null;
    }

    @Override
    public CashMoney registerCashMoney(String cashTransactionId, String publicKeyActorFrom, String publicKeyActorTo, String status, String balanceType, String transactionType, float amount, String cashCurrencyType, String cashReference, long runningBookBalance, long runningAvailableBalance, long timestamp, String memo) throws CantCreateCashMoneyException {
        return null;
    }

    @Override
    public CashMoney loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyException {
        return null;
    }

    @Override
    public void createCashMoney(String walletPublicKey) throws CantCreateCashMoneyException {

    }
}
