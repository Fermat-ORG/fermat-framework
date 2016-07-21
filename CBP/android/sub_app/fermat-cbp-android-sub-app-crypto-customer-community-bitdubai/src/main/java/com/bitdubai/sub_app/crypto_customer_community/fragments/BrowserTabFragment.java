package com.bitdubai.sub_app.crypto_customer_community.fragments;

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
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.EndlessScrollListener;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_android_api.ui.util.SearchViewStyleHelper;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.adapters.AvailableActorsListAdapter;
import com.bitdubai.sub_app.crypto_customer_community.common.dialogs.GeolocationDialog;
import com.bitdubai.sub_app.crypto_customer_community.common.dialogs.ListIdentitiesDialog;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;


/**
 * Created by Alejandro Bicelis on 02/02/2016.
 * <p/>
 * Updated by Nelson Ramirez (nelsonalfo@gmail.com) on 27/06/2016.
 */
public class BrowserTabFragment
        extends FermatListFragment<CryptoCustomerCommunityInformation, ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>>
        implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreDataListener, GeolocationDialog.AdapterCallback {

    //Constants
    private static final int MAX = 10;
    protected static final String TAG = "BrowserTabFragment";

    //Managers
    private CryptoCustomerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private ArrayList<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList = new ArrayList<>();
    private CryptoCustomerCommunitySelectableIdentity identity;
    private DeviceLocation location;
    private String alias = null;
    private double distance;
    private int offset;

    //Flags
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    //UI
    private AvailableActorsListAdapter adapter;
    private ImageView noUsers;
    private PresentationDialog helpDialog;
    private GeolocationDialog geolocationDialog;
    private RelativeLayout locationFilterBar;
    private FermatTextView locationFilterBarCountry;
    private FermatTextView locationFilterBarPlace;
    private Toolbar toolbar;

    //DATA
    private ActiveActorIdentityInformation selectedActorIdentity;
    //Obtain Settings or create new Settings if first time opening subApp
    CryptoCustomerCommunitySettings appSettings;

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
                List<CryptoCustomerCommunitySelectableIdentity> identities = moduleManager.listSelectableIdentities();
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

    private void loadSettings() {
        try {
            appSettings = this.moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            appSettings = null;
        }

        if (appSettings == null) {
            appSettings = new CryptoCustomerCommunitySettings();
            appSettings.setIsPresentationHelpEnabled(true);
            try {
                moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);

        configureToolbar();

        noUsers = (ImageView) rootView.findViewById(R.id.ccc_no_users);
        locationFilterBar = (RelativeLayout) rootView.findViewById(R.id.ccc_location_filter_footer_bar);
        locationFilterBarCountry = (FermatTextView) rootView.findViewById(R.id.ccc_location_filter_footer_bar_country);
        locationFilterBarPlace = (FermatTextView) rootView.findViewById(R.id.ccc_location_filter_footer_bar_place);

        final View locationFilterBarCloseButton = rootView.findViewById(R.id.ccc_location_filter_footer_bar_close_button);
        locationFilterBarCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = null;
                distance = identity.getAccuracy();
                offset = 0;

                final Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.ccc_slide_down);
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
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccc_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccc_action_bar_gradient_colors));
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.ccc_fragment_browser_tab;
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
            adapter = new AvailableActorsListAdapter(getActivity(), cryptoCustomerCommunityInformationList);
        }

        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
        menuItem.setIcon(R.drawable.ccc_search_icon_withe);

        final SearchView searchView = (SearchView) menuItem.getActionView();
        SearchViewStyleHelper.on(searchView)
                .setCursorColor(Color.WHITE)
                .setTextColor(Color.WHITE)
                .setHintTextColor(Color.WHITE)
                .setSearchHintDrawable(R.drawable.ccc_search_icon_withe)
                .setSearchButtonImageResource(R.drawable.ccc_search_icon_withe)
                .setCloseBtnImageResource(R.drawable.ccc_close_icon_white)
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
                List<CryptoCustomerCommunityInformation> filteredList = filterList(newText, cryptoCustomerCommunityInformationList);
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
                            .setBannerRes(R.drawable.ccc_banner)
                            .setIconRes(R.drawable.crypto_customer)
                            .setSubTitle(R.string.cbp_ccc_launch_action_creation_dialog_sub_title)
                            .setBody(R.string.cbp_ccc_launch_action_creation_dialog_body)
                            .setVIewColor(R.color.ccc_color_dialog)
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
        }

        return false;
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

        Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.ccc_slide_up);
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
    public List<CryptoCustomerCommunityInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoCustomerCommunityInformation> dataSet = new ArrayList<>();
        if (isVisible) {
            try {
                offset = pos;
                List<CryptoCustomerCommunityInformation> result = moduleManager.listWorldCryptoCustomers(moduleManager.getSelectedActorIdentity(), location, distance, alias, MAX, offset);
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
                        cryptoCustomerCommunityInformationList.clear();
                        cryptoCustomerCommunityInformationList.addAll((ArrayList) result[0]);
                        adapter.changeDataSet(cryptoCustomerCommunityInformationList);
                        ((EndlessScrollListener) scrollListener).notifyDataSetChanged();
                    } else {
                        cryptoCustomerCommunityInformationList.addAll((ArrayList) result[0]);
                        adapter.notifyItemRangeInserted(offset, cryptoCustomerCommunityInformationList.size() - 1);
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


    private void loadSelectedActorIdentityInBackground() {

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
                    if (selectedActorIdentity != null) {
                        Bitmap image = BitmapFactory.decodeByteArray(selectedActorIdentity.getImage(), 0, selectedActorIdentity.getImage().length);
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getRoundedShape(image, 120));
                        toolbar.setLogo(bitmapDrawable);
                    }
//                    else {
//                        Log.e(TAG, "selectedActorIdentity is null");
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

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage, int width) {
        // TODO Auto-generated method stub
        int targetWidth = width;
        int targetHeight = width;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

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
                        .setBannerRes(R.drawable.ccc_banner)
                        .setIconRes(R.drawable.crypto_customer)
                        .setSubTitle(R.string.cbp_ccc_launch_action_creation_dialog_sub_title)
                        .setBody(R.string.cbp_ccc_launch_action_creation_dialog_body)
                        .setTextFooter(R.string.cbp_ccc_launch_action_creation_dialog_footer)
                        .setTextNameLeft(R.string.cbp_ccc_launch_action_creation_name_left)
                        .setTextNameRight(R.string.cbp_ccc_launch_action_creation_name_right)
                        .setVIewColor(R.color.ccc_color_dialog)
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

    private void showOrHideEmptyView() {
        final boolean show = cryptoCustomerCommunityInformationList.isEmpty();
        final int animationResourceId = show ? android.R.anim.fade_in : android.R.anim.fade_out;

        Animation anim = AnimationUtils.loadAnimation(getActivity(), animationResourceId);
        if (show && (noUsers.getVisibility() == View.GONE || noUsers.getVisibility() == View.INVISIBLE)) {
            noUsers.setAnimation(anim);
            noUsers.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

        } else if (!show && noUsers.getVisibility() == View.VISIBLE) {
            noUsers.setAnimation(anim);
            noUsers.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private List<CryptoCustomerCommunityInformation> filterList(String filterText, List<CryptoCustomerCommunityInformation> baseList) {
        final ArrayList<CryptoCustomerCommunityInformation> filteredList = new ArrayList<>();
        for (CryptoCustomerCommunityInformation item : baseList) {
            if (item.getAlias().toLowerCase().contains(filterText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }
}




