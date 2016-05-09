package com.bitdubai.sub_app.crypto_customer_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.holders.AppWorldHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AppListAdapter extends FermatAdapter<CryptoCustomerCommunityInformation, AppWorldHolder> {


    public AppListAdapter(Context context) {
        super(context);
    }

    public AppListAdapter(Context context, List<CryptoCustomerCommunityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppWorldHolder createHolder(View itemView, int type) {
        return new AppWorldHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.ccc_row_connections_world;
    }

    @Override
    protected void bindHolder(AppWorldHolder holder, CryptoCustomerCommunityInformation data, int position) {
        holder.name.setText(data.getAlias());

        if(data.getConnectionState() != null && data.getConnectionState() == ConnectionState.CONNECTED)
            holder.connectionState.setVisibility(View.VISIBLE);
        else
            holder.connectionState.setVisibility(View.GONE);

        byte[] profileImage = data.getImage();
        if (profileImage != null && profileImage.length>0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
            holder.thumbnail.setImageBitmap(bitmap);
        }
        else
            Picasso.with(context).load(R.drawable.profile_image).into(holder.thumbnail);

    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}