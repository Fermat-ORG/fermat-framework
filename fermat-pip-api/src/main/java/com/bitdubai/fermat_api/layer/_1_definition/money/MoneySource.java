package com.bitdubai.fermat_api.layer._1_definition.money;


/**
 * Created by ciencias on 22.12.14.
 */
public class MoneySource {

   // BalanceChunk mBalanceChunk;
    private FiatMoney mFiatMoney;
    private CryptoMoney mCryptoMoney;
	public FiatMoney getmFiatMoney() {
		return mFiatMoney;
	}
	public void setmFiatMoney(FiatMoney mFiatMoney) {
		this.mFiatMoney = mFiatMoney;
	}
	public CryptoMoney getmCryptoMoney() {
		return mCryptoMoney;
	}
	public void setmCryptoMoney(CryptoMoney mCryptoMoney) {
		this.mCryptoMoney = mCryptoMoney;
	}
    
    

}
