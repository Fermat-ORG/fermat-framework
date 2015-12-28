package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.holders.IssuerViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.interfaces.AdapterChangeListener;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.models.ActorIssuer;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IssuerCommunityAdapter extends FermatAdapter<ActorIssuer, IssuerViewHolder> {

    private AdapterChangeListener<ActorIssuer> adapterChangeListener;

    public IssuerCommunityAdapter(Context context) {
        super(context);
    }

    public IssuerCommunityAdapter(Context context, List<ActorIssuer> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected IssuerViewHolder createHolder(View itemView, int type) {
        return new IssuerViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_dap_issuer_community_actor;
    }

    @Override
    protected void bindHolder(final IssuerViewHolder holder, final ActorIssuer data, final int position) {
        try {
            if (data.getDapConnectionState() == DAPConnectionState.REGISTERED_ONLINE || data.getDapConnectionState() == DAPConnectionState.CONNECTED_ONLINE) {
                holder.status.setText(R.string.status_online);
                holder.status.setBackgroundColor(holder.status.getResources().getColor(R.color.background_status_online));
            }
            if (data.getDapConnectionState() == DAPConnectionState.REGISTERED_OFFLINE || data.getDapConnectionState() == DAPConnectionState.CONNECTED_OFFLINE) {
                holder.status.setText(R.string.status_offline);
                holder.status.setBackgroundColor(holder.status.getResources().getColor(R.color.background_status_offline));
            }
            if (data.getDapConnectionState() == DAPConnectionState.CONNECTING) {
                holder.status.setText(R.string.status_connecting);
            }

            holder.name.setText(data.getName());
            holder.connect.setChecked(data.selected);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataSet.get(position).selected = !dataSet.get(position).selected;
                    notifyItemChanged(position);
                    if (adapterChangeListener != null)
                        adapterChangeListener.onDataSetChanged(dataSet);
                }
            });

            byte[] profileImage = data.getProfileImage();

            if (profileImage != null) {
                if (profileImage.length > 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                    holder.thumbnail.setImageBitmap(bitmap);
                } else Picasso.with(context).load(R.drawable.profile_image_standard).into(holder.thumbnail);
            } else Picasso.with(context).load(R.drawable.profile_image_standard).into(holder.thumbnail);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAdapterChangeListener(AdapterChangeListener<ActorIssuer> adapterChangeListener) {
        this.adapterChangeListener = adapterChangeListener;
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
