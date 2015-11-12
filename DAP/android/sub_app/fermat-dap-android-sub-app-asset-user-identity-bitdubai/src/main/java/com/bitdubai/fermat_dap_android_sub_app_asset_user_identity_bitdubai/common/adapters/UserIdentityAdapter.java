package com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.common.holders.UserIdentityViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.util.UtilsFuncs;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class UserIdentityAdapter extends FermatAdapter<IdentityAssetUser, UserIdentityViewHolder> {


    public UserIdentityAdapter(Context context, ArrayList<IdentityAssetUser> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected UserIdentityViewHolder createHolder(View itemView, int type) {
        return new UserIdentityViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_user_identity_list_item;
    }

    @Override
    protected void bindHolder(final UserIdentityViewHolder holder, final IdentityAssetUser data, final int position) {
        holder.getIdentityName().setText(data.getAlias() != null ? data.getAlias() : null);

        byte[] profileImage = data.getProfileImage();
        Bitmap imageBitmap = profileImage == null ?
                BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_image) :
                BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);

        Bitmap roundedBitmap = UtilsFuncs.getRoundedShape(imageBitmap);
        holder.getIdentityImage().setImageBitmap(roundedBitmap);
    }
}
