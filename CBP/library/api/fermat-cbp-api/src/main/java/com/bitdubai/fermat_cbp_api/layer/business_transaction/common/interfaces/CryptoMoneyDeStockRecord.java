package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;

import java.math.BigDecimal;


/**
 * This class represents the fields necessary to create a Crypto Money DeStock Transaction
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/12/15.
 */
public class CryptoMoneyDeStockRecord extends AbstractDeStockRecord {

    private CryptoCurrency cryptoCurrency;

    private String cryptoWalletPublicKey;

    private BlockchainNetworkType blockchainNetworkType;

    private long fee;

    private FeeOrigin feeOrigin;

    public CryptoMoneyDeStockRecord(
            BusinessTransactionRecord businessTransactionRecord) {

        this.publicKeyActor = businessTransactionRecord.getBrokerPublicKey();
        this.cryptoCurrency = businessTransactionRecord.getCryptoCurrency();
        this.cbpWalletPublicKey = businessTransactionRecord.getCBPWalletPublicKey();
        this.cryptoWalletPublicKey = businessTransactionRecord.getExternalWalletPublicKey();
        this.amount = BigDecimal.valueOf(businessTransactionRecord.getCryptoAmount());
        this.memo = generateMemo(businessTransactionRecord.getContractHash());
        this.priceReference = businessTransactionRecord.getPriceReference();
        this.originTransaction = OriginTransaction.SALE;
        this.blockchainNetworkType = businessTransactionRecord.getBlockchainNetworkType();
        this.fee = businessTransactionRecord.getFee();
        this.feeOrigin = businessTransactionRecord.getFeeOrigin();

    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }


    public String getCryptoWalletPublicKey() {
        return cryptoWalletPublicKey;
    }

    public void setCryptoWalletPublicKey(String cryptoWalletPublicKey) {
        this.cryptoWalletPublicKey = cryptoWalletPublicKey;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public FeeOrigin getFeeOrigin() {
        return feeOrigin;
    }

    public void setFeeOrigin(FeeOrigin feeOrigin) {
        this.feeOrigin = feeOrigin;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CryptoMoneyDeStockRecord{")
                .append("cryptoCurrency=").append(cryptoCurrency)
                .append(", cryptoWalletPublicKey='").append(cryptoWalletPublicKey)
                .append('\'')
                .append(", blockchainNetworkType=").append(blockchainNetworkType)
                .append(", fee=").append(fee)
                .append(", feeOrigin=").append(feeOrigin)
                .append('}').toString();
    }
}
