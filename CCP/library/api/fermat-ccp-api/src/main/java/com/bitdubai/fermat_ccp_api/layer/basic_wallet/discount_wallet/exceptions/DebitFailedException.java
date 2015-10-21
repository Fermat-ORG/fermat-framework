package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions;

/**
 * Created by ciencias on 3/24/15.
 */
public class DebitFailedException extends Exception  {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4911760450415120116L;
	private com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.DebitFailedReasons reason;

    public DebitFailedException (com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.DebitFailedReasons reason){
        this.reason = reason;
    }

    public com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.DebitFailedReasons getReason(){
        return this.reason;
    }
}
