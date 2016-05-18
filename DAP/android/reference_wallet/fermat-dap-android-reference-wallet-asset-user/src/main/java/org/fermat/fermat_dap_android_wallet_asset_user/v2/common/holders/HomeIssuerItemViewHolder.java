package org.fermat.fermat_dap_android_wallet_asset_user.v2.common.holders;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/24/16.
 */
public class HomeIssuerItemViewHolder extends ChildViewHolder {
    //Resources
    private Resources res;

    //UI
    private ImageView homeAssetImageView;
    private FermatTextView homeAssetName;
    private FermatTextView homeAssetDesc;
    private FermatTextView homeAssetAmount;
    private FermatTextView homeAssetExpDate;
    private FermatTextView homeAssetStatus;

    public HomeIssuerItemViewHolder(View itemView) {
        super(itemView);
        res = itemView.getResources();

        homeAssetImageView = (ImageView) itemView.findViewById(R.id.homeAssetImageView);
        homeAssetName = (FermatTextView) itemView.findViewById(R.id.homeAssetName);
        homeAssetDesc = (FermatTextView) itemView.findViewById(R.id.homeAssetDesc);
        homeAssetAmount = (FermatTextView) itemView.findViewById(R.id.homeAssetAmount);
        homeAssetExpDate = (FermatTextView) itemView.findViewById(R.id.homeAssetExpDate);
        homeAssetStatus = (FermatTextView) itemView.findViewById(R.id.homeAssetStatus);
    }

    public void bind(final Asset asset) {
        byte[] img = (asset.getImage() == null) ? new byte[0] : asset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(homeAssetImageView, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        homeAssetName.setText(asset.getName());
        homeAssetDesc.setText(asset.getDescription());
        homeAssetAmount.setText(asset.getFormattedAmount());
        homeAssetExpDate.setText(asset.getFormattedExpDate());
        homeAssetStatus.setText(asset.getStatus().getDesc());

        if (asset.getStatus().equals(Asset.Status.PENDING)) {
            homeAssetStatus.setTextColor(res.getColor(R.color.dap_user_wallet_blue_text));
        } else {
            homeAssetStatus.setTextColor(res.getColor(R.color.dap_user_wallet_black_text));
        }
    }
}
