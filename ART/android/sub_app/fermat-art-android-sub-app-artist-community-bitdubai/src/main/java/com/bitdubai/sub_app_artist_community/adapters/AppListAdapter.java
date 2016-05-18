package com.bitdubai.sub_app_artist_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.holders.AppWorldHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class AppListAdapter extends FermatAdapter<ArtistCommunityInformation, AppWorldHolder> {


    public AppListAdapter(Context context) {
        super(context);
    }

    public AppListAdapter(Context context, List<ArtistCommunityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppWorldHolder createHolder(View itemView, int type) {
        return new AppWorldHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.aac_row_connections_world;
    }

    @Override
    protected void bindHolder(AppWorldHolder holder, ArtistCommunityInformation data, int position) {

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
            Picasso.with(context).load(R.drawable.aac_profile_image).into(holder.thumbnail);

    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}