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
 * Created by Jinmy Bohorquez on 02/18/16.
 */
public class AssetTransferUserHolder extends FermatViewHolder {
    public AssetUserWalletSubAppModuleManager manager;
    public Context context;
    public Resources res;

    public FermatTextView nameText;
    //public ImageView selectUsersButton;
    public ImageView imageViewTransfer;
    public RelativeLayout userContent;


    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetTransferUserHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.userNameToTransfer);
        //selectUsersButton = (ImageView) itemView.findViewById(R.id.selectUserTransferButton);
        imageViewTransfer = (ImageView) itemView.findViewById(R.id.imageView_user_asset_tranfer_avatar);
        userContent = (RelativeLayout) itemView.findViewById(R.id.selectedUserEffect);
    }
}
