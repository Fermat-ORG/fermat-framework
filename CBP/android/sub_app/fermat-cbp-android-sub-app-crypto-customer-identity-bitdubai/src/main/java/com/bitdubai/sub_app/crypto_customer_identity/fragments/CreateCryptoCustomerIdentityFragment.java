package com.bitdubai.sub_app.crypto_customer_identity.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.util.FragmentsCommons;

import static com.bitdubai.sub_app.crypto_customer_identity.util.CreateCustomerIdentityExecutor.SUCCESS;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateCryptoCustomerIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager>, ResourceProviderManager> {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private static final String CUSTOMER_NAME = "Customer_name";

    private Bitmap cryptoCustomerBitmap = null;
    private byte[] cryptoCustomerImageByteArray = null;
    private String cryptoCustomerName = null;

    private EditText mCustomerName;
    private ImageView mCustomerImage;
    private View progressBar;


    public static CreateCryptoCustomerIdentityFragment newInstance() {
        return new CreateCryptoCustomerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //If we landed here from CryptoCustomerImageCropperFragment, save the cropped Image.
        if (appSession.getData(CryptoCustomerImageCropperFragment.CROPPED_IMAGE) != null) {
            cryptoCustomerImageByteArray = (byte[]) appSession.getData(CryptoCustomerImageCropperFragment.CROPPED_IMAGE);
            cryptoCustomerBitmap = BitmapFactory.decodeByteArray(cryptoCustomerImageByteArray, 0, cryptoCustomerImageByteArray.length);
            appSession.removeData(CryptoCustomerImageCropperFragment.CROPPED_IMAGE);

            cryptoCustomerName = (String) appSession.getData(CUSTOMER_NAME);
            appSession.removeData(CUSTOMER_NAME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_create_crypto_customer_identity, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    private void initViews(View layout) {

        progressBar = layout.findViewById(R.id.cci_progress_bar);
        mCustomerImage = (ImageView) layout.findViewById(R.id.crypto_customer_image);
        mCustomerName = (EditText) layout.findViewById(R.id.crypto_customer_name);


        if (cryptoCustomerBitmap != null) {
            RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), cryptoCustomerBitmap);
            bitmapDrawable.setCornerRadius(360);
            bitmapDrawable.setAntiAlias(true);
            mCustomerImage.setImageDrawable(bitmapDrawable);
        }

        if (cryptoCustomerName != null)
            mCustomerName.setText(cryptoCustomerName);

        mCustomerName.requestFocus();
        mCustomerName.performClick();


        mCustomerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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

        mCustomerName.requestFocus();

        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == FragmentsCommons.CREATE_IDENTITY_MENU_ID) {
            createNewIdentityInBackDevice();
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
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            ContentResolver contentResolver = getActivity().getContentResolver();
                            cryptoCustomerBitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            //Go to CryptoCustomerImageCropperFragment so the user can crop (square) his picture
            appSession.setData(CryptoCustomerImageCropperFragment.BACK_ACTIVITY, Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY);
            appSession.setData(CryptoCustomerImageCropperFragment.ORIGINAL_IMAGE, cryptoCustomerBitmap);
            appSession.setData(CUSTOMER_NAME, mCustomerName.getText().toString());
            changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER, appSession.getAppPublicKey());
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
    private void createNewIdentityInBackDevice() {
        final String customerNameText = mCustomerName.getText().toString();
        if (customerNameText.trim().equals("")) {
            Toast.makeText(getActivity(), "The alias must not be empty", Toast.LENGTH_LONG).show();
        } else {
            if (cryptoCustomerBitmap == null) {
                Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();
            } else {

                FermatWorker fermatWorker = new FermatWorker(getActivity()) {
                    @Override
                    protected Object doInBackground() throws Exception {
                        CryptoCustomerIdentityInformation identity;
                        identity = appSession.getModuleManager().createCryptoCustomerIdentity(customerNameText, cryptoCustomerImageByteArray);
                        appSession.getModuleManager().publishCryptoCustomerIdentity(identity.getPublicKey());

                        return SUCCESS;
                    }
                };

                fermatWorker.setCallBack(new FermatWorkerCallBack() {
                    @Override
                    public void onPostExecute(Object... result) {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Crypto Customer Identity Created.", Toast.LENGTH_LONG).show();
                        changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY, appSession.getAppPublicKey());
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "An error occurred trying to create a Crypto Customer Identity", Toast.LENGTH_SHORT).show();

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
        Intent loadImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(loadImageIntent, REQUEST_LOAD_IMAGE);
    }
}
