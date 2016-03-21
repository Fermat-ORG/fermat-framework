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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.CryptoVault;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.BitcoinsSpinnerAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.UserSelectorAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.SellInfo;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.User;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.SessionConstantsAssetUser;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by Jinmy Bohorquez on 15/02/2016.
 */
public class UserSellAssetFragment extends AbstractFermatFragment {

    private static final String TAG = "AssetSellUsersFragment";

    private Activity activity;

    private AssetUserSession assetUserSession;
    private AssetUserWalletSubAppModuleManager moduleManager;
    private UserSelectorAdapter adapter;



    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;
    SettingsManager<AssetUserSettings> settingsManager;
    List<User> users;
    private User user;

    private FermatEditText assetPrice;
    private FermatTextView assetPriceView;
    private Spinner assetCurrencySpinner;
    private AutoCompleteTextView userToSelectText;
    private View sellButton;
    //private ListView usersList;


    public UserSellAssetFragment() {

    }

    public static UserSellAssetFragment newInstance() {
        return new UserSellAssetFragment();
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

        users = getUsers();

        adapter = new UserSelectorAdapter(activity,R.layout.dap_v3_wallet_asset_user_sell,R.id.userName,R.id.imageView_user_sell_avatar,users);
        configureToolbar();
    }
    private List<User> getUsers(){
        List<User> users = new ArrayList<>();
        try {
            users = Data.getConnectedUsers(moduleManager);
        } catch (CantGetAssetUserActorsException e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_v3_wallet_asset_user_sell, container, false);
        res = rootView.getResources();


        setupUI();
        setupUIData();

        return rootView;
    }


    private void setupUI() {
        assetPrice = (FermatEditText) rootView.findViewById(R.id.assetSellbitcoins);
        assetPriceView = (FermatTextView) rootView.findViewById(R.id.assetSellbitcoinsText);
        assetCurrencySpinner = (Spinner) rootView.findViewById(R.id.assetCurrencySpinner);
        userToSelectText = (AutoCompleteTextView) rootView.findViewById(R.id.userToSelectText);
        sellButton = rootView.findViewById(R.id.sellButton);
        //usersList = (ListView) rootView.findViewById(R.id.userToSelectText);

        assetPrice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                updateBitcoins();
                return false;
            }
        });
    }
    private void setupUIData() {
        userToSelectText.setThreshold(1);
//        usersList.setTextFilterEnabled(true);
//        digitalAsset = (DigitalAsset) appSession.getData("asset_data");
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        }


        /*byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetSellImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);*/

        //assetSellNameText.setText(digitalAsset.getName());


        Object x = appSession.getData("user_selected");
        if (x != null) {
            user = (User) x;
            //txtSearch.setText(user.getName());
        }

        if (appSession.getData("sell_info") != null) {
            SellInfo sellInfo = (SellInfo) appSession.getData("sell_info");
            setSellInfo(sellInfo);
        } else {

            assetPrice.setText(digitalAsset.getAmount());
        }
        updateBitcoins();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        /*menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);*/
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            /*if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM) {
                setUpHelpAssetSell(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }*/

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private SellInfo getSellInfo() {
        SellInfo sellInfo = new SellInfo();
        sellInfo.setAssetValue(assetPrice.getText().toString());
        sellInfo.setAssetValueCurrencyIndex(assetCurrencySpinner.getSelectedItemPosition());
        return sellInfo;
    }

    private void setSellInfo(SellInfo sellInfo) {

        assetPrice.setText(sellInfo.getAssetValue());
        assetCurrencySpinner.setSelection(sellInfo.getAssetValueCurrencyIndex());

    }

    private boolean isValidSell() {


        String bitcoinsTotalStr = assetPrice.getText().toString();
        double total = Double.parseDouble(assetPrice.getText().toString());
        if (total == 0) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_total_zero),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        BitcoinConverter.Currency from = (BitcoinConverter.Currency) assetCurrencySpinner.getSelectedItem();
        long amountPerUnity = (long) BitcoinConverter.convert(Double.parseDouble(assetPrice.getText().toString()), from, SATOSHI);
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
        Object selectedItem = assetCurrencySpinner.getSelectedItem();
        String bitcoinStr = assetPrice.getText().toString();

        if (selectedItem != null && bitcoinStr.length() > 0 ) {
            BitcoinConverter.Currency from = (BitcoinConverter.Currency) selectedItem;
            double amount = Double.parseDouble(assetPrice.getText().toString());
            double amountBTC = BitcoinConverter.convert(amount, from, BITCOIN);

            assetPriceView.setText(String.format("%.6f BTC", amountBTC));
        } else if (bitcoinStr.length() == 0) {
            assetPriceView.setText(String.format("%.6f BTC", 0.0));

        }
    }

    private long getSatoshis() {
        String amountStr = assetPrice.getText().toString().trim();
        if (amountStr != null && amountStr.length() > 0) {
            BitcoinConverter.Currency currency = (BitcoinConverter.Currency) assetCurrencySpinner.getSelectedItem();
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

        //assetSellNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");

        /*if (digitalAsset.getUsableAssetsQuantity() == 0) {
            selectUserButton.setOnClickListener(null);
        }*/
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
