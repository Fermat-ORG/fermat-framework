package org.fermat.fermat_dap_android_wallet_asset_user.v2.common.holders;

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
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/3/16.
 */
public class RedeemPointViewHolder extends FermatViewHolder {
    //FERMAT
    private AssetUserWalletSubAppModuleManager manager;

    //ANDROID
    private Resources res;
    private Context context;

    //UI
    private ImageView redeemPointImage;
    private FermatTextView redeemPointName;
    private FermatTextView redeemPointAddress;

    public RedeemPointViewHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);

        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        redeemPointImage = (ImageView) itemView.findViewById(R.id.redeemPointImage);
        redeemPointName = (FermatTextView) itemView.findViewById(R.id.redeemPointName);
        redeemPointAddress = (FermatTextView) itemView.findViewById(R.id.redeemPointAddress);
    }

    public void bind(final RedeemPoint redeemPoint) {
        Bitmap bitmap;
        byte[] redeemPointImageData = redeemPoint.getImage();
        if (redeemPointImageData != null && redeemPointImageData.length > 0) {
            bitmap = BitmapFactory.decodeByteArray(redeemPointImageData, 0, redeemPointImageData.length);
        } else {
            bitmap = BitmapFactory.decodeResource(res, R.drawable.img_detail_without_image);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
        redeemPointImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));

        redeemPointName.setText(redeemPoint.getName());
        redeemPointAddress.setText(redeemPoint.getAddress());
    }
}
