package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.sessions.ChatIdentitySession;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.settings.ChatIdentitySettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.CommonLogger;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.CreateChatIdentityExecutor;
import static com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.CreateChatIdentityExecutor.SUCCESS;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.DialogCropImage;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.DialogSelectCamOrPic;
import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.EditIdentityExecutor;
import static com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.MenuConstants.MENU_ADD_ACTION;
import static com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.MenuConstants.MENU_HELP_ACTION;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_identity_bitdubai.R;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


/**
 * FERMAT-ORG
 * Developed by Lozadaa on 04/04/16.
 */

public class CreateChatIdentityFragment extends AbstractFermatFragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int GALLERY_KITKAT_INTENT_CALLED = 3;
    private Bitmap cryptoBrokerBitmap;
    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    ChatIdentityModuleManager moduleManager;
    private EditText mBrokerName;
    private EditText mChatConnectionState;
    private ImageView mBrokerImage;
    ErrorManager errorManager;
    private boolean actualizable;
    private boolean contextMenuInUse = false;
    TextView textViewChtTitle;
    byte[] fanImageByteArray;
    ChatIdentitySession Session;
    Context context;
    Toolbar toolbar;
    TextView statusView;
    ImageView placeholdImg;
    Button btnRotate;
    Bitmap cryptoBrokerBitmapp;
    int ROTATE_VALUE = 0;
    private Uri imageToUploadUri;
    private SettingsManager<ChatIdentitySettings> settingsManager;
    private ChatIdentityPreferenceSettings chatIdentitySettings;

    public static CreateChatIdentityFragment newInstance() {
        return new CreateChatIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            Session = (ChatIdentitySession) appSession;
            moduleManager = Session.getModuleManager();
            errorManager = Session.getErrorManager();

            chatIdentitySettings = null;
            try {
                chatIdentitySettings = moduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                chatIdentitySettings = null;
            }
            if (chatIdentitySettings == null) {
                chatIdentitySettings = new ChatIdentityPreferenceSettings();
                chatIdentitySettings.setIsPresentationHelpEnabled(true);
                try {
                    moduleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), chatIdentitySettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));

        }
        toolbar = getToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_cht_identity_create, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        actualizable = true;
        mBrokerName = (EditText) layout.findViewById(R.id.editTextName);
        mChatConnectionState = (EditText) layout.findViewById(R.id.editTextStatus);
        final Button botonG = (Button) layout.findViewById(R.id.cht_button);
        statusView = (TextView) layout.findViewById(R.id.statusView);
        mBrokerImage = (ImageView) layout.findViewById(R.id.cht_image);
        textViewChtTitle = (TextView) layout.findViewById(R.id.textViewChtTitle);
        placeholdImg = (ImageView) layout.findViewById(R.id.placeholdImg);
        Bitmap bitmap = null;
        if (chatIdentitySettings.isHomeTutorialDialogEnabled()) {
            setUpDialog();
        }
        try {
            if (ExistIdentity() == false) {

                botonG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actualizable = false;
                        createNewIdentityInBackDevice("onClick");
                    }
                });

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

                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else {
                bitmap = BitmapFactory.decodeByteArray(moduleManager.getIdentityChatUser().getImage(), 0, moduleManager.getIdentityChatUser().getImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                mBrokerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
                mBrokerName.setText(moduleManager.getIdentityChatUser().getAlias().toString());
                String state = moduleManager.getIdentityChatUser().getConnectionState();
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
            }
        } catch (CHTException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));

        }
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, MENU_HELP_ACTION, 0, "help").setIcon(R.drawable.ic_menu_help_cht)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        //menu.add(0, MENU_ADD_ACTION, 0, "help").setIcon(R.drawable.ic_action_add)
        //        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    public void setUpDialog() {
        try {
            PresentationDialog pd = new PresentationDialog.Builder(getActivity(), Session)
                    .setSubTitle(R.string.cht_chat_identity_subtitle)
                    .setBody(R.string.cht_chat_identity_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIconRes(R.drawable.chat_identity_subapp)
                    .setBannerRes(R.drawable.banner_identity_chat)
                    .setIsCheckEnabled(false)
                    .setTextFooter(R.string.cht_chat_footer).build();
            pd.show();
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT_IDENTITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case MENU_HELP_ACTION:
                    setUpDialog();
                    break;
                case MENU_ADD_ACTION:
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
                            cryptoBrokerBitmap = reducedSizeBitmap;
                        }
                    }
                    try {
                        if(checkCameraPermission()) {
                            if(checkWriteExternalPermission()) {
                                if (cryptoBrokerBitmap != null) {
                                    if (cryptoBrokerBitmap.getWidth() >= 192 && cryptoBrokerBitmap.getHeight() >= 192) {
                                        final DialogCropImage dialogCropImage = new DialogCropImage(getActivity(), appSession, null, cryptoBrokerBitmap);
                                        dialogCropImage.show();
                                        dialogCropImage.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                if (dialogCropImage.getCroppedImage() != null) {
                                                    cryptoBrokerBitmap =  getResizedBitmap(rotateBitmap(dialogCropImage.getCroppedImage(), ExifInterface.ORIENTATION_NORMAL), 230, 230);
                                                    Picasso.with(getActivity()).load(getImageUri(getActivity(),  cryptoBrokerBitmap)).transform(new CircleTransform()).into(mBrokerImage);
                                                }else{
                                                    cryptoBrokerBitmap = null;
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getActivity(), "The image selected is too small. Please select a photo with height and width of at least 192x192", Toast.LENGTH_LONG).show();
                                       // cryptoBrokerBitmap = null;
                                        //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Error on upload image", Toast.LENGTH_LONG).show();
                                  //  cryptoBrokerBitmap = null;
                                    //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
                               // cryptoBrokerBitmap = null;
                                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
                          //  cryptoBrokerBitmap = null;
                            //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
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
                            cryptoBrokerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            //cryptoBrokerBitmap = Bitmap.createScaledBitmap(cryptoBrokerBitmap, mBrokerImage.getWidth(), mBrokerImage.getHeight(), true);
                            if (cryptoBrokerBitmap.getWidth() >= 192 && cryptoBrokerBitmap.getHeight() >= 192) {
                               // cryptoBrokerBitmap = ImagesUtils.cropImage(cryptoBrokerBitmap);
                                final DialogCropImage dialogCropImagee = new DialogCropImage(getActivity(), appSession, null, cryptoBrokerBitmap);
                                dialogCropImagee.show();
                                dialogCropImagee.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        if (dialogCropImagee.getCroppedImage() != null) {
                                            cryptoBrokerBitmap =  getResizedBitmap(rotateBitmap(dialogCropImagee.getCroppedImage(), ExifInterface.ORIENTATION_NORMAL), 230, 230);
                                            Picasso.with(getActivity()).load(getImageUri(getActivity(), cryptoBrokerBitmap)).transform(new CircleTransform()).into(mBrokerImage);
                                        } else {
                                            cryptoBrokerBitmap = null;
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "The image selected is too small. Please select a photo with height and width of at least 192x192", Toast.LENGTH_LONG).show();
                               // cryptoBrokerBitmap = null;
                                // Toast.makeText(getActivity(), "The image selected is too small", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } catch (Exception e) {
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                        Toast.makeText(getActivity().getApplicationContext(), "Error loading the image", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case GALLERY_KITKAT_INTENT_CALLED:
                    Uri selectedImagee = data.getData();
                    // Check for the freshest data.
                    if(Build.VERSION.SDK_INT >= 23) {
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
                            cryptoBrokerBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImagee);
                            //cryptoBrokerBitmap = Bitmap.createScaledBitmap(cryptoBrokerBitmap, mBrokerImage.getWidth(), mBrokerImage.getHeight(), true);
                            if (cryptoBrokerBitmap.getWidth() >= 192 && cryptoBrokerBitmap.getHeight() >= 192) {
                                //cryptoBrokerBitmap = ImagesUtils.cropImage(cryptoBrokerBitmap);
                                final DialogCropImage dialogCropImagee = new DialogCropImage(getActivity(), appSession, null, cryptoBrokerBitmap);
                                dialogCropImagee.show();
                                dialogCropImagee.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        if(dialogCropImagee.getCroppedImage() != null){
                                            cryptoBrokerBitmap =  getResizedBitmap(rotateBitmap(dialogCropImagee.getCroppedImage(),ExifInterface.ORIENTATION_NORMAL),230,230);
                                            Picasso.with(getActivity()).load(getImageUri(getActivity(), cryptoBrokerBitmap)).transform(new CircleTransform()).into(mBrokerImage);
                                        }else{
                                            cryptoBrokerBitmap = null;
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "The image selected is too small. Please select a photo with height and width of at least 192x192", Toast.LENGTH_LONG).show();
                                //cryptoBrokerBitmap = null;
                                // Toast.makeText(getActivity(), "The image selected is too small", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                        Toast.makeText(getActivity().getApplicationContext(), "Error loading the image", Toast.LENGTH_SHORT).show();
                    }
            }
        }   getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);

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
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
    private void updateIdentityInBackDevice(String donde) throws CantGetChatIdentityException {
        String brokerNameText = mBrokerName.getText().toString();
        String state= mChatConnectionState.getText().toString();

        if (brokerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG).show();
        }
        if (cryptoBrokerBitmap == null) {
           // Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            cryptoBrokerBitmap = BitmapFactory.decodeByteArray(moduleManager.getIdentityChatUser().getImage(), 0, moduleManager.getIdentityChatUser().getImage().length);
            byte[] imgInBytes = ImagesUtils.toByteArray(cryptoBrokerBitmap);
            EditIdentityExecutor executor = null;
            try {
                executor = new EditIdentityExecutor(Session, moduleManager.getIdentityChatUser().getPublicKey(), brokerNameText, moduleManager.getIdentityChatUser().getImage(), state);
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("onClick")) {
//                            textViewChtTitle.setText(mBrokerName.getText());
//                            statusView.setText(mChatConnectionState.getText());
                            Toast.makeText(getActivity(), "Chat Identity Update.", Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                            //changeActivity(Activities.CHT_CHAT_CREATE_IDENTITY, appSession.getAppPublicKey());
                        }
                        break;
                }
            } catch (CHTException e) {
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));

            }
        }else {
            byte[] imgInBytes = ImagesUtils.toByteArray(cryptoBrokerBitmap);
            EditIdentityExecutor executor = null;
            try {
                executor = new EditIdentityExecutor(Session, moduleManager.getIdentityChatUser().getPublicKey(), brokerNameText, imgInBytes, moduleManager.getIdentityChatUser().getConnectionState());

                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("onClick")) {
                            //textViewChtTitle.setText(mBrokerName.getText());
                            Toast.makeText(getActivity(), "Chat Identity Update.", Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                           // changeActivity(Activities.CHT_CHAT_CREATE_IDENTITY, appSession.getAppPublicKey());
                        }
                        break;
                }
            } catch (CHTException e) {
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));

            }
        }
    }

    private void createNewIdentityInBackDevice(String donde) {
        String brokerNameText = mBrokerName.getText().toString();
        String identityConnectionNameText = mChatConnectionState.getText().toString();
        if (cryptoBrokerBitmap == null) {
            //Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            cryptoBrokerBitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.icon_profile);
        }
        if(identityConnectionNameText.length() == 0){
            identityConnectionNameText = "Available";
        }
        if (brokerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG).show();
        } else {

                byte[] imgInBytes = ImagesUtils.toByteArray(cryptoBrokerBitmap);
                CreateChatIdentityExecutor executor = null;
                try {
                    executor = new CreateChatIdentityExecutor(Session,brokerNameText, imgInBytes, identityConnectionNameText);

                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("onClick")) {
                            Toast.makeText(getActivity(), "Chat Identity Created.", Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                            //changeActivity(Activities.CHT_CHAT_CREATE_IDENTITY, appSession.getAppPublicKey());
                        }
                        break;
                }      } catch (CantGetChatIdentityException e) {
                    errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));


            }
        }
    }

    public boolean ExistIdentity() throws CHTException {
        try {
            if (!moduleManager.getIdentityChatUser().getAlias().isEmpty()) {
                Log.i("CHT EXIST IDENTITY", "TRUE");
                return true;
            }
        }
        catch(Exception e){
            return false;
        }
        Log.i("CHT EXIST IDENTITY", "FALSE");
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void dispatchTakePictureIntent() {
        // Check permission for CAMERA
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                if( getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED ){
                    getActivity().requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_IMAGE_CAPTURE);
                }else {
                    getActivity().requestPermissions(
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_IMAGE_CAPTURE);
                }
            }else{
                if (checkWriteExternalPermission()) {
                    Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
                    chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    imageToUploadUri = Uri.fromFile(f);
                    startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
                }else{
                    Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        }else{

                Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
                chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                imageToUploadUri = Uri.fromFile(f);
                startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
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
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

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
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

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

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
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
            startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean checkWriteExternalPermission()
    {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    private boolean checkCameraPermission()
    {
        String permission = "android.permission.CAMERA";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}