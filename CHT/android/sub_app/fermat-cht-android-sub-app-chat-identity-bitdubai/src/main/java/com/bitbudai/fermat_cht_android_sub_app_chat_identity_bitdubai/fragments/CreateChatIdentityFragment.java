package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.settings.ChatIdentitySettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.CreateChatIdentityExecutor;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.DialogCropImage;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.DialogSelectCamOrPic;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.EditIdentityExecutor;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cht_android_sub_app_chat_identity_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.CreateChatIdentityExecutor.SUCCESS;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 04/04/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/06/16.
 */

public class CreateChatIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ChatIdentityModuleManager>, SubAppResourcesProviderManager> {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int GALLERY_KITKAT_INTENT_CALLED = 3;
    private Bitmap chatBitmap;
    ChatIdentityModuleManager moduleManager;
    private EditText mChatName;
    private EditText mChatConnectionState;
    private ImageView mChatImage;
    ErrorManager errorManager;
    private boolean actualizable;
    private boolean contextMenuInUse = false;
    TextView textViewChtTitle;
    byte[] fanImageByteArray;
    Context context;
    Toolbar toolbar;
    TextView statusView;
    ImageView placeholdImg;
    Button btnRotate;
    int ROTATE_VALUE = 0;
    private Uri imageToUploadUri;
    private SettingsManager<ChatIdentitySettings> settingsManager;
    private ChatIdentityPreferenceSettings chatIdentitySettings;
    ChatIdentity identity;
    Location location;

    public static CreateChatIdentityFragment newInstance() {
        return new CreateChatIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            chatIdentitySettings = null;
            try {
                chatIdentitySettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                chatIdentitySettings = null;
            }
            if (chatIdentitySettings == null) {
                chatIdentitySettings = new ChatIdentityPreferenceSettings();
                chatIdentitySettings.setIsPresentationHelpEnabled(true);
                try {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), chatIdentitySettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }

            //Check if GPS is on and coordinate are fine
            try {
                location = moduleManager.getLocation();
            } catch (Exception e) {
                if (errorManager != null)
                    errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            }

            //Check if a default identity is configured
            try {
                identity = moduleManager.getIdentityChatUser();
            } catch (Exception e) {
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            }
            turnGPSOn();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_cht_identity_create, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        actualizable = true;
        mChatName = (EditText) layout.findViewById(R.id.editTextName);
        mChatConnectionState = (EditText) layout.findViewById(R.id.editTextStatus);
        final Button botonG = (Button) layout.findViewById(R.id.cht_button);
        statusView = (TextView) layout.findViewById(R.id.statusView);
        mChatImage = (ImageView) layout.findViewById(R.id.cht_image);
        textViewChtTitle = (TextView) layout.findViewById(R.id.textViewChtTitle);
        placeholdImg = (ImageView) layout.findViewById(R.id.placeholdImg);
        Bitmap bitmap = null;
        checkGPSOn();
        try {
            if (ExistIdentity() == false) {
                botonG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actualizable = false;
                        createNewIdentityInBackDevice("onClick");
                    }
                });

