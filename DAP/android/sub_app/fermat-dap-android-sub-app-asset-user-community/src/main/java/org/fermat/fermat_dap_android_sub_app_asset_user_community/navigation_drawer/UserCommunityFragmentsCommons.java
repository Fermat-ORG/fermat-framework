package org.fermat.fermat_dap_android_sub_app_asset_user_community.navigation_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.squareup.picasso.Picasso;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;

/**
 * @author Created by mati on 2015.11.12..
 * @author Modified byJose Manuel De Sousa 08/12/2015
 */
public class UserCommunityFragmentsCommons {

    public static View setUpHeaderScreen(LayoutInflater inflater, Context activity, ActiveActorIdentityInformation identityAssetUser) throws CantGetIdentityAssetUserException {
        /**
         * Navigation view header
         */
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        relativeLayout.setLayoutParams(layoutParams);
        View view = inflater.inflate(R.layout.dap_navigation_drawer_community_user_header, relativeLayout, true);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
        if (identityAssetUser != null) {
            if (identityAssetUser.getImage() != null) {
                if (identityAssetUser.getImage().length > 0) {
                    BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView, activity.getResources(), 0, false);
                    bitmapWorkerTask.execute(identityAssetUser.getImage());
//                    imageView.setImageBitmap((BitmapFactory.decodeByteArray(identityAssetUser.getImage(), 0, identityAssetUser.getImage().length)));
                } else
                    Picasso.with(activity).load(R.drawable.asset_user_comunity).into(imageView);
            } else
                Picasso.with(activity).load(R.drawable.asset_user_comunity).into(imageView);
            FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
            fermatTextView.setText(identityAssetUser.getAlias());
        }
        return view;
    }
}
