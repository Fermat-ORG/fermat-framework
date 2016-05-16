package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.adapters.IssuerCommunityAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.interfaces.AdapterChangeListener;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.models.ActorIssuer;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.popup.CancelDialog;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.popup.ConnectDialog;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions.AssetIssuerCommunitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions.SessionConstantsAssetIssuerCommunity;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
    private int issuerNotificationsCount = 0;

    ErrorManager errorManager;

    // recycler
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private IssuerCommunityAdapter adapter;
    private View rootView;
    private LinearLayout emptyView;


    private MenuItem menuItemSelect;
    private MenuItem menuItemUnselect;
    private MenuItem menuItemCancel;
    private MenuItem menuItemConnect;

    private List<ActorIssuer> actors;
    private List<ActorIssuer> actorsConnecting;
    private List<ActorAssetIssuer> actorsToConnect;
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

            issuerNotificationsCount = manager.getWaitingYourConnectionActorAssetIssuerCount();
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
                int selectedActors=0;
                int cheackeableActors = 0;
                List<ActorIssuer> actorsSelected = new ArrayList<>();
                actorsConnecting = new ArrayList<>();
                actorsToConnect = new ArrayList<>();

                for (ActorIssuer actor : actors) {
                    if (actor.getRecord().getExtendedPublicKey() == null)
                        cheackeableActors++;
                    if (actor.selected) {

                        actorsSelected.add(actor);
                        if (actor.getRecord().getDapConnectionState().equals(DAPConnectionState.CONNECTING))
                        {
                            actorsConnecting.add(actor);
                        }
                        if (!(actor.getRecord().getDapConnectionState().equals(DAPConnectionState.CONNECTING))){
                            actorsToConnect.add(actor.getRecord());
                        }

                        someSelected = true;
                        selectedActors++;

                    }
                }
                if (actorsConnecting.size() > 0)
                {
                    menuItemCancel.setVisible(true);
                }
                else {
                    menuItemCancel.setVisible(false);
                }

                if (someSelected) {
                    if (actorsConnecting.size() == selectedActors) {
                        menuItemConnect.setVisible(false);
                    }else if(actorsConnecting.size() == 0){
                        menuItemConnect.setVisible(true);
                    }
                    if (selectedActors > actorsConnecting.size()){
                        menuItemConnect.setVisible(true);
                    }
                    menuItemUnselect.setVisible(true);
                    if (selectedActors == cheackeableActors)
                    {
                        menuItemSelect.setVisible(false);
                    } else {
                        menuItemSelect.setVisible(true);
                    }
                }
                else
                {
                    restartButtons();
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

        menu.add(0, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_CONNECT, 0, R.string.connect).setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(1, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_CANCEL_CONNECTING, 0, "Cancel Connecting")//.setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(2, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_SELECT_ALL, 0, R.string.select_all)//.setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(3, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_UNSELECT_ALL, 0, R.string.unselect_all)//.setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(4, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_PRESENTATION, 0, R.string.help).setIcon(R.drawable.dap_community_issuer_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);


        menuItemConnect = menu.getItem(0);
        menuItemCancel = menu.getItem(1);
        menuItemSelect = menu.getItem(2);
        menuItemUnselect = menu.getItem(3);
        restartButtons();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_SELECT_ALL){

            for (ActorIssuer actorIssuer : actors)
            {
                if (actorIssuer.getRecord().getExtendedPublicKey() == null)
                    actorIssuer.selected = true;
            }
            adapter.changeDataSet(actors);
            adapter.getAdapterChangeListener().onDataSetChanged(actors);
            menuItemConnect.setVisible(true);
            menuItemSelect.setVisible(false);
            menuItemUnselect.setVisible(true);

        }

        if(id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_UNSELECT_ALL){

            for (ActorIssuer actorIssuer : actors)
            {
                actorIssuer.selected = false;
            }
            adapter.changeDataSet(actors);
            adapter.getAdapterChangeListener().onDataSetChanged(actors);
            restartButtons();
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
                            dialog.setMessage(getString(R.string.connecting));
                            dialog.setCancelable(false);
                            dialog.show();
                            FermatWorker worker = new FermatWorker() {
                                @Override
                                protected Object doInBackground() throws Exception {
                                    List<ActorAssetIssuer> toConnect = new ArrayList<>();
                                    for (ActorIssuer actorIssuer : actors) {
                                        if (actorIssuer.selected && !(actorIssuer.getRecord().getDapConnectionState().equals(DAPConnectionState.CONNECTING))){
                                            toConnect.add(actorIssuer.getRecord());
                                        }
                                    }
                                    //// TODO: 20/11/15 get Actor asset issuer
                                    manager.askActorAssetIssuerForConnection(toConnect);

                                    Intent broadcast = new Intent(SessionConstantsAssetIssuerCommunity.LOCAL_BROADCAST_CHANNEL);
                                    broadcast.putExtra(SessionConstantsAssetIssuerCommunity.BROADCAST_CONNECTED_UPDATE, true);
                                    sendLocalBroadcast(broadcast);

//                                    manager.connectToActorAssetIssuer(null, toConnect);
                                    return true;
                                }
                            };
                            worker.setContext(getActivity());
                            worker.setCallBack(new FermatWorkerCallBack() {
                                @Override
                                public void onPostExecute(Object... result) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), R.string.connection_request_send, Toast.LENGTH_SHORT).show();
                                    restartButtons();
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
                                    Toast.makeText(getActivity(), R.string.before_action, Toast.LENGTH_LONG).show();

                                }
                            });
                            worker.execute();
                            dismiss();
                        } else if (i == R.id.negative_button) {
                            dismiss();
                        }

                    }
                };
                connectDialog.setTitle(R.string.connection_request_title);
                connectDialog.setDescription(getString(R.string.connection_request_desc));
                connectDialog.setUsername((actorsToConnect.size() > 1) ? "" + actorsToConnect.size() +
                        " Issuers" : actorsToConnect.get(0).getName());
                connectDialog.setSecondDescription(getString(R.string.connection_request_desc_two));
                connectDialog.show();
                return true;
            }
            else{
                Toast.makeText(getActivity(), R.string.no_issuers_selected, Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_CANCEL_CONNECTING) {
            CancelDialog cancelDialog;

            cancelDialog = new CancelDialog(getActivity(), (AssetIssuerCommunitySubAppSession) appSession, null){
                @Override
                public void onClick(View v) {
                    int i = v.getId();
                    if (i == R.id.positive_button) {

                        final ProgressDialog dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Canceling, please wait...");
                        dialog.setCancelable(false);
                        dialog.show();
                        FermatWorker worker = new FermatWorker() {
                            @Override
                            protected Object doInBackground() throws Exception {


                                for(ActorIssuer actor: actorsConnecting) {
                                    //TODO revisar si esto es asi
                                    manager.cancelActorAssetIssuer(actor.getRecord());
                                }

                                    /*Intent broadcast = new Intent(SessionConstantsAssetUserCommunity.LOCAL_BROADCAST_CHANNEL);
                                    broadcast.putExtra(SessionConstantsAssetUserCommunity.BROADCAST_CONNECTED_UPDATE, true);
                                    sendLocalBroadcast(broadcast);*/
                                return true;
                            }
                        };
                        worker.setContext(getActivity());
                        worker.setCallBack(new FermatWorkerCallBack() {
                            @Override
                            public void onPostExecute(Object... result) {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Cancelation performed successfully", Toast.LENGTH_SHORT).show();
                                restartButtons();
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
                                    /*TODO aun no se que error deberia ir aqui*/
//                                Toast.makeText(getActivity(), String.format("An exception has been thrown: %s", ex.getMessage()), Toast.LENGTH_LONG).show();
                                Toast.makeText(getActivity(), "Can't cancel connection to selected issuers", Toast.LENGTH_LONG).show();
//                                ex.printStackTrace();
                            }
                        });
                        worker.execute();


                        dismiss();
                    } else if (i == R.id.negative_button) {
                        dismiss();
                    }
                }
            };
            cancelDialog.setTitle("Cancel request");
            cancelDialog.setDescription("Do you want to cancel connection with ");
            cancelDialog.setUsername((actorsConnecting.size() > 1) ? "" + actorsConnecting.size() +
                    " Issuers" : actorsConnecting.get(0).getRecord().getName());
            //connectDialog.setSecondDescription("a connection request");
            cancelDialog.show();
            return true;
        }
        try {

            if (id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_PRESENTATION) {
                setUpPresentation(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_issuer_community_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationsBadge(int count) {
        issuerNotificationsCount = count;
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
            return issuerNotificationsCount;
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
                    restartButtons();
//                    if (actors == null || actors.isEmpty() && getActivity() != null) // for test purpose only
//                        Toast.makeText(getActivity(), "There are no registered actors...", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    isRefreshing = false;
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                    if (getActivity() != null)
                        showEmpty(true, emptyView);
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
//                    ex.printStackTrace();
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
    
    private void restartButtons()
    {
        menuItemCancel.setVisible(false);
        menuItemSelect.setVisible(true);
        menuItemUnselect.setVisible(false);
        menuItemConnect.setVisible(false);
        
    }



}
