package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.holders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.io.ByteArrayInputStream;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsViewHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;

    private Resources res;
    public ImageView image;
    public FermatTextView nameText;
    public FermatTextView availableText;
    public FermatTextView bookText;
    public FermatTextView btcText;
    public FermatTextView expDateText;

    /**
     * Constructor
     *
     * @param itemView
     */
    public MyAssetsViewHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        image = (ImageView) itemView.findViewById(R.id.asset_image);
        nameText = (FermatTextView) itemView.findViewById(R.id.assetNameText);
        availableText = (FermatTextView) itemView.findViewById(R.id.assetAvailableText);
        bookText = (FermatTextView) itemView.findViewById(R.id.assetBookText);
        btcText = (FermatTextView) itemView.findViewById(R.id.assetBtcText);
        expDateText = (FermatTextView) itemView.findViewById(R.id.assetExpDateText);
    }

    public void bind(final DigitalAsset digitalAsset) {
        if (digitalAsset.getImage() != null) {
            image.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
        } else {
            image.setImageDrawable(res.getDrawable(R.drawable.img_asset_without_image));
        }
        nameText.setText(digitalAsset.getName());

        nameText.setText(digitalAsset.getName());
        availableText.setText(String.format("%d", digitalAsset.getAvailableBalanceQuantity()));
        bookText.setText(String.format("%d", digitalAsset.getBookBalanceQuantity()));
        btcText.setText(String.format("%s BTC", digitalAsset.getFormattedAvailableBalanceBitcoin()));
        expDateText.setText(digitalAsset.getFormattedExpDate());
    }
}
