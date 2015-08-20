package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

/**
 * Created by francisco on 13/08/15.
 */
public class BterServiceAPI {

    public String manyTradingPairs;
    public String marketInfo;
    public String marketDetails;
    public String manyTickers;
    public String ticker;
    public String historical;


//Return all the trading pairs supported by Bter
    public String getManyTradingPairs() {
        manyTradingPairs="http://data.bter.com/api/1/pairs";
        return manyTradingPairs;
    }
//Returns all markets' fee, minimum order total amount, price decimal places.
    public String getMarketInfo() {
        marketInfo="http://data.bter.com/api/1/marketinfo";
        return marketInfo;
    }
//Returns market details including pair, coin name, coin symbol, last price, trading volume, coin supply, coin market cap, price trend, etc.
    public String getMarketDetails() {
        marketDetails="http://data.bter.com/api/1/marketlist";
        return marketDetails;
    }
//returns the tickers for all the supported trading pairs at once, cached in 10 seconds:
    public String getManyTickers() {
        manyTickers="http://data.bter.com/api/1/tickers";
        return manyTickers;
    }
//returns the current ticker for the selected currency, cached in 10 seconds:
//    URL: http://data.bter.com/api/1/ticker/[CURR_A]_[CURR_B]
//Replace [CURR_A] and [CURR_B] with the selected currencies.
    public String getTicker() {
        ticker="http://data.bter.com/api/1/tickers";
        return ticker;
    }

    //Return at most 1,000 trade history records after
    //Example: http://data.bter.com/api/1/trade/[CURR_A]_[CURR_B]/[TID]
    //Replace [CURR_A] and [CURR_B] with the selected currencies.
    public String getHistorical() {
        historical="http://data.bter.com/api/1/trade/";
        return historical;
    }




}