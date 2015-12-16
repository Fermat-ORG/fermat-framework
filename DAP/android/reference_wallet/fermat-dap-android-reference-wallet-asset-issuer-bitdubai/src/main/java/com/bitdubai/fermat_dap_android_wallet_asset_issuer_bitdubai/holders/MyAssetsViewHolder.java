package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.holders;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatActivity;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/8/15.
 */
public class MyAssetsViewHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;

    private Resources res;
    public ImageView image;
    public FermatTextView nameText;
    public FermatTextView availableText;
    public FermatTextView bookText;
    public FermatTextView btcText;
    public FermatTextView expDateText;
    public FermatButton distributeTempButton; //TODO button only for temporally use

    /**
     * Constructor
     *
     * @param itemView
     */
    public MyAssetsViewHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
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
        distributeTempButton = (FermatButton) itemView.findViewById(R.id.distributeTempButton);
    }

    public void bind(final DigitalAsset digitalAsset) {
        image.setImageDrawable(res.getDrawable(R.drawable.img_asset_without_image)); //TODO change for asset image or default image
        nameText.setText(digitalAsset.getName());
        //TODO format this fields
        availableText.setText(digitalAsset.getAvailableBalance()+"");
        bookText.setText(digitalAsset.getBookBalance()+"");
        btcText.setText(digitalAsset.getBitcoinAmount()+" BTC");
        expDateText.setText(digitalAsset.getFormattedExpDate());

        distributeTempButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doDistribute(digitalAsset.getAssetPublicKey());
            }
        });
    }

    private void doDistribute(final String assetPublicKey) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
//                    manager.distributionAssets(
//                            asset.getAssetPublicKey(),
//                            asset.getWalletPublicKey(),
//                            asset.getActorAssetUser()
//                    );
                //TODO: Solo para la prueba del Distribution
                manager.distributionAssets(assetPublicKey, null);
                return true;
            }
        };
        task.setContext((Activity) context);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (context != null) {
                    Toast.makeText(context, "Everything ok...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (context != null)
                    Toast.makeText(context, "Fermat Has detected an exception",
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
}
