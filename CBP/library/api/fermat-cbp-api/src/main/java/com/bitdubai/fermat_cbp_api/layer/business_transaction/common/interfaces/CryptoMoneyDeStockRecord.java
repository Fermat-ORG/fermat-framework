package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;

/**
 * This class represents the fields necessary to create a Crypto Money DeStock Transaction
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/12/15.
 */
public class CryptoMoneyDeStockRecord extends AbstractDeStockRecord {

    private CryptoCurrency cryptoCurrency;

    private String cryWalletPublicKey;

    public CryptoMoneyDeStockRecord(
            BusinessTransactionRecord businessTransactionRecord){

        this.publicKeyActor=businessTransactionRecord.getBrokerPublicKey();
        //For this version, the crypto currency is set to BITCOIN
        this.cryptoCurrency=CryptoCurrency.BITCOIN;
        this.cbpWalletPublicKey=businessTransactionRecord.getCBPWalletPublicKey();
        this.cryWalletPublicKey=businessTransactionRecord.getExternalWalletPublicKey();
        this.amount=parseLongToBigDecimal(
                businessTransactionRecord.getCryptoAmount());
        this.memo=generateMemo(businessTransactionRecord.getContractHash());
        this.priceReference=businessTransactionRecord.getPriceReference();
        this.originTransaction=OriginTransaction.STOCK_INITIAL;

    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }


    public String getCryWalletPublicKey() {
        return cryWalletPublicKey;
    }

    public void setCryWalletPublicKey(String cryWalletPublicKey) {
        this.cryWalletPublicKey = cryWalletPublicKey;
    }

    @Override
    public String toString() {
        return "CryptoMoneyDeStockRecord{" +
                abstractToString()+
                "cryptoCurrency=" + cryptoCurrency +
                ", cryWalletPublicKey='" + cryWalletPublicKey + '\'' +
                '}';
    }
}
