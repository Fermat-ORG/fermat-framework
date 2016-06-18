package com.bitdubai.sub_app.wallet_publisher.wizard;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWizardPageFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.adapters.ScreenShootAdapter;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;

import static com.bitdubai.sub_app.wallet_publisher.util.CommonLogger.exception;
import static com.bitdubai.sub_app.wallet_publisher.util.CommonLogger.info;

/**
 * Publishing Factory Project Summary
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class PublishFactoryProjectSummary extends FermatWizardPageFragment {

    private final String TAG = "Summary";
    /**
     * MODULE
     */
    private WalletPublisherModuleManager manager;
    private Map<String, Object> data;

    /**
     * WFP
     */
    private WalletFactoryProject project;
    private byte[] mainScreenBytes;
    private byte[] iconScreenBytes;
    private ArrayList<byte[]> screenShootsBytes;
    private Version initialVersion;
    private Version finalVersion;
    private String videoUrlString;
    private PublisherIdentity identity;


    /**
     * UI
     */
    private View rootView;
    private ImageView mainScreen;
    private ImageView iconScreen;
    private FermatTextView name;
    private FermatTextView type;
    private FermatTextView initPlatform;
    private FermatTextView finalPlatform;
    private FermatTextView description;
    private FermatTextView video_url;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ScreenShootAdapter adapter;

    ProgressDialog dialog;

    /**
     * Get new instance of this fragment
     *
     * @param args Object[] passing session[0], settings[1], resourceManager[2], wfp[3]
     * @return fragment object
     */
    public static PublishFactoryProjectSummary newInstance(Object[] args) {
        if (args == null || args.length == 0)
            throw new NullPointerException("arguments cannot be null or empty");
        PublishFactoryProjectSummary f = new PublishFactoryProjectSummary();
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
            data = mParent.getData();
        } catch (Exception ex) {
            exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wizard_step_summary, container, false);

        mainScreen = (ImageView) rootView.findViewById(R.id.main_screenshot);
        iconScreen = (ImageView) rootView.findViewById(R.id.wallet_icon);
        name = (FermatTextView) rootView.findViewById(R.id.wallet_name);
        type = (FermatTextView) rootView.findViewById(R.id.wallet_type);
        initPlatform = (FermatTextView) rootView.findViewById(R.id.wallet_init_platform);
        finalPlatform = (FermatTextView) rootView.findViewById(R.id.wallet_final_platform);
        description = (FermatTextView) rootView.findViewById(R.id.wallet_short_description);
        video_url = (FermatTextView) rootView.findViewById(R.id.video_url);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.screenShootsRecycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ScreenShootAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (data == null || project == null)
            return;

        /* Projects Fields */
        name.setText(project.getName());
        description.setText(project.getDescription());
        type.setText(project.getWalletType().name());

        /* temp data fields */
        mainScreenBytes = (byte[]) data.get(PublishFactoryProjectStep1.MAIN_SCREEN_KEY);
        iconScreenBytes = (byte[]) data.get(PublishFactoryProjectStep1.WALLET_ICON_KEY);
        screenShootsBytes = (ArrayList) data.get(PublishFactoryProjectStep1.SCREEN_SHOOTS_KEY);
        identity = (PublisherIdentity) data.get(PublishFactoryProjectStep2.PUBLISHER_IDENTITY_KEY);
        videoUrlString = data.get(PublishFactoryProjectStep2.VIDEO_URL_KEY) != null ? data.get(PublishFactoryProjectStep2.VIDEO_URL_KEY).toString() : "";
        initialVersion = (Version) data.get(PublishFactoryProjectStep2.INIT_PLATFORM_KEY);
        finalVersion = (Version) data.get(PublishFactoryProjectStep2.FINAL_PLATFORM_KEY);

        if (mainScreenBytes != null)
            mainScreen.setImageDrawable(new BitmapDrawable(getResources(),
                    BitmapFactory.decodeByteArray(mainScreenBytes, 0, mainScreenBytes.length)));
        if (iconScreenBytes != null)
            iconScreen.setImageDrawable(new BitmapDrawable(getResources(),
                    BitmapFactory.decodeByteArray(iconScreenBytes, 0, iconScreenBytes.length)));
        if (screenShootsBytes != null && screenShootsBytes.size() > 0)
            adapter.changeDataSet(screenShootsBytes);

        if (videoUrlString != null)
            video_url.setText(videoUrlString);

        if (initialVersion != null) {
            initPlatform.setText(String.format("Initial Platform v%s", initialVersion.toString()));
        }
        if (finalVersion != null) {
            finalPlatform.setText(String.format("Final Platform v%s", finalVersion.toString()));
        }
    }

    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void savePage() {
        // do nothing...
    }

    @Override
    public void onWizardFinish(Map<String, Object> data) {
        getActivity()
                .runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
                        dialog = null;
                        dialog = new ProgressDialog(getActivity());
                        dialog.setTitle("Publishing...");
                        dialog.setMessage("Please wait...");
                        dialog.setCancelable(false);
                        dialog.show();
                        Executors.newSingleThreadExecutor().execute(worker);
                    }
                });
    }

    @Override
    public void onActivated(Map<String, Object> data) {
        this.data = data;
        onViewCreated(rootView, null);
    }

    @Override
    public CharSequence getTitle() {
        return "Publishing WFP - Summary";
    }

    public void setProject(WalletFactoryProject project) {
        this.project = project;
    }

    FermatWorkerCallBack callBack = new FermatWorkerCallBack() {
        @Override
        public void onPostExecute(Object... result) {
            if (isAttached) {
                dialog.dismiss();
                getActivity().finish();
            }
        }

        @Override
        public void onErrorOccurred(Exception ex) {
            if (isAttached) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Error Trying to publish this project...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    FermatWorker worker = new FermatWorker() {

        @Override
        protected Object doInBackground() throws Exception {
            if (getActivity() != null)
                setContext(getActivity());
            if (callBack != null)
                setCallBack(callBack);
            info(TAG, "Publishing Wallet...");
            videoUrlString = "https://www.youtube.com/watch?v=GS2JXAZhrYY";//// TODO: 08/09/15 remove this line
            URL url = new URL(videoUrlString);
            manager.publishWallet(project, iconScreenBytes, mainScreenBytes, screenShootsBytes, url,
                    "Hello Index Observation", initialVersion, finalVersion, identity);
            return true;
        }
    };
}
