package com.bitdubai.android_core.app.common.version_1.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.common.UtilsFuncs;
import com.bitdubai.sub_app.intra_user_community.holders.CryptoBrokerIdentityInfoViewHolder;

import java.util.ArrayList;

/**
 * Created by mati on 2015.10.26..
 */
public class NavigationDrawerAdapter extends FermatAdapter<MenuItem,MenuItemHolder> {

    public NavigationDrawerAdapter(Context context, ArrayList<MenuItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected MenuItemHolder createHolder(View itemView, int type) {
        return new MenuItemHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return com.bitdubai.sub_app.intra_user_community.R.layout.intra_user_identity_list_item;
    }

    @Override
    protected void bindHolder(final MenuItemHolder holder, final MenuItem data, final int position) {
        holder.getName().setText(data.getLabel());

//        byte[] profileImage = data.getProfileImage();
//        Bitmap imageBitmap = profileImage == null ?
//                BitmapFactory.decodeResource(context.getResources(), com.bitdubai.sub_app.intra_user_community.R.drawable.deniz_profile_picture) :
//                BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
//
//        Bitmap roundedBitmap = UtilsFuncs.getRoundedShape(imageBitmap);
//        holder.getIdentityImage().setImageBitmap(roundedBitmap);
    }
}
