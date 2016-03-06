package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;

import java.util.List;
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
public final class MatchingEngineMiddlewareEarningTransaction implements EarningTransaction {

    private final UUID                    id               ;
    private final Currency                earningCurrency  ;
    private final float                   amount           ;
    private final List<InputTransaction>  inputTransactions;
    private final EarningTransactionState state            ;

    public MatchingEngineMiddlewareEarningTransaction(final UUID                    id               ,
                                                      final Currency                earningCurrency  ,
                                                      final float                   amount           ,
                                                      final List<InputTransaction>  inputTransactions,
                                                      final EarningTransactionState state            ) {

        this.id                = id               ;
        this.earningCurrency   = earningCurrency  ;
        this.amount            = amount           ;
        this.inputTransactions = inputTransactions;
        this.state             = state            ;
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

    public List<InputTransaction> listInputTransactions() {
        return inputTransactions;
    }

    @Override
    public EarningTransactionState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "MatchingEngineMiddlewareEarningTransaction{" +
                "id=" + id +
                ", earningCurrency=" + earningCurrency +
                ", amount=" + amount +
                ", inputTransactions=" + inputTransactions +
                ", state=" + state +
                '}';
    }
}
