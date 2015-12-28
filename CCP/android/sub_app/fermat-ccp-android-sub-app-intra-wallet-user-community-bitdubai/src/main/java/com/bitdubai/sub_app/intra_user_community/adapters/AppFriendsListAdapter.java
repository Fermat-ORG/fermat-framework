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

import java.util.List;

/**
 * @author Jose Manuel De Sousa
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
        return R.layout.row_connection_list;
    }

    @Override
    protected void bindHolder(AppFriendsListHolder holder, IntraUserInformation data, int position) {
        if (data.getPublicKey() != null) {
            holder.friendName.setText(data.getName());
            if (data.getProfileImage() != null) {
                Bitmap bitmap;
                if (data.getProfileImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_image);
                }
                bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }else{
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_image);
                bitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }
        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
