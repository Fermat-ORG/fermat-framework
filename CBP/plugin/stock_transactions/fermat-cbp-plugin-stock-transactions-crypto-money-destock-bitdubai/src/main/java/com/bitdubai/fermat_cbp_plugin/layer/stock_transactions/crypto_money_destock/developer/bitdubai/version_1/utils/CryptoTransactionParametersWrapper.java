package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionParameters;

import java.util.UUID;

/**
 * Created by franklin on 18/11/15.
 */
public class CryptoTransactionParametersWrapper implements CryptoHoldTransactionParameters {
    private  UUID            transactionId;
    private  CryptoCurrency  cryptoCurrency;
    private  String          walletPublicKey;
    private  String          publicActorKey;
    private  float           amount;
    private  String          memo;
    private  String          publicKeyPlugin;

    public CryptoTransactionParametersWrapper(UUID transactionId,
                                              CryptoCurrency cryptoCurrency,
                                              String walletPublicKey,
                                              String publicActorKey,
                                              float amount,
                                              String memo,
                                              String publicKeyPlugin
    ){
        this.transactionId   = transactionId;
        this.cryptoCurrency  = cryptoCurrency;
        this.walletPublicKey = walletPublicKey;
        this.publicActorKey  = publicActorKey;
        this.amount          = amount;
        this.memo            = memo;
        this.publicKeyPlugin = publicKeyPlugin;
    }

    public CryptoTransactionParametersWrapper(){};

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String getPublicKeyWallet() {
        return walletPublicKey;
    }

    @Override
    public void setPublicKeyWallet(String publicKeyWallet) {
        this.walletPublicKey = walletPublicKey;
    }

    @Override
    public String getPublicKeyActor() {
        return publicActorKey;
    }

    @Override
    public void setPublicKeyActor(String publicKeyActor) {
        this.publicActorKey = publicKeyActor;
    }

    @Override
    public String getPublicKeyPlugin() {
        return publicKeyPlugin;
    }

    @Override
    public void setPublicKeyPlugin(String publicKeyPlugin) {
        this.publicKeyPlugin = publicKeyPlugin;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public CryptoCurrency getCurrency() {
        return cryptoCurrency;
    }

    @Override
    public void setCurrency(CryptoCurrency currency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
