package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.UserDelivery;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import static org.fermat.fermat_dap_api.layer.all_definition.enums.AssetCurrentStatus.ASSET_UNUSED;

/**
 * Created by frank on 12/23/15.
 */
public class StatsViewHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;
    private Resources res;

    private ImageView statsUserImage;
    private FermatTextView statsUserNameText;
    private FermatTextView statsStatusText;
    private FermatTextView statsTimeAgoText;

    /**
     * Constructor
     *
     * @param itemView
     */
    public StatsViewHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        statsUserImage = (ImageView) itemView.findViewById(R.id.statsUserImage);
        statsUserNameText = (FermatTextView) itemView.findViewById(R.id.statsUserNameText);
        statsStatusText = (FermatTextView) itemView.findViewById(R.id.statsStatusText);
        statsTimeAgoText = (FermatTextView) itemView.findViewById(R.id.statsTimeAgoText);
    }

    public void bind(final UserDelivery userDelivery) {
//        byte[] img = (userDelivery.getUserImage() == null) ? new byte[0] : userDelivery.getUserImage();
//        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(statsUserImage, res, R.drawable.img_asset_without_image, false);
//        bitmapWorkerTask.execute(img);
        if (userDelivery.getUserImage() != null && userDelivery.getUserImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(userDelivery.getUserImage(), 0, userDelivery.getUserImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            statsUserImage.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }

        statsTimeAgoText.setText(userDelivery.getFormattedDeliveryDate());
        statsUserNameText.setText(userDelivery.getUserName());
        statsStatusText.setText(userDelivery.getDeliveryStatusDescription());

        setupColor(userDelivery.getDeliveryStatus());
    }

    private void setupColor(String userDeliveryStatus) {
        if (userDeliveryStatus.equals(ASSET_UNUSED.getCode())) {
            statsStatusText.setTextColor(Color.parseColor("#BDBDBD"));
        } else {
            statsStatusText.setTextColor(Color.parseColor("#8C9EFF"));
        }
    }
}
