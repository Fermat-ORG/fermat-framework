package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

import java.util.List;

/**
 * NotificationAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class NotificationAdapter
        extends FermatAdapter<IntraUserInformation, NotificationHolder> {

    public NotificationAdapter(Context context, List<IntraUserInformation> lst) {
        super(context, lst);
    }

    @Override
    protected NotificationAdapter createHolder(View itemView, int type) {
        return new NotificationAdapter(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_connection_notification;
    }

    @Override
    protected void bindHolder(NotificationHolder holder, IntraUserInformation data, int position) {
        if (data.getPublicKey() != null) {
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
