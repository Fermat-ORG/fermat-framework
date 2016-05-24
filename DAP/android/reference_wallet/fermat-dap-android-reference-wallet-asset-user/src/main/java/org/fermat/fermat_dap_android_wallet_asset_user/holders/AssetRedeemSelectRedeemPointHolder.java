package org.fermat.fermat_dap_android_wallet_asset_user.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

/**
 * Created by frank on 12/8/15.
 */
public class AssetRedeemSelectRedeemPointHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView nameText;
    private ImageView selectRedeemPointButton;
    private ImageView imageViewRedeemPoint;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetRedeemSelectRedeemPointHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.redeemPointName);
        selectRedeemPointButton = (ImageView) itemView.findViewById(R.id.selectRedeemPointsButton);
        imageViewRedeemPoint = (ImageView) itemView.findViewById(R.id.imageView_redeempoint_avatar);
    }

    public void bind(final RedeemPoint redeemPoint) {
        nameText.setText(redeemPoint.getName());
        if (redeemPoint.isSelected()) {
            selectRedeemPointButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
        } else {
            selectRedeemPointButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
        }

        selectRedeemPointButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!redeemPoint.isSelected()) {
                    redeemPoint.setSelected(true);
                    selectRedeemPointButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
                } else {
                    redeemPoint.setSelected(false);
                    selectRedeemPointButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
                }
            }
        });

        if (redeemPoint.getActorAssetRedeemPoint().getProfileImage() != null && redeemPoint.getActorAssetRedeemPoint().getProfileImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(redeemPoint.getActorAssetRedeemPoint().getProfileImage(), 0, redeemPoint.getActorAssetRedeemPoint().getProfileImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            imageViewRedeemPoint.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }
    }
}
