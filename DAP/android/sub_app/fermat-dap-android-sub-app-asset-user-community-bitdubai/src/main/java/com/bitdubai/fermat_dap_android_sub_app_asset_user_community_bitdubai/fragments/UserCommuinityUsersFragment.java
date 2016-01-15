package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.adapters.UserCommunityAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.interfaces.AdapterChangeListener;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Group;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupMemberRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Home Fragment
 */
public class UserCommuinityUsersFragment extends AbstractFermatFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static AssetUserCommunitySubAppModuleManager manager;
    private static final int MAX = 20;

    private List<Actor> actors;
    ErrorManager errorManager;
    private Group group;

    // recycler
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private UserCommunityAdapter adapter;
    private View rootView;
    private LinearLayout emptyView;
    private int offset = 0;
    private MenuItem menuItemAdd;
    private Menu menu;

    /**
     * Flags
     */
    private boolean isRefreshing = false;

    public static UserCommuinityUsersFragment newInstance() {
        return new UserCommuinityUsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            group = (Group) appSession.getData("group_selected");
            manager = ((AssetUserCommunitySubAppSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.users_fragment, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.gridView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserCommunityAdapter(getActivity());
        adapter.setAdapterChangeListener(new AdapterChangeListener<Actor>() {
            @Override
            public void onDataSetChanged(List<Actor> dataSet) {
                actors = dataSet;

                boolean someSelected = false;
                for (Actor actor : actors){
                    if (actor.selected){
                        someSelected = true;
                        break;
                    }
                }

                if (someSelected)
                {
                    menuItemAdd.setVisible(true);
                }
                else {menuItemAdd.setVisible(false);}
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE);

        rootView.setBackgroundColor(Color.parseColor("#000b12"));
        emptyView = (LinearLayout) rootView.findViewById(R.id.empty_view);
        swipeRefreshLayout.setRefreshing(true);

        getToolbar().setTitle("Add users to " + group.getGroupName());
        onRefresh();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dap_community_users_menu, menu);
        this.menu = menu;
        menuItemAdd = menu.findItem(R.id.action_add_to_group);
        menuItemAdd.setVisible(false);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_group) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Adding users to group...");
            dialog.setCancelable(false);
            dialog.show();
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {

                    for (Actor actor : actors) {
                        if (actor.selected)
                        {   AssetUserGroupMemberRecord actorGroup = new AssetUserGroupMemberRecord();
                            actorGroup.setGroupId(group.getGroupId());
                            actorGroup.setActorPublicKey(actor.getActorPublicKey());
                            manager.addActorAssetUserToGroup(actorGroup);
                        }
                    }

                    return true;
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @Override
                public void onPostExecute(Object... result) {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Selected users added to the group", Toast.LENGTH_SHORT).show();
                    appSession.setData("group_selected", group);
                    changeActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_USERS_FRAGMENT, appSession.getAppPublicKey());
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

    private void setUpScreen(LayoutInflater layoutInflater) throws CantGetIdentityAssetUserException {

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
                            actors = (ArrayList<Actor>) result[0];
                            adapter.changeDataSet(actors);
                            if (actors.isEmpty()) {
                                showEmpty(true, emptyView);
                            } else {
                                showEmpty(false, emptyView);
                            }
                        }
                    } else {
                        showEmpty(true, emptyView);
                    }
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

    private synchronized List<Actor> getMoreData() throws Exception {
        List<Actor> dataSet = new ArrayList<>();
        List<AssetUserActorRecord> result = null;
        List<ActorAssetUser> usersInGroup = null;

        if (manager == null)
            throw new NullPointerException("AssetUserCommunitySubAppModuleManager is null");
        result = manager.getAllActorAssetUserRegistered();
        usersInGroup = manager.getListActorAssetUserByGroups(group.getGroupName());
        if (result != null && result.size() > 0) {
            for (AssetUserActorRecord record : result) {
                if (!userInGroup(record.getActorPublicKey(),usersInGroup))
                {
                    dataSet.add((new Actor(record)));
                }

            }
        }
        return dataSet;
    }

    private boolean userInGroup(String actorPublicKey, List<ActorAssetUser> usersInGroup) {
        for (ActorAssetUser record : usersInGroup) {
            if (record.getActorPublicKey().equals(actorPublicKey))
            {
                return true;
            }
        }
        return false;
    }
}
