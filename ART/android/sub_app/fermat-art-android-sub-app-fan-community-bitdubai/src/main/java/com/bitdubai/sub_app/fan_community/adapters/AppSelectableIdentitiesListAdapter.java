package com.bitdubai.sub_app.fan_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.sub_app.fan_community.R;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class AppSelectableIdentitiesListAdapter extends
        FermatAdapter<
                FanCommunitySelectableIdentity,
                AppSelectableIdentitiesListAdapter.AppSelectableIdentityHolder> {

    public AppSelectableIdentitiesListAdapter(
            Context context,
            List<FanCommunitySelectableIdentity> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AppSelectableIdentityHolder createHolder(View itemView, int type) {
        return new AppSelectableIdentityHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.afc_row_connection_list;
    }

    @Override
    protected void bindHolder(
            AppSelectableIdentityHolder holder,
            FanCommunitySelectableIdentity data,
            int position) {

        if (data.getPublicKey() != null) {
            holder.friendName.setText(data.getAlias());
            if (data.getImage() != null) {
                Bitmap bitmap;
                if (data.getImage().length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(
                            data.getImage(), 0, data.getImage().length);
                } else {
                    bitmap = BitmapFactory.decodeResource(
                            context.getResources(), R.drawable.afc_profile_image);
                }
            //    bitmap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                holder.friendAvatar.setImageDrawable(
                        ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }else{
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeResource(
                        context.getResources(), R.drawable.afc_profile_image);
            //    bitmap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);
                holder.friendAvatar.setImageDrawable(
                        ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }
        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }

    public class AppSelectableIdentityHolder extends FermatViewHolder {

        public ImageView friendAvatar;
        public FermatTextView friendName;

        /**
         * Default Constructor
         *
         * @param itemView cast ui elements
         */
        public AppSelectableIdentityHolder(View itemView) {
            super(itemView);

            friendName = (FermatTextView) itemView.findViewById(
                    R.id.afc_selectable_identity_username);
            friendAvatar = (ImageView) itemView.findViewById(
                    R.id.afc_selectable_identity_user_avatar);
        }
    }

}

