package org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_factory.util.CommonLogger;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.Utils;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.adapters.AssetValueSpinnerAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_factory.v3.adapters.FeeSpinnerAdapter;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPFeeType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/15/15.
 */
public class WizardCryptoFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetFactoryModuleManager>, ResourceProviderManager> {

    private Activity activity;
    private AssetFactoryModuleManager moduleManager;
    private ErrorManager errorManager;
    private static final String TAG = "Asset Factory";

    //UI
    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private FermatEditText wizardCryptoValueEditText;
    private Spinner wizardCryptoValueSpinner;
    private Spinner wizardCryptoFeeSpinner;
    private FermatEditText wizardCryptoQuantityEditText;
    private FermatTextView wizardCryptoTotalValue;
    private FermatTextView wizardCryptoBalanceValue;
    private FermatButton wizardCryptoBackButton;
    private FermatButton wizardCryptoNextButton;
    private FermatButton wizardCryptoSaveButton;
    private View wizardCryptoButtons;
    private ImageButton wizardCryptoStep1Image;
    private ImageButton wizardCryptoStep2Image;
    private ImageButton wizardCryptoStep4Image;

    private AssetFactory asset;

    public WizardCryptoFragment() {

    }

    public static WizardCryptoFragment newInstance() {
        return new WizardCryptoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        activity = getActivity();

//        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_v3_factory_wizard_crypto, container, false);
        res = rootView.getResources();

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setUpHelpAssetStatistics(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_factory)
                    .setIconRes(R.drawable.asset_factory)
                    .setImageLeft(R.drawable.profile_actor)
                    .setVIewColor(R.color.dap_asset_factory_view_color)
                    .setTitleTextColor(R.color.dap_asset_factory_view_color)
                    .setTextNameLeft(R.string.dap_asset_factory_welcome_name_left)
                    .setSubTitle(R.string.dap_asset_factory_wizard_crypto_subTitle)
                    .setBody(R.string.dap_asset_factory_wizard_crypto_body)
                    .setTextFooter(R.string.dap_asset_factory_welcome_Footer)
                    .setTemplateType((moduleManager.getLoggedIdentityAssetIssuer() == null) ? PresentationDialog.TemplateType.TYPE_PRESENTATION_WITH_ONE_IDENTITY : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
//        menu.add(0, SessionConstantsAssetFactory.IC_ACTION_HELP_FACTORY, 0, "Help")
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                //case IC_ACTION_HELP_FACTORY:
                case 2:
                    setUpHelpAssetStatistics(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
//                case 1:
//                    changeActivity(Activities.CHT_CHAT_GEOLOCATION_IDENTITY, appSession.getAppPublicKey());
//                    break;
            }
//            if (id == SessionConstantsAssetFactory.IC_ACTION_HELP_FACTORY) {
//                setUpHelpAssetStatistics(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_asset_factory_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        wizardCryptoValueEditText = (FermatEditText) rootView.findViewById(R.id.wizardCryptoValueEditText);
        wizardCryptoValueSpinner = (Spinner) rootView.findViewById(R.id.wizardCryptoValueSpinner);
        wizardCryptoFeeSpinner = (Spinner) rootView.findViewById(R.id.wizardCryptoFeeSpinner);
        wizardCryptoQuantityEditText = (FermatEditText) rootView.findViewById(R.id.wizardCryptoQuantityEditText);
        wizardCryptoTotalValue = (FermatTextView) rootView.findViewById(R.id.wizardCryptoTotalValue);
        wizardCryptoBalanceValue = (FermatTextView) rootView.findViewById(R.id.wizardCryptoBalanceValue);
        wizardCryptoBackButton = (FermatButton) rootView.findViewById(R.id.wizardCryptoBackButton);
        wizardCryptoNextButton = (FermatButton) rootView.findViewById(R.id.wizardCryptoNextButton);
        wizardCryptoSaveButton = (FermatButton) rootView.findViewById(R.id.wizardCryptoSaveButton);
        wizardCryptoButtons = rootView.findViewById(R.id.wizardCryptoButtons);
        wizardCryptoStep1Image = (ImageButton) rootView.findViewById(R.id.wizardCryptoStep1Image);
        wizardCryptoStep2Image = (ImageButton) rootView.findViewById(R.id.wizardCryptoStep2Image);
        wizardCryptoStep4Image = (ImageButton) rootView.findViewById(R.id.wizardCryptoStep4Image);
        wizardCryptoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCrypto();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardCryptoNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCrypto();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardCryptoSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(asset)) {
                    saveCrypto();
                    doFinish();
                    changeActivity(Activities.DAP_MAIN.getCode(), appSession.getAppPublicKey());
                    Toast.makeText(getActivity(), String.format("Asset %s has been edited", asset.getName()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        wizardCryptoStep1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCrypto();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardCryptoStep2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCrypto();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardCryptoStep4Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCrypto();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY.getCode(), appSession.getAppPublicKey());
            }
        });
    }

    private boolean isValid(AssetFactory asset) {
        boolean isValidDate = asset.getExpirationDate() == null ? true : asset.getExpirationDate().after(new Date());
        long amountSatoshi;
        try {
            double amount = DAPStandardFormats.BITCOIN_FORMAT.parse(wizardCryptoValueEditText.getText().toString()).doubleValue();
            BitcoinConverter.Currency from = (BitcoinConverter.Currency) wizardCryptoValueSpinner.getSelectedItem();
            amountSatoshi = ((Double) BitcoinConverter.convert(amount, from, SATOSHI)).longValue();
        } catch (ParseException e) {
            Toast.makeText(getActivity(), "Can't parse the value", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (asset.getName() != null && asset.getName().trim().length() > 0 &&
                asset.getDescription() != null && asset.getDescription().trim().length() > 0
                && Integer.parseInt(wizardCryptoQuantityEditText.getText().toString()) > 0
                && amountSatoshi >= BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND
                && isValidDate) {
            return true;

        } else if (asset.getName() == null || asset.getName().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getDescription() == null || asset.getDescription().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_description), Toast.LENGTH_SHORT).show();
            return false;
        } else if (Integer.parseInt(wizardCryptoQuantityEditText.getText().toString()) == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_quantity), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getExpirationDate() != null && asset.getExpirationDate().before(new Date())) {
            Toast.makeText(getActivity(), "Expiration date can't be in the past. Please modify the expiration date.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(getActivity(), "The minimum monetary amount for any Asset is " + BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND + " satoshis.\n" +
                    " \n This is needed to pay the fee of bitcoin transactions during delivery of the assets.\n " + "\n You selected " + amountSatoshi + " satoshis.\n", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validate() {
        return true;
    }

    private void doFinish() {
        if (asset != null) {
            if (asset.getFactoryId() == null) {
                asset.setFactoryId(UUID.randomUUID().toString());
            }
            asset.setTotalQuantity(asset.getQuantity());
            asset.setIsRedeemable(asset.getIsRedeemable());
            asset.setIsTransferable(asset.getIsTransferable());
            asset.setIsExchangeable(asset.getIsExchangeable());
            asset.setState(State.DRAFT);
            asset.setAssetBehavior(AssetBehavior.REGENERATION_ASSET);
            asset.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            saveAssetFactoryFinish();
        }
    }

    private void saveAssetFactoryFinish() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Saving asset");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.saveAssetFactory(asset);
                return true;
            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (getActivity() != null) {
//                    Toast.makeText(getActivity(), String.format("Asset %s has been saved", asset.getName()), Toast.LENGTH_SHORT).show();
                    appSession.setData("asset_factory", null);
                    changeActivity(Activities.DAP_MAIN.getCode(), appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (getActivity() != null) {
                    CommonLogger.exception(TAG, ex.getMessage(), ex);
                    Toast.makeText(getActivity(), "There was an error creating this asset", Toast.LENGTH_SHORT).show();
                }
            }
        });
        worker.execute();
    }

    private void saveCrypto() {
        if (asset != null) {
            try {
                if (wizardCryptoValueEditText.getText().toString().length() > 0) {
                    double amount = DAPStandardFormats.BITCOIN_FORMAT.parse(wizardCryptoValueEditText.getText().toString()).doubleValue();
                    BitcoinConverter.Currency from = (BitcoinConverter.Currency) wizardCryptoValueSpinner.getSelectedItem();
                    long amountSatoshi = ((Double) BitcoinConverter.convert(amount, from, SATOSHI)).longValue();
                    asset.setAmount(amountSatoshi);
                } else {

                    long amount = (long) BitcoinConverter.convert(0, SATOSHI, BITCOIN);
                    asset.setAmount(amount);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            asset.setFee(((DAPFeeType) wizardCryptoFeeSpinner.getSelectedItem()).getFeeValue());
            if (wizardCryptoQuantityEditText.getText().toString().length() > 0) {
                asset.setQuantity(Integer.valueOf(wizardCryptoQuantityEditText.getText().toString()));
            }
        }
    }

    private void setupUIData() {
        final BitcoinConverter.Currency[] data = BitcoinConverter.Currency.values();
        final ArrayAdapter<BitcoinConverter.Currency> spinnerAdapter = new AssetValueSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                data);
        wizardCryptoValueSpinner.setAdapter(spinnerAdapter);
        wizardCryptoValueSpinner.setSelection(0);
        wizardCryptoValueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateBitcoins();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        wizardCryptoValueEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                updateBitcoins();
                return false;
            }
        });
        wizardCryptoTotalValue.setText(String.format("%.6f BTC", 0.0));
        final DAPFeeType[] feeTypes = DAPFeeType.values();
        final ArrayAdapter<DAPFeeType> spinnerAdapterFee = new FeeSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                feeTypes);
        wizardCryptoFeeSpinner.setAdapter(spinnerAdapterFee);
        wizardCryptoFeeSpinner.setSelection(1);
        wizardCryptoFeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateBitcoins();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        wizardCryptoQuantityEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                updateBitcoins();
                return false;
            }
        });

        try {
            long satoshis = moduleManager.getBitcoinWalletBalance(Utils.getBitcoinWalletPublicKey(moduleManager));
            double bitcoinWalletBalance = BitcoinConverter.convert(satoshis, SATOSHI, BITCOIN);
            wizardCryptoBalanceValue.setText(String.format("%.6f BTC", bitcoinWalletBalance));
        } catch (Exception e) {
            wizardCryptoBalanceValue.setText(getResources().getString(R.string.dap_asset_factory_no_available));
        }

        if (appSession.getData("asset_factory") != null) {
            asset = (AssetFactory) appSession.getData("asset_factory");
            loadCrypto(spinnerAdapterFee);
        }

        if (asset != null) {
            wizardCryptoSaveButton.setVisibility((asset.getFactoryId() != null) ? View.VISIBLE : View.INVISIBLE);
            wizardCryptoButtons.setVisibility((asset.getFactoryId() != null) ? View.INVISIBLE : View.VISIBLE);
        }
    }

    private void loadCrypto(ArrayAdapter<DAPFeeType> spinnerAdapterFee) {
        if (asset.getAmount() > 0) {
            try {
                double amount = DAPStandardFormats.BITCOIN_FORMAT.parse(Long.toString(asset.getAmount())).doubleValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            BitcoinConverter.Currency to = (BitcoinConverter.Currency) wizardCryptoValueSpinner.getSelectedItem();
            double amountToLoad = BitcoinConverter.convert(asset.getAmount(), SATOSHI, to);
            String amountToLoadStr;
            if (to.equals(SATOSHI)) {
                amountToLoadStr = Long.toString(Double.valueOf(amountToLoad).longValue());
            } else {
                amountToLoadStr = DAPStandardFormats.BITCOIN_FORMAT.format(amountToLoad);
            }
            wizardCryptoValueEditText.setText(amountToLoadStr);
        } else {
            wizardCryptoValueEditText.setText("0");
        }

        if (asset.getFee() > 0) {
            try {
                int pos = spinnerAdapterFee.getPosition(DAPFeeType.findByFeeValue(asset.getFee()));
                wizardCryptoFeeSpinner.setSelection(pos);
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }
        if (asset.getQuantity() > 0) {
            wizardCryptoQuantityEditText.setText(Integer.toString(asset.getQuantity()));
        } else {
            wizardCryptoQuantityEditText.setText("0");
        }
        updateBitcoins();
    }

    private void updateBitcoins() {
        Object selectedItem = wizardCryptoValueSpinner.getSelectedItem();
        String bitcoinViewStr = wizardCryptoValueEditText.getText().toString();
        if (selectedItem != null && bitcoinViewStr != null && bitcoinViewStr.length() > 0) {
            BitcoinConverter.Currency from = (BitcoinConverter.Currency) wizardCryptoValueSpinner.getSelectedItem();
            double amount = Double.parseDouble(wizardCryptoValueEditText.getText().toString());
            double amountBTC = BitcoinConverter.convert(amount, from, BITCOIN);
            String qstr = wizardCryptoQuantityEditText.getText().toString();
            int quantity = (qstr != null && qstr.length() > 0) ? Integer.parseInt(qstr) : 0;
            double total = quantity * amountBTC;
            wizardCryptoTotalValue.setText(String.format("%.6f BTC", total));
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.redeem_home_bar_color));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.redeem_home_bar_color));
            }
        }
    }
}
