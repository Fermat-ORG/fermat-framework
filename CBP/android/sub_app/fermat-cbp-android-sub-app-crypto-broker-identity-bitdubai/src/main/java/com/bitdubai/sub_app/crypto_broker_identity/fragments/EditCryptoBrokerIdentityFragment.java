package com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.utils.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.EditIdentityWorker;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;

import static com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession.IDENTITY_INFO;
import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.SUCCESS;


/**
 * A simple {@link Fragment} subclass.
 */

public class EditCryptoBrokerIdentityFragment extends AbstractFermatFragment implements FermatWorkerCallBack {
    // Constants
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    // data
    private Bitmap cryptoBrokerBitmap;
    private boolean wantPublishIdentity;
    private String cryptoBrokerPublicKey;

    private boolean actualizable;

    // Managers
    private ErrorManager errorManager;

    private ImageView mBrokerImage;
    private ImageView sw;
    private EditText mBrokerName;

    private ExecutorService executor;
    private byte[] profileImage;


    public static EditCryptoBrokerIdentityFragment newInstance() {
        return new EditCryptoBrokerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            errorManager = appSession.getErrorManager();
        }catch (Exception e){
            if(errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            else
                Log.e("EditCustomerIdentity", e.getMessage(), e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        final Button botonU = (Button) layout.findViewById(R.id.update_crypto_broker_button);

        actualizable = true;

        mBrokerName    = (EditText) layout.findViewById(R.id.crypto_broker_name);
        mBrokerImage   = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        sw             = (ImageView) layout.findViewById(R.id.sw);
        final ImageView camara = (ImageView) layout.findViewById(R.id.camara);
        final ImageView galeria = (ImageView) layout.findViewById(R.id.galeria);

        final CryptoBrokerIdentityInformation identityInfo = (CryptoBrokerIdentityInformation) appSession.getData(IDENTITY_INFO);


        botonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizable = false;
                editIdentityInfoInBackDevice("onClick");
            }
        });

        if (identityInfo != null) {

            cryptoBrokerPublicKey = identityInfo.getPublicKey();
            mBrokerName.setText(identityInfo.getAlias());
            mBrokerName.selectAll();
            mBrokerName.requestFocus();
            mBrokerName.performClick();
            wantPublishIdentity = identityInfo.isPublished();

            profileImage = identityInfo.getProfileImage();

            if(profileImage.length == 0){
                mBrokerImage.setImageResource(R.drawable.pic_space);
            }else{
                BitmapDrawable bmd = new BitmapDrawable(getResources(), new ByteArrayInputStream(profileImage));
                mBrokerImage.setImageBitmap(bmd.getBitmap());
            }
        }

        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (actualizable) {
                    actualizable = false;
                    //editIdentityInfoInBackDevice("onFocus");
                }
            }
        });

        if(wantPublishIdentity){
            sw.setImageResource(R.drawable.switch_visible);
        }else{
            sw.setImageResource(R.drawable.switch_notvisible);
        }

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

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wantPublishIdentity) {
                    sw.setImageResource(R.drawable.switch_notvisible);
                    wantPublishIdentity = false;
                } else {
                    sw.setImageResource(R.drawable.switch_visible);
                    wantPublishIdentity = true;
                }
            }
        });

        ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
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
                            cryptoBrokerBitmap = Bitmap.createScaledBitmap(cryptoBrokerBitmap, mBrokerImage.getWidth(), mBrokerImage.getHeight(), true);
                            Picasso.with(getActivity()).load(selectedImage).into(mBrokerImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void editIdentityInfoInBackDevice(String d) {
        String brokerNameText = mBrokerName.getText().toString();
        ExposureLevel ex;
        byte[] imgInBytes;
        if (wantPublishIdentity) {
            ex = ExposureLevel.PUBLISH;
        }else{
            ex = ExposureLevel.HIDE;
        }
        if (cryptoBrokerBitmap != null) {
            imgInBytes = ImagesUtils.toByteArray(cryptoBrokerBitmap);
        }else{
            imgInBytes = profileImage;
        }
        if(brokerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG).show();
        }else{
            if(imgInBytes == null){
                Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            }else{
                if(cryptoBrokerPublicKey != null) {
                    if(d.equalsIgnoreCase("onClick")){
                        changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY, appSession.getAppPublicKey());
                    }
                    Toast.makeText(getActivity(), "Crypto Broker Identity Updated.", Toast.LENGTH_LONG).show();
                    CryptoBrokerIdentityInformation identity = new CryptoBrokerIdentityInformationImpl(brokerNameText, cryptoBrokerPublicKey, imgInBytes, ex);
                    EditIdentityWorker EditIdentityWorker = new EditIdentityWorker(getActivity(), appSession, identity, this);
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
            switch (resultCode) {
                case SUCCESS:
                    //Toast.makeText(getActivity(), "Crypto Broker Identity Updated.", Toast.LENGTH_LONG).show();
                break;
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
        errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
    }
}