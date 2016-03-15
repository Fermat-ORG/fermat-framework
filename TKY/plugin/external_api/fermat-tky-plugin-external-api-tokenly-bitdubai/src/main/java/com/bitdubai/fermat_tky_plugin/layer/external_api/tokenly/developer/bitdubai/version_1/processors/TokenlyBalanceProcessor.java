package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyBalancesType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyCurrency;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyBalance;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.TokenlyBalanceRecord;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class TokenlyBalanceProcessor extends AbstractTokenlyProcessor {

    /**
     * This method return TokenlyBalance by a given JsonObject
     * @param jsonObject
     * @return
     */
    private static TokenlyBalance getTokenBalanceByJsonObject(
            JsonObject jsonObject){

        //Balance
        String tokenlyCurrencyCode = TokenlyCurrency.BITCOIN.getCode();
        for (TokenlyCurrency tokenlyCurrency : TokenlyCurrency.values()) {
            tokenlyCurrencyCode = tokenlyCurrency.getCode();
            JsonElement jsonElement=jsonObject.get(tokenlyCurrencyCode);
            if(jsonElement!=null){
                break;
            }
        }
        long balance = getLongFromJsonObject(jsonObject, tokenlyCurrencyCode);
        //Create tokenly balance
        try{
            TokenlyBalance tokenlyBalance = new TokenlyBalanceRecord(
                    balance,
                    TokenlyCurrency.getByCode(tokenlyCurrencyCode));
            return tokenlyBalance;
        } catch (InvalidParameterException exception){
            //If I get an error, in theory, cannot be happened, I'll return the balance as BTC
            TokenlyBalance tokenlyBalance = new TokenlyBalanceRecord(
                    balance,
                    TokenlyCurrency.BITCOIN);
            return tokenlyBalance;
        }
    }

    /**
     * This method return TokenlyBalance by a given JsonObject
     * @param jsonObject
     * @return
     */
    private static TokenlyBalance getTokenBalanceByJsonObject(
            JsonObject jsonObject,
            TokenlyBalancesType tokenlyBalancesType){

        Gson gSonProcessor = new Gson();
        //Balance
        String tokenlyCurrencyCode = TokenlyCurrency.BITCOIN.getCode();
        for (TokenlyCurrency tokenlyCurrency : TokenlyCurrency.values()) {
            tokenlyCurrencyCode = tokenlyCurrency.getCode();
            JsonElement jsonElement=jsonObject.get(tokenlyCurrencyCode);
            if(jsonElement!=null){
                break;
            }
        }
        long balance = getLongFromJsonObject(jsonObject, tokenlyCurrencyCode);
        //Create tokenly balance
        try{
            TokenlyBalance tokenlyBalance = new TokenlyBalanceRecord(
                    tokenlyBalancesType,
                    balance,
                    TokenlyCurrency.getByCode(tokenlyCurrencyCode));
            return tokenlyBalance;
        } catch (InvalidParameterException exception){
            //If I get an error, in theory, cannot be happened, I'll return the balance as BTC
            TokenlyBalance tokenlyBalance = new TokenlyBalanceRecord(
                    tokenlyBalancesType,
                    balance,
                    TokenlyCurrency.BITCOIN);
            return tokenlyBalance;
        }
    }

    /**
     * This method returns a Receipt from a JsonObject.
     * @param jsonObject
     * @return
     */
    public static TokenlyBalanceRecord[] getTokenlyBalancesFromJsonObject(
            JsonObject jsonObject){
        //Balance
        double balance;
        int availableTokenlyCurrencySize = TokenlyCurrency.values().length;
        //For loop counter.
        int counter = 0;
        TokenlyBalanceRecord tokenlyBalance;
        TokenlyBalanceRecord[] tokenlyBalanceArray = new TokenlyBalanceRecord[availableTokenlyCurrencySize];
        for (TokenlyCurrency tokenlyCurrency : TokenlyCurrency.values()) {
            //Generic balance
            balance = getDoubleFromJsonObject(
                    jsonObject,
                    tokenlyCurrency.getCode());
            tokenlyBalance = new TokenlyBalanceRecord(
                    balance,
                    tokenlyCurrency);
            tokenlyBalanceArray[counter] = tokenlyBalance;
            counter++;
        }
        return tokenlyBalanceArray;
    }

    public static TokenlyBalance[][] getTokenlyBalancesByType(JsonObject jsonObject){
        int balanceTypesEnumSize = TokenlyBalancesType.values().length;
        //Tokenly balance single array
        TokenlyBalanceRecord[] tokenlyBalanceArray;
        TokenlyBalance[][] tokenlyBalanceArrays = new TokenlyBalance[balanceTypesEnumSize][];
        //For loop counter.
        int counter = 0;
        int innerCounter;
        for (TokenlyBalancesType tokenlyBalancesType : TokenlyBalancesType.values()) {
            tokenlyBalanceArray=getTokenlyBalancesFromJsonObject(
                    jsonObject.getAsJsonObject(
                            tokenlyBalancesType.getCode()));
            innerCounter=0;
            for(TokenlyBalanceRecord tokenlyBalance : tokenlyBalanceArray){
                tokenlyBalance.setType(tokenlyBalancesType);
                tokenlyBalanceArray[innerCounter]=tokenlyBalance;
                innerCounter++;
            }
            tokenlyBalanceArrays[counter]=tokenlyBalanceArray;
            counter++;
        }
        return tokenlyBalanceArrays;
    }

}
