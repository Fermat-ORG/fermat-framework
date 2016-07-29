package com.bitdubai.sub_app.chat_community.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.CommunityListAdapter;
import com.bitdubai.sub_app.chat_community.app_connection.ChatCommunityFermatAppConnection;
import com.bitdubai.sub_app.chat_community.common.popups.GeolocationDialog;
import com.bitdubai.sub_app.chat_community.common.popups.PresentationChatCommunityDialog;
import com.bitdubai.sub_app.chat_community.common.popups.SearchAliasDialog;
import com.bitdubai.sub_app.chat_community.constants.Constants;
import com.bitdubai.sub_app.chat_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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
        FermatListItemListeners<ChatActorCommunityInformation>,
        GeolocationDialog.AdapterCallback, SearchAliasDialog.AdapterCallbackAlias {

    //Constants
    public static final String CHAT_USER_SELECTED = "chat_user";
    private static final int MAX = 8;
    protected final String TAG = "Recycler Base";

    //Managers
    private ChatActorCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private ChatCommunityFermatAppConnection appConnection;
    FermatApplicationCaller applicationsHelper;
    //Data
    private ChatActorCommunitySettings appSettings;
    private ChatActorCommunitySelectableIdentity identity;
    private int offset = 0;
    private ArrayList<ChatActorCommunityInformation> lstChatUserInformations;
    private DeviceLocation location = null;
    private double distance = 0;
    private String alias;
    Location locationGPS;

    //Flags
    private boolean isRefreshing = false, launchActorCreationDialog = false, launchListIdentitiesDialog = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    //UI
    private View rootView;
    private SearchView searchView;
    private ProgressBar progressBar;
    private LinearLayout emptyView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private CommunityListAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private ExecutorService executor;
    TextView noDatalabel;
    ImageView noData;
    private Button refreshButton;
    private View refreshButtonView;

    private RelativeLayout greenBar;
    private ImageView closeGreenBar;
    private TextView greenBarCountry;
    private TextView greenBarCity;

    public static ConnectionsWorldFragment newInstance() {
        return new ConnectionsWorldFragment();
    }

    @Override
    public void onMethodCallback(ExtendedCity city) {

        greenBar = (RelativeLayout) rootView.findViewById(R.id.green_bar_layout);
        closeGreenBar = (ImageView) rootView.findViewById(R.id.close_green_bar);
        greenBarCountry = (TextView) rootView.findViewById(R.id.country_green_bar);
        greenBarCity = (TextView) rootView.findViewById(R.id.city_green_bar);

        greenBarCountry.setText(city.getCountryName());
        greenBarCity.setText(city.getName());
        greenBar.setVisibility(View.VISIBLE);

        location = new DeviceLocation();
        location.setLatitude((double) city.getLatitude());
        location.setLongitude((double) city.getLongitude());
        offset = 0;
        onRefresh();

        closeGreenBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                greenBar.setVisibility(View.GONE);
                location = null;
                offset = 0;
                onRefresh();
            }
        });

    }

    @Override
    public void onMethodCallbackAlias(String aliasSearch) {
        alias = aliasSearch;
        onRefresh();
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
            applicationsHelper = ((FermatApplicationSession) getActivity().getApplicationContext()).getApplicationManager();
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
                    if (errorManager != null)
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_COMMUNITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
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
            turnGPSOn();
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
            refreshButtonView = (View) rootView.findViewById(R.id.show_more_layout);
            refreshButton = (Button) rootView.findViewById(R.id.show_more_button);
            progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                        offset = totalItemCount;
                        final int lastItem = pastVisiblesItems + visibleItemCount;
                        if (lastItem == totalItemCount) {
                            refreshButtonView.setVisibility(View.VISIBLE);
                            refreshButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    refreshButtonView.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    onRefresh();
                                }
                            });
                        } else {
                            refreshButtonView.setVisibility(View.GONE);
                        }
                    }
                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            rootView.setBackgroundColor(Color.parseColor("#f9f9f9"));
            noDatalabel = (TextView) rootView.findViewById(R.id.nodatalabel);
            noData = (ImageView) rootView.findViewById(R.id.nodata);
            //Set up swipeRefresher
