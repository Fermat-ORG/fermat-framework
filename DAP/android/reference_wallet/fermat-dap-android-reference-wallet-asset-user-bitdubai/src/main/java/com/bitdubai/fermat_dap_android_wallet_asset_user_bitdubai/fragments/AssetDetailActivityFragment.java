package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by frank on 12/15/15.
 */
public class AssetDetailActivityFragment extends AbstractFermatFragment {

    private AssetUserSession assetUserSession;
    private AssetUserWalletSubAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private View assetDetailRedeemLayout;
    private View assetDetailAppropriateLayout;

    private ImageView assetImageDetail;
    private FermatTextView assetDetailNameText;
    private FermatTextView assetDetailExpDateText;
    private FermatTextView assetDetailAvailableText;
    private FermatTextView assetDetailBookText;
    private FermatTextView assetDetailBtcText;
    private FermatTextView assetDetailRedeemText;

    private DigitalAsset digitalAsset;

    public AssetDetailActivityFragment() {

    }

    public static AssetDetailActivityFragment newInstance() {
        return new AssetDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetUserSession = (AssetUserSession) appSession;
        moduleManager = assetUserSession.getModuleManager();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_user_asset_detail, container, false);

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetImageDetail = (ImageView) rootView.findViewById(R.id.asset_image_detail);
        assetDetailNameText = (FermatTextView) rootView.findViewById(R.id.assetDetailNameText);
        assetDetailExpDateText = (FermatTextView) rootView.findViewById(R.id.assetDetailExpDateText);
        assetDetailAvailableText = (FermatTextView) rootView.findViewById(R.id.assetDetailAvailableText);
        assetDetailBookText = (FermatTextView) rootView.findViewById(R.id.assetDetailBookText);
        assetDetailBtcText = (FermatTextView) rootView.findViewById(R.id.assetDetailBtcText);
        assetDetailRedeemText = (FermatTextView) rootView.findViewById(R.id.assetDetailRedeemText);

        assetDetailRedeemLayout = rootView.findViewById(R.id.assetDetailRedeemLayout);

        assetDetailAppropriateLayout = rootView.findViewById(R.id.assetDetailAppropriateLayout);
        assetDetailAppropriateLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doAppropriate(digitalAsset.getAssetPublicKey());
            }
        });
    }

    private void setupBackgroundBitmap() {
        AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

            WeakReference<ViewGroup> view;

            @Override
            protected void onPreExecute() {
                view = new WeakReference(rootView) ;
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap drawable = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    options.inSampleSize = 5;
                    drawable = BitmapFactory.decodeResource(
                            getResources(), R.drawable.bg_app_image_user,options);
                }catch (OutOfMemoryError error){
                    error.printStackTrace();
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Bitmap drawable) {
                if (drawable!= null) {
                    view.get().setBackground(new BitmapDrawable(getResources(),drawable));
                }
            }
        } ;
        asyncTask.execute();
    }

    private void setupUIData() {
        //TODO review this
        digitalAsset = (DigitalAsset) ((HashMap) appSession.getData("asset_data")).get("asset_data");

        toolbar.setTitle(digitalAsset.getName());

        if (digitalAsset.getImage() != null) {
            assetImageDetail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
        } else {
            assetImageDetail.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
        }

        if (digitalAsset.getAvailableBalanceQuantity() > 0) {
            assetDetailRedeemText.setTextColor(Color.parseColor("#00aeef"));
            assetDetailRedeemLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM, appSession.getAppPublicKey());
                }
            });
        } else {
            assetDetailRedeemText.setTextColor(Color.parseColor("#CCFFFFFF"));
        }

        assetDetailNameText.setText(digitalAsset.getName());
        assetDetailExpDateText.setText(digitalAsset.getFormattedExpDate());
        assetDetailAvailableText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetDetailBookText.setText(digitalAsset.getBookBalanceQuantity() + "");
        assetDetailBtcText.setText(digitalAsset.getFormattedAvailableBalanceBitcoin() + " BTC");
    }

    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor("#381a5e"));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#381a5e"));
            }
        }
    }

    private void doAppropriate(final String assetPublicKey) {
        final Activity activity = getActivity();
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
//                    manager.distributionAssets(
//                            asset.getAssetPublicKey(),
//                            asset.getWalletPublicKey(),
//                            asset.getActorAssetRedeemPoint()
//                    );
                //TODO: only for Appropriate test
                moduleManager.appropriateAsset(assetPublicKey, null);
                return true;
            }
        };
        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
                    Toast.makeText(activity, "Everything ok (appropriate)...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, "Fermat Has detected an exception",
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
}
