package com.bitdubai.sub_app.crypto_broker_community.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.OnLoadMoreDataListener;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.settings.CryptoBrokerCommunitySettings;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.adapters.AppListAdapter;
import com.bitdubai.sub_app.crypto_broker_community.common.popups.ListIdentitiesDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ConnectionsWorldFragment
        extends FermatListFragment<CryptoBrokerCommunityInformation, ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>>
        implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<CryptoBrokerCommunityInformation>, OnLoadMoreDataListener {

    //Constants
    private static final int MAX = 15;
    private static final int SPAN_COUNT = 3;
    protected static final String TAG = "ConnectionsWorldFrag";
    public static final String ACTOR_SELECTED = "actor_selected";

    //Managers
    private CryptoBrokerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    private ArrayList<CryptoBrokerCommunityInformation> cryptoBrokerCommunityInformationList = new ArrayList<>();

    //Flags
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    //UI
    private LinearLayout emptyView;
    private AppListAdapter adapter;
    TextView noDataLabel;
    ImageView noData;
    private int offset;

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
            CryptoBrokerCommunitySettings appSettings;
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
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);

        moduleManager.setAppPublicKey(appSession.getAppPublicKey());

        noDataLabel = (TextView) rootView.findViewById(R.id.nodatalabel);
        noData = (ImageView) rootView.findViewById(R.id.nodata);
        emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE);
        rootView.setBackgroundColor(Color.parseColor("#F9F9F9"));
        emptyView.setBackgroundColor(Color.parseColor("#F9F9F9"));

        launchPresentationDialog();
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_connections_world_cbp;
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
            adapter = new AppListAdapter(getActivity(), cryptoBrokerCommunityInformationList);
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
                        case AppListAdapter.DATA_ITEM:
                            return 1;
                        case AppListAdapter.LOADING_ITEM:
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
//        if (scrollListener == null) {
//            EndlessScrollListener endlessScrollListener = new EndlessScrollListener(getLayoutManager());
//            endlessScrollListener.setOnLoadMoreDataListener(this);
//            scrollListener = endlessScrollListener;
//        }
//
//        return scrollListener;
        return null;
    }

    @Override
    public void onItemClickListener(CryptoBrokerCommunityInformation data, int position) {
        appSession.setData(ACTOR_SELECTED, data);
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerCommunityInformation data, int position) {
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

        try {
            offset = pos;
            List<CryptoBrokerCommunityInformation> result = moduleManager.listWorldCryptoBrokers(moduleManager.getSelectedActorIdentity(), MAX, offset);
            dataSet.addAll(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }

    @Override
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

    private void launchPresentationDialog() {
        try {
            if (launchActorCreationDialog) {
                PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                        .setBannerRes(R.drawable.banner_crypto_broker)
                        .setIconRes(R.drawable.crypto_broker)
                        .setSubTitle(R.string.cbp_cbc_launch_action_creation_dialog_sub_title)
                        .setBody(R.string.cbp_cbc_launch_action_creation_dialog_body)
                        .setTextFooter(R.string.cbp_cbc_launch_action_creation_dialog_footer)
                        .setTextNameLeft(R.string.cbp_cbc_launch_action_creation_name_left)
                        .setTextNameRight(R.string.cbp_cbc_launch_action_creation_name_right)
                        .setImageRight(R.drawable.ic_profile_male)
                        .setIsCheckEnabled(true)
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
                ListIdentitiesDialog listIdentitiesDialog = new ListIdentitiesDialog(getActivity(), appSession, null);
                listIdentitiesDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        invalidate();
                        onRefresh();
                    }
                });

                listIdentitiesDialog.show();

            } else {
                invalidate();
                onRefresh();
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    private void showOrHideEmptyView() {
        final boolean show = cryptoBrokerCommunityInformationList.isEmpty();
        final int animationResourceId = show ? android.R.anim.fade_in : android.R.anim.fade_out;

        Animation anim = AnimationUtils.loadAnimation(getActivity(), animationResourceId);
        if (show && (emptyView.getVisibility() == View.GONE || emptyView.getVisibility() == View.INVISIBLE)) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noData.setAnimation(anim);
            noDataLabel.setAnimation(anim);
            noData.setVisibility(View.VISIBLE);
            noDataLabel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);

        } else if (!show && emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.GONE);
            noData.setAnimation(anim);
            emptyView.setBackgroundResource(0);
            noDataLabel.setAnimation(anim);
            noData.setVisibility(View.GONE);
            noDataLabel.setVisibility(View.GONE);
            ColorDrawable bgColor = new ColorDrawable(Color.parseColor("#F9F9F9"));
            emptyView.setBackground(bgColor);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}




