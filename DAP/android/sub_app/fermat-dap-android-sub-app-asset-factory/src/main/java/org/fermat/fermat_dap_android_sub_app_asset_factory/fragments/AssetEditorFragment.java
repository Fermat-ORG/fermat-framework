package org.fermat.fermat_dap_android_sub_app_asset_factory.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
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
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import org.fermat.fermat_dap_android_sub_app_asset_factory.adapters.BitcoinsSpinnerAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.AssetFactorySession;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.SessionConstantsAssetFactory;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.CommonLogger;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.Utils;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Asset Editor Fragment
 *
 * @author Francisco Vasquez
 */
public class AssetEditorFragment extends AbstractFermatFragment implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    private static final String NO_AVAILABLE = "No Available";
    private final String TAG = "AssetEditor";
    private AssetFactoryModuleManager moduleManager;
    AssetFactorySession assetFactorySession;
    private ErrorManager errorManager;
    private AssetFactory asset;


    private View rootView;
    private FermatEditText nameView;
    private FermatEditText descriptionView;
    private FermatEditText quantityView;
    private FermatEditText bitcoinsView;
    private Spinner bitcoinsSpinner;
    private FermatTextView bitcoinsTextView;
    private FermatTextView bitcoinBalanceText;
    private FermatButton expirationDate;
    private FermatButton expirationTime;
    private FermatCheckBox isRedeemableView;
    private LinearLayout hasExpirationDate;

    private ImageView takePicture;

    private Calendar expirationTimeCalendar;
    private DateFormat dateFormat = DAPStandardFormats.DATE_FORMAT;
    private DateFormat timeFormat = DAPStandardFormats.TIME_FORMAT;
    private SimpleDateFormat dateTimeFormat = DAPStandardFormats.SIMPLE_DATETIME_FORMAT;
    private boolean isEdit;
    private boolean hasResource;

    private long satoshisWalletBalance = 0;
    private boolean contextMenuInUse = false;

