package fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.FanIdentityManagerModule;
import com.bitdubai.fermat_art_api.layer.sub_app_module.identity.FanIdentitySettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.fan_identity.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import popup.PresentationArtFanUserIdentityDialog;
import sessions.ArtFanUserIdentitySubAppSession;
import sessions.SessionConstants;
import util.CommonLogger;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/04/16.
 */
public class CreateArtFanUserIdentityFragment extends AbstractFermatFragment {
    private static final String TAG = "CreateTokenlyFanIdentity";
    private static final int CREATE_IDENTITY_FAIL_MODULE_IS_NULL = 0;
    private static final int CREATE_IDENTITY_FAIL_NO_VALID_DATA = 1;
    private static final int CREATE_IDENTITY_FAIL_MODULE_EXCEPTION = 2;
    private static final int CREATE_IDENTITY_SUCCESS = 3;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    private ArtFanUserIdentitySubAppSession artFanUserIdentitySubAppSession;
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
    private EditText mFanExternalPassword;
    private Spinner mFanExternalPlatform;
    private SettingsManager<FanIdentitySettings> settingsManager;
    private FanIdentitySettings fanIdentitySettings = null;
    private boolean updateProfileImage = false;
    private boolean contextMenuInUse = false;


    private Handler handler;

    public static CreateArtFanUserIdentityFragment newInstance(){
        return new CreateArtFanUserIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            artFanUserIdentitySubAppSession = (ArtFanUserIdentitySubAppSession) appSession;
            moduleManager = artFanUserIdentitySubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            setHasOptionsMenu(false);
            settingsManager = artFanUserIdentitySubAppSession.getModuleManager().
                    getSettingsManager();

            try {
                if (artFanUserIdentitySubAppSession.getAppPublicKey()!= null){
                    fanIdentitySettings = settingsManager.loadAndGetSettings(
                            artFanUserIdentitySubAppSession.getAppPublicKey());
                }else{
                    fanIdentitySettings = settingsManager.loadAndGetSettings("art_fan_identity");
                }

            } catch (Exception e) {
                fanIdentitySettings = null;
            }

            if (fanIdentitySettings == null) {
                fanIdentitySettings = new FanIdentitySettings();
                fanIdentitySettings.setIsPresentationHelpEnabled(false);
                if(settingsManager != null){
                    if (artFanUserIdentitySubAppSession.getAppPublicKey()!=null){
                        settingsManager.persistSettings(
                                artFanUserIdentitySubAppSession.getAppPublicKey(), fanIdentitySettings);
                    }else{
                        settingsManager.persistSettings("art_fan_identity", fanIdentitySettings);
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
        return rootLayout;
    }

    public void showDialog(){
        PresentationArtFanUserIdentityDialog fanUserIdentityDialog =
                new PresentationArtFanUserIdentityDialog(
                        getActivity(),
                        artFanUserIdentitySubAppSession,
                        null,
                        moduleManager);
        fanUserIdentityDialog.show();
    }
    private void setUpIdentity() {
        try {
            identitySelected = (Fanatic) artFanUserIdentitySubAppSession.getData(
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
                bitmap = BitmapFactory.decodeByteArray(
                        identitySelected.getProfileImage(),
                        0,
                        identitySelected.getProfileImage().length);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.afi_profile_male);
            }
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            fanImageByteArray = toByteArray(bitmap);
            fanImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), bitmap));
        }
        mFanExternalUserName.setText(identitySelected.getAlias());
        List<String> arraySpinner = ArtExternalPlatform.getArrayItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mFanExternalPlatform.setAdapter(adapter);
        ArtExternalPlatform[] externalPlatforms = ArtExternalPlatform.values();
        for (int i=0; i<externalPlatforms.length;i++){
            if(externalPlatforms[i].getCode().equals(
                    identitySelected.getExternalPlatform().getCode())){
                mFanExternalPlatform.setSelection(i);
                break;
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
        mFanExternalUserName = (EditText) layout.findViewById(R.id.afi_alias_label);
        mFanExternalPassword = (EditText) layout.findViewById(R.id.afi_status_label);
        fanImage = (ImageView) layout.findViewById(R.id.afi_fan_image);
        mFanExternalPlatform = (Spinner) layout.findViewById(R.id.afi_external_platform);
        relativeLayout = (RelativeLayout) layout.findViewById(R.id.afi_user_image);
        createButton.setText((!isUpdate) ? "Create" : "Update");
        mFanExternalUserName.requestFocus();
        List<String> arraySpinner = ArtExternalPlatform.getArrayItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner);
        mFanExternalPlatform.setAdapter(adapter);
        mFanExternalUserName.requestFocus();
        registerForContextMenu(fanImage);
        fanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "get in on fanImage.setOnClickListener");
                getActivity().openContextMenu(fanImage);
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
                        } else {
                            Toast.makeText(getActivity(), "Changes saved", Toast.LENGTH_SHORT).show();
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
    }

    /**
     * Creates a new Fan identity in Art platform .
     *
     * @return key */
    private int createNewIdentity() {

        String fanExternalName = mFanExternalUserName.getText().toString();
        String fanPassword = "";
        if (!mFanExternalPassword.getText().toString().isEmpty()){
            fanPassword = mFanExternalPassword.getText().toString();
        }
        ArtExternalPlatform externalPlatform = ArtExternalPlatform.getDefaultExternalPlatform();
        if(mFanExternalPlatform.isSelected()){
            externalPlatform = ArtExternalPlatform.getArtExternalPlatformByLabel(
                    mFanExternalPlatform.getSelectedItem().toString());
        }
        boolean dataIsValid = validateIdentityData(
                fanExternalName,
                fanPassword,
                fanImageByteArray,
                externalPlatform);
        if (dataIsValid) {
            if (moduleManager != null) {
                try {
                    if (!isUpdate)
                        new ManageIdentity(
                                fanExternalName,
                                fanPassword,
                                externalPlatform,
                                ManageIdentity.CREATE_IDENTITY).execute();
                    else
                    if(updateProfileImage)
                        new ManageIdentity(
                                fanExternalName,
                                fanPassword,
                                externalPlatform,
                                ManageIdentity.UPDATE_IMAGE_IDENTITY).execute();
                    else
                        new ManageIdentity(
                                fanExternalName,
                                fanPassword,
                                externalPlatform,
                                ManageIdentity.UPDATE_IDENTITY).execute();
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
     * @param fanPassWord
     * @param fanImageBytes
     * @param externalPlatform
     * @return
     */
    private boolean validateIdentityData(
            String fanExternalName,
            String fanPassWord,
            byte[] fanImageBytes,
            ArtExternalPlatform externalPlatform) {
        if (fanExternalName.isEmpty())
            return false;
        if (fanPassWord.isEmpty())
            return false;
        if (fanImageBytes == null)
            return false;
        if (fanImageBytes.length > 0)
            return true;
//        if(externalPlatform != null)
//            return  true;
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                            fanImageByteArray = toByteArray(imageBitmap);
                            updateProfileImage = true;
                            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(fanImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            if (pictureView != null && imageBitmap != null)
                pictureView.setImageDrawable(
                        ImagesUtils.getRoundedBitmap(
                                getResources(), imageBitmap));
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Choose mode");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.afi_camera_green));
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
                showDialog();


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(
                    UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
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
        String fanStatus;
        ArtExternalPlatform externalPlatform;
        int identityAction;
        public static final int CREATE_IDENTITY = 0;
        public static final int UPDATE_IDENTITY = 1;
        public static final int UPDATE_IMAGE_IDENTITY = 2;

        public ManageIdentity(
                String fanExternalName,
                String fanStatus,
                ArtExternalPlatform externalPlatform,
                int identityAction
        ) {
            this.fanExternalName = fanExternalName;
            this.fanStatus = fanStatus;
            this.externalPlatform = externalPlatform;
            this.identityAction = identityAction;
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            try{
                switch (identityAction){
                    case CREATE_IDENTITY:
                        createIdentity(fanExternalName, fanStatus,externalPlatform);
                        break;
                    case UPDATE_IDENTITY:
                        updateIdentity(fanExternalName, fanStatus,externalPlatform);
                        break;
                    case UPDATE_IMAGE_IDENTITY:
                        updateIdentityImage(fanExternalName, fanStatus,externalPlatform);
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
            }
            return null;
        }
    }

    private void createIdentity(
            String fanExternalName,
            String fanPassword,
            ArtExternalPlatform externalPlatform) throws
            CantCreateFanIdentityException, FanIdentityAlreadyExistsException {
        moduleManager.createFanaticIdentity(
                fanExternalName,(fanImageByteArray == null) ? convertImage(R.drawable.ic_profile_male) : fanImageByteArray,
                UUID.randomUUID()) ;
    }

    private void updateIdentity(
            String fanExternalName,
            String fanPassword,
            ArtExternalPlatform externalPlatform) throws CantUpdateFanIdentityException {
        moduleManager.updateFanIdentity(
                fanExternalName,
                identitySelected.getPublicKey(),
                identitySelected.getProfileImage(),
                identitySelected.getExternalIdentityID());
    }

    private void updateIdentityImage(
            String fanExternalName,
            String fanPassword,
            ArtExternalPlatform externalPlatform) throws CantUpdateFanIdentityException {
        moduleManager.updateFanIdentity(
                fanExternalName,
                identitySelected.getPublicKey(),
                fanImageByteArray,
                identitySelected.getExternalIdentityID());
    }

}

