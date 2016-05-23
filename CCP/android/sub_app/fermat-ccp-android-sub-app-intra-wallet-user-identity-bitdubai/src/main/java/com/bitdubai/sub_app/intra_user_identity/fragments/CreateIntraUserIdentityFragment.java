package com.bitdubai.sub_app.intra_user_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraUserIdentitySettings;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantCreateNewIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserModuleIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.intra_user_identity.R;
import com.bitdubai.sub_app.intra_user_identity.common.popup.PresentationIntraUserIdentityDialog;
import com.bitdubai.sub_app.intra_user_identity.session.IntraUserIdentitySubAppSession;
import com.bitdubai.sub_app.intra_user_identity.session.SessionConstants;
import com.bitdubai.sub_app.intra_user_identity.util.CommonLogger;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateIntraUserIdentityFragment extends AbstractFermatFragment<IntraUserIdentitySubAppSession,ResourceProviderManager> {


    private static final String TAG = "CreateIdentity";

    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    private byte[] brokerImageByteArray;
    private ErrorManager errorManager;
    private Button createButton;
    private EditText mBrokerName;
    private ImageView mBrokerImage;
    private RelativeLayout relativeLayout;
    private Menu menuHelp;
    private IntraUserModuleIdentity identitySelected;
    private boolean isUpdate = false;
    private EditText mBrokerPhrase;
    IntraUserIdentitySettings intraUserIdentitySettings = null;
    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;

    ExecutorService executorService;

    public static CreateIntraUserIdentityFragment newInstance() {
        return new CreateIntraUserIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executorService = Executors.newFixedThreadPool(3);
        try {

            errorManager = appSession.getErrorManager();
            setHasOptionsMenu(true);
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    try {
                        if (appSession.getAppPublicKey()!= null){
                            intraUserIdentitySettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
                        }else{
                            //TODO: Joaquin: Lo estoy poniendo con un public key hardcoded porque en este punto no posee public key.
                            intraUserIdentitySettings = appSession.getModuleManager().loadAndGetSettings("123456789");
                        }

                    } catch (Exception e) {
                        intraUserIdentitySettings = null;
                    }

                    try {
                        if (intraUserIdentitySettings == null) {
                            intraUserIdentitySettings = new IntraUserIdentitySettings();
                            intraUserIdentitySettings.setIsPresentationHelpEnabled(true);
                            if (appSession.getAppPublicKey() != null) {
                                appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), intraUserIdentitySettings);
                            } else {
                                appSession.getModuleManager().persistSettings("123456789", intraUserIdentitySettings);
                            }

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });


//            if(moduleManager.getAllIntraWalletUsersFromCurrentDeviceUser().isEmpty()){
//                moduleManager.createNewIntraWalletUser("John Doe", null);
//            }
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_create_intra_user_identity, container, false);
        initViews(rootLayout);
        setUpIdentity();
        SharedPreferences pref = getActivity().getSharedPreferences("dont show dialog more", Context.MODE_PRIVATE);
