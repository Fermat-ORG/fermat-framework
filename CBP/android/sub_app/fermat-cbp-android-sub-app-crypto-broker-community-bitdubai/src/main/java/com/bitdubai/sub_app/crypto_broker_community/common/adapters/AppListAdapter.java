package com.bitdubai.sub_app.crypto_broker_community.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.holders.AppWorldHolder;
import com.bitdubai.sub_app.crypto_broker_community.common.holders.LoadingMoreViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AppListAdapter extends FermatAdapter<CryptoBrokerCommunityInformation, FermatViewHolder> {

    public static final int DATA_ITEM = 1;
    public static final int LOADING_ITEM = 2;
    private boolean loadingData = true;


    public AppListAdapter(Context context, List<CryptoBrokerCommunityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        if (type == DATA_ITEM)
            return new AppWorldHolder(itemView, type);
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
            return R.layout.cbc_row_connections_world;
        if (type == LOADING_ITEM)
            return R.layout.loading_more_list_item;
        return 0;
    }


    @Override
    protected int getCardViewResource() {
        return 0;
    }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {
        if (holder instanceof AppWorldHolder)
            super.onBindViewHolder(holder, position);

        else if (holder instanceof LoadingMoreViewHolder) {
            final LoadingMoreViewHolder loadingMoreViewHolder = (LoadingMoreViewHolder) holder;
            loadingMoreViewHolder.progressBar.setVisibility(loadingData ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void bindHolder(FermatViewHolder holder, CryptoBrokerCommunityInformation data, int position) {

        final AppWorldHolder appWorldHolder = (AppWorldHolder) holder;

        appWorldHolder.name.setText(data.getAlias());

        final ConnectionState connectionState = data.getConnectionState();

        int visibility = View.GONE;
        int connectionStateImage = R.drawable.ic_home_friend;

//        int visibility = connectionState != null && connectionState == ConnectionState.CONNECTED ? View.VISIBLE : View.GONE;

        if(connectionState != null){
            if(connectionState == ConnectionState.CONNECTED){
                visibility = View.VISIBLE;
            }else if(connectionState == ConnectionState.PENDING_REMOTELY_ACCEPTANCE){
                connectionStateImage = R.drawable.ic_home_friend_pending;
                visibility = View.VISIBLE;
            }
        }

        appWorldHolder.connectionState.setVisibility(visibility);
        appWorldHolder.connectionState.setImageResource(connectionStateImage);

        byte[] profileImage = data.getImage();
        if (profileImage != null && profileImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
            appWorldHolder.thumbnail.setImageBitmap(bitmap);
        } else
            Picasso.with(context).load(R.drawable.profile_image).into(appWorldHolder.thumbnail);
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