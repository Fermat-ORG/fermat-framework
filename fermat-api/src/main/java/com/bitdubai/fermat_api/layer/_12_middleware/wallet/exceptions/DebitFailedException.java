package com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions;

import com.bitdubai.fermat_api.layer._12_middleware.wallet.DebitFailedReasons;

/**
 * Created by ciencias on 3/24/15.
 */
public class DebitFailedException extends Exception  {

    private DebitFailedReasons reason;

    public DebitFailedException (DebitFailedReasons reason){
        this.reason = reason;
    }

    public DebitFailedReasons getReason(){
        return this.reason;
    }
}