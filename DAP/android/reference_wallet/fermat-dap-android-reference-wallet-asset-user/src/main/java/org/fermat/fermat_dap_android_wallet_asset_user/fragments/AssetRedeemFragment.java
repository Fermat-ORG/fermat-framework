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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
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
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class AssetRedeemFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager>, ResourceProviderManager> {

    private Activity activity;
    private AssetUserWalletSubAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private ImageView assetRedeemImage;
    private FermatTextView assetRedeemNameText;
    private FermatTextView assetRedeemRemainingText;
    private FermatTextView selectedRPText;
    private FermatEditText assetsToRedeemEditText;
    private View selectRPButton;
    private View redeemAssetsButton;

    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

    int selectedRPCount;

    public AssetRedeemFragment() {

    }

    public static AssetRedeemFragment newInstance() {
        return new AssetRedeemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        activity = getActivity();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_user_asset_redeem, container, false);
        res = rootView.getResources();

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
                    .setSubTitle(R.string.dap_user_wallet_redeem_subTitle)
                    .setBody(R.string.dap_user_wallet_redeem_body)
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
//        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM, 0, "Help")
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 1://IC_ACTION_USER_HELP_REDEEM
                    setUpHelpAssetRedeem(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }

//            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM) {
//                setUpHelpAssetRedeem(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetRedeemImage = (ImageView) rootView.findViewById(R.id.assetRedeemImage);
        assetRedeemNameText = (FermatTextView) rootView.findViewById(R.id.assetRedeemNameText);
        assetRedeemRemainingText = (FermatTextView) rootView.findViewById(R.id.assetRedeemRemainingText);
        assetsToRedeemEditText = (FermatEditText) rootView.findViewById(R.id.assetsToRedeemEditText);
        selectedRPText = (FermatTextView) rootView.findViewById(R.id.selectedRedeemPointsText);
        selectRPButton = rootView.findViewById(R.id.selectRedeemPointsButton);
        redeemAssetsButton = rootView.findViewById(R.id.redeemAssetsButton);

//        layout = rootView.findViewById(R.id.assetDetailRemainingLayout);
        redeemAssetsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectedRPCount > 0) {
                    Object x = appSession.getData("redeem_points");
                    if (x != null) {
                        final List<RedeemPoint> redeemPoints = (List<RedeemPoint>) x;
                        if (redeemPoints.size() > 0) {
                            new ConfirmDialog.Builder(getActivity(), appSession)
                                    .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                                    .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_entered_info))
                                    .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                                    .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                        @Override
                                        public void onClick() {
                                            int assetsAmount = Integer.parseInt(assetsToRedeemEditText.getText().toString());
                                            doRedeem(digitalAsset, redeemPoints, assetsAmount);
                                        }
                                    }).build().show();
                        }
                    }
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_redeem_points), Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectRPButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                appSession.setData("asset_data", data);
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM_SELECT_REDEEMPOINTS, appSession.getAppPublicKey());
            }
        });

        selectedRPCount = getRedeemPointsSelectedCount();
        String message = (selectedRPCount == 0) ? "Select redeem points" : selectedRPCount + " redeem points selected";
        selectedRPText.setText(message);
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

    private int getRedeemPointsSelectedCount() {
        Object x = appSession.getData("redeem_points");
        int count = 0;
        if (x != null) {
            List<RedeemPoint> redeemPoints = (List<RedeemPoint>) x;
            if (redeemPoints.size() > 0) {
                for (RedeemPoint redeemPoint :
                        redeemPoints) {
                    if (redeemPoint.isSelected()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void doRedeem(final DigitalAsset asset, final List<RedeemPoint> redeemPoints, final int assetAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.redeemAssetToRedeemPoint(asset.getDigitalAsset(), WalletUtilities.WALLET_PUBLIC_KEY, Data.getRedeemPoints(redeemPoints), assetAmount);
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
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_redeem_ok), Toast.LENGTH_LONG).show();
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

        assetRedeemNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetsToRedeemEditText.setText(selectedRPCount + "");
        assetRedeemRemainingText.setText(digitalAsset.getUsableAssetsQuantity() + " " + getResources().getString(R.string.dap_user_wallet_remaining_assets));

        if (digitalAsset.getUsableAssetsQuantity() == 0) {
            selectRPButton.setOnClickListener(null);
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

//        toolbar.setTitle(digitalAsset.getName());

//        if (digitalAsset.getImage() != null) {
//            assetRedeemImage.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
//        } else {
//            assetRedeemImage.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
//        }
        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetRedeemImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img); //todo comment to be able to compile

        assetRedeemNameText.setText(digitalAsset.getName());
//        assetsToRedeemEditText.setText(digitalAsset.getAvailableBalanceQuantity() + "");
        assetsToRedeemEditText.setText(selectedRPCount + "");
        long quantity = digitalAsset.getUsableAssetsQuantity();
        assetRedeemRemainingText.setText(quantity + ((quantity == 1) ? " Asset" : " Assets") + " Remaining");
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
