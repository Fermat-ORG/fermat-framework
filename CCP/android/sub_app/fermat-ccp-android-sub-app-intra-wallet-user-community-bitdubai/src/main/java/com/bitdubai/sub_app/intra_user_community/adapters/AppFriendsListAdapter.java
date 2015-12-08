package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
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

        holder.friendName.setText(data.getName());

        try {
            if(data.getProfileImage()!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(),0,data.getProfileImage().length);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(),bitmap));
            }else
            Picasso.with(context)
                    .load(R.id.profile_Image)
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
