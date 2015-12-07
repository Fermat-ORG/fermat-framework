package com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.CommonLogger;
import com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker;

import java.util.concurrent.ExecutorService;

import static com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession.IDENTITY_INFO;
import static com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker.DATA_NOT_CHANGED;
import static com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker.INVALID_ENTRY_DATA;
import static com.bitdubai.sub_app.crypto_broker_identity.util.PublishIdentityWorker.SUCCESS;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCryptoBrokerIdentityFragment extends FermatFragment implements FermatWorkerCallBack {
    // Constants
    private static final String TAG = "EditBrokerIdentity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    // data
    private Bitmap cryptoBrokerBitmap;
    private boolean wantPublishIdentity;

    // Managers
    private ErrorManager errorManager;

    private ImageView mBrokerImage;

    private ExecutorService executor;


    public static EditCryptoBrokerIdentityFragment newInstance() {
        return new EditCryptoBrokerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            errorManager = subAppsSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootLayout = inflater.inflate(R.layout.fragment_edit_crypto_broker_identity, container, false);
        initViews(rootLayout);


        return rootLayout;
    }

    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        Button mUpdateButton = (Button) layout.findViewById(R.id.update_crypto_broker_button);
        FermatTextView mBrokerName = (FermatTextView) layout.findViewById(R.id.crypto_broker_name);
        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        FermatCheckBox publishIdentityCheckBox = (FermatCheckBox) layout.findViewById(R.id.publish_identity);

        final CryptoBrokerIdentityInformation identityInfo = (CryptoBrokerIdentityInformation) subAppsSession.getData(IDENTITY_INFO);

        if (identityInfo != null) {
            mBrokerName.setText(identityInfo.getAlias());

            byte[] profileImage = identityInfo.getProfileImage();
            RoundedBitmapDrawable roundedBitmap = (profileImage != null) ?
                    ImagesUtils.getRoundedBitmap(getResources(), profileImage) :
                    ImagesUtils.getRoundedBitmap(getResources(), R.drawable.img_new_user_camera);

            mBrokerImage.setImageDrawable(roundedBitmap);

            wantPublishIdentity = identityInfo.isPublished();
            publishIdentityCheckBox.setChecked(wantPublishIdentity);
        }

        publishIdentityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean value) {
                CommonLogger.debug(TAG, "IN publishIdentityCheckBox.setOnCheckedChangeListener");
                wantPublishIdentity = value;
            }
        });

        mBrokerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "IN mBrokerImage.setOnClickListener");
                registerForContextMenu(mBrokerImage);
                getActivity().openContextMenu(mBrokerImage);
            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonLogger.debug(TAG, "IN mUpdateButton.setOnClickListener");
                editIdentityInfo();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ImageView pictureView = mBrokerImage;

            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    cryptoBrokerBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            cryptoBrokerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            cryptoBrokerBitmap = Bitmap.createScaledBitmap(cryptoBrokerBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error loading image.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            if (pictureView != null && cryptoBrokerBitmap != null) {
                RoundedBitmapDrawable roundedBitmap = ImagesUtils.getRoundedBitmap(getResources(), cryptoBrokerBitmap);
                pictureView.setImageDrawable(roundedBitmap);
            }
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
        switch (item.getItemId()) {
            case CONTEXT_MENU_CAMERA:
                dispatchTakePictureIntent();
                break;
            case CONTEXT_MENU_GALLERY:
                loadImageFromGallery();
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * Edita la informacion de la identidad que se muestra actualmente
     */
    private void editIdentityInfo() {

        // TODO falta implementar funcionalidad para editar info del identity en el backend

        PublishIdentityWorker publishIdentityWorker = new PublishIdentityWorker(getActivity(), subAppsSession, wantPublishIdentity, this);

        executor = publishIdentityWorker.execute();
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

        Intent loadImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }

    @Override
    public void onPostExecute(Object... result) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        if (result.length > 0) {
            int resultCode = (int) result[0];

            if (resultCode == SUCCESS || resultCode == DATA_NOT_CHANGED) {
                changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY.getCode(), subAppsSession.getAppPublicKey());

            } else if (resultCode == INVALID_ENTRY_DATA) {
                Toast.makeText(getActivity(), "Please check the submitted data", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        Toast.makeText(getActivity().getApplicationContext(),
                "Error trying to change the exposure level.",
                Toast.LENGTH_SHORT).
                show();

        errorManager.reportUnexpectedSubAppException(
                SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                ex);
    }
}
