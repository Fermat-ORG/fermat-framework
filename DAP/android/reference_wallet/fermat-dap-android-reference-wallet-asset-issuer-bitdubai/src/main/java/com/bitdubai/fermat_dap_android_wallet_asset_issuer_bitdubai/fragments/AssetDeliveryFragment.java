package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.dialogs.DistributeAcceptDialog;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.User;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.SessionConstantsAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class AssetDeliveryFragment extends AbstractFermatFragment {

    private Activity activity;
    private static final int MAX_ASSET_QUANTITY = 200;
    private AssetIssuerSession assetIssuerSession;
    private AssetIssuerWalletSupAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private ImageView assetDeliveryImage;
    private FermatTextView assetDeliveryNameText;
    private FermatTextView assetDeliveryRemainingText;
    private FermatTextView selectedUsersText;
    private FermatEditText assetsToDeliverEditText;
    private View selectUsersButton;
    private View deliverAssetsButton;

    private DigitalAsset digitalAsset;

    int selectedUsersCount;

    SettingsManager<AssetIssuerSettings> settingsManager;

    public AssetDeliveryFragment() {

    }

    public static AssetDeliveryFragment newInstance() {
        return new AssetDeliveryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assetIssuerSession = (AssetIssuerSession) appSession;
        moduleManager = assetIssuerSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();

        activity = getActivity();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_issuer_asset_delivery, container, false);
        res = rootView.getResources();

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setUpHelpAssetStatistics(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_issuer_wallet)
                    .setIconRes(R.drawable.asset_issuer)
                    .setVIewColor(R.color.dap_issuer_view_color)
                    .setTitleTextColor(R.color.dap_issuer_view_color)
                    .setSubTitle("Asset delivery section.")
                    .setBody("On this section you will be able to identify the users you are going to deliver this asset to.\n\n" +
                            "You can deliver as many assets as you have to any connected user. \n\n" +
                            "If no users are available, you will have to connect to them using the User Community sub application.")
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_DELIVERY, 0, "help").setIcon(R.drawable.dap_asset_issuer_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_DELIVERY) {
                setUpHelpAssetStatistics(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Issuer system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
                if (assetsToDeliverEditText.getText().length() == 0) {
                    Toast.makeText(activity, "Must be enter the number of assets to deliver", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(assetsToDeliverEditText.getText().toString()) > MAX_ASSET_QUANTITY){
                    Toast.makeText(activity, "Value can't be greater than "+MAX_ASSET_QUANTITY, Toast.LENGTH_SHORT).show();
                } else if (digitalAsset.getAvailableBalanceQuantity() == 0) {
                    Toast.makeText(activity, "There is not assets to distribute", Toast.LENGTH_SHORT).show();
                } else if (selectedUsersCount == 0) {
                    Toast.makeText(activity, "No users selected", Toast.LENGTH_SHORT).show();
                } else if (selectedUsersCount > digitalAsset.getAvailableBalanceQuantity()) {
                    Toast.makeText(activity, "There is not enought assets to distribute", Toast.LENGTH_SHORT).show();
                } else {
                    Object x = appSession.getData("users");
                    if (x != null) {
                        final List<User> users = (List<User>) x;
                        if (users.size() > 0) {
                            DistributeAcceptDialog dialog = new DistributeAcceptDialog(getActivity(), (AssetIssuerSession) appSession, appResourcesProviderManager);
                            dialog.setYesBtnListener(new DistributeAcceptDialog.OnClickAcceptListener() {
                                @Override
                                public void onClick() {
                                    int assetsAmount = Integer.parseInt(assetsToDeliverEditText.getText().toString());
                                    doDistribute(digitalAsset.getAssetPublicKey(), users, assetsAmount);
                                }
                            });
                            dialog.show();
                        }
                    }
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

    private void doDistribute(final String assetPublicKey, final List<User> users, final int assetsAmount) {
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
                    moduleManager.distributionAssets(assetPublicKey, null, assetsAmount);
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
                    refreshUIData();
                    //Toast.makeText(activity, "Everything ok...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity, "Assets are being delivered. It may take a couple of minutes to confirm or rollback depending on your network connection.", Toast.LENGTH_LONG).show();
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

    private void refreshUIData() {
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

        assetDeliveryNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetsToDeliverEditText.setText(selectedUsersCount+"");
        assetDeliveryRemainingText.setText(digitalAsset.getAvailableBalanceQuantity() + " Assets Remaining");

        if (digitalAsset.getAvailableBalanceQuantity() == 0) {
            selectUsersButton.setOnClickListener(null);
        }
    }

    private void setupUIData() {
//        digitalAsset = (DigitalAsset) appSession.getData("asset_data");
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

        toolbar.setTitle(digitalAsset.getName());

//        if (digitalAsset.getImage() != null) {
//            assetDeliveryImage.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
//        } else {
//            assetDeliveryImage.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
//        }

        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetDeliveryImage, res, R.drawable.img_asset_without_image, false);
        //bitmapWorkerTask.execute(img); //todo commenting to compile, please review

        assetDeliveryNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetsToDeliverEditText.setText(selectedUsersCount+"");
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
