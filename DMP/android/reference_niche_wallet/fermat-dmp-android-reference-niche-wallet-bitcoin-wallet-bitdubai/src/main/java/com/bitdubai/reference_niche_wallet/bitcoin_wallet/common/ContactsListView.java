package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common;

/**
 * Created by mati on 2015.07.14..
 */
public interface ContactsListView {


    public static final int TYPE_HEADER=0;
    public static final int TYPE_CONTACTS=1;


    public int getType();
    public Object getObject();
}
