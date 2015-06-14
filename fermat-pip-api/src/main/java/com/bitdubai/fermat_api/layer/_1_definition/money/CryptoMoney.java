package com.bitdubai.fermat_api.layer._1_definition.money;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class CryptoMoney implements Money {

    private CryptoCurrency mCryptoCurrency;
    private double mCryptoAmount;
	public CryptoCurrency getmCryptoCurrency() {
		return mCryptoCurrency;
	}
	public void setmCryptoCurrency(CryptoCurrency mCryptoCurrency) {
		this.mCryptoCurrency = mCryptoCurrency;
	}
	public double getmCryptoAmount() {
		return mCryptoAmount;
	}
	public void setmCryptoAmount(double mCryptoAmount) {
		this.mCryptoAmount = mCryptoAmount;
	}
    
    
    
}
