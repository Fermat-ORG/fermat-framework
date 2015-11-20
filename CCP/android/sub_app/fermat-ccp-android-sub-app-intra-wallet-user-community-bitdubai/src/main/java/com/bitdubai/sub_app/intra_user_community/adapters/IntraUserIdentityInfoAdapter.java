package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.common.UtilsFuncs;
import com.bitdubai.sub_app.intra_user_community.holders.CryptoBrokerIdentityInfoViewHolder;


import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class IntraUserIdentityInfoAdapter extends FermatAdapter<IntraUserInformation, CryptoBrokerIdentityInfoViewHolder> {


    public IntraUserIdentityInfoAdapter(Context context, ArrayList<IntraUserInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected CryptoBrokerIdentityInfoViewHolder createHolder(View itemView, int type) {
        return new CryptoBrokerIdentityInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_identity_list_item;
    }

    @Override
    protected void bindHolder(final CryptoBrokerIdentityInfoViewHolder holder, final IntraUserInformation data, final int position) {
        holder.getIdentityName().setText(data.getName());

        byte[] profileImage = data.getProfileImage();
        Bitmap imageBitmap = profileImage == null ?
                BitmapFactory.decodeResource(context.getResources(), R.drawable.deniz_profile_picture) :
                BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);

        Bitmap roundedBitmap = UtilsFuncs.getRoundedShape(imageBitmap);
        holder.getIdentityImage().setImageBitmap(roundedBitmap);
    }
}
