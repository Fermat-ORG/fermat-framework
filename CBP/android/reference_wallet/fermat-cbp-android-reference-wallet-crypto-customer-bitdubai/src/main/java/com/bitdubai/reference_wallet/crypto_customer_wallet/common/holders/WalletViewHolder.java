package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by nelson on 28/12/15.
 */
public class WalletViewHolder extends SingleDeletableItemViewHolder<InstalledWallet> {
    private FermatTextView title;
    private FermatTextView subTitle;


    public WalletViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.ccw_title);
        subTitle = (FermatTextView) itemView.findViewById(R.id.ccw_sub_title);
    }

    @Override
    public void bind(InstalledWallet data) {
        subTitle.setText(data.getWalletName());
        title.setText(getPlatformTitle(data.getPlatform()));
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.ccw_close_img_button;
    }

    private String getPlatformTitle(Platforms platform) {
        if (platform.equals(Platforms.BANKING_PLATFORM))
            return "Bank";
        if (platform.equals(Platforms.CASH_PLATFORM))
            return "Cash";

        return "Crypto";
    }
}
