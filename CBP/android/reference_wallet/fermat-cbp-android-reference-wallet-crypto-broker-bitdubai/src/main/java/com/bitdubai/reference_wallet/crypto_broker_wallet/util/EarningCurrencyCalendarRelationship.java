package com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

/**
 * The class <code>com.bitdubai.reference_wallet.crypto_broker_wallet.util.EarningCurrencyCalendarRelationship</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/03/2016.
 */
public class EarningCurrencyCalendarRelationship implements Comparable<EarningCurrencyCalendarRelationship> {

    private final Integer year;
    private final Integer month;
    private final Integer day;
    private final Integer timeReference;
    private final Currency currency;

    public EarningCurrencyCalendarRelationship(Integer year, Integer month, Integer day, Integer timeReference, Currency currency) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.timeReference = timeReference;
        this.currency = currency;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getTimeReference() {
        return timeReference;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EarningCurrencyCalendarRelationship that = (EarningCurrencyCalendarRelationship) o;

        return currency.equals(that.currency) &&
                year.equals(that.year) &&
                timeReference.equals(that.timeReference);

    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + currency.hashCode();
        result = 31 * result + year.hashCode();
        result = 31 * result + timeReference.hashCode();
        return result;
    }

    @Override
    public int compareTo(EarningCurrencyCalendarRelationship ed2) {

        if (getYear() < ed2.getYear())
            return -1;

        if (getYear() > ed2.getYear())
            return 1;

        if (getTimeReference() < ed2.getTimeReference())
            return -1;

        if (getTimeReference() > ed2.getTimeReference())
            return 1;

        if (getCurrency().hashCode() < ed2.getCurrency().hashCode())
            return -1;

        if (getCurrency().hashCode() > ed2.getCurrency().hashCode())
            return 1;

        return 0;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("EarningCurrencyCalendarRelationship{")
                .append("year=")
                .append(year)
                .append(", month=")
                .append(month)
                .append(", day=")
                .append(day)
                .append(", timeReference=")
                .append(timeReference)
                .append(", currency=")
                .append(currency)
                .append('}').toString();
    }
}