//    SettingsManager<AssetFactorySettings> settingsManager;

    public static AssetEditorFragment newInstance(AssetFactory asset) {
        AssetEditorFragment fragment = new AssetEditorFragment();
        fragment.setAsset(asset);
        fragment.setIsEdit(asset != null);
        fragment.expirationTimeCalendar = Calendar.getInstance();
        fragment.expirationTimeCalendar.setTime(new Date());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            assetFactorySession = ((AssetFactorySession) appSession);
            moduleManager = assetFactorySession.getModuleManager();
            errorManager = appSession.getErrorManager();

            if (!isEdit) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Draft Asset");
                dialog.setMessage("Creating new empty asset project, please wait...");
                dialog.setCancelable(false);
                dialog.show();
                FermatWorker worker = new FermatWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        asset = moduleManager.newAssetFactoryEmpty();
                        List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();
                        if (installedWallets != null && installedWallets.size() > 0) {
                            asset.setWalletPublicKey(Utils.getBitcoinWalletPublicKey(moduleManager));
                        }
                        return true;
                    }
                };
                worker.setContext(getActivity());
                worker.setCallBack(new FermatWorkerCallBack() {
                    @Override
                    public void onPostExecute(Object... result) {
                        dialog.dismiss();
                        // do nothing... continue with the form data
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Some error occurred while creating a new asset empty project", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                });
                worker.execute();
            }
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.asset_editor_fragment, container, false);
        configureToolbar();
        rootView.findViewById(R.id.action_create).setOnClickListener(this);

        ((FermatButton) rootView.findViewById(R.id.action_create)).setText(isEdit ? "Done" : "Create");

        nameView = (FermatEditText) rootView.findViewById(R.id.name);
        descriptionView = (FermatEditText) rootView.findViewById(R.id.description);
        quantityView = (FermatEditText) rootView.findViewById(R.id.quantity);
        bitcoinsView = (FermatEditText) rootView.findViewById(R.id.bitcoins);
        expirationDate = (FermatButton) rootView.findViewById(R.id.expiration_date);
        expirationTime = (FermatButton) rootView.findViewById(R.id.expiration_time);
        isRedeemableView = (FermatCheckBox) rootView.findViewById(R.id.isRedeemable);
        hasExpirationDate = (LinearLayout) rootView.findViewById(R.id.hasExpiration);
        takePicture = (ImageView) rootView.findViewById(R.id.picture);
        bitcoinBalanceText = (FermatTextView) rootView.findViewById(R.id.bitcoinBalanceText);
        bitcoinsSpinner = (Spinner) rootView.findViewById(R.id.bitcoinsSpinner);
        bitcoinsTextView = (FermatTextView) rootView.findViewById(R.id.bitcoinsText);

        final Currency[] data = Currency.values();
        final ArrayAdapter<Currency> spinnerAdapter = new BitcoinsSpinnerAdapter(
                getActivity(), android.R.layout.simple_spinner_item,
                data);
        bitcoinsSpinner.setAdapter(spinnerAdapter);
        bitcoinsSpinner.setSelection(0);
        bitcoinsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateBitcoins();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bitcoinsView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                updateBitcoins();
                return false;
            }
        });
        bitcoinsTextView.setText(String.format("%.6f BTC", 0.0));
        registerForContextMenu(takePicture);

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().openContextMenu(view);
            }
        });

        hasExpirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasExpirationDate.setActivated(!hasExpirationDate.isActivated());
            }
        });

        nameView.setText(isEdit ? asset.getName() != null ? asset.getName() : "" : "");
        descriptionView.setText(isEdit ? asset.getDescription() != null ? asset.getDescription() : "" : "");
        quantityView.setText(isEdit ? String.valueOf(asset.getQuantity()) : "");
        bitcoinsView.setText(isEdit ? String.valueOf(BitcoinConverter.convert(asset.getAmount(), SATOSHI, BITCOIN)) : "");

        if (isEdit)
            isRedeemableView.setChecked(asset.getIsRedeemable());

        expirationDate.setText(dateFormat.format(expirationTimeCalendar.getTime()));
        expirationTime.setText(timeFormat.format(expirationTimeCalendar.getTime()));
        expirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        expirationTimeCalendar.set(Calendar.YEAR, year);
                        expirationTimeCalendar.set(Calendar.MONTH, month);
                        expirationTimeCalendar.set(Calendar.DAY_OF_MONTH, day);
                        expirationDate.setText(dateFormat.format(expirationTimeCalendar.getTime()));
                    }
                }, expirationTimeCalendar.get(Calendar.YEAR), expirationTimeCalendar.get(Calendar.MONTH), expirationTimeCalendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
                CommonLogger.debug("DatePickerDialog", "Showing DatePickerDialog...");
            }
        });
        expirationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        expirationTimeCalendar.set(Calendar.HOUR_OF_DAY, hour);
                        expirationTimeCalendar.set(Calendar.MINUTE, minute);
                        expirationTime.setText(timeFormat.format(expirationTimeCalendar.getTime()));
                    }
                }, expirationTimeCalendar.get(Calendar.HOUR_OF_DAY), expirationTimeCalendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                CommonLogger.debug("DatePickerDialog", "Showing TimerPickerDialog...");
            }
        });
        /* loading bitmap if needed */
        if (asset.getResources() != null && !asset.getResources().isEmpty()) {
            if (asset.getResources().get(0) != null && asset.getResources().get(0).getResourceBinayData() != null) {
                hasResource = true;
                byte[] bitmapBytes = asset.getResources().get(0).getResourceBinayData();
                BitmapDrawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length));
                if (takePicture != null)
                    takePicture.setImageDrawable(drawable);
            }
        }

        if (isEdit && asset.getExpirationDate() != null) {
            hasExpirationDate.setActivated(true);
            expirationDate.setText(dateFormat.format(asset.getExpirationDate()));
            expirationTime.setText(timeFormat.format(asset.getExpirationDate()));
        }

        try {
            long satoshis = moduleManager.getBitcoinWalletBalance(Utils.getBitcoinWalletPublicKey(moduleManager));
            satoshisWalletBalance = satoshis;
            double bitcoinWalletBalance = BitcoinConverter.convert(satoshis, SATOSHI, BITCOIN);
            bitcoinBalanceText.setText(String.format("%.6f BTC", bitcoinWalletBalance));
        } catch (Exception e) {
            bitcoinBalanceText.setText(NO_AVAILABLE);
        }
