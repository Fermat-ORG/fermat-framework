package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsViewHolder extends FermatViewHolder {
    private AssetRedeemPointWalletSubAppModule manager;
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
    public MyAssetsViewHolder(View itemView, AssetRedeemPointWalletSubAppModule manager, Context context) {
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
        image.setImageDrawable(res.getDrawable(R.drawable.img_asset_without_image)); //TODO change for asset image or default image
        nameText.setText(digitalAsset.getName());
        //TODO format this fields
        availableText.setText(digitalAsset.getAvailableBalance()+"");
        bookText.setText(digitalAsset.getBookBalance()+"");
        btcText.setText(digitalAsset.getBitcoinAmount()+" BTC");
        expDateText.setText(digitalAsset.getFormattedExpDate());
    }
}
