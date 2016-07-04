package org.fermat.fermat_dap_android_sub_app_asset_user_community.fragments;

import android.content.DialogInterface;
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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.software.shell.fab.ActionButton;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.adapters.UserCommunityAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.dialogs.ConfirmDeleteDialog;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.holders.UserViewHolder;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.interfaces.AdapterChangeListener;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Group;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.popup.CreateGroupFragmentDialog;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupMemberRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * UserCommuinityGroupUsersFragment, Show all the users in the selected group
 */
public class UserCommuinityGroupUsersFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetUserCommunitySubAppModuleManager>, ResourceProviderManager>
        implements SwipeRefreshLayout.OnRefreshListener {

    private AssetUserCommunitySubAppModuleManager moduleManager;
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
    private MenuItem menuItemDelete;
    private Menu menu;
    private CreateGroupFragmentDialog dialog;

    /**
     * Flags
     */
    private boolean isRefreshing = false;
    private int menuItemSize;

    public static UserCommuinityGroupUsersFragment newInstance() {
        return new UserCommuinityGroupUsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            group = (Group) appSession.getData("group_selected");

            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.group_user_fragment, container, false);

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

                try {
                    if (someSelected) {
                        changeOptionMenuVisibility(menuItemDelete.getItemId(), true, true);
//                        menuItemDelete.setVisible(true);
                    } else {
                        changeOptionMenuVisibility(menuItemDelete.getItemId(), false, true);
//                        menuItemDelete.setVisible(false);
                    }
                } catch (InvalidParameterException e) {
                    e.printStackTrace();
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
        onRefresh();

        ActionButton create = (ActionButton) rootView.findViewById(R.id.add_users_group);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appSession.setData("group_selected", group);
                changeActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_USERS.getCode(), appSession.getAppPublicKey());

            }
        });
        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
        create.setVisibility(View.VISIBLE);

        //Toast.makeText(getActivity(), group.getGroupName(), Toast.LENGTH_LONG).show();
        getToolbar().setTitle(group.getGroupName() + " Users");
        //getToolbar().getMenu().getItem(1).setVisible(true);

        //initialize settings
