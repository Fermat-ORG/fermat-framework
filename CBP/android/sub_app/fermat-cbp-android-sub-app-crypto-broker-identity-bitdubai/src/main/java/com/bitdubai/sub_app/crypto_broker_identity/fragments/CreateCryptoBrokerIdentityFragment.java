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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.CommonLogger;
import com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor;

import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.EXCEPTION_THROWN;
import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.INVALID_ENTRY_DATA;
import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoBrokerIdentityFragment extends FermatFragment {
    private static final String TAG = "CreateBrokerIdentity";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private Bitmap cryptoBrokerBitmap;

    private ErrorManager errorManager;

    private EditText mBrokerName;
    private ImageView mBrokerImage;


    public static CreateCryptoBrokerIdentityFragment newInstance() {
        return new CreateCryptoBrokerIdentityFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootLayout = inflater.inflate(R.layout.fragment_create_crypto_broker_identity, container, false);
        initViews(rootLayout);

        return rootLayout;
    }

    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {

        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        mBrokerName.requestFocus();

        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        RoundedBitmapDrawable roundedBitmap = ImagesUtils.getRoundedBitmap(getResources(), R.drawable.img_new_user_camera);
        mBrokerImage.setImageDrawable(roundedBitmap);
        mBrokerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(mBrokerImage);
                getActivity().openContextMenu(mBrokerImage);
            }
        });

        Button createButton = (Button) layout.findViewById(R.id.create_crypto_broker_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewIdentity();
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
                    } catch (Exception ex) {
                        errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, ex);
                        Toast.makeText(getActivity(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
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
        menu.setHeaderTitle(R.string.title_photo_context_menu);
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, R.string.camera_option_context_menu);
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, R.string.gallery_option_context_menu);

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
     * Crea una nueva identidad para un crypto broker
     */
    private void createNewIdentity() {

        String brokerNameText = mBrokerName.getText().toString();
        byte[] imgInBytes = ImagesUtils.toByteArray(cryptoBrokerBitmap);

        CreateBrokerIdentityExecutor executor = new CreateBrokerIdentityExecutor(subAppsSession, brokerNameText, imgInBytes);
        int resultKey = executor.execute();

        switch (resultKey) {
            case SUCCESS:
                changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY.getCode());
                break;
            case EXCEPTION_THROWN:
                Toast.makeText(getActivity(), "Error al crear la identidad", Toast.LENGTH_LONG).show();
                break;
            case INVALID_ENTRY_DATA:
                Toast.makeText(getActivity(), "Los datos para crear la indentidad no son validos", Toast.LENGTH_LONG).show();
                break;
        }
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
}
