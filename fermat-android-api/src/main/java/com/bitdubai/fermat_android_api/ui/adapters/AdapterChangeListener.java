package com.bitdubai.fermat_android_api.ui.adapters;

import java.util.List;

/**
 * Created by francisco on 28/10/15.
 */
public interface AdapterChangeListener<M> {

    void onDataSetChanged(List<M> dataSet);
}
