package com.mati.revealmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.mati.horizontalscrollview.HorizontalRecyler;
import com.mati.horizontalscrollview.SetttingsItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.02.25..
 */
public class RevealMenu implements FermatListItemListeners<SetttingsItems> {

    private ViewGroup parentView;

    public RevealMenu(ViewGroup parentView) {
        this.parentView = parentView;
    }

    public void init(){

        LayoutInflater inflater = (LayoutInflater) parentView.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = inflater.inflate(R.layout.reveal_main, parentView);


        HorizontalRecyler.Builder builder = new HorizontalRecyler.Builder(rootView.getContext(),provisoryData()).setFermatListItemListeners(this);
        HorizontalRecyler horizontalRecyler = new HorizontalRecyler(rootView.getContext(),provisoryData());
        ((ViewGroup)rootView.findViewById(R.id.horizontal_container)).addView(horizontalRecyler);
    }


    private List<SetttingsItems> provisoryData(){
        List<SetttingsItems> lst = new ArrayList<>();
        lst.add(new SetttingsItems(1, R.drawable.bitcoin_wallet,"wallet1"));
        lst.add(new SetttingsItems(2,R.drawable.bitcoin_wallet,"wallet2"));
        lst.add(new SetttingsItems(3,R.drawable.bitcoin_wallet,"wallet3"));
        lst.add(new SetttingsItems(4,R.drawable.bitcoin_wallet,"wallet4"));
        lst.add(new SetttingsItems(5,R.drawable.bitcoin_wallet,"wallet4"));
        lst.add(new SetttingsItems(6,R.drawable.bitcoin_wallet,"wallet4"));
        lst.add(new SetttingsItems(7,R.drawable.bitcoin_wallet,"wallet4"));
        lst.add(new SetttingsItems(8,R.drawable.bitcoin_wallet,"wallet4"));

        return lst;
    }


    @Override
    public void onItemClickListener(SetttingsItems data, int position) {

    }

    @Override
    public void onLongItemClickListener(SetttingsItems data, int position) {

    }


    public static class Builder {

        private ViewGroup root;

        public Builder(ViewGroup root) {
            this.root = root;
        }

        public RevealMenu build(){
            return new RevealMenu(root);
        }


    }

}
