package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.adapters.ActorAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.interfaces.AdapterChangeListener;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Home Fragment
 */
public class HomeFragment extends FermatFragment implements SwipeRefreshLayout.OnRefreshListener {

    private AssetUserCommunitySubAppModuleManager manager;
    private List<Actor> actors;

    // recycler
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ActorAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    // flags
    private boolean isRefreshing = false;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            manager = ((AssetUserCommunitySubAppSession) subAppsSession).getManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ActorAdapter(getActivity());
        adapter.setAdapterChangeListener(new AdapterChangeListener<Actor>() {
            @Override
            public void onDataSetChanged(List<Actor> dataSet) {
                actors = dataSet;
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dap_community_user_home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (swipeRefresh != null)
            swipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_connect) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Connecting please wait...");
            dialog.setCancelable(false);
            dialog.show();
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    List<ActorAssetUser> toConnect = new ArrayList<>();
                    for (Actor actor : actors) {
                        if (actor.selected)
                            toConnect.add(actor);
                    }
                    //// TODO: 28/10/15 get Actor asset issuer
                    manager.connectToActorAssetUser(null, toConnect);
                    return true;
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @Override
                public void onPostExecute(Object... result) {
                    dialog.dismiss();
                    if (swipeRefresh != null)
                        swipeRefresh.post(new Runnable() {
                            @Override
                            public void run() {
                                onRefresh();
                            }
                        });
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), String.format("An exception has been thrown: %s", ex.getMessage()), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                            actors = (ArrayList<Actor>) result[0];
                            adapter.changeDataSet(actors);
                        }
                    }
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

    private synchronized List<Actor> getMoreData() throws Exception {
        List<Actor> dataSet = new ArrayList<>();
        List<AssetUserActorRecord> result = null;
        if (manager == null)
            throw new NullPointerException("AssetUserCommunitySubAppModuleManager is null");
        result = manager.getAllActorAssetUserRegistered();
        if (result != null && result.size() > 0) {
            for (AssetUserActorRecord record : result) {
                dataSet.add((new Actor(record)));
            }
        }
        return dataSet;
    }
}
