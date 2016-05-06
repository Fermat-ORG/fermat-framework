package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.R;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.popup.PresentationTokenlyArtistUserIdentityDialog;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.session.SessionConstants;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.session.TkyIdentitySubAppSession;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistPreferenceSettings;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by juan Sulbaran
 */
public class TokenlyArtistIdentityCreateProfile extends AbstractFermatFragment {

    private static final String TAG = "CreateTokenlyArtistIdentity";
    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    private TkyIdentitySubAppSession tkyIdentitySubAppSession;
    private byte[] ArtistImageByteArray;
    private TokenlyArtistIdentityManagerModule moduleManager;
    private ErrorManager errorManager;
    private Button createButton;
    private EditText mArtistExternalUserName;
    private ImageView ArtistImage;
    private RelativeLayout relativeLayout;
    private Menu menuHelp;
    private Artist identitySelected;
    private boolean isUpdate = false;
    private EditText mArtistExternalPassword;
    private Spinner mArtistExternalPlatform;
    private Spinner MexposureLevel;
    private Spinner MartistAcceptConnectionsType;
    private SettingsManager<TokenlyArtistPreferenceSettings> settingsManager;
    private TokenlyArtistPreferenceSettings tokenlyArtistPreferenceSettings = null;
    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;
    private boolean authenticationSuccessful = false;
    private boolean isWaitingForResponse = false;
    private ProgressDialog tokenlyRequestDialog;

    private Handler handler;



