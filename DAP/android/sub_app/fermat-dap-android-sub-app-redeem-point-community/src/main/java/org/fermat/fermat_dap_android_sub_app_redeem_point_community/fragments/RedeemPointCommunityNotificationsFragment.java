package org.fermat.fermat_dap_android_sub_app_redeem_point_community.fragments;

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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.adapters.RedeemPointCommunityNotificationAdapter;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.popup.AcceptDialog;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions.SessionConstantRedeemPointCommunity;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Nerio on 17/02/16.
 */
public class RedeemPointCommunityNotificationsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager>, ResourceProviderManager> implements
        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<Actor> {

    public static final String REDEEM_POINT_SELECTED = "redeemPoint";
    private static final int MAX = 20;
    //    protected final String TAG = "UserCommunityNotificationsFragment";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private boolean isRefreshing = false;
    private View rootView;
    private RedeemPointCommunityNotificationAdapter adapter;
    private LinearLayout emptyView;
    private RedeemPointCommunitySubAppModuleManager moduleManager;
    RedeemPointSettings settings = null;

    private ErrorManager errorManager;
    private int offset = 0;
    private Actor actorInformation;
    private List<Actor> listActorInformation;
    private ProgressDialog dialog;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RedeemPointCommunityNotificationsFragment newInstance() {
        return new RedeemPointCommunityNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        actorInformation = (Actor) appSession.getData(REDEEM_POINT_SELECTED);

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
            rootView = inflater.inflate(R.layout.dap_redeem_point_community_connections_notifications, container, false);
            setUpScreen(inflater);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new RedeemPointCommunityNotificationAdapter(getActivity(), listActorInformation);
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
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }


        return rootView;
    }

    private synchronized ArrayList<Actor> getMoreData() {
        ArrayList<Actor> dataSet = new ArrayList<>();
        List<ActorAssetRedeemPoint> result;

        try {
            if (moduleManager == null)
                throw new NullPointerException("AssetUserCommunitySubAppModuleManager is null");

            if (moduleManager.getActiveAssetRedeemPointIdentity() != null) {
                result = moduleManager.getWaitingYourConnectionActorAssetRedeem(moduleManager.getActiveAssetRedeemPointIdentity().getPublicKey(), MAX, offset);
                if (result != null && result.size() > 0) {
                    for (ActorAssetRedeemPoint record : result) {
                        dataSet.add((new Actor((RedeemPointActorRecord) record)));
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
            final ProgressDialog notificationsProgressDialog = new ProgressDialog(getActivity());
            notificationsProgressDialog.setMessage("Loading Notifications");
            notificationsProgressDialog.setCancelable(false);
            notificationsProgressDialog.show();
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
                    notificationsProgressDialog.dismiss();
                    isRefreshing = false;
                    if (swipeRefresh != null)
                        swipeRefresh.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            listActorInformation = (ArrayList<Actor>) result[0];
                            adapter.changeDataSet(listActorInformation);
                            if (listActorInformation.isEmpty()) {
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
                    notificationsProgressDialog.dismiss();
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
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);

//        menu.add(0, SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_NOTIFICATIONS, 0, "help").setIcon(R.drawable.dap_community_redeem_help_icon)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    private void setUpPresentation(boolean checkButton) {
        PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBannerRes(R.drawable.banner_redeem_point_community)
                .setIconRes(R.drawable.reddem_point_community)
                .setVIewColor(R.color.dap_community_redeem_view_color)
                .setTitleTextColor(R.color.dap_community_redeem_view_color)
                .setSubTitle(R.string.dap_redeem_community_welcome_subTitle)
                .setBody(R.string.dap_redeem_community_welcome_body)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setIsCheckEnabled(checkButton)
                .build();

        presentationDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        try {
            switch (id) {
                case 1://IC_ACTION_REDEEM_COMMUNITY_NOTIFICATIONS
                    setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }

//            if (id == SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_COMMUNITY_NOTIFICATIONS) {
//                setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Redeem Point system error",
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
    public void onItemClickListener(Actor data, int position) {
        try {

            AcceptDialog notificationAcceptDialog = new AcceptDialog(
                    getActivity(),
                    appSession,
                    null,
                    data,
                    moduleManager.getActiveAssetRedeemPointIdentity());

            notificationAcceptDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = appSession.getData(SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_NOTIFICATIONS_ACCEPTED);
                    try {
                        if (o != null)
                            if ((Boolean) o) {
                                appSession.removeData(SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_NOTIFICATIONS_ACCEPTED);
                            }
                        onRefresh();
                    } catch (Exception e) {
                        e.printStackTrace();
                        onRefresh();
                    }
                }
            });
            notificationAcceptDialog.show();

        } catch (CantGetIdentityRedeemPointException e) {
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
    public void onLongItemClickListener(Actor data, int position) {

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
