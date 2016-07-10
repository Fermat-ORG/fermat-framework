package com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.fragments;

/**
 * Created by edicson on 23/03/16.
 */

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
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
import android.widget.ImageButton;
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
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.session.ArtistIdentitySubAppSessionReferenceApp;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.session.SessionConstants;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Artist.ArtistIdentityManagerModule;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.Artist.ArtistIdentitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import com.bitdubai.sub_app.artist_identity.R;
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


public class CreateArtistIndetityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ArtistIdentityManagerModule>, SubAppResourcesProviderManager> {


    private static final String TAG = "CreateArtArtistIdentity";
    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    private static final int CONTEXT_MENU_DELETE = 3;
    private static final int MAX_ALIAS_CHARACTER=40;
    private static final int CONTEXT_MENU_TURN_RIGHT = 4;
    private static final int CONTEXT_MENU_TURN_LEFT = 5;


    //private ArtistIdentitySubAppSessionReferenceApp artistIdentitySubAppSession;
    private byte[] artistImageByteArray;
    private ArtistIdentityManagerModule moduleManager;
    private ErrorManager errorManager;
    private ImageButton createButton;
    private ImageButton camEditButton;
    private EditText mArtistUserName;
    private ImageView artistImage;
    private RelativeLayout relativeLayout;
    private Menu menuHelp;
    private Artist identitySelected;
    private boolean isUpdate = false;
    private Spinner mArtistExternalPlatform;
    private Spinner mArtistExternalName;
    private Spinner mArtistExposureLevel;
    private Spinner mArtistAcceptConnectionsType;
    //private SettingsManager<ArtistIdentitySettings> settingsManager;
    private ArtistIdentitySettings artArtistPreferenceSettings = null;
    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;
    private boolean updateCheck = false;
    private View WarningCircle;
    private TextView WarningLabel;
    private TextView alias;
    private String WarningColor = "#DF0101";
    private String NormalColor = "#0080FF";
    private boolean contextMenuDelete = false;


    private static final int ERROR_IMAGE_VIEW = 5;
    private static final int ERROR_USER_DATA = 6;
    private static final int ERROR_BOTH = 8;
    private static final int SUCCESSFULL_DATA = 7;


