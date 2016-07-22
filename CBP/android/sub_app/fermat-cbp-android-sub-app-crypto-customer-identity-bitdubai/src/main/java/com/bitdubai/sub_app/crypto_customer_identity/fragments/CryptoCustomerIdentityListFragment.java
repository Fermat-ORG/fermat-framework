package com.bitdubai.sub_app.crypto_customer_identity.fragments;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.IdentityCustomerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.common.adapters.CryptoCustomerIdentityInfoAdapter;
import com.bitdubai.sub_app.crypto_customer_identity.util.CryptoCustomerIdentityListFilter;
import com.bitdubai.sub_app.crypto_customer_identity.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CryptoCustomerIdentityListFragment
        extends FermatListFragment<CryptoCustomerIdentityInformation, ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager>>
        implements FermatListItemListeners<CryptoCustomerIdentityInformation> {

    // Data
    private List<CryptoCustomerIdentityInformation> identityInformationList;

    // UI
    private View noMatchView;
    View emptyListViewsContainer;

    private CryptoCustomerIdentityListFilter filter;
    private PresentationDialog presentationDialog;

    private View layout;

    public static CryptoCustomerIdentityListFragment newInstance() {
        return new CryptoCustomerIdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cleanSessionData();

        onRefresh();
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        this.layout = layout;

        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setDisplayShowHomeEnabled(false);
        }
        noMatchView = layout.findViewById(R.id.no_matches_crypto_customer_identity);
        emptyListViewsContainer = layout.findViewById(R.id.no_crypto_customer_identities);

        presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBannerRes(R.drawable.banner_identity_customer)
                .setBody(R.string.cbp_customer_identity_welcome_body)
                .setSubTitle(R.string.cbp_customer_identity_welcome_subTitle)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setVIewColor(R.color.ccc_color_dialog_identity)
                .setIsCheckEnabled(false)
                .build();

        IdentityCustomerPreferenceSettings subappSettings;
        try {
            subappSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            subappSettings = null;
        }

        if (subappSettings == null) {
            subappSettings = new IdentityCustomerPreferenceSettings();
            subappSettings.setIsPresentationHelpEnabled(true);
            try {
                appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), subappSettings);
            } catch (Exception e) {
                appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }

        boolean showDialog;
        try {
            showDialog = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isHomeTutorialDialogEnabled();
            if (showDialog) {
                presentationDialog.show();
            }
        } catch (FermatException e) {
            makeText(getActivity(), "Error dialogo", Toast.LENGTH_SHORT).show();
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

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cci_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cci_action_bar_gradient_colors));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == FragmentsCommons.HELP_OPTION_MENU_ID) {
            presentationDialog.show();
        }
        return true;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new CryptoCustomerIdentityInfoAdapter(getActivity(), identityInformationList);
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
        return R.layout.fragment_crypto_customer_identity_list;
    }


    @Override
    protected int getRecyclerLayoutId() {
        return R.id.crypto_customer_identity_recycler_view;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }


    @Override
    public List<CryptoCustomerIdentityInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoCustomerIdentityInformation> data = new ArrayList<>();

        try {
            data = appSession.getModuleManager().getAllCryptoCustomersIdentities(0, 0);
        } catch (CantGetCryptoCustomerListException ex) {
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(CryptoCustomerIdentityInformation data, int position) {
        appSession.setData(FragmentsCommons.IDENTITY_INFO, data);
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(CryptoCustomerIdentityInformation data, int position) {
        onRefresh();
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                identityInformationList = (List) result[0];
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
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {

        if (code.equalsIgnoreCase("cambios_en_el_identity_customer_creado")) {
            onRefresh();
            View emptyListViewsContainer = layout.findViewById(R.id.no_crypto_customer_identities);
            emptyListViewsContainer.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        if (code.equalsIgnoreCase("cambios_en_el_identity_customer_editado")) {
            onRefresh();
            View emptyListViewsContainer = layout.findViewById(R.id.no_crypto_customer_identities);
            emptyListViewsContainer.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void cleanSessionData() {
        if (appSession.getData(FragmentsCommons.ACCURACY_DATA) != null)
            appSession.removeData(FragmentsCommons.ACCURACY_DATA);

        if (appSession.getData(FragmentsCommons.FREQUENCY_DATA) != null)
            appSession.removeData(FragmentsCommons.FREQUENCY_DATA);

        if (appSession.getData(FragmentsCommons.IDENTITY_INFO) != null)
            appSession.removeData(FragmentsCommons.IDENTITY_INFO);

        if (appSession.getData(FragmentsCommons.CUSTOMER_NAME) != null)
            appSession.removeData(FragmentsCommons.CUSTOMER_NAME);

        if (appSession.getData(FragmentsCommons.CROPPED_IMAGE) != null)
            appSession.removeData(FragmentsCommons.CROPPED_IMAGE);

        if (appSession.getData(FragmentsCommons.ORIGINAL_IMAGE) != null)
            appSession.removeData(FragmentsCommons.ORIGINAL_IMAGE);
    }
}
