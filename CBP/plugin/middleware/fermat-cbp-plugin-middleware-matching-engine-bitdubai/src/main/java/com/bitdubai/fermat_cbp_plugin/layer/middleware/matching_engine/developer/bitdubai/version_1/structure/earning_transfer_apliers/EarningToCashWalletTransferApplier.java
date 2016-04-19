package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_transfer_apliers;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningToWalletTransferApplier;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.math.BigDecimal;
import java.util.List;


public class EarningToCashWalletTransferApplier implements EarningToWalletTransferApplier {
    final private CashMoneyDestockManager cashMoneyDestockManager;

    public EarningToCashWalletTransferApplier(CashMoneyDestockManager cashMoneyDestockManager) {
        this.cashMoneyDestockManager = cashMoneyDestockManager;
    }

    @Override
    public void applyTransference(EarningsPair earningsPair, EarningTransaction earningTransaction, String earningWalletPublicKey, String brokerWalletPublicKey) throws CantTransferEarningsToWalletException {
        try {
            cashMoneyDestockManager.createTransactionDestock(
                    "Actor",
                    FiatCurrency.getByCode(earningsPair.getEarningCurrency().getCode()),
                    brokerWalletPublicKey,
                    earningWalletPublicKey,
                    "Cash Reference",
                    BigDecimal.valueOf(earningTransaction.getAmount()),
                    "Transference of Earnings from the Broker Wallet",
                    BigDecimal.ZERO,
                    OriginTransaction.EARNING_EXTRACTION,
                    earningTransaction.getId().toString());

        } catch (CantCreateCashMoneyDestockException e) {
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", e,
                    "Trying to make the Cash Destock of the merchandise", "Verify the params are correct");

        } catch (InvalidParameterException e) {
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", e,
                    "Trying to get the earning currency to make the destock", "Verify the currency code or the currency type is correct");
        }
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CASH_PLATFORM;
    }

    @Override
    public void setAssociatedWallets(List<CryptoBrokerWalletAssociatedSetting> associatedWallets) {

    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EarningToWalletTransferApplier))
            return false;

        EarningToWalletTransferApplier transferApplier = (EarningToWalletTransferApplier) obj;

        return transferApplier.getPlatform() == Platforms.CASH_PLATFORM;
    }
}
