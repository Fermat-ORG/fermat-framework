package com.bitdubai.reference_niche_wallet.fermat_wallet.interfaces;

public interface WheelAdapter {
	public int getAfterNum();

	public int getBeforeNum();

	public int getCurIndex();

	public Object getCurValue();

	public String getLabel(int index);

	public int getLen();

	public Object getValue(int index);

	public int nextIndex();

	public int prevIndex();

	public void setCurIndex(int index);
	
	public void setCurValue(Object val);
	
	public void setValue(int index, Object val);
}
