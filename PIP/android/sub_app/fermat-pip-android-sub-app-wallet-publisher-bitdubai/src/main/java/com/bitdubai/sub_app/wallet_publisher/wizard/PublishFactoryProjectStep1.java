package com.bitdubai.sub_app.wallet_publisher.wizard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWizardPageFragment;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.adapters.ScreenShootAdapter;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bitdubai.sub_app.wallet_publisher.util.CommonLogger.error;
import static com.bitdubai.sub_app.wallet_publisher.util.CommonLogger.exception;
import static com.bitdubai.sub_app.wallet_publisher.util.CommonLogger.info;

/**
 * Publish Started
 */
@SuppressWarnings("FieldCanBeLocal")
public class PublishFactoryProjectStep1 extends FermatWizardPageFragment implements ScreenShootAdapter.OnScreenShootItemClickListener {

    private static ScreenShootEnumType screenShootEnumType;

    /**
     * Constants
     */
    private final String TAG = "PublishStep1";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;

    public static final String SCREEN_SHOOTS_KEY = "ScreenShoots";
    public static final String MAIN_SCREEN_KEY = "MainScreenShoot";
    public static final String WALLET_ICON_KEY = "WalletIconKey";

    /**
     * MODULE
     */
    private WalletPublisherModuleManager manager;
    private Map<String, Object> data;

    /**
     * WFP
     */
    private WalletFactoryProject project;
    private ArrayList<byte[]> screenShoots = new ArrayList<>(5);

    /**
     * UI
     */
    private View rootView;

    private FermatTextView walletName;
    private FermatTextView walletDescription;
    private FermatTextView walletType;
    private FermatTextView walletPlatform;

    private ImageView walletMainScreen;
    private ImageView walletIcon;

    private RecyclerView screenShootsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ScreenShootAdapter adapter;

