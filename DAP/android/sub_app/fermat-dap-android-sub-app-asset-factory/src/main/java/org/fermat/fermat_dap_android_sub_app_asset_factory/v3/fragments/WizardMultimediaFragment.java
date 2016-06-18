package org.fermat.fermat_dap_android_sub_app_asset_factory.v3.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.SessionConstantsAssetFactory;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.CommonLogger;
import org.fermat.fermat_dap_android_sub_app_asset_factory.util.Utils;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by frank on 12/15/15.
 */
public class WizardMultimediaFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetFactoryModuleManager>, ResourceProviderManager> {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    private static final String TAG = "Asset Factory";

    private Activity activity;
    private AssetFactoryModuleManager moduleManager;
    private ErrorManager errorManager;

    //UI
    private View rootView;
    private Toolbar toolbar;
    private Resources res;
    private FermatButton wizardMultimediaPhotoButton;
    private FermatButton wizardMultimediaCameraButton;
    private ImageView wizardMultimediaAssetImage;
    private FermatButton wizardMultimediaNextButton;
    private FermatButton wizardMultimediaSaveButton;
    private ImageButton wizardMultimediaStep2Image;
    private ImageButton wizardMultimediaStep3Image;
    private ImageButton wizardMultimediaStep4Image;
    Bitmap imageBitmap = null;
    private boolean hasResource = false;
    private boolean isEdit = false;
    private AssetFactory asset;

    public WizardMultimediaFragment() {

    }

    public static WizardMultimediaFragment newInstance() {
        return new WizardMultimediaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        activity = getActivity();

        configureToolbar();
    }

    private void loadMultimedia() {
        if (asset.getResources() != null && !asset.getResources().isEmpty()) {
            if (asset.getResources().get(0) != null && asset.getResources().get(0).getResourceBinayData() != null) {
                hasResource = true;
                byte[] bitmapBytes = asset.getResources().get(0).getResourceBinayData();
                BitmapDrawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length));
                if (wizardMultimediaAssetImage != null)
                    wizardMultimediaAssetImage.setImageDrawable(drawable);
            }
        }
    }

    private void createNewAssetFactory() {
        try {
            if (!isEdit) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setTitle("Draft Asset");
                dialog.setMessage("Creating new empty asset project, please wait...");
                dialog.setCancelable(false);
                dialog.show();
                FermatWorker worker = new FermatWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        asset = moduleManager.newAssetFactoryEmpty();
                        List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();
                        if (installedWallets != null && installedWallets.size() > 0) {
                            asset.setWalletPublicKey(Utils.getBitcoinWalletPublicKey(moduleManager));
                        }
                        return true;
                    }
                };
                worker.setContext(getActivity());
                worker.setCallBack(new FermatWorkerCallBack() {
                    @Override
                    public void onPostExecute(Object... result) {
                        dialog.dismiss();
                        // do nothing... continue with the form data
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Some error occurred while creating a new asset empty project", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                });
                worker.execute();
            }
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dap_v3_factory_wizard_multimedia, container, false);
        res = rootView.getResources();

        setupUI();
        setupUIData();

        return rootView;
    }

    private void setUpHelpAssetStatistics(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_factory)
                    .setIconRes(R.drawable.asset_factory)
                    .setImageLeft(R.drawable.profile_actor)
                    .setVIewColor(R.color.dap_asset_factory_view_color)
                    .setTitleTextColor(R.color.dap_asset_factory_view_color)
                    .setTextNameLeft(R.string.dap_asset_factory_welcome_name_left)
                    .setSubTitle(R.string.dap_asset_factory_welcome_subTitle)
                    .setBody(R.string.dap_asset_factory_welcome_body)
                    .setTextFooter(R.string.dap_asset_factory_welcome_Footer)
                    .setTemplateType((moduleManager.getLoggedIdentityAssetIssuer() == null) ? PresentationDialog.TemplateType.TYPE_PRESENTATION_WITH_ONE_IDENTITY : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        menu.add(0, SessionConstantsAssetFactory.IC_ACTION_HELP_FACTORY, 0, "Help")
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                //case IC_ACTION_HELP_FACTORY:
                case 2:
                setUpHelpAssetStatistics(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
//                case 1:
//                    changeActivity(Activities.CHT_CHAT_GEOLOCATION_IDENTITY, appSession.getAppPublicKey());
//                    break;
            }
//            if (id == SessionConstantsAssetFactory.IC_ACTION_HELP_FACTORY) {
//                setUpHelpAssetStatistics(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_asset_factory_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        wizardMultimediaPhotoButton = (FermatButton) rootView.findViewById(R.id.wizardMultimediaPhotoButton);
        wizardMultimediaCameraButton = (FermatButton) rootView.findViewById(R.id.wizardMultimediaCameraButton);
        wizardMultimediaAssetImage = (ImageView) rootView.findViewById(R.id.wizardMultimediaAssetImage);
        wizardMultimediaNextButton = (FermatButton) rootView.findViewById(R.id.wizardMultimediaNextButton);
        wizardMultimediaSaveButton = (FermatButton) rootView.findViewById(R.id.wizardMultimediaSaveButton);
        wizardMultimediaStep2Image = (ImageButton) rootView.findViewById(R.id.wizardMultimediaStep2Image);
        wizardMultimediaStep3Image = (ImageButton) rootView.findViewById(R.id.wizardMultimediaStep3Image);
        wizardMultimediaStep4Image = (ImageButton) rootView.findViewById(R.id.wizardMultimediaStep4Image);
        wizardMultimediaPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        wizardMultimediaCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery();
            }
        });
        wizardMultimediaNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getCode());
            }
        });
        wizardMultimediaSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(asset)) {
                    saveMultimedia();
                    doFinish();
                    Toast.makeText(getActivity(), String.format("Asset %s has been edited", asset.getName()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        wizardMultimediaStep2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES.getCode());
            }
        });
        wizardMultimediaStep3Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO.getCode());
            }
        });
        wizardMultimediaStep4Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(Activities.DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY.getCode());
            }
        });
    }

    private void doFinish() {
        if (asset != null) {
            if (asset.getFactoryId() == null) {
                asset.setFactoryId(UUID.randomUUID().toString());
            }
            asset.setTotalQuantity(asset.getQuantity());
            asset.setIsRedeemable(asset.getIsRedeemable());
            asset.setIsTransferable(asset.getIsTransferable());
            asset.setIsExchangeable(asset.getIsExchangeable());
            asset.setState(State.DRAFT);
            asset.setAssetBehavior(AssetBehavior.REGENERATION_ASSET);
            asset.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            saveAssetFactoryFinish();
        }
    }

    private void saveAssetFactoryFinish() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Saving asset");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.saveAssetFactory(asset);
                return true;
            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (getActivity() != null) {
//                    Toast.makeText(getActivity(), String.format("Asset %s has been saved", asset.getName()), Toast.LENGTH_SHORT).show();
                    appSession.setData("asset_factory", null);
                    changeActivity(Activities.DAP_MAIN.getCode(), appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (getActivity() != null) {
                    CommonLogger.exception(TAG, ex.getMessage(), ex);
                    Toast.makeText(getActivity(), "There was an error creating this asset", Toast.LENGTH_SHORT).show();
                }
            }
        });
        worker.execute();
    }

    private void go(String code) {
//        if (validate()) {
        saveMultimedia();
        appSession.setData("asset_factory", asset);
        changeActivity(code, appSession.getAppPublicKey());
//        }
    }

    private boolean isValid(AssetFactory asset) {
        boolean isValidDate = asset.getExpirationDate() == null ? true : asset.getExpirationDate().after(new Date());
        long amountSatoshi = asset.getAmount();


        if (asset.getName() != null && asset.getName().trim().length() > 0 &&
                asset.getDescription() != null && asset.getDescription().trim().length() > 0
                && asset.getQuantity() > 0 && asset.getAmount() >= BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND
                && isValidDate) {

            return true;

        } else if (asset.getName() == null || asset.getName().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getDescription() == null || asset.getDescription().trim().length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_description), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getQuantity() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_asset_factory_invalid_quantity), Toast.LENGTH_SHORT).show();
            return false;
        } else if (asset.getExpirationDate() != null && asset.getExpirationDate().before(new Date())) {
            Toast.makeText(getActivity(), "Expiration date can't be in the past. Please modify the expiration date.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(getActivity(), "The minimum monetary amount for any Asset is " + BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND + " satoshis.\n" +
                    " \n This is needed to pay the fee of bitcoin transactions during delivery of the assets.\n " + "\n You selected " + amountSatoshi + " satoshis.\n", Toast.LENGTH_LONG).show();
            return false;
        }
    }

