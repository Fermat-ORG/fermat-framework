package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.holders.ContactsListHolder;

import java.util.List;

/**
 * Created by roy on 13/06/16.
 */
public class GeolocationAdapter extends FermatAdapter<ChatActorCommunityInformation, ContactsListHolder> {

    public GeolocationAdapter(Context context, List<ChatActorCommunityInformation> dataset){
        super(context, dataset);
    }

    @Override
    protected ContactsListHolder createHolder(View itemView, int type) {
        return new ContactsListHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_geolocation_results_item;
    }

    @Override
    protected void bindHolder(ContactsListHolder holder, ChatActorCommunityInformation data, int position) {
        if (data.getPublicKey() != null) {
            holder.friendName.setText(data.getAlias());
            if (data.getImage() != null && data.getImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                //holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
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
