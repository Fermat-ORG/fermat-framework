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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.BitcoinsSpinnerAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;

import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.User;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.SessionConstantsAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by Jinmy Bohorquez on 15/02/2016.
 */
public class AssetSellFragment extends AbstractFermatFragment {

    private Activity activity;

    private AssetUserSession assetUserSession;
    private AssetUserWalletSubAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private ImageView assetSellImage;
    private FermatTextView assetSellNameText;
    private FermatTextView assetSellRemainingText;
    private FermatTextView selectedUserText;
    private FermatEditText assetsToSellEditText;

    private FermatEditText bitcoinsView;
    private Spinner bitcoinsSpinner;
    private FermatTextView bitcoinsTextView;
    private FermatTextView bitcoinBalanceText;

    private View selectUserButton;
    private View sellAssetsButton;
    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

    int selectedUserCount;

    SettingsManager<AssetUserSettings> settingsManager;

    private User user;

    public AssetSellFragment() {

    }

    public static AssetSellFragment newInstance() {
        return new AssetSellFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        assetUserSession = (AssetUserSession) appSession;
        moduleManager = assetUserSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();

        activity = getActivity();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_wallet_asset_user_asset_sell, container, false);
        res = rootView.getResources();


        setupUI();
        setupUIData();

        return rootView;
    }

    private void setUpHelpAssetSell(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setVIewColor(R.color.dap_user_view_color)
                    .setTitleTextColor(R.color.dap_user_view_color)
                    .setSubTitle(R.string.dap_user_wallet_sell_subTitle)
                    .setBody(R.string.dap_user_wallet_sell_body)
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
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM, 0, "help").setIcon(R.drawable.dap_asset_user_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM) {
                setUpHelpAssetSell(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
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

        assetSellImage = (ImageView) rootView.findViewById(R.id.assetSellImage);
        assetSellNameText = (FermatTextView) rootView.findViewById(R.id.assetSellNameText);
        assetSellRemainingText = (FermatTextView) rootView.findViewById(R.id.assetSellRemainingText);
        assetsToSellEditText = (FermatEditText) rootView.findViewById(R.id.assetsToSellEditText);
        selectedUserText = (FermatTextView) rootView.findViewById(R.id.selectedUsersText);
        selectUserButton = rootView.findViewById(R.id.selectUsersButton);
        sellAssetsButton = rootView.findViewById(R.id.sellAssetsButton);
        bitcoinsView = (FermatEditText) rootView.findViewById(R.id.bitcoins);
        bitcoinsSpinner = (Spinner) rootView.findViewById(R.id.bitcoinsSpinner);
        bitcoinsTextView = (FermatTextView) rootView.findViewById(R.id.bitcoinsText);
        bitcoinBalanceText = (FermatTextView) rootView.findViewById(R.id.bitcoinBalanceText);

        bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
        final BitcoinConverter.Currency[] data = BitcoinConverter.Currency.values();
        final ArrayAdapter<BitcoinConverter.Currency> spinnerAdapter = new BitcoinsSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                data);
        bitcoinsSpinner.setAdapter(spinnerAdapter);
        bitcoinsSpinner.setSelection(3);
        bitcoinsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateBitcoins();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        layout = rootView.findViewById(R.id.assetDetailRemainingLayout);
        sellAssetsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {/*
                if (selectedUserCount > 0) {
                    Object x = appSession.getData("redeem_points");
                    if (x != null) {
                        final List<User> redeemPoints = (List<User>) x;
                        if (redeemPoints.size() > 0) {
                            new ConfirmDialog.Builder(getActivity(), appSession)
                                    .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_entered_info))
                                                    .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                                                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                                                @Override
                                                                public void onClick() {
                                                                    int assetsAmount = Integer.parseInt(assetsToSellEditText.getText().toString());
                                                                    doSell(digitalAsset.getAssetPublicKey(), redeemPoints, assetsAmount);
                                                                }
                                                            }).build().show();
                        }
                    }
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_redeem_points), Toast.LENGTH_SHORT).show();
                }*/
                //TODO: agregar la funcion del sellAssetButton
            }
        });
        bitcoinsView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                updateBitcoins();
                return false;
            }
        });
        selectUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                appSession.setData("asset_data", data);
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_SELL_SELECT_USERS_ACTIVITY, appSession.getAppPublicKey());
                //TODO: aqui va la actividad de seleccion de users, users conectados a users la cual no existe
            }
        });
        selectedUserText.setText(getResources().getString(R.string.dap_user_wallet_sell_select_user));
    }
    private void updateBitcoins() {
        Object selectedItem = bitcoinsSpinner.getSelectedItem();
        String bitcoinViewStr = bitcoinsView.getText().toString();
        if (selectedItem != null && bitcoinViewStr != null && bitcoinViewStr.length() > 0) {
            BitcoinConverter.Currency from = (BitcoinConverter.Currency) bitcoinsSpinner.getSelectedItem();
            double amount = Double.parseDouble(bitcoinsView.getText().toString());
            double amountBTC = BitcoinConverter.convert(amount, from, BITCOIN);
            bitcoinsTextView.setText(String.format("%.6f BTC", amountBTC));
            int quantity = Integer.parseInt(assetsToSellEditText.getText().toString());
            bitcoinBalanceText.setText(String.format("%.6f BTC", quantity * amountBTC));
        }
    }
    private long getSatoshis() {
        String amountStr = bitcoinsView.getText().toString().trim();
        if (amountStr != null && amountStr.length() > 0) {
            BitcoinConverter.Currency currency = (BitcoinConverter.Currency) bitcoinsSpinner.getSelectedItem();
            double amount = Double.parseDouble(amountStr);
            return Double.valueOf(BitcoinConverter.convert(amount, currency, SATOSHI)).longValue();
        } else {
            return 0;
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

    private void doSell(final String assetPublicKey, final List<User> redeemPoints, final int assetAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                //moduleManager.redeemAssetToRedeemPoint(assetPublicKey, null, Data.getRedeemPoints(redeemPoints), assetAmount);
                //TODO implementar metodos de sellAsset y agregarlos aqui
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
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_sell_ok), Toast.LENGTH_LONG).show();
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

        assetSellNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetsToSellEditText.setText(selectedUserCount + "");
        assetSellRemainingText.setText(digitalAsset.getAvailableBalanceQuantity() + " " + getResources().getString(R.string.dap_user_wallet_remaining_assets));

        if (digitalAsset.getAvailableBalanceQuantity() == 0) {
            selectUserButton.setOnClickListener(null);
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
//            assetSellImage.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(digitalAsset.getImage())));
//        } else {
//            assetSellImage.setImageDrawable(rootView.getResources().getDrawable(R.drawable.img_asset_without_image));
//        }
        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetSellImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        assetSellNameText.setText(digitalAsset.getName());
//        assetsToSellEditText.setText(digitalAsset.getAvailableBalanceQuantity() + "");
        assetsToSellEditText.setText(selectedUserCount+"");
        long quantity = digitalAsset.getAvailableBalanceQuantity();
        assetSellRemainingText.setText(quantity + ((quantity == 1) ? " Asset" : " Assets") + " Remaining");

        Object x = appSession.getData("user_selected");
        if (x != null) {
            user = (User) x;
            selectedUserText.setText(user.getName());
        }
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
