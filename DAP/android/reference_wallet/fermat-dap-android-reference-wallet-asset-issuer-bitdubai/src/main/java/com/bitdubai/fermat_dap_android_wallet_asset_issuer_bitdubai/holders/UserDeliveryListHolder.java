package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.UserDelivery;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/23/15.
 */
public class UserDeliveryListHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;

    private final FermatTextView userName;
    private final FermatTextView deliveryDate;
    private final FermatTextView deliveryStatus;

    /**
     * Constructor
     *
     * @param itemView
     */
    public UserDeliveryListHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;

        userName = (FermatTextView) itemView.findViewById(R.id.userName);
        deliveryDate = (FermatTextView) itemView.findViewById(R.id.deliveryDate);
        deliveryStatus = (FermatTextView) itemView.findViewById(R.id.deliveryStatus);
    }

    public void bind(final UserDelivery userDelivery) {
        userName.setText(userDelivery.getUserName());
        deliveryDate.setText(userDelivery.getFormattedDeliveryDate());
        deliveryStatus.setText(userDelivery.getDeliveryStatus());
    }
}
