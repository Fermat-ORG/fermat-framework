package com.bitdubai.sub_app.crypto_customer_identity.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.common.adapters.CryptoCustomerIdentityInfoAdapter;
import com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession;
import com.bitdubai.sub_app.crypto_customer_identity.util.CommonLogger;
import com.bitdubai.sub_app.crypto_customer_identity.util.CryptoCustomerIdentityListFilter;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession.IDENTITY_INFO;

/**
 * A simple {@link Fragment} subclass.
 */
public class CryptoCustomerIdentityListFragment extends FermatListFragment<CryptoCustomerIdentityInformation> implements FermatListItemListeners<CryptoCustomerIdentityInformation>, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = "CustomerIdentityList";
    private CryptoCustomerIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<CryptoCustomerIdentityInformation> identityInformationList;
    private View noMatchView;
    private CryptoCustomerIdentityListFilter filter;
    private PresentationDialog presentationDialog;

    public static CryptoCustomerIdentityListFragment newInstance() {
        return new CryptoCustomerIdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            moduleManager = ((CryptoCustomerIdentitySubAppSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
            identityInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            if (errorManager != null) {
                errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY, UnexpectedSubAppExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
            }
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setDisplayShowHomeEnabled(false);
        }
        noMatchView = layout.findViewById(R.id.no_matches_crypto_customer_identity);
        if (identityInformationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            View emptyListViewsContainer = layout.findViewById(R.id.no_crypto_customer_identities);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }

        presentationDialog = new PresentationDialog.Builder(getActivity(),appSession)
                .setBannerRes(R.drawable.banner_identity)
                .setBody(R.string.cbp_customer_identity_welcome_body)
                .setSubTitle(R.string.cbp_customer_identity_welcome_subTitle)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .build();

        presentationDialog.show();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.crypto_customer_identity_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY.getCode(), appSession.getAppPublicKey());
        }
        if (item.getItemId() == R.id.action_help) {
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
            data = moduleManager.getAllCryptoCustomersIdentities(0, 0);
        } catch (CantGetCryptoCustomerListException ex) {
            if (errorManager != null) {
                errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        }
        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(CryptoCustomerIdentityInformation data, int position) {
        appSession.setData(IDENTITY_INFO, data);
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(CryptoCustomerIdentityInformation data, int position) {

    }

    @Override
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
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        if (filter == null) {
            CryptoCustomerIdentityInfoAdapter infoAdapter = (CryptoCustomerIdentityInfoAdapter) this.adapter;
            filter = (CryptoCustomerIdentityListFilter) infoAdapter.getFilter();
            filter.setNoMatchViews(noMatchView, recyclerView);
        }
        filter.filter(text);
        return true;
    }
}
