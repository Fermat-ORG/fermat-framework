package com.bitdubai.reference_wallet.crypto_customer_wallet.util;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.squareup.picasso.Picasso;

/**
 * Created by nelson on 16/11/15.
 */
public class FragmentsCommons {


    public static View setUpHeaderScreen(LayoutInflater inflater,Activity activity, ActorIdentity identity) throws CantGetActiveLoginIdentityException {
        View view = inflater.inflate(R.layout.navigation_view_row_first, null, true);
        try {
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
            if (identity != null) {
                if (identity.getProfileImage() != null) {
                    if (identity.getProfileImage().length > 0) {
                        imageView.setImageBitmap((BitmapFactory.decodeByteArray(identity.getProfileImage(), 0, identity.getProfileImage().length)));
                    } else
                        Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
                }
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(identity.getAlias());
            }

            return view;
        }catch (OutOfMemoryError outOfMemoryError){
            Toast.makeText(activity, "Error: out of memory ", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
