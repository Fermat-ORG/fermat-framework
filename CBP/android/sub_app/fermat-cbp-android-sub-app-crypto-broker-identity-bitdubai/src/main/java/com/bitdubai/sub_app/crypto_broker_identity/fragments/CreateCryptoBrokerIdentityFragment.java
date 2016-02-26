package com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.CommonLogger;
import com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor;

import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.EXCEPTION_THROWN;
import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.INVALID_ENTRY_DATA;
import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.MISSING_IMAGE;
import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoBrokerIdentityFragment extends AbstractFermatFragment {

    private static final String TAG = "CreateBrokerIdentity";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private Bitmap cryptoBrokerBitmap;

    private ErrorManager errorManager;

    private EditText mBrokerName;
    private ImageView mBrokerImage;

    private ImageView camara;
    private ImageView galeria;

    private boolean actualizable;

    public static CreateCryptoBrokerIdentityFragment newInstance() {
        return new CreateCryptoBrokerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            errorManager = appSession.getErrorManager();
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
        actualizable = true;
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        Button botonG = (Button) layout.findViewById(R.id.create_crypto_broker_button);

        botonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewIdentityInBackDevice();
            }
        });

        mBrokerName.requestFocus();
        mBrokerName.performClick();
        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (actualizable) {
                    //createNewIdentityInBackDevice();
                    actualizable = false;
                }
            }
        });
        camara = (ImageView) layout.findViewById(R.id.camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        galeria = (ImageView) layout.findViewById(R.id.galeria);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();
            }
        });
        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        mBrokerImage.setImageResource(R.drawable.img_new_user_camera);
        ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(actualizable) {
            //createNewIdentityInBackDevice();
            actualizable = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                        }
                    } catch (Exception ex) {
                        errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, ex);
                        Toast.makeText(getActivity(), "Cannot load image.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            if (pictureView != null && cryptoBrokerBitmap != null) {
                pictureView.setImageBitmap(cryptoBrokerBitmap);
            }
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void createNewIdentityInBackDevice(){
        String brokerNameText = mBrokerName.getText().toString();
        if(brokerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "The alias must not be empty", Toast.LENGTH_LONG).show();
        }else{
            if (cryptoBrokerBitmap == null) {
                Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            }else{
                byte[] imgInBytes = ImagesUtils.toByteArray(cryptoBrokerBitmap);
                CreateBrokerIdentityExecutor executor = new CreateBrokerIdentityExecutor(appSession, brokerNameText, imgInBytes);
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        Toast.makeText(getActivity(), "Crypto Broker Identity Created.", Toast.LENGTH_LONG).show();
                        changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY, appSession.getAppPublicKey());
                    break;
                }
            }
        }
    }

    private void dispatchTakePictureIntent() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void loadImageFromGallery() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent loadImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }
}
