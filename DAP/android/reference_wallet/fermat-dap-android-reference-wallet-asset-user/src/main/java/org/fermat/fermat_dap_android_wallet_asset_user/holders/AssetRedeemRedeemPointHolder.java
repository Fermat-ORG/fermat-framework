package org.fermat.fermat_dap_android_wallet_asset_user.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

/**
 * Created by frank on 12/8/15.
 */
public class AssetRedeemRedeemPointHolder extends FermatViewHolder {
    public AssetUserWalletSubAppModuleManager manager;
    public Context context;
    public Resources res;

    public FermatTextView nameText;
    public FermatTextView addressText;
    public FermatTextView cityText;
    //public ImageView selectRedeemPointButton;
    public ImageView imageViewRedeemPoint;
    public RelativeLayout redeemPointLayout;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetRedeemRedeemPointHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.redeemPointItemName);
        //selectRedeemPointButton = (ImageView) itemView.findViewById(R.id.selectRedeemPointsButton);
        imageViewRedeemPoint = (ImageView) itemView.findViewById(R.id.imageView_redeem_redeempoint_avatar);
        redeemPointLayout = (RelativeLayout) itemView.findViewById(R.id.selectedRedeemEffect);
        addressText = (FermatTextView) itemView.findViewById(R.id.selectedRedeemPointAddress);
        cityText = (FermatTextView) itemView.findViewById(R.id.selectedRedeemPointCity);
    }

}
