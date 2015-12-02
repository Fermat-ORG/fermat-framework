package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.squareup.picasso.Picasso;

/**
 * Created by Matias Furszyfer on 2015.11.12..
 */
public class FragmentsCommons {


    public static View setUpHeaderScreen(LayoutInflater inflater,Activity activity,IntraUserLoginIdentity intraUserLoginIdentity) throws CantGetActiveLoginIdentityException {
        View view = inflater.inflate(R.layout.navigation_view_row_first, null, true);
        try {
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
            if (intraUserLoginIdentity != null) {
                if (intraUserLoginIdentity.getProfileImage() != null) {
                    if (intraUserLoginIdentity.getProfileImage().length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(intraUserLoginIdentity.getProfileImage(), 0, intraUserLoginIdentity.getProfileImage().length);
                        bitmap = Bitmap.createScaledBitmap(bitmap,imageView.getMaxWidth(),imageView.getMaxHeight(),true);
                        imageView.setImageBitmap(bitmap);
                    } else
                        Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
                }
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(intraUserLoginIdentity.getAlias());
            }

            return view;
        }catch (OutOfMemoryError outOfMemoryError){
            Toast.makeText(activity,"Error: out of memory ",Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
