package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractor;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.math.BigDecimal;
import java.util.List;


public class CryptoEarningExtractor implements EarningExtractor {
    private static final String MEMO = "Earnings transference from the Broker Wallet";

    private final CryptoMoneyDestockManager cryptoMoneyDestockManager;


    public CryptoEarningExtractor(CryptoMoneyDestockManager cryptoMoneyDestockManager) {
        this.cryptoMoneyDestockManager = cryptoMoneyDestockManager;
    }

    @Override
    public void applyEarningExtraction(EarningsPair earningsPair, float amount, String earningWalletPublicKey, String brokerWalletPublicKey, long fee, FeeOrigin feeOrigin) throws CantExtractEarningsException {
        try {
            cryptoMoneyDestockManager.createTransactionDestock(
                    "Actor",
                    CryptoCurrency.getByCode(earningsPair.getEarningCurrency().getCode()),
                    brokerWalletPublicKey,
                    earningWalletPublicKey,
                    BigDecimal.valueOf(amount),
                    MEMO,
                    BigDecimal.ZERO,
                    OriginTransaction.EARNING_EXTRACTION,
                    earningsPair.getId().toString(),
                    BlockchainNetworkType.getDefaultBlockchainNetworkType(), fee, feeOrigin);

        } catch (CantCreateCryptoMoneyDestockException e) {
            throw new CantExtractEarningsException(e, "Trying to make the Crypto Destock of the merchandise",
                    "Verify the params are correct");

        } catch (InvalidParameterException e) {
            throw new CantExtractEarningsException(e, "Trying to get the earning currency to make the destock",
                    "Verify the currency code or the currency type is correct");
        }
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

    @Override
    public void setAssociatedWallets(List<CryptoBrokerWalletAssociatedSetting> associatedWallets) {
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EarningExtractor))
            return false;

        EarningExtractor transferApplier = (EarningExtractor) obj;
        return transferApplier.getPlatform() == Platforms.CRYPTO_CURRENCY_PLATFORM;
    }
}
