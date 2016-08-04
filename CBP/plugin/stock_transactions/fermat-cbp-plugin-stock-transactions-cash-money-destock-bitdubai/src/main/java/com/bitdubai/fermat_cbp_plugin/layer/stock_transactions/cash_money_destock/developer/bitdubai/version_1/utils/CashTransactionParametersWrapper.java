package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionParameters;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by franklin on 18/11/15.
 */
public class CashTransactionParametersWrapper implements CashUnholdTransactionParameters {
    private final UUID transactionId;
    private final FiatCurrency fiatCurrency;
    private final String walletPublicKey;
    private final String publicActorKey;
    private final BigDecimal amount;
    private final String memo;
    private final String publicKeyPlugin;

    public CashTransactionParametersWrapper(UUID transactionId,
                                            FiatCurrency fiatCurrency,
                                            String walletPublicKey,
                                            String publicActorKey,
                                            BigDecimal amount,
                                            String memo,
                                            String publicKeyPlugin
    ) {
        this.transactionId = transactionId;
        this.fiatCurrency = fiatCurrency;
        this.walletPublicKey = walletPublicKey;
        this.publicActorKey = publicActorKey;
        this.amount = amount;
        this.memo = memo;
        this.publicKeyPlugin = publicKeyPlugin;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public String getPublicKeyPlugin() {
        return publicKeyPlugin;
    }

    @Override
    public String getPublicKeyWallet() {
        return walletPublicKey;
    }

    @Override
    public String getPublicKeyActor() {
        return publicActorKey;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public FiatCurrency getCurrency() {
        return fiatCurrency;
    }

    @Override
    public String getMemo() {
        return memo;
    }

}
