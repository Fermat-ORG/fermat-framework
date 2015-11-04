package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.receive_offline_bank_transfer.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateReceiveOfflineBankTransferException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Bank Transaction Receive Offline Bank Transfer.";
    public CantCreateReceiveOfflineBankTransferException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
