package org.fermat.fermat_dap_android_wallet_asset_user.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.holders.AssetRedeemRedeemPointHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by frank on 12/8/15.
 */
public class AssetRedeemPointAdapter extends FermatAdapter<RedeemPoint, AssetRedeemRedeemPointHolder> {

    private AssetUserWalletSubAppModuleManager manager;

    public AssetRedeemPointAdapter(Context context, List<RedeemPoint> redeemPoints, AssetUserWalletSubAppModuleManager manager) {
        super(context, redeemPoints);
        this.manager = manager;
    }

    @Override
    protected AssetRedeemRedeemPointHolder createHolder(View itemView, int type) {
        return new AssetRedeemRedeemPointHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v3_wallet_asset_user_asset_redeem_select_redeempoint_item;
    }

    @Override
    protected void bindHolder(final AssetRedeemRedeemPointHolder holder, final RedeemPoint redeemPoint, int position) {
        holder.nameText.setText(redeemPoint.getName());
        if (redeemPoint.getActorAssetRedeemPoint().getAddress() != null) {
            holder.addressText.setText(String.format("%s %s", redeemPoint.getActorAssetRedeemPoint().getAddress().getStreetName(), redeemPoint.getActorAssetRedeemPoint().getAddress().getHouseNumber()));
            holder.cityText.setText(redeemPoint.getActorAssetRedeemPoint().getAddress().getCityName());
        }
        if (redeemPoint.isSelected()) {
            holder.redeemPointLayout.setVisibility(View.VISIBLE);
        } else {
            holder.redeemPointLayout.setVisibility(View.INVISIBLE);
        }

//        holder.selectRedeemPointButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (!redeemPoint.isSelected()) {
//                    redeemPoint.setSelected(true);
//                    holder.selectRedeemPointButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
//                } else {
//                    redeemPoint.setSelected(false);
//                    holder.selectRedeemPointButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
//                }
//            }
//        });

        if (redeemPoint.getActorAssetRedeemPoint().getProfileImage() != null && redeemPoint.getActorAssetRedeemPoint().getProfileImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(redeemPoint.getActorAssetRedeemPoint().getProfileImage(), 0, redeemPoint.getActorAssetRedeemPoint().getProfileImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            holder.imageViewRedeemPoint.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }
    }
}
