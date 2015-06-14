package com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.exceptions;

import com.bitdubai.fermat_api.layer._13_basic_wallet.discount_wallet.enums.CreditFailedReasons;

/**
 * Created by ciencias on 3/24/15.
 */
public class CreditFailedException extends Exception  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4161434272257933849L;
	private CreditFailedReasons reason;

    public CreditFailedException (CreditFailedReasons reason){
        this.reason = reason;
    }

    public CreditFailedReasons getReason(){
        return this.reason;
    }
}
