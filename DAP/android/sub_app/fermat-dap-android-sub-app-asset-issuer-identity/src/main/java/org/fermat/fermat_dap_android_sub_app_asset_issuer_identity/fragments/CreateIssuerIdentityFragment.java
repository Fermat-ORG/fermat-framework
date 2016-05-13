package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.session.IssuerIdentitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.session.SessionConstants;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantCreateNewIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_identity.IssuerIdentitySettings;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateIssuerIdentityFragment extends AbstractFermatFragment {

    private static final String TAG = "AssetIssuerIdentity";

    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private byte[] brokerImageByteArray;

    private IdentityAssetIssuerManager moduleManager;
    private ErrorManager errorManager;

    private Button createButton;
    private EditText mIdentityName;
    private ImageView mIdentityImage;

    IssuerIdentitySubAppSession issuerIdentitySubAppSession;
    private IdentityAssetIssuer identitySelected;
    private boolean isUpdate = false;

    SettingsManager<IssuerIdentitySettings> settingsManager;
    IssuerIdentitySettings issuerIdentitySettings = null;

    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;

    ExecutorService executorService;

    public static CreateIssuerIdentityFragment newInstance() {
        return new CreateIssuerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executorService = Executors.newFixedThreadPool(3);

        try {
            issuerIdentitySubAppSession = (IssuerIdentitySubAppSession) appSession;
            moduleManager = issuerIdentitySubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            setHasOptionsMenu(true);

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    settingsManager = appSession.getModuleManager().getSettingsManager();

                    try {
                        if (appSession.getAppPublicKey() != null) {
                            issuerIdentitySettings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
                        }
                    } catch (Exception e) {
                        issuerIdentitySettings = null;
                    }

                    try {
                        if (issuerIdentitySettings == null) {
                            issuerIdentitySettings = new IssuerIdentitySettings();
                            issuerIdentitySettings.setIsPresentationHelpEnabled(true);
                            if (appSession.getAppPublicKey() != null) {
                                settingsManager.persistSettings(appSession.getAppPublicKey(), issuerIdentitySettings);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_dap_create_issuer_identity, container, false);
        initViews(rootLayout);
        setUpIdentity();

        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable() {
            public void run() {
                if (issuerIdentitySettings != null) {
                    if (issuerIdentitySettings.isPresentationHelpEnabled()) {
                        setUpPresentation(false);
                    }
                }
            }
        }, 500);

        return rootLayout;
    }

    private void setUpPresentation(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_issuer_identity)
                    .setIconRes(R.drawable.asset_issuer)
                    .setVIewColor(R.color.dap_identity_issuer_view_color)
                    .setTitleTextColor(R.color.dap_identity_issuer_view_color)
                    .setSubTitle(R.string.dap_issuer_identity_welcome_subTitle)
                    .setBody(R.string.dap_issuer_identity_welcome_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.dap_issuer_button);
        mIdentityName = (EditText) layout.findViewById(R.id.dap_issuer_name);
        mIdentityImage = (ImageView) layout.findViewById(R.id.dap_issuer_image);

        createButton.setText((!isUpdate) ? "Create" : "Update");

        mIdentityName.requestFocus();
        registerForContextMenu(mIdentityImage);

        mIdentityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "Entrando en mIdentityImage.setOnClickListener");
                getActivity().openContextMenu(mIdentityImage);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "Entrando en createButton.setOnClickListener");
                createNewIdentity();

            }
        });
    }

    private void publishResult(final int resultKey) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (resultKey) {
                    case CREATE_IDENTITY_SUCCESS:
//                        changeActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY.getCode(), appSession.getAppPublicKey());
                        if (!isUpdate) {
                            Toast.makeText(getActivity(), "Identity created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                        }
                        getActivity().onBackPressed();
                        break;
                    case CREATE_IDENTITY_FAIL_MODULE_EXCEPTION:
                        Toast.makeText(getActivity(), "Error al crear la identidad", Toast.LENGTH_LONG).show();
                        break;
                    case CREATE_IDENTITY_FAIL_NO_VALID_DATA:
                        Toast.makeText(getActivity(), "La data no es valida", Toast.LENGTH_LONG).show();
                        break;
                    case CREATE_IDENTITY_FAIL_MODULE_IS_NULL:
                        Toast.makeText(getActivity(), "No se pudo acceder al module manager, es null", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        executorService.shutdown();
        super.onDestroy();
    }

    private void setUpIdentity() {
        try {

            identitySelected = (IdentityAssetIssuer) issuerIdentitySubAppSession.getData(SessionConstants.IDENTITY_SELECTED);

            if (identitySelected != null) {
                loadIdentity();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ActiveActorIdentityInformation activeActorIdentityInformation = null;
                        try {
                            activeActorIdentityInformation = moduleManager.getSelectedActorIdentity();
                        } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
                            e.printStackTrace();
                        }
                        if (activeActorIdentityInformation != null) {
                            identitySelected = (IdentityAssetIssuer) activeActorIdentityInformation;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (identitySelected != null) {
                                                                loadIdentity();
                                                                isUpdate = true;
                                                                createButton.setText("Save changes");
                                                            }
                                                        }
                                                    }
                        );

                    }
                }).start();

            }
