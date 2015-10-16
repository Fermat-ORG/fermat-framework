package com.bitdubai.sub_app.crypto_broker_identity.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.common.holders.CryptoBrokerIdentityInfoViewHolder;
import com.bitdubai.sub_app.crypto_broker_identity.util.UtilsFuncs;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class CryptoBrokerIdentityInfoAdapter extends FermatAdapter<CryptoBrokerIdentityInformation, CryptoBrokerIdentityInfoViewHolder> {


    public CryptoBrokerIdentityInfoAdapter(Context context, ArrayList<CryptoBrokerIdentityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected CryptoBrokerIdentityInfoViewHolder createHolder(View itemView, int type) {
        return new CryptoBrokerIdentityInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.crypto_broker_identity_list_item;
    }

    @Override
    protected void bindHolder(final CryptoBrokerIdentityInfoViewHolder holder, final CryptoBrokerIdentityInformation data, final int position) {
        holder.getIdentityName().setText(data.getName());

        byte[] profileImage = data.getProfileImage();
        Bitmap imageBitmap = profileImage == null ?
                BitmapFactory.decodeResource(context.getResources(), R.drawable.deniz_profile_picture) :
                BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);

        //Bitmap roundedBitmap = UtilsFuncs.getRoundedShape(imageBitmap);
        holder.getIdentityImage().setImageBitmap(imageBitmap);
    }
}