    /**
     * Get new instance of this fragment
     *
     * @param args Object[] passing session[0], settings[1], resourceManager[2], wfp[3]
     * @return fragment object
     */
    public static PublishFactoryProjectStep1 newInstance(Object[] args) {
        if (args == null || args.length == 0)
            throw new NullPointerException("arguments cannot be null or empty");
        PublishFactoryProjectStep1 f = new PublishFactoryProjectStep1();
        f.setSubAppsSession((SubAppsSession) args[0]);
        f.setSubAppSettings((SubAppSettings) args[1]);
        f.setSubAppResourcesProviderManager((SubAppResourcesProviderManager) args[2]);
        f.setProject((WalletFactoryProject) args[3]);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((WalletPublisherSubAppSession) subAppsSession).getWalletPublisherManager();
            mParent.setWizardActivity(getTitle());
        } catch (Exception ex) {
            exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public void onActivated(Map<String, Object> data) {
        if (data == null)
            return;
        // refreshing data
        this.data = data;
        onViewCreated(rootView, null);
    }

    @Override
    public CharSequence getTitle() {
        return "Publishing WPF - Store Listening Step 1";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (data != null) {
            if (data.containsKey(WALLET_ICON_KEY) && data.get(WALLET_ICON_KEY) != null) {
                byte[] bytes = (byte[]) data.get(WALLET_ICON_KEY);
                walletIcon.setImageDrawable(new BitmapDrawable(getResources(),
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
            }
            if (data.containsKey(MAIN_SCREEN_KEY) && data.get(MAIN_SCREEN_KEY) != null) {
                byte[] bytes = (byte[]) data.get(MAIN_SCREEN_KEY);
                walletMainScreen.setImageDrawable(new BitmapDrawable(getResources(),
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));
            }
            if (data.containsKey(SCREEN_SHOOTS_KEY) && data.get(SCREEN_SHOOTS_KEY) != null) {
                screenShoots = (ArrayList) data.get(SCREEN_SHOOTS_KEY);
                if (adapter != null)
                    adapter.changeDataSet(screenShoots);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wizard_step_1, container, false);

        walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        walletName.setText(project.getName());

        walletDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        walletDescription.setText(project.getDescription());

        walletType = (FermatTextView) rootView.findViewById(R.id.wallet_type);
        walletType.setText(project.getWalletType().name());

        walletPlatform = (FermatTextView) rootView.findViewById(R.id.wallet_platform);
        walletPlatform.setVisibility(View.GONE);
        try {
            List<Version> platforms = manager.getPlatformVersions();
            if (platforms != null && platforms.size() > 0) {
                Version version = platforms.get(0);
                walletPlatform.setText(
                        format(getString(R.string.wizard_step_1_current_platform), version.toString()));
                walletPlatform.setVisibility(View.VISIBLE);
                info(TAG, String.format("Current Platform Version: %s", version.toString()));
            } else
                error(TAG, "No version info available...");
        } catch (NullPointerException | CantLoadPlatformInformationException ex) {
            exception(TAG, ex.getMessage(), ex);
        }

        walletIcon = (ImageView) rootView.findViewById(R.id.wallet_icon);
        walletIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenShootEnumType = ScreenShootEnumType.WALLET_ICON;
                registerForContextMenu(walletIcon);
                getActivity().openContextMenu(walletIcon);
            }
        });

        walletMainScreen = (ImageView) rootView.findViewById(R.id.main_screenshot);
        walletMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenShootEnumType = ScreenShootEnumType.WALLET_MAIN_SCREEN;
                registerForContextMenu(walletMainScreen);
                getActivity().openContextMenu(walletMainScreen);
            }
        });
        /* init screenshoots */
        if (screenShoots == null || screenShoots.size() == 0 || screenShoots.size() < 5) {
            if (screenShoots == null)
                screenShoots = new ArrayList<>(5);
            screenShoots.add(null);
            screenShoots.add(null);
            screenShoots.add(null);
            screenShoots.add(null);
            screenShoots.add(null);
        }

        screenShootsRecyclerView = (RecyclerView) rootView.findViewById(R.id.screenShootsRecycler);
        screenShootsRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        screenShootsRecyclerView.setLayoutManager(layoutManager);

        adapter = new ScreenShootAdapter(getActivity(), screenShoots);
        adapter.setItemListener(this);
        screenShootsRecyclerView.setAdapter(adapter);

        return rootView;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap imageBitmap = null;
            ImageView pictureView = null;
            switch (screenShootEnumType) {
                case WALLET_ICON:
                    pictureView = walletIcon;
                    break;
                case WALLET_MAIN_SCREEN:
                    pictureView = walletMainScreen;
                    break;
                case SCREEN_SHOOT:
                    pictureView = screenShotItemView;
                    break;
            }
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
                            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, pictureView.getWidth(), pictureView.getHeight(), true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            if (imageBitmap != null && screenShootEnumType == ScreenShootEnumType.SCREEN_SHOOT) {
                screenShoots.set(position, toByteArray(imageBitmap));
                adapter.changeDataSet(screenShoots);
                adapter.notifyDataSetChanged();
            }
            //pictureView.setBackground(new RoundedDrawable(imageBitmap, takePictureButton));
            if (pictureView != null && imageBitmap != null)
                pictureView.setImageDrawable(new BitmapDrawable(getResources(), imageBitmap));
            //contactPicture = imageBitmap;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Choose mode");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(Menu.NONE, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(Menu.NONE, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
        /*
        if(contactPicture!=null)
            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete"); */
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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

    private void loadImageFromGallery() {
        Log.i(TAG, "Loading Image from Gallery...");
        Intent intentLoad = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
    }

    public void setProject(WalletFactoryProject project) {
        this.project = project;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean validate() {
        try {
            if (data.get(MAIN_SCREEN_KEY) == null)
                throw new NullPointerException("Main ScreenShoot is null");
            if (data.get(WALLET_ICON_KEY) == null)
                throw new NullPointerException("Wallet Icon is null");

            ArrayList<byte[]> screenShoots = (ArrayList) data.get(SCREEN_SHOOTS_KEY);
            //// TODO: 11/09/15 is not necessary for test purporse
            /*
            if (screenShoots == null || screenShoots.size() == 0)
                throw new NullPointerException("ScreenShoots are null");

            for (byte[] byteArray : screenShoots) {
                if (byteArray == null || byteArray.length == 0)
                    throw new NullPointerException("Some screen shoot is missing...");
            }
            */
            return true;
        } catch (Exception ex) {
            exception(TAG, ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public void savePage() {
        if (mParent == null)
            return;
        if (data == null)
            data = new HashMap<>();

        if (walletMainScreen.getDrawable() != null)
            data.put(MAIN_SCREEN_KEY, toByteArray(((BitmapDrawable) walletMainScreen.getDrawable()).getBitmap()));
        if (walletIcon.getDrawable() != null)
            data.put(WALLET_ICON_KEY, toByteArray(((BitmapDrawable) walletIcon.getDrawable()).getBitmap()));
        data.put(SCREEN_SHOOTS_KEY, screenShoots);

        mParent.putData(data);
    }

    @Override
    public void onWizardFinish(Map<String, Object> data) {

    }

    private static int position = -1;
    private ImageView screenShotItemView;

    @Override
    public void onScreenShootClickListener(View itemView, int _position) {
        screenShotItemView = (ImageView) itemView;
        screenShootEnumType = ScreenShootEnumType.SCREEN_SHOOT;
        position = _position;
        registerForContextMenu(itemView);
        getActivity().openContextMenu(itemView);
    }

    private enum ScreenShootEnumType {
        WALLET_ICON, WALLET_MAIN_SCREEN, SCREEN_SHOOT
    }

    /**
     * Bitmap to byte[]
     *
     * @param bitmap Bitmap
     * @return byte array
     */
    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
