package com.bitdubai.reference_niche_wallet.bitcoin_wallet.list;

/**
 * Created by mati on 2015.07.14..
 */
public interface TransactionListView {


    public static final int TYPE_HEADER=0;
    public static final int TYPE_TRANSACTION=1;


    public int getType();
    public Object getObject();
}
