package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.User;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by frank on 12/15/15.
 */
public class AssetDeliveryFragment extends AbstractFermatFragment {

    private Activity activity;

    private AssetIssuerSession assetIssuerSession;
    private AssetIssuerWalletSupAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private ImageView assetDeliveryImage;
    private FermatTextView assetDeliveryNameText;
    private FermatTextView assetDeliveryRemainingText;
    private FermatTextView selectedUsersText;
    private FermatEditText assetsToDeliverEditText;
    private View selectUsersButton;
    private View deliverAssetsButton;

    private DigitalAsset digitalAsset;

    int selectedUsersCount;

    public AssetDeliveryFragment() {

    }

    public static AssetDeliveryFragment newInstance() {
        return new AssetDeliveryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetIssuerSession = (AssetIssuerSession) appSession;
        moduleManager = assetIssuerSession.getModuleManager();
        activity = getActivity();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_issuer_asset_delivery, container, false);

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetDeliveryImage = (ImageView) rootView.findViewById(R.id.assetDeliveryImage);
        assetDeliveryNameText = (FermatTextView) rootView.findViewById(R.id.assetDeliveryNameText);
        assetDeliveryRemainingText = (FermatTextView) rootView.findViewById(R.id.assetDeliveryRemainingText);
        assetsToDeliverEditText = (FermatEditText) rootView.findViewById(R.id.assetsToDeliverEditText);
        selectedUsersText = (FermatTextView) rootView.findViewById(R.id.selectedUsersText);
        selectUsersButton = rootView.findViewById(R.id.selectUsersButton);
        deliverAssetsButton = rootView.findViewById(R.id.deliverAssetsButton);

//        layout = rootView.findViewById(R.id.assetDetailRemainingLayout);
        deliverAssetsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedUsersCount > 0) {
                    Object x = appSession.getData("users");
                    if (x != null) {
                        List<User> users = (List<User>) x;
                        if (users.size() > 0) {
                            doDistribute(digitalAsset.getAssetPublicKey(), users);
                        }
                    }
                } else {
                    Toast.makeText(activity, "No users selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectUsersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                appSession.setData("asset_data", data);
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_SELECT_USERS_GROUPS, appSession.getAppPublicKey());
            }
        });

        selectedUsersCount = getUsersSelectedCount();
        String message = (selectedUsersCount == 0) ? "Select users" : selectedUsersCount + " users selected";
        selectedUsersText.setText(message);
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

    private int getUsersSelectedCount() {
        Object x = appSession.getData("users");
        int count = 0;
        if (x != null) {
            List<User> users = (List<User>) x;
            if (users.size() > 0) {
                for (User user :
                        users) {
                    if (user.isSelected()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void doDistribute(final String assetPublicKey, final List<User> users) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (User user : users) {
                    if (user.isSelected()) {
                        moduleManager.addUserToDeliver(user.getActorAssetUser());
                    }
                }
                if (users.size() > 0) {
                    //TODO: Solo para la prueba del Distribution
                    moduleManager.distributionAssets(assetPublicKey, null);
                }
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
                    Toast.makeText(activity, "Everything ok...", Toast.LENGTH_SHORT).show();
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

    private void setupUIData() {
        digitalAsset = (DigitalAsset) appSession.getData("asset_data");

        toolbar.setTitle(digitalAsset.getName());

        if (digitalAsset.getImage() != null) {
            assetDeliveryImage.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
        } else {
            assetDeliveryImage.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
        }

        assetDeliveryNameText.setText(digitalAsset.getName());
        assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetDeliveryRemainingText.setText(digitalAsset.getAvailableBalanceQuantity() + " Assets Remaining");
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors, null);
            else
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors);

            toolbar.setBackground(drawable);
        }
    }
}
