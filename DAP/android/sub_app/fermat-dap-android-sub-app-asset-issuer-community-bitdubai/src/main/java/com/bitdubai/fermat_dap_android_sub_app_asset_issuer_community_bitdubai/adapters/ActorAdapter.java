package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.holders.ActorViewHolder;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

import java.util.List;

/**
 * Created by francisco on 14/10/15.
 */
public class ActorAdapter extends FermatAdapter<ActorAssetIssuer, ActorViewHolder> {

    public ActorAdapter(Context context) {
        super(context);
    }

    public ActorAdapter(Context context, List<ActorAssetIssuer> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ActorViewHolder createHolder(View itemView, int type) {
        return new ActorViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_dap_issuer_community_actor;
    }

    @Override
    protected void bindHolder(ActorViewHolder holder, ActorAssetIssuer data, int position) {
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
