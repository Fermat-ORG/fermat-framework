package com.bitdubai.sub_app.crypto_customer_community.fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.adapters.RequestsListAdapter;
import com.bitdubai.sub_app.crypto_customer_community.common.dialogs.AcceptDialog;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alejandro Bicelis on 02/02/2016.
 */
public class RequestsTabFragment
        extends FermatListFragment<LinkedCryptoCustomerIdentity, ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>>
        implements FermatListItemListeners<LinkedCryptoCustomerIdentity>, OnLoadMoreDataListener {

    //Constants
    private static final int MAX = 10;
    protected static final String TAG = "RequestsTabFragment";

    //Managers
    private CryptoCustomerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private ArrayList<LinkedCryptoCustomerIdentity> requestsList = new ArrayList<>();
    private int offset;

    private RequestsListAdapter adapter;
    private ImageView noRequest;
    private PresentationDialog helpDialog;


    public static RequestsTabFragment newInstance() {
        return new RequestsTabFragment();
    }


    /**
     * Fragment interface implementation.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setHasOptionsMenu(true);

            //Get managers
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            moduleManager.setAppPublicKey(appSession.getAppPublicKey());

            loadingSettings();

        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);

        configureToolbar();

        moduleManager.setAppPublicKey(appSession.getAppPublicKey());

        noRequest = (ImageView) rootView.findViewById(R.id.ccc_no_notifications);

        onRefresh();
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccc_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccc_action_bar_gradient_colors));
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.ccc_fragment_requests_tab;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipeRefresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.ccc_request_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new RequestsListAdapter(getActivity(), requestsList);
            adapter.setFermatListEventListener(this);
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
    public RecyclerView.OnScrollListener getScrollListener() {
        //TODO: Descomentar esto cuando funcione lo de la paginacion en P2P
//        if (scrollListener == null) {
//            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(getLayoutManager());
//            endlessScrollListener.setOnLoadMoreDataListener(this);
//            scrollListener = endlessScrollListener;
//        }
//
//        return scrollListener;

        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                if (helpDialog == null)
                    helpDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                            .setBannerRes(R.drawable.ccc_banner)
                            .setIconRes(R.drawable.crypto_customer)
                            .setSubTitle(R.string.cbp_ccc_launch_action_creation_dialog_sub_title)
                            .setBody(R.string.cbp_ccc_launch_action_creation_dialog_body)
                            .setVIewColor(R.color.ccc_color_dialog)
                            .setIsCheckEnabled(false)
                            .build();

                helpDialog.show();

                return true;
        }

        return false;
    }

    @Override
    public void onItemClickListener(final LinkedCryptoCustomerIdentity data, final int position) {
        try {
            AcceptDialog acceptDialog = new AcceptDialog(getActivity(), appSession, appResourcesProviderManager,
                    data, moduleManager.getSelectedActorIdentity());

            acceptDialog.setTitle("Connection Request");
            acceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onRefresh();
                }
            });

            acceptDialog.show();

        } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongItemClickListener(LinkedCryptoCustomerIdentity data, int position) {
    }

    @Override
    public void onLoadMoreData(int page, final int totalItemsCount) {
        adapter.setLoadingData(true);
        FermatWorker fermatWorker = new FermatWorker(getActivity(), this) {
            @Override
            protected Object doInBackground() throws Exception {
                return getMoreDataAsync(FermatRefreshTypes.NEW, totalItemsCount);
            }
        };

        fermatWorker.execute();
    }

    @Override
    public List<LinkedCryptoCustomerIdentity> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<LinkedCryptoCustomerIdentity> dataSet = new ArrayList<>();
        try {
            offset = pos;
            final CryptoCustomerCommunitySelectableIdentity selectedActorIdentity = moduleManager.getSelectedActorIdentity();
            List<LinkedCryptoCustomerIdentity> result = moduleManager.listCryptoCustomersPendingLocalAction(selectedActorIdentity, MAX, offset);
            dataSet.addAll(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            adapter.setLoadingData(false);
            if (result != null && result.length > 0) {

                if (adapter != null) {
                    if (offset == 0) {
                        requestsList.clear();
                        requestsList.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(requestsList);
                    } else {
                        requestsList.addAll((ArrayList) result[0]);
                        adapter.notifyItemRangeInserted(offset, requestsList.size() - 1);
                    }

                }
            }
        }

        showOrHideEmptyView();
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            Log.e(TAG, ex.getMessage(), ex);
        }

        Toast.makeText(getActivity(), "Sorry there was a problem loading the data", Toast.LENGTH_SHORT).show();
    }

    /**
     * Obtain Settings or create new Settings if first time opening subApp
     */
    private void loadingSettings() {
        CryptoCustomerCommunitySettings appSettings;
        try {
            appSettings = this.moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            appSettings = null;
        }

        if (appSettings == null) {
            appSettings = new CryptoCustomerCommunitySettings();
            appSettings.setIsPresentationHelpEnabled(true);
            try {
                moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Show or hide the empty view if there is data to show
     */
    private void showOrHideEmptyView() {
        final boolean show = requestsList.isEmpty();
        final int animationResourceId = show ? android.R.anim.fade_in : android.R.anim.fade_out;

        Animation anim = AnimationUtils.loadAnimation(getActivity(), animationResourceId);
        if (show && (noRequest.getVisibility() == View.GONE || noRequest.getVisibility() == View.INVISIBLE)) {
            noRequest.setAnimation(anim);
            noRequest.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

        } else if (!show && noRequest.getVisibility() == View.VISIBLE) {
            noRequest.setAnimation(anim);
            noRequest.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();
        if (isAttached) onRefresh();
    }
}
