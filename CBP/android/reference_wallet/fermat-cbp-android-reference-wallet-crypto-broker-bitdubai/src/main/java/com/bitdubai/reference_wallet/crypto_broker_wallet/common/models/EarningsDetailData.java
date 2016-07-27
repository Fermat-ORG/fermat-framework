package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.EarningCurrencyCalendarRelationship;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * The class <code>com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsDetailData</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/03/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class EarningsDetailData {

    private final EarningCurrencyCalendarRelationship relationship;
    private float amount;
    private final List<EarningTransaction> earningTransactions;

    public EarningsDetailData(EarningCurrencyCalendarRelationship relationship) {

        this.relationship = relationship;
        this.earningTransactions = new ArrayList<>();
    }

    public float getAmount() {
        return amount;
    }

    public List<EarningTransaction> listEarningTransactions() {
        return earningTransactions;
    }

    public void addEarningTransaction(final EarningTransaction earningTransaction) {

        earningTransactions.add(earningTransaction);
        amount += earningTransaction.getAmount();
    }

    public EarningCurrencyCalendarRelationship getRelationship() {
        return relationship;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("EarningsDetailData{")
                .append("amount=")
                .append(amount)
                .append(", earningTransactions=")
                .append(earningTransactions)
                .append('}').toString();
    }

    public static List<EarningsDetailData> generateEarningsDetailData(List<EarningTransaction> earningTransactionList, TimeFrequency timeFrequency) {

        TreeMap<EarningCurrencyCalendarRelationship, EarningsDetailData> earningsDetailDataMap = new TreeMap<>();

        for (EarningTransaction earningTransaction : earningTransactionList) {

            EarningCurrencyCalendarRelationship relationship = new EarningCurrencyCalendarRelationship(
                    earningTransaction.getYear(),
                    earningTransaction.getMonth(),
                    earningTransaction.getDay(),
                    getTimeReference(timeFrequency, earningTransaction),
                    earningTransaction.getEarningCurrency()
            );

            if (earningsDetailDataMap.containsKey(relationship)) {
                earningsDetailDataMap.get(relationship).addEarningTransaction(
                        earningTransaction
                );
            } else {
                EarningsDetailData earningsDetailData = new EarningsDetailData(relationship);
                earningsDetailData.addEarningTransaction(
                        earningTransaction
                );
                earningsDetailDataMap.put(
                        relationship,
                        earningsDetailData
                );
            }
        }

        List<EarningsDetailData> list = new ArrayList<>();

        for (TreeMap.Entry<EarningCurrencyCalendarRelationship, EarningsDetailData> entry : earningsDetailDataMap.entrySet())
            list.add(entry.getValue());

        return list;
    }

    private static int getTimeReference(TimeFrequency timeFrequency, EarningTransaction earningTransaction) {

        switch (timeFrequency) {
            case DAILY:
                return earningTransaction.getDayOfYear();
            case WEEKLY:
                return earningTransaction.getWeekOfYear();
            case MONTHLY:
                return earningTransaction.getMonth();
            case YEARLY:
                return earningTransaction.getYear();
            default:
                return 0;
        }
    }
}
