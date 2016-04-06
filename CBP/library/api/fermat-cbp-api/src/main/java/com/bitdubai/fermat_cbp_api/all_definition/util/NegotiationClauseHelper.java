package com.bitdubai.fermat_cbp_api.all_definition.util;

import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;

import java.util.regex.Pattern;


/**
 * Created by nelsonalfo on 05/04/16.
 */
public final class NegotiationClauseHelper {

    public static String getAccountNumberFromClause(Clause clause) {
        String account = "";

        if (clause != null) {
            final String value = clause.getValue();

            if (value != null && !value.isEmpty()) {
                String clauseValue = clause.getValue();
                String[] split = clauseValue.split("\\D+:\\s*");
                account = split.length == 1 ? split[0] : split[1];

                return Pattern.matches("(\\d-?)+", account) ? account : "";
            }
        }

        return account;
    }
}
