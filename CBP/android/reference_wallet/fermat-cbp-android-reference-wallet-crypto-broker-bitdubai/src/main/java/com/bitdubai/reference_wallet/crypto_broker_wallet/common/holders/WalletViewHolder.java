package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CBPInstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;


/**
 * Created by nelson on 28/12/15.
 */
//public class WalletViewHolder extends SingleDeletableItemViewHolder<InstalledWallet> {
public class WalletViewHolder extends SingleDeletableItemViewHolder<CBPInstalledWallet> {
    private FermatTextView title;
    private FermatTextView subTitle;
    StringBuilder stringBuilder;

    public WalletViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.cbw_title);
        subTitle = (FermatTextView) itemView.findViewById(R.id.cbw_sub_title);
    }

    @Override
//    public void bind(InstalledWallet data) {
    public void bind(CBPInstalledWallet data) {
        stringBuilder = new StringBuilder();
        stringBuilder.append(data.getWalletName())
                .append(" ( ")
                .append(data.getCurrency().getCode())
                .append(" )");
        subTitle.setText(stringBuilder.toString());
        title.setText(getPlatformTitle(data.getPlatform()).toUpperCase());
    }

    @Override
    public int getCloseButtonResource() {
        return R.id.cbw_close_img_button;
    }

    private String getPlatformTitle(Platforms platform) {
        if (platform.equals(Platforms.BANKING_PLATFORM))
            return "Bank";
        if (platform.equals(Platforms.CASH_PLATFORM))
            return "Cash";

        return "Crypto";
    }
}
