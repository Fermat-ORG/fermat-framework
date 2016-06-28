package com.bitdubai.sub_app.crypto_broker_identity.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.utils.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.util.EditIdentityWorker;
import com.bitdubai.sub_app.crypto_broker_identity.util.FragmentsCommons;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutorService;


/**
 * This Fragment let you edit a Broker Identity
 * <p/>
 * Created by Nelson Ramirez (nelsonalfo@gmail.com)
 */
public class EditCryptoBrokerIdentityFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager>, ResourceProviderManager>
        implements FermatWorkerCallBack {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    // data
    private Bitmap cryptoBrokerBitmap = null;
    private byte[] identityImgByteArray = null;
    private String cryptoBrokerName = null;
    private boolean wantPublishIdentity;
    private String cryptoBrokerPublicKey;
    private boolean actualizable;

    // Managers

    private ImageView sw;
    private EditText mBrokerName;
    private View progressBar;
    private int maxLenghtTextCount = 30;
    FermatTextView textCount;

    private ExecutorService executor;
    private byte[] profileImage;

    private final TextWatcher textWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {textCount.setText(String.valueOf(maxLenghtTextCount - s.length()));}
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    };

    public static EditCryptoBrokerIdentityFragment newInstance() {
        return new EditCryptoBrokerIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If we landed here from CryptoBrokerImageCropperFragment, save the cropped Image.
        if (appSession.getData(FragmentsCommons.CROPPED_IMAGE) != null) {
            identityImgByteArray = (byte[]) appSession.getData(FragmentsCommons.CROPPED_IMAGE);
            cryptoBrokerBitmap = BitmapFactory.decodeByteArray(identityImgByteArray, 0, identityImgByteArray.length);
            appSession.removeData(FragmentsCommons.CROPPED_IMAGE);

        } else if (appSession.getData(FragmentsCommons.ORIGINAL_IMAGE) != null) {
            cryptoBrokerBitmap = (Bitmap) appSession.getData(FragmentsCommons.ORIGINAL_IMAGE);
            appSession.removeData(FragmentsCommons.ORIGINAL_IMAGE);
        }

        if (appSession.getData(FragmentsCommons.BROKER_NAME) != null) {
            cryptoBrokerName = (String) appSession.getData(FragmentsCommons.BROKER_NAME);
            appSession.removeData(FragmentsCommons.BROKER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_edit_crypto_broker_identity, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    private void initViews(View layout) {
        actualizable = true;

        progressBar = layout.findViewById(R.id.cbi_progress_bar);
        mBrokerName = (EditText) layout.findViewById(R.id.crypto_broker_name);
        textCount = (FermatTextView) layout.findViewById(R.id.crypto_broker_name_text_count);

        sw = (ImageView) layout.findViewById(R.id.sw);
        final ImageView mBrokerImage = (ImageView) layout.findViewById(R.id.crypto_broker_image);
        final Button botonU = (Button) layout.findViewById(R.id.update_crypto_broker_button);
        final ImageView camara = (ImageView) layout.findViewById(R.id.camara);
        final ImageView galeria = (ImageView) layout.findViewById(R.id.galeria);

        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (actualizable) {
                    actualizable = false;
                }
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

        botonU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizable = false;
                editIdentityInfoInDevice();
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

        final CryptoBrokerIdentityInformation identityInfo = (CryptoBrokerIdentityInformation) appSession.getData(FragmentsCommons.IDENTITY_INFO);

        //Coming from List activity
        if (identityInfo != null) {

            cryptoBrokerPublicKey = identityInfo.getPublicKey();
            mBrokerName.setText(identityInfo.getAlias());
            mBrokerName.selectAll();
            wantPublishIdentity = identityInfo.isPublished();

            profileImage = identityInfo.getProfileImage();

            if (profileImage.length == 0) {
                mBrokerImage.setImageResource(R.drawable.pic_space);
            } else {
                BitmapDrawable bmd = new BitmapDrawable(getResources(), new ByteArrayInputStream(profileImage));
                mBrokerImage.setImageBitmap(bmd.getBitmap());
            }
        }

        //Coming from cropper activity
        if (cryptoBrokerBitmap != null)
            mBrokerImage.setImageBitmap(cryptoBrokerBitmap);

        if (cryptoBrokerName != null)
            mBrokerName.setText(cryptoBrokerName);

        mBrokerName.requestFocus();
        mBrokerName.performClick();
        mBrokerName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenghtTextCount)});
        mBrokerName.addTextChangedListener(textWatcher);
        textCount.setText(String.valueOf(maxLenghtTextCount));

        mBrokerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (actualizable) {
                    actualizable = false;
                }
            }
        });

        if (wantPublishIdentity) {
            sw.setImageResource(R.drawable.switch_visible);
        } else {
            sw.setImageResource(R.drawable.switch_notvisible);
        }

        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == FragmentsCommons.GEOLOCATION_SETTINGS_OPTION_MENU_ID) {
            appSession.setData(FragmentsCommons.BROKER_NAME, mBrokerName.getText().toString());
            appSession.setData(FragmentsCommons.ORIGINAL_IMAGE, cryptoBrokerBitmap);

            changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_GEOLOCATION_EDIT_IDENTITY, appSession.getAppPublicKey());
        }

        return false;
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
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            //Go to CryptoBrokerImageCropper so the user can crop (square) his picture
            appSession.setData(FragmentsCommons.BACK_ACTIVITY, Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY);
            appSession.setData(FragmentsCommons.ORIGINAL_IMAGE, cryptoBrokerBitmap);
            appSession.setData(FragmentsCommons.BROKER_NAME, mBrokerName.getText().toString());
            changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_IMAGE_CROPPER, appSession.getAppPublicKey());

        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPostExecute(Object... result) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        progressBar.setVisibility(View.GONE);

        Toast.makeText(getActivity(), "Crypto Broker Identity Updated.", Toast.LENGTH_LONG).show();
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY, appSession.getAppPublicKey());
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        progressBar.setVisibility(View.GONE);

        Toast.makeText(getActivity().getApplicationContext(), "Error trying to edit the identity.", Toast.LENGTH_SHORT).show();
        appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
    }

    private void editIdentityInfoInDevice() {
        final String brokerNameText = mBrokerName.getText().toString();

        final ExposureLevel exposureLevel = wantPublishIdentity ? ExposureLevel.PUBLISH : ExposureLevel.HIDE;

        final byte[] imgInBytes = (cryptoBrokerBitmap != null) ? identityImgByteArray : profileImage;

        if (brokerNameText.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a name or alias", Toast.LENGTH_LONG).show();

        } else if (imgInBytes == null) {
            Toast.makeText(getActivity(), "You must enter an image", Toast.LENGTH_LONG).show();

        } else {
            final int accuracy = getAccuracyData();
            final Frequency frequency = getFrequencyData();

            CryptoBrokerIdentityInformation identity = new CryptoBrokerIdentityInformationImpl(brokerNameText, cryptoBrokerPublicKey,
                    imgInBytes, exposureLevel, accuracy, frequency);

            FermatWorker fermatWorker = new EditIdentityWorker(getActivity(), appSession, identity, this);

            progressBar.setVisibility(View.VISIBLE);
            executor = fermatWorker.execute();
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

    private int getAccuracyData() {
        return appSession.getData(FragmentsCommons.ACCURACY_DATA) == null ? 0 :
                (int) appSession.getData(FragmentsCommons.ACCURACY_DATA);
    }

    private Frequency getFrequencyData() {
        return appSession.getData(FragmentsCommons.FREQUENCY_DATA) == null ? Frequency.NONE :
                (Frequency) appSession.getData(FragmentsCommons.FREQUENCY_DATA);
    }
}