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
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.SessionConstantsAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class AssetDetailActivityFragment extends AbstractFermatFragment {

    private AssetIssuerSession assetIssuerSession;
    private AssetIssuerWalletSupAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private View assetDetailDeliverLayout;
    private View assetDetailAppropiateBtnLayout;
    private View assetDetailAvailableLayout;

    private View assetDetailAppropiateLayout;

    private View assetDetailRedeemedLayout;

    private ImageView assetImageDetail;
    private FermatTextView assetDetailNameText;
    private FermatTextView assetDetailExpDateText;
    private FermatTextView availableText;
    private FermatTextView pendingText;
    private FermatTextView assetDetailBtcText;
    private FermatTextView assetDetailRemainingText;
    private FermatTextView assetDetailDelivered;
    private FermatTextView assetDetailRedeemText;
    private FermatTextView assetDetailAppropriatedText;

    private DigitalAsset digitalAsset;

    SettingsManager<AssetIssuerSettings> settingsManager;

    public AssetDetailActivityFragment() {

    }

    public static AssetDetailActivityFragment newInstance() {
        return new AssetDetailActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assetIssuerSession = (AssetIssuerSession) appSession;
        moduleManager = assetIssuerSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_issuer_asset_detail, container, false);
        res = rootView.getResources();

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setupUI() {
        setupBackgroundBitmap();

        assetImageDetail = (ImageView) rootView.findViewById(R.id.asset_image_detail);
        assetDetailNameText = (FermatTextView) rootView.findViewById(R.id.assetDetailNameText);
        assetDetailExpDateText = (FermatTextView) rootView.findViewById(R.id.assetDetailExpDateText);
        availableText = (FermatTextView) rootView.findViewById(R.id.assetAvailable1);
        pendingText = (FermatTextView) rootView.findViewById(R.id.assetAvailable2);
        assetDetailBtcText = (FermatTextView) rootView.findViewById(R.id.assetDetailBtcText);
        //Text = (FermatTextView) rootView.findViewById(R.id.assetDetailRemainingText);
        assetDetailDelivered = (FermatTextView) rootView.findViewById(R.id.assetDetailAvailableText2);
        assetDetailRedeemText = (FermatTextView) rootView.findViewById(R.id.assetDetailRedeemText);
        assetDetailAppropriatedText = (FermatTextView) rootView.findViewById(R.id.assetDetailAppropriatedText);

        assetDetailDeliverLayout = rootView.findViewById(R.id.assetDetailDeliverLayout);
        assetDetailDeliverLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY, appSession.getAppPublicKey());
            }
        });

        assetDetailAppropiateBtnLayout = rootView.findViewById(R.id.assetDetailAppropiateBtnLayout);
        assetDetailAppropiateBtnLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new ConfirmDialog.Builder(getActivity(), appSession)
                        .setTitle(getResources().getString(R.string.dap_issuer_wallet_confirm_title))
                        .setMessage(getResources().getString(R.string.dap_issuer_wallet_confirm_sure))
                        .setColorStyle(getResources().getColor(R.color.dap_issuer_wallet_principal))
                        .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                            @Override
                            public void onClick() {
                                doAppropriate(digitalAsset.getAssetPublicKey(), digitalAsset.getWalletPublicKey());
                            }
                        }).build().show();
            }
        });

        assetDetailAvailableLayout = rootView.findViewById(R.id.assetDetailAvailableLayout);
        assetDetailAppropiateLayout = rootView.findViewById(R.id.assetDetailAppropiateLayout);
        assetDetailRedeemedLayout = rootView.findViewById(R.id.assetDetailRedeemedLayout);
    }

    private void doAppropriate(final String assetPublicKey, final String walletPublicKey) {
        final Activity activity = getActivity();
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_issuer_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.appropriateAsset(assetPublicKey, walletPublicKey);
                return true;
            }
        };
        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
                    Toast.makeText(activity, R.string.dap_issuer_wallet_appropriation_ok, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, R.string.dap_issuer_wallet_exception_retry,
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
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
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
            Data.setStatistics(WalletUtilities.WALLET_PUBLIC_KEY, digitalAsset, moduleManager);
        } catch (CantGetAssetStatisticException e) {
            e.printStackTrace();
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }

        toolbar.setTitle(digitalAsset.getName());

        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetImageDetail, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img); //todo commenting to compile, please review

        assetDetailDeliverLayout.setVisibility((digitalAsset.getAvailableBalanceQuantity() > 0) ? View.VISIBLE : View.GONE);

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
        assetDetailDelivered.setText(digitalAsset.getUnused() + "");
        assetDetailRedeemText.setText(digitalAsset.getRedeemed()+"");
        assetDetailAppropriatedText.setText(digitalAsset.getAppropriated() + "");

        assetDetailAvailableLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (digitalAsset.getUnused() > 0) {
                    changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_USER_DELIVERY_LIST, appSession.getAppPublicKey());
                } else {
                    makeText(getActivity(), R.string.dap_issuer_wallet_no_assets_delivered, Toast.LENGTH_SHORT).show();
                }
            }
        });
        assetDetailAppropiateLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (digitalAsset.getAppropriated() > 0) {
                    changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_USER_APPROPIATE_LIST, appSession.getAppPublicKey());
                } else {
                    makeText(getActivity(), R.string.dap_issuer_wallet_no_assets_appropriated, Toast.LENGTH_SHORT).show();
                }
            }
        });
        assetDetailRedeemedLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (digitalAsset.getRedeemed() > 0) {
                    changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_USER_REDEEMED_LIST, appSession.getAppPublicKey());
                } else {
                    makeText(getActivity(), R.string.dap_issuer_wallet_no_assets_redeemed, Toast.LENGTH_SHORT).show();
                }
            }
        });

        assetDetailAppropiateBtnLayout.setVisibility((digitalAsset.getAvailableBalanceQuantity() > 0) ? View.VISIBLE : View.GONE);
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
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setBottom(Color.WHITE);
            Drawable drawable = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors, null);
            else
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_issuer_action_bar_gradient_colors);
            toolbar.setBackground(drawable);
        }
    }

    private void setUpHelpAssetDetail(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_issuer_wallet)
                    .setIconRes(R.drawable.asset_issuer)
                    .setVIewColor(R.color.dap_issuer_view_color)
                    .setTitleTextColor(R.color.dap_issuer_view_color)
                    .setSubTitle(R.string.dap_issuer_wallet_detail_subTitle)
                    .setBody(R.string.dap_issuer_wallet_detail_body)
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
        menu.add(0, SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_DETAIL, 0, "Help").setIcon(R.drawable.dap_asset_issuer_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_DETAIL) {
                setUpHelpAssetDetail(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_issuer_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
