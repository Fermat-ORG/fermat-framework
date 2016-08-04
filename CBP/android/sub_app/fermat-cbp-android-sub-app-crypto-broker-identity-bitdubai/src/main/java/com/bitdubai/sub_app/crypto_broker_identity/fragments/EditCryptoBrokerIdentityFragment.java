package com.bitdubai.sub_app.crypto_broker_identity.fragments;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.IdentityBrokerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.utils.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.CreateIdentityWorker;
import com.bitdubai.sub_app.crypto_broker_identity.util.DialogSelectCamOrPic;
import com.bitdubai.sub_app.crypto_broker_identity.util.EditIdentityWorker;
import com.bitdubai.sub_app.crypto_broker_identity.util.FragmentsCommons;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * This Fragment let you edit a Broker Identity
 * <p/>
 * Created by Nelson Ramirez (nelsonalfo@gmail.com)
 * Editd for the new desing, for Jinmy Bohorquez, 27/07/2016
 */
public class EditCryptoBrokerIdentityFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager>, ResourceProviderManager>
        implements FermatWorkerCallBack {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    // data
    private Bitmap cryptoBrokerBitmap = null;
    private byte[] identityImgByteArray = null;
    private String cryptoBrokerName = null;
    private EditText mBrokerStatus;
    private View visibilityLayout;
    private boolean wantPublishIdentity;
    private String cryptoBrokerPublicKey;
    private boolean actualizable;
    Location location;
    private Uri imageToUploadUri;
    //    IdentityBrokerPreferenceSettings settings;
    boolean isGpsDialogEnable = true;
    private ImageView mBrokerImage;
    private CryptoBrokerIdentityInformation identityInfo;
    List<CryptoBrokerIdentityInformation> brokerIdentities = new ArrayList<>();
    private PresentationDialog presentationDialog;
    IdentityBrokerPreferenceSettings subappSettings;

    // Managers

    private ImageView sw;
    private EditText mBrokerName;
    private View progressBar;
    private int maxLenghtTextCount = 30;
    FermatTextView textCount;

    private ExecutorService executor;
    private byte[] profileImage;

    private final TextWatcher textWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textCount.setText(String.valueOf(maxLenghtTextCount - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };

    public static EditCryptoBrokerIdentityFragment newInstance() {
        return new EditCryptoBrokerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //identityInfo = (CryptoBrokerIdentityInformation) appSession.getData(FragmentsCommons.IDENTITY_INFO);
        try {
            brokerIdentities = appSession.getModuleManager().listIdentities(0, 0);

            identityInfo = (brokerIdentities.size() > 0) ? brokerIdentities.get(0) : null;

        } catch (CantListCryptoBrokersException e) {
            e.printStackTrace();
        }
        appSession.setData(FragmentsCommons.VISIBILITY, (identityInfo != null) ?
                identityInfo.isPublished() : false);

        //If we landed here from CryptoBrokerImageCropperFragment, save the cropped Image.
        if (appSession.getData(FragmentsCommons.CROPPED_IMAGE) != null) {
            identityImgByteArray = (byte[]) appSession.getData(FragmentsCommons.CROPPED_IMAGE);
            appSession.setData(FragmentsCommons.IMAGE_BYTE_ARRAY, identityImgByteArray);
            cryptoBrokerBitmap = BitmapFactory.decodeByteArray(identityImgByteArray, 0, identityImgByteArray.length);
            appSession.removeData(FragmentsCommons.CROPPED_IMAGE);

        } else if (appSession.getData(FragmentsCommons.ORIGINAL_IMAGE) != null) {
            cryptoBrokerBitmap = (Bitmap) appSession.getData(FragmentsCommons.ORIGINAL_IMAGE);
            appSession.removeData(FragmentsCommons.ORIGINAL_IMAGE);

            if (appSession.getData(FragmentsCommons.IMAGE_BYTE_ARRAY) != null) {
                identityImgByteArray = (byte[]) appSession.getData(FragmentsCommons.IMAGE_BYTE_ARRAY);
                appSession.removeData(FragmentsCommons.IMAGE_BYTE_ARRAY);
            }
        }

        if (appSession.getData(FragmentsCommons.BROKER_NAME) != null) {
            cryptoBrokerName = (String) appSession.getData(FragmentsCommons.BROKER_NAME);
            appSession.removeData(FragmentsCommons.BROKER_NAME);
        }

        //Check if GPS is on and coordinate are fine


        turnGPSOn();

        configureToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_create_crypto_broker_identity_v2, container, false);
        initViews(rootLayout);
        getToolbar().setVisibility(View.VISIBLE);
        return rootLayout;
    }

    private void initViews(View layout) {

        actualizable = true;

        progressBar = layout.findViewById(R.id.cbi_progress_bar);
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        textCount = (FermatTextView) layout.findViewById(R.id.crypto_broker_name_text_count);
        mBrokerStatus = (EditText) layout.findViewById(R.id.crypto_broker_status);
        visibilityLayout = layout.findViewById(R.id.visibility_layout);
        sw = (ImageView) layout.findViewById(R.id.sw);
        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        final Button botonU = (Button) layout.findViewById(R.id.create_crypto_broker_button);
        /*final ImageView camara = (ImageView) layout.findViewById(R.id.camara);
        final ImageView galeria = (ImageView) layout.findViewById(R.id.galeria);*/

//        presentationDialog = new PresentationDialog.Builder(getActivity(), (ReferenceAppFermatSession) appSession)
//                .setBannerRes(R.drawable.banner_identity)
//                .setBody(R.string.cbp_broker_identity_welcome_body)
//                .setSubTitle(R.string.cbp_broker_identity_welcome_subTitle)
//                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
//                .setVIewColor(R.color.background_toolbar)
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
//            if ((appSession.getData(FragmentsCommons.SETTING_BROKER) != null)) {
//                subappSettings = (IdentityBrokerPreferenceSettings) appSession.getData(FragmentsCommons.SETTING_BROKER);
//            } else {
                subappSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
//            }
        } catch (Exception e) {
            subappSettings = null;
        }


        boolean showDialog;
        try {
            if (subappSettings == null) {
                subappSettings = new IdentityBrokerPreferenceSettings();
                subappSettings.setIsPresentationHelpEnabled(true);
//                appSession.setData(FragmentsCommons.SETTING_BROKER, subappSettings);
            }

            showDialog = subappSettings.isHomeTutorialDialogEnabled();
            if (showDialog) {
                homeTutorialDialog();
            }
            appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), subappSettings);

        } catch (Exception e) {
            makeText(getActivity(), R.string.cbi_system_error, Toast.LENGTH_SHORT).show();
        }

        //Check if GPS is on and coordinate are fine
        try {
            location = appSession.getModuleManager().getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
//            subappSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
//            subappSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
            isGpsDialogEnable = subappSettings.isGpsDialogEnabled();
        } catch (Exception e) {
            subappSettings = new IdentityBrokerPreferenceSettings();
            subappSettings.setGpsDialogEnabled(true);
            isGpsDialogEnable = true;
        }

        registerForContextMenu(mBrokerImage);
        mBrokerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogSelectCamOrPic Dcamgallery = new DialogSelectCamOrPic(getActivity(), appSession, null);
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

        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
              //  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (actualizable) {
                    actualizable = false;
                }
            }
        });

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wantPublishIdentity) {
                    sw.setImageResource(R.drawable.switch_off);
                    wantPublishIdentity = false;
                    appSession.setData(FragmentsCommons.TEMP_VISIBILITY, false);
                } else {
                    sw.setImageResource(R.drawable.switch_on);
                    wantPublishIdentity = true;
                    appSession.setData(FragmentsCommons.TEMP_VISIBILITY, true);
                }
            }
        });

        botonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSession.setData(FragmentsCommons.IDENTITY_INFO, identityInfo);
                if (identityInfo != null) {
                    actualizable = false;
                    editIdentityInfoInDevice();
                } else {
                    createNewIdentityInDevice();
                }
            }
        });

        //Coming from List activity
        if (identityInfo != null) {

            cryptoBrokerPublicKey = identityInfo.getPublicKey();
            mBrokerName.setText(identityInfo.getAlias());
            mBrokerName.selectAll();
            wantPublishIdentity = identityInfo.isPublished();

            profileImage = identityInfo.getProfileImage();

            if (profileImage.length == 0) {
                mBrokerImage.setImageResource(R.drawable.default_image);
            } else {
                BitmapDrawable bmd = new BitmapDrawable(getResources(), new ByteArrayInputStream(profileImage));
                mBrokerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bmd.getBitmap()));
            }
        } else {
            visibilityLayout.setVisibility(View.GONE);
        }

        //Coming from cropper activity
        if (cryptoBrokerBitmap != null)
            mBrokerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), cryptoBrokerBitmap));

        if (cryptoBrokerName != null)
            mBrokerName.setText(cryptoBrokerName);

        mBrokerName.requestFocus();
        mBrokerName.performClick();
        mBrokerName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenghtTextCount)});
        mBrokerName.addTextChangedListener(textWatcher);
        textCount.setText(String.valueOf(maxLenghtTextCount));

        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (actualizable) {
                    actualizable = false;
                }
            }
        });

        textCount.setText(String.valueOf(maxLenghtTextCount - mBrokerName.length()));


        if ((Boolean) appSession.getData(FragmentsCommons.TEMP_VISIBILITY) != null) {
            appSession.setData(FragmentsCommons.VISIBILITY, appSession.getData(FragmentsCommons.TEMP_VISIBILITY));
            if ((Boolean) appSession.getData(FragmentsCommons.TEMP_VISIBILITY) == true) {
                wantPublishIdentity = true;
            } else {
                wantPublishIdentity = false;
            }

        }

        if ((Boolean) appSession.getData(FragmentsCommons.VISIBILITY)) {
            sw.setImageResource(R.drawable.switch_on);
        } else {
            sw.setImageResource(R.drawable.switch_off);
        }

        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == FragmentsCommons.HELP_OPTION_MENU_ID) {
            homeTutorialDialog();
            return true;
        }
        if (item.getItemId() == FragmentsCommons.GEOLOCATION_SETTINGS_OPTION_MENU_ID) {
            appSession.setData(FragmentsCommons.BROKER_NAME, mBrokerName.getText().toString());
            appSession.setData(FragmentsCommons.ORIGINAL_IMAGE, cryptoBrokerBitmap);
            appSession.setData(FragmentsCommons.IDENTITY_INFO, identityInfo);
            appSession.setData(FragmentsCommons.IMAGE_BYTE_ARRAY, identityImgByteArray);
            //appSession.setData(FragmentsCommons.VISIBILITY, wantPublishIdentity);

            changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_EDIT_IDENTITY, appSession.getAppPublicKey());
        }

        return false;
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
                            cryptoBrokerBitmap = reducedSizeBitmap;
                        }
                    }
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            cryptoBrokerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        makeText(getActivity().getApplicationContext(), R.string.cbi_error_image, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            //Go to CryptoBrokerImageCropper so the user can crop (square) his picture
            appSession.setData(FragmentsCommons.BACK_ACTIVITY, Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY);
            appSession.setData(FragmentsCommons.ORIGINAL_IMAGE, cryptoBrokerBitmap);
            appSession.setData(FragmentsCommons.BROKER_NAME, mBrokerName.getText().toString());
            changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER, appSession.getAppPublicKey());

        }
       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onPostExecute(Object... result) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        //progressBar.setVisibility(View.GONE);

        if (identityInfo != null)
            makeText(getActivity(), R.string.cbi_identity_updated, Toast.LENGTH_LONG).show();
        else
            makeText(getActivity(), R.string.cbi_identity_created, Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        //progressBar.setVisibility(View.GONE);

        makeText(getActivity().getApplicationContext(), R.string.cbi_error_create, Toast.LENGTH_SHORT).show();
        appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
    }

    private void editIdentityInfoInDevice() {
        final String brokerNameText = mBrokerName.getText().toString();

        final ExposureLevel exposureLevel = wantPublishIdentity ? ExposureLevel.PUBLISH : ExposureLevel.HIDE;

        final byte[] imgInBytes = (cryptoBrokerBitmap != null) ? identityImgByteArray : profileImage;

        if (brokerNameText.trim().isEmpty()) {
            makeText(getActivity(), R.string.cbi_enter_name_alias, Toast.LENGTH_LONG).show();

        } else if (imgInBytes == null) {
            makeText(getActivity(), R.string.cbi_enter_image, Toast.LENGTH_LONG).show();

        } else {
            final int accuracy = getAccuracyData();
            final GeoFrequency frequency = getFrequencyData();

            CryptoBrokerIdentityInformation identity = new CryptoBrokerIdentityInformationImpl(brokerNameText, cryptoBrokerPublicKey,
                    imgInBytes, exposureLevel, accuracy, frequency);

            FermatWorker fermatWorker = new EditIdentityWorker(getActivity(), appSession, identity, this);

            //progressBar.setVisibility(View.VISIBLE);

            executor = fermatWorker.execute();
        }
    }

    private void createNewIdentityInDevice() {
        final String brokerAlias = mBrokerName.getText().toString();

        if (brokerAlias.trim().isEmpty()) {
            makeText(getActivity(), R.string.cbi_enter_name_alias, Toast.LENGTH_LONG).show();

        } else {
            final int accuracy = getAccuracyData();
            final GeoFrequency frequency = getFrequencyData();

            FermatWorker fermatWorker = new CreateIdentityWorker(getActivity(), appSession.getModuleManager(), this,
                    brokerAlias, (identityImgByteArray == null) ? ImagesUtils.toByteArray(convertImage(R.drawable.no_profile_image)) : identityImgByteArray, accuracy, frequency);

            //progressBar.setVisibility(View.VISIBLE);
            executor = fermatWorker.execute();

        }
    }

    private Bitmap convertImage(int resImage) {
        return BitmapFactory.decodeResource(getActivity().getResources(), resImage);
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
                    makeText(getActivity(), R.string.cbi_error, Toast.LENGTH_LONG).show();
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
       // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                        makeText(activity, R.string.cbi_turnon_gps, Toast.LENGTH_SHORT).show();
                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }

                } else {
                    String provider = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (!provider.contains("gps")) { //if gps is disabled
                        makeText(getContext(), R.string.cbi_turnon_gps, Toast.LENGTH_SHORT).show();
                        Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                }

            } catch (Exception ex) {
                if (Build.VERSION.SDK_INT < 23) {
                    makeText(activity, R.string.cbi_turnon_gps, Toast.LENGTH_SHORT).show();
                } else {
                    makeText(getContext(), R.string.cbi_turnon_gps, Toast.LENGTH_SHORT).show();
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

    public void turnOnGPSDialog() {

        try {
            PresentationDialog pd = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTitle(R.string.advertisement_text)
                    .setSubTitle(R.string.cbp_broker_identity_welcome_subTitle_gps)
                    .setBody(R.string.cbp_broker_identity_gps)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIconRes(R.drawable.bi_icon)
//                    .setTextNameLeft(R.string.dialog_button_close)
                    .setBannerRes(R.drawable.banner_identity)
                    .setVIewColor(R.color.background_toolbar)
                    .setCheckButtonAndTextVisible(0)
                    .build();
            pd.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeTutorialDialog() {
        presentationDialog = new PresentationDialog.Builder(getActivity(), (ReferenceAppFermatSession) appSession)
                .setBannerRes(R.drawable.banner_identity)
                .setBody(R.string.cbp_broker_identity_welcome_body)
                .setTitle(R.string.dialog_title)
                .setSubTitle(R.string.cbp_broker_identity_welcome_subTitle)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setVIewColor(R.color.background_toolbar)
                .setIconRes(R.drawable.bi_icon)
//                .setTextNameLeft(R.string.dialog_button_close)
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

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbp_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbp_action_bar_gradient_colors));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appSession.removeData(FragmentsCommons.TEMP_VISIBILITY);


    }
}