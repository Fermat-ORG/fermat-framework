package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces.HoldManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces.UnholdManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankingWallet;

import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 08/12/15.
 */
public class BankingWalletModuleImpl implements BankingWallet {

    private final BankMoneyWalletManager bankMoneyWalletManager;
    private final DepositManager depositManager;
    private final WithdrawManager withdrawManager;
    private final HoldManager holdManager;
    private final UnholdManager unholdManager;

    private UUID publicKey = UUID.fromString("banking_wallet");

    public BankingWalletModuleImpl(BankMoneyWalletManager bankMoneyWalletManager, DepositManager depositManager, WithdrawManager withdrawManager, HoldManager holdManager, UnholdManager unholdManager) {
        this.bankMoneyWalletManager = bankMoneyWalletManager;
        this.depositManager = depositManager;
        this.withdrawManager = withdrawManager;
        this.holdManager = holdManager;
        this.unholdManager = unholdManager;
    }

    public List<BankAccountNumber> getAccounts()throws CantLoadBankMoneyWalletException{
        return bankMoneyWalletManager.loadBankMoneyWallet(publicKey.toString()).getAccounts();
    }

}
