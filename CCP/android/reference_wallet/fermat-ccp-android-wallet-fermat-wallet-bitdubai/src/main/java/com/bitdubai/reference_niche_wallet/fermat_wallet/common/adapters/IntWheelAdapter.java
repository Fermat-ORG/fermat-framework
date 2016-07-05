package com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters;

import com.bitdubai.reference_niche_wallet.fermat_wallet.interfaces.WheelAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IntWheelAdapter implements WheelAdapter {
	private ArrayList<Integer> _val;
	private DecimalFormat df;
	Integer _cur;

	public IntWheelAdapter(int min, int max, String format) {
		df = new DecimalFormat(format);
		this._val = new ArrayList<Integer>();
		for (int i = min; i <= max; i++) {
			this._val.add(new Integer(i));
		}
		this._cur = 0;

	}

	public int getAfterNum() {
		// TODO Auto-generated method stub
		return this.getLen() - this._cur;
	}

	public int getBeforeNum() {
		// TODO Auto-generated method stub
		return this._cur;
	}

	public int getCurIndex() {
		// TODO Auto-generated method stub
		return this._cur;
	}

	public String getCurLabel() {
		// TODO Auto-generated method stub
		return "" + this._val.get(this._cur);
	}

	public Integer getCurValue() {
		return this._val.get(this.getCurIndex());
	}
	
	public void setCurValue(Object val)
	{
		int v = (Integer)val;
		for ( int i =0; i<_val.size(); i++)
		{
			
			if ( v == (Integer)_val.get(i) )
			{
				this.setCurIndex(i);
				return;
			}
		}
		setCurIndex(0);
	}

	public String getLabel(int index) {
		return df.format(this._val.get(index));
	}

	public int getLen() {
		// TODO Auto-generated method stub
		return this._val.size();
	}

	public Integer getValue(int index) {
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

	@Override
	public void setValue(int index, Object val) {
		// TODO Auto-generated method stub
		
	}

}
