package org.fermat.fermat_dap_android_wallet_asset_issuer.holders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserRedeemed;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by Penny on 01/08/16.
 */
public class UserRedeemedListHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;

    private final FermatTextView redeemedListUserName;
    private final FermatTextView redeemedListRedeemedDate;
    private final FermatTextView redeemedListRedeemedStatus;
    private final FermatTextView redeemedListRedeemedRP;

    /**
     * Constructor
     *
     * @param itemView
     */
    public UserRedeemedListHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;

        redeemedListUserName = (FermatTextView) itemView.findViewById(R.id.redeemedListUserName);
        redeemedListRedeemedDate = (FermatTextView) itemView.findViewById(R.id.redeemedListRedeemedDate);
        redeemedListRedeemedStatus = (FermatTextView) itemView.findViewById(R.id.redeemedListRedeemedStatus);
        redeemedListRedeemedRP = (FermatTextView) itemView.findViewById(R.id.redeemedListRedeemedRP);
    }

    public void bind(final UserRedeemed userRedeemed) {
        redeemedListUserName.setText(userRedeemed.getUserName());
        redeemedListRedeemedDate.setText(userRedeemed.getFormattedRedeemedDate());
        redeemedListRedeemedStatus.setText(userRedeemed.getRedeemedStatus());
        redeemedListRedeemedRP.setText("at " + userRedeemed.getRedeemPoint());

        setupColor(userRedeemed);
    }

    private void setupColor(UserRedeemed userRedeemed) {
        redeemedListRedeemedStatus.setTextColor(Color.parseColor("#013572"));
    }
}
