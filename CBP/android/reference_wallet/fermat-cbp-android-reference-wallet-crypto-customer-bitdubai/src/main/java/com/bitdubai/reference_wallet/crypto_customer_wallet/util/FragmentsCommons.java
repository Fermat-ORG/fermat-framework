package com.bitdubai.reference_wallet.crypto_customer_wallet.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.squareup.picasso.Picasso;

/**
 * Created by nelson on 16/11/15.
 */
public class FragmentsCommons {

    public static final String CONTRACT_DATA = "CONTRACT_DATA";
    public static final String NEGOTIATION_DATA = "NEGOTIATION_DATA";
    public static final String CONFIGURED_DATA = "CONFIGURED_DATA";
    public static final String LOCATION_LIST = "LOCATION_LIST";
    public static final String BANK_ACCOUNT_LIST = "BANK_ACCOUNT_LIST";
    public static final String BROKER_ACTOR = "BROKER_ACTOR";
    public static final String CURRENCY_TO_BUY = "CURRENCY_TO_BUY";
    public static final String QUOTES = "QUOTES";
    public static final String EXCHANGE_RATES = "EXCHANGE_RATES";
    public static final String NEGOTIATION_ID = "NEGOTIATION_ID";
    public static final String LAST_ACTIVITY = "LAST_ACTIVITY";
    public static final int CANCEL_NEGOTIATION_OPTION_MENU_ID = 1;
    public static final int CONTRACT_HISTORY_FILTER_OPTION_MENU_ID = 2;
    public static final int NO_FILTER_OPTION_MENU_ID = 3;
    public static final int SUCCEEDED_FILTER_OPTION_MENU_ID = 4;
    public static final int CANCELED_FILTER_OPTION_MENU_ID = 5;
    public static final int START_NEGOTIATION_OPTION_MENU_ID = 6;
    public static final int REQUEST_QUOTES_OPTION_MENU_ID = 7;
    public static final int OPEN_CUSTOMER_IDENTITY_APP_OPTION_MENU_ID = 8;
    public static final int OPEN_BROKER_COMMUNITY_APP_OPTION_MENU_ID = 9;

    public static int getClauseNumberImageRes(int clauseNumber) {
        switch (clauseNumber) {
            case 1:
                return R.drawable.bg_detail_number_01;
            case 2:
                return R.drawable.bg_detail_number_02;
            case 3:
                return R.drawable.bg_detail_number_03;
            case 4:
                return R.drawable.bg_detail_number_04;
            case 5:
                return R.drawable.bg_detail_number_05;
            case 6:
                return R.drawable.bg_detail_number_06;
            case 7:
                return R.drawable.bg_detail_number_07;
            case 8:
                return R.drawable.bg_detail_number_08;
            default:
                return R.drawable.bg_detail_number_09;
        }
    }

    public static View setUpHeaderScreen(LayoutInflater inflater, Context activity, CryptoCustomerIdentity identity,
                                         final FermatApplicationCaller applicationsHelper) throws CantGetActiveLoginIdentityException {

        RelativeLayout relativeLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
        relativeLayout.setLayoutParams(layoutParams);
        View view = inflater.inflate(R.layout.ccw_navigation_view_header, relativeLayout, true);
        try {
            ImageView imageView = (ImageView) view.findViewById(R.id.ccw_image_view_profile);
            if (identity != null) {
                if (identity.getProfileImage() != null) {
                    if (identity.getProfileImage().length > 0) {
                        imageView.setImageBitmap((BitmapFactory.decodeByteArray(identity.getProfileImage(), 0, identity.getProfileImage().length)));
                    } else
                        Picasso.with(activity).load(R.drawable.profile_image_standard).into(imageView);
                }
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(identity.getAlias());
            } else {
                Picasso.with(activity).load(R.drawable.profile_image_standard).into(imageView);
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(R.string.ccw_identity_alias_default_text);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CBP_CUSTOMER_IDENTITY.getCode());
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
