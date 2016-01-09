package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.adapters.GroupCommunityAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.adapters.UserCommunityAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.interfaces.AdapterChangeListener;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Group;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.popup.CreateGroupFragmentDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nerio on 06/01/16.
 */
public class UserCommunityGroupFragment extends AbstractFermatFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private static AssetUserCommunitySubAppModuleManager manager;
    private static final int MAX = 20;

    private List<Group> groups;
    ErrorManager errorManager;

    // recycler
    private final String TAG = "DapMain";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private GroupCommunityAdapter adapter;
    private View rootView;
    private LinearLayout emptyView;
    private int offset = 0;
    private CreateGroupFragmentDialog dialog;

    /**
     * Flags
     */
    private boolean isRefreshing = false;

    public static UserCommunityGroupFragment newInstance() {
        return new UserCommunityGroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            manager = ((AssetUserCommunitySubAppSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.group_fragment, container, false);
//        initViews(rootView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.gridViewGroup);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupCommunityAdapter(getActivity());
        adapter.setAdapterChangeListener(new AdapterChangeListener<Group>() {
            @Override
            public void onDataSetChanged(List<Group> dataSet) {
                groups = dataSet;
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_group);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE);

        rootView.setBackgroundColor(Color.parseColor("#000b12"));
        emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view_group);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();

        ActionButton create = (ActionButton) rootView.findViewById(R.id.create_group);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* create new asset factory project */
//                selectedAsset = null;
//                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), appSession.getAppPublicKey(), getAssetForEdit());
                lauchCreateGroupDialog();
            }
        });
        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
        create.setVisibility(View.VISIBLE);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dap_community_user_home_menu, menu);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        selectedAsset = null;
    }

    protected void initViews(View layout) {
        Log.i(TAG, "recycler view setup");
        if (layout == null)
            return;
        recyclerView = (RecyclerView) layout.findViewById(R.id.gridViewGroup);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            adapter = new GroupCommunityAdapter(getActivity());
            adapter.setAdapterChangeListener(new AdapterChangeListener<Group>() {
                @Override
                public void onDataSetChanged(List<Group> dataSet) {
                    groups = dataSet;
                }
            });
//            adapter = new AssetFactoryAdapter(getActivity());
//            adapter.setMenuItemClick(new PopupMenu() {
//                @Override
//                public void onMenuItemClickListener(View menuView, AssetFactory project, int position) {
//                    selectedAsset = project;
//                    /*Showing up popup menu*/
//                    android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getActivity(), menuView);
//                    MenuInflater inflater = popupMenu.getMenuInflater();
//                    inflater.inflate(R.menu.asset_factory_main, popupMenu.getMenu());
//                    try {
//                        if (!manager.isReadyToPublish(selectedAsset.getAssetPublicKey())) {
//                            popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
//                        }
//                    } catch (CantPublishAssetFactoy cantPublishAssetFactoy) {
//                        cantPublishAssetFactoy.printStackTrace();
//                        popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
//                    }
//                    popupMenu.setOnMenuItemClickListener(EditableAssetsFragment.this);
//                    popupMenu.show();
//                }
//            });
            recyclerView.setAdapter(adapter);

        }
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_group);
        if (swipeRefreshLayout != null) {
            isRefreshing = false;
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
            swipeRefreshLayout.setOnRefreshListener(this);
        }

        // fab action button create
        ActionButton create = (ActionButton) layout.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* create new asset factory project */
//                selectedAsset = null;
//                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), appSession.getAppPublicKey(), getAssetForEdit());
                lauchCreateGroupDialog();
            }
        });
        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
        create.setVisibility(View.VISIBLE);
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

    private void lauchCreateGroupDialog(){
        dialog = new CreateGroupFragmentDialog(
                getActivity(),manager);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                onRefresh();
            }
        });
        dialog.show();
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(true);
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
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        if (getActivity() != null && adapter != null) {
                            groups = (ArrayList<Group>) result[0];
                            adapter.changeDataSet(groups);
                            if (groups.isEmpty()) {
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
                    isRefreshing = false;
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
        }
    }

    private synchronized List<Group> getMoreData() throws Exception {
        List<Group> dataSet = new ArrayList<>();
        List<ActorAssetUserGroup> result = null;
        if (manager == null)
            throw new NullPointerException("AssetUserCommunitySubAppModuleManager is null");
        result = manager.getGroups();
        if (result != null && result.size() > 0) {
            for (ActorAssetUserGroup record : result) {
                dataSet.add((new Group(record)));
            }
        }
        return dataSet;
    }

}
