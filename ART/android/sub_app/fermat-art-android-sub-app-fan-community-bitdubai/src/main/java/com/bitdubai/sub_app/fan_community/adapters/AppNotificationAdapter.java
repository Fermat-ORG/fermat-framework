package com.bitdubai.sub_app.fan_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.LinkedFanIdentity;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.holders.AppNotificationsHolder;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class AppNotificationAdapter extends FermatAdapter<LinkedFanIdentity, AppNotificationsHolder> {

    public AppNotificationAdapter(Context context, List<LinkedFanIdentity> lst) {
        super(context, lst);
    }

    @Override
    protected AppNotificationsHolder createHolder(View itemView, int type) {
        return new AppNotificationsHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.afc_row_connection_notification;
    }

    @Override
    protected void bindHolder(AppNotificationsHolder holder, LinkedFanIdentity data, int position) {
        Bitmap bitmap;
        holder.userName.setText(data.getAlias());
        if (data.getImage().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.afc_profile_image);
        }
    //    bitmap = Bitmap.createScaledBitmap(bitmap, 40,40, true);
        //holder.userAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        holder.userAvatar.setImageBitmap(bitmap);
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}

