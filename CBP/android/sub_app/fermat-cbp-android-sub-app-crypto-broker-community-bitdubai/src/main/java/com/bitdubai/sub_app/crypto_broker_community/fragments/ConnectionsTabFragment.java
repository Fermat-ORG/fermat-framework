package com.bitdubai.sub_app.crypto_broker_community.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.EndlessScrollListener;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_android_api.ui.util.SearchViewStyleHelper;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.adapters.ConnectionsListAdapter;
import com.bitdubai.sub_app.crypto_broker_community.common.dialogs.DisconnectDialog;
import com.bitdubai.sub_app.crypto_broker_community.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ConnectionsTabFragment
        extends FermatListFragment<CryptoBrokerCommunityInformation, ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>>
        implements FermatListItemListeners<CryptoBrokerCommunityInformation>, OnLoadMoreDataListener {

    //Constants
    private static final int MAX = 10;
    private static final int SPAN_COUNT = 2;
    protected static final String TAG = "ConnectionsTabFragment";

    //Managers
    private CryptoBrokerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private ArrayList<CryptoBrokerCommunityInformation> connectedActorList = new ArrayList<>();
    private int offset;

    private ConnectionsListAdapter adapter;
    private PresentationDialog helpDialog;
    private ImageView noContacts;


    public static ConnectionsTabFragment newInstance() {
        return new ConnectionsTabFragment();
    }


    /**
     * Fragment interface implementation.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        moduleManager.setAppPublicKey(appSession.getAppPublicKey());

        loadSettings();

        onRefresh();
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);

        configureToolbar();

//        moduleManager.setAppPublicKey(appSession.getAppPublicKey());

        noContacts = (ImageView) rootView.findViewById(R.id.cbc_no_contacts);
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbc_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbc_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.cbc_fragment_connections_tab;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.gridView;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ConnectionsListAdapter(getActivity(), connectedActorList);
            adapter.setFermatListEventListener(this);
        }

        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
//        if (layoutManager == null) {
//            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    final int itemViewType = adapter.getItemViewType(position);
//                    switch (itemViewType) {
//                        case AvailableActorsListAdapter.DATA_ITEM:
//                            return 1;
//                        case AvailableActorsListAdapter.LOADING_ITEM:
//                            return SPAN_COUNT;
//                        default:
//                            return GridLayoutManager.DEFAULT_SPAN_COUNT;
//                    }
//                }
//            });
//
//            layoutManager = gridLayoutManager;
//        }

        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }

        return layoutManager;
    }

    @Override
    public RecyclerView.OnScrollListener getScrollListener() {
        if (scrollListener == null) {
            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(getLayoutManager());
            endlessScrollListener.setOnLoadMoreDataListener(this);
            scrollListener = endlessScrollListener;
        }

        return scrollListener;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        final MenuItem menuItem = menu.findItem(FragmentsCommons.SEARCH_FILTER_OPTION_MENU_ID);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        SearchViewStyleHelper.on(searchView)
                .setCursorColor(Color.WHITE)
                .setTextColor(Color.WHITE)
                .setHintTextColor(Color.WHITE)
                .setSearchHintDrawable(R.drawable.lupa_blanca)
                .setSearchButtonImageResource(R.drawable.lupa_blanca)
                .setCloseBtnImageResource(R.drawable.x_blanca)
                .setSearchPlateTint(Color.WHITE)
                .setSubmitAreaTint(Color.WHITE);

        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<CryptoBrokerCommunityInformation> filteredList = filterList(newText, connectedActorList);
                adapter.changeDataSet(filteredList);
                return true;
            }
        });
    }

    private List<CryptoBrokerCommunityInformation> filterList(String filterText, List<CryptoBrokerCommunityInformation> baseList) {
        final ArrayList<CryptoBrokerCommunityInformation> filteredList = new ArrayList<>();
        for (CryptoBrokerCommunityInformation item : baseList) {
            if (item.getAlias().toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                if (helpDialog == null)
                    helpDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                            .setBannerRes(R.drawable.cbc_banner)
                            .setIconRes(R.drawable.crypto_broker)
                            .setSubTitle(R.string.cbp_cbc_launch_action_creation_dialog_sub_title)
                            .setBody(R.string.cbp_cbc_launch_action_creation_dialog_body)
                            .setVIewColor(R.color.cbc_toolbar_start_background)
                            .setIsCheckEnabled(false)
                            .build();

                helpDialog.show();
                return true;
        }

        return false;
    }

    @Override
    public void onItemClickListener(final CryptoBrokerCommunityInformation data, final int position) {
        try {
            DisconnectDialog connectDialog = new DisconnectDialog(getActivity(), appSession, appResourcesProviderManager,
                    data, moduleManager.getSelectedActorIdentity());

            connectDialog.setTitle("Confirm Disconnection");
            connectDialog.setDescription(String.format("Do you want to disconnect from %1$s?", data.getAlias()));
            connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    updateSelectedActorInList(position);
                }
            });

            connectDialog.show();

        } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e) {
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerCommunityInformation data, int position) {
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
    public List<CryptoBrokerCommunityInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoBrokerCommunityInformation> dataSet = new ArrayList<>();
        if (isVisible) {
            try {
                offset = pos;
                if (moduleManager.getSelectedActorIdentity() != null) {
                    final CryptoBrokerCommunitySelectableIdentity selectedActorIdentity = moduleManager.getSelectedActorIdentity();
                    List<CryptoBrokerCommunityInformation> result = moduleManager.listAllConnectedCryptoBrokers(selectedActorIdentity, MAX, offset);
                    dataSet.addAll(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataSet;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            adapter.setLoadingData(false);
            if (result != null && result.length > 0) {

                if (adapter != null) {
                    if (offset == 0) {
                        connectedActorList.clear();
                        connectedActorList.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(connectedActorList);
                    } else {
                        connectedActorList.addAll((ArrayList) result[0]);
                        adapter.notifyItemRangeInserted(offset, connectedActorList.size() - 1);
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
    private void loadSettings() {
        CryptoBrokerCommunitySettings appSettings;
        try {
            appSettings = this.moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            appSettings = null;
        }

        if (appSettings == null) {
            appSettings = new CryptoBrokerCommunitySettings();
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
        final boolean show = connectedActorList.isEmpty();
        final int animationResourceId = show ? android.R.anim.fade_in : android.R.anim.fade_out;

        Animation anim = AnimationUtils.loadAnimation(getActivity(), animationResourceId);
        if (show && (noContacts.getVisibility() == View.GONE || noContacts.getVisibility() == View.INVISIBLE)) {
            noContacts.setAnimation(anim);
            noContacts.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

        } else if (!show && noContacts.getVisibility() == View.VISIBLE) {
            noContacts.setAnimation(anim);
            noContacts.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Update the actor information and notify the adapter to show the updated info
     *
     * @param position the actor's position in the adapter
     */
    private void updateSelectedActorInList(int position) {
        if (appSession.getData(FragmentsCommons.CONNECTION_RESULT) == null)
            return;

        try {
            final ConnectionState newConnectionState = (ConnectionState) appSession.getData(FragmentsCommons.CONNECTION_RESULT);
            appSession.removeData(FragmentsCommons.CONNECTION_RESULT);

            if (newConnectionState == ConnectionState.DISCONNECTED_LOCALLY) {
                connectedActorList.remove(position);
                adapter.notifyItemRemoved(position);
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();

        onRefresh();
    }
}
