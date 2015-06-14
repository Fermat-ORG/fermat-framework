package com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.exceptions;

import com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.enums.TransferFailedReasons;

/**
 * Created by ciencias on 3/24/15.
 */
public class TransferFailedException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3343389477839363564L;
	private TransferFailedReasons reason;
    
    public TransferFailedException (TransferFailedReasons reason){
        this.reason = reason;
    }
    
    public TransferFailedReasons getReason(){
        return this.reason;
    }
}
