package org.fermat.fermat_dap_android_sub_app_asset_factory.v3.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

/**
 * Created by Jinmy Bohorquez on 29/04/16.
 */
public class AssetFactoryDraftHolder extends FermatViewHolder {

    private Context context;
    public Resources res;
    public FermatTextView draftItemQuantity;
    public FermatTextView draftItemState;
    public ImageView draftAssetImage;

    public FermatTextView draftItemAssetName;
    public FermatTextView draftItemAssetValue;
    public FermatTextView draftItemExpDate;

    public View normalAssetButtons;
    public ImageButton draftItemEditButton;
    public ImageButton draftItemEraseButton;
    public ImageButton draftItemPublishButton;

    public View draftSeparatorLine;

    public View publishedAssetButtons;
    public ImageButton publishedItemEditButton;
    public ImageButton publishedItemEraseButton;
    public ImageButton publishedItemPublishButton;

    public ImageView assetStatusImage;


    public AssetFactoryDraftHolder(View itemView) {
        super(itemView);
        this.context = context;
        res = itemView.getResources();

        draftItemQuantity = (FermatTextView) itemView.findViewById(R.id.draftItemQuantity);
        draftItemState = (FermatTextView) itemView.findViewById(R.id.draftItemState);
        draftAssetImage = (ImageView) itemView.findViewById(R.id.draftAssetImage);

        draftItemAssetName = (FermatTextView) itemView.findViewById(R.id.draftItemAssetName);
        draftItemAssetValue = (FermatTextView) itemView.findViewById(R.id.draftItemAssetValue);
        draftItemExpDate = (FermatTextView) itemView.findViewById(R.id.draftItemExpDate);

        normalAssetButtons = itemView.findViewById(R.id.normalAssetButtons);
        draftItemEditButton = (ImageButton) itemView.findViewById(R.id.draftItemEditButton);
        draftItemEraseButton = (ImageButton) itemView.findViewById(R.id.draftItemEraseButton);
        draftItemPublishButton = (ImageButton) itemView.findViewById(R.id.draftItemPublishButton);

        draftSeparatorLine = itemView.findViewById(R.id.draftSeparatorLine);

        publishedAssetButtons = itemView.findViewById(R.id.publishedAssetButtons);
        publishedItemEditButton = (ImageButton) itemView.findViewById(R.id.publishedItemEditButton);
        publishedItemEraseButton = (ImageButton) itemView.findViewById(R.id.publishedItemEraseButton);
        publishedItemPublishButton = (ImageButton) itemView.findViewById(R.id.publishedItemPublishButton);

        assetStatusImage = (ImageView) itemView.findViewById(R.id.assetStatusImage);

    }
}
