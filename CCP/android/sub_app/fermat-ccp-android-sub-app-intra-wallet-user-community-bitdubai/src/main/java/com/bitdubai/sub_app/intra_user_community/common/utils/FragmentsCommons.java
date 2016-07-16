package com.bitdubai.sub_app.intra_user_community.common.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.sub_app.intra_user_community.R;

import com.squareup.picasso.Picasso;

/**
 * @author Created by mati on 2015.11.12..
 * @author Modified byJose Manuel De Sousa 08/12/2015
 */
public class FragmentsCommons {
    public static final String CONNECTION_RESULT = "connection_result";
    public static final int SEARCH_FILTER_OPTION_MENU_ID = 1;
    public static final int LOCATION_FILTER_OPTION_MENU_ID = 2;
    public static final int HELP_OPTION_MENU_ID = 3;
    public static final int CBC_BACKGROUND_TAB_ID = 4;

    public static View setUpHeaderScreen(LayoutInflater inflater, Context activity, ReferenceAppFermatSession<IntraUserModuleManager> intraUserSubAppSession,final FermatApplicationCaller applicationsHelper) throws CantGetActiveLoginIdentityException {
        /**
         * Navigation view header
         *
         */
        RelativeLayout relativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        relativeLayout.setLayoutParams(layoutParams);
        View view = inflater.inflate(R.layout.row_navigation_drawer_community_header, relativeLayout, true);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
        try
        {
            IntraUserLoginIdentity identity = intraUserSubAppSession.getModuleManager().getActiveIntraUserIdentity();


            if (identity != null) {
                if (identity.getProfileImage() != null) {
                    if (identity.getProfileImage().length > 0) {
                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView,activity.getResources(),0,false);
                        bitmapWorkerTask.execute(identity.getProfileImage());
                    } else
                        Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
                } else
                    Picasso.with(activity).load(R.drawable.profile_image).into(imageView);
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(identity.getAlias());
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CCP_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch(Exception e)
        {

        }

        return view;
    }
}
