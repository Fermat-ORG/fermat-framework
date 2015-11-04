package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.holders.ActorViewHolder;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.List;

/**
 * Created by Nerio on 21/10/15.
 */
public class ActorAdapter extends FermatAdapter<ActorAssetRedeemPoint, ActorViewHolder> {

    public ActorAdapter(Context context) {
        super(context);
    }

    public ActorAdapter(Context context, List<ActorAssetRedeemPoint> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ActorViewHolder createHolder(View itemView, int type) {
        return new ActorViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_dap_redeem_point_actor;
    }

    @Override
    protected void bindHolder(ActorViewHolder holder, ActorAssetRedeemPoint data, int position) {
        try {
            holder.name.setText(data.getName());
            if (data.getProfileImage() != null && data.getProfileImage().length > 0) {
                holder.thumbnail.setImageDrawable(new BitmapDrawable(context.getResources(),
                        BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
