package org.fermat.fermat_dap_android_wallet_asset_user.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsViewHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;

    private Resources res;
    public ImageView image;
    public FermatTextView nameText;

    public View normalAssetLayout;
    public FermatTextView availableText;
    public FermatTextView pendingText;
    public FermatTextView assetBalanceText;
    public FermatTextView btcText;
    public FermatTextView expDateText;
    public FermatTextView assetUserLockedAssets;

    public View negotiationAssetLayout;
    //public FermatTextView negotiationAssetQuantity;
    public FermatTextView negotiationAssetUnitPrice;

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

        normalAssetLayout = itemView.findViewById(R.id.normalAssetLayout);
        assetBalanceText = (FermatTextView) itemView.findViewById(R.id.assetBalanceText);
        availableText = (FermatTextView) itemView.findViewById(R.id.assetAvailable1);
        pendingText = (FermatTextView) itemView.findViewById(R.id.assetAvailable2);
        btcText = (FermatTextView) itemView.findViewById(R.id.assetBtcText);
        expDateText = (FermatTextView) itemView.findViewById(R.id.assetExpDateText);

        negotiationAssetLayout = itemView.findViewById(R.id.negotiationAssetLayout);
        negotiationAssetUnitPrice = (FermatTextView) itemView.findViewById(R.id.negotiationAssetUnitPrice);

        assetUserLockedAssets = (FermatTextView) itemView.findViewById(R.id.assetUserLockedAssets);

    }

    public void bind(final DigitalAsset digitalAsset) {

        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(image, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        nameText.setText(digitalAsset.getName());

        if(digitalAsset.getUserAssetNegotiation() != null){
            normalAssetLayout.setVisibility(View.GONE);
            negotiationAssetLayout.setVisibility(View.VISIBLE);


            negotiationAssetUnitPrice.setText(String.format("%s BTC", DAPStandardFormats.BITCOIN_FORMAT.format(
                    BitcoinConverter.convert(Double.valueOf(digitalAsset.getUserAssetNegotiation().getAmmountPerUnit()), SATOSHI, BITCOIN))));
            //negotiationAssetUnitPrice.setText(String.format("%s BTC", digitalAsset.getUserAssetNegotiation().getAmmountPerUnit()));
            //long quantity = digitalAsset.getAvailableBalanceQuantity();
            //negotiationAssetQuantity.setText(availableText(quantity));

            /*assetBalanceText.setVisibility(View.GONE);
            availableText.setVisibility(View.GONE);
            pendingText.setVisibility(View.GONE);
            btcText.setVisibility(View.GONE);*/


        }else {
    //        if (digitalAsset.getImage() != null) {
    //            image.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
    //        } else {
    //            image.setImageDrawable(res.getDrawable(R.drawable.img_asset_without_image));
    //        }

            long available = digitalAsset.getAvailableBalanceQuantity();
            long book = digitalAsset.getBookBalanceQuantity();
            availableText.setText(availableText(available));
            if (available == book) {
                pendingText.setVisibility(View.INVISIBLE);
            } else {
                long pendingValue = Math.abs(available - book);
                pendingText.setText(pendingText(pendingValue));
                pendingText.setVisibility(View.VISIBLE);
            }


            btcText.setText(String.format("%s BTC", digitalAsset.getFormattedAvailableBalanceBitcoin()));
            expDateText.setText(digitalAsset.getFormattedExpDate());

            if (digitalAsset.getLockedAssets() > 0){
                assetUserLockedAssets.setVisibility(View.VISIBLE);
                assetUserLockedAssets.setText((digitalAsset.getLockedAssets() == 1) ?
                        digitalAsset.getLockedAssets() +" Locked Asset" : digitalAsset.getLockedAssets() +" Locked Assets");
            }else{
                assetUserLockedAssets.setVisibility(View.GONE);
            }

        }
    }

    private String pendingText(long pendingValue) {
        return "(" + pendingValue + " pending confirmation)";
    }

    private String availableText(long available) {
        return available + ((available == 1) ? " Asset" : " Assets");
    }
}
