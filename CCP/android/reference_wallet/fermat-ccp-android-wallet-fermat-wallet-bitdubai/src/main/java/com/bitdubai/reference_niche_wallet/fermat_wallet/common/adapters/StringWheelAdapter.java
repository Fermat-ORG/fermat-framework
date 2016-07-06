package com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters;


import com.bitdubai.reference_niche_wallet.fermat_wallet.interfaces.WheelAdapter;

import java.util.ArrayList;
import java.util.List;

public class StringWheelAdapter implements WheelAdapter {
	protected ArrayList<String> _val;
	protected int _cur;

	private List<String> fiatCurrenciesLst;
	
	public StringWheelAdapter( List<String> fiatCurrencyList)
	{
		fiatCurrenciesLst = fiatCurrencyList;
		_val = new ArrayList<String>();
		_val.addAll(fiatCurrencyList);


		//_val.addAll(Arrays.asList(vals));
	}

	public int getAfterNum() {
		// TODO Auto-generated method stub
		return this.getLen() - this._cur;
	}

	public int getBeforeNum() {
		// TODO Auto-generated method stub
		return _cur;
	}

	public int getCurIndex() {
		// TODO Auto-generated method stub
		return _cur;
	}

	public String getCurLabel() {
		// TODO Auto-generated method stub
		return "" + this._val.get(_cur);
	}

	public String getCurValue() {
		return this._val.get(this.getCurIndex());
	}
	
	public void setCurValue(String val)
	{
		_val.set(_cur, val);
	}

	public String getLabel(int index) {
		// TODO Auto-generated method stub
		return this._val.get(index);
	}

	public int getLen() {
		// TODO Auto-generated method stub
		return this._val.size();
	}

	public String getValue(int index) {
		return this._val.get(index);
	}

	public int nextIndex() {
		if (this._cur < this.getLen() - 1) {
			this._cur += 1;
		}
		return this._cur;
	}

	public int prevIndex() {
		if (this._cur > 0) {
			this._cur -= 1;
		}
		return this._cur;
	}

	public void setCur(int index) {
		this._cur = index;
	}

	public void setCurIndex(int index) {
		// TODO Auto-generated method stub
		this._cur = index;
	}

	public void setValue(int index, String val) {
		_val.set(index, val);		
	}

	@Override
	public void setCurValue(Object val) {
		setCurValue((String)val);		
	}

	@Override
	public void setValue(int index, Object val) {
		setValue(index,(String)val);		
	}

}
