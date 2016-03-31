package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.R;
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.session.TkyIdentitySubAppSession;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistPreferenceSettings;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.TokenlyFanPreferenceSettings;
import com.bitdubai.sub_app.intra_user_identity.util.CommonLogger;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 21/03/16.
 */
public class TkyIdentityCreateProfile extends AbstractFermatFragment {

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


    private Handler handler;


    public static TkyIdentityCreateProfile newInstance() {
        return new TkyIdentityCreateProfile();
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
         //-------- setUpIdentity();
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






    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.create_tokenly_fan_identity);
        mArtistExternalUserName = (EditText) layout.findViewById(R.id.external_username);
        mArtistExternalPassword = (EditText) layout.findViewById(R.id.tokenly_access_password);
        ArtistImage = (ImageView) layout.findViewById(R.id.tokenly_fan_image);
        mArtistExternalPlatform = (Spinner) layout.findViewById(R.id.external_platform);
        MexposureLevel = (Spinner) layout.findViewById(R.id.exposureLevel);
        MartistAcceptConnectionsType = (Spinner) layout.findViewById(R.id.artistAcceptConnectionsType);
        relativeLayout = (RelativeLayout) layout.findViewById(R.id.user_image);
        createButton.setText((!isUpdate) ? "Create" : "Update");
        mArtistExternalUserName.requestFocus();

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
                CommonLogger.debug(TAG, "Entrando en fanImage.setOnClickListener");
                getActivity().openContextMenu(ArtistImage);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "Entrando en createButton.setOnClickListener");

                /*
                int resultKey = createNewIdentity();
                switch (resultKey) {
                    case CREATE_IDENTITY_SUCCESS:
//                        changeActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY.getCode(), appSession.getAppPublicKey());
                        if (!isUpdate) {
                            Toast.makeText(getActivity(), "Identity created", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                        }
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
                */
            }
        });
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

    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}//main
