package com.bitdubai.sub_app.art_fan_identity.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Fan.FanIdentityManagerModule;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Fan.FanIdentitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.art_fan_identity.R;
import com.bitdubai.sub_app.art_fan_identity.sessions.ArtFanUserIdentitySubAppSessionReferenceApp;
import com.bitdubai.sub_app.art_fan_identity.sessions.SessionConstants;
import com.bitdubai.sub_app.art_fan_identity.util.CommonLogger;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/04/16.
 */
public class CreateArtFanUserIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<FanIdentityManagerModule>, SubAppResourcesProviderManager> {
    private static final String TAG = "CreateArtFanIdentity";
    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int CONTEXT_MENU_DELETE = 3;
    private static final int CONTEXT_MENU_TURN_RIGHT = 4;
    private static final int CONTEXT_MENU_TURN_LEFT = 5;


    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private static final int MAX_ALIAS_CHARACTER = 40;


    //private ArtFanUserIdentitySubAppSession artFanUserIdentitySubAppSession;
    //private ArtFanUserIdentitySubAppSessionReferenceApp artFanUserIdentitySubAppSession;
    private byte[] fanImageByteArray;
    private FanIdentityManagerModule moduleManager;
    private ErrorManager errorManager;
    private Button createButton;
    private EditText mFanExternalUserName;
    private ImageView fanImage;
    private RelativeLayout relativeLayout;
    private Menu menuHelp;
    private Fanatic identitySelected;
    private boolean isUpdate = false;
    private Spinner mFanExternalPlatform;
    private Spinner mFanExternalUser;
    //private SettingsManager<FanIdentitySettings> settingsManager;
    private FanIdentitySettings fanIdentitySettings = null;
    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;
    private boolean contextMenuDelete = false;

    private Handler handler;
    private boolean updateCheck = false;
    private View WarningCircle;
    private TextView WarningLabel;
    private TextView alias;
    private String WarningColor = "#DF0101";
    private String NormalColor  =  "#0080FF";

    private boolean TheresPic = false;
    private boolean TheresAlias = false;

    public static CreateArtFanUserIdentityFragment newInstance(){
        return new CreateArtFanUserIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //artFanUserIdentitySubAppSession = (ArtFanUserIdentitySubAppSessionReferenceApp) appSession;
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            setHasOptionsMenu(false);
            //settingsManager = artFanUserIdentitySubAppSession.getModuleManager().
             //       getSettingsManager();

            try {
                if (appSession.getAppPublicKey()!= null){
                    fanIdentitySettings = moduleManager.loadAndGetSettings(
                            appSession.getAppPublicKey());
                }else{
                    fanIdentitySettings = moduleManager.loadAndGetSettings("art_fan_identity");
                }

            } catch (Exception e) {
                fanIdentitySettings = null;
            }

            if (fanIdentitySettings == null) {
                fanIdentitySettings = new FanIdentitySettings();
                fanIdentitySettings.setIsPresentationHelpEnabled(false);
                if(moduleManager != null){
                fanIdentitySettings.setIsPresentationHelpEnabled(true);
                    if (appSession.getAppPublicKey()!=null){
                        moduleManager.persistSettings(
                                appSession.getAppPublicKey(), fanIdentitySettings);
                    }else{
                        moduleManager.persistSettings("art_fan_identity", fanIdentitySettings);
                    }
                }
            }
        } catch (Exception ex) {
            errorManager.reportUnexpectedSubAppException(
                    SubApps.ART_FAN_IDENTITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT,
                    ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_create_art_fan_user_identity, container, false);
        initViews(rootLayout);
        setUpIdentity();
        if (fanIdentitySettings.isPresentationHelpEnabled()) {
            setUpHelpArtFan(false);
        }
        return rootLayout;


    }

