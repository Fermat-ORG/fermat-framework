package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 17/11/15.
 */
public class CryptoMoneyRestockTransactionImpl implements CryptoMoneyTransaction {
    UUID transactionId;
    String actorPublicKey;
    CryptoCurrency cryptoCurrency;
    String cbpWalletPublicKey;
    String cryWalletPublicKey;
    String memo;
    String concept;
    BigDecimal amount;
    Timestamp timeStamp;
    TransactionStatusRestockDestock transactionStatus;
    BigDecimal priceReference;
    OriginTransaction originTransaction;
    String originTransactionId;
    BlockchainNetworkType blockchainNetworkType;
    //fee values
    private long fee;
    private FeeOrigin feeOrigin;

    public CryptoMoneyRestockTransactionImpl() {

    }

    public CryptoMoneyRestockTransactionImpl(UUID transactionId,
                                             String actorPublicKey,
                                             CryptoCurrency cryptoCurrency,
                                             String cbpWalletPublicKey,
                                             String cryWalletPublicKey,
                                             String memo,
                                             String concept,
                                             BigDecimal amount,
                                             Timestamp timeStamp,
                                             TransactionStatusRestockDestock transactionStatus,
                                             BigDecimal priceReference,
                                             OriginTransaction originTransaction,
                                             String originTransactionId,
                                             BlockchainNetworkType blockchainNetworkType,
                                             long fee,
                                             FeeOrigin feeOrigin) {
        this.transactionId = transactionId;
        this.actorPublicKey = actorPublicKey;
        this.cryptoCurrency = cryptoCurrency;
        this.cbpWalletPublicKey = cbpWalletPublicKey;
        this.cryWalletPublicKey = cryWalletPublicKey;
        this.memo = memo;
        this.concept = concept;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.transactionStatus = transactionStatus;
        this.priceReference = priceReference;
        this.originTransaction = originTransaction;
        this.originTransactionId = originTransactionId;
        this.blockchainNetworkType = blockchainNetworkType;
        this.fee = fee;
        this.feeOrigin = feeOrigin;
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
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    @Override
    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    @Override
    public String getCbpWalletPublicKey() {
        return cbpWalletPublicKey;
    }

    @Override
    public void setCbpWalletPublicKey(String cbpWalletPublicKey) {
        this.cbpWalletPublicKey = cbpWalletPublicKey;
    }

    @Override
    public String getCryWalletPublicKey() {
        return this.cryWalletPublicKey;
    }

    @Override
    public void setCryWalletPublicKey(String cryWalletPublicKey) {
        this.cryWalletPublicKey = cryWalletPublicKey;
    }

    @Override
    public String getConcept() {
        return concept;
    }

    @Override
    public void setConcept(String concept) {
        this.concept = concept;
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
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public TransactionStatusRestockDestock getTransactionStatus() {
        return transactionStatus;
    }

    @Override
    public void setTransactionStatus(TransactionStatusRestockDestock transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public BigDecimal getPriceReference() {
        return priceReference;
    }

    @Override
    public void setPriceReference(BigDecimal priceReference) {
        this.priceReference = priceReference;
    }

    @Override
    public OriginTransaction getOriginTransaction() {
        return originTransaction;
    }

    @Override
    public void setOriginTransaction(OriginTransaction originTransaction) {
        this.originTransaction = originTransaction;
    }

    /**
     * The property <code>OriginTransactionId</code>  represented the Origin Transaction
     *
     * @return the String
     */
    @Override
    public String getOriginTransactionId() {
        return this.originTransactionId;
    }

    @Override
    public void setOriginTransactionId(String originTransactionId) {
        this.originTransactionId = originTransactionId;
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
