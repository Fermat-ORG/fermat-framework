package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_transfer_apliers;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningToWalletTransferApplier;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;

import java.math.BigDecimal;
import java.util.List;


public class EarningToCryptoWalletTransferApplier implements EarningToWalletTransferApplier {
    final private CryptoMoneyDestockManager cryptoMoneyDestockManager;

    public EarningToCryptoWalletTransferApplier(CryptoMoneyDestockManager cryptoMoneyDestockManager) {
        this.cryptoMoneyDestockManager = cryptoMoneyDestockManager;
    }

    @Override
    public void applyTransference(EarningsPair earningsPair, EarningTransaction earningTransaction, String earningWalletPublicKey, String brokerWalletPublicKey) throws CantTransferEarningsToWalletException {
        try {
            cryptoMoneyDestockManager.createTransactionDestock(
                    "Actor",
                    CryptoCurrency.getByCode(earningsPair.getEarningCurrency().getCode()),
                    brokerWalletPublicKey,
                    earningWalletPublicKey,
                    BigDecimal.valueOf(earningTransaction.getAmount()),
                    "Transference of Earnings from the Broker Wallet",
                    BigDecimal.ZERO,
                    OriginTransaction.EARNING_EXTRACTION,
                    earningTransaction.getId().toString(),
                    BlockchainNetworkType.getDefaultBlockchainNetworkType());

        } catch (CantCreateCryptoMoneyDestockException e) {
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", e,
                    "Trying to make the Crypto Destock of the merchandise", "Verify the params are correct");

        } catch (InvalidParameterException e) {
            throw new CantTransferEarningsToWalletException("Cant Transfer the earnings to the Earning Wallet", e,
                    "Trying to get the earning currency to make the destock", "Verify the currency code or the currency type is correct");
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
        if (!(obj instanceof EarningToWalletTransferApplier))
            return false;

        EarningToWalletTransferApplier transferApplier = (EarningToWalletTransferApplier) obj;

        return transferApplier.getPlatform() == Platforms.CRYPTO_CURRENCY_PLATFORM;
    }
}
