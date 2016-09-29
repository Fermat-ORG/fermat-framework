package com.bitdubai.sub_app.intra_user_community.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Gian Barboza on 14/07/16.
 */

public class AvailableIntraUserViewHolder extends FermatViewHolder {

    private ImageView imageView_avatar;
    private FermatTextView username;
    private FermatTextView location;
    private Resources res;
    private Context context;

    public AvailableIntraUserViewHolder(View itemView, int holderType, Context context) {
        super(itemView, holderType);
        res = itemView.getResources();
            this.context = context;
        imageView_avatar = (ImageView) itemView.findViewById(R.id.imageView_avatar);
        username = (FermatTextView) itemView.findViewById(R.id.username);
        location = (FermatTextView) itemView.findViewById(R.id.location);

    }

    public void bind (IntraUserInformation data){

        if (data.getPublicKey() != null) {

            username.setText(data.getName());

            String city = data.getCity();
            String country = data.getCountry();

            if (city!=null && country!=null)
                if (!city.equals("----") && !country.equals("----"))
                    if (country!=null &&(city==null || city=="----" || city==""))
                        location.setText(country);
                    else if (city!=null &&(country==null || country=="----" || country==""))
                        location.setText(city);
                    else location.setText(country+", "+city);
                else location.setText("No Location");
            else location.setText("No Location");


            if (data.getProfileImage() != null && data.getProfileImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                imageView_avatar.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));

            }
            else
            {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_profile_male);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                imageView_avatar.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));
            }
        }
    }
}
