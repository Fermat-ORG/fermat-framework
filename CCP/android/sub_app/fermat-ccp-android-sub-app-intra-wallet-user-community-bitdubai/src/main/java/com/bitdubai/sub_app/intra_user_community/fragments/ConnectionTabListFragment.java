package com.bitdubai.sub_app.intra_user_community.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.EndlessScrollListener;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraUserWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.AppFriendsListAdapter;
import com.bitdubai.sub_app.intra_user_community.common.popups.DeleteAllContactsDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.GeolocationDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.IntraUserDisconectDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.PresentationIntraUserCommunityDialog;
import com.bitdubai.sub_app.intra_user_community.common.utils.FragmentsCommons;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14/07/16.
 */
public class ConnectionTabListFragment extends FermatListFragment<IntraUserInformation,ReferenceAppFermatSession<IntraUserModuleManager>>
        implements FermatListItemListeners<IntraUserInformation>, OnLoadMoreDataListener,GeolocationDialog.AdapterCallback {

    //Constants
    private static final int MAX = 10;
    private static final int SPAN_COUNT = 2;
    protected static final String TAG = "ConnectionsTabFragment";

    //Managers
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;
    private FermatApplicationCaller fermatApplicationCaller;
    private ReferenceAppFermatSession<IntraUserModuleManager> intraUserSubAppSession;


    private ArrayList<IntraUserInformation> intraUserlist = new ArrayList<>();
    private int offset;

    private AppFriendsListAdapter adapter;
    private PresentationDialog helpDialog;
    private LinearLayout noContacts;

    private FermatWorker fermatWorker;

    public static ConnectionTabListFragment newInstance() {
        return new ConnectionTabListFragment();
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
        intraUserSubAppSession = appSession;
        fermatApplicationCaller = ((FermatApplicationSession)getActivity().getApplicationContext()).getApplicationManager();

        loadSettings();

        onRefresh();
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        configureToolbar();
        noContacts = (LinearLayout) rootView.findViewById(R.id.empty_view);
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setElevation(15);

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    @Override
    protected boolean hasMenu() { return true; }

    @Override
    protected int getLayoutResource() { return R.layout.ccp_connection_tab_list_main;}

    @Override
    protected int getSwipeRefreshLayoutId() { return R.id.swipe_refresh; }

    @Override
    protected int getRecyclerLayoutId() { return R.id.my_recycler_view; }

    @Override
    protected boolean recyclerHasFixedSize() { return true; }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new AppFriendsListAdapter(getActivity(), intraUserlist);
            adapter.setFermatListEventListener(this);
        }

        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    final int itemViewType = adapter.getItemViewType(position);
                    switch (itemViewType) {
                        case AppFriendsListAdapter.DATA_ITEM:
                            return 1;
                        case AppFriendsListAdapter.LOADING_ITEM:
                            return SPAN_COUNT;
                        default:
                            return GridLayoutManager.DEFAULT_SPAN_COUNT;
                    }
                }
            });

            layoutManager = gridLayoutManager;
        }


        return layoutManager;
    }

    private List<IntraUserInformation> filterList(String filterText, List<IntraUserInformation> baseList) {
        final ArrayList<IntraUserInformation> filteredList = new ArrayList<>();
        for (IntraUserInformation item : baseList) {
            if (item.getName().toLowerCase().contains(filterText.toLowerCase()))
                filteredList.add(item);
        }

        return filteredList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                break;
            case 2:
                try {
                    GeolocationDialog geolocationDialog = new GeolocationDialog(getActivity(),appSession, null, this);
                    geolocationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    Window window = geolocationDialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.TOP;
                    //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);
                    //geolocationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    geolocationDialog.show();

                } catch ( Exception e) {
                    e.printStackTrace();
                }

                break;

            case 3:
                DeleteAllContactsDialog deleteAllContactsDialog = null;
                try {
                    deleteAllContactsDialog = new DeleteAllContactsDialog(getActivity(),appSession,null,moduleManager.getActiveIntraUserIdentity());
                    deleteAllContactsDialog.show();
                } catch (CantGetActiveLoginIdentityException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try{
                    fermatApplicationCaller.openFermatApp(SubAppsPublicKeys.CCP_IDENTITY.getCode());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 5:
                showDialogHelp();
                break;
        }

        return false;
    }


    @Override
    public void onItemClickListener(IntraUserInformation data, int position) {
        IntraUserDisconectDialog intraUserDisconectDialog = null;
        try {
            intraUserDisconectDialog = new IntraUserDisconectDialog(getActivity(), (ReferenceAppFermatSession) appSession, null, data, moduleManager.getActiveIntraUserIdentity());
            intraUserDisconectDialog.show();
            intraUserDisconectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    onRefresh();
                }
            });
            updateSelectedActorInList(position);
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLongItemClickListener(IntraUserInformation data, int position) {

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
                        intraUserlist.clear();
                        intraUserlist.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(intraUserlist);
                    } else {
                        intraUserlist.addAll((ArrayList) result[0]);
                        adapter.notifyItemRangeInserted(offset, intraUserlist.size() - 1);
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

    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();
        onRefresh();
    }

    private void showOrHideEmptyView() {
        final boolean show = intraUserlist.isEmpty();
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

    private void loadSettings() {
        IntraUserWalletSettings appSettings;
        try {
            appSettings = this.moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            appSettings = null;
        }

        if (appSettings == null) {
            appSettings = new IntraUserWalletSettings();
            appSettings.setIsPresentationHelpEnabled(true);
            try {
                moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public void onLoadMoreData(int page, final int totalItemsCount) {
        adapter.setLoadingData(true);
        fermatWorker = new FermatWorker(getActivity(), this) {
            @Override
            protected Object doInBackground() throws Exception {
                return getMoreDataAsync(FermatRefreshTypes.NEW, totalItemsCount);
            }
        };

        fermatWorker.execute();
    }

    @Override
    public List<IntraUserInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<IntraUserInformation> dataSet = new ArrayList<>();

        try {
            offset = pos;
            if(moduleManager.getSelectedActorIdentity() != null) {
                final String intraUserIdentity = moduleManager.getActiveIntraUserIdentity().getPublicKey();
                List<IntraUserInformation> result = moduleManager.getAllIntraUsers(intraUserIdentity, MAX, offset);
                dataSet.addAll(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }

    private void updateSelectedActorInList(int position) {
        if (appSession.getData(FragmentsCommons.CONNECTION_RESULT) == null)
            return;

        try {
            final ConnectionState newConnectionState = (ConnectionState) appSession.getData(FragmentsCommons.CONNECTION_RESULT);
            appSession.removeData(FragmentsCommons.CONNECTION_RESULT);

            if (newConnectionState == ConnectionState.DISCONNECTED_LOCALLY) {
                intraUserlist.remove(position);
                adapter.notifyItemRemoved(position);
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedSubAppException(SubApps.CCP_INTRA_USER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }



    @Override
    public void onStop() {
        if (fermatWorker != null)
            fermatWorker.shutdownNow();
        super.onStop();
    }

    private void showDialogHelp() {
        try {
            if (moduleManager.getActiveIntraUserIdentity() != null) {
                if (!moduleManager.getActiveIntraUserIdentity().getPublicKey().isEmpty()) {
                    PresentationIntraUserCommunityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserCommunityDialog(getActivity(),
                            intraUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationIntraUserCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
                    presentationIntraUserCommunityDialog.show();
                    presentationIntraUserCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // showCriptoUsersCache();
                            onRefresh();
                            invalidate();
                        }
                    });
                } else {
                    PresentationIntraUserCommunityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserCommunityDialog(getActivity(),
                            intraUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationIntraUserCommunityDialog.TYPE_PRESENTATION);
                    presentationIntraUserCommunityDialog.show();
                    presentationIntraUserCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Boolean isBackPressed = (Boolean) intraUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
                            if (isBackPressed != null) {
                                if (isBackPressed) {
                                    getActivity().finish();
                                }
                            } else {
                                onRefresh();
                                //showCriptoUsersCache();

                            }
                        }
                    });
                }
            } else {
                PresentationIntraUserCommunityDialog presentationIntraUserCommunityDialog = new PresentationIntraUserCommunityDialog(getActivity(),
                        intraUserSubAppSession,
                        null,
                        moduleManager,
                        PresentationIntraUserCommunityDialog.TYPE_PRESENTATION);
                presentationIntraUserCommunityDialog.show();
                presentationIntraUserCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Boolean isBackPressed = null;

                        isBackPressed = (Boolean) intraUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);

                        if (isBackPressed != null) {
                            if (isBackPressed) {
                                getActivity().onBackPressed();
                            }
                        } else
                            onRefresh();
                        //showCriptoUsersCache();
                    }
                });
            }
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMethodCallback(ExtendedCity cityFromList) {
    }
}
