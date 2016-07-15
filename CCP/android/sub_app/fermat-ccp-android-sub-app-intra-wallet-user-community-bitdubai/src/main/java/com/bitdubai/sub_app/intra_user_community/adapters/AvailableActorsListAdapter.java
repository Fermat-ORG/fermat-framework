package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.holders.AvailableActorsViewHolder;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.LoadingMoreViewHolder;

import java.util.List;

/**
 * Created by natalia on 13/07/16.
 */
public class AvailableActorsListAdapter extends FermatAdapter<IntraUserInformation, FermatViewHolder> {

    public static final int DATA_ITEM = 1;
    public static final int LOADING_ITEM = 2;
    private boolean loadingData = true;


    public AvailableActorsListAdapter(Context context, List<IntraUserInformation> dataSet) {
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
            return R.layout.row_connections_world;
        if (type == LOADING_ITEM)
            return R.layout.ccp_view_loading_more_list_item;
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
    protected void bindHolder(FermatViewHolder holder, IntraUserInformation data, int position) {
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