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
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.chat_community.adapters.CommunityListAdapter;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.common.popups.PresentationChatCommunityDialog;
import com.bitdubai.sub_app.chat_community.constants.Constants;
import com.bitdubai.sub_app.chat_community.session.ChatUserSubAppSession;
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
        extends AbstractFermatFragment<ChatUserSubAppSession, SubAppResourcesProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener,
        FermatListItemListeners<ChatActorCommunityInformation> {

    //Constants
    public static final String CHAT_USER_SELECTED = "chat_user";
    private static final int MAX = 20;
    protected final String TAG = "Recycler Base";

    //Managers
    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatActorCommunitySettings> settingsManager;
    private ChatUserSubAppSession chatUserSubAppSession;

    //Data
    private ChatActorCommunitySettings appSettings;
    private int offset = 0;
    private int mNotificationsCount = 0;
    private ArrayList<ChatActorCommunityInformation> lstChatUserInformations;//cryptoBrokerCommunityInformationList;

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
    private EditText searchEditText;
    private ImageView closeSearch;
    private CommunityListAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private SearchView searchView;//private View searchView;
    private android.support.v7.widget.Toolbar toolbar;
    private List<ChatActorCommunityInformation> dataSetFiltered;
    private LinearLayout searchEmptyView;
    TextView noDatalabel;
    FermatWorker worker;
    ImageView noData;
    private List<ChatActorCommunityInformation> dataSet = new ArrayList<>();

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
            chatUserSubAppSession = ((ChatUserSubAppSession) appSession);
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            //@Deprecated
            //settingsManager = moduleManager.getSettingsManager();
            moduleManager.setAppPublicKey(appSession.getAppPublicKey());


            //Obtain Settings or create new Settings if first time opening subApp
            appSettings = null;
            try {
                appSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            }catch (Exception e){ appSettings = null; }

            if(appSettings == null){
                appSettings = new ChatActorCommunitySettings();
                appSettings.setIsPresentationHelpEnabled(true);
                try {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //Check if a default identity is configured
            try{
                moduleManager.getSelectedActorIdentity();
            }catch (CantGetSelectedActorIdentityException e){
                //There are no identities in device
                launchActorCreationDialog = true;
            }catch (ActorIdentityNotSelectedException e){
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
            layoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
            adapter = new CommunityListAdapter(getActivity(), lstChatUserInformations);
            adapter.setFermatListEventListener(this);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            rootView.setBackgroundColor(Color.parseColor("#f9f9f9"));
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            noData=(ImageView) rootView.findViewById(R.id.nodata);
            //Set up swipeRefresher
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#F9F9F9"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            //searchView = inflater.inflate(R.layout.cht_comm_search_edit_text, null);
            //searchEditText = (EditText) searchView.findViewById(R.id.search);
            ///closeSearch = (ImageView) searchView.findViewById(R.id.close_search);
            searchEmptyView = (LinearLayout) rootView.findViewById(R.id.search_empty_view);
            showEmpty(true, emptyView);

            if(launchActorCreationDialog) {
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
            }
            else if(launchListIdentitiesDialog)
            {
                PresentationChatCommunityDialog presentationChatCommunityDialog =
                            new PresentationChatCommunityDialog(getActivity(),
                            chatUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
                presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        invalidate();
                        onRefresh();
                    }
                });
                presentationChatCommunityDialog.show();
            }
            else
            {
                invalidate();
                onRefresh();
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            //Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
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
                    //Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            worker.execute();
        }
    }


    public void showEmpty(boolean show, View emptyView) {
        Animation anim = AnimationUtils.loadAnimation(getActivity(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show /*&& (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)*/) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            emptyView.setBackgroundResource(R.drawable.cht_comm_background);
            noDatalabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDatalabel.setVisibility(View.VISIBLE);
            rootView.setBackgroundResource(R.drawable.cht_comm_background);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else if (!show /*&& emptyView.getVisibility() == View.VISIBLE*/) {
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
        List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
        try {
            List<ChatActorCommunityInformation> result = moduleManager.listWorldChatActor(moduleManager.getSelectedActorIdentity(), MAX, offset);
            dataSet.addAll(result);
            offset = dataSet.size();
        } catch (Exception e) {
            //Toast.makeText(getActivity(), "No Chat Identity Created",
            //        Toast.LENGTH_LONG).show();
            //e.printStackTrace();
        }
        return dataSet;
    }

    @Override
    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
        try {
            if (moduleManager.getSelectedActorIdentity() != null) {
                appSession.setData(CHAT_USER_SELECTED, data);
                changeActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
            } else {
                showDialogHelp();
            }
        } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException e)
        {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {}

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cht_comm_menu, menu);
        // Locate the search item
//        MenuItem searchItem = menu.findItem(R.id.menu_search);
//        searchView = (SearchView) searchItem.getActionView();
//        searchView.setQueryHint(getResources().getString(R.string.description_search));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                if (s.equals(searchView.getQuery().toString())) {
//                    adapter.getFilter().filter(s);
//                }
//                return false;
//            }
//        });

//        try {
//            final MenuItem searchItem = menu.findItem(R.id.action_search);
//            menu.findItem(R.id.action_help).setVisible(true);
//            menu.findItem(R.id.action_search).setVisible(true);
//            searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    menu.findItem(R.id.action_help).setVisible(false);
//                    menu.findItem(R.id.action_search).setVisible(false);
//                    toolbar = getToolbar();
//                    toolbar.setTitle("");
//                    toolbar.addView(searchView);
//                    if (closeSearch != null)
//                        closeSearch.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                menu.findItem(R.id.action_help).setVisible(true);
//                                menu.findItem(R.id.action_search).setVisible(true);
//                                toolbar = getToolbar();
//                                toolbar.removeView(searchView);
//                                toolbar.setTitle("Chat Users");
//                                onRefresh();
//                            }
//                        });
//
//                    if (searchEditText != null) {
//                        searchEditText.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                            }
//
//                            @Override
//                            public void onTextChanged(final CharSequence s, int start, int before, int count) {
//                                if (s.length() > 0) {
//                                    worker = new FermatWorker() {
//                                        @Override
//                                        protected Object doInBackground() throws Exception {
//                                            return getQueryData(s);
//                                        }
//                                    };
//                                    worker.setContext(getActivity());
//                                    worker.setCallBack(new FermatWorkerCallBack() {
//                                        @SuppressWarnings("unchecked")
//                                        @Override
//                                        public void onPostExecute(Object... result) {
//                                            isRefreshing = false;
//                                            if (swipeRefresh != null)
//                                                swipeRefresh.setRefreshing(false);
//                                            if (result != null &&
//                                                    result.length > 0) {
//                                                if (getActivity() != null && adapter != null) {
//                                                    dataSetFiltered = (ArrayList<ChatActorCommunityInformation>) result[0];
//                                                    adapter.changeDataSet(dataSetFiltered);
//                                                    if (dataSetFiltered != null) {
//                                                        if (dataSetFiltered.isEmpty()) {
//                                                            showEmpty(true, searchEmptyView);
//                                                            showEmpty(false, emptyView);
//
//                                                        } else {
//                                                            showEmpty(false, searchEmptyView);
//                                                            showEmpty(false, emptyView);
//                                                        }
//                                                    } else {
//                                                        showEmpty(true, searchEmptyView);
//                                                        showEmpty(false, emptyView);
//                                                    }
//                                                }
//                                            } else {
//                                                showEmpty(true, searchEmptyView);
//                                                showEmpty(false, emptyView);
//                                            }
//                                        }
//                                        @Override
//                                        public void onErrorOccurred(Exception ex) {
//                                            isRefreshing = false;
//                                            if (swipeRefresh != null)
//                                                swipeRefresh.setRefreshing(false);
//                                            showEmpty(true, searchEmptyView);
//                                            if (getActivity() != null)
//                                                Toast.makeText(getActivity(), ex.getMessage(),
//                                                        Toast.LENGTH_LONG).show();
//                                            ex.printStackTrace();
//
//                                        }
//                                    });
//                                    worker.execute();
//                                } else {
//                                    menu.findItem(R.id.action_help).setVisible(true);
//                                    menu.findItem(R.id.action_search).setVisible(true);
//                                    toolbar = getToolbar();
//                                    toolbar.removeView(searchView);
//                                    //toolbar.setTitle("Cripto wallet users");
//                                    onRefresh();
//                                }
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable s) {
//                            }
//                        });
//                    }
//                    return false;
//                }
//            });
//        } catch (Exception e) { }
    }

    private synchronized List<ChatActorCommunityInformation> getQueryData(final CharSequence charSequence) {
        if (dataSet != null && !dataSet.isEmpty()) {
            if (searchEditText != null && !searchEditText.getText().toString().isEmpty()) {
                dataSetFiltered = new ArrayList<ChatActorCommunityInformation>();
                for (ChatActorCommunityInformation chatUser : dataSet) {
                    if(chatUser.getAlias().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        dataSetFiltered.add(chatUser);
                }
            }
            else
                dataSetFiltered = null;
        }
        return dataSetFiltered;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.action_help)
                showDialogHelp();

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
                            chatUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
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
                            chatUserSubAppSession,
                            null,
                            moduleManager,
                                    PresentationChatCommunityDialog.TYPE_PRESENTATION);
                    presentationChatCommunityDialog.show();
                    presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Boolean isBackPressed =
                                    (Boolean) chatUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
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
                        chatUserSubAppSession,
                        null,
                        moduleManager,
                                PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
                presentationChatCommunityDialog.show();
                presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Boolean isBackPressed = (Boolean) chatUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
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
                    new PresentationChatCommunityDialog(getActivity(),
                            chatUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
            presentationChatCommunityDialog.show();
            presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //showCriptoUsersCache();
                }
            });
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            PresentationChatCommunityDialog presentationChatCommunityDialog =
                    new PresentationChatCommunityDialog(getActivity(),
                            chatUserSubAppSession,
                            null,
                            moduleManager,
                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
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

//
//public class ConnectionsWorldFragment extends AbstractFermatFragment implements
//        AdapterView.OnItemClickListener,
//        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<ChatActorCommunityInformation> {
//
//
//    public static final String CHAT_USER_SELECTED = "chat_user";
//
//    private static final int MAX = 20;
//    /**
//     * MANAGERS
//     */
//    private static ChatActorCommunitySubAppModuleManager moduleManager;
//    private static ErrorManager errorManager;
//
//    protected final String TAG = "Recycler Base";
//    FermatWorker worker;
//    SettingsManager<ChatActorCommunitySettings> settingsManager;
//    ChatActorCommunitySettings chatUserSettings = null;
//    private ErrorConnectingFermatNetwork errorConnectingFermatNetwork;
//    private int offset = 0;
//    private int mNotificationsCount = 0;
//    private SearchView mSearchView;
//    private CommunityListAdapter adapter;
//    private boolean isStartList = false;
//    private RecyclerView recyclerView;
//    private GridLayoutManager layoutManager;
//    private SwipeRefreshLayout swipeRefresh;
//    private View searchView;
//    // flags
//    private boolean isRefreshing = false;
//    private View rootView;
//    private ChatUserSubAppSession chatUserSubAppSession;
//    private String searchName;
//    private LinearLayout emptyView;
//    private ArrayList<ChatActorCommunityInformation> lstChatUserInformations;
//    private List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
//    private android.support.v7.widget.Toolbar toolbar;
//    private EditText searchEditText;
//    private List<ChatActorCommunityInformation> dataSetFiltered;
//    private ImageView closeSearch;
//    private LinearLayout searchEmptyView;
//    private LinearLayout noNetworkView;
//    private LinearLayout noFermatNetworkView;
//    private Handler handler = new Handler();
//
//    /**
//     * Create a new instance of this fragment
//     *
//     * @return InstalledFragment instance object
//     */
//    public static ConnectionsWorldFragment newInstance() {
//        return new ConnectionsWorldFragment();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//
//            setHasOptionsMenu(true);
//            // setting up  module
//            chatUserSubAppSession = ((ChatUserSubAppSession) appSession);
//            moduleManager = chatUserSubAppSession.getModuleManager();
//            errorManager = appSession.getErrorManager();
//            settingsManager = chatUserSubAppSession.getModuleManager().getSettingsManager();
//            //TODO: SETTINGS OF ACTOR LAYER
//            try {
//                chatUserSettings =
//                        settingsManager.loadAndGetSettings(chatUserSubAppSession.getAppPublicKey());
//            } catch (Exception e) {
//                chatUserSettings = null;
//            }
//
//            if (chatUserSubAppSession.getAppPublicKey() != null) //the identity not exist yet
//            {
//                if (chatUserSettings == null) {
//                    chatUserSettings = new ChatActorCommunitySettings();
//                    chatUserSettings.setIsPresentationHelpEnabled(true);
//                    settingsManager.persistSettings(chatUserSubAppSession.getAppPublicKey(),
//                            chatUserSettings);
//                }
//            }
//            //mNotificationsCount = moduleManager.getChatActorWaitingYourAcceptanceCount(PublicKey, max, offset);
//            new FetchCountTask().execute();
//        } catch (Exception ex) {
//            CommonLogger.exception(TAG, ex.getMessage(), ex);
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
//                    UnexpectedUIExceptionSeverity.CRASH, ex);
//        }
//    }
//
//    /**
//     * Fragment Class implementation.
//     */
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        try {
//            rootView = inflater.inflate(R.layout.cht_comm_connections_world_fragment, container, false);
//            toolbar = getToolbar();
//            //toolbar.setTitle("Chat Users");
//            setUpScreen(inflater);
//            searchView = inflater.inflate(R.layout.cht_comm_search_edit_text, null);
//            setUpReferences();
//            switch (getFermatState().getFermatNetworkStatus()) {
//                case CONNECTED:
//                   // setUpReferences();
//                    break;
//                case DISCONNECTED:
//                    showErrorFermatNetworkDialog();
//                    break;
//            }
//
//        } catch (Exception ex) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
//                    UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
//            Toast.makeText(getActivity().getApplicationContext(),
//                    "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//        }
//        return rootView;
//    }
//
//    public void setUpReferences() {
//        dataSet = new ArrayList<>();
//        rootView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    worker.shutdownNow();
//                    return true;
//                }
//                return false;
//            }
//        });
//        searchEditText = (EditText) searchView.findViewById(R.id.search);
//        closeSearch = (ImageView) searchView.findViewById(R.id.close_search);
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new CommunityListAdapter(getActivity(), lstChatUserInformations);
//        recyclerView.setAdapter(adapter);
//        adapter.setFermatListEventListener(this);
//        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
//        swipeRefresh.setOnRefreshListener(this);
//        swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
//        rootView.setBackgroundColor(Color.parseColor("#000b12"));
//        emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
//        searchEmptyView = (LinearLayout) rootView.findViewById(R.id.search_empty_view);
//        noNetworkView = (LinearLayout) rootView.findViewById(R.id.no_connection_view);
//        noFermatNetworkView = (LinearLayout) rootView.findViewById(R.id.no_fermat_connection_view);
//        try {
//            dataSet.addAll(moduleManager.getChatActorSearch().getResult());//.getCacheSuggestionsToContact(MAX, offset));****
//        } catch (CantGetChtActorSearchResult e) {
//            e.printStackTrace();
//        }
//        if (chatUserSettings.isPresentationHelpEnabled()) {
//            showDialogHelp();
//        } else {
//            showCriptoUsersCache();
//        }
//    }
//
//    public void showErrorNetworkDialog() {
//        ErrorConnectingFermatNetworkDialog errorConnectingFermatNetworkDialog =
//                new ErrorConnectingFermatNetworkDialog(getActivity(), chatUserSubAppSession, null);
//        errorConnectingFermatNetworkDialog.setDescription("You are not connected  /n to the Fermat Network");
//        errorConnectingFermatNetworkDialog.setRightButton("Connect", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        errorConnectingFermatNetworkDialog.setLeftButton("Cancel", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        errorConnectingFermatNetworkDialog.show();
//    }
//
//    public void showErrorFermatNetworkDialog() {
//        final ErrorConnectingFermatNetworkDialog errorConnectingFermatNetworkDialog =
//                new ErrorConnectingFermatNetworkDialog(getActivity(), chatUserSubAppSession, null);
//        errorConnectingFermatNetworkDialog.setDescription("The access to the /n Fermat Network is disabled.");
//        errorConnectingFermatNetworkDialog.setRightButton("Enable", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                errorConnectingFermatNetworkDialog.dismiss();
//                try {
//                    if (getFermatState().getFermatNetworkStatus() == NetworkStatus.DISCONNECTED) {
//                        Toast.makeText(getActivity(), "Wait a minute please, trying to reconnect...",
//                                Toast.LENGTH_SHORT).show();
//                        //getActivity().onBackPressed();
//                    }
//                } catch (CantGetCommunicationNetworkStatusException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        errorConnectingFermatNetworkDialog.setLeftButton("Cancel", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                errorConnectingFermatNetworkDialog.dismiss();
//            }
//        });
//        errorConnectingFermatNetworkDialog.show();
//    }
//
//    @Override
//    public void onRefresh() {
//        if (!isRefreshing) {
//            isRefreshing = true;
//            worker = new FermatWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
//                    return getMoreData();
//                }
//            };
//            worker.setContext(getActivity());
//            worker.setCallBack(new FermatWorkerCallBack() {
//                @SuppressWarnings("unchecked")
//                @Override
//                public void onPostExecute(Object... result) {
//                    isRefreshing = false;
//                    if (swipeRefresh != null)
//                        swipeRefresh.setRefreshing(false);
//                    if (result != null &&
//                            result.length > 0) {
//                        if (getActivity() != null && adapter != null) {
//                            lstChatUserInformations = (ArrayList<ChatActorCommunityInformation>) result[0];
//                            adapter.changeDataSet(lstChatUserInformations);
//                            if (lstChatUserInformations.isEmpty()) {
//                                try {
//                                    if (!moduleManager.getChatActorSearch().getResult().isEmpty()) {//moduleManager.getCacheSuggestionsToContact(MAX, offset)
//                                        lstChatUserInformations
//                                                .addAll(moduleManager.getChatActorSearch().getResult());//getCacheSuggestionsToContact(MAX, offset));
//                                        showEmpty(false, emptyView);
//                                        showEmpty(false, searchEmptyView);
//                                    } else {
//                                        showEmpty(true, emptyView);
//                                        showEmpty(false, searchEmptyView);
//                                    }
//                                } catch (CantGetChtActorSearchResult e) {
//                                    e.printStackTrace();
//                                }
//
//                            } else {
//                                showEmpty(false, emptyView);
//                                showEmpty(false, searchEmptyView);
//                            }
//                        }
//                    } else {
//                        try {
//                            showEmpty(false, emptyView);
//                            showEmpty(false, searchEmptyView);
//                            lstChatUserInformations
//                                    .addAll(moduleManager.getChatActorSearch().getResult());//getCacheSuggestionsToContact(MAX, offset));
//                        } catch (CantGetChtActorSearchResult e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onErrorOccurred(Exception ex) {
//                    isRefreshing = false;
//                    if (swipeRefresh != null)
//                        swipeRefresh.setRefreshing(false);
//                    if (getActivity() != null)
//                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
//                    ex.printStackTrace();
//
//                }
//            });
//            worker.execute();
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.cht_comm_menu, menu);//cripto_users_menu
//
//        try {
//            final MenuItem searchItem = menu.findItem(R.id.action_search);
//            menu.findItem(R.id.action_help).setVisible(true);
//            menu.findItem(R.id.action_search).setVisible(true);
//            searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    menu.findItem(R.id.action_help).setVisible(false);
//                    menu.findItem(R.id.action_search).setVisible(false);
//                    toolbar = getToolbar();
//                    toolbar.setTitle("");
//                    toolbar.addView(searchView);
//                    if (closeSearch != null)
//                        closeSearch.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                menu.findItem(R.id.action_help).setVisible(true);
//                                menu.findItem(R.id.action_search).setVisible(true);
//                                toolbar = getToolbar();
//                                toolbar.removeView(searchView);
//                                toolbar.setTitle("Chat Users");
//                                onRefresh();
//                            }
//                        });
//
//                    if (searchEditText != null) {
//                        searchEditText.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                            }
//
//                            @Override
//                            public void onTextChanged(final CharSequence s, int start, int before, int count) {
//                                if (s.length() > 0) {
//                                    worker = new FermatWorker() {
//                                        @Override
//                                        protected Object doInBackground() throws Exception {
//                                            return getQueryData(s);
//                                        }
//                                    };
//                                    worker.setContext(getActivity());
//                                    worker.setCallBack(new FermatWorkerCallBack() {
//                                        @SuppressWarnings("unchecked")
//                                        @Override
//                                        public void onPostExecute(Object... result) {
//                                            isRefreshing = false;
//                                            if (swipeRefresh != null)
//                                                swipeRefresh.setRefreshing(false);
//                                            if (result != null &&
//                                                    result.length > 0) {
//                                                if (getActivity() != null && adapter != null) {
//                                                    dataSetFiltered = (ArrayList<ChatActorCommunityInformation>) result[0];
//                                                    adapter.changeDataSet(dataSetFiltered);
//                                                    if (dataSetFiltered != null) {
//                                                        if (dataSetFiltered.isEmpty()) {
//                                                            showEmpty(true, searchEmptyView);
//                                                            showEmpty(false, emptyView);
//
//                                                        } else {
//                                                            showEmpty(false, searchEmptyView);
//                                                            showEmpty(false, emptyView);
//                                                        }
//                                                    } else {
//                                                        showEmpty(true, searchEmptyView);
//                                                        showEmpty(false, emptyView);
//                                                    }
//                                                }
//                                            } else {
//                                                showEmpty(true, searchEmptyView);
//                                                showEmpty(false, emptyView);
//                                            }
//                                        }
//                                        @Override
//                                        public void onErrorOccurred(Exception ex) {
//                                            isRefreshing = false;
//                                            if (swipeRefresh != null)
//                                                swipeRefresh.setRefreshing(false);
//                                            showEmpty(true, searchEmptyView);
//                                            if (getActivity() != null)
//                                                Toast.makeText(getActivity(), ex.getMessage(),
//                                                        Toast.LENGTH_LONG).show();
//                                            ex.printStackTrace();
//
//                                        }
//                                    });
//                                    worker.execute();
//                                } else {
//                                    menu.findItem(R.id.action_help).setVisible(true);
//                                    menu.findItem(R.id.action_search).setVisible(true);
//                                    toolbar = getToolbar();
//                                    toolbar.removeView(searchView);
//                                    //toolbar.setTitle("Cripto wallet users");
//                                    onRefresh();
//                                }
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable s) {
//                            }
//                        });
//                    }
//                    return false;
//                }
//            });
//        } catch (Exception e) {
//
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        try {
//            int id = item.getItemId();
//
//            if (id == R.id.action_help)
//                showDialogHelp();
//
//        } catch (Exception e) {
//            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
//                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
//            makeText(getActivity(), "Oooops! recovering from system error",
//                    LENGTH_LONG).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void updateNotificationsBadge(int count) {
//        mNotificationsCount = count;
//        getActivity().invalidateOptionsMenu();
//    }
//
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//    }
//
//    private synchronized List<ChatActorCommunityInformation> getQueryData(final CharSequence charSequence) {
//        if (dataSet != null && !dataSet.isEmpty()) {
//            if (searchEditText != null && !searchEditText.getText().toString().isEmpty()) {
//                dataSetFiltered = new ArrayList<ChatActorCommunityInformation>();
//                for (ChatActorCommunityInformation chatUser : dataSet) {
//
//                    if(chatUser.getAlias().toLowerCase().contains(charSequence.toString().toLowerCase()))
//                        dataSetFiltered.add(chatUser);
//                }
//            } else dataSetFiltered = null;
//        }
//        return dataSetFiltered;
//    }
//
//
//    private synchronized List<ChatActorCommunityInformation> getMoreData() {
//        List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
//
//         try {
//            //verifico la cache para mostrar los que tenia antes y los nuevos
//             List<ChatActorCommunityInformation> userCacheList = new ArrayList<>();
//             try {
//                     userCacheList = moduleManager.getChatActorSearch().getResult(); //getCacheSuggestionsToContact(MAX, offset);
//             } catch (CantGetChtActorSearchResult e) {
//                 e.printStackTrace();
//             }
//
//            List<ChatActorCommunityInformation> userList = moduleManager.getSuggestionsToContact(moduleManager.getSelectedActorIdentity().getPublicKey() ,MAX, offset);
//            if(userCacheList.size() == 0)
//            {
//                dataSet.addAll(userList);
//                moduleManager.saveCacheChatUsersSuggestions(userList);
//            }
//            else
//            {
//                if(userList.size() == 0)
//                {
//                    dataSet.addAll(userCacheList);
//                }
//                else
//                {
//                    for (ChatActorCommunityInformation chatUserCache : userCacheList) {
//                        boolean exist = false;
//                        for (ChatActorCommunityInformation chatUser : userList) {
//                            if(chatUserCache.getPublicKey().equals(chatUser.getPublicKey())){
//                                exist = true;
//                                break;
//                            }
//                        }
//                        if(!exist)
//                            userList.add(chatUserCache);
//                    }
//                    //guardo el cache
//                    moduleManager.saveCacheChatUsersSuggestions(userList);
//                    dataSet.addAll(userList);
//                }
//            }
//            //offset = dataSet.size();
//
//        } catch (CantListChatIdentityException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dataSet;
//    }
//
//
//    @Override
//    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
//        try {
//            if (moduleManager.getSelectedActorIdentity() != null) {
//                if (!moduleManager.getSelectedActorIdentity().getPublicKey().isEmpty())
//                    appSession.setData(CHAT_USER_SELECTED, data);
//                changeActivity(Activities.CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(),
//                        appSession.getAppPublicKey());
//            }
//        } catch (CantGetSelectedActorIdentityException e) {
//            e.printStackTrace();
//        } catch (ActorIdentityNotSelectedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {
//
//    }
//
//
//    private void showDialogHelp() {
//        try {
//            if (moduleManager.getSelectedActorIdentity() != null) {
//                if (!moduleManager.getSelectedActorIdentity().getPublicKey().isEmpty()) {
//                    PresentationChatCommunityDialog presentationChatCommunityDialog =
//                            new PresentationChatCommunityDialog(getActivity(),
//                            chatUserSubAppSession,
//                            null,
//                            moduleManager,
//                            PresentationChatCommunityDialog.TYPE_PRESENTATION_WITHOUT_IDENTITIES);
//                    presentationChatCommunityDialog.show();
//                    presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            showCriptoUsersCache();
//                        }
//                    });
//                } else {
//                    PresentationChatCommunityDialog presentationChatCommunityDialog =
//                            new PresentationChatCommunityDialog(getActivity(),
//                            chatUserSubAppSession,
//                            null,
//                            moduleManager,
//                                    PresentationChatCommunityDialog.TYPE_PRESENTATION);
//                    presentationChatCommunityDialog.show();
//                    presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            Boolean isBackPressed =
//                                    (Boolean) chatUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
//                            if (isBackPressed != null) {
//                                if (isBackPressed) {
//                                    getActivity().finish();
//                                }
//                            } else {
//                                showCriptoUsersCache();
//                                invalidate();
//                            }
//                        }
//                    });
//                }
//            } else {
//                PresentationChatCommunityDialog presentationChatCommunityDialog =
//                        new PresentationChatCommunityDialog(getActivity(),
//                        chatUserSubAppSession,
//                        null,
//                        moduleManager,
//                                PresentationChatCommunityDialog.TYPE_PRESENTATION);
//                presentationChatCommunityDialog.show();
//                presentationChatCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        Boolean isBackPressed = (Boolean) chatUserSubAppSession.getData(Constants.PRESENTATION_DIALOG_DISMISS);
//                        if (isBackPressed != null) {
//                            if (isBackPressed) {
//                                getActivity().onBackPressed();
//                            }
//                        } else
//                            showCriptoUsersCache();
//                    }
//                });
//            }
//        } catch (CantGetSelectedActorIdentityException e) {
//            e.printStackTrace();
//        } catch (ActorIdentityNotSelectedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void showCriptoUsersCache() {
//
//        ChatActorCommunitySubAppModuleManager moduleManager = chatUserSubAppSession.getModuleManager();
//        if(moduleManager==null){
//            getActivity().onBackPressed();
//        }else{
//            invalidate();
//        }if (dataSet.isEmpty()) {
//            showEmpty(true, emptyView);
//            swipeRefresh.post(new Runnable() {
//                @Override
//                public void run() {
//                    swipeRefresh.setRefreshing(true);
//                    onRefresh();
//                }
//
//            });
//        } else {
//            adapter.changeDataSet(dataSet);
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    onRefresh();
//                }
//            }, 1500);
//        }
//    }
//
//    public void showEmpty(boolean show, View emptyView) {
//        Animation anim = AnimationUtils.loadAnimation(getActivity(),
//                show ? android.R.anim.fade_in : android.R.anim.fade_out);
//        if (show &&
//                (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.VISIBLE);
//            if (adapter != null)
//                adapter.changeDataSet(null);
//        } else if (!show && emptyView.getVisibility() == View.VISIBLE) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.GONE);
//        }
//
//    }
//
//    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetSelectedActorIdentityException {
//
//    }
//
//    /*
//    Sample AsyncTask to fetch the notifications count
//    */
//    class FetchCountTask extends AsyncTask<Void, Void, Integer> {
//
//        @Override
//        protected Integer doInBackground(Void... params) {
//            // example count. This is where you'd
//            // query your data store for the actual count.
//            return mNotificationsCount;
//        }
//
//        @Override
//        public void onPostExecute(Integer count) {
//            updateNotificationsBadge(count);
//        }
//    }
//
//    @Override
//    public void onUpdateViewOnUIThread(String code){
//        try
//        {
//            //update user list
//            if(code.equals("ACCEPTED_CONNECTION"))
//              onRefresh();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
//
//
//
//
