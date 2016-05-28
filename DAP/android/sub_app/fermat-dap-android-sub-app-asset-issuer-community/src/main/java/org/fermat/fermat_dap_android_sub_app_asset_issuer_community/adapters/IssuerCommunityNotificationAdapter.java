package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.holders.IssuerCommunityNotificationsHolder;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.models.ActorIssuer;

import java.util.List;

/**
 * @author Jose Manuel De Sousa.
 */
public class IssuerCommunityNotificationAdapter extends FermatAdapter<ActorIssuer, IssuerCommunityNotificationsHolder> {

    public IssuerCommunityNotificationAdapter(Context context, List<ActorIssuer> lst) {
        super(context, lst);
    }

    @Override
    protected IssuerCommunityNotificationsHolder createHolder(View itemView, int type) {
        return new IssuerCommunityNotificationsHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_issuer_community_row_connection_notification;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(IssuerCommunityNotificationsHolder holder, ActorIssuer data, int position) {
        if (data.getRecord().getActorPublicKey() != null) {
            holder.userName.setText(data.getRecord().getName());
            if (data.getRecord().getProfileImage() != null && data.getRecord().getProfileImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getRecord().getProfileImage(), 0, data.getRecord().getProfileImage().length);
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
