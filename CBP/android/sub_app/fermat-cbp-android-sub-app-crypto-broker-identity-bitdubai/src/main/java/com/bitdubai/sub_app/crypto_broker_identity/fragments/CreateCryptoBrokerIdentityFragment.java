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
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cht_api.all_definition.enums.Frecuency;
import com.bitdubai.sub_app.crypto_broker_identity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoBrokerIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager>, ResourceProviderManager> {
    public static final int SUCCESS = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int IMAGE_COMPRESSION_PERCENTAGE = 25;

    private Bitmap cryptoBrokerBitmap;
    private byte[] cryptoBrokerImageByteArray;

    private EditText mBrokerName;
    private ImageView mBrokerImage;
    private View progressBar;


    public static CreateCryptoBrokerIdentityFragment newInstance() {
        return new CreateCryptoBrokerIdentityFragment();
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

        progressBar = layout.findViewById(R.id.cbi_progress_bar);
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        final Button botonG = (Button) layout.findViewById(R.id.create_crypto_broker_button);
        final ImageView camara = (ImageView) layout.findViewById(R.id.camara);
        final ImageView galeria = (ImageView) layout.findViewById(R.id.galeria);

        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        botonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewIdentityInBackDevice();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();
            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
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

                    //Crop image
                    //cryptoBrokerBitmap = Bitmap.createScaledBitmap(cryptoBrokerBitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);

                    //Compress image
                    cryptoBrokerImageByteArray = ImagesUtils.toCompressedByteArray(cryptoBrokerBitmap, IMAGE_COMPRESSION_PERCENTAGE);

                    if (mBrokerImage != null && cryptoBrokerBitmap != null)
                        mBrokerImage.setImageDrawable(new BitmapDrawable(getResources(), cryptoBrokerBitmap));

                break;

                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            cryptoBrokerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);

                            //Crop image
                            //cryptoBrokerBitmap = Bitmap.createScaledBitmap(cryptoBrokerBitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);

                            //Compress image
                            cryptoBrokerImageByteArray = ImagesUtils.toCompressedByteArray(cryptoBrokerBitmap, IMAGE_COMPRESSION_PERCENTAGE);

                            if (mBrokerImage != null && cryptoBrokerBitmap != null)
                                mBrokerImage.setImageDrawable(new BitmapDrawable(getResources(), cryptoBrokerBitmap));

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
                        appSession.getModuleManager().createCryptoBrokerIdentity(brokerNameText, cryptoBrokerImageByteArray, 0, com.bitdubai.fermat_cbp_api.all_definition.enums.Frecuency.NONE);

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
