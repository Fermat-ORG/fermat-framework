package com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_broker_identity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoBrokerIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager>, ResourceProviderManager> {
    public static final int SUCCESS = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final String BROKER_NAME = "Broker_name";

    private Bitmap cryptoBrokerBitmap = null;
    private byte[] cryptoBrokerImageByteArray = null;
    private String cryptoBrokerName = null;

    private EditText mBrokerName;
    private ImageView mBrokerImage;
    private View progressBar;


    public static CreateCryptoBrokerIdentityFragment newInstance() {
        return new CreateCryptoBrokerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //If we landed here from CryptoBrokerImageCropperFragment, save the cropped Image.
        if(appSession.getData(CryptoBrokerImageCropperFragment.CROPPED_IMAGE) != null)
        {
            cryptoBrokerImageByteArray = (byte[]) appSession.getData(CryptoBrokerImageCropperFragment.CROPPED_IMAGE);
            cryptoBrokerBitmap = BitmapFactory.decodeByteArray(cryptoBrokerImageByteArray, 0, cryptoBrokerImageByteArray.length);
            appSession.removeData(CryptoBrokerImageCropperFragment.CROPPED_IMAGE);

            cryptoBrokerName = (String) appSession.getData(BROKER_NAME);
            appSession.removeData(BROKER_NAME);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_create_crypto_broker_identity, container, false);
        initViews(rootLayout);
        return rootLayout;
    }


    private void initViews(View layout) {
        progressBar = layout.findViewById(R.id.cbi_progress_bar);
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        Button createButton = (Button) layout.findViewById(R.id.create_crypto_broker_button);
        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);

        if(cryptoBrokerBitmap != null)
            mBrokerImage.setImageBitmap(cryptoBrokerBitmap);

        if(cryptoBrokerName != null)
            mBrokerName.setText(cryptoBrokerName);


        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewIdentityInBackDevice();
            }
        });

        final ImageView cameraImageView = (ImageView) layout.findViewById(R.id.camara);
        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        final ImageView galleryImageView = (ImageView) layout.findViewById(R.id.galeria);
        galleryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();
            }
        });

        mBrokerName.requestFocus();
        mBrokerName.performClick();

        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "There has been an error loading the image", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            //Go to CryptoBrokerImageCropper so the user can crop (square) his picture
            appSession.setData(CryptoBrokerImageCropperFragment.BACK_ACTIVITY, Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY);
            appSession.setData(CryptoBrokerImageCropperFragment.ORIGINAL_IMAGE, cryptoBrokerBitmap);
            appSession.setData(BROKER_NAME, mBrokerName.getText().toString());
            changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER, appSession.getAppPublicKey());
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createNewIdentityInBackDevice() {
        final String brokerNameText = mBrokerName.getText().toString();

        if (brokerNameText.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG).show();
        } else {

            if (cryptoBrokerBitmap == null) {
                Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            } else {

                FermatWorker fermatWorker = new FermatWorker(getActivity()) {
                    @Override
                    protected Object doInBackground() throws Exception {
                        appSession.getModuleManager().createCryptoBrokerIdentity(brokerNameText, cryptoBrokerImageByteArray);

                        return SUCCESS;
                    }
                };

                fermatWorker.setCallBack(new FermatWorkerCallBack() {
                    @Override
                    public void onPostExecute(Object... result) {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Crypto Broker Identity Created.", Toast.LENGTH_LONG).show();

                        changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY, appSession.getAppPublicKey());
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "An error occurred trying to create a Crypto Broker Identity", Toast.LENGTH_SHORT).show();

                        appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                    }
                });

                progressBar.setVisibility(View.VISIBLE);
                fermatWorker.execute();
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
