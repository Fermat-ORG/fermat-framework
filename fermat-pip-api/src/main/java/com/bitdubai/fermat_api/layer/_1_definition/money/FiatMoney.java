package com.bitdubai.fermat_api.layer._1_definition.money;

import com.bitdubai.fermat_api.layer._1_definition.enums.FiatCurrency;

/**
 * Created by ciencias on 22.12.14.
 */
public class FiatMoney implements Money {

    private FiatCurrency mFiatCurrency;
    private double mFiatAmount;
    
    
	public FiatCurrency getmFiatCurrency() {
		return mFiatCurrency;
	}
	public void setmFiatCurrency(FiatCurrency mFiatCurrency) {
		this.mFiatCurrency = mFiatCurrency;
	}
	public double getmFiatAmount() {
		return mFiatAmount;
	}
	public void setmFiatAmount(double mFiatAmount) {
		this.mFiatAmount = mFiatAmount;
	}
    
    
}
