package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.holders.ActorViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Actor;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by francisco on 14/10/15.
 */
public class ActorAdapter extends FermatAdapter<Actor, ActorViewHolder> {

    public ActorAdapter(Context context) {
        super(context);
    }

    public ActorAdapter(Context context, List<Actor> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected ActorViewHolder createHolder(View itemView, int type) {
        return new ActorViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_actor;
    }

    @Override
    protected void bindHolder(ActorViewHolder holder, Actor data, int position) {
        try {
            holder.name.setText(data.getName());
            Picasso.with(context)
                    .load(data.getThumbnail())
                    .into(holder.thumbnail);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
