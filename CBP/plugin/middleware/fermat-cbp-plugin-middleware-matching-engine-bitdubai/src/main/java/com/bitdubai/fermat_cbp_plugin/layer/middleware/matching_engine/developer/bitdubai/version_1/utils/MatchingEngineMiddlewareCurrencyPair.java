package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;

import java.security.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.utils.MatchingEngineMiddlewareCurrencyPair</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/02/2016.
 */
public class MatchingEngineMiddlewareCurrencyPair implements CurrencyPair {

    private final Currency from;
    private final Currency to  ;

    public MatchingEngineMiddlewareCurrencyPair(final Currency from,
                                                final Currency to  ) {


        if (from == null)
            throw new InvalidParameterException("From is null.");

        if (to == null)
            throw new InvalidParameterException("To is null.");

        this.from = from;
        this.to   = to  ;
    }

    @Override
    public Currency getFrom() {
        return from;
    }

    @Override
    public Currency getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchingEngineMiddlewareCurrencyPair that = (MatchingEngineMiddlewareCurrencyPair) o;

        return (from.equals(that.from) && to.equals(that.to)) ||
                (from.equals(that.to) && to.equals(that.from));

    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

}
