package com.bitdubai.sub_app_artist_community.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHandleNewsEventException;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.enums.ArtistActorConnectionNotificationType;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.enums.FanActorConnectionNotificationType;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySubAppModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.settings.ArtistCommunitySettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.artist_community.R;
import com.bitdubai.sub_app_artist_community.adapters.AppListAdapter;
import com.bitdubai.sub_app_artist_community.commons.popups.ListIdentitiesDialog;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSessionReferenceApp;
import com.bitdubai.sub_app_artist_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ConnectionsWorldFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ArtistCommunitySubAppModuleManager>, SubAppResourcesProviderManager> implements
        SwipeRefreshLayout.OnRefreshListener, FermatListItemListeners<ArtistCommunityInformation> {

    //Constants
    public static final String ACTOR_SELECTED = "actor_selected";
    private static final int MAX = 20;
    protected final String TAG = "Recycler Base";

    //Managers
    private ArtistCommunitySubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    //private SettingsManager<ArtistCommunitySettings> settingsManager;

    //Data
    private ArtistCommunitySettings appSettings;
    private int offset = 0;
    private int mNotificationsCount = 0;
    private ArrayList<ArtistCommunityInformation> artistCommunityInformationList;

    //Flags
    private boolean isRefreshing = false;
    private boolean launchActorCreationDialog = false;
    private boolean launchListIdentitiesDialog = false;

    //UI
    private View rootView;
    private LinearLayout emptyView;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
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
            //settingsManager = moduleManager.getSettingsManager();
            moduleManager.setAppPublicKey(appSession.getAppPublicKey());


            //Obtain Settings or create new Settings if first time opening subApp
            appSettings = null;
            try {
                if (appSession.getAppPublicKey()!= null){
                    appSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
                }else{
                    appSettings = moduleManager.loadAndGetSettings("sub_app_art_artist_community");
                }

            } catch (Exception e) {
                appSettings = null;
            }

            if (appSettings == null) {
                appSettings = new ArtistCommunitySettings();
                appSettings.setIsPresentationHelpEnabled(false);
                if(moduleManager != null){
                    if (appSession.getAppPublicKey()!=null){
                        moduleManager.persistSettings(appSession.getAppPublicKey(), appSettings);
                    }else{
                        moduleManager.persistSettings("sub_app_art_artist_community", appSettings);
                    }
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

            if(moduleManager!=null){
                //Get notification requests count
                ArtistCommunitySelectableIdentity artistCommunitySelectableIdentity=
                        moduleManager.getSelectedActorIdentity();
                if(artistCommunitySelectableIdentity!=null){
                    List<ArtistCommunityInformation> artistCommunityInformation =
                            moduleManager.listArtistsPendingLocalAction(
                                    artistCommunitySelectableIdentity,
                                    MAX,
                                    offset);
                    if(artistCommunityInformation!=null){
                        mNotificationsCount = artistCommunityInformation
                                .size();
                        //mNotificationsCount = 4;
                        new FetchCountTask().execute();
                    }
                }
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
            rootView = inflater.inflate(R.layout.aac_fragment_connections_world, container, false);

            //Set up RecyclerView
            layoutManager  = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            adapter = new AppListAdapter(getActivity(), artistCommunityInformationList);
            adapter.setFermatListEventListener(this);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.aac_gridView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);

            //Set up swipeRefresher
            swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.aac_swipe);
            swipeRefresh.setOnRefreshListener(this);
            swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

            rootView.setBackgroundColor(Color.parseColor("#363636"));
            emptyView = (LinearLayout) rootView.findViewById(R.id.aac_empty_view);


            if(launchActorCreationDialog) {
                PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                        .setBannerRes(R.drawable.banner_artist_community)
                        .setIconRes(R.drawable.artist)
                        .setSubTitle(R.string.art_aac_launch_action_creation_dialog_sub_title)
                        .setBody(R.string.art_aac_launch_action_creation_dialog_body)
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

        configureToolbar();
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
                            artistCommunityInformationList = (ArrayList<ArtistCommunityInformation>) result[0];
                            adapter.changeDataSet(artistCommunityInformationList);
                            if (artistCommunityInformationList.isEmpty()) {
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

    private synchronized List<ArtistCommunityInformation> getMoreData() {
        List<ArtistCommunityInformation> dataSet = new ArrayList<>();

        try {
            ArtistCommunitySelectableIdentity artistCommunitySelectableIdentity = moduleManager.getSelectedActorIdentity();
            if(!Validate.isObjectNull(artistCommunitySelectableIdentity)){
                List<ArtistCommunityInformation> result = moduleManager.listWorldArtists(artistCommunitySelectableIdentity, MAX, offset);
                dataSet.addAll(result);
                offset = dataSet.size();
            }
            //I'll check all the connections
            moduleManager.checkAllConnections();
        } catch (CantGetSelectedActorIdentityException e){
            //There are no identities in device
            //Nothing to do here.
        } catch (CantHandleNewsEventException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
        }

        return dataSet;
    }

    @Override
    public void onItemClickListener(ArtistCommunityInformation data, int position) {
        appSession.setData(ACTOR_SELECTED, data);
        changeActivity(Activities.ART_SUB_APP_ARTIST_COMMUNITY_CONNECTION_OTHER_PROFILE.getCode(), appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(ArtistCommunityInformation data, int position) {}

    /*
    Sample AsyncTask to fetch the notifications count
    */
    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            return mNotificationsCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;
        getActivity().invalidateOptionsMenu();
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.degrade_colorj, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.degrade_colorj));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    @Override
    public void onUpdateViewOnUIThread(String code){
        try
        {
            //update intra user list
            if(code.equals(ArtistActorConnectionNotificationType.ACTOR_CONNECTED.getCode())){
                invalidate();
                onRefresh();
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}




