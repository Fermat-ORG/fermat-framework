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
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import org.fermat.fermat_dap_android_wallet_asset_user.models.Data;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.AssetUserSession;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.SessionConstantsAssetUser;
import org.fermat.fermat_dap_android_wallet_asset_user.util.Utils;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.AssetNegotiation;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.lang.ref.WeakReference;
import java.util.UUID;

import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by Jinmy Bohorquez on 15/02/2016.
 */
public class AssetNegotiationDetailFragment extends AbstractFermatFragment {

    private Activity activity;

    private AssetUserSession assetUserSession;
    private AssetUserWalletSubAppModuleManager moduleManager;

    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    
    private ImageView assetNegotiationImage;
    
    private FermatTextView assetNegotiationDetailNameText;
    private FermatTextView assetNegotiationQuantity;
    private FermatTextView assetNegotiationUnitPrice;
    private FermatEditText assetNegotiationAssetsToBuy;
    private FermatTextView assetNegotiationDetailBtcText;
    private FermatTextView bitcoinBalanceText;
    private View acceptNegotiationButton;
    private View rejectNegotiationButton;
    private FermatTextView bitcoinsTotalText;

    private AssetNegotiation assetNegotiation;
    private DigitalAsset digitalAsset;
    private ErrorManager errorManager;

    SettingsManager<AssetUserSettings> settingsManager;

    private User user;

    private long bitcoinWalletBalanceSatoshis;

    public AssetNegotiationDetailFragment() {

    }

    public static AssetNegotiationDetailFragment newInstance() {
        return new AssetNegotiationDetailFragment();
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
        rootView = inflater.inflate(R.layout.dap_wallet_asset_user_asset_negotiation_detail, container, false);
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
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM, 0, "Help").setIcon(R.drawable.dap_asset_user_help_icon)
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

        assetNegotiationImage = (ImageView) rootView.findViewById(R.id.asset_image_negotiation_detail);
        assetNegotiationDetailNameText = (FermatTextView) rootView.findViewById(R.id.assetNegotiationDetailNameText);
        assetNegotiationQuantity = (FermatTextView) rootView.findViewById(R.id.assetNegotiationQuantity);
        assetNegotiationUnitPrice = (FermatTextView) rootView.findViewById(R.id.assetNegotiationUnitPrice);
        assetNegotiationAssetsToBuy = (FermatEditText) rootView.findViewById(R.id.assetNegotiationAssetsToBuy);
        assetNegotiationDetailBtcText = (FermatTextView) rootView.findViewById(R.id.assetNegotiationDetailBtcText);
        bitcoinBalanceText = (FermatTextView) rootView.findViewById(R.id.bitcoinBalanceText);

        acceptNegotiationButton = rootView.findViewById(R.id.acceptNegotiationButton);
        rejectNegotiationButton = rootView.findViewById(R.id.rejectNegotiationButton);

        assetNegotiationDetailBtcText.setText(String.format("%.6f BTC", 0.0));
        /*sellAssetsButton = rootView.findViewById(R.id.sellAssetsButton);
        bitcoins = (FermatEditText) rootView.findViewById(R.id.bitcoins);


        bitcoinsTotal = (FermatEditText) rootView.findViewById(R.id.bitcoinsTotal);
        bitcoinsTotalSpinner = (Spinner) rootView.findViewById(R.id.bitcoinsTotalSpinner);
        bitcoinsTotalText = (FermatTextView) rootView.findViewById(R.id.bitcoinsTotalText);*/


        /*bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
        final BitcoinConverter.Currency[] currenciesSpinner = BitcoinConverter.Currency.values();
        final ArrayAdapter<BitcoinConverter.Currency> bitcoinsSpinnerAdapter = new BitcoinsSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                currenciesSpinner);*/


        /*bitcoinsTotalText.setText(String.format("%.6f BTC", 0.0));
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
        });*/

//        layout = rootView.findViewById(R.id.assetDetailRemainingLayout);

        acceptNegotiationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: implementar logica de Buy
                if (isValidBuy()) {
                    new ConfirmDialog.Builder(getActivity(), appSession)
                            .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_asset_buy))
                            .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                @Override
                                public void onClick() {
                                    /*BitcoinConverter.Currency from = (BitcoinConverter.Currency) bitcoinsSpinner.getSelectedItem();
                                    BitcoinConverter.Currency fromTotal = (BitcoinConverter.Currency) bitcoinsTotalSpinner.getSelectedItem();
                                    long amountPerUnity = (long) BitcoinConverter.convert(Double.parseDouble(bitcoins.getText().toString()), from, SATOSHI);
                                    long amountTotal = (long) BitcoinConverter.convert(Double.parseDouble(bitcoinsTotal.getText().toString()), fromTotal, SATOSHI);
                                    int quantityToSell = Integer.parseInt(assetsToSellEditText.getText().toString());*/

                                    doBuy(digitalAsset.getUserAssetNegotiation().getNegotiationId());
                                }
                            }).build().show();
                }
            }
        });
        rejectNegotiationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDecline(digitalAsset);
            }
        });

    }

    private void doDecline(final DigitalAsset digitalAsset) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.declineAsset(digitalAsset.getUserAssetNegotiation().getNegotiationId());
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_sell_cancel), Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_MAIN_ACTIVITY, appSession.getAppPublicKey());
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

    private void setupUIData() {

        digitalAsset = (DigitalAsset) appSession.getData("asset_data");

//        toolbar.setTitle(digitalAsset.getName());

        byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(assetNegotiationImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        assetNegotiationDetailNameText.setText(digitalAsset.getName());
        assetNegotiationUnitPrice.setText(Long.toString( digitalAsset.getUserAssetNegotiation().getAmmountPerUnit()));

        //assetNegotiationQuantity.setText(digitalAsset.getUserAssetNegotiation().getQuantityToBuy());

        /* TODO:completar esta logica
        long quantity = digitalAsset.getAvailableBalanceQuantity();
        assetNegotiationQuantity.setText(quantity + ((quantity == 1) ? " Asset" : " Assets") + " Remaining");*/

        /*Object x = appSession.getData("user_selected");
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
        updateBitcoins();*/

        try {
            bitcoinWalletBalanceSatoshis = moduleManager.getBitcoinWalletBalance(Utils.getBitcoinWalletPublicKey(moduleManager));
            double bitcoinWalletBalance = BitcoinConverter.convert(bitcoinWalletBalanceSatoshis, SATOSHI, BITCOIN);
            bitcoinBalanceText.setText(DAPStandardFormats.BITCOIN_FORMAT.format(bitcoinWalletBalance));
        } catch (Exception e) {
            bitcoinBalanceText.setText(getResources().getString(R.string.dap_user_wallet_buy_no_available));
        }
    }

    private boolean isValidBuy() {
        /*String assetsToBuyStr = assetNegotiationAssetsToBuy.getText().toString();
        if (assetsToBuyStr.length() == 0) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_buy_quantity_zero),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        int quantity = Integer.parseInt(assetsToBuyStr);
        int totalAssets = Integer.parseInt(assetNegotiationQuantity.getText().toString());
        if (quantity == 0) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_buy_quantity_zero),
                    Toast.LENGTH_SHORT).show();
            return false;

        } else if (quantity > totalAssets) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_buy_quantity),
                    Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (digitalAsset.getUserAssetNegotiation().getAmmountPerUnit() > bitcoinWalletBalanceSatoshis) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_buy_available),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*private void updateBitcoins() {
        Object selectedItem = bitcoinsSpinner.getSelectedItem();
        String bitcoinStr = bitcoins.getText().toString();
        String assetsToBuyStr = assetsToSellEditText.getText().toString();
        if (selectedItem != null && bitcoinStr.length() > 0 && assetsToBuyStr.length() > 0) {
            BitcoinConverter.Currency from = (BitcoinConverter.Currency) selectedItem;
            double amount = Double.parseDouble(bitcoins.getText().toString());
            double amountBTC = BitcoinConverter.convert(amount, from, BITCOIN);
            int quantity = Integer.parseInt(assetsToBuyStr);
            bitcoinsTextView.setText(String.format("%.6f BTC", amountBTC));
            bitcoinsTotalSpinner.setSelection(bitcoinsSpinner.getSelectedItemPosition());
            bitcoinsTotal.setText(DAPStandardFormats.EDIT_NUMBER_FORMAT.format((amount * quantity)));
        } else if (bitcoinStr.length() == 0) {
            bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
            bitcoinsTotal.setText("");
        } else if (assetsToBuyStr.length() == 0) {
            bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
            bitcoinsTotal.setText("");
        }
        updateBitcoinsTotal();
    }*/

    /*private void updateBitcoinsTotal() {
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
    }*/

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

    private void doBuy(final UUID uuid) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                //moduleManager.startSell(user.getActorAssetUser(), amountPerUnity, amountTotal, quantityToSell, digitalAsset.getAssetPublicKey());
                moduleManager.acceptAsset(uuid);
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
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_MAIN_ACTIVITY, appSession.getAppPublicKey());
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
        assetNegotiationDetailNameText.setText(digitalAsset.getName());
        //assetsToDeliverEditText.setText(digitalAsset.getAvailableBalanceQuantity()+"");
        assetNegotiationQuantity.setText(digitalAsset.getAvailableBalanceQuantity() + " " );

        /*if (digitalAsset.getAvailableBalanceQuantity() == 0) {
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
