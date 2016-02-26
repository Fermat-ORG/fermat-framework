package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.adapters.IssuerCommunityAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.interfaces.AdapterChangeListener;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.models.ActorIssuer;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.popup.ConnectDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.AssetIssuerCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.SessionConstantsAssetIssuerCommunity;
import com.bitdubai.fermat_dap_api.layer.all_definition.DAPConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by francisco on 21/10/15.
 */
public class IssuerCommunityHomeFragment extends AbstractFermatFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener,
        FermatListItemListeners<ActorIssuer> {

    public static final String ISSUER_SELECTED = "issuer";
    private static AssetIssuerCommunitySubAppModuleManager manager;
    private int IssuerNotificationsCount = 0;

    ErrorManager errorManager;

    // recycler
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private IssuerCommunityAdapter adapter;
    private View rootView;
    private LinearLayout emptyView;
    private Menu menu;

    private List<ActorIssuer> actors;
    private ActorIssuer actor;

    SettingsManager<AssetIssuerSettings> settingsManager;

    /**
     * Flags
     */
    private boolean isRefreshing = false;

    public static IssuerCommunityHomeFragment newInstance() {
        return new IssuerCommunityHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            manager = ((AssetIssuerCommunitySubAppSession) appSession).getModuleManager();
            actor = (ActorIssuer) appSession.getData(ISSUER_SELECTED);

            errorManager = appSession.getErrorManager();
            settingsManager = appSession.getModuleManager().getSettingsManager();

            IssuerNotificationsCount = manager.getWaitingYourConnectionActorAssetIssuerCount();
            new FetchCountTask().execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.home_dap_issuer_community_fragment, container, false);
//        initViews(rootView);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new IssuerCommunityAdapter(getActivity());
        adapter.setAdapterChangeListener(new AdapterChangeListener<ActorIssuer>() {
            @Override
            public void onDataSetChanged(List<ActorIssuer> dataSet) {
                actors = dataSet;

                boolean someSelected = false;
                for (ActorIssuer actor : actors) {
                    if (actor.selected) {
                        someSelected = true;
                        break;
                    }
                }

                if (someSelected) {
                    menu.getItem(2).setVisible(true);
                }
                else
                {
                    menu.getItem(2).setVisible(false);
                }

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setFermatListEventListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE);

        rootView.setBackgroundColor(Color.parseColor("#000b12"));
        emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();

        //Initialize settings
        settingsManager = appSession.getModuleManager().getSettingsManager();
        AssetIssuerSettings settings = null;
        try {
            settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }
        if (settings == null) {
            settings = new AssetIssuerSettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);

            try {
                settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        }

//        final AssetIssuerSettings assetIssuerSettingsTemp = settings;
//
//
//        Handler handlerTimer = new Handler();
//        handlerTimer.postDelayed(new Runnable() {
//            public void run() {
//                if (assetIssuerSettingsTemp.isPresentationHelpEnabled()) {
//                    setUpPresentation(false);
//                }
//            }
//        }, 500);

        return rootView;
    }

    private void setUpPresentation(boolean checkButton) {
//        try {
        PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBannerRes(R.drawable.banner_asset_issuer_community)
                .setIconRes(R.drawable.asset_issuer_comunity)
                .setVIewColor(R.color.dap_community_issuer_view_color)
                .setTitleTextColor(R.color.dap_community_issuer_view_color)
                .setSubTitle(R.string.dap_issuer_community_welcome_subTitle)
                .setBody(R.string.dap_issuer_community_welcome_body)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setIsCheckEnabled(checkButton)
                .build();

//            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    Object o = appSession.getData(SessionConstantsAssetIssuer.PRESENTATION_IDENTITY_CREATED);
//                    if (o != null) {
//                        if ((Boolean) (o)) {
//                            //invalidate();
//                            appSession.removeData(SessionConstantsAssetIssuer.PRESENTATION_IDENTITY_CREATED);
//                        }
//                    }
//                    try {
//                        IdentityAssetIssuer identityAssetIssuer = moduleManager.getActiveAssetIssuerIdentity();
//                        if (identityAssetIssuer == null) {
//                            getActivity().onBackPressed();
//                        } else {
//                            invalidate();
//                        }
//                    } catch (CantGetIdentityAssetIssuerException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        presentationDialog.show();
//        } catch (CantGetIdentityAssetIssuerException e) {
//            e.printStackTrace();
//        }
    }

//    protected void initViews(View layout) {
//
//        // fab action button create
//        ActionButton create = (ActionButton) layout.findViewById(R.id.create);
//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final ProgressDialog dialog = new ProgressDialog(getActivity());
//                dialog.setMessage("Connecting please wait...");
//                dialog.setCancelable(false);
//                dialog.show();
//                FermatWorker worker = new FermatWorker() {
//                    @Override
//                    protected Object doInBackground() throws Exception {
//                        List<ActorAssetIssuer> toConnect = new ArrayList<>();
//                        for (ActorIssuer actorIssuer : actors) {
//                            if (actorIssuer.selected)
//                                toConnect.add(actorIssuer.getRecord());
//                        }
//                        //// TODO: 20/11/15 get Actor asset issuer
//                        manager.connectToActorAssetIssuer(null, toConnect);
//                        return true;
//                    }
//                };
//                worker.setContext(getActivity());
//                worker.setCallBack(new FermatWorkerCallBack() {
//                    @Override
//                    public void onPostExecute(Object... result) {
//                        dialog.dismiss();
//                        if (swipeRefreshLayout != null)
//                            swipeRefreshLayout.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    onRefresh();
//                                }
//                            });
//                    }
//
//                    @Override
//                    public void onErrorOccurred(Exception ex) {
//                        dialog.dismiss();
//                        Toast.makeText(getActivity(), String.format("We have detected an error. Make sure you have created an Asset Issuer or Redeem Point identities using the corresponding Identity application."), Toast.LENGTH_LONG).show();
//                        ex.printStackTrace();
//                    }
//                });
//                worker.execute();
//
////                return true;
//                /* create new asset factory project */
////                selectedAsset = null;
////                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), appSession.getAppPublicKey(), getAssetForEdit());
//            }
//        });
//        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
//        create.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            });
    }

//    private void configureToolbar() {
//        Toolbar toolbar = getPaintActivtyFeactures().getToolbar();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            toolbar.setBackground(getResources().getDrawable(R.drawable.dap_action_bar_gradient_colors, null));
//        else
//            toolbar.setBackground(getResources().getDrawable(R.drawable.dap_action_bar_gradient_colors));
//
//        toolbar.setTitleTextColor(Color.WHITE);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        menu.add(0, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_CONNECT, 0, "Connect").setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(1, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_SELECT_ALL, 0, "Select All")//.setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(2, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_DESELECT_ALL, 0, "Deselect All")//.setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(3, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_PRESENTATION, 0, "Help").setIcon(R.drawable.dap_community_issuer_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.getItem(2).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_SELECT_ALL){

            for (ActorIssuer actorIssuer : actors)
            {
                actorIssuer.selected = true;
            }
            adapter.changeDataSet(actors);
            menu.getItem(2).setVisible(true);

        }

        if(id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_DESELECT_ALL){

            for (ActorIssuer actorIssuer : actors)
            {
                actorIssuer.selected = false;
            }
            adapter.changeDataSet(actors);
            menu.getItem(2).setVisible(false);
        }

        if (id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_CONNECT) {
            List<ActorAssetIssuer> toConnect = new ArrayList<>();
            for (ActorIssuer actorIssuer : actors) {
                if (actorIssuer.selected)
                    toConnect.add(actorIssuer.getRecord());
            }

            if (toConnect.size() > 0)
            {
                ConnectDialog connectDialog;
                connectDialog = new ConnectDialog(getActivity(), (AssetIssuerCommunitySubAppSession) appSession, null){
                    @Override
                    public void onClick(View v) {
                        int i = v.getId();
                        if (i == R.id.positive_button) {
                            final ProgressDialog dialog = new ProgressDialog(getActivity());
                            dialog.setMessage("Connecting please wait...");
                            dialog.setCancelable(false);
                            dialog.show();
                            FermatWorker worker = new FermatWorker() {
                                @Override
                                protected Object doInBackground() throws Exception {
                                    List<ActorAssetIssuer> toConnect = new ArrayList<>();
                                    for (ActorIssuer actorIssuer : actors) {
                                        if (actorIssuer.selected)
                                            toConnect.add(actorIssuer.getRecord());
                                    }
                                    //// TODO: 20/11/15 get Actor asset issuer
                                    manager.connectToActorAssetIssuer(null, toConnect);
                                    return true;
                                }
                            };
                            worker.setContext(getActivity());
                            worker.setCallBack(new FermatWorkerCallBack() {
                                @Override
                                public void onPostExecute(Object... result) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Connection request sent", Toast.LENGTH_SHORT).show();
                                    if (swipeRefreshLayout != null)
                                        swipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                onRefresh();
                                            }
                                        });
                                }

                                @Override
                                public void onErrorOccurred(Exception ex) {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "Redeem Point or Asset Issuer Identities must be created before using this app.", Toast.LENGTH_LONG).show();

                                }
                            });
                            worker.execute();
                            dismiss();
                        } else if (i == R.id.negative_button) {
                            dismiss();
                        }

                    }
                };
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send to");
                connectDialog.setUsername((toConnect.size() > 1) ? "" + toConnect.size() +
                        " Issuers" : toConnect.get(0).getName());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.show();
                return true;
            }
            else{
                Toast.makeText(getActivity(), "No Issuers selected to Connect", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        try {

            if (id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_PRESENTATION) {
                setUpPresentation(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Community Issuer system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationsBadge(int count) {
        IssuerNotificationsCount = count;
        getActivity().invalidateOptionsMenu();
    }

    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show &&
                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else if (!show && emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetIdentityAssetIssuerException {

    }

    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            return IssuerNotificationsCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(true);
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreData();
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    isRefreshing = false;
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            actors = (ArrayList<ActorIssuer>) result[0];
                            adapter.changeDataSet(actors);
                            if (actors.isEmpty()) {
                                showEmpty(true, emptyView);
                            } else {
                                showEmpty(false, emptyView);
                            }
                        }
                    } else
                        showEmpty(true, emptyView);
//                    if (actors == null || actors.isEmpty() && getActivity() != null) // for test purpose only
//                        Toast.makeText(getActivity(), "There are no registered actors...", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    isRefreshing = false;
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
        }
    }

    private synchronized List<ActorIssuer> getMoreData() throws Exception {
        List<ActorIssuer> dataSet = new ArrayList<>();
        List<AssetIssuerActorRecord> result = null;
        if (manager == null)
            throw new NullPointerException("AssetIssuerCommunitySubAppModuleManager is null");
        result = manager.getAllActorAssetIssuerRegistered();
        if (result != null && result.size() > 0) {
            for (AssetIssuerActorRecord record : result) {
                dataSet.add((new ActorIssuer(record)));
            }
        }
        return dataSet;
    }

    @Override
    public void onItemClickListener(ActorIssuer data, int position) {
        appSession.setData(ISSUER_SELECTED, data);
        changeActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case DAPConstants.DAP_UPDATE_VIEW_ANDROID:
                onRefresh();
                break;
            default:
                super.onUpdateViewOnUIThread(code);
        }
    }

    @Override
    public void onLongItemClickListener(ActorIssuer data, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
