package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.AppFriendsListHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by josemanueldsds on 30/11/15.
 */
public class AppFriendsListAdapter extends FermatAdapter<IntraUserInformation, AppFriendsListHolder> {

    public AppFriendsListAdapter(Context context, List<IntraUserInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppFriendsListHolder createHolder(View itemView, int type) {
        return new AppFriendsListHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_row_friend_list;
    }

    @Override
    protected void bindHolder(AppFriendsListHolder holder, IntraUserInformation data, int position) {

        holder.friendName.setText("Jeniffer");

        try {
            Picasso.with(context)
                    .load("http://www.garuyo.com/sites/default/files/jennifer-lawrence-signo-del-zodiaco-leo-1.jpg")
                    .placeholder(R.drawable.profile_image_round)
                    .into((Target) holder.friendAvatar);
        } catch (Exception ex) {

        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
