package com.bitdubai.android_core.app.common.version_1.top_settings;

import android.view.ViewGroup;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.mati.horizontalscrollview.HorizontalRecyler;
import com.mati.horizontalscrollview.SetttingsItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.01.26..
 */
public class TopSettings implements FermatListItemListeners<SetttingsItems>{

    private ViewGroup root;

    public TopSettings(ViewGroup root) {
        this.root = root;
    }

    public void init(){
        HorizontalRecyler.Builder builder = new HorizontalRecyler.Builder(root.getContext(),provisoryData()).setFermatListItemListeners(this);
        HorizontalRecyler horizontalRecyler = new HorizontalRecyler(root.getContext(),provisoryData());
        root.addView(horizontalRecyler);
    }


    private List<SetttingsItems> provisoryData(){
        List<SetttingsItems> lst = new ArrayList<>();
        lst.add(new SetttingsItems(1,R.drawable.bitcoin_wallet_2,"wallet1"));
        lst.add(new SetttingsItems(2,R.drawable.bitcoin_wallet_2,"wallet2"));
        lst.add(new SetttingsItems(3,R.drawable.bitcoin_wallet_2,"wallet3"));
        lst.add(new SetttingsItems(4,R.drawable.bitcoin_wallet_2,"wallet4"));
        lst.add(new SetttingsItems(5,R.drawable.bitcoin_wallet_2,"wallet4"));
        lst.add(new SetttingsItems(6,R.drawable.bitcoin_wallet_2,"wallet4"));
        lst.add(new SetttingsItems(7,R.drawable.bitcoin_wallet_2,"wallet4"));
        lst.add(new SetttingsItems(8,R.drawable.bitcoin_wallet_2,"wallet4"));

        return lst;
    }

    @Override
    public void onItemClickListener(SetttingsItems data, int position) {

    }

    @Override
    public void onLongItemClickListener(SetttingsItems data, int position) {

    }
}
