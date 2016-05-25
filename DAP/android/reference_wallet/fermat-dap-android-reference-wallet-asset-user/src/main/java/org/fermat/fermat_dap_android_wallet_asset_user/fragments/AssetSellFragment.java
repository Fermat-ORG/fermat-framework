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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.CryptoVault;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.adapters.BitcoinsSpinnerAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.SellInfo;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.AssetUserSession;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.SessionConstantsAssetUser;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.lang.ref.WeakReference;

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

    private FermatEditText bitcoins;
    private Spinner bitcoinsSpinner;
    private FermatTextView bitcoinsTextView;

    private FermatEditText bitcoinsTotal;
    private Spinner bitcoinsTotalSpinner;
    private FermatTextView bitcoinsTotalText;

    private View selectUserButton;
    private View sellAssetsButton;
    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

//    SettingsManager<AssetUserSettings> settingsManager;

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

        assetUserSession = ((AssetUserSession) appSession);
        moduleManager = assetUserSession.getModuleManager();
        errorManager = appSession.getErrorManager();

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
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM) {
                setUpHelpAssetSell(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
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
        bitcoins = (FermatEditText) rootView.findViewById(R.id.bitcoins);
        bitcoinsSpinner = (Spinner) rootView.findViewById(R.id.bitcoinsSpinner);
        bitcoinsTextView = (FermatTextView) rootView.findViewById(R.id.bitcoinsText);
        bitcoinsTotal = (FermatEditText) rootView.findViewById(R.id.bitcoinsTotal);
        bitcoinsTotalSpinner = (Spinner) rootView.findViewById(R.id.bitcoinsTotalSpinner);
        bitcoinsTotalText = (FermatTextView) rootView.findViewById(R.id.bitcoinsTotalText);


        bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
        final BitcoinConverter.Currency[] currenciesSpinner = BitcoinConverter.Currency.values();
        final ArrayAdapter<BitcoinConverter.Currency> bitcoinsSpinnerAdapter = new BitcoinsSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                currenciesSpinner);
        bitcoinsSpinner.setAdapter(bitcoinsSpinnerAdapter);
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

        bitcoinsTotalText.setText(String.format("%.6f BTC", 0.0));
        final BitcoinConverter.Currency[] currenciesSpinnerTotal = BitcoinConverter.Currency.values();
        final ArrayAdapter<BitcoinConverter.Currency> bitcoinsSpinnerTotalAdapter = new BitcoinsSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                currenciesSpinnerTotal);
        bitcoinsTotalSpinner.setAdapter(bitcoinsSpinnerTotalAdapter);
        bitcoinsTotalSpinner.setSelection(3);
        bitcoinsTotalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateBitcoinsTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        layout = rootView.findViewById(R.id.assetDetailRemainingLayout);
        sellAssetsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isValidSell()) {
                    new ConfirmDialog.Builder(getActivity(), appSession)
                            .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_entered_info))
                            .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                @Override
                                public void onClick() {
                                    BitcoinConverter.Currency from = (BitcoinConverter.Currency) bitcoinsSpinner.getSelectedItem();
                                    BitcoinConverter.Currency fromTotal = (BitcoinConverter.Currency) bitcoinsTotalSpinner.getSelectedItem();
                                    long amountPerUnity = (long) BitcoinConverter.convert(Double.parseDouble(bitcoins.getText().toString()), from, SATOSHI);
                                    long amountTotal = (long) BitcoinConverter.convert(Double.parseDouble(bitcoinsTotal.getText().toString()), fromTotal, SATOSHI);
                                    int quantityToSell = Integer.parseInt(assetsToSellEditText.getText().toString());

                                    doSell(digitalAsset.getAssetPublicKey(), user, amountPerUnity, amountTotal, quantityToSell);
                                }
                            }).build().show();
                }
            }
        });
        assetsToSellEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                updateBitcoins();
                return false;
            }
        });
        bitcoins.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                updateBitcoins();
                return false;
            }
        });
        bitcoinsTotal.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                updateBitcoinsTotal();
                return false;
            }
        });
        selectUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                appSession.setData("asset_data", data);
                appSession.setData("sell_info", getSellInfo());
                changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_SELL_SELECT_USERS_ACTIVITY, appSession.getAppPublicKey());
                //TODO: aqui va la actividad de seleccion de users, users conectados a users la cual no existe
            }
        });
        selectedUserText.setText(getResources().getString(R.string.dap_user_wallet_sell_select_user));
    }

    private SellInfo getSellInfo() {
        SellInfo sellInfo = new SellInfo();
        sellInfo.setAssetsToSell(assetsToSellEditText.getText().toString());
        sellInfo.setAssetValue(bitcoins.getText().toString());
        sellInfo.setTotalValue(bitcoinsTotal.getText().toString());
        sellInfo.setAssetValueCurrencyIndex(bitcoinsSpinner.getSelectedItemPosition());
        sellInfo.setTotalValueCurrencyIndex(bitcoinsTotalSpinner.getSelectedItemPosition());
        return sellInfo;
    }

    private void setSellInfo(SellInfo sellInfo) {
        assetsToSellEditText.setText(sellInfo.getAssetsToSell());
        bitcoins.setText(sellInfo.getAssetValue());
        bitcoinsTotal.setText(sellInfo.getTotalValue());
        bitcoinsSpinner.setSelection(sellInfo.getAssetValueCurrencyIndex());
        bitcoinsTotalSpinner.setSelection(sellInfo.getTotalValueCurrencyIndex());
    }

    private boolean isValidSell() {
        String assetsToSellStr = assetsToSellEditText.getText().toString();
        if (assetsToSellStr.length() == 0) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_quantity_zero),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        int quantity = Integer.parseInt(assetsToSellStr);
        if (quantity == 0) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_quantity_zero),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (quantity > digitalAsset.getUsableAssetsQuantity()) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_quantity),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        String bitcoinsTotalStr = bitcoinsTotal.getText().toString();
        if (bitcoinsTotalStr.length() == 0) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_total_zero),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        double total = Double.parseDouble(bitcoinsTotal.getText().toString());
        if (total == 0) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_total_zero),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        BitcoinConverter.Currency from = (BitcoinConverter.Currency) bitcoinsSpinner.getSelectedItem();
        long amountPerUnity = (long) BitcoinConverter.convert(Double.parseDouble(bitcoins.getText().toString()), from, SATOSHI);
        if (CryptoVault.isDustySend(amountPerUnity)) {
            makeText(getActivity(), "The minimum monetary amount for any Asset is " + BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND + " satoshis.\n" +
                            " \n This is needed to pay the fee of bitcoin transactions during delivery of the assets.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (appSession.getData("user_selected") == null) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_user),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateBitcoins() {
        Object selectedItem = bitcoinsSpinner.getSelectedItem();
        String bitcoinStr = bitcoins.getText().toString();
        String assetsToSellStr = assetsToSellEditText.getText().toString();
        if (selectedItem != null && bitcoinStr.length() > 0 && assetsToSellStr.length() > 0) {
            BitcoinConverter.Currency from = (BitcoinConverter.Currency) selectedItem;
            double amount = Double.parseDouble(bitcoins.getText().toString());
            double amountBTC = BitcoinConverter.convert(amount, from, BITCOIN);
            int quantity = Integer.parseInt(assetsToSellStr);
            bitcoinsTextView.setText(String.format("%.6f BTC", amountBTC));
            bitcoinsTotalSpinner.setSelection(bitcoinsSpinner.getSelectedItemPosition());
            bitcoinsTotal.setText(DAPStandardFormats.EDIT_NUMBER_FORMAT.format((amount * quantity)));
        } else if (bitcoinStr.length() == 0) {
            bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
            bitcoinsTotal.setText("");
        } else if (assetsToSellStr.length() == 0) {
            bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
            bitcoinsTotal.setText("");
        }
        updateBitcoinsTotal();
    }

    private void updateBitcoinsTotal() {
        Object selectedItem = bitcoinsTotalSpinner.getSelectedItem();
        String bitcoinTotalStr = bitcoinsTotal.getText().toString();
        if (selectedItem != null && bitcoinTotalStr.length() > 0) {
            BitcoinConverter.Currency from = (BitcoinConverter.Currency) selectedItem;
            double amount = Double.parseDouble(bitcoinsTotal.getText().toString());
            double amountBTC = BitcoinConverter.convert(amount, from, BITCOIN);
            bitcoinsTotalText.setText(String.format("%.6f BTC", amountBTC));
        } else if (bitcoinTotalStr.length() == 0) {
            bitcoinsTotalText.setText(String.format("%.6f BTC", 0.0));
        }
    }

    private long getSatoshis() {
        String amountStr = bitcoins.getText().toString().trim();
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

    private void doSell(final String assetPublicKey, final User user, final long amountPerUnity, final long amountTotal, final int quantityToSell) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.startSell(user.getActorAssetUser(), amountPerUnity, amountTotal, quantityToSell, digitalAsset.getAssetPublicKey());
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
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_sell_ok), Toast.LENGTH_LONG).show();
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

        assetSellNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetSellRemainingText.setText(digitalAsset.getUsableAssetsQuantity() + " " + getResources().getString(R.string.dap_user_wallet_remaining_assets));

        if (digitalAsset.getUsableAssetsQuantity() == 0) {
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

//        toolbar.setTitle(digitalAsset.getName());

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
        long quantity = digitalAsset.getUsableAssetsQuantity();
        assetSellRemainingText.setText(quantity + ((quantity == 1) ? " Asset" : " Assets") + " Remaining");

        Object x = appSession.getData("user_selected");
        if (x != null) {
            user = (User) x;
            selectedUserText.setText(user.getName());
        }

        if (appSession.getData("sell_info") != null) {
            SellInfo sellInfo = (SellInfo) appSession.getData("sell_info");
            setSellInfo(sellInfo);
        } else {
            assetsToSellEditText.setText(Long.toString(quantity));
            bitcoins.setText(digitalAsset.getAmount());
        }
        updateBitcoins();
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
