package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.WalletsAdapter;

/**
 * Created by nelson on 28/12/15.
 */
public class WalletViewHolder extends FermatViewHolder {
    private FermatTextView title;
    private FermatTextView subTitle;
    private ImageView closeButton;


    /**
     * Constructor
     *
     * @param itemView
     */
    public WalletViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.cbw_title);
        subTitle = (FermatTextView) itemView.findViewById(R.id.cbw_sub_title);
        closeButton = (ImageView) itemView.findViewById(R.id.cbw_close_img_button);
    }

    public void bind(InstalledWallet data) {
        subTitle.setText(data.getWalletName());
        title.setText(getPlatformTitle(data.getPlatform()));
    }

    public ImageView getCloseButton(){
        return closeButton;
    }

    private String getPlatformTitle(Platforms platform) {
        if (platform.equals(Platforms.BANKING_PLATFORM))
            return "Bank";
        if (platform.equals(Platforms.CASH_PLATFORM))
            return "Cash";

        return "Crypto";
    }
}