    public static TokenlyArtistIdentityCreateProfile newInstance() {
        return new TokenlyArtistIdentityCreateProfile();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            tkyIdentitySubAppSession = (TkyIdentitySubAppSession) appSession;
            moduleManager = tkyIdentitySubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            setHasOptionsMenu(false);
            settingsManager = tkyIdentitySubAppSession.getModuleManager().getSettingsManager();

            try {
                if (tkyIdentitySubAppSession.getAppPublicKey()!= null){
                    tokenlyArtistPreferenceSettings = settingsManager.loadAndGetSettings(tkyIdentitySubAppSession.getAppPublicKey());
                }else{
                    //TODO: Joaquin: Lo estoy poniendo con un public key hardcoded porque en este punto no posee public key.
                    tokenlyArtistPreferenceSettings = settingsManager.loadAndGetSettings("123456789");
                }

            } catch (Exception e) {
                tokenlyArtistPreferenceSettings = null;
            }

            if (tokenlyArtistPreferenceSettings == null) {
                tokenlyArtistPreferenceSettings = new TokenlyArtistPreferenceSettings();
                tokenlyArtistPreferenceSettings.setIsPresentationHelpEnabled(false);
                if(settingsManager != null){
                    if (tkyIdentitySubAppSession.getAppPublicKey()!=null){
                        settingsManager.persistSettings(tkyIdentitySubAppSession.getAppPublicKey(), tokenlyArtistPreferenceSettings);
                    }else{
                        settingsManager.persistSettings("123456789", tokenlyArtistPreferenceSettings);
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
          View rootLayout = inflater.inflate(R.layout.fragment_tky_artist_create_identity, container, false);
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





    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.create_tokenly_Artist_identity);
        mArtistExternalUserName = (EditText) layout.findViewById(R.id.external_username);
        mArtistExternalPassword = (EditText) layout.findViewById(R.id.tokenly_access_password);
        ArtistImage =  (ImageView) layout.findViewById(R.id.tokenly_Artist_image);
        mArtistExternalPlatform = (Spinner) layout.findViewById(R.id.external_platform);
        MexposureLevel = (Spinner) layout.findViewById(R.id.exposureLevel);
        MartistAcceptConnectionsType = (Spinner) layout.findViewById(R.id.artistAcceptConnectionsType);
        relativeLayout = (RelativeLayout) layout.findViewById(R.id.user_image);
        createButton.setText((!isUpdate) ? "Create" : "Update");
        mArtistExternalUserName.requestFocus();

        TextView text = (TextView) layout.findViewById(R.id.external_platform_label);
        TextView text2 = (TextView) layout.findViewById(R.id.exposure_level_label);
        TextView text3 = (TextView) layout.findViewById(R.id.artist_accept_connections_type_label);

        text.setTextColor(Color.parseColor("#000000"));
        text2.setTextColor(Color.parseColor("#000000"));
        text3.setTextColor(Color.parseColor("#000000"));

        text.setVisibility(View.GONE);
        mArtistExternalPlatform.setVisibility(View.GONE);

        text2.setVisibility(View.GONE);
        MexposureLevel.setVisibility(View.GONE);

        text3.setVisibility(View.GONE);
        MartistAcceptConnectionsType.setVisibility(View.GONE);

        List<String> arraySpinner = ExternalPlatform.getArrayItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mArtistExternalPlatform.setAdapter(adapter);

         arraySpinner = ExposureLevel.getArrayItems();
         adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        MexposureLevel.setAdapter(adapter);

         arraySpinner = ArtistAcceptConnectionsType.getArrayItems();
         adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        MartistAcceptConnectionsType.setAdapter(adapter);

        mArtistExternalUserName.requestFocus();
        registerForContextMenu(ArtistImage);

        ArtistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "Entrando en ArtImage.setOnClickListener");
                getActivity().openContextMenu(ArtistImage);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "Entrando en createButton.setOnClickListener");
                tokenlyRequestDialog = new ProgressDialog(getContext());
                tokenlyRequestDialog.setMessage("Please Wait");
                tokenlyRequestDialog.setTitle("Connecting to Tokenly");
                tokenlyRequestDialog.show();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(!isWaitingForResponse){
                                int resultKey = createNewIdentity();
                                switch (resultKey) {
                                    case CREATE_IDENTITY_SUCCESS:
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
                            }else{
                                tokenlyRequestDialog.dismiss();
                                Toast.makeText(getActivity(), "Waiting for Tokenly API response, please wait.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (InvalidParameterException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void setUpIdentity() {
        try {

            identitySelected = (Artist) tkyIdentitySubAppSession.getData(SessionConstants.IDENTITY_SELECTED);


            if (identitySelected != null) {
                loadIdentity();
            } else {
                List<Artist> lst = moduleManager.listIdentitiesFromCurrentDeviceUser();
                if(!lst.isEmpty()){
                    identitySelected = lst.get(0);
                }
                if (identitySelected != null) {
                    loadIdentity();
                    isUpdate = true;
                    createButton.setText("Save changes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            ImageView pictureView = ArtistImage;
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
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                            ArtistImageByteArray = toByteArray(imageBitmap);
                            updateProfileImage = true;
                            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(ArtistImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            if (pictureView != null && imageBitmap != null)
                pictureView.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), imageBitmap));

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
            ArtistImageByteArray = toByteArray(bitmap);
            ArtistImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
        }
        mArtistExternalUserName.setText(identitySelected.getUsername());
        //  mFanExternalPassword.setText(identitySelected.getApiToken());
        List<String> arraySpinner = ExternalPlatform.getArrayItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mArtistExternalPlatform.setAdapter(adapter);
        ExternalPlatform[] externalPlatforms = ExternalPlatform.values();
        for (int i=0; i<externalPlatforms.length;i++){
            if(externalPlatforms[i] == identitySelected.getExternalPlatform()){
                mArtistExternalPlatform.setSelection(i);
                break;
            }
        }

        arraySpinner = ExposureLevel.getArrayItems();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        MexposureLevel.setAdapter(adapter);
        ExposureLevel[] exposureLevels = ExposureLevel.values();
        for (int i=0; i<exposureLevels.length; i++){
            if(exposureLevels[i] == identitySelected.getExposureLevel()){
                MexposureLevel.setSelection(i);
                break;
            }
        }

        arraySpinner = ArtistAcceptConnectionsType.getArrayItems();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        MartistAcceptConnectionsType.setAdapter(adapter);
        ArtistAcceptConnectionsType[] artistAcceptConnectionsTypes = ArtistAcceptConnectionsType.values();
        for (int i=0; i<artistAcceptConnectionsTypes.length; i++){
            if(artistAcceptConnectionsTypes[i] == identitySelected.getArtistAcceptConnectionsType()){
                MartistAcceptConnectionsType.setSelection(i);
                break;
            }
        }


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
    private int createNewIdentity() throws InvalidParameterException {

        String ArtistExternalName = mArtistExternalUserName.getText().toString();
        String ArtistPassword = "";
        if (!mArtistExternalPassword.getText().toString().isEmpty()){
            ArtistPassword = mArtistExternalPassword.getText().toString();
        }
        ExternalPlatform externalPlatform = ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM;
        if(mArtistExternalPlatform.isSelected()){
            externalPlatform = ExternalPlatform.getExternalPlatformByLabel(mArtistExternalPlatform.getSelectedItem().toString());
        }

        ExposureLevel  exposureLevel;
        exposureLevel = ExposureLevel.getExposureLevelByLabel(MexposureLevel.getSelectedItem().toString());

        ArtistAcceptConnectionsType artistAcceptConnectionsType;
        artistAcceptConnectionsType = ArtistAcceptConnectionsType.getArtistAcceptConnectionsTypeByLabel(MartistAcceptConnectionsType.getSelectedItem().toString());

        boolean dataIsValid = validateIdentityData(ArtistExternalName, ArtistPassword, ArtistImageByteArray, externalPlatform);


        if (dataIsValid) {
            if (moduleManager != null) {
                try {
                    if (!isUpdate) {
                        //moduleManager.createFanIdentity(fanExternalName,(fanImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : fanImageByteArray,fanPassword,externalPlatform) ;
                         new ManageIdentity(ArtistExternalName,ArtistPassword,externalPlatform,exposureLevel,artistAcceptConnectionsType, ManageIdentity.CREATE_IDENTITY).execute();

                    }
                    else
                    if(updateProfileImage)
                        //moduleManager.updateFanIdentity(fanExternalName, fanPassword, identitySelected.getId(), identitySelected.getPublicKey(), fanImageByteArray, externalPlatform);
                        new ManageIdentity(ArtistExternalName,ArtistPassword,externalPlatform, exposureLevel,artistAcceptConnectionsType, ManageIdentity.UPDATE_IMAGE_IDENTITY).execute();
                    else
                        //moduleManager.updateFanIdentity(fanExternalName,fanPassword,identitySelected.getId(), identitySelected.getPublicKey(), identitySelected.getProfileImage(),externalPlatform);
                        new ManageIdentity(ArtistExternalName,ArtistPassword,externalPlatform,exposureLevel,artistAcceptConnectionsType,  ManageIdentity.UPDATE_IDENTITY).execute();
                } /*catch (CantCreateFanIdentityException | FanIdentityAlreadyExistsException |CantUpdateFanIdentityException e) {
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                } */

                catch (Exception e){
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

    private boolean validateIdentityData(String ArtistExternalName, String ArtistPassWord, byte[] ArtistImageBytes, ExternalPlatform externalPlatform) {
        if (ArtistExternalName.isEmpty())
            return false;
        if (ArtistPassWord.isEmpty())
            return false;
        if (ArtistImageBytes == null)
            return false;
        if (ArtistImageBytes.length > 0)
            return true;
//        if(externalPlatform != null)
//            return  true;
        return true;
    }

    private class ManageIdentity extends AsyncTask {
        String fanExternalName;
        String fanPassword;
        ExternalPlatform externalPlatform;
        ExposureLevel exposureLevel;
        ArtistAcceptConnectionsType artistAcceptConnectionsType;

        private Artist artist;
        int identityAction;
        public static final int CREATE_IDENTITY = 0;
        public static final int UPDATE_IDENTITY = 1;
        public static final int UPDATE_IMAGE_IDENTITY = 2;

        public ManageIdentity(
                String fanExternalName,
                String fanPassword,
                ExternalPlatform externalPlatform,
                ExposureLevel exposureLevel,
                ArtistAcceptConnectionsType artistAcceptConnectionsType,
                int identityAction
        ) {
            this.fanExternalName = fanExternalName;
            this.fanPassword = fanPassword;
            this.externalPlatform = externalPlatform;
            this.identityAction = identityAction;
            this.exposureLevel = exposureLevel;
            this.artistAcceptConnectionsType = artistAcceptConnectionsType;
            authenticationSuccessful = true;
            isWaitingForResponse = true;
        }

        @Override
        protected void onPostExecute(Object result) {

            isWaitingForResponse = false;
            if(!authenticationSuccessful){
                //I'll launch a toast
                tokenlyRequestDialog.dismiss();
                Toast.makeText(
                        getActivity(),
                        "Authentication credentials are invalid.",
                        Toast.LENGTH_SHORT).show();
            }
            if(Validate.isObjectNull(artist)){
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
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            try{
                switch (identityAction){
                    case CREATE_IDENTITY:
                       artist = createIdentity(
                               fanExternalName,
                               fanPassword,
                               externalPlatform,
                               exposureLevel,
                               artistAcceptConnectionsType);
                        break;
                    case UPDATE_IDENTITY:
                        artist = updateIdentity(
                                fanExternalName,
                                fanPassword,
                                externalPlatform,
                                exposureLevel,
                                artistAcceptConnectionsType);
                        break;
                    case UPDATE_IMAGE_IDENTITY:
                        artist = updateIdentityImage(
                                fanExternalName,
                                fanPassword,
                                externalPlatform,
                                exposureLevel,
                                artistAcceptConnectionsType);
                        break;
                }

            } catch (ArtistIdentityAlreadyExistsException e) {
                errorManager.reportUnexpectedUIException(
                        UISource.VIEW,
                        UnexpectedUIExceptionSeverity.UNSTABLE,
                        e);
            } catch (CantCreateArtistIdentityException e) {
                errorManager.reportUnexpectedUIException(
                        UISource.VIEW,
                        UnexpectedUIExceptionSeverity.UNSTABLE,
                        e);
            } catch (CantUpdateArtistIdentityException e) {
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

    private Artist createIdentity(
            String fanExternalName,
            String fanPassword,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) throws
            CantCreateArtistIdentityException,
            ArtistIdentityAlreadyExistsException,
            WrongTokenlyUserCredentialsException {
       return moduleManager.createArtistIdentity(
                fanExternalName,
                (ArtistImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : ArtistImageByteArray,
                fanPassword,
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType) ;
    }

    private Artist updateIdentity(
            String fanExternalName,
            String fanPassword,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) throws
            CantUpdateArtistIdentityException,
            WrongTokenlyUserCredentialsException {
       return moduleManager.updateArtistIdentity(
                fanExternalName,
                fanPassword, identitySelected.getId(),
                identitySelected.getPublicKey(),
                identitySelected.getProfileImage(),
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType);
    }

    private Artist updateIdentityImage(
            String fanExternalName,
            String fanPassword,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) throws
            CantUpdateArtistIdentityException,
            WrongTokenlyUserCredentialsException {
       return  moduleManager.updateArtistIdentity(
                fanExternalName,
                fanPassword,
                identitySelected.getId(),
                identitySelected.getPublicKey(),
                ArtistImageByteArray,
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType);
    }

    private byte[] convertImage(int resImage){
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void showDialog(){
        PresentationTokenlyArtistUserIdentityDialog presentationTokenlyFanUserIdentityDialog = new PresentationTokenlyArtistUserIdentityDialog(getActivity(),tkyIdentitySubAppSession, null,moduleManager);
        presentationTokenlyFanUserIdentityDialog.show();
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

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Choose mode");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");

        super.onCreateContextMenu(menu, view, menuInfo);
    }

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

}//main
