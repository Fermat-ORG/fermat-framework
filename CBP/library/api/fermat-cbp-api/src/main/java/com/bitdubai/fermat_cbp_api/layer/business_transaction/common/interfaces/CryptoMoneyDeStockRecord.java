package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;

/**
 * This class represents the fields necessary to create a Crypto Money DeStock Transaction
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/12/15.
 */
public class CryptoMoneyDeStockRecord extends AbstractDeStockRecord {

    private CryptoCurrency cryptoCurrency;

    private String cryptoWalletPublicKey;

    private BlockchainNetworkType blockchainNetworkType;

    public CryptoMoneyDeStockRecord(
            BusinessTransactionRecord businessTransactionRecord){

        this.publicKeyActor = businessTransactionRecord.getBrokerPublicKey();
        //For this version, the crypto currency is set to BITCOIN
        this.cryptoCurrency = CryptoCurrency.BITCOIN;
        this.cbpWalletPublicKey = businessTransactionRecord.getCBPWalletPublicKey();
        this.cryptoWalletPublicKey = businessTransactionRecord.getExternalWalletPublicKey();
        this.amount = parseLongToBigDecimal(
                businessTransactionRecord.getCryptoAmount());
        this.memo=generateMemo(businessTransactionRecord.getContractHash());
        this.priceReference = businessTransactionRecord.getPriceReference();
        this.originTransaction = OriginTransaction.SALE;
        this.blockchainNetworkType = businessTransactionRecord.getBlockchainNetworkType();

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

    @Override
    public String toString() {
        return "CryptoMoneyDeStockRecord{" +
                "cryptoCurrency=" + cryptoCurrency +
                ", cryptoWalletPublicKey='" + cryptoWalletPublicKey + '\'' +
                ", blockchainNetworkType=" + blockchainNetworkType +
                '}';
    }
}