                registerForContextMenu(mChatImage);
                mChatImage.setOnClickListener(new View.OnClickListener() {
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

                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else {
                bitmap = BitmapFactory.decodeByteArray(identity.getImage(), 0, identity.getImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                mChatImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
                mChatName.setText(identity.getAlias().toString());
                String state = identity.getConnectionState();
                mChatConnectionState.setText(state);
                botonG.setText("Save Changes");
                botonG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actualizable = false;
                        try {
                            updateIdentityInBackDevice("onClick");
                        } catch (CantGetChatIdentityException e) {
                            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));

                        }
                    }
                });
                registerForContextMenu(mChatImage);
                mChatImage.setOnClickListener(new View.OnClickListener() {
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
            }
        } catch (CHTException e) {
            if (errorManager != null)
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setUpDialog() {
        try {
            PresentationDialog pd = new PresentationDialog.Builder(getActivity(), appSession)
                    .setSubTitle(R.string.cht_chat_identity_subtitle)
                    .setBody(R.string.cht_chat_identity_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIconRes(R.drawable.chat_identity_subapp)
                    .setBannerRes(R.drawable.banner_identity_chat)
                    .setVIewColor(R.color.cht_color_dialog_identity)
                    .setIsCheckEnabled(false)
                    .setTextFooter(R.string.cht_chat_footer)
                    .build();
            pd.show();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT_IDENTITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public void turnOnGPSDialog() {
        try {
            PresentationDialog pd = new PresentationDialog.Builder(getActivity(), appSession)
                    .setSubTitle(R.string.cht_chat_identity_subtitle)
                    .setBody(R.string.cht_chat_identity_gps)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIconRes(R.drawable.chat_identity_subapp)
                    .setCheckButtonAndTextVisible(0)
                    .setIsCheckEnabled(false)
                    .setBannerRes(R.drawable.banner_identity_chat)
                    .setVIewColor(R.color.cht_color_dialog_identity)
                    .setTextFooter(R.string.cht_chat_footer).build();
            pd.show();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT_IDENTITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 2:
                    setUpDialog();
                    break;
                case 1:
                    if (identity != null)
                        changeActivity(Activities.CHT_CHAT_GEOLOCATION_IDENTITY, appSession.getAppPublicKey());
                    else {
                        if (Build.VERSION.SDK_INT < 23) {
                            makeText(getActivity(), "You must create an identity to open this menu option", Toast.LENGTH_SHORT);
                        } else {
                            makeText(getContext(), "You must create an identity to open this menu option", Toast.LENGTH_SHORT);
                        }
                        setUpDialog();
                    }
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.M)
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
                            chatBitmap = reducedSizeBitmap;
                        }
                    }
                    try {
                        if (checkCameraPermission()) {
                            if (checkWriteExternalPermission()) {
                                if (chatBitmap != null) {
                                    if (chatBitmap.getWidth() >= 192 && chatBitmap.getHeight() >= 192) {
                                        final DialogCropImage dialogCropImage = new DialogCropImage(getActivity(), appSession, null, chatBitmap);
                                        dialogCropImage.show();
                                        dialogCropImage.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                if (dialogCropImage.getCroppedImage() != null) {
                                                    chatBitmap = getResizedBitmap(rotateBitmap(dialogCropImage.getCroppedImage(), ExifInterface.ORIENTATION_NORMAL), dpToPx(), dpToPx());
                                                    mChatImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), chatBitmap));
                                                } else {
                                                    chatBitmap = null;
                                                }
                                            }
                                        });
                                    } else {
                                        makeText(getActivity(), "The image selected is too small. Please select a photo with height and width of at least 192x192", Toast.LENGTH_LONG);
                                    }
                                } else {
                                    makeText(getActivity(), "Error on upload image", Toast.LENGTH_LONG);
                                }
                            } else {
                                makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG);
                            }
                        } else {
                            makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                    }
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            chatBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            if (chatBitmap.getWidth() >= 192 && chatBitmap.getHeight() >= 192) {
                                final DialogCropImage dialogCropImagee = new DialogCropImage(getActivity(), appSession, null, chatBitmap);
                                dialogCropImagee.show();
                                dialogCropImagee.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        if (dialogCropImagee.getCroppedImage() != null) {
                                            chatBitmap = getResizedBitmap(rotateBitmap(dialogCropImagee.getCroppedImage(), ExifInterface.ORIENTATION_NORMAL), dpToPx(), dpToPx());
                                            mChatImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), chatBitmap));
                                        } else {
                                            chatBitmap = null;
                                        }
                                    }
                                });
                            } else {
                                makeText(getActivity(), "The image selected is too small. Please select a photo with height and width of at least 192x192", Toast.LENGTH_LONG);
                                // cryptoBrokerBitmap = null;
                                // Toast.makeText(getActivity(), "The image selected is too small", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (Exception e) {
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                        makeText(getActivity().getApplicationContext(), "Error loading the image", Toast.LENGTH_SHORT);
                    }
                    break;
                case GALLERY_KITKAT_INTENT_CALLED:
                    Uri selectedImagee = data.getData();
                    // Check for the freshest data.
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            String provider = "com.android.providers.media.MediaProvider";
                            getActivity().getContentResolver().takePersistableUriPermission(selectedImagee, Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getActivity().getContentResolver().takePersistableUriPermission(selectedImagee, Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getActivity().grantUriPermission(provider, selectedImagee, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            getActivity().grantUriPermission(provider, selectedImagee, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            getActivity().grantUriPermission(provider, selectedImagee, Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                            getActivity().requestPermissions(
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},

                                    REQUEST_LOAD_IMAGE);
                        }
                    }
                    try {
                        if (isAttached) {
                            chatBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImagee);
                            //cryptoBrokerBitmap = Bitmap.createScaledBitmap(cryptoBrokerBitmap, mBrokerImage.getWidth(), mBrokerImage.getHeight(), true);
                            if (chatBitmap.getWidth() >= 192 && chatBitmap.getHeight() >= 192) {
                                //cryptoBrokerBitmap = ImagesUtils.cropImage(cryptoBrokerBitmap);
                                final DialogCropImage dialogCropImagee = new DialogCropImage(getActivity(), appSession, null, chatBitmap);
                                dialogCropImagee.show();
                                dialogCropImagee.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        if (dialogCropImagee.getCroppedImage() != null) {
                                            chatBitmap = getResizedBitmap(rotateBitmap(dialogCropImagee.getCroppedImage(), ExifInterface.ORIENTATION_NORMAL), dpToPx(), dpToPx());
                                            mChatImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), chatBitmap));
                                        } else {
                                            chatBitmap = null;
                                        }
                                    }
                                });
                            } else {
                                makeText(getActivity(), "The image selected is too small. Please select a photo with height and width of at least 192x192", Toast.LENGTH_LONG);
                                //cryptoBrokerBitmap = null;
                                // Toast.makeText(getActivity(), "The image selected is too small", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                        makeText(getActivity().getApplicationContext(), "Error loading the image", Toast.LENGTH_SHORT);
                    }
            }
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void saveAndGoBack() {
        try {
            if (ExistIdentity()) {
                updateIdentityInBackDevice("onBack");
            } else {
                if (!mChatName.getText().toString().equals("")) {
                    createNewIdentityInBackDevice("onBack");
                }
            }
        } catch (CHTException e) {
            //e.printStackTrace();
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

    public int dpToPx() {
        int dp = 150;
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateIdentityInBackDevice(String donde) throws CantGetChatIdentityException {
        String chatNameText = mChatName.getText().toString();
        String state = mChatConnectionState.getText().toString();

        if (chatNameText.trim().equals("")) {
            makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG);
        }
        if (chatBitmap == null) {
            chatBitmap = BitmapFactory.decodeByteArray(identity.getImage(), 0, identity.getImage().length);
            byte[] imgInBytes = ImagesUtils.toByteArray(chatBitmap);
            EditIdentityExecutor executor = null;
            try {
                executor = new EditIdentityExecutor(appSession, identity.getPublicKey(), chatNameText, identity.getImage(), state);
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("onClick")) {
                            makeText(getActivity(), "Chat Identity Update.", Toast.LENGTH_LONG);
                            getActivity().onBackPressed();
                        }
                        break;
                }
            } catch (Exception e) {
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            }
        } else {
            byte[] imgInBytes = ImagesUtils.toByteArray(chatBitmap);
            EditIdentityExecutor executor = null;
            try {
                executor = new EditIdentityExecutor(appSession, identity.getPublicKey(), chatNameText, imgInBytes,
                        identity.getConnectionState());
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("onClick")) {
                            makeText(getActivity(), "Chat Identity Update.", Toast.LENGTH_LONG);
                            getActivity().onBackPressed();
                        }
                        break;
                }
            } catch (Exception e) {
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            }
        }
    }

    private void createNewIdentityInBackDevice(String donde) {
        String chatNameText = mChatName.getText().toString();
        String identityConnectionNameText = mChatConnectionState.getText().toString();
        if (chatBitmap == null) {
            chatBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.cht_id_image_profile);
        }
        if (identityConnectionNameText.length() == 0) {
            identityConnectionNameText = "Available";
        }
        if (chatNameText.trim().equals("")) {
            makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG);
        } else {

            byte[] imgInBytes = ImagesUtils.toByteArray(chatBitmap);
            CreateChatIdentityExecutor executor = null;
            try {
                executor = new CreateChatIdentityExecutor(appSession, chatNameText, imgInBytes, identityConnectionNameText);

                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("onClick")) {
                            makeText(getActivity(), "Chat Identity Created.", Toast.LENGTH_LONG);
                            getActivity().onBackPressed();
                        }
                        break;
                }
            } catch (CantGetChatIdentityException e) {
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            }
        }
    }

    public boolean ExistIdentity() throws CHTException {
        try {
            if (!identity.getAlias().isEmpty()) {
                Log.i("CHT EXIST IDENTITY", "TRUE");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        Log.i("CHT EXIST IDENTITY", "FALSE");
        return false;
    }

    private boolean availableCameras() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void dispatchTakePictureIntent() {
        // Check available cameras
        PackageManager pm = getActivity().getPackageManager();
        boolean frontCam = false, rearCam = false;
        //Must have a targetSdk >= 9 defined in the AndroidManifest
        frontCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
        rearCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if ((frontCam || rearCam) && availableCameras()) {
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
                        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
                        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        imageToUploadUri = Uri.fromFile(f);
                        startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
                    } else {
                        makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG);
                    }
                }
            } else {
                Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
                chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                imageToUploadUri = Uri.fromFile(f);
                startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                makeText(getContext(), "there is no cameras available", Toast.LENGTH_SHORT);
            } else {
                makeText(getActivity(), "there is no cameras available", Toast.LENGTH_SHORT);
            }
        }
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

    @TargetApi(Build.VERSION_CODES.M)
    private void loadImageFromGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                getActivity().requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        GALLERY_KITKAT_INTENT_CALLED);
            }
        }
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Choose picture"), REQUEST_LOAD_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/jpeg");
            //Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean checkWriteExternalPermission() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void deleteImageUri(Uri uri) {
        getActivity().getContentResolver().delete(uri, null, null);
    }

    private boolean checkCameraPermission() {
        String permission = "android.permission.CAMERA";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public void turnGPSOn() {
        try {
            if (!checkGPSFineLocation() || !checkGPSCoarseLocation()) { //if gps is disabled
                if (Build.VERSION.SDK_INT < 23) {
                    if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this.getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                    if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this.getActivity(),
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                } else {
                    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        getActivity().requestPermissions(
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        getActivity().requestPermissions(
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                }
            }
        } catch (Exception e) {
            try {
                Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                intent.putExtra("enabled", true);
                if (Build.VERSION.SDK_INT < 23) {
                    String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (!provider.contains("gps")) { //if gps is disabled
                        makeText(getActivity(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                } else {
                    String provider = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (!provider.contains("gps")) { //if gps is disabled
                        makeText(getContext(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                }
            } catch (Exception ex) {
                if (Build.VERSION.SDK_INT < 23) {
                    makeText(getActivity(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
                } else {
                    makeText(getContext(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
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

    private void checkGPSOn() {
        if (location != null) {
            if (location.getLongitude() == 0 || location.getLatitude() == 0) {
                //if (chatIdentitySettings.isHomeTutorialDialogEnabled()) {
                turnOnGPSDialog();
                // }
            } else {
                if (chatIdentitySettings.isHomeTutorialDialogEnabled()) {
                    setUpDialog();
                }
            }
        } else
            turnOnGPSDialog();
    }
}