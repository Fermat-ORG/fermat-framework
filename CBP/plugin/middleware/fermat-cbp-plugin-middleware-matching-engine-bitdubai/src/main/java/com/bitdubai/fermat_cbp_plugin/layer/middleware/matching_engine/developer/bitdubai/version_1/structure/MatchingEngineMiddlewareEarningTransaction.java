package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantMarkEarningTransactionAsExtractedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningTransactionNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;


/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningTransaction</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareEarningTransaction implements EarningTransaction, Serializable {

    private final UUID id;
    private final Currency earningCurrency;
    private final float amount;
    private final long time;
    private final Calendar cal;

    private EarningTransactionState state;


    public MatchingEngineMiddlewareEarningTransaction(final UUID id,
                                                      final Currency earningCurrency,
                                                      final float amount,
                                                      final EarningTransactionState state,
                                                      final long time,
                                                      final MatchingEngineMiddlewareDao dao) {

        this.id = id;
        this.earningCurrency = earningCurrency;
        this.amount = amount;
        this.state = state;
        this.time = time;

        cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis(this.time);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Currency getEarningCurrency() {
        return earningCurrency;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public void markAsExtracted() throws EarningTransactionNotFoundException, CantMarkEarningTransactionAsExtractedException {
        state = EarningTransactionState.EXTRACTED;
    }

    @Override
    public EarningTransactionState getState() {
        return state;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "MatchingEngineMiddlewareEarningTransaction{" +
                "id=" + id +
                ", earningCurrency=" + earningCurrency +
                ", amount=" + amount +
                ", state=" + state +
                '}';
    }

    public int getDay() {

        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayOfYear() {

        return cal.get(Calendar.DAY_OF_YEAR);
    }

    public int getWeekOfYear() {

        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public int getMonth() {

        return cal.get(Calendar.MONTH);
    }

    public int getYear() {

        return cal.get(Calendar.YEAR);
    }
}
