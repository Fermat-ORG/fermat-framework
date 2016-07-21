package com.bitdubai.sub_app.crypto_broker_community.fragments;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
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
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.classes.CryptoBrokerCommunitySubAppModuleInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.adapters.AvailableActorsListAdapter;
import com.bitdubai.sub_app.crypto_broker_community.common.dialogs.ConnectDialog;
import com.bitdubai.sub_app.crypto_broker_community.common.dialogs.GeolocationDialog;
import com.bitdubai.sub_app.crypto_broker_community.common.dialogs.ListIdentitiesDialog;
import com.bitdubai.sub_app.crypto_broker_community.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class BrowserTabFragment
        extends FermatListFragment<CryptoBrokerCommunityInformation, ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>>
        implements FermatListItemListeners<CryptoBrokerCommunityInformation>, OnLoadMoreDataListener, GeolocationDialog.AdapterCallback {

    //Constants
    private static final int MAX = 10;
    private static final int SPAN_COUNT = 2;
    protected static final String TAG = "BrowserTabFragment";

    //Managers
    private CryptoBrokerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private ArrayList<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();
    private int offset = 0;
    private DeviceLocation location;
    private double distance;
    private CryptoBrokerCommunitySelectableIdentity identity;

    //Flags
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    //UI
    private AvailableActorsListAdapter adapter;
    private ImageView noContacts;
    private PresentationDialog helpDialog;
    private GeolocationDialog geolocationDialog;
    private RelativeLayout locationFilterBar;
    private FermatTextView locationFilterBarCountry;
    private FermatTextView locationFilterBarPlace;
    private Toolbar toolbar;

    //DATA
    private ActiveActorIdentityInformation selectedActorIdentity;


    CryptoBrokerCommunitySettings appSettings;

    public static BrowserTabFragment newInstance() {
        return new BrowserTabFragment();
    }

    /**
     * Fragment interface implementation.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        toolbar = getToolbar();
        moduleManager.setAppPublicKey(appSession.getAppPublicKey());

        loadSelectedActorIdentityInBackground();
        loadSettings();

        //Check if a default identity is configured
        try {
            identity = moduleManager.getSelectedActorIdentity();
            if (identity == null) {
                List<CryptoBrokerCommunitySelectableIdentity> identities = moduleManager.listSelectableIdentities();
                if (identities.isEmpty())
                    launchActorCreationDialog = true; //There are no identities in device
                else
                    launchListIdentitiesDialog = true; //There are identities in device, but none selected
            }

        } catch (CantGetSelectedActorIdentityException | ActorIdentityNotSelectedException | CantListIdentitiesToSelectException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);

        configureToolbar();

        noContacts = (ImageView) rootView.findViewById(R.id.cbc_no_contacts);
        locationFilterBar = (RelativeLayout) rootView.findViewById(R.id.cbc_location_filter_footer_bar);
        locationFilterBarCountry = (FermatTextView) rootView.findViewById(R.id.cbc_location_filter_footer_bar_country);
        locationFilterBarPlace = (FermatTextView) rootView.findViewById(R.id.cbc_location_filter_footer_bar_place);

        final View locationFilterBarCloseButton = rootView.findViewById(R.id.cbc_location_filter_footer_bar_close_button);
        locationFilterBarCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = null;
                distance = identity.getAccuracy();
                offset = 0;

                final Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.cbc_slide_down);
                locationFilterBar.startAnimation(slideDown);
                locationFilterBar.setVisibility(View.GONE);
            }
        });

        if (identity == null)
            launchPresentationDialog();
        else
            onRefresh();
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbc_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbc_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.cbc_fragment_browser_tab;
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
            adapter = new AvailableActorsListAdapter(getActivity(), cryptoBrokerCommunityInformationList);
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

        final MenuItem menuItem = menu.findItem(FragmentsCommons.SEARCH_FILTER_OPTION_MENU_ID);
        menuItem.setIcon(R.drawable.lupa_blanca);

        final SearchView searchView = (SearchView) menuItem.getActionView();
        SearchViewStyleHelper.on(searchView)
                .setCursorColor(Color.WHITE)
                .setTextColor(Color.WHITE)
                .setHintTextColor(Color.WHITE)
                .setSearchHintDrawable(R.drawable.lupa_blanca)
                .setSearchButtonImageResource(R.drawable.lupa_blanca)
                .setCloseBtnImageResource(R.drawable.x_blanca)
                .setSearchPlateTint(Color.WHITE)
                .setSubmitAreaTint(Color.WHITE);

        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<CryptoBrokerCommunityInformation> filteredList = filterList(newText, cryptoBrokerCommunityInformationList);
                adapter.changeDataSet(filteredList);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case FragmentsCommons.HELP_OPTION_MENU_ID:
                if (helpDialog == null)
                    helpDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                            .setBannerRes(R.drawable.cbc_banner)
                            .setIconRes(R.drawable.crypto_broker)
                            .setSubTitle(R.string.cbp_cbc_launch_action_creation_dialog_sub_title)
                            .setBody(R.string.cbp_cbc_launch_action_creation_dialog_body)
                            .setVIewColor(R.color.cbc_toolbar_start_background)
                            .setIsCheckEnabled(false)
                            .build();

                helpDialog.show();

                return true;

            case FragmentsCommons.LOCATION_FILTER_OPTION_MENU_ID:
                try {
                    geolocationDialog = new GeolocationDialog(getActivity(), appSession, appResourcesProviderManager, this);
                    geolocationDialog.show();

                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                            UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }

                return true;
        }

        return false;
    }

    @Override
    public void onItemClickListener(final CryptoBrokerCommunityInformation data, final int position) {
        try {
            if(data.getConnectionState() == null || data.getConnectionState() != ConnectionState.CONNECTED) {
                ConnectDialog connectDialog = new ConnectDialog(getActivity(), appSession, appResourcesProviderManager, data, identity);

                connectDialog.setTitle("Connection Request");
                connectDialog.setSubtitle("New Request");
                connectDialog.setDescription(String.format("Do you want to send a connection request to %1$s?", data.getAlias()));
                connectDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateSelectedActorInList(data, position);
                        onRefresh();
                    }
                });

                connectDialog.show();
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
            Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerCommunityInformation data, int position) {
    }

    @Override
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

        Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.cbc_slide_up);
        locationFilterBar.setVisibility(View.VISIBLE);
        locationFilterBar.startAnimation(slideUp);

        onRefresh();
    }

    @Override
    public void onLoadMoreData(int page, final int totalItemsCount) {
        adapter.setLoadingData(true);
        FermatWorker fermatWorker = new FermatWorker(getActivity(), this) {
            @Override
            protected Object doInBackground() throws Exception {
                return getMoreDataAsync(FermatRefreshTypes.NEW, totalItemsCount);
            }
        };

        fermatWorker.execute();
    }

    @Override
    public List<CryptoBrokerCommunityInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoBrokerCommunityInformation> dataSet = new ArrayList<>();
        if(isVisible) {
            try {
                offset = pos;
                List<CryptoBrokerCommunityInformation> result = moduleManager.listWorldCryptoBrokers(identity, location, 0, null, MAX, offset);
                dataSet.addAll(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                        cryptoBrokerCommunityInformationList.clear();
                        cryptoBrokerCommunityInformationList.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(cryptoBrokerCommunityInformationList);
                        ((EndlessScrollListener) scrollListener).notifyDataSetChanged();
                    } else {
                        cryptoBrokerCommunityInformationList.addAll((ArrayList) result[0]);
                        adapter.notifyItemRangeInserted(offset, cryptoBrokerCommunityInformationList.size() - 1);
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

        FermatWorker fermatWorker = new FermatWorker(getActivity()) {
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
                    if(selectedActorIdentity!=null) {
                        Bitmap image = BitmapFactory.decodeByteArray(selectedActorIdentity.getImage(), 0, selectedActorIdentity.getImage().length);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getRoundedShape(image, 120));
                        toolbar.setLogo(bitmapDrawable);
                    }
//                    else{
//                        Log.e(TAG,"selectedActorIdentity null, Nelson fijate si esto queres que haga");
//                    }
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
     * Obtain Settings or create new Settings if first time opening subApp
     */
    private void loadSettings() {
        try {
            appSettings = this.moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            appSettings = null;
        }

        if (appSettings == null) {
            appSettings = new CryptoBrokerCommunitySettings();
            appSettings.setIsPresentationHelpEnabled(true);
            try {
                moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Launch a presentation dialog to create a identity or the list of available identities to select
     */
    private void launchPresentationDialog() {
        try {
            DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    try {
                        identity = moduleManager.getSelectedActorIdentity();
                        loadSelectedActorIdentityInBackground();
                        if (identity == null)
                            getActivity().onBackPressed();
                        else {
                            invalidate();
                            onRefresh();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            if (launchActorCreationDialog) {
                PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                        .setBannerRes(R.drawable.cbc_banner)
                        .setIconRes(R.drawable.crypto_broker)
                        .setSubTitle(R.string.cbp_cbc_launch_action_creation_dialog_sub_title)
                        .setBody(R.string.cbp_cbc_launch_action_creation_dialog_body)
                        .setTextFooter(R.string.cbp_cbc_launch_action_creation_dialog_footer)
                        .setTextNameLeft(R.string.cbp_cbc_launch_action_creation_name_left)
                        .setTextNameRight(R.string.cbp_cbc_launch_action_creation_name_right)
                        .setVIewColor(R.color.cbc_toolbar_start_background)
                        .setIsCheckEnabled(false)
                        .build();

                presentationDialog.setOnDismissListener(onDismissListener);
                presentationDialog.show();

            } else if (launchListIdentitiesDialog) {
                ListIdentitiesDialog listIdentitiesDialog = new ListIdentitiesDialog(getActivity(), appSession, appResourcesProviderManager);
                listIdentitiesDialog.setOnDismissListener(onDismissListener);
                listIdentitiesDialog.show();

            } else {
                onRefresh();
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Show or hide the empty view if there is data to show
     */
    private void showOrHideEmptyView() {
        final boolean show = cryptoBrokerCommunityInformationList.isEmpty();
        final int animationResourceId = show ? android.R.anim.fade_in : android.R.anim.fade_out;
        if(isAttached) {
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
        }else{
            Log.e(TAG,"Fragment not attached");
        }
    }

    /**
     * Update the actor information and notify the adapter to show the updated info
     *
     * @param actorInformation the actor information
     * @param position         the actor's position in the adapter
     */
    private void updateSelectedActorInList(CryptoBrokerCommunityInformation actorInformation, int position) {
        if (appSession.getData(FragmentsCommons.CONNECTION_RESULT) == null)
            return;

        try {
            final ConnectionState newConnectionState = (ConnectionState) appSession.getData(FragmentsCommons.CONNECTION_RESULT);
            appSession.removeData(FragmentsCommons.CONNECTION_RESULT);

            CryptoBrokerCommunityInformation updatedInfo = new CryptoBrokerCommunitySubAppModuleInformation(
                    actorInformation.getPublicKey(),
                    actorInformation.getAlias(),
                    actorInformation.getImage(),
                    newConnectionState,
                    actorInformation.getConnectionId(),
                    actorInformation.getLocation(),
                    actorInformation.getCountry(),
                    actorInformation.getPlace());

            cryptoBrokerCommunityInformationList.set(position, updatedInfo);
            adapter.notifyItemChanged(position);

        } catch (Exception ex) {
            errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    private List<CryptoBrokerCommunityInformation> filterList(String filterText, List<CryptoBrokerCommunityInformation> baseList) {
        final ArrayList<CryptoBrokerCommunityInformation> filteredList = new ArrayList<>();
        for (CryptoBrokerCommunityInformation item : baseList) {
            if (item.getAlias().toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }
}




