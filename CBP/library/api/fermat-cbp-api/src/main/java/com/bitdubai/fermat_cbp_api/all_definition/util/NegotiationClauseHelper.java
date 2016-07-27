package com.bitdubai.fermat_cbp_api.all_definition.util;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;

import java.util.Collection;
import java.util.regex.Pattern;


/**
 * Created by nelsonalfo on 05/04/16.
 */
public final class NegotiationClauseHelper {

    public static String getAccountNumberFromClause(Clause clause) {
        String account = "";

        if (clause != null) {
            final String value = clause.getValue();

            return getAccountNumberFromString(value);
        }

        return account;
    }

    public static String getAccountNumberFromString(String value) {
        String account = "";

        if (value != null && !value.isEmpty()) {
            String[] split = value.split("\\D+:\\s*");
            account = split.length == 1 ? split[0] : split[1];

            return Pattern.matches("(\\d-?)+", account) ? account : "";
        }

        return account;
    }

    /**
     * Get the value of a negotiation clause
     *
     * @param negotiationClauses the list of clauses
     * @param clauseType         the clause to find
     * @return the clause value or <code>null</code>
     */
    public static String getNegotiationClauseValue(Collection<Clause> negotiationClauses, ClauseType clauseType) {

        for (Clause clause : negotiationClauses)
            if (clause.getType().getCode().equals(clauseType.getCode()))
                return clause.getValue();

        return null;
    }
}
