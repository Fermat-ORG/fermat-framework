package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsViewHolder extends FermatViewHolder {
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
    public MyAssetsViewHolder(View itemView) {
        super(itemView);
        res = itemView.getResources();

        image = (ImageView) itemView.findViewById(R.id.asset_image);
        nameText = (FermatTextView) itemView.findViewById(R.id.assetNameText);
        availableText = (FermatTextView) itemView.findViewById(R.id.assetAvailableText);
        bookText = (FermatTextView) itemView.findViewById(R.id.assetBookText);
        btcText = (FermatTextView) itemView.findViewById(R.id.assetBtcText);
        expDateText = (FermatTextView) itemView.findViewById(R.id.assetExpDateText);
    }

    public void bind(DigitalAsset digitalAsset) {
        //TODO format this fields
        nameText.setText(digitalAsset.getName());
        availableText.setText(digitalAsset.getAvailableBalance()+"");
        bookText.setText(digitalAsset.getBookBalance()+"");
        btcText.setText(digitalAsset.getBitcoinAmount()+"");
        expDateText.setText(digitalAsset.getExpDate().toString());
    }
}
