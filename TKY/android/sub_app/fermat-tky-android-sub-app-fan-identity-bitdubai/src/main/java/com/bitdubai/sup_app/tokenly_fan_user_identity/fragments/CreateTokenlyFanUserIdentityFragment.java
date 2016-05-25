package com.bitdubai.sup_app.tokenly_fan_user_identity.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.FanIdentitiesList;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanPreferenceSettings;
import com.bitdubai.sub_app.fan_identity.R;
import com.bitdubai.sup_app.tokenly_fan_user_identity.popup.PresentationTokenlyFanUserIdentityDialog;
import com.bitdubai.sup_app.tokenly_fan_user_identity.session.SessionConstants;
import com.bitdubai.sup_app.tokenly_fan_user_identity.session.TokenlyFanUserIdentitySubAppSession;
import com.bitdubai.sup_app.tokenly_fan_user_identity.util.CommonLogger;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 22/03/16.
 */
public class CreateTokenlyFanUserIdentityFragment extends AbstractFermatFragment {
    private static final String TAG = "CreateTokenlyFanIdentity";
    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    private TokenlyFanUserIdentitySubAppSession tokenlyFanUserIdentitySubAppSession;
    private byte[] fanImageByteArray;
    private TokenlyFanIdentityManagerModule moduleManager;
    private ErrorManager errorManager;
    private Button createButton;
    private EditText mFanExternalUserName;
    private ImageView fanImage;
    private RelativeLayout relativeLayout;
    private Menu menuHelp;
    private Fan identitySelected;
    private boolean isUpdate = false;
    private EditText mFanExternalPassword;
    private Spinner mFanExternalPlatform;
    //private SettingsManager<TokenlyFanPreferenceSettings> settingsManager;
    private TokenlyFanPreferenceSettings tokenlyFanPreferenceSettings = null;
    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;
    private boolean authenticationSuccessful = false;
    private boolean isWaitingForResponse = false;
    private ProgressDialog tokenlyRequestDialog;
    //private View WarningCircle;
    //private TextView WarningLabel;
    private String WarningColor = "#DF0101";
    private String NormalColor  =  "#23056A";
    private View buttonCam;

    private TextView UserNameLabel;
    private TextView PassWordLabel;

    private Handler handler;

    public static CreateTokenlyFanUserIdentityFragment newInstance(){
        return new CreateTokenlyFanUserIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            tokenlyFanUserIdentitySubAppSession = (TokenlyFanUserIdentitySubAppSession) appSession;
            moduleManager = tokenlyFanUserIdentitySubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            setHasOptionsMenu(false);
            //settingsManager = tokenlyFanUserIdentitySubAppSession.getModuleManager().;

            try {
                if (tokenlyFanUserIdentitySubAppSession.getAppPublicKey()!= null){
                    tokenlyFanPreferenceSettings = moduleManager.loadAndGetSettings(tokenlyFanUserIdentitySubAppSession.getAppPublicKey());
                }else{
                    //TODO: Joaquin: Lo estoy poniendo con un public key hardcoded porque en este punto no posee public key.
                    tokenlyFanPreferenceSettings = moduleManager.loadAndGetSettings("123456789");
                }

            } catch (Exception e) {
                tokenlyFanPreferenceSettings = null;
            }

            if (tokenlyFanPreferenceSettings == null) {
                tokenlyFanPreferenceSettings = new TokenlyFanPreferenceSettings();
                tokenlyFanPreferenceSettings.setIsPresentationHelpEnabled(false);
                if(moduleManager != null){
                    if (tokenlyFanUserIdentitySubAppSession.getAppPublicKey()!=null){
                        moduleManager.persistSettings(tokenlyFanUserIdentitySubAppSession.getAppPublicKey(), tokenlyFanPreferenceSettings);
                    }else{
                        moduleManager.persistSettings("123456789", tokenlyFanPreferenceSettings);
                    }
                }
            }

//            if(moduleManager.getAllIntraWalletUsersFromCurrentDeviceUser().isEmpty()){
//                moduleManager.createNewIntraWalletUser("John Doe", null);
//            }
        } catch (Exception ex) {
            errorManager.reportUnexpectedSubAppException(SubApps.TKY_FAN_IDENTITY_SUB_APP, UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT,ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_create_tokenly_fan_user_identity, container, false);
        initViews(rootLayout);
        setUpIdentity();
       // SharedPreferences pref = getActivity().getSharedPreferences("dont show dialog more", Context.MODE_PRIVATE);
//        if (!pref.getBoolean("isChecked", false)) {
//            PresentationTokenlyFanUserIdentityDialog presentationIntraUserCommunityDialog = new PresentationTokenlyFanUserIdentityDialog(getActivity(), null, null);
//            presentationIntraUserCommunityDialog.show();
//        }

//        if (tokenlyFanPreferenceSettings.isHomeTutorialDialogEnabled()) {
//            PresentationTokenlyFanUserIdentityDialog presentationTokenlyFanUserIdentityDialog = new PresentationTokenlyFanUserIdentityDialog(getActivity(),tokenlyFanUserIdentitySubAppSession, null,moduleManager);
//            presentationTokenlyFanUserIdentityDialog.show();
//        }

        return rootLayout;
    }

