package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.AppNotificationsHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jose Manuel De Sousa.
 */
public class AppNotificationAdapter extends FermatAdapter<IntraUserInformation, AppNotificationsHolder> {

    public AppNotificationAdapter(Context context, List<IntraUserInformation> lst) {
        super(context, lst);
    }

    @Override
    protected AppNotificationsHolder createHolder(View itemView, int type) {
        return new AppNotificationsHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.wallet_notification_row;
    }

    @Override
    protected void bindHolder(AppNotificationsHolder holder, IntraUserInformation data, int position) {
        holder.userName.setText(data.getName());
        Bitmap bitmap;
        if (data.getProfileImage().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_image);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 40,40, true);
        holder.userAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
