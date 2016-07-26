package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;


/**
 * This class represents the fields necessary to create a Bank Money DeStock Transaction
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/12/15.
 */
public class BankMoneyDeStockRecord extends AbstractDeStockRecord {

    private FiatCurrency fiatCurrency;

    private String bankWalletPublicKey;

    private String bankAccount;

    public BankMoneyDeStockRecord(BusinessTransactionRecord businessTransactionRecord) {

        this.publicKeyActor = businessTransactionRecord.getBrokerPublicKey();
        this.fiatCurrency = businessTransactionRecord.getFiatCurrency();
        this.cbpWalletPublicKey = businessTransactionRecord.getCBPWalletPublicKey();
        this.bankWalletPublicKey = businessTransactionRecord.getExternalWalletPublicKey();
        this.memo = generateMemo(businessTransactionRecord.getContractHash());
        this.priceReference = businessTransactionRecord.getPriceReference();
        this.originTransaction = OriginTransaction.SALE;

    }

    public FiatCurrency getFiatCurrency() {
        return fiatCurrency;
    }

    public void setFiatCurrency(FiatCurrency fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }

    public String getBankWalletPublicKey() {
        return bankWalletPublicKey;
    }

    public void setBankWalletPublicKey(String bankWalletPublicKey) {
        this.bankWalletPublicKey = bankWalletPublicKey;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("BankMoneyDeStockRecord{")
                .append(abstractToString())
                .append("fiatCurrency=").append(fiatCurrency)
                .append(", bankWalletPublicKey='").append(bankWalletPublicKey)
                .append('\'')
                .append(", bankAccount='").append(bankAccount)
                .append('\'')
                .append(", priceReference=").append(priceReference)
                .append(", originTransaction=").append(originTransaction)
                .append('}').toString();
    }
}