    public void showDialog(){
        PresentationTokenlyFanUserIdentityDialog presentationTokenlyFanUserIdentityDialog = new PresentationTokenlyFanUserIdentityDialog(getActivity(),tokenlyFanUserIdentitySubAppSession, null,moduleManager);
        presentationTokenlyFanUserIdentityDialog.show();
    }

    private void setUpIdentity() {
        try {
            List<Fan> lst = new ArrayList<>();
            identitySelected = (Fan) tokenlyFanUserIdentitySubAppSession.getData(SessionConstants.IDENTITY_SELECTED);

            if (identitySelected != null) {
                loadIdentity();
            } else {
                FanIdentitiesList fanIdentitiesList = moduleManager.listIdentitiesFromCurrentDeviceUser();
                lst = fanIdentitiesList.getFanIdentitiesList();
                if(lst!=null&&!lst.isEmpty()){
                    identitySelected = lst.get(0);
                }
                if (identitySelected != null) {
                    loadIdentity();
                    isUpdate = true;
                    buttonCam.setBackgroundResource(R.drawable.boton_editar);
                    createButton.setText("Save changes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void loadIdentity(){
        if (identitySelected.getProfileImage() != null) {
            Bitmap bitmap = null;
            if (identitySelected.getProfileImage().length > 0) {
                bitmap = BitmapFactory.decodeByteArray(identitySelected.getProfileImage(), 0, identitySelected.getProfileImage().length);
//                bitmap = Bitmap.createScaledBitmap(bitmap, fanImage.getWidth(), fanImage.getHeight(), true);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_male);

                //Picasso.with(getActivity()).load(R.drawable.profile_image).into(fanImage);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            fanImageByteArray = toByteArray(bitmap);
            fanImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
        }
        mFanExternalUserName.setText(identitySelected.getUsername());
      //  mFanExternalPassword.setText(identitySelected.getApiToken());
        List<String> arraySpinner = ExternalPlatform.getArrayItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mFanExternalPlatform.setAdapter(adapter);
        ExternalPlatform[] externalPlatforms = ExternalPlatform.values();
        for (int i=0; i<externalPlatforms.length;i++){
            if(externalPlatforms[i] == identitySelected.getExternalPlatform()){
                mFanExternalPlatform.setSelection(i);
                break;
            }
        }
    }
    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.create_tokenly_fan_identity);
        mFanExternalUserName = (EditText) layout.findViewById(R.id.external_username);
        mFanExternalPassword = (EditText) layout.findViewById(R.id.tokenly_access_password);
        fanImage = (ImageView) layout.findViewById(R.id.tokenly_Fan_image);
        mFanExternalPlatform = (Spinner) layout.findViewById(R.id.external_platform);
        //relativeLayout = (RelativeLayout) layout.findViewById(R.id.user_image);
        createButton.setText((!isUpdate) ? "Create" : "Update");
        mFanExternalUserName.requestFocus();
        List<String> arraySpinner = ExternalPlatform.getArrayItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mFanExternalPlatform.setAdapter(adapter);
        mFanExternalUserName.requestFocus();
        mFanExternalPlatform.setVisibility(View.GONE);

        //WarningCircle = (View) layout.findViewById(R.id.warning_cirlcle);
        //WarningCircle.setVisibility(View.GONE);

        //WarningLabel = (TextView) layout.findViewById(R.id.warning_label);
        //WarningLabel.setVisibility(View.GONE);

        buttonCam = (View) layout.findViewById(R.id.boton_cam);

        UserNameLabel = (TextView) layout.findViewById(R.id.external_username_label);

        PassWordLabel = (TextView) layout.findViewById(R.id.tokenly_acces_password_label);



        registerForContextMenu(fanImage);
        fanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WarningLabel.setVisibility(View.GONE);
                //WarningCircle.setVisibility(View.GONE);
                CommonLogger.debug(TAG, "Entrando en fanImage.setOnClickListener");
                getActivity().openContextMenu(fanImage);
               // buttonCam.setBackgroundResource(R.drawable.boton_editar);
            }
        });





        mFanExternalUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserNameLabel.setTextColor(Color.parseColor(NormalColor));

            }
        });

        mFanExternalPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassWordLabel.setTextColor(Color.parseColor(NormalColor));

            }
        });




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "Entrando en createButton.setOnClickListener");
                tokenlyRequestDialog = new ProgressDialog(getActivity());
                tokenlyRequestDialog.setMessage("Please Wait");
                tokenlyRequestDialog.setTitle("Connecting to Tokenly");
                tokenlyRequestDialog.show();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isWaitingForResponse) {
                            int resultKey = createNewIdentity();
                            switch (resultKey) {
                                case CREATE_IDENTITY_SUCCESS:
                                    break;
                                case CREATE_IDENTITY_FAIL_MODULE_EXCEPTION:
                                    Toast.makeText(getActivity(), "Create identity Error", Toast.LENGTH_LONG).show();
                                    break;
                                case CREATE_IDENTITY_FAIL_NO_VALID_DATA:
                                    Toast.makeText(getActivity(), "Invalid data", Toast.LENGTH_LONG).show();
                                    break;
                                case CREATE_IDENTITY_FAIL_MODULE_IS_NULL:
                                    Toast.makeText(getActivity(), "Could not access module manager, it's null", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } else {
                            tokenlyRequestDialog.dismiss();
                            Toast.makeText(getActivity(), "Identity created", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        configureToolbar();
    }





    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }







    /**
     * Crea una nueva identidad para un Fan de tokenly
     *
     * @return key con el resultado de la operacion:<br/><br/>
     * <code>CREATE_IDENTITY_SUCCESS</code>: Se creo exitosamente una identidad <br/>
     * <code>CREATE_IDENTITY_FAIL_MODULE_EXCEPTION</code>: Se genero una excepcion cuando se ejecuto el metodo para crear la identidad en el Module Manager <br/>
     * <code>CREATE_IDENTITY_FAIL_MODULE_IS_NULL</code>: No se tiene una referencia al Module Manager <br/>
     * <code>CREATE_IDENTITY_FAIL_NO_VALID_DATA</code>: Los datos ingresados para crear la identidad no son validos (faltan datos, no tiene el formato correcto, etc) <br/>
     */
    private int createNewIdentity() {

        String fanExternalName = mFanExternalUserName.getText().toString();
        String fanPassword = "";
        if (!mFanExternalPassword.getText().toString().isEmpty()){
            fanPassword = mFanExternalPassword.getText().toString();
        }
        ExternalPlatform externalPlatform = ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM;
        if(mFanExternalPlatform.isSelected()){
            externalPlatform = ExternalPlatform.getExternalPlatformByLabel(mFanExternalPlatform.getSelectedItem().toString());
        }

        boolean dataIsValid = validateIdentityData(fanExternalName, fanPassword, fanImageByteArray, externalPlatform);


        if (dataIsValid) {
            if (moduleManager != null) {
                try {
                    if (!isUpdate)
                        //moduleManager.createFanIdentity(fanExternalName,(fanImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : fanImageByteArray,fanPassword,externalPlatform) ;
                        new ManageIdentity(fanExternalName,fanPassword,externalPlatform, ManageIdentity.CREATE_IDENTITY).execute();
                    else
                    if(updateProfileImage)
                        //moduleManager.updateFanIdentity(fanExternalName, fanPassword, identitySelected.getId(), identitySelected.getPublicKey(), fanImageByteArray, externalPlatform);
                        new ManageIdentity(fanExternalName,fanPassword,externalPlatform, ManageIdentity.UPDATE_IMAGE_IDENTITY).execute();
                    else
                        //moduleManager.updateFanIdentity(fanExternalName,fanPassword,identitySelected.getId(), identitySelected.getPublicKey(), identitySelected.getProfileImage(),externalPlatform);
                        new ManageIdentity(fanExternalName,fanPassword,externalPlatform, ManageIdentity.UPDATE_IDENTITY).execute();
                } /*catch (CantCreateFanIdentityException | FanIdentityAlreadyExistsException |CantUpdateFanIdentityException e) {
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                } */catch (Exception e){
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                    e.printStackTrace();
                }
                return CREATE_IDENTITY_SUCCESS;
            }
            return CREATE_IDENTITY_FAIL_MODULE_IS_NULL;
        }
        tokenlyRequestDialog.dismiss();
        return CREATE_IDENTITY_FAIL_NO_VALID_DATA;

    }

    private byte[] convertImage(int resImage){
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    private void dispatchTakePictureIntent() {
      //  Log.i(TAG, "Opening Camera app to take the picture...");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void loadImageFromGallery() {
      //  Log.i(TAG, "Loading Image from Gallery...");

        Intent loadImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }

    private boolean validateIdentityData(String fanExternalName, String fanPassWord, byte[] fanImageBytes, ExternalPlatform externalPlatform) {

        ShowWarnings(fanExternalName,fanPassWord,fanImageBytes);
        if (fanExternalName.isEmpty())
            return false;
        if (fanPassWord.isEmpty())
            return false;
        /*if (fanImageBytes == null)
            return false;
        if (fanImageBytes.length > 0)
            return true;*/
//        if(externalPlatform != null)
//            return  true;
        return true;
    }

    private void ShowWarnings(String fanExternalName,String fanPassWord, byte[] fanImageBytes) {



        if (fanExternalName.isEmpty()){
            UserNameLabel.setTextColor(Color.parseColor(WarningColor));
            mFanExternalUserName.setHintTextColor(Color.parseColor(WarningColor));
        }

        if (fanPassWord.isEmpty()){
            PassWordLabel.setTextColor(Color.parseColor(WarningColor));
            mFanExternalPassword.setHintTextColor(Color.parseColor(WarningColor));
        }

        /*if (fanImageBytes == null){
                WarningLabel.setVisibility(View.VISIBLE);
           // WarningCircle.setVisibility(View.VISIBLE);
        }*/


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            ImageView pictureView = fanImage;
            contextMenuInUse = true;

            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    updateProfileImage = true;
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            buttonCam.setBackgroundResource(R.drawable.boton_editar);
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                            fanImageByteArray = toByteArray(imageBitmap);
                            updateProfileImage = true;
                            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(fanImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error loading picture", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            if (pictureView != null && imageBitmap != null)
                pictureView.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), imageBitmap));

        }
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
    public void onDestroy() {
        super.onDestroy();
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

    /**
     * Experimental code to get http responses from android.
     * The main idea is make the cURL request in android background with an AsyncTask
     */
    private class ManageIdentity extends AsyncTask {
        String fanExternalName;
        String fanPassword;
        ExternalPlatform externalPlatform;
        int identityAction;
        public static final int CREATE_IDENTITY = 0;
        public static final int UPDATE_IDENTITY = 1;
        public static final int UPDATE_IMAGE_IDENTITY = 2;
        private Fan fan;
        public ManageIdentity(
                String fanExternalName,
                String fanPassword,
                ExternalPlatform externalPlatform,
                int identityAction
                ) {
            this.fanExternalName = fanExternalName;
            this.fanPassword = fanPassword;
            this.externalPlatform = externalPlatform;
            this.identityAction = identityAction;
            authenticationSuccessful = true;
            isWaitingForResponse = true;
        }
        @Override
        protected void onPostExecute(Object result) {

            if(!authenticationSuccessful){
                //I'll launch a toast
                tokenlyRequestDialog.dismiss();
                Toast.makeText(
                        getActivity(),
                        "Authentication credentials are invalid.",
                        Toast.LENGTH_SHORT).show();
            }
            if(Validate.isObjectNull(fan)){
                tokenlyRequestDialog.dismiss();
                Toast.makeText(getActivity(), "The tokenly authentication failed.", Toast.LENGTH_SHORT).show();
            }else{
                if(isUpdate){
                    tokenlyRequestDialog.dismiss();
                    Toast.makeText(getActivity(), "Identity updated", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }else{
                    tokenlyRequestDialog.dismiss();
                    Toast.makeText(getActivity(), "Identity created", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }
            isWaitingForResponse = false;
        }
        @Override
        protected Object doInBackground(Object... arg0) {
            try{
                switch (identityAction){
                    case CREATE_IDENTITY:
                        fan = createIdentity(fanExternalName, fanPassword, externalPlatform);
                        break;
                    case UPDATE_IDENTITY:
                        fan = updateIdentity(fanExternalName, fanPassword, externalPlatform);
                        break;
                    case UPDATE_IMAGE_IDENTITY:
                        fan = updateIdentityImage(fanExternalName,fanPassword,externalPlatform);
                        //Toast.makeText(getActivity(), "Identity updated", Toast.LENGTH_SHORT).show();
                        break;
                }

            } catch (FanIdentityAlreadyExistsException e) {
                errorManager.reportUnexpectedUIException(
                        UISource.VIEW,
                        UnexpectedUIExceptionSeverity.UNSTABLE,
                        e);
            } catch (CantCreateFanIdentityException e) {
                errorManager.reportUnexpectedUIException(
                        UISource.VIEW,
                        UnexpectedUIExceptionSeverity.UNSTABLE,
                        e);
            } catch (CantUpdateFanIdentityException e) {
                errorManager.reportUnexpectedUIException(
                        UISource.VIEW,
                        UnexpectedUIExceptionSeverity.UNSTABLE,
                        e);
            } catch (WrongTokenlyUserCredentialsException e) {
                //We are not going to report this exception
                authenticationSuccessful=false;
                //System.out.println("TKY WTUCE:" + e);

            }
            return null;
        }
    }

    private Fan createIdentity(
            String fanExternalName,
            String fanPassword,
            ExternalPlatform externalPlatform) throws
            CantCreateFanIdentityException,
            FanIdentityAlreadyExistsException,
            WrongTokenlyUserCredentialsException {
        return moduleManager.createFanIdentity(
                fanExternalName,(fanImageByteArray == null) ? convertImage(R.drawable.ic_profile_tokenly) : fanImageByteArray,
                fanPassword,
                externalPlatform) ;
    }

    private Fan updateIdentity(
            String fanExternalName,
            String fanPassword,
            ExternalPlatform externalPlatform) throws
            CantUpdateFanIdentityException,
            WrongTokenlyUserCredentialsException {
        return moduleManager.updateFanIdentity(
                fanExternalName,
                fanPassword, identitySelected.getId(),
                identitySelected.getPublicKey(),
                identitySelected.getProfileImage(),
                externalPlatform);
    }

    private Fan updateIdentityImage(
            String fanExternalName,
            String fanPassword,
            ExternalPlatform externalPlatform) throws
            CantUpdateFanIdentityException,
            WrongTokenlyUserCredentialsException {
        return moduleManager.updateFanIdentity(
                fanExternalName,
                fanPassword,
                identitySelected.getId(),
                identitySelected.getPublicKey(),
                fanImageByteArray,
                externalPlatform);
    }

}
