package com.bitdubai.sub_app.intra_user_community.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.EndlessScrollListener;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_android_api.ui.util.SearchViewStyleHelper;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraUserWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.AvailableActorsListAdapter;

import com.bitdubai.sub_app.intra_user_community.common.popups.ConnectDialog;

import com.bitdubai.sub_app.intra_user_community.common.popups.ErrorConnectingFermatNetworkDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.GeolocationDialog;
import com.bitdubai.sub_app.intra_user_community.common.popups.PresentationIntraUserCommunityDialog;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;

/**
 * Created by natalia on 14/07/16.
 */
public class BrowserTabFragment
        extends FermatListFragment<IntraUserInformation, ReferenceAppFermatSession<IntraUserModuleManager>>
        implements FermatListItemListeners<IntraUserInformation>, OnLoadMoreDataListener, GeolocationDialog.AdapterCallback {

    //Constants
    private static final int MAX = 10;
    private static final int SPAN_COUNT = 2;
    protected static final String TAG = "BrowserTabFragment";
    public static final String INTRA_USER_SELECTED = "intra_user";
    //Managers
    private static IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;

    private ArrayList<IntraUserInformation> lstIntraUserInformations = new ArrayList<>();
    private int offset = 0;
    private Location location;
    private double distance = 100;
    private IntraUserLoginIdentity identity;
    private FermatWorker fermatWorker;


    //Flags
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    //UI
    private AvailableActorsListAdapter adapter;
    private LinearLayout noContacts;
    private PresentationDialog helpDialog;
    private GeolocationDialog geolocationDialog;
    private RelativeLayout locationFilterBar;
    private FermatTextView locationFilterBarCountry;
    private FermatTextView locationFilterBarPlace;

    private Toolbar toolbar;
    private ExecutorService _executor;
    LinearLayout searchEmptyView;
    private SwipeRefreshLayout swipeRefresh;

    //DATA
    private ActiveActorIdentityInformation selectedActorIdentity;
    private ReferenceAppFermatSession<IntraUserModuleManager> intraUserSubAppSession;
    IntraUserWalletSettings intraUserWalletSettings = null;
    private FermatApplicationCaller fermatApplicationCaller;

    private Handler handler = new Handler();

    public static BrowserTabFragment newInstance() {
        return new BrowserTabFragment();
    }

    /**
     * Fragment interface implementation.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            _executor = Executors.newFixedThreadPool(2);

            setHasOptionsMenu(true);
            // setting up  module

            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            intraUserSubAppSession = appSession;
            fermatApplicationCaller = ((FermatApplicationSession)getActivity().getApplicationContext()).getApplicationManager();

            try {
                intraUserWalletSettings = moduleManager.loadAndGetSettings(intraUserSubAppSession.getAppPublicKey());
            } catch (Exception e) {
                intraUserWalletSettings = null;
            }

            if (intraUserSubAppSession.getAppPublicKey() != null) //the identity not exist yet
            {
                if (intraUserWalletSettings == null) {
                    intraUserWalletSettings = new IntraUserWalletSettings();
                    intraUserWalletSettings.setIsPresentationHelpEnabled(true);
                    intraUserSubAppSession.getModuleManager().persistSettings(intraUserSubAppSession.getAppPublicKey(), intraUserWalletSettings);
                }
            }


            toolbar = getToolbar();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setElevation(10);
            }

            //consult net work status


            _executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                NetworkStatus networkStatus = null;
                                try {
                                    networkStatus = getFermatNetworkStatus();
                                } catch (CantGetCommunicationNetworkStatusException e) {
                                    e.printStackTrace();
                                }
                                switch (networkStatus) {
                                    case CONNECTED:
                                        // setUpReferences();
                                        break;
                                    case DISCONNECTED:
                                        showErrorFermatNetworkDialog();
                                        break;
                                }

                            }
                        }, 400);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            //getting location and setting device locacion
            location = moduleManager.getLocationManager();

            if(location==null){
                //  showErrorGPS();
                Toast.makeText(getActivity(), "Please, turn ON your GPS", Toast.LENGTH_SHORT);
            }

            identity =  moduleManager.getActiveIntraUserIdentity();

            if(identity != null)
              distance = identity.getAccuracy();

            // turnGPSOn();


        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);

        configureToolbar();

        noContacts = (LinearLayout) rootView.findViewById(R.id.empty_view);
        searchEmptyView = (LinearLayout) rootView.findViewById(R.id.search_empty_view);


       // locationFilterBar = (RelativeLayout) rootView.findViewById(R.id.cbc_location_filter_footer_bar);
       // locationFilterBarCountry = (FermatTextView) rootView.findViewById(R.id.cbc_location_filter_footer_bar_country);
       // locationFilterBarPlace = (FermatTextView) rootView.findViewById(R.id.cbc_location_filter_footer_bar_place);

      //  final View locationFilterBarCloseButton = rootView.findViewById(R.id.cbc_location_filter_footer_bar_close_button);
       /* locationFilterBarCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = null;
                distance = identity.getAccuracy();
                offset = 0;

                final Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                locationFilterBar.startAnimation(slideDown);
                locationFilterBar.setVisibility(View.GONE);
            }
        });*/

        if (identity == null)
            showDialogHelp();
        else
            onRefresh();
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbc_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbc_action_bar_gradient_colors));*/

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_connections_world;
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
            adapter = new AvailableActorsListAdapter(getActivity(), lstIntraUserInformations);
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
                        case AvailableActorsListAdapter.DATA_ITEM:
                            return 1;
                        case AvailableActorsListAdapter.LOADING_ITEM:
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

        final MenuItem menuItem = menu.findItem(1);
        if(menuItem!=null) {
            menuItem.setIcon(R.drawable.search_icon);

            final android.widget.SearchView searchView = (android.widget.SearchView) menuItem.getActionView();
            SearchViewStyleHelper.on(searchView)
                    .setCursorColor(Color.WHITE)
                    .setTextColor(Color.WHITE)
                    .setHintTextColor(Color.WHITE)
                    .setSearchHintDrawable(R.drawable.search_icon)
                    .setSearchButtonImageResource(R.drawable.search_icon)
                    .setCloseBtnImageResource(R.drawable.search_icon)
                    .setSearchPlateTint(Color.WHITE)
                    .setSubmitAreaTint(Color.WHITE);

            searchView.setQueryHint("Search...");
            searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    List<IntraUserInformation> filteredList = filterList(newText, lstIntraUserInformations);
                    adapter.changeDataSet(filteredList);
                    return true;
                }
            });
        }else{
            Log.e(TAG,"SearchView null, please check this");
        }
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

            if (data.getState().equals(ProfileStatus.ONLINE)) {
                if (moduleManager.getActiveIntraUserIdentity() != null) {
                    if (!moduleManager.getActiveIntraUserIdentity().getPublicKey().isEmpty())
                        appSession.setData(INTRA_USER_SELECTED, data);
                    ConnectDialog connectDialog;
                    connectDialog = new ConnectDialog(getActivity(),
                            (ReferenceAppFermatSession) appSession, null, data, moduleManager.getActiveIntraUserIdentity());
                    connectDialog.setTitle("CONFIRM CONNECTION");
                    connectDialog.setDescription("Are you sure you want to connect with " + data.getName() + "?");
                    connectDialog.setUsername(data.getName());
                    connectDialog.setSecondDescription("");
                    connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            onRefresh();
                        }
                    });
                    connectDialog.show();
                }
            }else
                Toast.makeText(getActivity(),"USER OFFLINE",Toast.LENGTH_SHORT).show();

            } catch (CantGetActiveLoginIdentityException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onLongItemClickListener(IntraUserInformation data, int position) {

    }


    public void showErrorFermatNetworkDialog() {
        final ErrorConnectingFermatNetworkDialog errorConnectingFermatNetworkDialog = new ErrorConnectingFermatNetworkDialog(getActivity(), intraUserSubAppSession, null);
        errorConnectingFermatNetworkDialog.setLeftButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConnectingFermatNetworkDialog.dismiss();
                getActivity().onBackPressed();
            }
        });
        errorConnectingFermatNetworkDialog.setRightButton("CONNECT", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConnectingFermatNetworkDialog.dismiss();
                try {
                    if (getFermatNetworkStatus() == NetworkStatus.DISCONNECTED) {
                        Toast.makeText(getActivity(), "Wait a minute please, trying to reconnect...", Toast.LENGTH_SHORT).show();
                        //getActivity().onBackPressed();
                    }
                } catch (CantGetCommunicationNetworkStatusException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // changeActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY, appSession.getAppPublicKey());
            }
        });
        errorConnectingFermatNetworkDialog.show();
    }

   // @Override
    public void onLocationItemClicked(ExtendedCity city) {
        offset = 0;

        location = new DeviceLocation();
        location.setLatitude((double) city.getLatitude());
        location.setLongitude((double) city.getLongitude());

        distance = identity.getAccuracy();
        location.setAccuracy((long) distance);


        geolocationDialog.dismiss();
        locationFilterBarCountry.setText(city.getCountryName());
        locationFilterBarPlace.setText(city.getName());

        Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in);
        locationFilterBar.setVisibility(View.VISIBLE);
        locationFilterBar.startAnimation(slideUp);

        onRefresh();
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
            List<IntraUserInformation> userList = moduleManager.getSuggestionsToContact(location, distance, null, MAX, offset);
            if(userList != null)
                dataSet.addAll(userList);
            else {
                if(lstIntraUserInformations!=null) {
                    dataSet.addAll(lstIntraUserInformations);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "Request User List Time Out.", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Log.e(TAG,"Request null");
                }
            }


        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), "Request User List Time Out.", Toast.LENGTH_LONG).show();
                }
            });
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
                        lstIntraUserInformations.clear();
                        lstIntraUserInformations.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(lstIntraUserInformations);
                        ((EndlessScrollListener) scrollListener).notifyDataSetChanged();
                    } else {
                        lstIntraUserInformations.addAll((ArrayList) result[0]);
                        adapter.notifyItemRangeInserted(offset, lstIntraUserInformations.size() - 1);
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

    private void loadSelectedActorIdentityInBackground(){

        fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground() throws Exception {
                if (selectedActorIdentity == null)
                    return moduleManager.getSelectedActorIdentity();
                return selectedActorIdentity;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                try {
                    selectedActorIdentity = (ActiveActorIdentityInformation) result[0];
                    if (selectedActorIdentity != null) {
                        Bitmap image = BitmapFactory.decodeByteArray(selectedActorIdentity.getImage(), 0, selectedActorIdentity.getImage().length);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getRoundedShape(image, 120));
                        toolbar.setLogo(bitmapDrawable);
                    } else {
                        Log.e(TAG, "selectedActorIdentity null, Nelson fijate si esto queres que haga");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorOccurred(Exception ex) {
                final ErrorManager errorManager = appSession.getErrorManager();
                errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        });

        fermatWorker.execute();
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage,int width) {
        // TODO Auto-generated method stub
        int targetWidth = width;
        int targetHeight = width;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }


    /**
     * Launch a presentation dialog to create a identity or the list of available identities to select
     */
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

    /**
     * Show or hide the empty view if there is data to show
     */
    private void showOrHideEmptyView() {
        final boolean show = lstIntraUserInformations.isEmpty();
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



    private List<IntraUserInformation> filterList(String filterText, List<IntraUserInformation> baseList) {
        final ArrayList<IntraUserInformation> filteredList = new ArrayList<>();
        for (IntraUserInformation item : baseList) {
            if (item.getName().toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    @Override
    public void onMethodCallback(ExtendedCity cityFromList) {

    }


    @Override
    public void onStop() {
        if(fermatWorker != null)
            fermatWorker.shutdownNow();
        super.onStop();
    }

}




