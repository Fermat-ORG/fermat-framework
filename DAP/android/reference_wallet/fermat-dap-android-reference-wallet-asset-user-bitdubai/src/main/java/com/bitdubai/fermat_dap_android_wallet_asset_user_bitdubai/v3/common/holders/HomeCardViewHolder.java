package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Asset;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/8/15.
 */
public class HomeCardViewHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;

    private Resources res;

    private ImageView homeIssuerImage;
    private FermatTextView cardActorName;
    private FermatTextView cardTime;
    private ImageView cardConfirmedImage;
    private ImageView cardAssetImage;
    private FermatTextView cardAssetName;
    private FermatTextView cardExpDate;
    private ImageButton cardRedeemButton;
    private ImageButton cardTransferButton;
    private ImageButton cardAppropriateButton;
    private ImageButton cardSellButton;
    private ImageButton cardTransactionsButton;

    /**
     * Constructor
     *
     * @param itemView
     */
    public HomeCardViewHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        homeIssuerImage = (ImageView) itemView.findViewById(R.id.homeIssuerImage);
        cardActorName = (FermatTextView) itemView.findViewById(R.id.cardActorName);
        cardTime = (FermatTextView) itemView.findViewById(R.id.cardTime);
        cardConfirmedImage = (ImageView) itemView.findViewById(R.id.cardConfirmedImage);
        cardAssetImage = (ImageView) itemView.findViewById(R.id.cardAssetImage);
        cardAssetName = (FermatTextView) itemView.findViewById(R.id.cardAssetName);
        cardExpDate = (FermatTextView) itemView.findViewById(R.id.cardExpDate);
        cardRedeemButton = (ImageButton) itemView.findViewById(R.id.cardRedeemButton);
        cardTransferButton = (ImageButton) itemView.findViewById(R.id.cardTransferButton);
        cardAppropriateButton = (ImageButton) itemView.findViewById(R.id.cardAppropriateButton);
        cardSellButton = (ImageButton) itemView.findViewById(R.id.cardSellButton);
        cardTransactionsButton = (ImageButton) itemView.findViewById(R.id.cardTransactionsButton);
    }

    public void bind(final Asset asset) {

        byte[] img = (asset.getActorImage() == null) ? new byte[0] : asset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(homeIssuerImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        cardActorName.setText(asset.getActorName());
        cardTime.setText("2 minutes ago"); //TODO setting time

        int image = (asset.getStatus().equals(Asset.Status.CONFIRMED)) ? R.drawable.detail_check : R.drawable.detail_uncheck;
        cardConfirmedImage.setImageResource(image);

        img = (asset.getImage() == null) ? new byte[0] : asset.getImage();
        bitmapWorkerTask = new BitmapWorkerTask(homeIssuerImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        cardAssetName.setText(asset.getName());
        cardExpDate.setText(asset.getFormattedExpDate());

        initActions();
    }

    private void initActions() {
        cardRedeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardRedeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardRedeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardRedeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardRedeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
