/*
 * @#MainFragment.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.sub_app.wallet_publisher.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.dmp_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.adapters.InformationPublishedComponentAdapter;
import com.bitdubai.sub_app.wallet_publisher.adapters.WalletFactoryProjectsAdapter;
import com.bitdubai.sub_app.wallet_publisher.interfaces.PopupMenu;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;
import com.bitdubai.sub_app.wallet_publisher.util.CommonLogger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment</code> is
 * the fragment ui that represent the main page on the wallet publisher.
 * <p/>
 * <p/>
 * Created by natalia on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 26/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MainFragment extends FermatListFragment<WalletFactoryProject>
        implements FermatListItemListeners<WalletFactoryProject>, OnMenuItemClickListener {

    /**
     * Represent the TAG
     */
    private static final String TAG = "WalletPublisher";

    /**
     * Represent the walletPublisherModuleManager
     */
    private WalletPublisherModuleManager walletPublisherModuleManager;

    /**
     * Represent Wallet Factory Projects objects
     */
    private ArrayList<WalletFactoryProject> projects;
    private WalletFactoryProject project;

    /**
     * Factory instance method
     *
     * @return MainFragment
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    /**
     * (no-javadoc)
     *
     * @see FermatFragment#onCreate(Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            CommonLogger.info(TAG, "Setting up WalletPublisherModule");
            /*
             * Get the module instance
             */
            walletPublisherModuleManager = ((WalletPublisherSubAppSession) subAppsSession).getWalletPublisherManager();
            /*Getting WFP */
            projects = (ArrayList<WalletFactoryProject>) walletPublisherModuleManager.getProjectsReadyToPublish();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
        //// TODO: 01/09/15  remove this block
        if (projects == null || projects.size() == 0)
            projects = getFakesProjects();
        CommonLogger.debug(TAG, String.format("Initial Projects ready to publish %d", projects != null ? projects.size() : 0));
    }

    /**
     * Use this function to setting up your custom views, focused on any view that is not the recycler view.
     *
     * @param layout View root
     */
    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        CommonLogger.info(TAG, "Setting up other views");
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.wallet_publisher_main_fragment;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.component_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new WalletFactoryProjectsAdapter(getActivity(), projects);
            adapter.setFermatListEventListener(this);
            ((WalletFactoryProjectsAdapter) adapter).setOnMenuClickListener(new PopupMenu() {
                @Override
                public void onMenuItemClickListener(View menuView, WalletFactoryProject project, int position) {
                    MainFragment.this.project = project;
                    /*Showing up popup menu*/
                    android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getActivity(), menuView);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.menu_factory_item, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(MainFragment.this);
                    popupMenu.show();
                }
            });
        }
        return adapter;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_more
                && project != null) {
            Toast.makeText(getActivity(), "Starting wizard to publish wallet: " + project.getName(), Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(getActivity(), "Starting wizard to publish...", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public void onItemClickListener(WalletFactoryProject data, int position) {
        Toast.makeText(getActivity(), "Item Clicked: " + data.getDescription(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClickListener(WalletFactoryProject data, int position) {
        // do nothing..
    }

    /* Fermat Worker CallBack Methods */

    /**
     * @param result array of native object (handle result field with result[0], result[1],... result[n]
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) { // -> this mean that this fragment is attached to he activity and it still active on the UI Thread
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                //informationPublishedComponentList = (ArrayList) result[0];
                if (adapter != null) {
                    //adapter.changeDataSet((ArrayList) informationPublishedComponentList);
                }
            }
        }
    }

    /**
     * @param ex Throwable object
     */
    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {// -> this mean that this fragment is attached to he activity and it still active on the UI Thread
            swipeRefreshLayout.setRefreshing(false);
            //todo: alert to the user that error was thrown and need to try again...
        }
    }

    @Override
    public ArrayList<WalletFactoryProject> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<WalletFactoryProject> items = null;

        return items; //todo: implement paging with refresh types and pos(values: 0 if the request is NEW and Last Item size if the request is OLD)
    }

    ArrayList<WalletFactoryProject> getFakesProjects() {
        ArrayList<WalletFactoryProject> items = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            final int pos = x;
            WalletFactoryProject tmp = new WalletFactoryProject() {
                @Override
                public String getProjectPublicKey() {
                    return null;
                }

                @Override
                public void setProjectPublickKey(String publickKey) {

                }

                @Override
                public String getName() {
                    return String.format("WFP#%d", pos);
                }

                @Override
                public void setName(String name) {

                }

                @Override
                public String getDescription() {
                    return String.format("Description #%d", pos);
                }

                @Override
                public void setDescription(String description) {

                }

                @Override
                public WalletType getWalletType() {
                    return null;
                }

                @Override
                public void setWalletType(WalletType walletType) {

                }

                @Override
                public WalletCategory getWalletCategory() {
                    return null;
                }

                @Override
                public void setWalletCategory(WalletCategory walletCategory) {

                }

                @Override
                public FactoryProjectType getFactoryProjectType() {
                    return FactoryProjectType.WALLET;
                }

                @Override
                public void setFactoryProjectType(FactoryProjectType factoryProjectType) {

                }

                @Override
                public WalletFactoryProjectState getProjectState() {
                    return null;
                }

                @Override
                public void setProjectState(WalletFactoryProjectState projectState) {

                }

                @Override
                public Timestamp getCreationTimestamp() {
                    return null;
                }

                @Override
                public void setCreationTimestamp(Timestamp timestamp) {

                }

                @Override
                public Timestamp getLastModificationTimestamp() {
                    return null;
                }

                @Override
                public void setLastModificationTimeststamp(Timestamp timestamp) {

                }

                @Override
                public int getSize() {
                    return 0;
                }

                @Override
                public void setSize(int size) {

                }

                @Override
                public Skin getDefaultSkin() {
                    return null;
                }

                @Override
                public void setDefaultSkin(Skin skin) {

                }

                @Override
                public List<Skin> getSkins() {
                    return null;
                }

                @Override
                public void setSkins(List<Skin> skins) {

                }


                @Override
                public Language getDefaultLanguage() {
                    return null;
                }

                @Override
                public void setDefaultLanguage(Language language) {

                }

                @Override
                public List<Language> getLanguages() {
                    return null;
                }

                @Override
                public void setLanguages(List<Language> languages) {

                }


                @Override
                public WalletNavigationStructure getNavigationStructure() {
                    return null;
                }

                @Override
                public void setNavigationStructure(WalletNavigationStructure navigationStructure) {

                }
            };
            items.add(tmp);
        }
        return items;
    }


}
// Publisher Identity
/*04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80*/
