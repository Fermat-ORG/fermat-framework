package com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.TransferFailedReasons;

/**
 * Created by ciencias on 3/24/15.
 */
public class TransferFailedException extends Exception {

    private TransferFailedReasons reason;
    
    public TransferFailedException (TransferFailedReasons reason){
        this.reason = reason;
    }
    
    public TransferFailedReasons getReason(){
        return this.reason;
    }
}