//        setUpHelpEditor(false);
//        showPresentationDialog();
        return rootView;
    }

    private void updateBitcoins() {
        Object selectedItem = bitcoinsSpinner.getSelectedItem();
        String bitcoinViewStr = bitcoinsView.getText().toString();
        if (selectedItem != null && bitcoinViewStr != null && bitcoinViewStr.length() > 0) {
            Currency from = (Currency) bitcoinsSpinner.getSelectedItem();
            double amount = Double.parseDouble(bitcoinsView.getText().toString());
            double amountBTC = BitcoinConverter.convert(amount, from, BITCOIN);
            bitcoinsTextView.setText(String.format("%.6f BTC", amountBTC));
        }
    }

//    private void showPresentationDialog() {
//        //Initialize settings
//        settingsManager = appSession.getModuleManager().getSettingsManager();
//        AssetFactorySettings settings = null;
//        try {
//            settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
//        } catch (Exception e) {
//            settings = null;
//        }
//        if (settings == null) {
//            settings = new AssetFactorySettings();
//            settings.setIsContactsHelpEnabled(true);
//            settings.setIsPresentationHelpEnabled(true);
//            try {
//                settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
//            } catch (CantPersistSettingsException e) {
//                e.printStackTrace();
//            }
//        }
//
//        final AssetFactorySettings assetFactorySettingsTemp = settings;
//
//        Handler handlerTimer = new Handler();
//        handlerTimer.postDelayed(new Runnable() {
//            public void run() {
//                if (assetFactorySettingsTemp.isPresentationHelpEnabled()) {
//                    setUpHelpEditor(false);
//                }
//            }
//        }, 500);
//    }

    private void setUpHelpEditor(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_factory)
                    .setIconRes(R.drawable.asset_factory)
                    .setVIewColor(R.color.dap_asset_factory_view_color)
                    .setTitleTextColor(R.color.dap_asset_factory_view_color)
                    .setSubTitle(R.string.dap_asset_factory_editor_subTitle)
                    .setBody(R.string.dap_asset_factory_editor_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

//            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    Object o = appSession.getData(SessionConstantsAssetFactory.PRESENTATION_IDENTITY_CREATED);
//                    if (o != null) {
//                        if ((Boolean) )) {
//                            //invalidate();
//                            appSession.removeData(SessionConstantsAssetFactory.PRESENTATION_IDENTITY_CREATED);
//                        }
//
//                    IdentityAssetIssuer identityAssetIssuer = moduleManager.getLoggedIdentityAssetIssuer();
//                    if (identityAssetIssuer == null) {
//                       getActivity().onBackPressed();
//                    } else {
//                        invalidate();
//                    }
//
//
            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetFactory.IC_ACTION_EDITOR_ASSET, 0, "help").setIcon(R.drawable.dap_asset_factory_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetFactory.IC_ACTION_EDITOR_ASSET) {
                setUpHelpEditor(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Factory system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            contextMenuInUse = false;
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, takePicture.getWidth(), takePicture.getHeight(), true);
                            if(imageBitmap != null){
                                hasResource = true;
//                                Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(takePicture);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error Loading Image", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            //pictureView.setBackground(new RoundedDrawable(imageBitmap, takePictureButton));
            if (imageBitmap != null) {
                hasResource = true;
                takePicture.setImageDrawable(new BitmapDrawable(getResources(), imageBitmap));
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Choose mode");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
        /*
        if(contactPicture!=null)
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete"); */
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(!contextMenuInUse) {
            switch (item.getItemId()) {
                case CONTEXT_MENU_CAMERA:
                    dispatchTakePictureIntent();
                    contextMenuInUse = true;
                    return true;
                case CONTEXT_MENU_GALLERY:
                    loadImageFromGallery();
                    contextMenuInUse = true;
                    return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void loadImageFromGallery() {
        Log.i(TAG, "Loading Image from Gallery...");
        Intent intentLoad = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
    }

    private void configureToolbar() {
        Toolbar toolbar = getPaintActivtyFeactures().getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor("#1d1d25"));
            toolbar.setTitleTextColor(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#1d1d25"));
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setAsset(AssetFactory asset) {
        this.asset = asset;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.action_create) {
            saveAsset();
        }
    }

    private void saveAsset() {
        //asset.setPublicKey("asset-factory-public-key");//// TODO: 02/10/15 set public key
        if (asset.getFactoryId() == null) {
            asset.setFactoryId(UUID.randomUUID().toString());
        }

        if (nameView.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Invalid Asset Name.", Toast.LENGTH_SHORT).show();
        } else {
            doSaveAsset();
        }
    }

    private void doSaveAsset() {
        asset.setName(nameView.getText().toString().trim());
        asset.setDescription(descriptionView.getText().toString().trim());
        asset.setQuantity(Integer.parseInt(quantityView.getText().toString().trim().isEmpty() ? "0" : quantityView.getText().toString().trim()));
        asset.setTotalQuantity(Integer.parseInt(quantityView.getText().toString().trim().isEmpty() ? "0" : quantityView.getText().toString().trim()));
        asset.setAmount(getSatoshis());
        asset.setIsRedeemable(isRedeemableView.isChecked());
        asset.setState(State.DRAFT);
        //// TODO: 02/10/15 Asset behaviour is given from the final user through dropdown control list
        asset.setAssetBehavior(AssetBehavior.REGENERATION_ASSET);
        if (hasResource) {
            List<Resource> resources = new ArrayList<>();
            Resource resource = new Resource();
            if (asset.getResources() != null && asset.getResources().size() > 0) {
                resource.setId(asset.getResources().get(0).getId());
            } else {
                resource.setId(UUID.randomUUID());
            }
            resource.setResourceType(ResourceType.IMAGE);
            resource.setResourceDensity(ResourceDensity.HDPI);
            resource.setResourceBinayData(toByteArray(takePicture));
            resources.add(resource);
            asset.setResources(resources);
        } else
            asset.setResources(null);
        if (hasExpirationDate.isActivated()) {
            if (!expirationDate.getText().toString().trim().isEmpty()) {
                try {
//                    asset.setExpirationDate(new Timestamp(dateFormat.parse(expirationDate.getText().toString()).getTime()));
                    Date date = dateTimeFormat.parse(expirationDate.getText().toString() + " " + expirationTime.getText().toString());
                    asset.setExpirationDate(new Timestamp(date.getTime()));
                    asset.setCreationTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
                } catch (Exception ex) {
                    CommonLogger.exception(TAG, ex.getMessage(), ex);
                }
            }
        } else // this asset hasn't expiration date
            asset.setExpirationDate(null);

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
                    if (!isEdit) {
                        Toast.makeText(getActivity(), String.format("Asset %s has been created", asset.getName()), Toast.LENGTH_SHORT).show();
                    }
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

    private long getSatoshis() {
        String amountStr = bitcoinsView.getText().toString().trim();
        if (amountStr != null && amountStr.length() > 0) {
            Currency currency = (Currency) bitcoinsSpinner.getSelectedItem();
            double amount = Double.parseDouble(amountStr);
            return Double.valueOf(BitcoinConverter.convert(amount, currency, SATOSHI)).longValue();
        } else {
            return 0;
        }
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    /**
     * ImageView to byte[]
     *
     * @return byte array
     */
    private byte[] toByteArray(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bm = imageView.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


}