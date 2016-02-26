package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.common.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatRoundedImageView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Issuer;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class HomeIssuerItemViewHolder extends FermatViewHolder {
    //Resources
    private Resources res;

    //UI
    private ImageView homeIssuerImage;
    private FermatTextView homeIssuerNameText;
    private FermatTextView homeIssuerNumAssets;

    public HomeIssuerItemViewHolder(View itemView, int holderType) {
        super(itemView, holderType);
        res = itemView.getResources();

        homeIssuerImage = (ImageView) itemView.findViewById(R.id.homeIssuerImage);
        homeIssuerNameText = (FermatTextView) itemView.findViewById(R.id.homeIssuerNameText);
        homeIssuerNumAssets = (FermatTextView) itemView.findViewById(R.id.homeIssuerNumAssets);
    }

    public void bind(final Issuer issuer) {
        Bitmap bitmap;
        if (issuer.getImage() != null && issuer.getImage().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(issuer.getImage(), 0, issuer.getImage().length);
        } else {
            bitmap = BitmapFactory.decodeResource(res, R.drawable.img_detail_without_image);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
        homeIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));

        homeIssuerNameText.setText(issuer.getName());

        if (issuer.getAssets() != null) {
            homeIssuerNumAssets.setText(issuer.getAssets().size() + res.getString(R.string.dap_user_wallet_v2_home_issuer_item_num_assets));
        }
    }
}