    /*public void showDialog(){
        PresentationArtFanUserIdentityDialog fanUserIdentityDialog =
                new PresentationArtFanUserIdentityDialog(
                        getActivity(),
                        artFanUserIdentitySubAppSession,
                        null,
                        moduleManager);
        fanUserIdentityDialog.show();
    }*/
    private void setUpHelpArtFan(boolean checkButton) {
        try {
            PresentationDialog presentationDialog;
            presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.banner_fan_community)
                    .setIconRes(R.drawable.fan)
                    .setSubTitle(R.string.art_fan_identity_welcome_subTitle)
                    .setBody(R.string.art_fan_identity_welcome_body)
                    .setTextFooter(R.string.art_fan_identity_welcome_footer)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(
                    SubApps.TKY_ARTIST_IDENTITY_SUB_APP,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                    e);
        }
    }

    private void setUpIdentity() {
        try {
            identitySelected = (Fanatic) appSession.getData(
                    SessionConstants.IDENTITY_SELECTED);

            if (identitySelected != null) {
                loadIdentity();
            } else {
                List<Fanatic> lst = moduleManager.listIdentitiesFromCurrentDeviceUser();
                if(!lst.isEmpty()){
                    identitySelected = lst.get(0);
                }
                if (identitySelected != null) {
                    loadIdentity();
                    isUpdate = true;
                    createButton.setBackgroundResource(R.drawable.button_save_active);
                    TheresAlias = true;
                    TheresPic = true;

                    // createButton.setText("Save changes");
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
        updateCheck = true;

        if (identitySelected.getProfileImage() != null) {
            Bitmap bitmap = null;
            if (identitySelected.getProfileImage().length > 0) {
                bitmap = BitmapFactory.decodeByteArray(
                        identitySelected.getProfileImage(),
                        0,
                        identitySelected.getProfileImage().length);
                updateProfileImage = true;
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.afi_profile_male);
            }
           // bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            fanImageByteArray = toByteArray(bitmap);
            fanImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
        }
        mFanExternalUserName.setText(identitySelected.getAlias());
        List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Select a Platform...");
        arraySpinner.addAll(ArtExternalPlatform.getArrayItems());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mFanExternalPlatform.setAdapter(adapter);
        List<String> arraySpinner2 = new ArrayList<>();
        arraySpinner2.add("Select an Identity...");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                arraySpinner2
        );
        mFanExternalUser.setAdapter(adapter2);
        ArtExternalPlatform[] externalPlatforms = ArtExternalPlatform.values();
        for (int i=0; i<externalPlatforms.length;i++){
            if(externalPlatforms[i].getCode().equals(
                    identitySelected.getExternalPlatform().getCode()))
            {
                mFanExternalPlatform.setSelection(i + 1);
                try{
                    List<UUID> externalIdentityIDList = getFanIdentityIdByPlatform(externalPlatforms[i]);
                    for (int j=0; j<externalIdentityIDList.size();j++){
                        UUID identitySelectedExternalID;
                        try{
                            identitySelectedExternalID = identitySelected.getExternalIdentityID();
                        }catch(Exception e){
                            identitySelectedExternalID = null;
                        }
                        ArtExternalPlatform externalPlatform = externalPlatforms[i];
                        if(externalPlatform != null){
                            arraySpinner2.addAll(getFanIdentityByPlatform(externalPlatform));
                            adapter2 = new ArrayAdapter<String>(
                                    getActivity(),
                                    android.R.layout.simple_spinner_item,
                                    arraySpinner2
                            );
                        }
                        mFanExternalUser.setAdapter(adapter2);
                        if(identitySelectedExternalID != null){
                            if(externalIdentityIDList.get(j).equals(identitySelectedExternalID)) {
                                mFanExternalUser.setSelection(j + 1);
                                break;
                            }
                        }
                    }
                }catch (Exception e){

                }
            }
        }
    }
    /**
     * Initialize the views from this fragment
     *
     * @param layout
     */
    private void initViews(View layout) {
        createButton = (Button) layout.findViewById(R.id.afi_create_art_fan_identity);
        mFanExternalUserName = (EditText) layout.findViewById(R.id.afi_alias);
        fanImage = (ImageView) layout.findViewById(R.id.afi_fan_image);
        mFanExternalPlatform = (Spinner) layout.findViewById(R.id.afi_external_platform);
        mFanExternalUser = (Spinner) layout.findViewById(R.id.afi_external_platform_user_id);
        //relativeLayout = (RelativeLayout) layout.findViewById(R.id.afi_user_image);
       // createButton.setText((!isUpdate) ? "Create" : "Update");
        createButton.setBackgroundResource((!isUpdate) ? R.drawable.button_save_inactive:R.drawable.button_save_active);



        mFanExternalUserName.requestFocus();


        List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Select a Platform...");
        arraySpinner.addAll(ArtExternalPlatform.getArrayItems());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mFanExternalPlatform.setAdapter(adapter);
        externalPlatformSpinnerListener();
        mFanExternalUserName.requestFocus();
        registerForContextMenu(fanImage);
        fanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommonLogger.debug(TAG, "Entrando en ArtImage.setOnClickListener");
                getActivity().openContextMenu(fanImage);

                /*
                WarningLabel.setVisibility(View.GONE);
                //WarningCircle.setVisibility(View.GONE);
                CommonLogger.debug(TAG, "get in on fanImage.setOnClickListener");
                getActivity().openContextMenu(fanImage);
                */
                /*
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_dialog);
                dialog.findViewById(R.id.img_cam1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchTakePictureIntent();
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.img_gallery1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadImageFromGallery();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                //botonG.setVisibility(View.VISIBLE);
                CommonLogger.debug("Chat identity", "Entrando en chatImg.setOnClickListener")
                   */
            }


        });

        WarningCircle = (View) layout.findViewById(R.id.warning_cirlcle);

        WarningCircle.setVisibility(View.GONE);

        WarningLabel = (TextView) layout.findViewById(R.id.warning_label);
        WarningLabel.setVisibility(View.GONE);

        alias = (TextView) layout.findViewById(R.id.afi_alias_label);

        mFanExternalUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alias.setTextColor(Color.parseColor("#919090"));
            }
        });

        mFanExternalUserName.addTextChangedListener(new TextWatcher() {

            private boolean setTextFlag = true;
            private Toast toastChar = null;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if ( mFanExternalUserName.getText().length()>= MAX_ALIAS_CHARACTER) {
                    //this to avoid toast accumulation
                    if (toastChar != null) toastChar.cancel();
                    toastChar = Toast.makeText(getActivity(), "Only "+MAX_ALIAS_CHARACTER+" chars allowed", Toast.LENGTH_SHORT);
                    toastChar.show();
                    // set the text to a string max length MAX_ALIAS_CHARACTER:
                    if (setTextFlag) {
                        setTextFlag = false;
                        mFanExternalUserName.setText(s.subSequence(0, MAX_ALIAS_CHARACTER));
                        mFanExternalUserName.setSelection(mFanExternalUserName.getText().length());
                    } else {
                        setTextFlag = true;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!((mFanExternalUserName.getText().toString()).equals("")) || (mFanExternalUserName.getText() != null)) {
                    TheresAlias = true;
                    CheckTheres();
                }


            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                CommonLogger.debug(TAG, "get in on createButton.setOnClickListener");
                int resultKey = createNewIdentity();
                switch (resultKey) {
                    case CREATE_IDENTITY_SUCCESS:
                        if (!isUpdate) {
                            Toast.makeText(getActivity(), "Identity created", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        } else {
                            Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                        break;
                    case CREATE_IDENTITY_FAIL_MODULE_EXCEPTION:
                        Toast.makeText(getActivity(), "Error creating the identity", Toast.LENGTH_LONG).show();
                        break;
                    case CREATE_IDENTITY_FAIL_NO_VALID_DATA:
                        Toast.makeText(getActivity(), "The submitted data is not valid", Toast.LENGTH_LONG).show();
                        break;
                    case CREATE_IDENTITY_FAIL_MODULE_IS_NULL:
                        Toast.makeText(getActivity(), "The module manager is null", Toast.LENGTH_LONG).show();
                        break;
                }


            }


        });

       // configureToolbar();
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackgroundColor(Color.WHITE);
        else
            toolbar.setBackgroundColor(Color.WHITE);

         toolbar.setTitleTextColor(Color.BLACK);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }



    private int createNewIdentity() {
        UUID externalIdentityID = null;
        ArtExternalPlatform artExternalPlatform = ArtExternalPlatform.getDefaultExternalPlatform();
        if(mFanExternalPlatform.getSelectedItem() != mFanExternalPlatform.getItemAtPosition(0)){
            artExternalPlatform = ArtExternalPlatform.getArtExternalPlatformByLabel(
                    mFanExternalPlatform.getSelectedItem().toString());
        }
        if(!mFanExternalUser.getSelectedItem().equals(mFanExternalUser.getItemAtPosition(0))){
            if(artExternalPlatform !=null){
                List<UUID> identityByPlatformList = new ArrayList<>();
                try{
                    identityByPlatformList = getFanIdentityIdByPlatform(artExternalPlatform);
                }catch(Exception e){
                }
                if (!identityByPlatformList.isEmpty()) {
                    externalIdentityID = identityByPlatformList.get(mFanExternalUser.getSelectedItemPosition() - 1);
                }
            }
        }
        String fanExternalName = mFanExternalUserName.getText().toString();
        String externalUsername="";

        /*if(mFanExternalUser.getCount()>1){
            externalUsername=mFanExternalUser.getSelectedItem().toString();
        }*/
        boolean dataIsValid = validateIdentityData(
                fanExternalName,
                fanImageByteArray,
                externalIdentityID);
        if (dataIsValid) {
            if (moduleManager != null) {
                try {
                    if (!isUpdate) {
                        moduleManager.createFanaticIdentity(
                                fanExternalName,
                                (fanImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : fanImageByteArray,
                                externalIdentityID,
                                artExternalPlatform,
                                externalUsername);
                    } else
                        if (updateProfileImage) {
                            moduleManager.updateFanIdentity(
                                    fanExternalName,
                                    identitySelected.getPublicKey(),
                                    fanImageByteArray,
                                    externalIdentityID,
                                    artExternalPlatform,
                                    externalUsername);

                        }
                        else
                            moduleManager.updateFanIdentity(fanExternalName,
                                    identitySelected.getPublicKey(),
                                    identitySelected.getProfileImage(),
                                    externalIdentityID,
                                    artExternalPlatform,
                                    externalUsername);

                } catch (Exception e){
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                    e.printStackTrace();
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void loadImageFromGallery() {
        Intent loadImageIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }

    /**
     * This method checks the submitted data.
     * @param fanExternalName
     * @param fanImageBytes
     * @param externalIdentityID
     * @return
     */
    private boolean validateIdentityData(
            String fanExternalName,
            byte[] fanImageBytes,
            UUID externalIdentityID) {

        ShowWarnings(fanExternalName,fanImageBytes);



        if (fanExternalName.isEmpty())
            return false;
        /*if (externalPlatformID==null)
            return false;*/
        boolean identitySelectedHasID;
        try{
            identitySelectedHasID = identitySelected.getExternalIdentityID() != null;
        }catch(Exception e){
            identitySelectedHasID = false;
        }
        if(externalIdentityID == null && identitySelectedHasID && isUpdate)
            return false;
        if (fanImageBytes == null)
            return false;
        if (fanImageBytes.length > 0)
            return true;
        /*if(externalPlatform != null)
            return  true;*/
        return true;
    }

    private void ShowWarnings(String fanExternalName, byte[] fanImageBytes) {

        if (fanExternalName.isEmpty()){
           // mFanExternalUserName.setHintTextColor(Color.parseColor(WarningColor));

        }

        if (fanImageBytes == null){
            WarningLabel.setVisibility(View.VISIBLE);
            alias.setTextColor(Color.parseColor(WarningColor));


            //WarningCircle.setVisibility(View.VISIBLE);
        }




        if(mFanExternalPlatform.getSelectedItemPosition()==0){
            //mFanExternalPlatform.setBackgroundColor(Color.parseColor(WarningColor));
        }else{
            //mFanExternalPlatform.setBackgroundColor(Color.parseColor(NormalColor));
        }
/*
        if(mFanExternalUser.getSelectedItemPosition()==0){
            mFanExternalUser.setBackgroundColor(Color.parseColor("#DF0101"));
        }else{
            mFanExternalUser.setBackgroundColor(Color.parseColor("#0080FF"));
        }
*/
        mFanExternalUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mFanExternalUser.setBackgroundColor(Color.parseColor(NormalColor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            ImageView pictureView = fanImage;
            contextMenuInUse = true;
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Uri selectedImage2 = data.getData();
                    if(selectedImage2==null){
                        break;
                    }
                    File myFile = new File(selectedImage2.getPath());
                    myFile.getAbsolutePath();
                    Bundle extras2 = data.getExtras();
                    Bitmap fixedBitmap = FixRotation(myFile, (Bitmap) extras2.get("data"));
                    imageBitmap = fixedBitmap;
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                    fanImageByteArray = toByteArray(imageBitmap);
                    updateProfileImage = true;
                    Picasso.with(getActivity()).load(selectedImage2).transform(new CircleTransform()).into(fanImage);
                    updateProfileImage = true;
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    String absolutePath = getRealPathFromURI(selectedImage);
                    File uriFile = new File(absolutePath);
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            imageBitmap = FixRotation(uriFile, imageBitmap);
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                            fanImageByteArray = toByteArray(imageBitmap);
                            updateProfileImage = true;
                            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(fanImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error loading the picture", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            if (pictureView != null && imageBitmap != null)
                pictureView.setImageDrawable(
                        ImagesUtils.getRoundedBitmap(
                                getResources(), imageBitmap));
            contextMenuInUse = false;
        }
    }

    private Bitmap FixRotation(File myFile, Bitmap bitmap) {

        Bitmap rotatedBitmap = null;

        try {
            ExifInterface exif = new ExifInterface(myFile.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);


        } catch (IOException ex) {
            Log.e(TAG, "Failed to get Exif data", ex);
        }

        return rotatedBitmap;
    }

    private int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if(cursor != null){
            column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);}

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }









    private void CheckTheres() {
        if(TheresAlias && TheresPic){createButton.setBackgroundResource(R.drawable.button_save_active);}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {


        menu.setHeaderTitle("Choose mode");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.afi_camera_green));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
        if (updateProfileImage) {
            menu.add(Menu.NONE, CONTEXT_MENU_TURN_RIGHT, Menu.NONE, "Turn pic right");
            menu.add(Menu.NONE, CONTEXT_MENU_TURN_LEFT, Menu.NONE, "Turn pic left");
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete Picture");
        }
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
                case CONTEXT_MENU_DELETE:
                    DeletePicture();
                    contextMenuDelete = true;
                    return true;
                case CONTEXT_MENU_TURN_RIGHT:
                    turnPicture(90f);
                    return true;
                case CONTEXT_MENU_TURN_LEFT:
                    turnPicture(-90f);
                    return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void DeletePicture() {
       fanImage.setImageDrawable(null);
        fanImageByteArray = null;
       // camEditButton.setBackgroundResource(R.drawable.art_edit_picture_button);
        updateProfileImage = false;
    }


    private void turnPicture(float rotationInDegrees) {
        ImageView pictureView = fanImage;
        Bitmap bitmap = ((RoundedBitmapDrawable)pictureView.getDrawable()).getBitmap();
        Matrix matrix = new Matrix();
        matrix.preRotate(rotationInDegrees);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        fanImage.setImageDrawable(
                ImagesUtils.getRoundedBitmap(
                        getResources(), rotatedBitmap));
        fanImageByteArray = toByteArray(rotatedBitmap);
        contextMenuInUse = false;
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        try {
            menu.add(1, 99, 1, "help").setIcon(R.drawable.afi_help_icon)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

            final MenuItem action_help = menu.findItem(R.id.afi_action_help);
            menu.findItem(R.id.afi_action_help).setVisible(true);
            action_help.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    menu.findItem(R.id.afi_action_help).setVisible(false);
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
                setUpHelpArtFan(false);


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(
                    UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> getFanIdentityByPlatform(ArtExternalPlatform externalPlatform) throws Exception{
        HashMap<UUID, String>fanIdentityByPlatform = moduleManager.listExternalIdentitiesFromCurrentDeviceUser().get(externalPlatform);
        List<String> identityNameList = new ArrayList<>();
        if(fanIdentityByPlatform != null){
            Iterator<Map.Entry<UUID, String>> entries2 = fanIdentityByPlatform.entrySet().iterator();
            while(entries2.hasNext()){
                Map.Entry<UUID, String> entry2 = entries2.next();
                identityNameList.add(entry2.getValue());
            }
            return identityNameList;
        }

        return identityNameList;
    }

    private List<UUID> getFanIdentityIdByPlatform(ArtExternalPlatform externalPlatform) throws Exception{
        HashMap<UUID, String>fanIdentityByPlatform = moduleManager.listExternalIdentitiesFromCurrentDeviceUser().get(externalPlatform);
        List<UUID> identityIdList = new ArrayList<>();
        if(fanIdentityByPlatform != null){
            Iterator<Map.Entry<UUID, String>> entries2 = fanIdentityByPlatform.entrySet().iterator();
            while(entries2.hasNext()){
                Map.Entry<UUID, String> entry2 = entries2.next();
                identityIdList.add(entry2.getKey());
            }
            return identityIdList;
        }
        return identityIdList;
    }

    private void externalPlatformSpinnerListener(){
        mFanExternalPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //mFanExternalPlatform.setBackgroundColor(Color.parseColor(NormalColor));
                    if (!updateCheck) {
                        List<String> arraySpinner = new ArrayList<>();
                        arraySpinner.add("Select an Identity...");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getActivity(),
                                android.R.layout.simple_spinner_item,
                                arraySpinner
                        );
                        if (!mFanExternalPlatform.getSelectedItem().equals(mFanExternalPlatform.getItemAtPosition(0))) {
                            ArtExternalPlatform externalPlatform = ArtExternalPlatform.getArtExternalPlatformByLabel(parent.getItemAtPosition(position).toString());
                            if (externalPlatform != null) {
                                List<String> identityByPlatformList = getFanIdentityByPlatform(externalPlatform);
                                if (!identityByPlatformList.isEmpty()) {
                                    arraySpinner.addAll(identityByPlatformList);
                                    adapter = new ArrayAdapter<String>(
                                            getActivity(),
                                            android.R.layout.simple_spinner_item,
                                            arraySpinner
                                    );
                                }

                            }
                        }
                        mFanExternalUser.setAdapter(adapter);
                        mFanExternalUser.setSelection(0);
                    }
                    updateCheck = false;
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(
                            SubApps.ART_FAN_IDENTITY,
                            UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT,
                            e);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

