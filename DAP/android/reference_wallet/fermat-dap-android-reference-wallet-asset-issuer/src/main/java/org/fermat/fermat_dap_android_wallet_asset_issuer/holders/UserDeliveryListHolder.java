package org.fermat.fermat_dap_android_wallet_asset_issuer.holders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserDelivery;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/23/15.
 */
public class UserDeliveryListHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;

    private final FermatTextView deliveryListUserName;
    private final FermatTextView deliveryListDeliveryDate;
    private final FermatTextView deliveryListDeliveryStatus;

    /**
     * Constructor
     *
     * @param itemView
     */
    public UserDeliveryListHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;

        deliveryListUserName = (FermatTextView) itemView.findViewById(R.id.deliveryListUserName);
        deliveryListDeliveryDate = (FermatTextView) itemView.findViewById(R.id.deliveryListDeliveryDate);
        deliveryListDeliveryStatus = (FermatTextView) itemView.findViewById(R.id.deliveryListDeliveryStatus);
    }

    public void bind(final UserDelivery userDelivery) {
        deliveryListUserName.setText(userDelivery.getUserName());
        deliveryListDeliveryDate.setText(userDelivery.getFormattedDeliveryDate());
        deliveryListDeliveryStatus.setText(userDelivery.getDeliveryStatus());

        setupColor(userDelivery);
    }

    private void setupColor(UserDelivery userDelivery) {
        if (userDelivery.getDeliveryStatus().equals("ASAP")) {
            deliveryListDeliveryStatus.setTextColor(Color.parseColor("#E7212C"));
        } else if (userDelivery.getDeliveryStatus().equals("ASRE")) {
            deliveryListDeliveryStatus.setTextColor(Color.parseColor("#013572"));
        }
    }
}