    public static CreateArtistIndetityFragment newInstance() {
        return new CreateArtistIndetityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //artistIdentitySubAppSession = appSession;
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            setHasOptionsMenu(false);
            //settingsManager = appSession.getModuleManager().
            //        getSettingsManager();

            try {
                if (appSession.getAppPublicKey() != null) {
                    artArtistPreferenceSettings = moduleManager.loadAndGetSettings(
                            appSession.getAppPublicKey());
                } else {
                    artArtistPreferenceSettings = moduleManager.loadAndGetSettings("public_key_art_artist_identity");
                }

            } catch (Exception e) {
                artArtistPreferenceSettings = null;
            }

            if (artArtistPreferenceSettings == null) {
                artArtistPreferenceSettings = new ArtistIdentitySettings();
                artArtistPreferenceSettings.setIsPresentationHelpEnabled(false);
                if (moduleManager != null) {
                artArtistPreferenceSettings.setIsPresentationHelpEnabled(true);
                    if (appSession.getAppPublicKey() != null) {
                        moduleManager.persistSettings(
                                appSession.getAppPublicKey(), artArtistPreferenceSettings);
                    } else {
                        moduleManager.persistSettings("public_key_art_artist_identity", artArtistPreferenceSettings);
                    }
                }
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(
                    SubApps.ART_ARTIST_IDENTITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT,
                    e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_art_create_artist_identity, container, false);
        initViews(rootLayout);
        setUpIdentity();

        getToolbar().setBackground(getResources().getDrawable(R.drawable.art_action_bar_background));


        if (artArtistPreferenceSettings.isPresentationHelpEnabled() == true) {
            setUpHelpTkyArtist(false);
        }


        return rootLayout;
    }

    private void setUpIdentity() {
        try {
            identitySelected = (Artist) appSession.getData(
                    SessionConstants.IDENTITY_SELECTED);

            if (identitySelected != null) {
                loadIdentity();
            } else {
                List<Artist> lst = moduleManager.listIdentitiesFromCurrentDeviceUser();
                if (lst!=null&&!lst.isEmpty()) {
                    identitySelected = lst.get(0);
                }
                if (identitySelected != null) {
                    loadIdentity();
                    isUpdate = true;
                    updateProfileImage = true;
                    camEditButton.setImageResource(R.drawable.art_edit_picture_button);
                    createButton.setImageResource(R.drawable.art_save_changes_button);
                }
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(
                    SubApps.ART_ARTIST_IDENTITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT,
                    e);
        }
    }

    private void loadIdentity() {
        updateCheck = true;
        if (identitySelected.getProfileImage() != null) {
            Bitmap bitmap = null;
            if (identitySelected.getProfileImage().length > 0) {
                bitmap = BitmapFactory.decodeByteArray(
                        identitySelected.getProfileImage(),
                        0,
                        identitySelected.getProfileImage().length);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_male);
            }
            //  bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            artistImageByteArray = toByteArray(bitmap);
            artistImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
        }
        mArtistUserName.setText(identitySelected.getAlias());
        List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Select a Platform...");
        arraySpinner.addAll(ArtExternalPlatform.getArrayItems());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.art_custom_spinner,
                arraySpinner);
        mArtistExternalPlatform.setAdapter(adapter);
        List<String> arraySpinner2 = new ArrayList<>();
        arraySpinner2.add("Select an Identity...");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getActivity(),
                R.layout.art_custom_spinner,
                arraySpinner2
        );
        mArtistExternalName.setAdapter(adapter2);
        ArtExternalPlatform[] externalPlatforms = ArtExternalPlatform.values();
        for (int i = 0; i < externalPlatforms.length; i++) {
            if (externalPlatforms[i].getCode().equals(
                    identitySelected.getExternalPlatform().getCode())) {
                mArtistExternalPlatform.setSelection(i + 1);
                try {
                    List<UUID> externalIdentityIDList = getArtistIdentityIdByPlatform(externalPlatforms[i]);
                    for (int j = 0; j < externalIdentityIDList.size(); j++) {
                        UUID identitySelectedExternalID;
                        try {
                            identitySelectedExternalID = identitySelected.getExternalIdentityID();
                        } catch (Exception e) {
                            identitySelectedExternalID = null;
                        }
                        ArtExternalPlatform externalPlatform = externalPlatforms[i];
                        if (externalPlatform != null) {
                            arraySpinner2.addAll(getArtistIdentityByPlatform(externalPlatform));
                            adapter2 = new ArrayAdapter<String>(
                                    getActivity(),
                                    R.layout.art_custom_spinner,
                                    arraySpinner2
                            );
                        }
                        mArtistExternalName.setAdapter(adapter2);
                        if (identitySelectedExternalID != null) {
                            if (externalIdentityIDList.get(j).equals(identitySelectedExternalID)) {
                                mArtistExternalName.setSelection(j + 1);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {

                }
                break;

            }
        }
        /*for (int i=0; i<externalPlatforms.length;i++){
            if(externalPlatforms[i] == identitySelected.getExternalPlatform()){
                mArtistExternalPlatform.setSelection(i+1);
                break;
            }
        }*/
        /*if(Validate.isValidString(identitySelected.getExternalUsername())){
            arraySpinner = new ArrayList<>();
            arraySpinner.add(identitySelected.getExternalUsername());
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
            mArtistExternalName.setAdapter(adapter);
        }*/
        arraySpinner = ExposureLevel.getArrayItems();
        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.art_custom_spinner,
                arraySpinner);
        mArtistExposureLevel.setAdapter(adapter);

        ExposureLevel[] exposureLevels = ExposureLevel.values();
        for (int i = 0; i < exposureLevels.length; i++) {
            if (exposureLevels[i] == identitySelected.getExposureLevel()) {
                mArtistExposureLevel.setSelection(i);
                break;
            }
        }

        arraySpinner = ArtistAcceptConnectionsType.getArrayItems();
        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.art_custom_spinner,
                arraySpinner);
        mArtistAcceptConnectionsType.setAdapter(adapter);

        ArtistAcceptConnectionsType[] artistAcceptConnectionsTypes = ArtistAcceptConnectionsType.values();
        for (int i = 0; i < artistAcceptConnectionsTypes.length; i++) {
            if (artistAcceptConnectionsTypes[i] == identitySelected.getArtistAcceptConnectionsType()) {
                mArtistAcceptConnectionsType.setSelection(i);
                break;
            }
        }

    }

    private void initViews(View layout) {
        createButton = (ImageButton) layout.findViewById(R.id.create_art_artist_identity);
        mArtistUserName = (EditText) layout.findViewById(R.id.aai_username);
        artistImage = (ImageView) layout.findViewById(R.id.aai_artist_image);
        mArtistExternalPlatform = (Spinner) layout.findViewById(R.id.aai_external_platform);
        mArtistExternalName = (Spinner) layout.findViewById(R.id.aai_userIdentityName);
        mArtistExposureLevel = (Spinner) layout.findViewById(R.id.art_exposureLevel);
        mArtistAcceptConnectionsType = (Spinner) layout.findViewById(R.id.art_artistAcceptConnectionsType);
        relativeLayout = (RelativeLayout) layout.findViewById(R.id.aai_layout_user_image);
        camEditButton = (ImageButton) layout.findViewById(R.id.art_cam_edit_image_button);

        mArtistExternalPlatform.getBackground().setColorFilter(Color.parseColor("#31C5F2"), PorterDuff.Mode.SRC_ATOP);
        mArtistExternalName.getBackground().setColorFilter(Color.parseColor("#31C5F2"), PorterDuff.Mode.SRC_ATOP);
        mArtistExposureLevel.getBackground().setColorFilter(Color.parseColor("#31C5F2"), PorterDuff.Mode.SRC_ATOP);
        mArtistAcceptConnectionsType.getBackground().setColorFilter(Color.parseColor("#31C5F2"), PorterDuff.Mode.SRC_ATOP);

        if (!isUpdate) {
            camEditButton.setImageResource(R.drawable.art_add_picture_button);
            createButton.setImageResource(R.drawable.art_create_button);
        } else {
            camEditButton.setImageResource(R.drawable.art_edit_picture_button);
            createButton.setImageResource(R.drawable.art_save_changes_button);
        }
        mArtistUserName.requestFocus();


        WarningCircle = (View) layout.findViewById(R.id.warning_cirlcle);

        WarningCircle.setVisibility(View.GONE);

        WarningLabel = (TextView) layout.findViewById(R.id.warning_label);

        WarningLabel.setVisibility(View.GONE);

        alias = (TextView) layout.findViewById(R.id.aai_username_label);


        /*


        mArtistExternalPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArtistExternalPlatform.setBackgroundColor(Color.parseColor("#919090"));
            }
        });

        mArtistExternalName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArtistExternalName.setBackgroundColor(Color.parseColor("#919090"));
            }
        });

*/

        mArtistUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alias.setTextColor(Color.parseColor("#919090"));


            }
        });

        mArtistUserName.addTextChangedListener(new TextWatcher() {
            /** flag to prevent loop call of onTextChanged() */
            private boolean setTextFlag = true;
            private Toast toastChar = null;



            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 if (mArtistUserName.getText().length() >= MAX_ALIAS_CHARACTER) {
                     //this to avoid toast accumulation
                     if (toastChar != null) toastChar.cancel();
                     toastChar = Toast.makeText(getActivity(), "Only "+MAX_ALIAS_CHARACTER+" chars allowed", Toast.LENGTH_SHORT);
                     toastChar.show();


                    // set the text to a string max length MAX_ALIAS_CHARACTER:
                    if (setTextFlag) {
                        setTextFlag = false;
                        mArtistUserName.setText(s.subSequence(0, MAX_ALIAS_CHARACTER));
                        mArtistUserName.setSelection(mArtistUserName.getText().length());
                    } else {
                        setTextFlag = true;
                    }
                }
            }

        });

        mArtistExternalName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //MIG
                //    mArtistExternalName.setBackgroundColor(Color.parseColor(NormalColor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Select a Platform...");
        arraySpinner.addAll(ArtExternalPlatform.getArrayItems());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.art_custom_spinner,
                arraySpinner);

        mArtistExternalPlatform.setAdapter(adapter);

        arraySpinner = ExposureLevel.getArrayItems();
        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.art_custom_spinner,
                arraySpinner);
        mArtistExposureLevel.setAdapter(adapter);

        arraySpinner = ArtistAcceptConnectionsType.getArrayItems();
        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.art_custom_spinner,
                arraySpinner);
        mArtistAcceptConnectionsType.setAdapter(adapter);
        externalPlatformSpinnerListener();
        mArtistUserName.requestFocus();
        registerForContextMenu(artistImage);

        camEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WarningLabel.setVisibility(View.GONE);
                CommonLogger.debug(TAG, "Entrando en CamImagenIcon.setOnClickListener");
                getActivity().openContextMenu(artistImage);
            }
        });

        artistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WarningLabel.setVisibility(View.GONE);
                CommonLogger.debug(TAG, "Entrando en ArtImage.setOnClickListener");
                getActivity().openContextMenu(artistImage);
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
                        Toast.makeText(getActivity(), "fill required items", Toast.LENGTH_LONG).show();
                        break;
                    case CREATE_IDENTITY_FAIL_MODULE_IS_NULL:
                        Toast.makeText(getActivity(), "The module manager is null", Toast.LENGTH_LONG).show();
                        break;


                    case ERROR_IMAGE_VIEW:
                        //WarningCircle.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "ERROR_IMAGE_VIEW", Toast.LENGTH_LONG).show();
                        break;
                    case ERROR_USER_DATA:

                        Toast.makeText(getActivity(), "ERROR_USER_DATA", Toast.LENGTH_LONG).show();
                        break;
                    case ERROR_BOTH:

                        Toast.makeText(getActivity(), "ERROR_BOTH", Toast.LENGTH_LONG).show();
                        break;


                }

            }

        });
    }


    private void setUpHelpTkyArtist(boolean checkButton) {
        try {
            PresentationDialog presentationDialog;
            presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.banner_artist_community)
                    .setIconRes(R.drawable.artist)
                    .setSubTitle(R.string.art_artist_identity_welcome_subTitle)
                    .setBody(R.string.art_artist_identity_welcome_body)
                    .setTextFooter(R.string.art_artist_identity_welcome_footer)
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


    private int createNewIdentity() throws InvalidParameterException {
        UUID externalIdentityID = null;
        ArtExternalPlatform artExternalPlatform = ArtExternalPlatform.getDefaultExternalPlatform();
        if (mArtistExternalPlatform.getSelectedItem() != mArtistExternalPlatform.getItemAtPosition(0)) {
            artExternalPlatform = ArtExternalPlatform.getArtExternalPlatformByLabel(
                    mArtistExternalPlatform.getSelectedItem().toString());
        }
        if (!mArtistExternalName.getSelectedItem().equals(mArtistExternalName.getItemAtPosition(0))) {
            if (artExternalPlatform != null) {
                List<UUID> identityByPlatformList = new ArrayList<>();
                try {
                    identityByPlatformList = getArtistIdentityIdByPlatform(artExternalPlatform);
                } catch (Exception e) {
                }
                if (!identityByPlatformList.isEmpty()) {
                    externalIdentityID = identityByPlatformList.get(mArtistExternalName.getSelectedItemPosition() - 1);
                }
            }
        }
        String artistName = mArtistUserName.getText().toString();
        String externalUsername = "";
        /*if(mArtistExternalPlatform.isSelected()){
        if (mArtistExternalPlatform.isSelected()) {
            externalPlatform = ArtExternalPlatform.getArtExternalPlatformByLabel(
                    mArtistExternalPlatform.getSelectedItem().toString());
        }*/
        if (mArtistExternalName.getCount() > 1) {
            if (mArtistExternalName.getSelectedItemPosition() > 0) {
                externalUsername = mArtistExternalName.getSelectedItem().toString();
            }
        }

        ExposureLevel exposureLevel = ExposureLevel.getExposureLevelByLabel(mArtistExposureLevel.getSelectedItem().toString());
        ArtistAcceptConnectionsType artistAcceptConnectionsType = ArtistAcceptConnectionsType.getArtistAcceptConnectionsTypeByLabel(mArtistAcceptConnectionsType.getSelectedItem().toString());

        boolean dataIsValid = validateIdentityData(
                artistName,
                artistImageByteArray,
                externalIdentityID);
        if (dataIsValid) {
            if (moduleManager != null) {
                try {
                    if (!isUpdate) {
                        moduleManager.createArtistIdentity(
                                artistName,
                                (artistImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : artistImageByteArray,
                                externalUsername,
                                exposureLevel,
                                artistAcceptConnectionsType,
                                externalIdentityID,
                                artExternalPlatform);
                    } else {
                        byte[] currentImage;
                        if (updateProfileImage) {
                            currentImage = checkedImage();
                            moduleManager.updateArtistIdentity(
                                    artistName,
                                    identitySelected.getPublicKey(),
                                    currentImage,
                                    exposureLevel,
                                    artistAcceptConnectionsType,
                                    externalIdentityID,
                                    artExternalPlatform,
                                    externalUsername);
                        } else
                            moduleManager.updateArtistIdentity(
                                    artistName,
                                    identitySelected.getPublicKey(),
                                    (artistImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : artistImageByteArray,
                                    exposureLevel,
                                    artistAcceptConnectionsType,
                                    externalIdentityID,
                                    artExternalPlatform,
                                    externalUsername);
                    }
                } catch (Exception e) {
                    errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                    e.printStackTrace();
                }
                return CREATE_IDENTITY_SUCCESS;
            }
            return CREATE_IDENTITY_FAIL_MODULE_IS_NULL;
        }
        return CREATE_IDENTITY_FAIL_NO_VALID_DATA;
    }

    private byte[] checkedImage() {


        if (artistImageByteArray == null) {
            return convertImage(R.drawable.ic_profile_male);
        }

        return artistImageByteArray;
    }


    private boolean validateIdentityData(
            String ArtistExternalName,
            byte[] ArtistImageBytes,
            UUID externalIdentityID) {

        if (ArtistImageBytes == null) {
            //  WarningCircle.setVisibility(View.VISIBLE);
            //WarningLabel.setVisibility(View.VISIBLE);
        }

        if (mArtistExternalPlatform.getSelectedItemPosition() == 0) {
            //mArtistExternalPlatform.setBackgroundColor(Color.parseColor(WarningColor));
        } else {
            //MIG
            //mArtistExternalPlatform.setBackgroundColor(Color.parseColor(NormalColor));
        }


        /*
        if(mArtistExternalName.getSelectedItemPosition()==0){
            mArtistExternalName.setBackgroundColor(Color.parseColor("#DF0101"));
        }else{
            mArtistExternalName.setBackgroundColor(Color.parseColor("#0080FF"));}
*/

        if (ArtistExternalName.isEmpty()) {
            //mArtistUserName.setHintTextColor(Color.parseColor(WarningColor));
            alias.setTextColor(Color.parseColor(WarningColor));
        }

        if (ArtistExternalName.isEmpty()) {

            return false;
        }

        boolean identitySelectedHasID;
        try {
            identitySelectedHasID = identitySelected.getExternalIdentityID() != null;
        } catch (Exception e) {
            identitySelectedHasID = false;
        }
        if (externalIdentityID == null && identitySelectedHasID && isUpdate) {

            return false;
        }
        /*
        if (ArtistImageBytes == null){

            return false;}
            */
        /*
        if (ArtistImageBytes.length > 0){

            return true;}
            */
        return true;
    }

    private byte[] convertImage(int resImage) {
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }


    private void externalPlatformSpinnerListener() {
        mArtistExternalPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //MIG
                //    mArtistExternalPlatform.setBackgroundColor(Color.parseColor(NormalColor));
                try {
                    if (!updateCheck) {
                        List<String> arraySpinner = new ArrayList<>();
                        arraySpinner.add("Select an Identity...");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getActivity(),
                                R.layout.art_custom_spinner,
                                arraySpinner
                        );
                        if (!mArtistExternalPlatform.getSelectedItem().equals(mArtistExternalPlatform.getItemAtPosition(0))) {
                            ArtExternalPlatform externalPlatform = ArtExternalPlatform.getArtExternalPlatformByLabel(parent.getItemAtPosition(position).toString());
                            if (externalPlatform != null) {
                                List<String> identityByPlatformList = getArtistIdentityByPlatform(externalPlatform);
                                if (!identityByPlatformList.isEmpty()) {
                                    arraySpinner.addAll(identityByPlatformList);
                                    adapter = new ArrayAdapter<String>(
                                            getActivity(),
                                            R.layout.art_custom_spinner,
                                            arraySpinner
                                    );
                                }
                            }
                        }
                        mArtistExternalName.setAdapter(adapter);
                        mArtistExternalName.setSelection(0);
                    }
                    updateCheck = false;
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(
                            SubApps.ART_ARTIST_IDENTITY,
                            UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT,
                            e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private List<String> getArtistIdentityByPlatform(ArtExternalPlatform externalPlatform) throws Exception {
        List<String> identityNameList = new ArrayList<>();
        HashMap<ArtExternalPlatform,HashMap<UUID,String>> artExternalPlatform =
                moduleManager.listExternalIdentitiesFromCurrentDeviceUser();
        if(artExternalPlatform!=null){
            HashMap<UUID, String> artistIdentityByPlatform = artExternalPlatform.get(externalPlatform);
            if (artistIdentityByPlatform != null) {
                Iterator<Map.Entry<UUID, String>> entries2 = artistIdentityByPlatform.entrySet().iterator();
                while (entries2.hasNext()) {
                    Map.Entry<UUID, String> entry2 = entries2.next();
                    identityNameList.add(entry2.getValue());
                }
                return identityNameList;
            }
            return identityNameList;
        }
        return identityNameList;
    }

    private List<UUID> getArtistIdentityIdByPlatform(ArtExternalPlatform externalPlatform) throws Exception {
        HashMap<UUID, String> artistIdentityByPlatform = moduleManager.listExternalIdentitiesFromCurrentDeviceUser().get(externalPlatform);
        List<UUID> identityIdList = new ArrayList<>();
        if (artistIdentityByPlatform != null) {
            Iterator<Map.Entry<UUID, String>> entries2 = artistIdentityByPlatform.entrySet().iterator();
            while (entries2.hasNext()) {
                Map.Entry<UUID, String> entry2 = entries2.next();
                identityIdList.add(entry2.getKey());
            }
            return identityIdList;
        }
        return identityIdList;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            ImageView pictureView = artistImage;
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
                    artistImageByteArray = toByteArray(imageBitmap);
                    updateProfileImage = true;
                    //Picasso.with(getActivity()).load(selectedImage2).transform(new CircleTransform()).into(artistImage);
                    //updateProfileImage = true;
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
                            artistImageByteArray = toByteArray(imageBitmap);
                            updateProfileImage = true;
                           // Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(artistImage);
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

                matrix.preRotate(rotationInDegrees);

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
        if (cursor != null) {
            column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Choose mode");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
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
                case CONTEXT_MENU_DELETE:
                    DeletePicture();
                    contextMenuDelete = true;
                    return true;
                case CONTEXT_MENU_TURN_RIGHT:
                    turnpicture(90f);
                    return true;
                case CONTEXT_MENU_TURN_LEFT:
                    turnpicture(-90f);
                    return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void turnpicture(float rotationInDegrees) {
        ImageView pictureView = artistImage;
        Bitmap bitmap = ((RoundedBitmapDrawable)pictureView.getDrawable()).getBitmap();
        Matrix matrix = new Matrix();
        matrix.preRotate(rotationInDegrees);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        artistImage.setImageDrawable(
                ImagesUtils.getRoundedBitmap(
                        getResources(), rotatedBitmap));
        artistImageByteArray = toByteArray(rotatedBitmap);
        contextMenuInUse = false;
    }

    private void DeletePicture() {
        artistImage.setImageDrawable(null);
        artistImageByteArray = null;
        camEditButton.setBackgroundResource(R.drawable.art_edit_picture_button);
        updateProfileImage = false;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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

            if (id == 99) {

            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(
                    UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


}//main