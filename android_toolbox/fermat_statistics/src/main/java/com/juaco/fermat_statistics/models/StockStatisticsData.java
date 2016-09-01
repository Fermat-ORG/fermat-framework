package com.juaco.fermat_statistics.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

//import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;


import java.util.List;

/**
 * Created by Joaquin Carrasquero on 02/03/16.
 */
public class StockStatisticsData {

   // private List<CryptoBrokerStockTransaction> stockTransactions;
    private Currency currency;
    private float balance;


//    public StockStatisticsData(List<CryptoBrokerStockTransaction> stockTransactions, Currency currency, float balance) {
//        this.stockTransactions = stockTransactions;
//        this.currency = currency;
//        this.balance = balance;
//    }



//    public List<CryptoBrokerStockTransaction> getStockTransactions() {
//        return stockTransactions;
//    }

//    public void setStockTransactions(List<CryptoBrokerStockTransaction> stockTransactions) {
//        this.stockTransactions = stockTransactions;
//    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
