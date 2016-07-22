package com.bitdubai.sub_app.intra_user_community.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by Gian Barboza on 14/07/16.
 */

public class AvailableIntraUserViewHolder extends FermatViewHolder {

    private ImageView imageView_avatar;
    private FermatTextView username;
    private FermatTextView location;
    private Resources res;

    public AvailableIntraUserViewHolder(View itemView, int holderType) {
        super(itemView, holderType);
        res = itemView.getResources();

        imageView_avatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
        username = (FermatTextView) itemView.findViewById(R.id.username);
        location = (FermatTextView) itemView.findViewById(R.id.location);

    }

    public void bind (IntraUserInformation data){
        if (data.getPublicKey() != null) {
            username.setText(data.getName());
            location.setText("Not Location");
            if (data.getProfileImage() != null && data.getProfileImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                imageView_avatar.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));

            }
        }
    }
}