//        if (!pref.getBoolean("isChecked", false)) {
//            PresentationIntraUserIdentityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserIdentityDialog(getActivity(), null, null);
//            presentationIntraUserCommunityDialog.show();
//        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (intraUserIdentitySettings != null) {
                    if (intraUserIdentitySettings.isPresentationHelpEnabled()) {
                        if(getActivity()!=null) {
                            PresentationIntraUserIdentityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserIdentityDialog(getActivity(), appSession, null, appSession.getModuleManager());
                            presentationIntraUserCommunityDialog.show();
                        }
                    }
                }
            }
        }, 5000);


        return rootLayout;
    }


    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.create_crypto_broker_button);
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        mBrokerPhrase = (EditText) layout.findViewById(R.id.crypto_broker_phrase);
        mBrokerImage = (ImageView) layout.findViewById(R.id.img_photo);
        relativeLayout = (RelativeLayout) layout.findViewById(R.id.user_image);



        createButton.setText((!isUpdate) ? "Create" : "Update");

        mBrokerName.requestFocus();
        registerForContextMenu(mBrokerImage);

        mBrokerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "Entrando en mBrokerImage.setOnClickListener");
                getActivity().openContextMenu(mBrokerImage);
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

    private void publishResult(final int resultKey){
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
        super.onDestroy();
        executorService.shutdown();
    }

    private void setUpIdentity() {
        try {

            identitySelected = (IntraUserModuleIdentity) appSession.getData(SessionConstants.IDENTITY_SELECTED);


            if (identitySelected != null) {
                loadIdentity();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ActiveActorIdentityInformation activeActorIdentityInformation = null;
                        try {
                            activeActorIdentityInformation = appSession.getModuleManager().getSelectedActorIdentity();
                        } catch (CantGetSelectedActorIdentityException e) {
                            e.printStackTrace();
                        } catch (ActorIdentityNotSelectedException e) {
                            e.printStackTrace();
                        }
                        if(activeActorIdentityInformation!=null){
                            identitySelected = (IntraUserModuleIdentity) activeActorIdentityInformation;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadIdentity(){
        if (identitySelected.getImage() != null) {
            Bitmap bitmap = null;
            if (identitySelected.getImage().length > 0) {
                bitmap = BitmapFactory.decodeByteArray(identitySelected.getImage(), 0, identitySelected.getImage().length);
//                bitmap = Bitmap.createScaledBitmap(bitmap, mBrokerImage.getWidth(), mBrokerImage.getHeight(), true);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_male);

                //Picasso.with(getActivity()).load(R.drawable.profile_image).into(mBrokerImage);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            brokerImageByteArray = toByteArray(bitmap);
            mBrokerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
        }
        mBrokerName.setText(identitySelected.getAlias());
        mBrokerPhrase.setText(identitySelected.getPhrase());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            contextMenuInUse = true;
            Bitmap imageBitmap = null;
            ImageView pictureView = mBrokerImage;

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
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, mBrokerImage.getWidth(), mBrokerImage.getHeight(), true);
                            brokerImageByteArray = toByteArray(imageBitmap);
                            updateProfileImage = true;
                            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(mBrokerImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

//            final Bitmap finalImageBitmap = imageBitmap;
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (mBrokerImage != null && finalImageBitmap != null) {
////                        mBrokerImage.setImageDrawable(new BitmapDrawable(getResources(), finalImageBitmap));
//                        mBrokerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), finalImageBitmap));
//                    }
//                }
//            });

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

        final String brokerNameText = mBrokerName.getText().toString();
        String brokerPhraseText = "";

        if (!mBrokerPhrase.getText().toString().isEmpty()){
             brokerPhraseText = mBrokerPhrase.getText().toString();
        }else{
            brokerPhraseText = "Available";
        }

        boolean dataIsValid = validateIdentityData(brokerNameText, brokerPhraseText, brokerImageByteArray);

        if (dataIsValid) {
            if (appSession.getModuleManager() != null) {
                try {
                    if (!isUpdate) {
                        final String finalBrokerPhraseText = brokerPhraseText;
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    appSession.getModuleManager().createNewIntraWalletUser(brokerNameText, finalBrokerPhraseText, (brokerImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : brokerImageByteArray);
                                    publishResult(CREATE_IDENTITY_SUCCESS);
                                } catch (CantCreateNewIntraUserIdentityException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    else {
                        final String finalBrokerPhraseText1 = brokerPhraseText;
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (updateProfileImage)
                                        appSession.getModuleManager().updateIntraUserIdentity(identitySelected.getPublicKey(), brokerNameText, finalBrokerPhraseText1, brokerImageByteArray);
                                    else
                                        appSession.getModuleManager().updateIntraUserIdentity(identitySelected.getPublicKey(), brokerNameText, finalBrokerPhraseText1, identitySelected.getImage());
                                    publishResult(CREATE_IDENTITY_SUCCESS);
                                }catch (Exception e){
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

    private byte[] convertImage(int resImage){
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
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

    private boolean validateIdentityData(String brokerNameText, String brokerPhraseText, byte[] brokerImageBytes) {
        if (brokerNameText.isEmpty())
            return false;
        if (brokerPhraseText.isEmpty())
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


    public void showDialog(){
        if(getActivity()!=null) {
            PresentationIntraUserIdentityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserIdentityDialog(getActivity(), appSession, null, appSession.getModuleManager());
            presentationIntraUserCommunityDialog.show();
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.menu_main, menu);

        try {
            menu.add(1, 99, 1, "help").setIcon(R.drawable.help_icon)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            final MenuItem action_help = menu.findItem(R.id.action_help);
            menu.findItem(R.id.action_help).setVisible(true);
            action_help.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    menu.findItem(R.id.action_help).setVisible(false);
                    return false;
                }
            });

        } catch (Exception e) {

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == 99)
                showDialog();


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
