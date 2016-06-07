package org.fermat.fermat_dap_android_wallet_redeem_point.holders;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.models.UserRedeemed;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

/**
 * Created by Penny on 01/08/16.
 */
public class UserRedeemedPointListHolder extends FermatViewHolder {
    private AssetRedeemPointWalletSubAppModule manager;
    private Context context;

    private final FermatTextView redeemedListUserName;
    private final FermatTextView redeemedListRedeemedDate;

    /**
     * Constructor
     *
     * @param itemView
     */
    public UserRedeemedPointListHolder(View itemView, AssetRedeemPointWalletSubAppModule manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;

        redeemedListUserName = (FermatTextView) itemView.findViewById(R.id.redeemedListUserName);
        redeemedListRedeemedDate = (FermatTextView) itemView.findViewById(R.id.redeemedListRedeemedDate);

    }

    public void bind(final UserRedeemed userRedeemed) {
        redeemedListUserName.setText(userRedeemed.getUserName());
        redeemedListRedeemedDate.setText(userRedeemed.getFormattedRedeemedDate());


    }


}
