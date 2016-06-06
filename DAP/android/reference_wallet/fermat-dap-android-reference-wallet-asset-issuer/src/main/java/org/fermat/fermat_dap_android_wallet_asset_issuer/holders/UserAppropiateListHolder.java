package org.fermat.fermat_dap_android_wallet_asset_issuer.holders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserAppropiate;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/23/15.
 */
public class UserAppropiateListHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;

    private final FermatTextView appropiateListUserName;
    private final FermatTextView appropiateListAppropiateDate;
    private final FermatTextView appropiateListAppropiateStatus;

    /**
     * Constructor
     *
     * @param itemView
     */
    public UserAppropiateListHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;

        appropiateListUserName = (FermatTextView) itemView.findViewById(R.id.appropiateListUserName);
        appropiateListAppropiateDate = (FermatTextView) itemView.findViewById(R.id.appropiateListAppropiateDate);
        appropiateListAppropiateStatus = (FermatTextView) itemView.findViewById(R.id.appropiateListAppropiateStatus);
    }

    public void bind(final UserAppropiate userAppropiate) {
        appropiateListUserName.setText(userAppropiate.getUserName());
        appropiateListAppropiateDate.setText(userAppropiate.getFormattedAppropiateDate());
        appropiateListAppropiateStatus.setText(userAppropiate.getAppropiateStatus());

        setupColor(userAppropiate);
    }

    private void setupColor(UserAppropiate userAppropiate) {
        appropiateListAppropiateStatus.setTextColor(Color.parseColor("#E7212C"));
    }
}
