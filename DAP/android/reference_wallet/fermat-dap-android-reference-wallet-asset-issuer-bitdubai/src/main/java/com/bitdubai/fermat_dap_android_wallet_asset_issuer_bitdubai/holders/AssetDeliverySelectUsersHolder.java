package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.User;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.io.ByteArrayInputStream;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDeliverySelectUsersHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView nameText;
    private ImageView selectUserButton;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetDeliverySelectUsersHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.userName);
        selectUserButton = (ImageView) itemView.findViewById(R.id.selectUserButton);
    }

    public void bind(final User user) {
        nameText.setText(user.getName());

        selectUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!user.isSelected()) {
                    user.setSelected(true);
                    selectUserButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
                } else {
                    user.setSelected(false);
                    selectUserButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
                }
            }
        });
    }
}
