package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments;

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
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;

/**
 * Created by frank on 12/15/15.
 */
public class AssetDetailActivityFragment extends AbstractFermatFragment {

    private AssetIssuerSession assetIssuerSession;
    private AssetIssuerWalletSupAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private View assetDetailRemainingLayout;
    private View assetDetailAvailableLayout;

    private View assetDetailAppropiateLayout;

    private View assetDetailRedeemedLayout;

    private ImageView assetImageDetail;
    private FermatTextView assetDetailNameText;
    private FermatTextView assetDetailExpDateText;
    private FermatTextView assetDetailAvailableText;
    private FermatTextView assetDetailBookText;
    private FermatTextView assetDetailBtcText;
    private FermatTextView assetDetailRemainingText;
    private FermatTextView assetDetailDelivered;
    private FermatTextView assetDetailRedeemText;
    private FermatTextView assetDetailAppropriatedText;

    private DigitalAsset digitalAsset;

    public AssetDetailActivityFragment() {

    }

    public static AssetDetailActivityFragment newInstance() {
        return new AssetDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetIssuerSession = (AssetIssuerSession) appSession;
        moduleManager = assetIssuerSession.getModuleManager();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_issuer_asset_detail, container, false);

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
        assetDetailRemainingText = (FermatTextView) rootView.findViewById(R.id.assetDetailRemainingText);
        assetDetailDelivered = (FermatTextView) rootView.findViewById(R.id.assetDetailAvailableText2);
        assetDetailRedeemText = (FermatTextView) rootView.findViewById(R.id.assetDetailRedeemText);
        assetDetailAppropriatedText = (FermatTextView) rootView.findViewById(R.id.assetDetailAppropriatedText);

        assetDetailRemainingLayout = rootView.findViewById(R.id.assetDetailRemainingLayout);
        assetDetailRemainingLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY, appSession.getAppPublicKey());
            }
        });

        assetDetailAvailableLayout = rootView.findViewById(R.id.assetDetailAvailableLayout);
        assetDetailAvailableLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_USER_DELIVERY_LIST, appSession.getAppPublicKey());
            }
        });


        assetDetailAppropiateLayout = rootView.findViewById(R.id.assetDetailAppropiateLayout);
        assetDetailAppropiateLayout.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_USER_APPROPIATE_LIST, appSession.getAppPublicKey());
           }
       });

        assetDetailRedeemedLayout = rootView.findViewById(R.id.assetDetailRedeemedLayout);
        assetDetailRedeemedLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_USER_REDEEMED_LIST, appSession.getAppPublicKey());

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
                            getResources(), R.drawable.bg_app_image,options);
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
        digitalAsset = (DigitalAsset) appSession.getData("asset_data");

        try {
            Data.setStatistics("walletPublicKeyTest", digitalAsset, moduleManager);
        } catch (CantGetAssetStatisticException e) {
            e.printStackTrace();
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

        toolbar.setTitle(digitalAsset.getName());

        if (digitalAsset.getImage() != null) {
            assetImageDetail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
        } else {
            assetImageDetail.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
        }

        assetDetailNameText.setText(digitalAsset.getName());
        assetDetailExpDateText.setText(digitalAsset.getFormattedExpDate());
        assetDetailAvailableText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetDetailBookText.setText(digitalAsset.getBookBalanceQuantity()+"");
        assetDetailBtcText.setText(digitalAsset.getFormattedAvailableBalanceBitcoin() + " BTC");
        assetDetailRemainingText.setText(digitalAsset.getAvailableBalanceQuantity() + " Assets Remaining");
        assetDetailDelivered.setText(digitalAsset.getUnused() + "");
        assetDetailRedeemText.setText(digitalAsset.getRedeemed()+"");
        assetDetailAppropriatedText.setText(digitalAsset.getAppropriated()+"");
    }

    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
//            toolbar.setBackgroundColor(Color.parseColor("#1d1d25"));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setBottom(Color.WHITE);
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                Window window = getActivity().getWindow();
//                window.setStatusBarColor(Color.parseColor("#1d1d25"));
//            }
            Drawable drawable = null;
            //TODO uncomment
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors, null);
            else
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors);

            toolbar.setBackground(drawable);
        }
    }
}
