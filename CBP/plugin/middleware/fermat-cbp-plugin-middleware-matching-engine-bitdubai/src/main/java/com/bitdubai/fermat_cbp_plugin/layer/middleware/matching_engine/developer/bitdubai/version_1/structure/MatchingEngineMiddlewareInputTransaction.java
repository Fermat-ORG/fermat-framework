package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction</code>
 * contains all the information about an InputTransaction.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareInputTransaction implements InputTransaction {

    private final String                id               ;
    private final Currency              currencyGiving   ;
    private final float                 amountGiving     ;
    private final Currency              currencyReceiving;
    private final float                 amountReceiving  ;
    private final InputTransactionState state            ;

    public MatchingEngineMiddlewareInputTransaction(final String                 id               ,
                                                    final Currency               currencyGiving   ,
                                                    final float                  amountGiving     ,
                                                    final Currency               currencyReceiving,
                                                    final float                  amountReceiving  ,
                                                    final InputTransactionState  state            ) {

        this.id                = id               ;
        this.currencyGiving    = currencyGiving   ;
        this.amountGiving      = amountGiving     ;
        this.currencyReceiving = currencyReceiving;
        this.amountReceiving   = amountReceiving  ;
        this.state             = state            ;
    }

    public String getId() {
        return id;
    }

    public Currency getCurrencyGiving() {
        return currencyGiving;
    }

    public float getAmountGiving() {
        return amountGiving;
    }

    public Currency getCurrencyReceiving() {
        return currencyReceiving;
    }

    public float getAmountReceiving() {
        return amountReceiving;
    }

    public InputTransactionState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "InputTransaction{" +
                "id=" + id +
                ", currencyGiving=" + currencyGiving +
                ", amountGiving=" + amountGiving +
                ", currencyReceiving=" + currencyReceiving +
                ", amountReceiving=" + amountReceiving +
                ", state=" + state +
                '}';
    }

}
