package com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.exceptions;

import com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.enums.DebitFailedReasons;

/**
 * Created by ciencias on 3/24/15.
 */
public class DebitFailedException extends Exception  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4911760450415120116L;
	private DebitFailedReasons reason;

    public DebitFailedException (DebitFailedReasons reason){
        this.reason = reason;
    }

    public DebitFailedReasons getReason(){
        return this.reason;
    }
}
