package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.AppWorldHolder;

import java.util.List;

public class AppListAdapter extends FermatAdapter<IntraUserInformation, AppWorldHolder> {


    public AppListAdapter(Context context) {
        super(context);
    }

    public AppListAdapter(Context context, List<IntraUserInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppWorldHolder createHolder(View itemView, int type) {
        return new AppWorldHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_connections_world;
    }

    @Override
    protected void bindHolder(AppWorldHolder holder, IntraUserInformation data, int position) {
        holder.connectionState.setVisibility(View.GONE);
        ConnectionState connectionState = data.getConnectionState();
        switch (connectionState) {
            case CONNECTED:
                if (holder.connectionState.getVisibility() == View.GONE)
                    holder.connectionState.setVisibility(View.VISIBLE);
                break;
            case BLOCKED_LOCALLY:
            case BLOCKED_REMOTELY:
            case CANCELLED_LOCALLY:
            case CANCELLED_REMOTELY:
            case NO_CONNECTED:
            case DENIED_LOCALLY:
            case DENIED_REMOTELY:
            case DISCONNECTED_LOCALLY:
            case DISCONNECTED_REMOTELY:
            case ERROR:
            case INTRA_USER_NOT_FOUND:
            case PENDING_LOCALLY_ACCEPTANCE:
            case PENDING_REMOTELY_ACCEPTANCE:
            default:
                if (holder.connectionState.getVisibility() == View.VISIBLE)
                    holder.connectionState.setVisibility(View.GONE);
                break;
        }
        holder.name.setText(data.getName());
        byte[] profileImage = data.getProfileImage();
        if (profileImage != null && profileImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
            holder.thumbnail.setImageBitmap(bitmap);
        }

    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}