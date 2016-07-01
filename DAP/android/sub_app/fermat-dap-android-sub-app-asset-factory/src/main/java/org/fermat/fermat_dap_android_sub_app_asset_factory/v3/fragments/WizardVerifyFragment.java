package org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
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
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_factory.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPFeeType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/15/15.
 */
public class WizardVerifyFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetFactoryModuleManager>, ResourceProviderManager> {

    private Activity activity;
    private AssetFactoryModuleManager moduleManager;
    private ErrorManager errorManager;
    private static final String TAG = "Asset Factory";
    //UI
    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private FermatTextView wizardVerifyAssetNameText;
    private ImageView wizardVerifyAssetImage;
    private FermatTextView wizardVerifyDescText;
    private FermatTextView wizardVerifyFeeValue;
    private FermatTextView wizardVerifyExpDateValue;
    private FermatTextView wizardVerifyQuantityValue;
    private FermatCheckBox wizardVerifyRedeemableCheck;
    private FermatCheckBox wizardVerifyTransferableCheck;
    private FermatCheckBox wizardVerifyExchangeableCheck;
    private FermatTextView wizardVerifyAssetValue;
    private FermatTextView wizardVerifyTotalValue;
    private FermatButton wizardVerifyBackButton;
    private FermatButton wizardVerifyFinishButton;
    private FermatButton wizardVerifySaveButton;
    private View wizardVerifyButtons;
    private ImageButton wizardVerifyStep1Image;
    private ImageButton wizardVerifyStep2Image;
    private ImageButton wizardVerifyStep3Image;

    private AssetFactory asset;
    private boolean isEdit = false;

    public WizardVerifyFragment() {

    }

