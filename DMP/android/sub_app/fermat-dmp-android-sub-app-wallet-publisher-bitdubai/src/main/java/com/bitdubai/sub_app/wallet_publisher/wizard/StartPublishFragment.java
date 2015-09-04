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
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.adapters.ScreenShootAdapter;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;
import com.bitdubai.sub_app.wallet_publisher.util.CommonLogger;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Publish Started
 */
public class StartPublishFragment extends FermatWizardPageFragment implements ScreenShootAdapter.OnScreenShootItemClickListener {

    private static ScreenShootEnumType screenShootEnumType;

    /**
     * Constants
     */
    private final String TAG = "StartPublishFragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private static final int CONTEXT_MENU_CAMERA = 1;
    private static final int CONTEXT_MENU_GALLERY = 2;
    /**
     * MODULE
     */
    private WalletPublisherModuleManager manager;

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
    public static StartPublishFragment newInstance(Object[] args) {
        if (args == null || args.length == 0)
            throw new NullPointerException("arguments cannot be null or empty");
        StartPublishFragment f = new StartPublishFragment();
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
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallet_publisher_wizard_step_1, container, false);

        walletName = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        walletName.setText(project.getName());

        walletDescription = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        walletDescription.setText(project.getDescription());

        walletPlatform = (FermatTextView) rootView.findViewById(R.id.wallet_platform);
        walletPlatform.setText(project.getWalletType().name());

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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                screenShoots.set(position, byteArray);
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
        menu.setHeaderTitle("Select contact picture");
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

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public void savePage() {

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
}
