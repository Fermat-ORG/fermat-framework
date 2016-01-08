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
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Jinmy on 01/06/16.
 */
public class UserAppropiateListHeaderFragment extends AbstractFermatFragment {
    private AssetIssuerSession assetIssuerSession;
    private AssetIssuerWalletSupAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private ImageView assetImageUserAppropiateList;
    private FermatTextView assetNameUserAppropiateListText;
    private FermatTextView assetsRemainingUserAppropiateListText;

    private DigitalAsset digitalAsset;

    public UserAppropiateListHeaderFragment() {

    }

    public static UserAppropiateListHeaderFragment newInstance() {
        return new UserAppropiateListHeaderFragment();
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
        rootView = inflater.inflate(R.layout.dap_wallet_asset_issuer_user_appropiate_list_header, container, false);

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetImageUserAppropiateList = (ImageView) rootView.findViewById(R.id.assetImageUserAppropiateList);
        assetNameUserAppropiateListText = (FermatTextView) rootView.findViewById(R.id.assetNameUserAppropiateListText);
        assetsRemainingUserAppropiateListText = (FermatTextView) rootView.findViewById(R.id.assetsRemainingUserAppropiateListText);
    }

    private void setupUIData() {
        digitalAsset = (DigitalAsset) appSession.getData("asset_data");

        if (digitalAsset.getImage() != null) {
            assetImageUserAppropiateList.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
        } else {
            assetImageUserAppropiateList.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
        }

        assetNameUserAppropiateListText.setText(digitalAsset.getName());
        assetsRemainingUserAppropiateListText.setText(digitalAsset.getAvailableBalance() + " Assets Remaining");
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
}
