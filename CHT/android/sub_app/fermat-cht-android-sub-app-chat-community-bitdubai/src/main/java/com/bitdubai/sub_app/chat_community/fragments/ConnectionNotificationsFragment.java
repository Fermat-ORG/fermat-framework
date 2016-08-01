package com.bitdubai.sub_app.chat_community.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.NotificationAdapter;
import com.bitdubai.sub_app.chat_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.chat_community.common.popups.PresentationChatCommunityDialog;
import com.bitdubai.sub_app.chat_community.constants.Constants;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * ConnectionNotificationsFragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})

public class ConnectionNotificationsFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener,
        FermatListItemListeners<ChatActorCommunityInformation>, AcceptDialog.OnDismissListener {

    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatActorCommunitySettings> settingsManager;
    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> chatUserSubAppSession;
    public static final String CHAT_USER_SELECTED = "chat_user";
    private static final int MAX = 100;
    private ChatActorCommunitySelectableIdentity identity;
    FermatApplicationCaller applicationsHelper;
    protected final String TAG = "ConnectionNotificationsFragment";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private ProgressBar progressBar;
    private NotificationAdapter adapter;
    private LinearLayout emptyView;
    private int offset = 0;
    private ChatActorCommunityInformation chatUserInformation;
    private List<ChatActorCommunityInformation> lstChatUserInformations;
    private ChatActorCommunitySettings appSettings;
    ImageView noData;
    TextView noDatalabel;
    private ExecutorService executor;
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

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
        try {
            setHasOptionsMenu(true);

            // setting up  module
            chatUserInformation = (ChatActorCommunityInformation) appSession.getData(CHAT_USER_SELECTED);
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            moduleManager.setAppPublicKey(appSession.getAppPublicKey());
            applicationsHelper = ((FermatApplicationSession) getActivity().getApplicationContext()).getApplicationManager();
            lstChatUserInformations = new ArrayList<>();

            //Obtain Settings or create new Settings if first time opening subApp
            appSettings = null;
            try {
                appSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                appSettings = null;
            }

            if (appSettings == null) {
                appSettings = new ChatActorCommunitySettings();
                appSettings.setIsPresentationHelpEnabled(true);
                try {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Check if a default identity is configured
            try {
                identity = moduleManager.getSelectedActorIdentity();
                if (identity == null)
                    launchListIdentitiesDialog = true;
            } catch (CantGetSelectedActorIdentityException e) {
                //There are no identities in device
                launchActorCreationDialog = true;
            } catch (ActorIdentityNotSelectedException e) {
                //There are identities in device, but none selected
                launchListIdentitiesDialog = true;
            }
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.cht_comm_notifications_fragment, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new NotificationAdapter(getActivity(), lstChatUserInformations);
            adapter.setFermatListEventListener(this);

            recyclerView.setAdapter(adapter);
            noData = (ImageView) rootView.findViewById(R.id.nodata);
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
//            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
//            swipeRefresh.setOnRefreshListener(this);
//            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
            rootView.setBackgroundColor(Color.parseColor("#F9F9F9"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            showEmpty(true, emptyView);
            progressBar.setVisibility(View.VISIBLE);
            onRefresh();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    @Override
    public void onFragmentFocus() {
        offset = 0;
        onRefresh();
    }

    private ArrayList<ChatActorCommunityInformation> getMoreData() {
        ArrayList<ChatActorCommunityInformation> dataSet = new ArrayList<>();
        try {
            List<ChatActorCommunityInformation> result;
            if (identity != null) {
                result = moduleManager.listChatActorPendingLocalAction(identity.getPublicKey(),
                        identity.getActorType(), MAX, offset);
                dataSet.addAll(result);
                //offset = dataSet.size();
            }
        } catch (CantListChatActorException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActiveLoginIdentityException, CantGetChatUserIdentityException {
    }

    @Override
    public void onRefresh() {
        try {
            if (!isRefreshing) {
                isRefreshing = true;
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
//                        if (swipeRefresh != null)
//                            swipeRefresh.setRefreshing(false);
                        if (result != null &&
                                result.length > 0) {
                            if (getActivity() != null && adapter != null) {
                                lstChatUserInformations = (ArrayList<ChatActorCommunityInformation>) result[0];
                                adapter.changeDataSet(lstChatUserInformations);
                                if (lstChatUserInformations.isEmpty()) {
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
                        try {
                            isRefreshing = false;
//                            if (swipeRefresh != null)
//                                swipeRefresh.setRefreshing(false);
                            if (getActivity() != null)
                                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                            ex.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                executor = worker.execute();
            }
        } catch (Exception ignore) {
            if (executor != null) {
                executor.shutdown();
                executor = null;
            }
        }
    }

    @Override
    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
        try {
            AcceptDialog notificationAcceptDialog = new AcceptDialog(getActivity(), appSession, null, data, identity);
            final ChatActorCommunityInformation dataNow = data;
            notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ArrayList<ChatActorCommunityInformation> dataNew = new ArrayList<>();
                    try {
                        for (ChatActorCommunityInformation dat : lstChatUserInformations) {
                            if (!dataNow.getPublicKey().equals(dat.getPublicKey())) {
                                dataNew.add(dat);
                            }
                        }
                        lstChatUserInformations = dataNew;
                        adapter.changeDataSet(lstChatUserInformations);
                        if (lstChatUserInformations.isEmpty()) {
                            showEmpty(true, emptyView);
                        } else {
                            showEmpty(false, emptyView);
                        }
                    } catch (Exception e) {
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                                UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                    }
                }
            });
            notificationAcceptDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Connection Accepted but.. ERROR! ->", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {
    }

    /**
     * @param show
     */
    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDatalabel.setVisibility(View.VISIBLE);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else {
            emptyView.setVisibility(View.GONE);
            noData.setAnimation(anim);
            emptyView.setBackgroundResource(0);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.GONE);
            noDatalabel.setVisibility(View.GONE);
            rootView.setBackgroundResource(0);
            ColorDrawable bgcolor = new ColorDrawable(Color.parseColor("#F9F9F9"));
            emptyView.setBackground(bgcolor);
            rootView.setBackground(bgcolor);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        onRefresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 1:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    showDialogHelp();
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogHelp() {
        try {
            if (identity != null) {
                if (!identity.getPublicKey().isEmpty()) {
                    PresentationChatCommunityDialog presentationChatCommunityDialog =
                            new PresentationChatCommunityDialog(getActivity(),
                                    appSession,
                                    null,
                                    moduleManager,
                                    PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES/*,
                                    applicationsHelper.get(), showIdentity*/);
                    presentationChatCommunityDialog.show();
                    presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            //showCriptoUsersCache();
                        }
                    });
                } else {
                    PresentationChatCommunityDialog presentationChatCommunityDialog =
                            new PresentationChatCommunityDialog(getActivity(),
                                    appSession,
                                    null,
                                    moduleManager,
                                    PresentationChatCommunityDialog.TYPE_PRESENTATION/*,
                                    applicationsHelper.get(), showIdentity*/);
                    presentationChatCommunityDialog.show();
                    presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Boolean isBackPressed =
                                    (Boolean) appSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
                            if (isBackPressed != null) {
                                if (isBackPressed) {
                                    getActivity().finish();
                                }
                            } else {
                                invalidate();
                            }
                        }
                    });
                }
            } else {
                PresentationChatCommunityDialog presentationChatCommunityDialog =
                        new PresentationChatCommunityDialog(getActivity(),
                                appSession,
                                null,
                                moduleManager,
                                PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES/*,
                                applicationsHelper.get(), showIdentity*/);
                presentationChatCommunityDialog.show();
                presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Boolean isBackPressed = (Boolean) appSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
                        if (isBackPressed != null) {
                            if (isBackPressed) {
                                getActivity().onBackPressed();
                            }
                        }
                    }
                });
            }
//        } catch (CantGetSelectedActorIdentityException e) {
        } catch (Exception e) {
//            PresentationChatCommunityDialog presentationChatCommunityDialog =
//                    new PresentationChatCommunityDialog(getActivity(),
//                            appSession,
//                            null,
//                            moduleManager,
//                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES/*,
//                            applicationsHelper.get(), showIdentity*/);
//            presentationChatCommunityDialog.show();
//            presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//                    //showCriptoUsersCache();
//                }
//            });
//            e.printStackTrace();
//        } catch (ActorIdentityNotSelectedException e) {
            PresentationChatCommunityDialog presentationChatCommunityDialog =
                    new PresentationChatCommunityDialog(getActivity(),
                            appSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES/*,
                            applicationsHelper.get(), showIdentity*/);
            presentationChatCommunityDialog.show();
            presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //showCriptoUsersCache();
                }
            });
            e.printStackTrace();
        }
    }
}
