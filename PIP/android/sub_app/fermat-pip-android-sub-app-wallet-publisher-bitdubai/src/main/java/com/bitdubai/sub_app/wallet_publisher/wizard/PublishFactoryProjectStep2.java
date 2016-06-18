package com.bitdubai.sub_app.wallet_publisher.wizard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWizardPageFragment;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions.CantSingMessageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;

import java.util.HashMap;
import java.util.Map;

import static com.bitdubai.sub_app.wallet_publisher.util.CommonLogger.exception;

/**
 * Publish Factory Project Wizard Step 2
 * <b>Fields:</b> Video Url, Init & Final Platforms Supported.<br/>
 * <b>Pending:</b> Publisher Identity
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class PublishFactoryProjectStep2 extends FermatWizardPageFragment {

    /**
     * CONSTANTS
     */
    private final String TAG = "PublishStep2";
    /**
     * KEYS
     */
    public static final String VIDEO_URL_KEY = "video_url";
    public static final String INIT_PLATFORM_KEY = "initial_platform";
    public static final String FINAL_PLATFORM_KEY = "final_platform";
    public static final String PUBLISHER_IDENTITY_KEY = "PublisherIdentity";
    /**
     * MODULE
     */
    private WalletPublisherModuleManager manager;
    private Map<String, Object> data;

    private Version currentPlatform;
    private Version initialPlatform;
    private Version finalPlatform;

    private PublisherIdentity identity = new PublisherIdentity() {
        @SuppressWarnings("SpellCheckingInspection")
        @Override
        public String getAlias() {
            return "fvasquezjatar";
        }

        @Override
        public String getPublicKey() {
            return "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
        }

        @Override
        public String getWebsiteurl() {
            return "https://github.com/fvasquezjatar";
        }

        @Override
        public String createMessageSignature(String mensage) throws CantSingMessageException {
            return "Hola Mundo";
        }
    };

    /**
     * WFP
     */
    private WalletFactoryProject project;

    /**
     * UI
     */
    private View rootView;
    private FermatEditText videoUrl;
    private FermatEditText initPlatformMajor;
    private FermatEditText initPlatformMinor;
    private FermatEditText initPlatformPatch;
    private FermatEditText finalPlatformMajor;
    private FermatEditText finalPlatformMinor;
    private FermatEditText finalPlatformPatch;

    //// TODO: 07/09/15 Publisher Identity Control

    /**
     * Get new instance of this fragment
     *
     * @param args Object[] passing session[0], settings[1], resourceManager[2], wfp[3]
     * @return fragment object
     */
    public static PublishFactoryProjectStep2 newInstance(Object[] args) {
        if (args == null || args.length == 0)
            throw new NullPointerException("arguments cannot be null or empty");
        PublishFactoryProjectStep2 f = new PublishFactoryProjectStep2();
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
            currentPlatform = manager.getPlatformVersions().get(0);
        } catch (Exception ex) {
            exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wizard_step_2, container, false);

        videoUrl = (FermatEditText) rootView.findViewById(R.id.video_url);
        initPlatformMajor = (FermatEditText) rootView.findViewById(R.id.init_major);
        initPlatformMinor = (FermatEditText) rootView.findViewById(R.id.init_minor);
        initPlatformPatch = (FermatEditText) rootView.findViewById(R.id.init_patch);
        finalPlatformMajor = (FermatEditText) rootView.findViewById(R.id.final_major);
        finalPlatformMinor = (FermatEditText) rootView.findViewById(R.id.final_minor);
        finalPlatformPatch = (FermatEditText) rootView.findViewById(R.id.final_patch);

        if (currentPlatform != null) {
            initPlatformMajor.setText(String.valueOf(currentPlatform.getMajor()));
            initPlatformMinor.setText(String.valueOf(currentPlatform.getMinor()));
            initPlatformPatch.setText(String.valueOf(currentPlatform.getPatch()));

            finalPlatformMajor.setText(String.valueOf(currentPlatform.getMajor()));
            finalPlatformMinor.setText(String.valueOf(currentPlatform.getMinor()));
            finalPlatformPatch.setText(String.valueOf(currentPlatform.getPatch()));
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (data == null)
            return;
        if (data.containsKey(VIDEO_URL_KEY) && !data.get(VIDEO_URL_KEY).toString().isEmpty()) {
            videoUrl.setText(data.get(VIDEO_URL_KEY).toString().trim());
        }
        if (data.containsKey(INIT_PLATFORM_KEY)) {
            initialPlatform = (Version) data.get(INIT_PLATFORM_KEY);
            if (initialPlatform != null) {
                initPlatformMajor.setText(String.valueOf(initialPlatform.getMajor()));
                initPlatformMinor.setText(String.valueOf(initialPlatform.getMinor()));
                initPlatformPatch.setText(String.valueOf(initialPlatform.getPatch()));
            }
        }
        if (data.containsKey(FINAL_PLATFORM_KEY)) {
            finalPlatform = (Version) data.get(FINAL_PLATFORM_KEY);
            if (initialPlatform != null) {
                finalPlatformMajor.setText(String.valueOf(finalPlatform.getMajor()));
                finalPlatformMinor.setText(String.valueOf(finalPlatform.getMinor()));
                finalPlatformPatch.setText(String.valueOf(finalPlatform.getPatch()));
            }
        }
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void savePage() {
        if (data == null)
            data = new HashMap<>();

        int initMajor = Integer.parseInt(initPlatformMajor.getText().toString().trim());
        int initMinor = Integer.parseInt(initPlatformMinor.getText().toString().trim());
        int initPatch = Integer.parseInt(initPlatformPatch.getText().toString().trim());

        int finalMajor = Integer.parseInt(finalPlatformMajor.getText().toString().trim());
        int finalMinor = Integer.parseInt(finalPlatformMinor.getText().toString().trim());
        int finalPatch = Integer.parseInt(finalPlatformPatch.getText().toString().trim());

        initialPlatform = new Version(initMajor, initMinor, initPatch);
        finalPlatform = new Version(finalMajor, finalMinor, finalPatch);

        data.put(VIDEO_URL_KEY, videoUrl.getText().toString().trim());
        data.put(INIT_PLATFORM_KEY, initialPlatform);
        data.put(FINAL_PLATFORM_KEY, finalPlatform);
        data.put(PUBLISHER_IDENTITY_KEY, identity);
    }

    @Override
    public void onWizardFinish(Map<String, Object> data) {

    }

    @Override
    public void onActivated(Map<String, Object> data) {
        this.data = data;
        onViewCreated(rootView, null);
    }

    @Override
    public CharSequence getTitle() {
        return "Publishing WFP - Store Listening Step 2";
    }

    public void setProject(WalletFactoryProject project) {
        this.project = project;
    }
}
