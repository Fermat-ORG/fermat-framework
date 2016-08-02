package com.bitdubai.sub_app.crypto_customer_community.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.holders.AvailableActorsViewHolder;
import com.bitdubai.sub_app.crypto_customer_community.common.holders.LoadingMoreViewHolder;

import java.util.List;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AvailableActorsListAdapter extends FermatAdapter<CryptoCustomerCommunityInformation, FermatViewHolder> {

    public static final int DATA_ITEM = 1;
    public static final int LOADING_ITEM = 2;
    private boolean loadingData = true;


    public AvailableActorsListAdapter(Context context, List<CryptoCustomerCommunityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        if (type == DATA_ITEM)
            return new AvailableActorsViewHolder(itemView, type);
        if (type == LOADING_ITEM)
            return new LoadingMoreViewHolder(itemView, type);
        return null;
    }

    @Override
    public FermatViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(type), viewGroup, false), type);
    }

    protected int getCardViewResource(int type) {
        if (type == DATA_ITEM)
            return R.layout.ccc_fragment_browser_tab_list_item;
        if (type == LOADING_ITEM)
            return R.layout.ccc_view_loading_more_list_item;
        return 0;
    }


    @Override
    protected int getCardViewResource() {
        return 0;
    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {
        if (holder instanceof AvailableActorsViewHolder)
            super.onBindViewHolder(holder, position);

        else if (holder instanceof LoadingMoreViewHolder) {
            final LoadingMoreViewHolder loadingMoreViewHolder = (LoadingMoreViewHolder) holder;
            loadingMoreViewHolder.progressBar.setVisibility(loadingData ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void bindHolder(FermatViewHolder holder, CryptoCustomerCommunityInformation data, int position) {
        final AvailableActorsViewHolder availableActorsViewHolder = (AvailableActorsViewHolder) holder;
        availableActorsViewHolder.bind(data);
    }

    @Override
    public int getItemViewType(int position) {
        return position == super.getItemCount() ? LOADING_ITEM : DATA_ITEM;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    public boolean isLoadingData() {
        return loadingData;
    }

    public void setLoadingData(boolean loadingData) {
        this.loadingData = loadingData;
    }
}