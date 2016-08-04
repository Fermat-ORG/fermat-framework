package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.holders.NotificationHolder;

import java.util.List;

/**
 * NotificationAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
public class NotificationAdapter
        extends FermatAdapter<ChatActorCommunityInformation, NotificationHolder> {

    public NotificationAdapter(Context context, List<ChatActorCommunityInformation> lst) {
        super(context, lst);
    }

    @Override
    protected NotificationHolder createHolder(View itemView, int type) {
        return new NotificationHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_notification_item;
    }

    @Override
    protected void bindHolder(NotificationHolder holder, ChatActorCommunityInformation data, int position) {
        if (data.getPublicKey() != null) {
            holder.userName.setText(data.getAlias());
            if (data.getImage() != null && data.getImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                //holder.userAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
                holder.date.setText("");
            }
        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
