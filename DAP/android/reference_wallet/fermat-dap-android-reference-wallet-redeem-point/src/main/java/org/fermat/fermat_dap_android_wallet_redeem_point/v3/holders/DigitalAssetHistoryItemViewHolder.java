package org.fermat.fermat_dap_android_wallet_redeem_point.v3.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.v3.models.DigitalAssetHistory;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

/**
 * Created by Penny on 19/04/16.
 */
public class DigitalAssetHistoryItemViewHolder extends FermatViewHolder {
    private AssetRedeemPointWalletSubAppModule manager;
    private Context context;

    private final LinearLayout historySectionHeader;
    private final FermatTextView historyDateHeader;
    private final FermatTextView historyAssetsQuantity;

    private final FermatTextView historyAssetName;
    private final FermatTextView historyUserName;

    private final ImageView imageViewAssetRedeemedAvatar;
    private final ImageView imageViewUserRedeemedAvatar;

    /**
     * Constructor
     *
     * @param itemView
     */
    public DigitalAssetHistoryItemViewHolder(View itemView, AssetRedeemPointWalletSubAppModule manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;

        historySectionHeader = (LinearLayout) itemView.findViewById(R.id.historySectionHeader);

        historyDateHeader = (FermatTextView) itemView.findViewById(R.id.historyDateHeader);
        historyAssetsQuantity = (FermatTextView) itemView.findViewById(R.id.historyAssetsQuantity);
        historyAssetName = (FermatTextView) itemView.findViewById(R.id.historyAssetName);
        historyUserName = (FermatTextView) itemView.findViewById(R.id.historyUserName);

        imageViewAssetRedeemedAvatar = (ImageView) itemView.findViewById(R.id.imageViewAssetRedeemedAvatar);
        imageViewUserRedeemedAvatar = (ImageView) itemView.findViewById(R.id.imageViewUserRedeemedAvatar);

    }

    public void bind(final DigitalAssetHistory digitalAssetHistory, String sectionTextDate, Integer assetsQuantity, boolean showSection) {

        if (showSection) {
            historySectionHeader.setVisibility(View.VISIBLE);
            historyDateHeader.setText(sectionTextDate);
            String textSectionRight = (assetsQuantity > 1 ? (assetsQuantity + " Assets") : (assetsQuantity + " Asset"));
            historyAssetsQuantity.setText(textSectionRight);
        } else
            historySectionHeader.setVisibility(View.GONE);

        historyAssetName.setText(digitalAssetHistory.getHistoryNameAsset());
        historyUserName.setText(digitalAssetHistory.getHistoryNameUser());

        byte[] imgAsset = (digitalAssetHistory.getImageAsset() == null) ? new byte[0] : digitalAssetHistory.getImageAsset();
        BitmapWorkerTask bitmapWorkerTaskAsset = new BitmapWorkerTask(imageViewAssetRedeemedAvatar, context.getResources(), R.drawable.img_asset_without_image, true);
        bitmapWorkerTaskAsset.execute(imgAsset);

        byte[] imgUser = (digitalAssetHistory.getImageActorUserFrom() == null) ? new byte[0] : digitalAssetHistory.getImageActorUserFrom();
        BitmapWorkerTask bitmapWorkerTaskUser = new BitmapWorkerTask(imageViewUserRedeemedAvatar, context.getResources(), R.drawable.redeem_point_identity, true);
        bitmapWorkerTaskUser.execute(imgUser);


    }


}
