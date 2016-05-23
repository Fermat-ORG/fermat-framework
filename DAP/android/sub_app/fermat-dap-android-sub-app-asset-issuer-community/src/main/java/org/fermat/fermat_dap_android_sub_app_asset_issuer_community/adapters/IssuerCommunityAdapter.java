package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.squareup.picasso.Picasso;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.holders.IssuerViewHolder;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.interfaces.AdapterChangeListener;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.models.ActorIssuer;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;

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
            holder.name.setText(String.format("%s", data.getRecord().getName()));
            if (data.getRecord().getExtendedPublicKey() != null) {
                holder.connectedStateConnected.setVisibility(View.VISIBLE);
                holder.connectedStateDenied.setVisibility(View.GONE);
                holder.connectedStateWaiting.setVisibility(View.GONE);
                holder.connect.setVisibility(View.GONE);
            } else {
                switch (data.getRecord().getDapConnectionState()){
                    case CONNECTING:
                    case PENDING_LOCALLY:
                    case PENDING_REMOTELY:
                        holder.connectedStateWaiting.setVisibility(View.VISIBLE);
                        holder.connectedStateDenied.setVisibility(View.GONE);
                        break;
                    case DENIED_LOCALLY:
                    case DENIED_REMOTELY:
                    case CANCELLED_LOCALLY:
                    case CANCELLED_REMOTELY:
                        holder.connectedStateWaiting.setVisibility(View.GONE);
                        holder.connectedStateDenied.setVisibility(View.VISIBLE);
                        break;
                    default:
                        holder.connectedStateWaiting.setVisibility(View.GONE);
                        holder.connectedStateDenied.setVisibility(View.GONE);

                }
                holder.connectedStateConnected.setVisibility(View.GONE);
                holder.connect.setVisibility(View.VISIBLE);
            }

            if (data.getRecord().getDapConnectionState() == DAPConnectionState.REGISTERED_ONLINE || data.getRecord().getDapConnectionState() == DAPConnectionState.CONNECTED_ONLINE) {
                holder.status.setText(R.string.status_online);
                holder.status.setBackgroundColor(holder.status.getResources().getColor(R.color.background_status_online));
            }
            if (data.getRecord().getDapConnectionState() == DAPConnectionState.REGISTERED_OFFLINE || data.getRecord().getDapConnectionState() == DAPConnectionState.CONNECTED_OFFLINE) {
                holder.status.setText(R.string.status_offline);
                holder.status.setBackgroundColor(holder.status.getResources().getColor(R.color.background_status_offline));
            }
            if (data.getRecord().getDapConnectionState() == DAPConnectionState.CONNECTING) {
                holder.status.setText(R.string.status_connecting);
            }

            if (data.getRecord().getDapConnectionState() == DAPConnectionState.DENIED_LOCALLY || data.getRecord().getDapConnectionState() == DAPConnectionState.DENIED_REMOTELY) {
                holder.status.setText(R.string.status_denied);
            }

            if (data.getRecord().getDapConnectionState() == DAPConnectionState.CANCELLED_LOCALLY || data.getRecord().getDapConnectionState() == DAPConnectionState.CANCELLED_REMOTELY) {
                holder.status.setText(R.string.status_canceled);
            }

            holder.name.setText(data.getRecord().getName());
            holder.connect.setChecked(data.selected);
            holder.connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataSet.get(position).selected = !dataSet.get(position).selected;
                    notifyItemChanged(position);
                    if (adapterChangeListener != null)
                        adapterChangeListener.onDataSetChanged(dataSet);
                }
            });

            byte[] profileImage = data.getRecord().getProfileImage();

            if (profileImage != null) {
                if (profileImage.length > 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                    holder.thumbnail.setImageBitmap(bitmap);
                } else Picasso.with(context).load(R.drawable.asset_issuer_comunity).into(holder.thumbnail);
            } else Picasso.with(context).load(R.drawable.asset_issuer_comunity).into(holder.thumbnail);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAdapterChangeListener(AdapterChangeListener<ActorIssuer> adapterChangeListener) {
        this.adapterChangeListener = adapterChangeListener;
    }

    public AdapterChangeListener<ActorIssuer> getAdapterChangeListener() {
        return adapterChangeListener;
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
