package com.bitdubai.sub_app_artist_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.holders.AppNotificationsHolder;

import java.util.List;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/04/16.
 */
public class AppNotificationAdapter extends FermatAdapter<ArtistCommunityInformation, AppNotificationsHolder> {

    public AppNotificationAdapter(Context context, List<ArtistCommunityInformation> lst) {
        super(context, lst);
    }

    @Override
    protected AppNotificationsHolder createHolder(View itemView, int type) {
        return new AppNotificationsHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.aac_row_connection_notification;
    }

    @Override
    protected void bindHolder(AppNotificationsHolder holder, ArtistCommunityInformation data, int position) {
        holder.userName.setText(data.getAlias());
        Bitmap bitmap;
        if (data.getImage().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aac_profile_image);
        }
    //    bitmap = Bitmap.createScaledBitmap(bitmap, 40,40, true);
        holder.userAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
