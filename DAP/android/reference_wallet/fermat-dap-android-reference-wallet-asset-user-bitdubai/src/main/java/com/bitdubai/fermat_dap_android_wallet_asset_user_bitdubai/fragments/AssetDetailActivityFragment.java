package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;

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
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.SessionConstantsAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class AssetDetailActivityFragment extends AbstractFermatFragment {

    private AssetUserSession assetUserSession;
    private AssetUserWalletSubAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private View assetDetailRedeemLayout;
    private View assetDetailAppropriateLayout;

    private ImageView assetImageDetail;
    private FermatTextView assetDetailNameText;
    private FermatTextView assetDetailExpDateText;
    private FermatTextView availableText;
    private FermatTextView pendingText;
    private FermatTextView assetDetailBtcText;
    private FermatTextView assetDetailRedeemText;

    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

    SettingsManager<AssetUserSettings> settingsManager;

    public AssetDetailActivityFragment() {

    }

    public static AssetDetailActivityFragment newInstance() {
        return new AssetDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assetUserSession = (AssetUserSession) appSession;
        moduleManager = assetUserSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_user_asset_detail, container, false);
        res = rootView.getResources();

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setUpHelpAssetDetail(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setVIewColor(R.color.dap_user_view_color)
                    .setTitleTextColor(R.color.dap_user_view_color)
                    .setSubTitle("Asset Detail.")
                    .setBody("You can review detailed information about the selected asset.\n\n" +
                            "You can also decide to redeem your asset in a connected Redeem Point. If you are not connected to any Redeem Point, " +
                            "use the Redeem Point Community sub app to search for Redeem Points.\n\n" +
                            "You can also appropriate the asset in order to get the Bitcoins associated with it. They will be sent to your Bitcoin wallet.")
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
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_DETAIL, 0, "help").setIcon(R.drawable.dap_asset_user_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_DETAIL) {
                setUpHelpAssetDetail(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset User system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetImageDetail = (ImageView) rootView.findViewById(R.id.asset_image_detail);
        assetDetailNameText = (FermatTextView) rootView.findViewById(R.id.assetDetailNameText);
        assetDetailExpDateText = (FermatTextView) rootView.findViewById(R.id.assetDetailExpDateText);
        availableText = (FermatTextView) rootView.findViewById(R.id.assetAvailable1);
        pendingText = (FermatTextView) rootView.findViewById(R.id.assetAvailable2);
        assetDetailBtcText = (FermatTextView) rootView.findViewById(R.id.assetDetailBtcText);
        assetDetailRedeemText = (FermatTextView) rootView.findViewById(R.id.assetDetailRedeemText);

        assetDetailRedeemLayout = rootView.findViewById(R.id.assetDetailRedeemLayout);

        assetDetailAppropriateLayout = rootView.findViewById(R.id.assetDetailAppropriateLayout);
        assetDetailAppropriateLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doAppropriate(digitalAsset.getAssetPublicKey());
            }
        });

        assetDetailRedeemLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM, appSession.getAppPublicKey());
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
//        digitalAsset = (DigitalAsset) appSession.getData("asset_data");
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

        toolbar.setTitle(digitalAsset.getName());

//        if (digitalAsset.getImage() != null) {
//            assetImageDetail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
//        } else {
//            assetImageDetail.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
//        }
        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetImageDetail, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img); //todo comment to compile, please review.

        assetDetailRedeemLayout.setVisibility((digitalAsset.getAvailableBalanceQuantity() > 0) ? View.VISIBLE : View.GONE);

        assetDetailNameText.setText(digitalAsset.getName());
        assetDetailExpDateText.setText(digitalAsset.getFormattedExpDate());

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

        assetDetailBtcText.setText(digitalAsset.getFormattedAvailableBalanceBitcoin() + " BTC");
    }

    private String pendingText(long pendingValue) {
        return "(" + pendingValue + " pending confirmation)";
    }

    private String availableText(long available) {
        return available + ((available == 1) ? " Asset" : " Assets");
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
                    Toast.makeText(activity, "Appropriation of the asset has started successfully. The process will be completed in a couple of minutes.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, "Fermat Has detected an exception. Please retry again.",
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
}
