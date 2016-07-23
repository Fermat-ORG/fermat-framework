package com.bitdubai.sub_app.intra_user_community.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.AppNotificationAdapter;
import com.bitdubai.sub_app.intra_user_community.common.popups.AcceptDialog;

import com.bitdubai.sub_app.intra_user_community.common.popups.DeleteAllContactsDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.GeolocationDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.PresentationIntraUserCommunityDialog;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;
import com.bitdubai.sub_app.intra_user_community.session.SessionConstants;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Creado por Jose manuel De Sousa el 30/11/2015
 * updated Andres Abreu aabreu1 - 20/07/2016
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ConnectionNotificationsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<IntraUserModuleManager> ,ResourceProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<IntraUserInformation> {

    public static final String INTRA_USER_SELECTED = "intra_user";
    private static final int MAX = 20;
    protected final String TAG = "ConnectionNotificationsFragment";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private AppNotificationAdapter adapter;
    private ReferenceAppFermatSession<IntraUserModuleManager> intraUserSubAppSession;
    private LinearLayout emptyView;
    private LinearLayout connectionSuccess;
    private FermatTextView textConnectionSuccess;
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;
    private int offset = 0;
    private IntraUserInformation intraUserInformation;
    private List<IntraUserInformation> lstIntraUserInformations;
    private IntraUserLoginIdentity identity;
    private ProgressDialog dialog;
    private FermatWorker worker;
    private FermatApplicationCaller fermatApplicationCaller;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionNotificationsFragment newInstance() {
        return new ConnectionNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // setting up  module
        intraUserSubAppSession = ((ReferenceAppFermatSession) appSession);

        intraUserInformation = (IntraUserInformation) ((ReferenceAppFermatSession) appSession).getData(INTRA_USER_SELECTED);

        fermatApplicationCaller = ((FermatApplicationSession)getActivity().getApplicationContext()).getApplicationManager();
        moduleManager = intraUserSubAppSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        lstIntraUserInformations = new ArrayList<>();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_connections_notifications, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new AppNotificationAdapter(getActivity(), lstIntraUserInformations);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

           // rootView.setBackgroundColor(Color.parseColor("#ffff"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            connectionSuccess = (LinearLayout) rootView.findViewById(R.id.connection_success);
            textConnectionSuccess = (FermatTextView) rootView.findViewById(R.id.text_connection_success);





        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }

        onRefresh();

        return rootView;
    }

    private synchronized ArrayList<IntraUserInformation> getMoreData() {
        ArrayList<IntraUserInformation> dataSet = new ArrayList<>();
        try {

            List list = moduleManager.getIntraUsersWaitingYourAcceptance(moduleManager.getActiveIntraUserIdentity().getPublicKey(), MAX, offset);
            if(list!=null) dataSet.addAll(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException {

    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
           /* final ProgressDialog notificationsProgressDialog = new ProgressDialog(getActivity());
            notificationsProgressDialog.setMessage("Loading Notifications");
            notificationsProgressDialog.setCancelable(false);
            notificationsProgressDialog.show();*/
             worker = new FermatWorker() {
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
                    //notificationsProgressDialog.dismiss();
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            lstIntraUserInformations = (ArrayList<IntraUserInformation>) result[0];
                            adapter.changeDataSet(lstIntraUserInformations);
                            if (lstIntraUserInformations.isEmpty()) {
                                showEmpty(true, emptyView);
                            } else {
                                showEmpty(false, emptyView);
                            }
                        }
                    } else
                        showEmpty(adapter.getSize() < 0, emptyView);
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                   // notificationsProgressDialog.dismiss();
                    try {
                        isRefreshing = false;
                        if (swipeRefresh != null)
                            swipeRefresh.setRefreshing(false);
                        if (getActivity() != null)
                            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            worker.execute();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                try{
                    fermatApplicationCaller.openFermatApp(SubAppsPublicKeys.CCP_IDENTITY.getCode());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case 4:
                showDialogHelp();
                break;

        }

        return false;
    }


    @Override
    public void onItemClickListener(IntraUserInformation data, int position) {
        try {
            AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(), intraUserSubAppSession,null, data, moduleManager.getActiveIntraUserIdentity());
            notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = appSession.getData(SessionConstants.NOTIFICATION_ACCEPTED);
                    try {
                        if ((Boolean) o) {
                            onRefresh();
                            appSession.removeData(SessionConstants.NOTIFICATION_ACCEPTED);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        onRefresh();
                    }
                }
            });
            notificationAcceptDialog.show();

                textConnectionSuccess.setText("You're now connected with "+ data.getName());
                showEmpty(notificationAcceptDialog.getResultado(), connectionSuccess);
        } catch (CantGetActiveLoginIdentityException e) {e.printStackTrace();
        }
    }

    @Override
    public void onLongItemClickListener(IntraUserInformation data, int position) {

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();

        onRefresh();
    }

    @Override
    public void onStop() {
        if (worker != null)
            worker.shutdownNow();
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
}
