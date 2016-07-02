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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.CryptoVault;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.adapters.BitcoinsSpinnerAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.adapters.UserSelectorAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.SellInfo;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.data.DataManager;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by Jinmy Bohorquez on 15/02/2016.
 */
public class UserSellAssetFragment extends FermatWalletListFragment<User, ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<User> {


    private static final String TAG = "AssetSellUsersFragment";

    private Activity activity;

    private AssetUserWalletSubAppModuleManager moduleManager;

    private Asset assetToSell;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;
    List<User> users;
    private User user;
    String digitalAssetPublicKey;

    private FermatEditText assetPrice;
    private FermatTextView assetPriceView;
    private Spinner assetCurrencySpinner;
    //private AutoCompleteTextView userToSelectText;
    private FermatEditText userToSelectText;
    private View sellButton;
    private View eraseButton;

    //private ListView usersListView;

    private View noUsersView;
    private User userSelected;

    public static UserSellAssetFragment newInstance() {
        return new UserSellAssetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        try {

            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            activity = getActivity();

            users = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_ASSET_ISSUER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

    }

    //    private List<User> getUsers(){
//        List<User> users = new ArrayList<>();
//        try {
//            users = Data.getConnectedUsers(moduleManager);
//        } catch (CantGetAssetUserActorsException e) {
//            e.printStackTrace();
//        }
//        return users;
//    }
    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        assetToSell = (Asset) appSession.getData("asset_data");
        digitalAssetPublicKey = assetToSell.getDigitalAsset().getPublicKey();
        //digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);

        noUsersView = layout.findViewById(R.id.asset_sell_no_users_view);
        assetPrice = (FermatEditText) layout.findViewById(R.id.assetSellbitcoins);
        assetPriceView = (FermatTextView) layout.findViewById(R.id.assetSellbitcoinsText);
        assetCurrencySpinner = (Spinner) layout.findViewById(R.id.assetCurrencySpinner);
        sellButton = layout.findViewById(R.id.sellButton);
        eraseButton = layout.findViewById(R.id.eraseButton);


        userToSelectText = (FermatEditText) layout.findViewById(R.id.userToSelectText);

        TextWatcher filterTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (((UserSelectorAdapter) getAdapter()).getFilter()).filter(s);
                if (s.length() > 0) {
                    eraseButton.setVisibility(View.VISIBLE);
                } else {
                    eraseButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        userToSelectText.addTextChangedListener(filterTextWatcher);

        assetPrice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                updateBitcoins();
                return false;
            }
        });

        showOrHideNoUsersView(users.isEmpty());

        assetPriceView.setText(String.format("%.6f BTC", 0.0));
        final BitcoinConverter.Currency[] currenciesSpinner = BitcoinConverter.Currency.values();
        final ArrayAdapter<BitcoinConverter.Currency> bitcoinsSpinnerAdapter = new BitcoinsSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                currenciesSpinner);
        assetCurrencySpinner.setAdapter(bitcoinsSpinnerAdapter);
        assetCurrencySpinner.setSelection(3);
        assetCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) assetCurrencySpinner.getSelectedView()).setTextColor(getResources().getColor(R.color.color_black_light));
                updateBitcoins();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userToSelectText.setText("");
                userSelected = null;
                for (User user : users) {
                    user.setSelected(false);
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void doSell(final String assetPublicKey, final User user, final long amountPerUnity, final long amountTotal, final int quantityToSell) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.startSell(user.getActorAssetUser(), amountPerUnity, amountTotal, quantityToSell, assetPublicKey);
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
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_V3_HOME, appSession.getAppPublicKey());
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

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_v3_wallet_asset_user_sell;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.asset_sell_swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_user_asset_sell_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                users = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(users);

                showOrHideNoUsersView(users.isEmpty());
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new UserSelectorAdapter(getActivity(), users, moduleManager);
            adapter.setFermatListEventListener(this);
        }
