package com.bitdubai.sub_app.crypto_customer_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession;
import com.bitdubai.sub_app.crypto_customer_identity.util.CommonLogger;
import com.bitdubai.sub_app.crypto_customer_identity.util.CreateCustomerIdentityExecutor;

import static com.bitdubai.sub_app.crypto_customer_identity.util.CreateCustomerIdentityExecutor.EXCEPTION_THROWN;
import static com.bitdubai.sub_app.crypto_customer_identity.util.CreateCustomerIdentityExecutor.INVALID_ENTRY_DATA;
import static com.bitdubai.sub_app.crypto_customer_identity.util.CreateCustomerIdentityExecutor.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoCustomerIdentityFragment extends AbstractFermatFragment<CryptoCustomerIdentitySubAppSession, ResourceProviderManager> {
    private static final String TAG = "CreateCustomerIdentity";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private Bitmap cryptoCustomerBitmap;

    private ErrorManager errorManager;

    private EditText mCustomerName;
    private ImageView mCustomerImage;

    private ImageView camara;
    private ImageView galeria;

    private boolean actualizable;


    public static CreateCryptoCustomerIdentityFragment newInstance() {
        return new CreateCryptoCustomerIdentityFragment();
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

        View rootLayout = inflater.inflate(R.layout.fragment_create_crypto_customer_identity, container, false);
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
        mCustomerName = (EditText) layout.findViewById(R.id.crypto_customer_name);
        mCustomerName.requestFocus();
        mCustomerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (actualizable) {
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    createNewIdentityInBackDevice();
                    actualizable = false;
                }
            }
        });

        mCustomerImage = (ImageView) layout.findViewById(R.id.crypto_customer_image);
        mCustomerImage.setImageResource(R.drawable.img_new_user_camera);
        mCustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(mCustomerImage);
                getActivity().openContextMenu(mCustomerImage);
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

        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ImageView pictureView = mCustomerImage;

            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    cryptoCustomerBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            cryptoCustomerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                        }
                    } catch (Exception ex) {
                        errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, ex);
                        Toast.makeText(getActivity(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            if (pictureView != null && cryptoCustomerBitmap != null) {
                pictureView.setImageBitmap(cryptoCustomerBitmap);
            }
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
     * Crea una nueva identidad para un crypto customer
     */
    private void createNewIdentityInBackDevice() {
        String customerNameText = mCustomerName.getText().toString();
        if (!customerNameText.trim().equals("")) {
            if (cryptoCustomerBitmap != null) {
                byte[] imgInBytes = ImagesUtils.toByteArray(cryptoCustomerBitmap);
                final CreateCustomerIdentityExecutor executor = new CreateCustomerIdentityExecutor(appSession, customerNameText, imgInBytes);
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        Toast.makeText(getActivity(), "Crypto Customer Identity Created.", Toast.LENGTH_LONG).show();
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    appSession.getModuleManager().publishCryptoCustomerIdentity(executor.getIdentity().getPublicKey());
                                } catch (CouldNotPublishCryptoCustomerException e) {
                                    Toast.makeText(getActivity(), "Error al publicar la identidad", Toast.LENGTH_LONG).show();
                                }
                            }
                        }.start();
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
        Intent loadImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }
}
