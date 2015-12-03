package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.AppNotificationsHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Arrays;

/**
 * Created by josemanueldsds on 29/11/15.
 */
public class AppNotificationAdapter extends FermatAdapter<IntraUserInformation, AppNotificationsHolder> {

    public AppNotificationAdapter(Context context) {
        super(context);
    }

    @Override
    protected AppNotificationsHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.wallet_notification_row;
    }

    @Override
    protected void bindHolder(AppNotificationsHolder holder, IntraUserInformation data, int position) {
        holder.userName.setText(data.getName());
        try {
            Picasso.with(context)
                    .load(Arrays.toString(data.getProfileImage()))
                    .placeholder(R.drawable.profile_image_round)
                    .into((Target) holder.userAvatar);
        } catch (Exception ex) {

        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