//        else {
//            adapter.changeDataSet(users);
//        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public void onItemClickListener(User data, int position) {
        appSession.setData("user_selected", data);

        for (User user : users) {
            if (!data.equals(user)) {
                user.setSelected(false);
            } else {
                data.setSelected(!data.isSelected());
                if (data.isSelected()) {
                    userSelected = data;
                    userToSelectText.setText(data.getName());
                } else {
                    userSelected = null;
                    userToSelectText.setText("");

                }
            }
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLongItemClickListener(User data, int position) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        try {
//            IssuerWalletNavigationViewPainter navigationViewPainter = new IssuerWalletNavigationViewPainter(getActivity(), null);
//            getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
//        } catch (Exception e) {
//            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
//            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
//        }
    }


    @Override
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
//                case 1://IC_ACTION_USER_HELP_REDEEM
//                    setUpHelpAssetRedeem(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                    break;
                case 2://IC_ACTION_USER_ASSET_REDEEM
                    if (isValidSell()) {
                        new ConfirmDialog.Builder(getActivity(), appSession)
                                .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                                .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_asset_sell))
                                .setColorStyle(getResources().getColor(R.color.card_toolbar))
                                .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                    @Override
                                    public void onClick() {
                                        BitcoinConverter.Currency from = (BitcoinConverter.Currency) assetCurrencySpinner.getSelectedItem();
                                        long sellPrice = (long) BitcoinConverter.convert(Double.parseDouble(assetPrice.getText().toString()), from, SATOSHI);
                                        doSell(digitalAssetPublicKey, userSelected, sellPrice, sellPrice, 1);
                                    }
                                }).build().show();
                    }
                    break;
            }

//            if (id == SessionConstantsAssetUser.IC_ACTION_USER_ITEM_SELL) {
//                if (isValidSell()) {
//                    new ConfirmDialog.Builder(getActivity(), appSession)
//                            .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
//                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_asset_sell))
//                            .setColorStyle(getResources().getColor(R.color.card_toolbar))
//                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
//                                @Override
//                                public void onClick() {
//                                    BitcoinConverter.Currency from = (BitcoinConverter.Currency) assetCurrencySpinner.getSelectedItem();
//                                    long sellPrice = (long) BitcoinConverter.convert(Double.parseDouble(assetPrice.getText().toString()), from, SATOSHI);
//                                    doSell(digitalAssetPublicKey, userSelected, sellPrice, sellPrice, 1);
//                                }
//                            }).build().show();
//                }
//            }
//            return true;

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

        if (assetPrice.getText() != null && assetPrice.getText().length() != 0) {
            double total = Double.parseDouble(assetPrice.getText().toString());
            if (total == 0) {
                makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_total_zero),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
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
        if (userSelected == null) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_sell_user),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateBitcoins() {
        Object selectedItem = assetCurrencySpinner.getSelectedItem();
        String bitcoinStr = assetPrice.getText().toString();

        if (selectedItem != null && bitcoinStr.length() > 0) {
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


    private void refreshUIData() {
        String digitalAssetPublicKey = ((DigitalAsset) appSession.getData("asset_data")).getAssetPublicKey();
        try {
//            digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
            assetToSell = (Asset) DataManager.getAssets();

        } catch (CantLoadWalletException e) {
            e.printStackTrace();
        } catch (CantGetTransactionsException e) {
            e.printStackTrace();
        }

        //assetSellNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");

        /*if (digitalAsset.getUsableAssetsQuantity() == 0) {
            selectUserButton.setOnClickListener(null);
        }*/
    }

    @Override
    public List<User> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<User> users = new ArrayList<>();
        if (moduleManager != null) {
            try {
                //DigitalAsset digitalAsset = (DigitalAsset) appSession.getData("asset_data");
//                users = Data.getConnectedUsers(moduleManager);
                users = DataManager.getConnectedUsers();
                appSession.setData("users", users);
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.DAP_ASSET_USER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).
                    show();
        }
        return users;
    }

    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.card_toolbar));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.card_toolbar));
            }
        }
    }

    private void showOrHideNoUsersView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noUsersView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noUsersView.setVisibility(View.GONE);
        }
    }


}
