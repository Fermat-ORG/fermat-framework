package org.fermat.fermat_dap_android_sub_app_asset_user_community.navigation_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.squareup.picasso.Picasso;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

/**
 * @author Created by mati on 2015.11.12..
 * @author Modified byJose Manuel De Sousa 08/12/2015
 */
public class UserCommunityFragmentsCommons {

    public static View setUpHeaderScreen(LayoutInflater inflater,
                                         Context activity,
                                         ReferenceAppFermatSession<AssetUserCommunitySubAppModuleManager> assetUserCommunitySubAppSession,
                                         final FermatApplicationCaller applicationsHelper) throws CantGetIdentityAssetUserException {
        /**
         * Navigation view header
         */
        View view = inflater.inflate(R.layout.dap_navigation_drawer_community_user_header, null, true);
        try {
            ActiveActorIdentityInformation identityInformation = assetUserCommunitySubAppSession.getModuleManager().getActiveAssetUserIdentity();

            RelativeLayout relativeLayout = new RelativeLayout(activity);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
            relativeLayout.setLayoutParams(layoutParams);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
            if (identityInformation != null) {
                if (identityInformation.getImage() != null) {
                    if (identityInformation.getImage().length > 0) {
                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView, activity.getResources(), 0, false);
                        bitmapWorkerTask.execute(identityInformation.getImage());
                    } else
                        Picasso.with(activity).load(R.drawable.banner_asset_user_community).into(imageView);
                } else
                    Picasso.with(activity).load(R.drawable.banner_asset_user_community).into(imageView);
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(identityInformation.getAlias());
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.DAP_IDENTITY_USER.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(activity, "Error: out of memory ", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
