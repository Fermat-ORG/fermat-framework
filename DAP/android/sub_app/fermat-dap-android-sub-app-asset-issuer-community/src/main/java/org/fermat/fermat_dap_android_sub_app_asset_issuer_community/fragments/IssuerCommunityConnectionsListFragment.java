package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.LinearLayout;
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
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.adapters.IssuerConnectionsListAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.models.ActorIssuer;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions.AssetIssuerCommunitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions.SessionConstantsAssetIssuerCommunity;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.exceptions.CantGetActorAssetIssuerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;


import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Penelope Quintero
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class IssuerCommunityConnectionsListFragment extends AbstractFermatFragment implements SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<ActorIssuer> {

    public static final String ISSUER_SELECTED = "issuer";
    private static final int MAX = 20;
    protected final String TAG = "ConnectionNotificationsFragment";
    private int offset = 0;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout empty;
    private boolean isRefreshing = false;
    private View rootView;
    private IssuerConnectionsListAdapter adapter;
    private AssetIssuerCommunitySubAppSession actorIssuerSubAppSession;
    private LinearLayout emptyView;
    private static AssetIssuerCommunitySubAppModuleManager manager;
    private ErrorManager errorManager;
    private List<ActorIssuer> lstActorIssuers;
    SettingsManager<AssetIssuerSettings> settingsManager;

    public static IssuerCommunityConnectionsListFragment newInstance() {
        return new IssuerCommunityConnectionsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        actorIssuerSubAppSession = ((AssetIssuerCommunitySubAppSession) appSession);
        manager = actorIssuerSubAppSession.getModuleManager();
        errorManager = appSession.getErrorManager();
        settingsManager = appSession.getModuleManager().getSettingsManager();
        lstActorIssuers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.dap_issuer_community_fragment_connections_list, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new IssuerConnectionsListAdapter(getActivity(), lstActorIssuers);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);
            onRefresh();
        } catch (Exception ex) {
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), R.string.dap_issuer_community_opps_system_error, Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_PRESENTATION, 0, R.string.help).setIcon(R.drawable.dap_community_issuer_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetActorAssetIssuerException {
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            final ProgressDialog connectionsProgressDialog = new ProgressDialog(getActivity());
            connectionsProgressDialog.setMessage(getString(R.string.loading_connections));
            connectionsProgressDialog.setCancelable(false);
            connectionsProgressDialog.show();
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
                    connectionsProgressDialog.dismiss();
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            lstActorIssuers = (ArrayList<ActorIssuer>) result[0];
                            adapter.changeDataSet(lstActorIssuers);
                            if (lstActorIssuers.isEmpty()) {
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
                    connectionsProgressDialog.dismiss();
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

    private synchronized List<ActorIssuer> getMoreData() {
        List<ActorIssuer> dataSet = new ArrayList<>();

        List<ActorAssetIssuer> result;
        try {
            if (manager == null)
                throw new NullPointerException("AssetIssuerCommunitySubAppModuleManager is null");

            result = manager.getAllActorAssetIssuerConnected();
            if (result != null && result.size() > 0) {
                for (ActorAssetIssuer record : result) {
                    dataSet.add((new ActorIssuer((AssetIssuerActorRecord) record)));
                }
            }
        } catch (CantGetAssetIssuerActorsException e) {
            e.printStackTrace();
        }
        return dataSet;
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
    private void setUpPresentation(boolean checkButton) {
        PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBannerRes(R.drawable.banner_asset_issuer_community)
                .setIconRes(R.drawable.asset_issuer_comunity)
                .setVIewColor(R.color.dap_community_issuer_view_color)
                .setTitleTextColor(R.color.dap_community_issuer_view_color)
                .setSubTitle(R.string.dap_issuer_community_welcome_subTitle)
                .setBody(R.string.dap_issuer_community_welcome_body)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setIsCheckEnabled(checkButton)
                .build();
        presentationDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        try {
            if (id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_PRESENTATION) {
                setUpPresentation(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_issuer_community_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClickListener(ActorIssuer data, int position) {
        appSession.setData(ISSUER_SELECTED, data);
        changeActivity(Activities.DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(ActorIssuer data, int position) {

    }
}
