package org.fermat.fermat_dap_android_sub_app_asset_user_community.fragments;

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
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.adapters.UserCommunityAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.holders.UserViewHolder;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.interfaces.AdapterChangeListener;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Group;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.sessions.AssetUserCommunitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.sessions.SessionConstantsAssetUserCommunity;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupMemberRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * UserCommuinityUsersFragment, Shows all the users in current network not in the seleted group for adding
 *
 */
public class UserCommuinityUsersFragment extends AbstractFermatFragment implements SwipeRefreshLayout.OnRefreshListener {

    private AssetUserCommunitySubAppModuleManager moduleManager;
    AssetUserCommunitySubAppSession assetUserCommunitySubAppSession;
    AssetUserSettings settings = null;

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

//    SettingsManager<AssetUserSettings> settingsManager;

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

            assetUserCommunitySubAppSession = ((AssetUserCommunitySubAppSession) appSession);
            moduleManager = assetUserCommunitySubAppSession.getModuleManager();
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
        adapter = new UserCommunityAdapter(getActivity()) {
            @Override
            protected void bindHolder(UserViewHolder holder, Actor data, int position) {
                super.bindHolder(holder, data, position);
                holder.connect.setVisibility(View.VISIBLE);
            }
        };
        adapter.setAdapterChangeListener(new AdapterChangeListener<Actor>() {
            @Override
            public void onDataSetChanged(List<Actor> dataSet) {
                actors = dataSet;

                boolean someSelected = false;
                for (Actor actor : actors) {
                    if (actor.selected) {
                        someSelected = true;
                        break;
                    }
                }

                if (someSelected) {
                    menuItemAdd.setVisible(true);
                } else {
                    menuItemAdd.setVisible(false);
                }
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

//        settingsManager = appSession.getModuleManager().getSettingsManager();
//        AssetUserSettings settings = null;
        try {
            settings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }
        if (settings == null) {
            settings = new AssetUserSettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);
            settings.setNotificationEnabled(true);

            try {
                if (moduleManager != null) {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), settings);
                    moduleManager.setAppPublicKey(appSession.getAppPublicKey());
                }
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.add(0, SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_ADD_USERS, 0, "Add to group")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(1, SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_HELP_USERS, 0, "Help").setIcon(R.drawable.dap_community_user_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menuItemAdd = menu.getItem(0);
        menuItemAdd.setVisible(false);
    }

    private void setUpPresentation(boolean checkButton) {

        PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBannerRes(R.drawable.banner_asset_user_community)
                .setIconRes(R.drawable.asset_user_comunity)
                .setVIewColor(R.color.dap_community_user_view_color)
                .setTitleTextColor(R.color.dap_community_user_view_color)
                .setSubTitle(R.string.dap_user_community_group_users_subTitle)
                .setBody(R.string.dap_user_community_group_users_body)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setIsCheckEnabled(checkButton)
                .build();

        presentationDialog.show();

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

        if (moduleManager == null)
            throw new NullPointerException("AssetUserCommunitySubAppModuleManager is null");

        result = moduleManager.getAllActorAssetUserRegisteredWithCryptoAddressNotIntheGroup(group.getGroupId());

        if (result != null && result.size() > 0) {
            for (AssetUserActorRecord record : result) {
                    dataSet.add((new Actor(record)));
            }
        }
        return dataSet;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        try {
            if (id == SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_HELP_USERS) {
                setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }
            else if (id == SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_ADD_USERS) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Adding users to group...");
                dialog.setCancelable(false);
                dialog.show();
                FermatWorker worker = new FermatWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {

                        for (Actor actor : actors) {
                            if (actor.selected) {
                                AssetUserGroupMemberRecord actorGroup = new AssetUserGroupMemberRecord();
                                actorGroup.setGroupId(group.getGroupId());
                                actorGroup.setActorPublicKey(actor.getActorPublicKey());
                                moduleManager.addActorAssetUserToGroup(actorGroup);
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
                return false;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset User system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
