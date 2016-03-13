package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records.swapbot;

import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyBalancesType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyCurrency;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.TokenlyBalance;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class TokenlyBalanceRecord implements TokenlyBalance {

    private TokenlyBalancesType type;
    private double balance;
    private TokenlyCurrency currencyType;

    /**
     * Constructor with balance type set as CONFIRMED.
     * @param balance
     * @param currencyType
     */
    public TokenlyBalanceRecord(
            double balance,
            TokenlyCurrency currencyType) {
        this.balance = balance;
        this.currencyType = currencyType;
        this.type = TokenlyBalancesType.CONFIRMED;
    }

    /**
     * Constructor with all parameters sets.
     * @param type
     * @param balance
     * @param currencyType
     */
    public TokenlyBalanceRecord(
            TokenlyBalancesType type,
            long balance,
            TokenlyCurrency currencyType) {
        this.type = type;
        this.balance = balance;
        this.currencyType = currencyType;
    }

    /**
     * This method returns the balance type.
     * @return
     */
    @Override
    public TokenlyBalancesType getType() {
        return this.type;
    }

    /**
     * This method sets the TokenlyBalancesType
     * @param tokenlyBalancesType
     */
    @Override
    public void setType(TokenlyBalancesType tokenlyBalancesType) {
        this.type = tokenlyBalancesType;
    }

    /**
     * This method returns the balance.
     * @return
     */
    @Override
    public double getBalance() {
        return 0;
    }

    /**
     * This method returns the currency type.
     * @return
     */
    @Override
    public TokenlyCurrency getCurrencyType() {
        return this.currencyType;
    }

    @Override
    public String toString() {
        return "TokenlyBalanceRecord{" +
                "type=" + type +
                ", balance=" + balance +
                ", currencyType=" + currencyType +
                '}';
    }
}
