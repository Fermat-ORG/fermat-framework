package org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_factory.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class WizardPropertiesFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetFactoryModuleManager>, ResourceProviderManager> {

    Date date = null;
    private Activity activity;
    private AssetFactoryModuleManager moduleManager;
    private ErrorManager errorManager;
    private static final String TAG = "Asset Factory";

    //UI
    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private FermatEditText wizardPropertiesAssetNameEditText;
    private FermatEditText wizardPropertiesAssetDescEditText;
    private FermatCheckBox wizardPropertiesRedeemableCheck;
    private FermatCheckBox wizardPropertiesTransfereableCheck;
    private FermatCheckBox wizardPropertiesExchangeableCheck;
    private FermatTextView wizardPropertiesAssetNameEditCountText;
    private FermatTextView wizardPropertiesAssetDescEditCountText;
    private FermatEditText wizardPropertiesExpDateEditText;
    private ImageButton wizardPropertiesDateButton;
    private FermatButton wizardPropertiesBackButton;
    private FermatButton wizardPropertiesNextButton;
    private View wizardPropertiesButtons;
    private FermatButton wizardPropertiesSaveButton;
    private ImageButton wizardPropertiesStep1Image;
    private ImageButton wizardPropertiesStep3Image;
    private ImageButton wizardPropertiesStep4Image;
    private ImageButton wizardPropertiesEraseDateButton;

    private AssetFactory asset;
    private Calendar expirationTimeCalendar;

    public WizardPropertiesFragment() {

    }

    public static WizardPropertiesFragment newInstance() {
        return new WizardPropertiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        activity = getActivity();

        expirationTimeCalendar = Calendar.getInstance();
        expirationTimeCalendar.setTime(new Date());

//        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_v3_factory_wizard_properties, container, false);
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
                    .setSubTitle(R.string.dap_asset_factory_wizard_properties_subTitle)
                    .setBody(R.string.dap_asset_factory_wizard_properties_body)
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
        wizardPropertiesAssetNameEditText = (FermatEditText) rootView.findViewById(R.id.wizardCryptoValueEditText);
        wizardPropertiesAssetDescEditText = (FermatEditText) rootView.findViewById(R.id.wizardPropertiesAssetDescEditText);
        wizardPropertiesAssetNameEditCountText = (FermatTextView) rootView.findViewById(R.id.wizardCryptoValueEditCountText);
        wizardPropertiesAssetDescEditCountText = (FermatTextView) rootView.findViewById(R.id.wizardPropertiesAssetDescEditCountText);
        wizardPropertiesRedeemableCheck = (FermatCheckBox) rootView.findViewById(R.id.wizardVerifyRedeemableCheck);
        wizardPropertiesTransfereableCheck = (FermatCheckBox) rootView.findViewById(R.id.wizardVerifyTransfereableCheck);
        wizardPropertiesExchangeableCheck = (FermatCheckBox) rootView.findViewById(R.id.wizardVerifyExchangeableCheck);
        wizardPropertiesExpDateEditText = (FermatEditText) rootView.findViewById(R.id.wizardPropertiesQuantityEditText);
        wizardPropertiesDateButton = (ImageButton) rootView.findViewById(R.id.wizardPropertiesDateButton);
        wizardPropertiesBackButton = (FermatButton) rootView.findViewById(R.id.wizardVerifyBackButton);
        wizardPropertiesNextButton = (FermatButton) rootView.findViewById(R.id.wizardVerifyFinishButton);
        wizardPropertiesButtons = rootView.findViewById(R.id.wizardPropertiesButtons);
        wizardPropertiesSaveButton = (FermatButton) rootView.findViewById(R.id.wizardPropertiesSaveButton);
        wizardPropertiesStep1Image = (ImageButton) rootView.findViewById(R.id.wizardPropertiesStep1Image);
        wizardPropertiesStep3Image = (ImageButton) rootView.findViewById(R.id.wizardPropertiesStep3Image);
        wizardPropertiesStep4Image = (ImageButton) rootView.findViewById(R.id.wizardPropertiesStep4Image);
        wizardPropertiesEraseDateButton = (ImageButton) rootView.findViewById(R.id.wizardPropertiesEraseDateButton);

        wizardPropertiesEraseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wizardPropertiesExpDateEditText.getText().toString().equals("")) {
                    wizardPropertiesExpDateEditText.setText("");
                    wizardPropertiesEraseDateButton.setVisibility(View.GONE);
                }

            }
        });

        wizardPropertiesDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        expirationTimeCalendar.set(Calendar.YEAR, year);
                        expirationTimeCalendar.set(Calendar.MONTH, month);
                        expirationTimeCalendar.set(Calendar.DAY_OF_MONTH, day);
                        wizardPropertiesExpDateEditText.setText(DAPStandardFormats.DATE_FORMAT.format(expirationTimeCalendar.getTime()));
                        wizardPropertiesEraseDateButton.setVisibility(View.VISIBLE);
                    }
                }, expirationTimeCalendar.get(Calendar.YEAR), expirationTimeCalendar.get(Calendar.MONTH), expirationTimeCalendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
                CommonLogger.debug("DatePickerDialog", "Showing DatePickerDialog...");
            }
        });
        wizardPropertiesBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProperties();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardPropertiesNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProperties();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardPropertiesSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(asset)) {
                    saveProperties();
                    doFinish();
                    changeActivity(Activities.DAP_MAIN.getCode(), appSession.getAppPublicKey());
                    Toast.makeText(getActivity(), String.format("Asset %s has been edited", asset.getName()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        wizardPropertiesStep1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProperties();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardPropertiesStep3Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProperties();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getCode(), appSession.getAppPublicKey());
            }
        });
        wizardPropertiesStep4Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProperties();
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY.getCode(), appSession.getAppPublicKey());
            }
        });

        wizardPropertiesAssetNameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String count = Integer.toString(wizardPropertiesAssetNameEditText.getText().toString().length());
                wizardPropertiesAssetNameEditCountText.setText(count + "/24");
                return false;
            }
        });
        wizardPropertiesAssetDescEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String count = Integer.toString(wizardPropertiesAssetDescEditText.getText().toString().length());
                wizardPropertiesAssetDescEditCountText.setText(count + "/160");
                return false;
            }
        });

    }

    private boolean validate() {
        return true;
    }

    private boolean isValid(AssetFactory asset) {
        Date newExpDate = null;
        long amountSatoshi = asset.getAmount();
        try {
            if (!wizardPropertiesExpDateEditText.getText().toString().equals("")) {
                newExpDate = new Timestamp(DAPStandardFormats.DATE_FORMAT.parse(wizardPropertiesExpDateEditText.getText().toString()).getTime());
            }
        } catch (ParseException e) {
            Toast.makeText(getActivity(), "Invalid Date Format", Toast.LENGTH_SHORT).show();
        }
        boolean isValidDate = newExpDate == null ? true : newExpDate.after(new Date());

        if (wizardPropertiesAssetNameEditText.getText().toString().trim().length() > 0 &&
                wizardPropertiesAssetDescEditText.getText().toString().trim().length() > 0 &&
                asset.getQuantity() > 0 && asset.getAmount() >= BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND &&
                isValidDate) {
            return true;

        } else if (wizardPropertiesAssetNameEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (wizardPropertiesAssetDescEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_description), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getQuantity() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_quantity), Toast.LENGTH_SHORT).show();
            return false;
        } else if (newExpDate != null && newExpDate.before(new Date())) {
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

    private void saveProperties() {
        if (asset != null) {
            if (wizardPropertiesAssetNameEditText.getText().toString().trim().length() > 0) {
                asset.setName(wizardPropertiesAssetNameEditText.getText().toString().trim());
            }
            if (wizardPropertiesAssetDescEditText.getText().toString().trim().length() > 0) {
                asset.setDescription(wizardPropertiesAssetDescEditText.getText().toString().trim());
            }

            try {
                if (wizardPropertiesExpDateEditText.getText().toString().length() > 0 && !wizardPropertiesExpDateEditText.getText().toString().equals("")) {
                    asset.setExpirationDate(new Timestamp(DAPStandardFormats.DATE_FORMAT.parse(wizardPropertiesExpDateEditText.getText().toString()).getTime()));
                } else {
                    asset.setExpirationDate(null);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<ContractProperty> contractProperties = new ArrayList<>();
            contractProperties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, wizardPropertiesRedeemableCheck.isChecked()));
            contractProperties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.TRANSFERABLE, wizardPropertiesTransfereableCheck.isChecked()));
            contractProperties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.SALEABLE, wizardPropertiesExchangeableCheck.isChecked()));
            contractProperties.add(new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, asset.getExpirationDate()));
            asset.setContractProperties(contractProperties);
        }
    }

    private void setupUIData() {
        if (appSession.getData("asset_factory") != null) {
            asset = (AssetFactory) appSession.getData("asset_factory");
            loadProperties();
        }

        if (asset != null) {
            wizardPropertiesSaveButton.setVisibility((asset.getFactoryId() != null) ? View.VISIBLE : View.INVISIBLE);
            wizardPropertiesButtons.setVisibility((asset.getFactoryId() != null) ? View.INVISIBLE : View.VISIBLE);
        }

        wizardPropertiesEraseDateButton.setVisibility(
                (wizardPropertiesExpDateEditText.getText().toString().equals("")) ? View.GONE : View.VISIBLE);
    }

    private void loadProperties() {
        if (asset.getName() != null && asset.getName().length() > 0) {
            wizardPropertiesAssetNameEditText.setText(asset.getName());
            String count = Integer.toString(wizardPropertiesAssetNameEditText.getText().toString().length());
            wizardPropertiesAssetNameEditCountText.setText(count + "/24");
        } else {
            wizardPropertiesAssetNameEditText.setText("");
        }
        if (asset.getDescription() != null && asset.getDescription().length() > 0) {
            wizardPropertiesAssetDescEditText.setText(asset.getDescription());
            String count = Integer.toString(wizardPropertiesAssetDescEditText.getText().toString().length());
            wizardPropertiesAssetDescEditCountText.setText(count + "/160");
        } else {
            wizardPropertiesAssetDescEditText.setText("");
        }
        List<ContractProperty> properties = asset.getContractProperties();
        wizardPropertiesRedeemableCheck.setChecked(false);
        wizardPropertiesTransfereableCheck.setChecked(false);
        wizardPropertiesExchangeableCheck.setChecked(false);
        if (properties != null && properties.size() > 0) {
            for (ContractProperty property : properties) {
                if (property.getName().equals(DigitalAssetContractPropertiesConstants.REDEEMABLE)) {
                    wizardPropertiesRedeemableCheck.setChecked(((Boolean) property.getValue()).booleanValue());
                }
                if (property.getName().equals(DigitalAssetContractPropertiesConstants.TRANSFERABLE)) {
                    wizardPropertiesTransfereableCheck.setChecked(((Boolean) property.getValue()).booleanValue());
                }
                if (property.getName().equals(DigitalAssetContractPropertiesConstants.SALEABLE)) {
                    wizardPropertiesExchangeableCheck.setChecked(((Boolean) property.getValue()).booleanValue());
                }
            }
        }
        if (asset.getExpirationDate() != null) {
            wizardPropertiesExpDateEditText.setText(DAPStandardFormats.DATE_FORMAT.format(new Date(asset.getExpirationDate().getTime())));
        } else {
            wizardPropertiesExpDateEditText.setText("");
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
