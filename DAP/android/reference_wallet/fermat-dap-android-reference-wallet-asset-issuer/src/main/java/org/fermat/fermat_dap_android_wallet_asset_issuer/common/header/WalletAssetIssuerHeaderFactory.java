package org.fermat.fermat_dap_android_wallet_asset_issuer.common.header;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.CircularProgressBar;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

/**
 * Created by Matias Furszyfer on 2015.11.16..
 */
public class WalletAssetIssuerHeaderFactory extends HeaderFactory<WalletAssetIssuerHeaderClickListener> implements HeaderFactoryInterface {

    /**
     * UI components
     */
    private CircularProgressBar circularProgressBar;
    private FermatTextView txt_type_balance;
    private FermatTextView txt_amount_type;
    private FermatTextView txt_balance_amount;

    public WalletAssetIssuerHeaderFactory(ViewGroup mRootView) {
        super(mRootView);
    }

    @Override
    public void constructHeader(LayoutInflater layoutInflater, ViewGroup header) {
        circularProgressBar = (CircularProgressBar) header.findViewById(R.id.progress);
        txt_type_balance = (FermatTextView) header.findViewById(R.id.txt_type_balance);
        txt_amount_type = (FermatTextView) header.findViewById(R.id.txt_balance_amount_type);
        txt_balance_amount = (FermatTextView) header.findViewById(R.id.txt_balance_amount);

        circularProgressBar.setProgressValue(20);
        circularProgressBar.setProgressValue2(28);
        circularProgressBar.setBackgroundProgressColor(Color.parseColor("#022346"));
        circularProgressBar.setProgressColor(Color.parseColor("#05ddd2"));
        circularProgressBar.setProgressColor2(Color.parseColor("#05537c"));

        txt_type_balance.setOnClickListener(onClickListener);
        txt_balance_amount.setOnClickListener(onClickListener);
        txt_amount_type.setOnClickListener(onClickListener);
    }

    @Override
    public String setParentBackgroundColor() {
        return "#06356f";
    }

    @Override
    public int setLayoutId() {
        return R.layout.donut_header;
    }

    @Override
    protected WalletAssetIssuerHeaderClickListener setOnClickListener() {
        return new WalletAssetIssuerHeaderClickListener();
    }


}
