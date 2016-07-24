package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionParameters;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by franklin on 18/11/15.
 */
public class CryptoHoldTransactionParametersWrapper implements CryptoHoldTransactionParameters {
    private UUID transactionId;
    private CryptoCurrency cryptoCurrency;
    private String walletPublicKey;
    private String publicActorKey;
    private BigDecimal amount;
    private String memo;
    private String publicKeyPlugin;
    private BlockchainNetworkType blockchainNetworkType;
    //fee values
    private long fee;
    private FeeOrigin feeOrigin;

    public CryptoHoldTransactionParametersWrapper(UUID transactionId,
                                                  CryptoCurrency cryptoCurrency,
                                                  String walletPublicKey,
                                                  String publicActorKey,
                                                  BigDecimal amount,
                                                  String memo,
                                                  String publicKeyPlugin,
                                                  BlockchainNetworkType blockchainNetworkType,
                                                  long fee,
                                                  FeeOrigin feeOrigin
    ) {
        this.transactionId = transactionId;
        this.cryptoCurrency = cryptoCurrency;
        this.walletPublicKey = walletPublicKey;
        this.publicActorKey = publicActorKey;
        this.amount = amount;
        this.memo = memo;
        this.publicKeyPlugin = publicKeyPlugin;
        this.blockchainNetworkType = blockchainNetworkType;
        this.fee = fee;
        this.feeOrigin = feeOrigin;
    }

    public CryptoHoldTransactionParametersWrapper() {
    }

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
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
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

    /**
     * Returns the blockchainNetworkType of the transaction
     *
     * @return BlockchainNetworkType of the transaction
     */
    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    @Override
    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    @Override
    public long getFee() {
        return fee;
    }

    @Override
    public void setFee(long fee) {
        this.fee = fee;
    }

    @Override
    public void setFee(BitcoinFee bitcoinFee) {
        this.fee = bitcoinFee.getFee();
    }

    @Override
    public FeeOrigin getFeeOrigin() {
        return feeOrigin;
    }

    @Override
    public void setFeeOrigin(FeeOrigin feeOrigin) {
        this.feeOrigin = feeOrigin;
    }
}
