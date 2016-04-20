package com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.fragments;

/**
 * Created by edicson on 23/03/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.popups.PresentationArtArtistUserIdentityDialog;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.session.ArtistIdentitySubAppSession;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Artist.ArtistIdentityManagerModule;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Artist.ArtistIdentitySettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.sub_app.artist_identity.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class CreateArtistIndetityFragment extends AbstractFermatFragment {



    private static final String TAG = "CreateTokenlyArtistIdentity";
    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    private ArtistIdentitySubAppSession artistIdentitySubAppSession;
    private byte[] ArtistImageByteArray;
    private ArtistIdentityManagerModule moduleManager;
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
    private SettingsManager<ArtistIdentitySettings> settingsManager;
    private ArtistIdentitySettings artArtistPreferenceSettings = null;
    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;
    private boolean authenticationSuccessful = false;

    private Handler handler;
    boolean checked =false;


    public static CreateArtistIndetityFragment newInstance() {
        return new CreateArtistIndetityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_art_create_issuer_identity, container, false);
       initViews(rootLayout);
      //  setUpIdentity();

        return rootLayout;
    }


    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.create_tokenly_Artist_identity);
        mArtistExternalUserName = (EditText) layout.findViewById(R.id.external_username);
        //mArtistExternalPassword = (EditText) layout.findViewById(R.id.tokenly_access_password);
        ArtistImage =  (ImageView) layout.findViewById(R.id.tokenly_Artist_image);
        mArtistExternalPlatform = (Spinner) layout.findViewById(R.id.external_platform);
        //MexposureLevel = (Spinner) layout.findViewById(R.id.exposureLevel);
        //MartistAcceptConnectionsType = (Spinner) layout.findViewById(R.id.artistAcceptConnectionsType);
        relativeLayout = (RelativeLayout) layout.findViewById(R.id.user_image);
        createButton.setText((!isUpdate) ? "Create" : "Update");
        mArtistExternalUserName.requestFocus();


        List<String> arraySpinner = ArtExternalPlatform.getArrayItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);

        mArtistExternalPlatform.setAdapter(adapter);


        mArtistExternalPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                  List<String> jj = getArtistIdentityByPlatform(parent.getItemAtPosition(position).toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //getArtistIdentityByPlatform();


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


                int resultKey = 0;
                try {
                    resultKey = createNewIdentity();
                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
                switch (resultKey) {
                    case CREATE_IDENTITY_SUCCESS:
//                        changeActivity(Activities.CCP_SUB_APP_INTRA_USER_IDENTITY.getCode(), appSession.getAppPublicKey());
                        if (!isUpdate) {
                            //Toast.makeText(getActivity(), "Identity created", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), "Not Yet Ready", Toast.LENGTH_SHORT).show();
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

            }

        });
    }






    private int createNewIdentity() throws InvalidParameterException {

        String ArtistExternalName = mArtistExternalUserName.getText().toString();
        //String ArtistPassword = "";

        /*
        if (!mArtistExternalPassword.getText().toString().isEmpty()){
            ArtistPassword = mArtistExternalPassword.getText().toString();
        }
        */


        /*
        ExternalPlatform externalPlatform = ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM;
        if(mArtistExternalPlatform.isSelected()){
            externalPlatform = ExternalPlatform.getExternalPlatformByLabel(mArtistExternalPlatform.getSelectedItem().toString());
        }

        ExposureLevel  exposureLevel = ExposureLevel.DEFAULT_EXPOSURE_LEVEL;
        if(MexposureLevel.isSelected()){
            exposureLevel = ExposureLevel.getByCode(MexposureLevel.getSelectedItem().toString());
        }

        ArtistAcceptConnectionsType artistAcceptConnectionsType = ArtistAcceptConnectionsType.AUTOMATIC;
        if(MartistAcceptConnectionsType.isSelected()){
            artistAcceptConnectionsType = ArtistAcceptConnectionsType.getByCode(MartistAcceptConnectionsType.getSelectedItem().toString());
        }
*/
        boolean dataIsValid = validateIdentityData(ArtistExternalName,  ArtistImageByteArray);


/*
        if (dataIsValid) {
            if (moduleManager != null) {
                try {
                    if (!isUpdate) {
                        moduleManager.createArtistIdentity(ArtistExternalName, (ArtistImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : ArtistImageByteArray, UUID.fromString()) ;
                        //new ManageIdentity(ArtistExternalName,ArtistPassword,externalPlatform,exposureLevel,artistAcceptConnectionsType, ManageIdentity.CREATE_IDENTITY).execute();

                    }
                    else
                    if(updateProfileImage)
                        //moduleManager.updateFanIdentity(fanExternalName, fanPassword, identitySelected.getId(), identitySelected.getPublicKey(), fanImageByteArray, externalPlatform);
                       // new ManageIdentity(ArtistExternalName,ArtistPassword,externalPlatform, exposureLevel,artistAcceptConnectionsType, ManageIdentity.UPDATE_IMAGE_IDENTITY).execute();
                    else
                        //moduleManager.updateFanIdentity(fanExternalName,fanPassword,identitySelected.getId(), identitySelected.getPublicKey(), identitySelected.getProfileImage(),externalPlatform);
                        //new ManageIdentity(ArtistExternalName,ArtistPassword,externalPlatform,exposureLevel,artistAcceptConnectionsType,  ManageIdentity.UPDATE_IDENTITY).execute();
                }

                catch (Exception e){
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                    e.printStackTrace();
                }
                return CREATE_IDENTITY_SUCCESS;
            }
            return CREATE_IDENTITY_FAIL_MODULE_IS_NULL;
        }

        return CREATE_IDENTITY_FAIL_NO_VALID_DATA;
*/
    return CREATE_IDENTITY_SUCCESS;
    }



    private boolean validateIdentityData(String ArtistExternalName,  byte[] ArtistImageBytes) {
        if (ArtistExternalName.isEmpty())
            return false;
        if (ArtistImageBytes == null)
            return false;
        if (ArtistImageBytes.length > 0)
            return true;
//        if(externalPlatform != null)
//            return  true;
        return true;
    }

    private byte[] convertImage(int resImage){
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }



    private void externalPlatformSpinnerListener(){

    }


    private  List<String> getArtistIdentityByPlatform(String spinnerChoice) throws Exception{

        ArtExternalPlatform externalPlatform = ArtExternalPlatform.getArtExternalPlatformByLabel(spinnerChoice);



        HashMap<UUID, String> ArtIdentityByPlatform = null;


        if(externalPlatform == ArtExternalPlatform.TOKENLY) {
            //fanIdentityByPlatform = moduleManager.listExternalIdentitiesFromCurrentDeviceUser().get(ArtExternalPlatform.TOKENLY);
        }


        ArtIdentityByPlatform = moduleManager.listExternalIdentitiesFromCurrentDeviceUser().get(ArtExternalPlatform.TOKENLY);

        //HashMap<ArtExternalPlatform, HashMap<UUID, String>> JHashMap = moduleManager.listExternalIdentitiesFromCurrentDeviceUser();

        //fanIdentityByPlatform = moduleManager.listExternalIdentitiesFromCurrentDeviceUser().get(externalPlatform);

            Iterator<Map.Entry<UUID, String>> entries2 = ArtIdentityByPlatform.entrySet().iterator();
        List<String> identityNameList = new ArrayList<>();
        List<UUID> identityIdList = new ArrayList<>();
        while(entries2.hasNext()){
            Map.Entry<UUID, String> entry2 = entries2.next();
            identityNameList.add(entry2.getValue());
            identityIdList.add(entry2.getKey());
        }

        return identityNameList;
    }




}//main