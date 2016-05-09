package com.bitdubai.sub_app.crypto_customer_community.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.adapters.AppListAdapter;
import com.bitdubai.sub_app.crypto_customer_community.common.popups.ListIdentitiesDialog;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;
import com.bitdubai.sub_app.crypto_customer_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 02/02/2016.
 */
public class ConnectionsWorldFragment extends AbstractFermatFragment<CryptoCustomerCommunitySubAppSession, SubAppResourcesProviderManager> implements
        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<CryptoCustomerCommunityInformation> {

    //Constants
    public static final String ACTOR_SELECTED = "actor_selected";
    private static final int MAX = 20;
    protected final String TAG = "Recycler Base";

    //Managers
    private CryptoCustomerCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<CryptoCustomerCommunitySettings> settingsManager;

    //Data
    private CryptoCustomerCommunitySettings appSettings;
    private int offset = 0;
    private int mNotificationsCount = 0;
    private ArrayList<CryptoCustomerCommunityInformation> cryptoCustomerCommunityInformationList;

    //Flags
    private boolean isRefreshing = false;
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    //UI
    private View rootView;
    private LinearLayout emptyView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private AppListAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;


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
            settingsManager = moduleManager.getSettingsManager();
            moduleManager.setAppPublicKey(appSession.getAppPublicKey());


            //Obtain Settings or create new Settings if first time opening subApp
            appSettings = null;
            try {
                appSettings = this.settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
            }catch (Exception e){ appSettings = null; }

            if(appSettings == null){
                appSettings = new CryptoCustomerCommunitySettings();
                appSettings.setIsPresentationHelpEnabled(true);
                try {
                    settingsManager.persistSettings(appSession.getAppPublicKey(), appSettings);
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

            //Get notification requests count
            //mNotificationsCount = moduleManager.listCryptoCustomersPendingLocalAction(moduleManager.getSelectedActorIdentity(), MAX, offset).size();
            //mNotificationsCount = 4;
            //new FetchCountTask().execute();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, ex);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        moduleManager.setAppPublicKey(appSession.getAppPublicKey());

        try {
            rootView = inflater.inflate(R.layout.fragment_connections_world, container, false);

            //Set up RecyclerView
            layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
            adapter = new AppListAdapter(getActivity(), cryptoCustomerCommunityInformationList);
            adapter.setFermatListEventListener(this);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);

            //Set up swipeRefresher
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#000b12"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);


            if(launchActorCreationDialog) {
                PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                        .setBannerRes(R.drawable.banner_crypto_customer_wallet)
                        .setIconRes(R.drawable.crypto_customer)
                        .setSubTitle(R.string.cbp_ccc_launch_action_creation_dialog_sub_title)
                        .setBody(R.string.cbp_ccc_launch_action_creation_dialog_body)
                        .setTextFooter(R.string.cbp_ccc_launch_action_creation_dialog_footer)
                        .setTextNameLeft(R.string.cbp_ccc_launch_action_creation_name_left)
                        .setTextNameRight(R.string.cbp_ccc_launch_action_creation_name_right)
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
                ListIdentitiesDialog listIdentitiesDialog = new ListIdentitiesDialog(getActivity(), appSession, null);
                listIdentitiesDialog.show();
                listIdentitiesDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        invalidate();
                        onRefresh();
                    }
                });
                listIdentitiesDialog.show();
            }
            else
            {
                invalidate();
                onRefresh();
            }

        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
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
                            cryptoCustomerCommunityInformationList = (ArrayList<CryptoCustomerCommunityInformation>) result[0];
                            adapter.changeDataSet(cryptoCustomerCommunityInformationList);
                            if (cryptoCustomerCommunityInformationList.isEmpty()) {
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

    private synchronized List<CryptoCustomerCommunityInformation> getMoreData() {
        List<CryptoCustomerCommunityInformation> dataSet = new ArrayList<>();

        try {
            List<CryptoCustomerCommunityInformation> result = moduleManager.listWorldCryptoCustomers(moduleManager.getSelectedActorIdentity(), MAX, offset);
            dataSet.addAll(result);
            offset = dataSet.size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataSet;
    }


    @Override
    public void onItemClickListener(CryptoCustomerCommunityInformation data, int position) {
        appSession.setData(ACTOR_SELECTED, data);
        changeActivity(Activities.CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(CryptoCustomerCommunityInformation data, int position) {}





    /**
     * Simple AsyncTask to fetch the notifications count.
     */
//    class FetchCountTask extends AsyncTask<Void, Void, Integer> {
//
//        @Override
//        protected Integer doInBackground(Void... params) {
//            int notificationCount = 0;
//
//            try {
//                CryptoCustomerCommunitySelectableIdentity selectedIdentity = moduleManager.getSelectedActorIdentity();
//                notificationCount = moduleManager.listCryptoCustomersPendingLocalAction(selectedIdentity, MAX, offset).size();
//            } catch(CantListCryptoCustomersException | ActorIdentityNotSelectedException | CantGetSelectedActorIdentityException e){
//                errorManager.reportUnexpectedUIException(UISource.TASK, UnexpectedUIExceptionSeverity.NOT_IMPORTANT, e);
//            }
//            notificationCount = 80;
//            return notificationCount;
//        }
//
//        @Override
//        public void onPostExecute(Integer count) {
//            updateOptionMenuNotificationsBadge(count);
//        }
//    }

}




