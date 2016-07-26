package com.bitdubai.sub_app.crypto_broker_identity.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatBroadcastReceiver;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.FermatIntentFilter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.IdentityBrokerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantListCryptoBrokersException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.common.adapters.CryptoBrokerIdentityInfoAdapter;
import com.bitdubai.sub_app.crypto_broker_identity.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CryptoBrokerIdentityListFragment
        extends FermatListFragment<CryptoBrokerIdentityInformation, ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager>>
        implements FermatListItemListeners<CryptoBrokerIdentityInformation> {

    // Constants
    private static final String TAG = "BrokerIdentityList";

    // Data
    private ArrayList<CryptoBrokerIdentityInformation> identityInformationList;

    View emptyListViewsContainer;

    private PresentationDialog presentationDialog;

    private View layout;
    private BrokerIdentityBroadcastReceiver broadcastReceiver;


    public static CryptoBrokerIdentityListFragment newInstance() {
        return new CryptoBrokerIdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatIntentFilter fermatIntentFilter = new FermatIntentFilter(BroadcasterType.UPDATE_VIEW);
        broadcastReceiver = new BrokerIdentityBroadcastReceiver();
        registerReceiver(fermatIntentFilter, broadcastReceiver);

        cleanSessionData();
    }

    @Override
    public void onStart() {
        super.onStart();
        onRefresh();
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        this.layout = layout;

        emptyListViewsContainer = layout.findViewById(R.id.no_crypto_broker_identities);

        presentationDialog = new PresentationDialog.Builder(getActivity(), (ReferenceAppFermatSession) appSession)
                .setBannerRes(R.drawable.banner_identity)
                .setBody(R.string.cbp_broker_identity_welcome_body)
                .setSubTitle(R.string.cbp_broker_identity_welcome_subTitle)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setVIewColor(R.color.background_toolbar)
                .setIsCheckEnabled(false)
                .build();

        IdentityBrokerPreferenceSettings subappSettings;
        try {
            subappSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            subappSettings = null;
        }

        if (subappSettings == null) {
            subappSettings = new IdentityBrokerPreferenceSettings();
            subappSettings.setIsPresentationHelpEnabled(true);
            try {
                appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), subappSettings);
            } catch (Exception ignore) {

            }
        }

        boolean showDialog;
        try {
            showDialog = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isHomeTutorialDialogEnabled();
            if (showDialog) {
                presentationDialog.show();
            }
        } catch (FermatException e) {
            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        configureToolbar();
    }

    private void showOrHideNoIdentitiesView() {
        if (identityInformationList == null || identityInformationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyListViewsContainer.setVisibility(View.GONE);
        }
    }

    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = getToolbar();

        toolbar.setBackgroundResource(R.color.background_toolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == FragmentsCommons.HELP_OPTION_MENU_ID) {
            presentationDialog.show();
        }
        return true;
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new CryptoBrokerIdentityInfoAdapter(getActivity(), appSession.getErrorManager(), identityInformationList);
            adapter.setFermatListEventListener(this); // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_crypto_broker_identity_list;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.crypto_broker_identity_recycler_view;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    public List<CryptoBrokerIdentityInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoBrokerIdentityInformation> data = new ArrayList<>();
        try {
            data = appSession.getModuleManager().listIdentities(0, 0);
        } catch (CantListCryptoBrokersException ex) {
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(CryptoBrokerIdentityInformation data, int position) {
        appSession.setData(FragmentsCommons.IDENTITY_INFO, data);
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_EDIT_IDENTITY.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerIdentityInformation data, int position) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                identityInformationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(identityInformationList);
            }
        }
        showOrHideNoIdentitiesView();
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    private void cleanSessionData() {
        if (appSession.getData(FragmentsCommons.ACCURACY_DATA) != null)
            appSession.removeData(FragmentsCommons.ACCURACY_DATA);

        if (appSession.getData(FragmentsCommons.FREQUENCY_DATA) != null)
            appSession.removeData(FragmentsCommons.FREQUENCY_DATA);

        if (appSession.getData(FragmentsCommons.IDENTITY_INFO) != null)
            appSession.removeData(FragmentsCommons.IDENTITY_INFO);

        if (appSession.getData(FragmentsCommons.BROKER_NAME) != null)
            appSession.removeData(FragmentsCommons.BROKER_NAME);

        if (appSession.getData(FragmentsCommons.CROPPED_IMAGE) != null)
            appSession.removeData(FragmentsCommons.CROPPED_IMAGE);

        if (appSession.getData(FragmentsCommons.ORIGINAL_IMAGE) != null)
            appSession.removeData(FragmentsCommons.ORIGINAL_IMAGE);
    }

    /**
     * Receiver class implemented
     */
    private class BrokerIdentityBroadcastReceiver extends FermatBroadcastReceiver {

        @Override
        public void onReceive(FermatBundle fermatBundle) {
            try {
                if (isAttached) {
                    String code = fermatBundle.getString(Broadcaster.NOTIFICATION_TYPE);

                    if (code.equals(CBPBroadcasterConstants.CBI_BROKER_IDENTITY_CREATED)) {
                        onRefresh();
                        View emptyListViewsContainer = layout.findViewById(R.id.no_crypto_broker_identities);
                        emptyListViewsContainer.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    if (code.equals(CBPBroadcasterConstants.CBI_BROKER_IDENTITY_EDITED)) {
                        onRefresh();
                        View emptyListViewsContainer = layout.findViewById(R.id.no_crypto_broker_identities);
                        emptyListViewsContainer.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }

            } catch (ClassCastException e) {
                appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
    }
}
