package com.bitdubai.sub_app.crypto_customer_identity.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.IdentityCustomerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.Utils.CryptoCustomerIdentityInformationImpl;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.dialogs.DialogSelectCamPic;
import com.bitdubai.sub_app.crypto_customer_identity.util.CreateIdentityWorker;
import com.bitdubai.sub_app.crypto_customer_identity.util.EditCustomerIdentityWorker;
import com.bitdubai.sub_app.crypto_customer_identity.util.FragmentsCommons;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;


/**
 * Created by Penny, 26/07/2016
 */
public class CreateAndEditCryptoCustomerIdentityFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager>, ResourceProviderManager>
        implements FermatWorkerCallBack {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private Bitmap cryptoCustomerBitmap = null;
    private byte[] identityImageByteArray = null;
    private String cryptoCustomerName = null;
    private byte[] profileImage;
    Location location;
    private Uri imageToUploadUri;

    private EditText mCustomerName;
    private EditText mCustomerStatus;
    private LinearLayout mSaveUpdateButton;

    private PresentationDialog presentationDialog;

    //private View progressBar;
    private int maxLenghtTextCount = 30;
    FermatTextView textCount;
    private String cryptoCustomerPublicKey;
    IdentityCustomerPreferenceSettings subappSettings;

    private CryptoCustomerIdentityInformation identityInfo;
    List<CryptoCustomerIdentityInformation> customerIdentities = new ArrayList<>();

//    IdentityCustomerPreferenceSettings settings;
    boolean isGpsDialogEnable;

    private ExecutorService executor;
    private boolean isEditing;

    private final TextWatcher textWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textCount.setText(String.valueOf(maxLenghtTextCount - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };

    public static CreateAndEditCryptoCustomerIdentityFragment newInstance() {
        return new CreateAndEditCryptoCustomerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //Getting the first Identity since it was implemented for multiples identities before
            customerIdentities = appSession.getModuleManager().getAllCryptoCustomersIdentities(0, 0);

            identityInfo = (customerIdentities.size() > 0) ? customerIdentities.get(0) : null;

        } catch (CantGetCryptoCustomerListException e) {
            e.printStackTrace();
        }

        //If we landed here from CryptoCustomerImageCropperFragment or geo settings fragment
        //Use the cropped image, if there is one (!=null)
        if (appSession.getData(FragmentsCommons.CROPPED_IMAGE) != null) {
            identityImageByteArray = (byte[]) appSession.getData(FragmentsCommons.CROPPED_IMAGE);
            appSession.setData(FragmentsCommons.IMAGE_BYTE_ARRAY, identityImageByteArray);
            cryptoCustomerBitmap = BitmapFactory.decodeByteArray(identityImageByteArray, 0, identityImageByteArray.length);
            appSession.removeData(FragmentsCommons.CROPPED_IMAGE);
        } else if (appSession.getData(FragmentsCommons.ORIGINAL_IMAGE) != null) {
            cryptoCustomerBitmap = (Bitmap) appSession.getData(FragmentsCommons.ORIGINAL_IMAGE);
            appSession.removeData(FragmentsCommons.ORIGINAL_IMAGE);

            if (appSession.getData(FragmentsCommons.IMAGE_BYTE_ARRAY) != null) {
                identityImageByteArray = (byte[]) appSession.getData(FragmentsCommons.IMAGE_BYTE_ARRAY);
                appSession.removeData(FragmentsCommons.IMAGE_BYTE_ARRAY);
            }
        }

        //And the customer name, if there is one (!=null)
        if (appSession.getData(FragmentsCommons.CUSTOMER_NAME) != null) {
            cryptoCustomerName = (String) appSession.getData(FragmentsCommons.CUSTOMER_NAME);
            appSession.removeData(FragmentsCommons.CUSTOMER_NAME);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_create_crypto_customer_identity_v2, container, false);
        initViews(rootLayout);
        getToolbar().setVisibility(View.VISIBLE);
        return rootLayout;
    }

    private void initViews(View layout) {

        //progressBar = layout.findViewById(R.id.cci_progress_bar);
        final ImageView mCustomerImage = (ImageView) layout.findViewById(R.id.crypto_customer_image);
        mCustomerName = (EditText) layout.findViewById(R.id.crypto_customer_name);
        mCustomerStatus = (EditText) layout.findViewById(R.id.crypto_customer_status);
        textCount = (FermatTextView) layout.findViewById(R.id.crypto_customer_name_text_count);
        mSaveUpdateButton = (LinearLayout) layout.findViewById(R.id.cbp_customer_button);

        turnGPSOn();

//        presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
//                .setBannerRes(R.drawable.banner_identity_customer)
//                .setBody(R.string.cbp_customer_identity_welcome_body)
//                .setSubTitle(R.string.cbp_customer_identity_welcome_subTitle)
//                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
//                .setVIewColor(R.color.ccc_color_dialog_identity)
//                .setIsCheckEnabled(false)
//                .build();
//
//        presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                checkGPSOn();
//            }
//        });

        try {
//            if((appSession.getData(FragmentsCommons.SETTING_CUSTOMER) != null)) {
//                subappSettings = (IdentityCustomerPreferenceSettings) appSession.getData(FragmentsCommons.SETTING_CUSTOMER);
//            } else {
                subappSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
//            }
        } catch (Exception e) {
            subappSettings = null;
        }

        if (subappSettings == null) {
            subappSettings = new IdentityCustomerPreferenceSettings();
            subappSettings.setIsPresentationHelpEnabled(true);
//            appSession.setData(FragmentsCommons.SETTING_CUSTOMER, subappSettings);
        }
            try {
                appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), subappSettings);
            } catch (Exception e) {
                appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }

        boolean showDialog;
        try {
//            showDialog = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isHomeTutorialDialogEnabled();
            showDialog = subappSettings.isHomeTutorialDialogEnabled();
            if (showDialog) {
                homeTutorialDialog();
            }
        } catch (Exception e) {
            makeText(getActivity(), R.string.crypto_customer_error_dialog, Toast.LENGTH_SHORT).show();
        }

        //Check if GPS is on and coordinates are fine
        try {
            location = appSession.getModuleManager().getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            isGpsDialogEnable = true;
//            subappSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
            isGpsDialogEnable = subappSettings.isGpsDialogEnabled();
        } catch (Exception e) {
            subappSettings = new IdentityCustomerPreferenceSettings();
            subappSettings.setGpsDialogEnabled(true);
            isGpsDialogEnable = true;
        }


        if (identityInfo != null) { //I'm editing the customer
            isEditing = true;
            cryptoCustomerPublicKey = identityInfo.getPublicKey();

            //If I'm editing, and I come from another Fragment, like GEO or Crop,
            // don't put identity old values here.

            if (cryptoCustomerName == null) {
                mCustomerName.setText(identityInfo.getAlias());
                mCustomerName.selectAll();
            }
            if (cryptoCustomerBitmap == null) {
                profileImage = identityInfo.getProfileImage();

                if (profileImage.length == 0) {
                    mCustomerImage.setImageResource(R.drawable.default_image);
                } else {
                    BitmapDrawable bmd = new BitmapDrawable(getResources(), new ByteArrayInputStream(profileImage));
                    mCustomerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bmd.getBitmap()));
                }
            }
        }

        if (cryptoCustomerBitmap != null) {
            RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), cryptoCustomerBitmap);
            bitmapDrawable.setCornerRadius(360);
            bitmapDrawable.setAntiAlias(true);
            mCustomerImage.setImageDrawable(bitmapDrawable);
        }

        if (cryptoCustomerName != null)
            mCustomerName.setText(cryptoCustomerName);

        mCustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogSelectCamPic Dcamgallery = new DialogSelectCamPic(getActivity(), appSession, null);
                Dcamgallery.show();
                Dcamgallery.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (Dcamgallery.getButtonTouch() == Dcamgallery.TOUCH_CAM) {
                            dispatchTakePictureIntent();
                        } else if (Dcamgallery.getButtonTouch() == Dcamgallery.TOUCH_GALLERY) {
                            loadImageFromGallery();
                        }
                    }
                });
            }
        });

        mSaveUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //appSession.setData(FragmentsCommons.IDENTITY_INFO, identityInfo);
                if (identityInfo != null && isEditing) {
                    editIdentityInfoInBackDevice();
                } else {
                    createNewIdentityInBackDevice();
                }
            }
        });


        mCustomerName.requestFocus();
        mCustomerName.performClick();
        mCustomerName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenghtTextCount)});
        mCustomerName.addTextChangedListener(textWatcher);
        textCount.setText(String.valueOf(maxLenghtTextCount - mCustomerName.getText().length()));

       /* mCustomerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });*/

        mCustomerName.requestFocus();

        textCount.setText(String.valueOf(maxLenghtTextCount - mCustomerName.length()));


        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);

        configureToolbar();
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cci_action_bar_gradient_colors_v2, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cci_action_bar_gradient_colors_v2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int menuItemId = item.getItemId();

        switch (menuItemId) {

            case FragmentsCommons.HELP_OPTION_MENU_ID:
                new PresentationDialog.Builder(getActivity(), appSession)
                        .setBannerRes(R.drawable.banner_identity_customer)
                        .setBody(R.string.cbp_customer_identity_welcome_body)
                        .setSubTitle(R.string.cbp_customer_identity_welcome_subTitle)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                        .setVIewColor(R.color.ccc_color_dialog_identity)
                        .setIsCheckEnabled(false)
                        .build().show();
                return true;

            case FragmentsCommons.GEOLOCATION_SETTINGS_OPTION_MENU_ID:

                //Save customer name and cropped image
                appSession.setData(FragmentsCommons.CUSTOMER_NAME, mCustomerName.getText().toString());
                appSession.setData(FragmentsCommons.CROPPED_IMAGE, identityImageByteArray);
                appSession.setData(FragmentsCommons.IDENTITY_INFO, identityInfo);
                appSession.setData(FragmentsCommons.IMAGE_BYTE_ARRAY, identityImageByteArray);

                changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_CREATE_IDENTITY, appSession.getAppPublicKey());
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    // grant all three uri permissions!
                    if (imageToUploadUri != null) {
                        String provider = "com.android.providers.media.MediaProvider";
                        Uri selectedImage = imageToUploadUri;
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                getActivity().getContentResolver().takePersistableUriPermission(selectedImage, Intent.FLAG_GRANT_READ_URI_PERMISSION
                                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                getActivity().grantUriPermission(provider, selectedImage, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                getActivity().grantUriPermission(provider, selectedImage, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                getActivity().grantUriPermission(provider, selectedImage, Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                                getActivity().requestPermissions(
                                        new String[]{Manifest.permission.CAMERA},
                                        REQUEST_IMAGE_CAPTURE);
                            }
                        }
                        getActivity().getContentResolver().notifyChange(selectedImage, null);
                        Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());
                        if (reducedSizeBitmap != null) {
                            cryptoCustomerBitmap = reducedSizeBitmap;
                        }
                    }
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            cryptoCustomerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), R.string.crypto_customer_error_image, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            //Go to CryptoCustomerImageCropperFragment so the user can crop (square) his picture
            appSession.setData(FragmentsCommons.ORIGINAL_IMAGE, cryptoCustomerBitmap);
            appSession.setData(FragmentsCommons.CUSTOMER_NAME, mCustomerName.getText().toString());
            changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER, appSession.getAppPublicKey());
        }
       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Creates the new identity of the customer in device
     */
    private void createNewIdentityInBackDevice() {
        final String customerAlias = mCustomerName.getText().toString();

        if (customerAlias.trim().isEmpty()) {
            Toast.makeText(getActivity(), R.string.crypto_customer_enter_name_alias, Toast.LENGTH_LONG).show();

        } else {
            final int accuracy = getAccuracyData();
            final GeoFrequency frequency = getFrequencyData();

            FermatWorker fermatWorker = new CreateIdentityWorker(getActivity(), appSession.getModuleManager(), this,
                    customerAlias, (identityImageByteArray == null) ? ImagesUtils.toByteArray(convertImage(R.drawable.no_profile_image)) : identityImageByteArray, accuracy, frequency);

            //progressBar.setVisibility(View.VISIBLE);
            executor = fermatWorker.execute();
        }
    }

    private void editIdentityInfoInBackDevice() {
        final String customerNameText = mCustomerName.getText().toString();

        final byte[] imgInBytes = (cryptoCustomerBitmap != null) ? ImagesUtils.toByteArray(convertImage(R.drawable.no_profile_image)) : profileImage;

        if (customerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), R.string.crypto_customer_enter_name, Toast.LENGTH_LONG).show();

        } else if (imgInBytes == null) {
            Toast.makeText(getActivity(), R.string.crypto_customer_enter_image, Toast.LENGTH_LONG).show();

        } else {
            final int accuracy = getAccuracyData();
            final GeoFrequency frequency = getFrequencyData();

            CryptoCustomerIdentityInformationImpl identity = new CryptoCustomerIdentityInformationImpl(customerNameText, cryptoCustomerPublicKey,
                    imgInBytes, ExposureLevel.PUBLISH, accuracy, frequency);

            FermatWorker fermatWorker = new EditCustomerIdentityWorker(getActivity(), appSession, identity, this);

            //progressBar.setVisibility(View.VISIBLE);
            executor = fermatWorker.execute();
        }
    }

    private Bitmap convertImage(int resImage) {
        return BitmapFactory.decodeResource(getActivity().getResources(), resImage);
    }

    @Override
    public void onPostExecute(Object... result) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        //progressBar.setVisibility(View.GONE);
        if (!isEditing)
            Toast.makeText(getActivity(), R.string.crypto_customer_identity_created, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity(), R.string.crypto_customer_identity_updated, Toast.LENGTH_LONG).show();

        getActivity().onBackPressed();
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        //progressBar.setVisibility(View.GONE);

        Toast.makeText(getActivity(), R.string.crypto_customer_error_create, Toast.LENGTH_SHORT).show();

        appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
    }

    private void dispatchTakePictureIntent() {
       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Check permission for CAMERA
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    getActivity().requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_IMAGE_CAPTURE);
                } else {
                    getActivity().requestPermissions(
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_IMAGE_CAPTURE);
                }
            } else {
                if (checkWriteExternalPermission()) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    imageToUploadUri = Uri.fromFile(f);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else {
                    Toast.makeText(getActivity(), R.string.crypto_customer_error, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            imageToUploadUri = Uri.fromFile(f);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void loadImageFromGallery() {
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent loadImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }

    private int getAccuracyData() {
        return appSession.getData(FragmentsCommons.ACCURACY_DATA) == null ? 0 :
                (int) appSession.getData(FragmentsCommons.ACCURACY_DATA);
    }

    private GeoFrequency getFrequencyData() {
        return appSession.getData(FragmentsCommons.FREQUENCY_DATA) == null ? GeoFrequency.NONE :
                (GeoFrequency) appSession.getData(FragmentsCommons.FREQUENCY_DATA);
    }

    private Bitmap getBitmap(String path) {
        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 3000000; // 1.2MP
            in = getActivity().getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", new StringBuilder().append("scale = ").append(scale).append(", orig-width: ").append(o.outWidth).append(", orig-height: ").append(o.outHeight).toString());

            Bitmap b = null;
            in = getActivity().getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", new StringBuilder().append("1th scale operation dimenions - width: ").append(width).append(", height: ").append(height).toString());

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", new StringBuilder().append("bitmap size - width: ").append(b.getWidth()).append(", height: ").append(b.getHeight()).toString());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    public void turnGPSOn() {
        final Activity activity = getActivity();

        try {
            if (!checkGPSFineLocation() || !checkGPSCoarseLocation()) { //if gps is disabled
                if (Build.VERSION.SDK_INT < 23) {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                } else {
                    if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    if (activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                        activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                }
            }
        } catch (Exception e) {
            try {
                Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                intent.putExtra("enabled", true);

                if (Build.VERSION.SDK_INT < 23) {
                    String provider = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (!provider.contains("gps")) { //if gps is disabled
                        Toast.makeText(activity, R.string.crypto_customer_turnon_gps, Toast.LENGTH_SHORT).show();
                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }

                } else {
                    String provider = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (!provider.contains("gps")) { //if gps is disabled
                        Toast.makeText(getContext(), R.string.crypto_customer_turnon_gps, Toast.LENGTH_SHORT).show();
                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                }

            } catch (Exception ex) {
                if (Build.VERSION.SDK_INT < 23) {
                    Toast.makeText(activity, R.string.crypto_customer_turnon_gps, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.crypto_customer_turnon_gps, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkGPSCoarseLocation() {
        String permission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkGPSFineLocation() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void checkGPSOn() {
        if (location != null) {
            if (location.getLongitude() == 0 || location.getLatitude() == 0) {
                if (isGpsDialogEnable) {
                    turnOnGPSDialog();
                }

            }
        } else if (isGpsDialogEnable) {
            turnOnGPSDialog();
        }
    }

    public void homeTutorialDialog() {
        presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBannerRes(R.drawable.banner_identity_customer)
                .setBody(R.string.cbp_customer_identity_welcome_body)
                .setSubTitle(R.string.cbp_customer_identity_welcome_subTitle)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setVIewColor(R.color.ccc_color_dialog_identity)
                .setIconRes(R.drawable.building)
                .setIsCheckEnabled(false)
                .build();

        presentationDialog.show();

        presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                checkGPSOn();
            }
        });
    }

    public void turnOnGPSDialog() {
        try {
            PresentationDialog pd = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTitle(R.string.cbp_customer_identity_welcome_title_gps)
                    .setSubTitle(R.string.cbp_customer_identity_welcome_subTitle_gps)
                    .setBody(R.string.cbp_customer_identity_gps)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.banner_identity_customer)
                    .setVIewColor(R.color.ccc_color_dialog_identity)
                    .setIconRes(R.drawable.building)
                    .setCheckButtonAndTextVisible(0)
                    .build();

            pd.show();
            subappSettings.setGpsDialogEnabled(false);
            appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), subappSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
