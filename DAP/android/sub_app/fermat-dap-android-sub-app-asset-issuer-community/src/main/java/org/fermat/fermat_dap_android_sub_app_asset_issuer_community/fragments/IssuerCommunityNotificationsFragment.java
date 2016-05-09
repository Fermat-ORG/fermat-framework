package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.adapters.IssuerCommunityNotificationAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.models.ActorIssuer;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.popup.AcceptDialog;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions.AssetIssuerCommunitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions.SessionConstantsAssetIssuerCommunity;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.AssetIssuerSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Nerio on 17/02/16.
 */
public class IssuerCommunityNotificationsFragment extends AbstractFermatFragment implements
        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<ActorIssuer> {

    public static final String ISSUER_SELECTED = "issuer";
    private static final int MAX = 20;
    //    protected final String TAG = "UserCommunityNotificationsFragment";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private IssuerCommunityNotificationAdapter adapter;
    private LinearLayout emptyView;
    private static AssetIssuerCommunitySubAppModuleManager manager;
    private AssetIssuerCommunitySubAppSession assetIssuerCommunitySubAppSession;

    private ErrorManager errorManager;
    private int offset = 0;
    private ActorIssuer actorInformation;
    private List<ActorIssuer> listActorInformation;
    //    private IntraUserLoginIdentity identity;
    private ProgressDialog dialog;

    SettingsManager<AssetIssuerSettings> settingsManager;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static IssuerCommunityNotificationsFragment newInstance() {
        return new IssuerCommunityNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        assetIssuerCommunitySubAppSession = ((AssetIssuerCommunitySubAppSession) appSession);

        manager = ((AssetIssuerCommunitySubAppSession) appSession).getModuleManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();

        actorInformation = (ActorIssuer) appSession.getData(ISSUER_SELECTED);

        errorManager = appSession.getErrorManager();
        listActorInformation = new ArrayList<>();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.dap_issuer_community_connections_notifications, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new IssuerCommunityNotificationAdapter(getActivity(), listActorInformation);
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#000b12"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);

            onRefresh();

        } catch (Exception ex) {
//            CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), R.string.dap_issuer_community_opps_system_error, Toast.LENGTH_SHORT).show();

        }


        return rootView;
    }

    private synchronized List<ActorIssuer> getMoreData() throws Exception {
        List<ActorIssuer> dataSet = new ArrayList<>();
        List<ActorAssetIssuer> result = null;

        try {
            if (manager == null)
                throw new NullPointerException("AssetIssuerCommunitySubAppModuleManager is null");

            if (manager.getActiveAssetIssuerIdentity() != null) {
                result = manager.getWaitingYourConnectionActorAssetIssuer(manager.getActiveAssetIssuerIdentity().getPublicKey(), MAX, offset);
                if (result != null && result.size() > 0) {
                    for (ActorAssetIssuer record : result) {
//                dataSet.add((new ActorIssuer(record)));
                        dataSet.add((new ActorIssuer((AssetIssuerActorRecord) record)));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetIdentityAssetUserException {

    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            if (swipeRefresh != null)
                swipeRefresh.setRefreshing(true);
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
                        if (getActivity() != null && adapter != null) {
                            listActorInformation = (ArrayList<ActorIssuer>) result[0];
                            adapter.changeDataSet(listActorInformation);
                            if (listActorInformation.isEmpty()) {
                                showEmpty(true, emptyView);
                            } else {
                                showEmpty(false, emptyView);
                            }
                        }
                    } else
                        showEmpty(true, emptyView);
//                    if (actors == null || actors.isEmpty() && getActivity() != null) // for test purpose only
//                        Toast.makeText(getActivity(), "There are no registered actors...", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
        }
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

        menu.add(1, SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_HELP_PRESENTATION, 0, R.string.help).setIcon(R.drawable.dap_community_issuer_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
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
            if (id == SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_COMMUNITY_NOTIFICATIONS) {
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

    /**
     * onItem click listener event
     *
     * @param data
     * @param position
     */
    @Override
    public void onItemClickListener(ActorIssuer data, int position) {
        try {
            AcceptDialog notificationAcceptDialog = new AcceptDialog(
                    getActivity(),
                    assetIssuerCommunitySubAppSession,
                    null,
                    data,
                    manager.getActiveAssetIssuerIdentity());

            notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = appSession.getData(SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_NOTIFICATIONS_ACCEPTED);
                    try {
                        if (o != null)
                            if ((Boolean) o) {
                                appSession.removeData(SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_NOTIFICATIONS_ACCEPTED);
                            }
                        onRefresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                        onRefresh();
                    }
                }
            });
            notificationAcceptDialog.show();

        } catch (CantGetIdentityAssetIssuerException e) {
            e.printStackTrace();
        }
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(ActorIssuer data, int position) {

    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case DAPConstants.DAP_UPDATE_VIEW_ANDROID:
                onRefresh();
                break;
            default:
                super.onUpdateViewOnUIThread(code);
        }
    }
}