//    private boolean validate() {
//        return true;
//    }

    private void saveMultimedia() {
        if (hasResource) {
            List<Resource> resources = new ArrayList<>();
            Resource resource = new Resource();
            if (asset.getResources() != null && asset.getResources().size() > 0) {
                resource.setId(asset.getResources().get(0).getId());
            } else {
                resource.setId(UUID.randomUUID());
            }
            resource.setResourceType(ResourceType.IMAGE);
            resource.setResourceDensity(ResourceDensity.HDPI);
            resource.setResourceBinayData(ImagesUtils.toByteArray(ConvertToBitMap(wizardMultimediaAssetImage)));
            resources.add(resource);
            asset.setResources(resources);
        } else
            asset.setResources(null);
    }

    private Bitmap ConvertToBitMap(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        return imageView.getDrawingCache();
    }

    private void setupUIData() {
        if (appSession.getData("asset_factory") != null) {
            asset = (AssetFactory) appSession.getData("asset_factory");
            loadMultimedia();
        } else {
            createNewAssetFactory();
        }

        if (asset != null) {
            wizardMultimediaSaveButton.setVisibility((asset.getFactoryId() != null) ? View.VISIBLE : View.INVISIBLE);
            wizardMultimediaNextButton.setVisibility((asset.getFactoryId() != null) ? View.INVISIBLE : View.VISIBLE);
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.redeem_home_bar_color));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.redeem_home_bar_color));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        if (isAttached) {
                            imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, wizardMultimediaAssetImage.getWidth(), wizardMultimediaAssetImage.getHeight(), true);
                            if (imageBitmap != null) {
                                hasResource = true;
//                                Picasso.with(getActivity()).load(selectedImage).transform(new CircleTransform()).into(takePicture);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error Loading Image", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            //pictureView.setBackground(new RoundedDrawable(imageBitmap, takePictureButton));
            if (imageBitmap != null) {
                hasResource = true;
                wizardMultimediaAssetImage.setImageDrawable(new BitmapDrawable(getResources(), imageBitmap));
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void loadImageFromGallery() {
        Log.i("Multimedia", "Loading Image from Gallery...");
        Intent intentLoad = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appSession.setData("asset_factory", null);
    }
}
