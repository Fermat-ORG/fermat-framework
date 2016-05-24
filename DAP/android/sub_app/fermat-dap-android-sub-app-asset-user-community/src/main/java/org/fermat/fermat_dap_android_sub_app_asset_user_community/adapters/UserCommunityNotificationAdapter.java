package org.fermat.fermat_dap_android_sub_app_asset_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.holders.UserCommunityNotificationsHolder;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Actor;

import java.util.List;

/**
 * @author Jose Manuel De Sousa.
 */
public class UserCommunityNotificationAdapter extends FermatAdapter<Actor, UserCommunityNotificationsHolder> {

    public UserCommunityNotificationAdapter(Context context, List<Actor> lst) {
        super(context, lst);
    }

    @Override
    protected UserCommunityNotificationsHolder createHolder(View itemView, int type) {
        return new UserCommunityNotificationsHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_user_community_row_connection_notification;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(UserCommunityNotificationsHolder holder, Actor data, int position) {
        if (data.getActorPublicKey() != null) {
            holder.userName.setText(data.getName());
            if (data.getProfileImage() != null && data.getProfileImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                holder.userAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }
        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