//            } else {
//                List<IdentityAssetIssuer> lst = moduleManager.getIdentityAssetIssuersFromCurrentDeviceUser();
//                if (!lst.isEmpty()) {
//                    identitySelected = lst.get(0);
//                }
//                if (identitySelected != null) {
//                    loadIdentity();
//                    isUpdate = true;
//                    createButton.setText("Save changes");
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadIdentity() {
        if (identitySelected.getImage() != null) {
            Bitmap bitmap = null;
            if (identitySelected.getImage().length > 0) {
                bitmap = BitmapFactory.decodeByteArray(identitySelected.getImage(), 0, identitySelected.getImage().length);
//                bitmap = Bitmap.createScaledBitmap(bitmap, mBrokerImage.getWidth(), mBrokerImage.getHeight(), true);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.asset_issuer_identity);

                //Picasso.with(getActivity()).load(R.drawable.profile_image).into(mBrokerImage);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            brokerImageByteArray = toByteArray(bitmap);
            mIdentityImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
        }
        mIdentityName.setText(identitySelected.getAlias());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            ImageView pictureView = mIdentityImage;
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
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                            brokerImageByteArray = toByteArray(imageBitmap);
                            updateProfileImage = true;
                            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(mIdentityImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error Load Image", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

//            if (pictureView != null && imageBitmap != null)
//                pictureView.setImageDrawable(new BitmapDrawable(getResources(), imageBitmap));
//                pictureView.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), imageBitmap));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Choose mode");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");

        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (!contextMenuInUse) {
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

    /**
     * Crea una nueva identidad para un crypto broker
     *
     * @return key con el resultado de la operacion:<br/><br/>
     * <code>CREATE_IDENTITY_SUCCESS</code>: Se creo exitosamente una identidad <br/>
     * <code>CREATE_IDENTITY_FAIL_MODULE_EXCEPTION</code>: Se genero una excepcion cuando se ejecuto el metodo para crear la identidad en el Module Manager <br/>
     * <code>CREATE_IDENTITY_FAIL_MODULE_IS_NULL</code>: No se tiene una referencia al Module Manager <br/>
     * <code>CREATE_IDENTITY_FAIL_NO_VALID_DATA</code>: Los datos ingresados para crear la identidad no son validos (faltan datos, no tiene el formato correcto, etc) <br/>
     */
    private int createNewIdentity() {

        final String brokerNameText = mIdentityName.getText().toString();
        boolean dataIsValid = validateIdentityData(brokerNameText, brokerImageByteArray);

        if (dataIsValid) {
            if (moduleManager != null) {
                try {
                    if (!isUpdate) {
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    moduleManager.createNewIdentityAssetIssuer(brokerNameText, (brokerImageByteArray == null) ? convertImage(R.drawable.asset_issuer_identity) : brokerImageByteArray);
                                    publishResult(CREATE_IDENTITY_SUCCESS);
                                } catch (CantCreateNewIdentityAssetIssuerException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (updateProfileImage)
                                        moduleManager.updateIdentityAssetIssuer(identitySelected.getPublicKey(), brokerNameText, brokerImageByteArray);
                                    else
                                        moduleManager.updateIdentityAssetIssuer(identitySelected.getPublicKey(), brokerNameText, identitySelected.getImage());
                                    publishResult(CREATE_IDENTITY_SUCCESS);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                }
                return CREATE_IDENTITY_SUCCESS;
            }
            return CREATE_IDENTITY_FAIL_MODULE_IS_NULL;
        }
        return CREATE_IDENTITY_FAIL_NO_VALID_DATA;

    }

    private byte[] convertImage(int resImage) {
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void dispatchTakePictureIntent() {
        Log.i(TAG, "Opening Camera app to take the picture...");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void loadImageFromGallery() {
        Log.i(TAG, "Loading Image from Gallery...");

        Intent loadImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }

    private boolean validateIdentityData(String brokerNameText, byte[] brokerImageBytes) {
        if (brokerNameText.isEmpty())
            return false;
        if (brokerImageBytes == null)
            return true;
        if (brokerImageBytes.length > 0)
            return true;

        return true;
    }

    /**
     * Bitmap to byte[]
     *
     * @param bitmap Bitmap
     * @return byte array
     */
    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dap_issuer_identity_menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            if (item.getItemId() == R.id.action_identity_issuer_help) {
                setUpPresentation(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Identity Issuer system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
