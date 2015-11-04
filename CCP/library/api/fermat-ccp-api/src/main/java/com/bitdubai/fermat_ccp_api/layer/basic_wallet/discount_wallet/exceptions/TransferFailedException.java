package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.exceptions;

/**
 * Created by ciencias on 3/24/15.
 */
public class TransferFailedException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3343389477839363564L;
	private com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.TransferFailedReasons reason;
    
    public TransferFailedException (com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.TransferFailedReasons reason){
        this.reason = reason;
    }
    
    public com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums.TransferFailedReasons getReason(){
        return this.reason;
    }
}