    public static WizardVerifyFragment newInstance() {
        return new WizardVerifyFragment();
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
        rootView = inflater.inflate(R.layout.dap_v3_factory_wizard_verify, container, false);
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
                    .setSubTitle(R.string.dap_asset_factory_wizard_verify_subTitle)
                    .setBody(R.string.dap_asset_factory_wizard_verify_body)
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
        wizardVerifyAssetNameText = (FermatTextView) rootView.findViewById(R.id.wizardVerifyAssetNameText);
        wizardVerifyAssetImage = (ImageView) rootView.findViewById(R.id.wizardVerifyAssetImage);
        wizardVerifyDescText = (FermatTextView) rootView.findViewById(R.id.wizardVerifyDescText);
        wizardVerifyFeeValue = (FermatTextView) rootView.findViewById(R.id.wizardVerifyFeeValue);
        wizardVerifyExpDateValue = (FermatTextView) rootView.findViewById(R.id.wizardVerifyExpDateValue);
        wizardVerifyQuantityValue = (FermatTextView) rootView.findViewById(R.id.wizardVerifyQuantityValue);
        wizardVerifyRedeemableCheck = (FermatCheckBox) rootView.findViewById(R.id.wizardVerifyRedeemableCheck);
        wizardVerifyTransferableCheck = (FermatCheckBox) rootView.findViewById(R.id.wizardVerifyTransfereableCheck);
        wizardVerifyExchangeableCheck = (FermatCheckBox) rootView.findViewById(R.id.wizardVerifyExchangeableCheck);
        wizardVerifyAssetValue = (FermatTextView) rootView.findViewById(R.id.wizardVerifyAssetValue);
        wizardVerifyTotalValue = (FermatTextView) rootView.findViewById(R.id.wizardVerifyTotalValue);
        wizardVerifyBackButton = (FermatButton) rootView.findViewById(R.id.wizardVerifyBackButton);
        wizardVerifyFinishButton = (FermatButton) rootView.findViewById(R.id.wizardVerifyFinishButton);
        wizardVerifySaveButton = (FermatButton) rootView.findViewById(R.id.wizardVerifySaveButton);
        wizardVerifyButtons = rootView.findViewById(R.id.wizardVerifyButtons);
        wizardVerifyStep1Image = (ImageButton) rootView.findViewById(R.id.wizardVerifyStep1Image);
        wizardVerifyStep2Image = (ImageButton) rootView.findViewById(R.id.wizardVerifyStep2Image);
        wizardVerifyStep3Image = (ImageButton) rootView.findViewById(R.id.wizardVerifyStep3Image);
        wizardVerifyBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardVerifyFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(asset)) {
                    doFinish();
                    Toast.makeText(getActivity(), String.format("Asset %s has been created", asset.getName()), Toast.LENGTH_LONG).show();
                }
            }
        });

        wizardVerifySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(asset)) {
                    doFinish();
                    changeActivity(Activities.DAP_MAIN.getCode(), appSession.getAppPublicKey());
                    Toast.makeText(getActivity(), String.format("Asset %s has been edited", asset.getName()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        wizardVerifyStep1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardVerifyStep2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardVerifyStep3Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getCode(), appSession.getAppPublicKey());
            }
        });
    }

    private boolean isValid(AssetFactory asset) {
        boolean isValidDate = asset.getExpirationDate() == null ? true : asset.getExpirationDate().after(new Date());
        long amountSatoshi = asset.getAmount();

        if (asset.getName() != null && asset.getName().trim().length() > 0 &&
                asset.getDescription() != null && asset.getDescription().trim().length() > 0
                && asset.getQuantity() > 0 && asset.getAmount() >= BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND
                && isValidDate) {
            return true;

        } else if (asset.getName() == null || asset.getName().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getDescription() == null || asset.getDescription().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_description), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getQuantity() == 0) {
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

    private void doFinish() {
        if (asset != null) {
            if (asset.getFactoryId() == null) {
                asset.setFactoryId(UUID.randomUUID().toString());
            }
            asset.setTotalQuantity(asset.getQuantity());
            asset.setIsRedeemable(wizardVerifyRedeemableCheck.isChecked());
            asset.setIsTransferable(wizardVerifyTransferableCheck.isChecked());
            asset.setIsExchangeable(wizardVerifyExchangeableCheck.isChecked());
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
//                    if (!isEdit) {
//                        Toast.makeText(getActivity(), String.format("Asset %s has been created", asset.getName()), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), String.format("Asset %s has been saved", asset.getName()), Toast.LENGTH_SHORT).show();
//                    }
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

    private void setupUIData() {
        if (appSession.getData("asset_factory") != null) {
            asset = (AssetFactory) appSession.getData("asset_factory");
            loadVerify();
        }

        if (asset != null) {
            wizardVerifySaveButton.setVisibility((asset.getFactoryId() != null) ? View.VISIBLE : View.INVISIBLE);
            wizardVerifyButtons.setVisibility((asset.getFactoryId() != null) ? View.INVISIBLE : View.VISIBLE);
        }
    }

    private void loadVerify() {
        if (asset.getName() != null && asset.getName().length() > 0) {
            wizardVerifyAssetNameText.setText(asset.getName());
        } else {
            wizardVerifyAssetNameText.setText("");
        }
        if (asset.getDescription() != null && asset.getDescription().length() > 0) {
            wizardVerifyDescText.setText(asset.getDescription());
        } else {
            wizardVerifyDescText.setText("Description must be set");
        }
        if (asset.getResources() != null && !asset.getResources().isEmpty()) {
            if (asset.getResources().get(0) != null && asset.getResources().get(0).getResourceBinayData() != null) {
                byte[] bitmapBytes = asset.getResources().get(0).getResourceBinayData();
                BitmapDrawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length));
                if (wizardVerifyAssetImage != null)
                    wizardVerifyAssetImage.setImageDrawable(drawable);
            }
        }
        wizardVerifyFeeValue.setText("");
        if (asset.getFee() > 0) {
            try {
                wizardVerifyFeeValue.setText(DAPFeeType.findByFeeValue(asset.getFee()).getDescription());
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }
        if (asset.getExpirationDate() != null) {
            wizardVerifyExpDateValue.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(asset.getExpirationDate().getTime())));
        } else {
            wizardVerifyExpDateValue.setText("No Exp date");
        }
        if (asset.getQuantity() > 0) {
            wizardVerifyQuantityValue.setText(Integer.toString(asset.getQuantity()) + ((asset.getQuantity() == 1) ? " asset" : " assets"));
        } else {
            wizardVerifyQuantityValue.setText("Quantity must be set");
        }
        List<ContractProperty> properties = asset.getContractProperties();
        wizardVerifyRedeemableCheck.setChecked(false);
        wizardVerifyTransferableCheck.setChecked(false);
        wizardVerifyExchangeableCheck.setChecked(false);
        if (properties != null && properties.size() > 0) {
            for (ContractProperty property : properties) {
                if (property.getName().equals(DigitalAssetContractPropertiesConstants.REDEEMABLE)) {
                    wizardVerifyRedeemableCheck.setChecked(((Boolean) property.getValue()).booleanValue());
                }
                if (property.getName().equals(DigitalAssetContractPropertiesConstants.TRANSFERABLE)) {
                    wizardVerifyTransferableCheck.setChecked(((Boolean) property.getValue()).booleanValue());
                }
                if (property.getName().equals(DigitalAssetContractPropertiesConstants.SALEABLE)) {
                    wizardVerifyExchangeableCheck.setChecked(((Boolean) property.getValue()).booleanValue());
                }
            }
        }
        if (asset.getAmount() > 0) {
            double amount = BitcoinConverter.convert(asset.getAmount(), SATOSHI, BITCOIN);
            wizardVerifyAssetValue.setText(String.format("%.6f BTC", amount));
        } else {
            wizardVerifyAssetValue.setText(String.format("%.6f BTC", 0.0));
        }
        if (asset.getAmount() > 0 && asset.getQuantity() > 0 && asset.getFee() > 0) {
            double amount = BitcoinConverter.convert(asset.getAmount(), SATOSHI, BITCOIN);
            double total = amount * asset.getQuantity();
            wizardVerifyTotalValue.setText(String.format("%.6f BTC", total));
        } else {
            wizardVerifyTotalValue.setText(String.format("%.6f BTC", 0.0));
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
