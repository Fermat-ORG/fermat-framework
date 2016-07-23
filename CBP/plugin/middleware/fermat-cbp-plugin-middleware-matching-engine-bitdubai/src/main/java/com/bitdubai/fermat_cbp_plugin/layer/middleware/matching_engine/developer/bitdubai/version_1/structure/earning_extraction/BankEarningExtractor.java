package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractor;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by nelsonalfo on 19/04/16.
 */
public class BankEarningExtractor implements EarningExtractor {
    private static final String MEMO = "Transference of earnings from the Broker Wallet";

    final private BankMoneyDestockManager bankMoneyDestockManager;
    private List<CryptoBrokerWalletAssociatedSetting> associatedWallets;

    public BankEarningExtractor(BankMoneyDestockManager bankMoneyDestockManager) {
        this.bankMoneyDestockManager = bankMoneyDestockManager;
    }

    @Override
    public void applyEarningExtraction(EarningsPair earningsPair, float amount, String earningWalletPublicKey, String brokerWalletPublicKey, long fee, FeeOrigin feeOrigin) throws CantExtractEarningsException {
        try {
            final Currency earningCurrency = earningsPair.getEarningCurrency();
            final String accountNumber = getAccountNumber(earningCurrency);

            bankMoneyDestockManager.createTransactionDestock(
                    "Actor",
                    FiatCurrency.getByCode(earningCurrency.getCode()),
                    brokerWalletPublicKey,
                    earningWalletPublicKey,
                    accountNumber,
                    BigDecimal.valueOf(amount),
                    MEMO,
                    BigDecimal.ZERO,
                    OriginTransaction.EARNING_EXTRACTION,
                    earningsPair.getId().toString());

        } catch (InvalidParameterException e) {
            throw new CantExtractEarningsException(e, "Trying to get the earning currency to make the destock",
                    "Verify the currency code or the currency type is correct");

        } catch (CantCreateBankMoneyDestockException e) {
            throw new CantExtractEarningsException(e, "Trying to make the Bank Destock of the merchandise",
                    "Verify the params are correct");
        }
    }

    private String getAccountNumber(Currency merchandiseCurrency) {

        for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
            Platforms platform = associatedWallet.getPlatform();
            Currency currency = associatedWallet.getMerchandise();

            if (platform == Platforms.BANKING_PLATFORM && currency == merchandiseCurrency)
                return associatedWallet.getBankAccount();
        }

        return "";
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.BANKING_PLATFORM;
    }

    @Override
    public void setAssociatedWallets(List<CryptoBrokerWalletAssociatedSetting> associatedWallets) {
        this.associatedWallets = associatedWallets;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EarningExtractor))
            return false;

        EarningExtractor transferApplier = (EarningExtractor) obj;
        return transferApplier.getPlatform() == Platforms.BANKING_PLATFORM;
    }
}