//        settingsManager = appSession.getModuleManager().getSettingsManager();
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
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);

        if (menuItemSize == 0 || menuItemSize == menu.size()) {
            menuItemSize = menu.size();

            try {
                menuItemDelete = menu.findItem(2);
                changeOptionMenuVisibility(menuItemDelete.getItemId(), false, true);
//            menuItemDelete.setVisible(false);
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        try {
            ConfirmDeleteDialog dialog;
            switch (id) {
                case 1://IC_ACTION_USER_COMMUNITY_HELP_GROUP_RENAME
                    lauchCreateGroupDialog();
                    break;
                case 2://IC_ACTION_USER_COMMUNITY_HELP_GROUP_DELETE
                    appSession.setData("group_ID", group.getGroupId());
                    dialog = new ConfirmDeleteDialog(getActivity(), appSession, appResourcesProviderManager);
                    dialog.setYesBtnListener(new ConfirmDeleteDialog.OnClickAcceptListener() {
                        @Override
                        public void onClick() {
                            String groupSelectedID;
                            try {
                                groupSelectedID = (String) appSession.getData("group_ID");
                                List<ActorAssetUser> userList = moduleManager.getListActorAssetUserByGroups(groupSelectedID);
                                for (ActorAssetUser user : userList) {

                                    AssetUserGroupMemberRecord actorGroup = new AssetUserGroupMemberRecord();
                                    actorGroup.setGroupId(groupSelectedID);
                                    actorGroup.setActorPublicKey(user.getActorPublicKey());
                                    moduleManager.removeActorAssetUserFromGroup(actorGroup);

                                }

                                moduleManager.deleteGroup(groupSelectedID);
                                Toast.makeText(getActivity(), "Group deleted.", Toast.LENGTH_SHORT).show();
                                changeActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN, appSession.getAppPublicKey());
                            } catch (CantDeleteAssetUserGroupException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "This group couldn't be deleted.", Toast.LENGTH_SHORT).show();
                            } catch (RecordsNotFoundException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Group not found.", Toast.LENGTH_SHORT).show();
                            } catch (CantGetAssetUserActorsException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Can't get users from group.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                    break;
                case 3://IC_ACTION_USER_COMMUNITY_HELP_GROUP_DELETE_MEMBERS
                    dialog = new ConfirmDeleteDialog(getActivity(), appSession, appResourcesProviderManager);
                    dialog.setYesBtnListener(new ConfirmDeleteDialog.OnClickAcceptListener() {
                        @Override
                        public void onClick() {
                            try {
                                for (Actor actor : actors) {
                                    if (actor.selected) {
                                        AssetUserGroupMemberRecord actorGroup = new AssetUserGroupMemberRecord();
                                        actorGroup.setGroupId(group.getGroupId());
                                        actorGroup.setActorPublicKey(actor.getActorPublicKey());
                                        moduleManager.removeActorAssetUserFromGroup(actorGroup);
                                    }
                                }
                                Toast.makeText(getActivity(), "Selected users deleted from the group", Toast.LENGTH_SHORT).show();
                                onRefresh();
//                                menuItemDelete.setVisible(false);
                                changeOptionMenuVisibility(menuItemDelete.getItemId(), false, true);
                            } catch (CantDeleteAssetUserGroupException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "This users couldn't be deleted.", Toast.LENGTH_SHORT).show();
                            } catch (RecordsNotFoundException e) {
                                Toast.makeText(getActivity(), "Records not found.", Toast.LENGTH_SHORT).show();
                            } catch (InvalidParameterException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.show();
                    break;
                case 4://IC_ACTION_USER_COMMUNITY_HELP_GROUP
                    setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }

//            if (id == SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_HELP_GROUP) {
//                setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            } else {
//                if (item.getItemId() == SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_HELP_GROUP_RENAME) {
//                    lauchCreateGroupDialog();
//                } else if (item.getItemId() == SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_HELP_GROUP_DELETE) {
//                    appSession.setData("group_ID", group.getGroupId());
//                    ConfirmDeleteDialog dialog = new ConfirmDeleteDialog(getActivity(), appSession, appResourcesProviderManager);
//                    dialog.setYesBtnListener(new ConfirmDeleteDialog.OnClickAcceptListener() {
//                        @Override
//                        public void onClick() {
//                            String groupSelectedID;
//                            try {
//                                groupSelectedID = (String) appSession.getData("group_ID");
//                                List<ActorAssetUser> userList = moduleManager.getListActorAssetUserByGroups(groupSelectedID);
//                                for (ActorAssetUser user : userList) {
//
//                                    AssetUserGroupMemberRecord actorGroup = new AssetUserGroupMemberRecord();
//                                    actorGroup.setGroupId(groupSelectedID);
//                                    actorGroup.setActorPublicKey(user.getActorPublicKey());
//                                    moduleManager.removeActorAssetUserFromGroup(actorGroup);
//
//                                }
//
//                                moduleManager.deleteGroup(groupSelectedID);
//                                Toast.makeText(getActivity(), "Group deleted.", Toast.LENGTH_SHORT).show();
//                                changeActivity(Activities.DAP_ASSET_USER_COMMUNITY_ACTIVITY_ADMINISTRATIVE_GROUP_MAIN, appSession.getAppPublicKey());
//                            } catch (CantDeleteAssetUserGroupException e) {
//                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "This group couldn't be deleted.", Toast.LENGTH_SHORT).show();
//                            } catch (RecordsNotFoundException e) {
//                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "Group not found.", Toast.LENGTH_SHORT).show();
//                            } catch (CantGetAssetUserActorsException e) {
//                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "Can't get users from group.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    dialog.show();
//
//                } else if (item.getItemId() == SessionConstantsAssetUserCommunity.IC_ACTION_USER_COMMUNITY_HELP_GROUP_DELETE_MEMBERS) {
//                    ConfirmDeleteDialog dialog = new ConfirmDeleteDialog(getActivity(), appSession, appResourcesProviderManager);
//                    dialog.setYesBtnListener(new ConfirmDeleteDialog.OnClickAcceptListener() {
//                        @Override
//                        public void onClick() {
//                            try {
//                                for (Actor actor : actors) {
//                                    if (actor.selected) {
//                                        AssetUserGroupMemberRecord actorGroup = new AssetUserGroupMemberRecord();
//                                        actorGroup.setGroupId(group.getGroupId());
//                                        actorGroup.setActorPublicKey(actor.getActorPublicKey());
//                                        moduleManager.removeActorAssetUserFromGroup(actorGroup);
//                                    }
//                                }
//                                Toast.makeText(getActivity(), "Selected users deleted from the group", Toast.LENGTH_SHORT).show();
//                                onRefresh();
//                                menuItemDelete.setVisible(false);
//                            } catch (CantDeleteAssetUserGroupException e) {
//                                e.printStackTrace();
//                                Toast.makeText(getActivity(), "This users couldn't be deleted.", Toast.LENGTH_SHORT).show();
//                            } catch (RecordsNotFoundException e) {
//                                Toast.makeText(getActivity(), "Records not found.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    dialog.show();
//
//                }
//            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset User system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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

    private void lauchCreateGroupDialog() {
        dialog = new CreateGroupFragmentDialog(
                getActivity(), moduleManager, group);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getToolbar().setTitle(group.getGroupName() + " Users");
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
        List<ActorAssetUser> resultAux = null;
        if (moduleManager == null)
            throw new NullPointerException("AssetUserCommunitySubAppModuleManager is null");

        resultAux = moduleManager.getListActorAssetUserByGroups(group.getGroupId());


        if (resultAux != null && resultAux.size() > 0) {
            for (ActorAssetUser record : resultAux) {

                dataSet.add((new Actor((AssetUserActorRecord) record)));
            }
        }
        return dataSet;
    }
}
