package com.bitdubai.sub_app.chat_community.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.CommunityListAdapter;
import com.bitdubai.sub_app.chat_community.common.popups.AcceptDialog;
import com.bitdubai.sub_app.chat_community.common.popups.ConnectDialog;
import com.bitdubai.sub_app.chat_community.common.popups.DisconnectDialog;
import com.bitdubai.sub_app.chat_community.common.popups.PresentationChatCommunityDialog;
import com.bitdubai.sub_app.chat_community.constants.Constants;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * ConnectionsWorldFragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

public class ConnectionsWorldFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener,
        FermatListItemListeners<ChatActorCommunityInformation> {

    //Constants
    public static final String CHAT_USER_SELECTED = "chat_user";
    private static final int MAX = 20;
    protected final String TAG = "Recycler Base";

    //Managers
    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    //Data
    private ChatActorCommunitySettings appSettings;
    private int offset = 0;
    private ArrayList<ChatActorCommunityInformation> lstChatUserInformations;

    //Flags
    private boolean isRefreshing = false;
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    //UI
    private View rootView;
    //View layout;
    private LinearLayout emptyView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private CommunityListAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    TextView noDatalabel;
    ImageView noData;

    public static ConnectionsWorldFragment newInstance() {
        return new ConnectionsWorldFragment();
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
                moduleManager.getSelectedActorIdentity();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        moduleManager.setAppPublicKey(appSession.getAppPublicKey());

        try {
            rootView = inflater.inflate(R.layout.cht_comm_connections_world_fragment, container, false);
            //Set up RecyclerView
            layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
            adapter = new CommunityListAdapter(getActivity(), lstChatUserInformations,
                    appSession, moduleManager);
            adapter.setFermatListEventListener(this);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            rootView.setBackgroundColor(Color.parseColor("#f9f9f9"));
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            noData = (ImageView) rootView.findViewById(R.id.nodata);
            //Set up swipeRefresher
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#F9F9F9"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);

            showEmpty(true, emptyView);

            if (launchActorCreationDialog) {
                PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                        .setBannerRes(R.drawable.chat_banner_community)
                        .setIconRes(R.drawable.chat_subapp)
                        .setSubTitle(R.string.cht_creation_dialog_sub_title)
                        .setBody(R.string.cht_creation_dialog_body)
                        .setTextFooter(R.string.cht_creation_dialog_footer)
                        .setTextNameLeft(R.string.cht_creation_name_left)
                        .setTextNameRight(R.string.cht_creation_name_right)
                        .setImageRight(R.drawable.ic_profile_male)
                        .build();
                presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        invalidate();
                        onRefresh();
                    }
                });
                presentationDialog.show();
            } else if (launchListIdentitiesDialog) {
                PresentationChatCommunityDialog presentationChatCommunityDialog =
                        new PresentationChatCommunityDialog(getActivity(),
                                appSession,
                                null,
                                moduleManager,
                                PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                        );
                presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        invalidate();
                        onRefresh();
                    }
                });
                presentationChatCommunityDialog.show();
            } else {
                onRefresh();
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
        }
        return rootView;
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        progressDialog.dismiss();
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
                        showEmpty(true, emptyView);
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    progressDialog.dismiss();
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (getActivity() != null)
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
                }
            });
            worker.execute();
        }
    }

    public void showEmpty(boolean show, View emptyView) {

        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            //emptyView.setBackgroundResource(R.drawable.cht_comm_background);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDatalabel.setVisibility(View.VISIBLE);
            //rootView.setBackgroundResource(R.drawable.cht_comm_background);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else {
            emptyView.setAnimation(anim);
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
    }

    private synchronized List<ChatActorCommunityInformation> getMoreData() {
        System.out.println("****************** GETMORE DATA SYNCRHINIEZED ENTERING");
        List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
        try {
            List<ChatActorCommunityInformation> result = moduleManager.listWorldChatActor(moduleManager.getSelectedActorIdentity(), MAX, offset);

            System.out.println("****************** GETMORE DATA SYNCRHINIEZED RESULT SIZE: " + result.size());
            for (ChatActorCommunityInformation chat : result) {
                if (chat.getConnectionState() != null) {
                    if (chat.getConnectionState().getCode().equals(ConnectionState.CONNECTED.getCode())) {
                        moduleManager.requestConnectionToChatActor(moduleManager.getSelectedActorIdentity(), chat);
                        dataSet.add(chat);
                    } else dataSet.add(chat);
                } else dataSet.add(chat);
            }
            offset = dataSet.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("****************** GETMORE DATA SYNCRHINIEZED SALGO BIEN: ");
        return dataSet;
    }

    @Override
    public void onItemClickListener(ChatActorCommunityInformation data, int position) {

    }

    @Override
    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 3:
                    showDialogHelp();
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogHelp() {

        try {
            moduleManager = appSession.getModuleManager();
            if (moduleManager.getSelectedActorIdentity() != null) {
                if (!moduleManager.getSelectedActorIdentity().getPublicKey().isEmpty()) {
                    PresentationChatCommunityDialog presentationChatCommunityDialog =
                            new PresentationChatCommunityDialog(getActivity(),
                                    appSession,
                                    null,
                                    moduleManager,
                                    PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                            );
                    presentationChatCommunityDialog.show();

                } else {
                    PresentationChatCommunityDialog presentationChatCommunityDialog =
                            new PresentationChatCommunityDialog(getActivity(),
                                    appSession,
                                    null,
                                    moduleManager,
                                    PresentationChatCommunityDialog.TYPE_PRESENTATION
                            );
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
                                PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                        );
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
        } catch (CantGetSelectedActorIdentityException e) {
            PresentationChatCommunityDialog presentationChatCommunityDialog =
                    new PresentationChatCommunityDialog(
                            getActivity(),
                            appSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                    );
            presentationChatCommunityDialog.show();
            presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
            });
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            PresentationChatCommunityDialog presentationChatCommunityDialog =
                    new PresentationChatCommunityDialog(getActivity(),
                            appSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES
                    );
            presentationChatCommunityDialog.show();

            e.printStackTrace();
        }
    }

    private void actionsDialog(int action, ChatActorCommunityInformation data) {
        ConnectDialog connectDialog;
        final ChatActorCommunityInformation dat = data;
        switch (action) {
            case 1://connect
                CommonLogger.info(TAG, "User connection state " +
                        data.getConnectionState());
                try {
                    connectDialog =
                            new ConnectDialog(getActivity(), appSession, null,
                                    data, moduleManager.getSelectedActorIdentity());
                    connectDialog.setTitle("Connection Request");
                    connectDialog.setDescription("Are you sure you want to send a connection request to this contact?");
                    connectDialog.setUsername(data.getAlias());
//                    connectDialog.setSecondDescription("a connection request?");
                    connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            //updateButton(dat);
                        }
                    });
                    connectDialog.show();
                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
                break;
            case 2://Disconnect
                CommonLogger.info(TAG, "User connection state " +
                        data.getConnectionState());
                final DisconnectDialog disconnectDialog;
                try {
                    disconnectDialog =
                            new DisconnectDialog(getActivity(), appSession, null,
                                    data, moduleManager.getSelectedActorIdentity());
                    disconnectDialog.setTitle("Disconnect");
                    disconnectDialog.setDescription("Do you want to disconnect from");
                    disconnectDialog.setUsername(data.getAlias() + "?");
                    disconnectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            //updateButton(dat);
                        }
                    });
                    disconnectDialog.show();
                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
                break;
            case 3://Accept Connection
                try {
                    AcceptDialog notificationAcceptDialog =
                            new AcceptDialog(getActivity(), appSession, null,
                                    data, moduleManager.getSelectedActorIdentity());
                    notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            //updateButton(dat);
                        }
                    });
                    notificationAcceptDialog.show();

                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
                break;
            case 4://Resend Connection
                CommonLogger.info(TAG, "User connection state "
                        + data.getConnectionState());
                Toast.makeText(getActivity(), "The connection request has been sent\n you need to wait until the user responds", Toast.LENGTH_SHORT).show();
                try {
                    connectDialog =
                            new ConnectDialog(getActivity(), appSession, null,
                                    data, moduleManager.getSelectedActorIdentity());
                    connectDialog.setTitle("Resend Connection Request");
                    connectDialog.setDescription("Do you want to resend ");
                    connectDialog.setUsername(data.getAlias());
                    connectDialog.setSecondDescription("a connection request?");
                    connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            //updateButton(dat);
                        }
                    });
                    connectDialog.show();
                } catch (CantGetSelectedActorIdentityException
                        | ActorIdentityNotSelectedException e) {
                    e.printStackTrace();
                }
                break;
            case 5://Reject Connection
                CommonLogger.info(TAG, "User connection state "
                        + data.getConnectionState());
                Toast.makeText(getActivity(), "The connection request has been rejected", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}