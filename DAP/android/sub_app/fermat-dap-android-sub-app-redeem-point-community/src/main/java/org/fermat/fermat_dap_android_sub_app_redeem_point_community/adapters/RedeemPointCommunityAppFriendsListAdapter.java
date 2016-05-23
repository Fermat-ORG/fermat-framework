package org.fermat.fermat_dap_android_sub_app_redeem_point_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.holders.RedeemPointAppFriendsListHolder;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.models.Actor;

import java.util.List;

/**
 * Jinmy Bohorquez 13/02/2016
 */
public class RedeemPointCommunityAppFriendsListAdapter extends FermatAdapter<Actor, RedeemPointAppFriendsListHolder> {

    public RedeemPointCommunityAppFriendsListAdapter(Context context, List<Actor> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected RedeemPointAppFriendsListHolder createHolder(View itemView, int type) {
        return new RedeemPointAppFriendsListHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_redeem_point_row_connection_list;
    }

    @Override
    protected void bindHolder(RedeemPointAppFriendsListHolder holder, Actor data, int position) {
        if (data.getActorPublicKey() != null) {
            holder.friendName.setText(data.getName());
            if (data.getProfileImage() != null && data.getProfileImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
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