//            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
//            swipeRefresh.setOnRefreshListener(this);
//            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
            rootView.setBackgroundColor(Color.parseColor("#F9F9F9"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            showEmpty(true, emptyView);
            progressBar.setVisibility(View.VISIBLE);
            launchPresentationDialog();
        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
        }
        return rootView;
    }

    private void launchPresentationDialog() {
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
                    .setVIewColor(R.color.cht_color_dialog_community)
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
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            presentationChatCommunityDialog.show();
        } else {
            invalidate();
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        try {
            if (!isRefreshing) {
                isRefreshing = true;
                final FermatWorker worker = new FermatWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        return getMoreDataAsync(location, distance, alias, MAX, offset);
                    }
                };
                worker.setContext(getActivity());
                worker.setCallBack(new FermatWorkerCallBack() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onPostExecute(Object... result) {
                        isRefreshing = false;
                        if (/*swipeRefresh != null &&*/ isAttached) {
                            //swipeRefresh.setRefreshing(false);
                            if (result != null &&
                                    result.length > 0) {
                                if (getActivity() != null && adapter != null) {
                                    if (offset == 0) {
                                        if (lstChatUserInformations != null) {
                                            lstChatUserInformations.clear();
                                            lstChatUserInformations.addAll((ArrayList<ChatActorCommunityInformation>) result[0]);
                                        } else {
                                            lstChatUserInformations = (ArrayList<ChatActorCommunityInformation>) result[0];
                                        }
                                        adapter.refreshEvents((ArrayList<ChatActorCommunityInformation>) result[0]);
                                    } else {
                                        ArrayList<ChatActorCommunityInformation> temp = (ArrayList<ChatActorCommunityInformation>) result[0];
                                        for (ChatActorCommunityInformation info : temp)
                                            if (notInList(info)) {
                                                lstChatUserInformations.add(info);
                                            }
                                        adapter.notifyItemRangeInserted(offset, lstChatUserInformations.size() - 1);
                                    }
                                    if (lstChatUserInformations.isEmpty()) {
                                        showEmpty(true, emptyView);
                                    } else {
                                        showEmpty(false, emptyView);
                                    }
                                }
                            } else
                                showEmpty(true, emptyView);
                        }
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        try {
                            isRefreshing = false;
                            /*if (swipeRefresh != null && isAttached)
                                swipeRefresh.setRefreshing(false);*/
                            worker.shutdownNow();
                            if (getActivity() != null)
                                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                worker.execute();
            }
        } catch (Exception ignore) {
            if (executor != null) {
                executor.shutdown();
                executor = null;
            }
        }
    }

    private boolean notInList(ChatActorCommunityInformation info) {
        for (ChatActorCommunityInformation contact : lstChatUserInformations) {
            if (contact.getPublicKey().equals(info.getPublicKey()))
                return false;
        }
        return true;
    }

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

    private List<ChatActorCommunityInformation> getMoreDataAsync(DeviceLocation location, double distance, String alias, int max, int offset) {
        List<ChatActorCommunityInformation> dataSet = new ArrayList<>();
        try {
            List<ChatActorCommunityInformation> result;
            if (identity != null) {
                result = moduleManager.listWorldChatActor(identity.getPublicKey(), identity.getActorType(),
                        location, distance, alias, max, offset);
                dataSet.addAll(result);
                offset = dataSet.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    @Override
    public void onFragmentFocus() {
    }

    @Override
    public void onItemClickListener(ChatActorCommunityInformation data, int position) {
    }

    @Override
    public void onLongItemClickListener(ChatActorCommunityInformation data, int position) {
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
    }

    public void onOptionMenuPrepared(Menu menu) {
        MenuItem searchItem = menu.findItem(1);
        final SearchAliasDialog.AdapterCallbackAlias ad = this;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint(getResources().getString(R.string.description_search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    try {
                        SearchAliasDialog notificationSearchAliasDialog =
                                new SearchAliasDialog(getActivity(), appSession, null, null, null, s, ad);
                        notificationSearchAliasDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });
                        notificationSearchAliasDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals(searchView.getQuery().toString())) {
                        adapter.changeDataSet(lstChatUserInformations);
                        adapter.getFilter().filter(s);
                    }
                    return false;
                }
            });
            if (appSession.getData("filterString") != null) {
                String filterString = (String) appSession.getData("filterString");
                if (filterString.length() > 0) {
                    searchView.setQuery(filterString, true);
                    searchView.setIconified(false);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 3:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    showDialogHelp();
                    break;
                case 1:
                    break;
                case 2:
                    try {
                        GeolocationDialog geolocationDialog =
                                new GeolocationDialog(getActivity(), appSession, null, this);
                        geolocationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        });
                        Window window = geolocationDialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.TOP;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                        window.setAttributes(wlp);
                        geolocationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        geolocationDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            if (identity != null) {
                if (!identity.getPublicKey().isEmpty()) {
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
        } catch (Exception e) {
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

    public void turnGPSOn() {
        try {
            if (!checkGPSFineLocation() || !checkGPSCoarseLocation()) { //if gps is disabled
                if (Build.VERSION.SDK_INT < 23) {
                    if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this.getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                    if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this.getActivity(),
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                } else {
                    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        getActivity().requestPermissions(
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        getActivity().requestPermissions(
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                }
            }
        } catch (Exception e) {
            try {
                Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                intent.putExtra("enabled", true);
                if (Build.VERSION.SDK_INT < 23) {
                    String provider = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (!provider.contains("gps")) { //if gps is disabled
                        makeText(getActivity(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                } else {
                    String provider = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (!provider.contains("gps")) { //if gps is disabled
                        makeText(getContext(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
                        Intent gpsOptionsIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                    }
                }
            } catch (Exception ex) {
                if (Build.VERSION.SDK_INT < 23) {
                    makeText(getActivity(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
                } else {
                    makeText(getContext(), "Please, turn on your GPS", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private boolean checkGPSCoarseLocation() {
        String permission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkGPSFineLocation() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}