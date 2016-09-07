package com.juaco.fermat_statistics.models;

import android.app.Activity;


//import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;

import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;

import java.util.Collection;

/**
 * Created by Joaquin Carrasquero on 01/03/16.
 */
public class Fermat_market_data {


    //IndexInfoSummary indexInfo;

    Collection<ExchangeRate> collection;

    Activity activity;


    public Fermat_market_data(/*IndexInfoSummary indexInfo,*/ Collection<ExchangeRate> collection, Activity activity){
//        this.indexInfo = indexInfo;

        this.collection = collection;
        this.activity = activity;
    }

//    public IndexInfoSummary getIndexInfo() {
//        return indexInfo;
//    }

    public Collection<ExchangeRate> getCollection() {
        return collection;
    }

    public Activity getActivity() {
        return activity;
    }*/
}
