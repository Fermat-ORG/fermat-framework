package org.fermat.fermat_dap_android_wallet_asset_user.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.AssetUserSession;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.SessionConstantsAssetUser;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Jinmy Bohorquez on 02/18/2016.
 */
public class AssetTransferFragment extends AbstractFermatFragment {

    private Activity activity;

    private AssetUserSession assetUserSession;
    private AssetUserWalletSubAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private ImageView assetTransferImage;
    private FermatTextView assetTransferNameText;
    private FermatTextView assetTransferRemainingText;
    private FermatTextView selectedUserText;
    private FermatEditText assetsToTransferEditText;
    private View selectUserButton;
    private View transferAssetsButton;

    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

    int selectedUserCount;

//    SettingsManager<AssetUserSettings> settingsManager;

    public AssetTransferFragment() {

    }

    public static AssetTransferFragment newInstance() {
        return new AssetTransferFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assetUserSession = ((AssetUserSession) appSession);
        moduleManager = assetUserSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        activity = getActivity();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_user_asset_transfer, container, false);
        res = rootView.getResources();
        moduleManager.clearDeliverList();
        setupUI();
        setupUIData();

        return rootView;
    }

    private void setUpHelpAssetRedeem(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setVIewColor(R.color.dap_user_view_color)
                    .setTitleTextColor(R.color.dap_user_view_color)
                    .setSubTitle(R.string.dap_user_wallet_transfer_subTitle)
                    .setBody(R.string.dap_user_wallet_transfer_body)
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
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_TRANSFER, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_TRANSFER) {
                setUpHelpAssetRedeem(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetTransferImage = (ImageView) rootView.findViewById(R.id.assetTransferImage);
        assetTransferNameText = (FermatTextView) rootView.findViewById(R.id.assetTransferNameText);
        assetTransferRemainingText = (FermatTextView) rootView.findViewById(R.id.assetTransferRemainingText);
        assetsToTransferEditText = (FermatEditText) rootView.findViewById(R.id.assetsToTransferEditText);
        selectedUserText = (FermatTextView) rootView.findViewById(R.id.selectedTransferUsersText);
        selectUserButton = rootView.findViewById(R.id.selectUserButton);
        transferAssetsButton = rootView.findViewById(R.id.transferAssetsButton);

        transferAssetsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedUserCount > 0) {
                    Object x = appSession.getData("users_to_transfer");
                    if (x != null) {
                        final List<User> users = (List<User>) x;
                        if (users.size() > 0) {
                            new ConfirmDialog.Builder(getActivity(), appSession)
                                    .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                                    .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_entered_info))
                                    .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                                    .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                        @Override
                                        public void onClick() {
                                            int assetsAmount = Integer.parseInt(assetsToTransferEditText.getText().toString());
                                            doTransfer(digitalAsset, users, assetsAmount);
                                        }
                                    }).build().show();
                        }
                    }
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_users), Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                appSession.setData("asset_data", data);
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_TRANSFER_SELECT_USERS_ACTIVITY, appSession.getAppPublicKey());
            }
        });

        selectedUserCount = getUsersSelectedCount();
        String message = (selectedUserCount == 0) ? "Select users" : selectedUserCount + " users selected";
        selectedUserText.setText(message);
    }

    private void setupBackgroundBitmap() {
        AsyncTask<Void, Void, Bitmap> asyncTask = new AsyncTask<Void, Void, Bitmap>() {

            WeakReference<ViewGroup> view;

            @Override
            protected void onPreExecute() {
                view = new WeakReference(rootView);
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap drawable = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = true;
                    options.inSampleSize = 5;
                    drawable = BitmapFactory.decodeResource(
                            getResources(), R.drawable.bg_app_image_user, options);
                } catch (OutOfMemoryError error) {
                    error.printStackTrace();
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Bitmap drawable) {
                if (drawable != null) {
                    view.get().setBackground(new BitmapDrawable(getResources(), drawable));
                }
            }
        };
        asyncTask.execute();
    }

    private int getUsersSelectedCount() {
        Object x = appSession.getData("users_to_transfer");
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

    private void doTransfer(final DigitalAsset asset, final List<User> users, final int assetAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
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
                moduleManager.transferAssets(asset.getDigitalAsset(), WalletUtilities.WALLET_PUBLIC_KEY, assetAmount);
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
//                    refreshUIData();
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_transfer_ok), Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_DETAIL, appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_exception_retry),
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

        assetTransferNameText.setText(digitalAsset.getName());
        assetsToTransferEditText.setText(selectedUserCount + "");
        assetTransferRemainingText.setText(digitalAsset.getUsableAssetsQuantity() + " " + getResources().getString(R.string.dap_user_wallet_remaining_assets));

        if (digitalAsset.getUsableAssetsQuantity() == 0) {
            selectUserButton.setOnClickListener(null);
        }
    }

    private void setupUIData() {
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

//        toolbar.setTitle(digitalAsset.getName());

        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetTransferImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img); //todo comment to be able to compile

        assetTransferNameText.setText(digitalAsset.getName());

        assetsToTransferEditText.setText(selectedUserCount + "");
        long quantity = digitalAsset.getUsableAssetsQuantity();
        assetTransferRemainingText.setText(quantity + ((quantity == 1) ? " Asset" : " Assets") + " Remaining");
    }

    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_user_wallet_principal));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_user_wallet_principal));
            }
        }
    }
}
