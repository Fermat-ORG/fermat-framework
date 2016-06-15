package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantCreateTransactionStatementPairException</code>
 * is thrown when there is an error trying to create a transaction statement pair.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/05/2016.
 */
public class CantCreateTransactionStatementPairException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT CREATE TRANSACTION STATEMENT PAIR EXCEPTION";

    public CantCreateTransactionStatementPairException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateTransactionStatementPairException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCreateTransactionStatementPairException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
