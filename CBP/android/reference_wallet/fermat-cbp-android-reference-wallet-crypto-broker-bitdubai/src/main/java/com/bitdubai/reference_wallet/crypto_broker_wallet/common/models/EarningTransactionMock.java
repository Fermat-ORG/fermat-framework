package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantMarkEarningTransactionAsExtractedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningTransactionNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;

import java.util.UUID;


/**
 * Created by nelsonalfo on 26/07/16.
 */
public class EarningTransactionMock implements EarningTransaction {
    private UUID id;
    private Currency currency;
    private int year;
    private int month;
    private int day;
    private float amount;
    private boolean extracted;
    private EarningTransactionState state;

    public EarningTransactionMock(float amount, Currency currency, int year, int month, int day) {
        this.amount = amount;
        this.currency = currency;
        this.year = year;
        this.month = month;
        this.day = day;
        this.extracted = false;
        state = EarningTransactionState.CALCULATED;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Currency getEarningCurrency() {
        return currency;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public void markAsExtracted() throws EarningTransactionNotFoundException, CantMarkEarningTransactionAsExtractedException {
        extracted = true;
    }

    @Override
    public EarningTransactionState getState() {
        if (extracted) state = EarningTransactionState.EXTRACTED;
        return state;
    }

    @Override
    public long getTime() {
        return 0;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getDayOfYear() {
        return 0;
    }

    @Override
    public int getWeekOfYear() {
        return 0;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getYear() {
        return year;
    }
}
