package org.fermat.fermat_dap_android_sub_app_asset_user_community.common.header;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;

/**
 * Created by Nerio on 07/03/16.
 */
public class UserAssetCommunityHeaderFactory extends UserAbstractCommunityHeaderFactory<UserAssetCommunityHeaderCLickListener>
        implements UserCommunityHeaderFactoryInterface {


    /**
     * UI components
     */
//    private CircularProgressBar circularProgressBar;
    private FermatTextView txt_type_balance;
    private FermatTextView txt_amount_type;
    private FermatTextView txt_balance_amount;


    public UserAssetCommunityHeaderFactory(ViewGroup mRootView) {
        super(mRootView);
    }

    @Override
    public void constructHeader(LayoutInflater layoutInflater, ViewGroup header) {
//        circularProgressBar = (CircularProgressBar) header.findViewById(R.id.progress);
//        txt_type_balance = (FermatTextView) header.findViewById(R.id.txt_type_balance);
//        txt_amount_type = (FermatTextView) header.findViewById(R.id.txt_balance_amount_type);
//        txt_balance_amount = (FermatTextView) header.findViewById(R.id.txt_balance_amount);
//
//        circularProgressBar.setProgressValue(20);
//        circularProgressBar.setProgressValue2(28);
//        circularProgressBar.setBackgroundProgressColor(Color.parseColor("#022346"));
//        circularProgressBar.setProgressColor(Color.parseColor("#05ddd2"));
//        circularProgressBar.setProgressColor2(Color.parseColor("#05537c"));
//
//        txt_type_balance.setOnClickListener(onClickListener);
//        txt_balance_amount.setOnClickListener(onClickListener);
//        txt_amount_type.setOnClickListener(onClickListener);
    }

    //
    @Override
    public String setParentBackgroundColor() {
        return "#06356f";
    }

    //
    @Override
    public int setLayoutId() {
        return 0;
//        return R.layout.donut_header;
    }

    //
    @Override
    protected UserAssetCommunityHeaderCLickListener setOnClickListener() {
        return new UserAssetCommunityHeaderCLickListener();
    }

}