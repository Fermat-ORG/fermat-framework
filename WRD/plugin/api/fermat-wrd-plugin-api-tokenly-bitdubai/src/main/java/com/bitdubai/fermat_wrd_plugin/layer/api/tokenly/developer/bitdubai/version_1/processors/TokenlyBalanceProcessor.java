package com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.processors;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_wrd_api.all_definitions.enums.TokenlyBalancesType;
import com.bitdubai.fermat_wrd_api.all_definitions.enums.TokenlyCurrency;
import com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces.TokenlyBalance;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.config.TokenlyBotJSonAttNames;
import com.bitdubai.fermat_wrd_plugin.layer.api.tokenly.developer.bitdubai.version_1.records.TokenlyBalanceRecord;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class TokenlyBalanceProcessor extends AbstractTokenlyProcessor {

    /*public static TokenlyBalance[] getTokenlyBalancesByJsonObject(JsonObject jsonObject){
        JsonElement jsonElement = jsonObject.get(TokenlyBotJSonAttNames.BALANCES);
        JsonObject jsonObjectArray = jsonElement.getAsJsonObject();
        TokenlyBalance[] tokenlyBalances = new TokenlyBalance[TokenlyCurrency.values().length];
        int counter = 0;
        for (TokenlyCurrency tokenlyCurrency : TokenlyCurrency.values()) {
            JsonElement jsonElementFromArray = jsonObjectArray.get(tokenlyCurrency.getCode());
            if(jsonElementFromArray==null){
                continue;
            }
            JsonObject jsonObjectFromArray = jsonElementFromArray.getAsJsonObject();
            tokenlyBalances[counter] = getTokenBalanceByJsonObject(
                    jsonObjectFromArray);
            counter++;
        }
        return tokenlyBalances;
    }

    /*
    public static TokenlyBalance[][] getTokenlyBalancesByType(JsonObject jsonObject){
        JsonElement jsonElement = jsonObject.get(TokenlyBotJSonAttNames.ALL_BALANCES_BY_TYPE);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        TokenlyBalance[][] tokenlyBalances = new TokenlyBalance[jsonArray.size()][];
        int counter = 0;
        String tokenlyBalanceCode;
        for (JsonElement jSonElementArray : jsonArray) {
            JsonObject jsonObjectFromArray = jSonElementArray.getAsJsonObject();
            for (TokenlyBalancesType tokenlyBalance : TokenlyBalancesType.values()) {
                tokenlyBalanceCode = tokenlyBalance.getCode();
                JsonElement jsonElementByBalance = jsonObjectFromArray.get(tokenlyBalanceCode);
                if(jsonElementByBalance!=null){
                    JsonArray jsonArrayByBalance = jsonElementByBalance.getAsJsonArray();
                    int innerCounter = 0;
                    TokenlyBalance[] innerTokenlyBalances = new TokenlyBalance[jsonArrayByBalance.size()];
                    for (JsonElement innerJSonElementArray : jsonArrayByBalance) {
                        JsonObject innerJsonObjectFromArray = innerJSonElementArray.getAsJsonObject();
                        innerTokenlyBalances[innerCounter] = getTokenBalanceByJsonObject(
                                innerJsonObjectFromArray,
                                tokenlyBalance);
                        innerCounter++;
                    }
                    tokenlyBalances[counter] = innerTokenlyBalances;
                    break;
                }
            }
            counter++;
        }
        return tokenlyBalances;
    }*/

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
