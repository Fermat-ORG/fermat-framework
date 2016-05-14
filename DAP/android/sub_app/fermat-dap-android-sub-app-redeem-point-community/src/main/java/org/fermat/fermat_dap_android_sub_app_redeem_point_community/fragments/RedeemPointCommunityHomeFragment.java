package org.fermat.fermat_dap_android_sub_app_redeem_point_community.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.adapters.RedeemPointCommunityAdapter;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.interfaces.AdapterChangeListener;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.popup.CancelDialog;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.popup.ConnectDialog;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.popup.DisconnectDialog;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions.AssetRedeemPointCommunitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions.SessionConstantRedeemPointCommunity;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * changed by jinmy Bohorquez on 11/02/16
 */
public class RedeemPointCommunityHomeFragment extends AbstractFermatFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener,
        FermatListItemListeners<Actor> {

    protected final String TAG = "RedeemCommunityFragment";

    public static final String REDEEM_POINT_SELECTED = "redeemPoint";
    private static RedeemPointCommunitySubAppModuleManager moduleManager;
    AssetRedeemPointCommunitySubAppSession assetRedeemPointCommunitySubAppSession;
    RedeemPointSettings settings = null;
    private int redeemNotificationsCount = 0;

    private List<Actor> actorsConnecting;
    private List<ActorAssetRedeemPoint> actorsToConnect;
    private List<Actor> actors;
    private Actor actor;
    static ErrorManager errorManager;

    // recycler
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RedeemPointCommunityAdapter adapter;
    private View rootView;
    private LinearLayout emptyView;
    private int offset = 0;
    private Menu menu;

    private MenuItem menuItemSelect;
    private MenuItem menuItemUnselect;
    private MenuItem menuItemConnect;
    private MenuItem menuItemDisconnect;
    private MenuItem menuItemCancel;

//    SettingsManager<RedeemPointSettings> settingsManager;

    /**
     * Flags
     */
    private boolean isRefreshing = false;

    public static RedeemPointCommunityHomeFragment newInstance() {
        return new RedeemPointCommunityHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setHasOptionsMenu(true);

            actor = (Actor) appSession.getData(REDEEM_POINT_SELECTED);

            assetRedeemPointCommunitySubAppSession = ((AssetRedeemPointCommunitySubAppSession) appSession);
            moduleManager = assetRedeemPointCommunitySubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            try {
                settings = assetRedeemPointCommunitySubAppSession.getModuleManager().loadAndGetSettings(assetRedeemPointCommunitySubAppSession.getAppPublicKey());
            } catch (Exception e) {
                settings = null;
            }

            if (assetRedeemPointCommunitySubAppSession.getAppPublicKey() != null) //the identity not exist yet
            {
                if (settings == null) {
                    settings = new RedeemPointSettings();
                    settings.setIsPresentationHelpEnabled(true);
                    assetRedeemPointCommunitySubAppSession.getModuleManager().persistSettings(assetRedeemPointCommunitySubAppSession.getAppPublicKey(), settings);
                }
            }

            redeemNotificationsCount = moduleManager.getWaitingYourConnectionActorAssetRedeemCount();
            new FetchCountTask().execute();
        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.home_dap_redeem_point_fragment, container, false);
//        initViews(rootView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RedeemPointCommunityAdapter(getActivity());
        adapter.setAdapterChangeListener(new AdapterChangeListener<Actor>() {
            @Override
            public void onDataSetChanged(List<Actor> dataSet) {

                actors = dataSet;
                boolean someSelected = false;
                int selectedActors = 0;
                int cheackeableActors = 0;
                List<Actor> actorsSelected = new ArrayList<>();
                actorsConnecting = new ArrayList<>();
                actorsToConnect = new ArrayList<>();

                for (Actor actor : actors) {
                    if (actor.getCryptoAddress() == null) {
                        cheackeableActors++;
                    }

                    if (actor.selected) {

                        actorsSelected.add(actor);
                        if (actor.getDapConnectionState().equals(DAPConnectionState.CONNECTING)) {
                            actorsConnecting.add(actor);
                        }
                        if (!(actor.getDapConnectionState().equals(DAPConnectionState.CONNECTING))) {
                            actorsToConnect.add(actor);
                        }
                        someSelected = true;
                        selectedActors++;
                    }
                }

                if (actorsConnecting.size() > 0) {
                    menuItemCancel.setVisible(true);

                } else {
                    menuItemCancel.setVisible(false);
                }

                if (someSelected) {
                    if (actorsConnecting.size() == selectedActors) {
                        menuItemConnect.setVisible(false);
                    } else if (actorsConnecting.size() == 0) {
                        menuItemConnect.setVisible(true);
                    }
                    if (selectedActors > actorsConnecting.size()) {
                        menuItemConnect.setVisible(true);
                    }
                    menuItemUnselect.setVisible(true);
                    if (selectedActors == cheackeableActors) {
                        menuItemSelect.setVisible(false);
                    } else {
                        menuItemSelect.setVisible(true);
                    }
//                    if(ableToDisconnect(actorsSelected)){
//                        menuItemConnect.setVisible(false);
//                        menuItemDisconnect.setVisible(true);
//                      /*TODO solucion temporal discutir*/
//                     /* if (cantSelected > 1){
//                          menuItemConnect.setVisible(false);
//                          menuItemDisconnect.setVisible(false);
//                      }*/
//                    }else if(!(ableToDisconnect(actorsSelected))&& cantSelected > 1 && !(ableToConnect(actorsSelected))){
//                        menuItemConnect.setVisible(false);
//                        menuItemDisconnect.setVisible(false);
//                    }

                } else {
                    restartButtons();
//                    menuItemUnselect.setVisible(false);
//                    menuItemSelect.setVisible(true);
//                    menuItemConnect.setVisible(true);
//                    menuItemDisconnect.setVisible(false);
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
//        settingsManager = appSession.getModuleManager().getSettingsManager();
//        RedeemPointSettings settings = null;
        try {
            settings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }
        if (settings == null) {
            settings = new RedeemPointSettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);
            settings.setNotificationEnabled(true);

            try {
                if (moduleManager != null) {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), settings);
                    moduleManager.setAppPublicKey(appSession.getAppPublicKey());
                }
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        }

        final RedeemPointSettings redeemPointSettingsTemp = settings;

        Handler handlerTimer = new Handler();
        handlerTimer.postDelayed(new Runnable() {
            public void run() {
                if (redeemPointSettingsTemp.isPresentationHelpEnabled()) {
                    setUpPresentation(false);
                }
            }
        }, 500);

        return rootView;
    }

    private void setUpPresentation(boolean checkButton) {
//        try {
        PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBannerRes(R.drawable.banner_redeem_point)
                .setIconRes(R.drawable.reddem_point_community)
                .setVIewColor(R.color.dap_community_redeem_view_color)
                .setTitleTextColor(R.color.dap_community_redeem_view_color)
                .setSubTitle(R.string.dap_redeem_community_welcome_subTitle)
                .setBody(R.string.dap_redeem_community_welcome_body)
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
//                        List<ActorAssetRedeemPoint> toConnect = new ArrayList<>();
//                        for (Actor actor : actors) {
//                            if (actor.selected)
//                                toConnect.add(actor);
//                        }
//                        //// TODO: 28/10/15 get Actor asset Redeem Point
//                        moduleManager.connectToActorAssetRedeemPoint(null, toConnect);
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
//                        Toast.makeText(getActivity(), String.format("We have detected an error. Make sure you have created an Asset User or Redeem Point identities using the corresponding Identity application."), Toast.LENGTH_LONG).show();
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
        menu.add(0, SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_CONNECT, 0, "Connect").setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(1, SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_DISCONNECT, 1, "Disconnect")//.setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(2, SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_CANCEL_CONNECTING, 2, "Cancel Connecting")//.setIcon(R.drawable.ic_sub_menu_connect)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(3, SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_HELP_SELECT_ALL, 3, "Select All")//.setIcon(R.drawable.dap_community_user_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(4, SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_HELP_UNSELECT_ALL, 4, "Unselect All")//.setIcon(R.drawable.dap_community_user_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(5, SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_HELP_PRESENTATION, 5, "Help").setIcon(R.drawable.dap_community_redeem_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menuItemConnect = menu.getItem(0);
        menuItemDisconnect = menu.getItem(1);
        menuItemCancel = menu.getItem(2);
        menuItemSelect = menu.getItem(3);
        menuItemUnselect = menu.getItem(4);
        restartButtons();

        //inflater.inflate(R.menu.dap_community_redeem_point_home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_HELP_SELECT_ALL) {

            for (Actor actorIssuer : actors) {
                if (actorIssuer.getCryptoAddress() == null) {
                    actorIssuer.selected = true;
                }
            }
            adapter.changeDataSet(actors);
            adapter.getAdapterChangeListener().onDataSetChanged(actors);
            menuItemConnect.setVisible(true);
            menuItemSelect.setVisible(false);
            menuItemUnselect.setVisible(true);

        }

        if (id == SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_HELP_UNSELECT_ALL) {

            for (Actor actorIssuer : actors) {
                actorIssuer.selected = false;
            }
            adapter.changeDataSet(actors);
            adapter.getAdapterChangeListener().onDataSetChanged(actors);
            menuItemSelect.setVisible(true);
            menuItemUnselect.setVisible(false);
            restartButtons();
        }


        if (id == SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_CONNECT) {
            List<ActorAssetRedeemPoint> actorsSelected = new ArrayList<>();
            for (Actor actor : actors) {
                if (actor.selected)
                    actorsSelected.add(actor);
            }
            if (actorsSelected.size() > 0) {
                ConnectDialog connectDialog;

                connectDialog = new ConnectDialog(getActivity(), (AssetRedeemPointCommunitySubAppSession) appSession, null) {
                    @Override
                    public void onClick(View v) {
                        int i = v.getId();
                        if (i == R.id.positive_button) {//


                            final ProgressDialog dialog = new ProgressDialog(getActivity());
                            dialog.setMessage("Connecting please wait...");
                            dialog.setCancelable(false);
                            dialog.show();
                            FermatWorker worker = new FermatWorker() {
                                @Override
                                protected Object doInBackground() throws Exception {
                                    List<ActorAssetRedeemPoint> toConnect = new ArrayList<>();
                                    for (Actor actor : actors) {
                                        if (actor.selected && !(actor.getDapConnectionState().equals(DAPConnectionState.CONNECTING))) {
                                            toConnect.add(actor);
                                        }
                                    }
                                    //// TODO: 28/10/15 get Actor asset Redeem Point
                                    moduleManager.askActorAssetRedeemForConnection(toConnect);

                                    Intent broadcast = new Intent(SessionConstantRedeemPointCommunity.LOCAL_BROADCAST_CHANNEL);
                                    broadcast.putExtra(SessionConstantRedeemPointCommunity.BROADCAST_CONNECTED_UPDATE, true);
                                    sendLocalBroadcast(broadcast);

//                                    moduleManager.connectToActorAssetRedeemPoint(null, toConnect);
                                    return true;
                                }
                            };
                            worker.setContext(getActivity());
                            worker.setCallBack(new FermatWorkerCallBack() {
                                @Override
                                public void onPostExecute(Object... result) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), R.string.dap_other_profile_request_send, Toast.LENGTH_SHORT).show();
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
//                                Toast.makeText(getActivity(), String.format("An exception has been thrown: %s", ex.getMessage()), Toast.LENGTH_LONG).show();
                                    Toast.makeText(getActivity(), R.string.before_action_redeem, Toast.LENGTH_LONG).show();
//                                ex.printStackTrace();
                                }
                            });
                            worker.execute();
//

                            dismiss();
                        } else if (i == R.id.negative_button) {
                            dismiss();
                        }
                    }
                };
                connectDialog.setTitle("Connection Request");
                connectDialog.setDescription("Do you want to send to ");
                connectDialog.setUsername((actorsToConnect.size() > 1) ? "" + actorsToConnect.size() +
                        " Redeem Points" : actorsToConnect.get(0).getName());
                connectDialog.setSecondDescription("a connection request");
                connectDialog.show();
                return true;
            } else {
                Toast.makeText(getActivity(), "No Redeem Point selected to connect.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (id == SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_DISCONNECT) {
            List<ActorAssetRedeemPoint> actorsSelected = new ArrayList<>();
            for (Actor actor : actors) {
                if (actor.selected)
                    actorsSelected.add(actor);
            }
            if (actorsSelected.size() > 0) {

                DisconnectDialog disconnectDialog;

                disconnectDialog = new DisconnectDialog(getActivity(), (AssetRedeemPointCommunitySubAppSession) appSession, null) {
                    @Override
                    public void onClick(View v) {
                        int i = v.getId();
                        if (i == R.id.positive_button) {

                            final ProgressDialog dialog = new ProgressDialog(getActivity());
                            dialog.setMessage("Disconnecting please wait...");
                            dialog.setCancelable(false);
                            dialog.show();
                            FermatWorker worker = new FermatWorker() {
                                @Override
                                protected Object doInBackground() throws Exception {
                                    List<ActorAssetRedeemPoint> toDisconnect = new ArrayList<>();
                                    for (Actor actor : actors) {
                                        if (actor.selected)
                                            toDisconnect.add(actor);
                                    }
                                    /*TODO implementar disconnect*/
                                    for (ActorAssetRedeemPoint actor : toDisconnect) {
                                        moduleManager.disconnectToActorAssetRedeemPoint(actor);
                                    }

                                    /*Intent broadcast = new Intent(SessionConstantsAssetUserCommunity.LOCAL_BROADCAST_CHANNEL);
                                    broadcast.putExtra(SessionConstantsAssetUserCommunity.BROADCAST_CONNECTED_UPDATE, true);
                                    sendLocalBroadcast(broadcast);*/

//                                    moduleManager.connectToActorAssetUser(null, toConnect);
                                    return true;
                                }
                            };
                            worker.setContext(getActivity());
                            worker.setCallBack(new FermatWorkerCallBack() {
                                @Override
                                public void onPostExecute(Object... result) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Disconnection performed successfully", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getActivity(), "Asset Redeem Point must be created before using this app.", Toast.LENGTH_LONG).show();
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
                disconnectDialog.setTitle("Disconnection request");
                disconnectDialog.setDescription("Do you want to disconnect from ");
                disconnectDialog.setUsername((actorsSelected.size() > 1) ? "" + actorsSelected.size() +
                        " Redeem Points" : actorsSelected.get(0).getName());
                //connectDialog.setSecondDescription("a connection request");
                disconnectDialog.show();
                return true;
            } else {
                Toast.makeText(getActivity(), "No Redeem point selected to disconnect.", Toast.LENGTH_LONG).show();
                return false;
            }


        }

        if (id == SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_CANCEL_CONNECTING) {
            CancelDialog cancelDialog;

            cancelDialog = new CancelDialog(getActivity(), (AssetRedeemPointCommunitySubAppSession) appSession, null) {
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


                                for (Actor actor : actorsConnecting) {
                                    //TODO revisar si esto es asi
                                    moduleManager.cancelActorAssetRedeem(actor.getActorPublicKey());
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
                                Toast.makeText(getActivity(), "Can't cancel connection to selected Redeem Points", Toast.LENGTH_LONG).show();
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
                    " Redeem points" : actorsConnecting.get(0).getName());
            //connectDialog.setSecondDescription("a connection request");
            cancelDialog.show();
            return true;
        }

        try {
            if (id == SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_HELP_PRESENTATION) {
                setUpPresentation(moduleManager.loadAndGetSettings(assetRedeemPointCommunitySubAppSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Redeem Point system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNotificationsBadge(int count) {
        redeemNotificationsCount = count;
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        } else {
            Log.e(TAG, "updateNotificationsBadge activity null, please check this, class" + getClass().getName() + " line: " + new Throwable().getStackTrace()[0].getLineNumber());
        }
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

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetIdentityRedeemPointException {

    }

    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            return redeemNotificationsCount;
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
                            actors = (ArrayList<Actor>) result[0];
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

    private synchronized List<Actor> getMoreData() throws Exception {
        List<Actor> dataSet = new ArrayList<>();
        List<RedeemPointActorRecord> result = null;
        if (moduleManager == null)
            throw new NullPointerException("AssetRedeemPointCommunitySubAppModuleManager is null");

        try {
            result = moduleManager.getAllActorAssetRedeemPointRegistered();
            if (result != null && result.size() > 0) {
                for (RedeemPointActorRecord record : result) {
                    dataSet.add((new Actor(record)));
                }
            }
        } catch (CantGetAssetRedeemPointActorsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    @Override
    public void onItemClickListener(Actor data, int position) {
        appSession.setData(REDEEM_POINT_SELECTED, data);
        changeActivity(Activities.DAP_ASSET_REDEEM_POINT_COMMUNITY_ACTIVITY_PROFILE.getCode(), assetRedeemPointCommunitySubAppSession.getAppPublicKey());
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
    public void onLongItemClickListener(Actor data, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void restartButtons() {
        menuItemCancel.setVisible(false);
        menuItemSelect.setVisible(true);
        menuItemUnselect.setVisible(false);
        menuItemConnect.setVisible(false);
        menuItemDisconnect.setVisible(false);
    }
}
