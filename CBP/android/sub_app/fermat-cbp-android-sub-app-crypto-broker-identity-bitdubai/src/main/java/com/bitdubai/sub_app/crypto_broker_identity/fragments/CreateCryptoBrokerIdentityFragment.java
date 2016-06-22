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
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor;
import com.squareup.picasso.Picasso;

import static com.bitdubai.sub_app.crypto_broker_identity.util.CreateBrokerIdentityExecutor.SUCCESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoBrokerIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession,ResourceProviderManager> {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private Bitmap cryptoBrokerBitmap;


    private EditText mBrokerName;
    private ImageView mBrokerImage;

    private boolean actualizable;

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
        actualizable = true;
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        Button botonG = (Button) layout.findViewById(R.id.create_crypto_broker_button);

        botonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizable = false;
                createNewIdentityInBackDevice("onClick");
            }
        });

        mBrokerName.requestFocus();
        mBrokerName.performClick();
        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                if (actualizable) {
                    /*
                    if(!mBrokerName.getText().toString().trim().equals("")) {
                        if (cryptoBrokerBitmap != null) {
                            new AlertDialog.Builder(v.getContext())
                                    .setTitle("Create Identity?")
                                    .setMessage("You want to create identity?")
                                    .setNegativeButton(android.R.string.no, null)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            createNewIdentityInBackDevice("onFocus");
                                        }
                                    }).create().show();
                        }
                    }
                    */
                }
            }
        });
        final ImageView camara = (ImageView) layout.findViewById(R.id.camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        final ImageView galeria = (ImageView) layout.findViewById(R.id.galeria);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();
            }
        });
        mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    cryptoBrokerBitmap = (Bitmap) extras.get("data");

                    if (mBrokerImage != null && cryptoBrokerBitmap != null) {
                        mBrokerImage.setImageDrawable(new BitmapDrawable(getResources(), cryptoBrokerBitmap));
                    }

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

    private void createNewIdentityInBackDevice(String donde){
        String brokerNameText = mBrokerName.getText().toString();
        if(brokerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG).show();
        }else{
            if (cryptoBrokerBitmap == null) {
                Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            }else{
                byte[] imgInBytes = ImagesUtils.toByteArray(cryptoBrokerBitmap);
                CreateBrokerIdentityExecutor executor = new CreateBrokerIdentityExecutor(appSession, brokerNameText, imgInBytes);
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if( donde.equalsIgnoreCase("onClick") ){
                            Toast.makeText(getActivity(), "Crypto Broker Identity Created.", Toast.LENGTH_LONG).show();
                            changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY, appSession.getAppPublicKey());
                        }
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
