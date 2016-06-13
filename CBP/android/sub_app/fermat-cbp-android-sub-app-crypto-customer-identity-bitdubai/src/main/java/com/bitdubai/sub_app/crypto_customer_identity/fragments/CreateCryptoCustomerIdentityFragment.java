package com.bitdubai.sub_app.crypto_customer_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.util.CreateCustomerIdentityExecutor;
import com.squareup.picasso.Picasso;

import static com.bitdubai.sub_app.crypto_customer_identity.util.CreateCustomerIdentityExecutor.SUCCESS;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoCustomerIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager>, ResourceProviderManager> {
    private static final String TAG = "CreateCustomerIdentity";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private Bitmap cryptoCustomerBitmap;

    private EditText mCustomerName;
    private ImageView mCustomerImage;


    public static CreateCryptoCustomerIdentityFragment newInstance() {
        return new CreateCryptoCustomerIdentityFragment();
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

        mCustomerImage = (ImageView) layout.findViewById(R.id.crypto_customer_image);

        mCustomerName = (EditText) layout.findViewById(R.id.crypto_customer_name);
        mCustomerName.requestFocus();
        mCustomerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        ImageView camara = (ImageView) layout.findViewById(R.id.camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        ImageView galeria = (ImageView) layout.findViewById(R.id.galeria);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();
            }
        });

        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);

        configureToolbar();
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cci_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cci_action_bar_gradient_colors));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.crypto_customer_identity_new_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create) {
            createNewIdentityInBackDevice("OnClick");
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    cryptoCustomerBitmap = (Bitmap) extras.get("data");

                    if (mCustomerImage != null && cryptoCustomerBitmap != null) {
                        mCustomerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), cryptoCustomerBitmap));

                        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), cryptoCustomerBitmap);

                        bitmapDrawable.setCornerRadius(360);
                        bitmapDrawable.setAntiAlias(true);

                        mCustomerImage.setImageDrawable(bitmapDrawable);

                    }

                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            cryptoCustomerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                            cryptoCustomerBitmap = Bitmap.createScaledBitmap(cryptoCustomerBitmap, mCustomerImage.getWidth(), mCustomerImage.getHeight(), true);

                            Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(mCustomerImage);
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
    private void createNewIdentityInBackDevice(String donde) {
        String customerNameText = mCustomerName.getText().toString();
        if (customerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "The alias must not be empty", Toast.LENGTH_LONG).show();
        } else {
            if (cryptoCustomerBitmap == null) {
                Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            } else {
                byte[] imgInBytes = ImagesUtils.toByteArray(cryptoCustomerBitmap);
                final CreateCustomerIdentityExecutor executor = new CreateCustomerIdentityExecutor(appSession, customerNameText, imgInBytes);
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("OnClick")) {
                            Toast.makeText(getActivity(), "Crypto Customer Identity Created.", Toast.LENGTH_LONG).show();
                            changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY, appSession.getAppPublicKey());
                        }
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
