package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;

import java.security.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.utils.MatchingEngineMiddlewareCurrencyPair</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/02/2016.
 */
public class MatchingEngineMiddlewareCurrencyPair implements CurrencyPair {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private final Currency from;
    private final Currency to;

    public MatchingEngineMiddlewareCurrencyPair(final Currency from,
                                                final Currency to) {


        if (from == null)
            throw new InvalidParameterException("From is null.");

        if (to == null)
            throw new InvalidParameterException("To is null.");

        this.from = from;
        this.to = to;
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

        int c = 0;
        c += from.hashCode();
        c += to.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("MatchingEngineMiddlewareCurrencyPair{")
                .append("from=").append(from)
                .append(", to=").append(to)
                .append('}').toString();
    }
}
