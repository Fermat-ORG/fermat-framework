package com.bitdubai.sub_app.crypto_customer_identity.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
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
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.utils.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.Utils.CryptoCustomerIdentityInformationImpl;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.util.EditCustomerIdentityWorker;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;

import static com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession.IDENTITY_INFO;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCryptoCustomerIdentityFragment extends AbstractFermatFragment implements FermatWorkerCallBack {
    // Constants
    private static final String TAG = "EditBrokerIdentity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    // data
    private Bitmap cryptoCustomerBitmap;

    // Managers
    private CryptoCustomerIdentityModuleManager moduleManager;
    private ErrorManager errorManager;

    // UI
    private EditText mBrokerName;
    private ImageView mBrokerImage;

    private boolean actualizable;

    private byte[] profileImage;

    private ImageView camara;
    private ImageView galeria;

    private String cryptoCustomerPublicKey;

    private ExecutorService executor;

    private FermatListItemListeners<CryptoCustomerIdentityInformation> mCallback;


    public static EditCryptoCustomerIdentityFragment newInstance() {
        return new EditCryptoCustomerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_edit_crypto_customer_identity, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    /**
     * Inicializa las vistas de este Fragment
     *
     * @param layout el layout de este Fragment que contiene las vistas
     */
    private void initViews(View layout) {
        Button botonU = (Button) layout.findViewById(R.id.update_crypto_customer_button);
        botonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizable = false;
                editIdentityInfoInBackDevice();
            }
        });
        actualizable = true;
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_customer_name);
        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_customer_image);
        camara = (ImageView) layout.findViewById(R.id.camara);
        galeria = (ImageView) layout.findViewById(R.id.galeria);
        CryptoCustomerIdentityInformation identityInfo = (CryptoCustomerIdentityInformation) appSession.getData(IDENTITY_INFO);
        if (identityInfo != null) {
            cryptoCustomerPublicKey = identityInfo.getPublicKey();
            mBrokerName.setText(identityInfo.getAlias());
            mBrokerName.selectAll();
            mBrokerName.requestFocus();
            mBrokerName.performClick();
            profileImage = identityInfo.getProfileImage();
            ByteArrayInputStream bytes = new ByteArrayInputStream(profileImage);
            BitmapDrawable bmd = new BitmapDrawable(bytes);
            mBrokerImage.setImageBitmap(bmd.getBitmap());
        }
        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (actualizable) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Save Changes?")
                            .setMessage("You want to save changes?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    editIdentityInfoInBackDevice();
                                }
                            }).create().show();
                    actualizable = false;
                }
            }
        });
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();
            }
        });
        ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ImageView pictureView = mBrokerImage;
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
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error loading image.", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            if (pictureView != null && cryptoCustomerBitmap != null) {
                pictureView.setImageBitmap(cryptoCustomerBitmap);
            }
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void editIdentityInfoInBackDevice() {
        String brokerNameText = mBrokerName.getText().toString();
        byte[] imgInBytes;
        if (cryptoCustomerBitmap != null) {
            imgInBytes = ImagesUtils.toByteArray(cryptoCustomerBitmap);
        }else{
            imgInBytes = profileImage;
        }
        if(brokerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_LONG).show();
        }else{
            if(imgInBytes == null){
                Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            }else{
                if(cryptoCustomerPublicKey != null) {
                    CryptoCustomerIdentityInformationImpl identity = new CryptoCustomerIdentityInformationImpl(brokerNameText, cryptoCustomerPublicKey, imgInBytes, ExposureLevel.PUBLISH);
                    EditCustomerIdentityWorker EditIdentityWorker = new EditCustomerIdentityWorker(getActivity(), appSession, identity, this);
                    executor = EditIdentityWorker.execute();
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

    @Override
    public void onPostExecute(Object... result) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
        if (result.length > 0) {
            int resultCode = (int) result[0];
            if (resultCode == 1) {
                Toast.makeText(getActivity(), "Crypto Customer Identity Updated.", Toast.LENGTH_LONG).show();
            } else if (resultCode == 4) {
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
        Toast.makeText(getActivity().getApplicationContext(), "Error trying to edit the identity.", Toast.LENGTH_SHORT).show();
        errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
    }
}
