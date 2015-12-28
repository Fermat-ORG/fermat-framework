package com.bitdubai.sub_app.wallet_factory.ui.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.adapters.InstalledWalletsAdapter;
import com.bitdubai.sub_app.wallet_factory.interfaces.PopupMenu;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.bitdubai.sub_app.wallet_factory.ui.dialogs.GetProjectNameDialog;
import com.bitdubai.sub_app.wallet_factory.utils.CommonLogger;

import java.util.ArrayList;

/**
 * Available projects fragment to clone
 *
 * @author Francisco Vasquez
 * @author Matias Furszy
 * @version 1.0
 */
public class AvailableProjectsFragment extends AbstractFermatFragment
        implements SwipeRefreshLayout.OnRefreshListener, OnMenuItemClickListener {

    private final String TAG = "FactoryProjects";

    /**
     * Manager
     */
    private WalletFactoryManager manager;

    /**
     * flags
     */
    private boolean isRefreshing;

    /**
     * View references
     */
    private View rootView;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private InstalledWalletsAdapter adapter;

    private ArrayList<InstalledWallet> dataSet;

    public static AbstractFermatFragment newInstance() {
        return new AvailableProjectsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.factory_available_projects_fragment, container, false);

        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(Color.BLUE, Color.BLUE);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new InstalledWalletsAdapter(getActivity());
        adapter.setMenuItemClickListener(new PopupMenu() {
            @Override
            public void onMenuItemClickListener(View menuView, Object project, int position) {
                projectToClone = (InstalledWallet) project;
                /*Showing up popup menu*/
                android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getActivity(), menuView);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu_installed_wallet, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(AvailableProjectsFragment.this);
                popupMenu.show();
            }
        });
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isRefreshing) {
            onRefresh();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setting up wallet manager
            setManager(((WalletFactorySubAppSession) appSession).getWalletFactoryManager());
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    public void setManager(WalletFactoryManager manager) {
        this.manager = manager;
    }

    public WalletFactoryManager getManager() {
        return manager;
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            swipeRefresh.setRefreshing(true);
            new FermatWorker(getActivity(), new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    isRefreshing = false;
                    if (isAttached) {
                        swipeRefresh.setRefreshing(false);
                        if (result != null && result.length > 0) {
                            dataSet = (ArrayList<InstalledWallet>) result[0];
                            adapter.changeDataSet(dataSet);
                            showEmpty();
                        }
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    isRefreshing = false;
                    if (isAttached) {
                        swipeRefresh.setRefreshing(false);
                        showEmpty();
                    }
                }
            }) {

                @Override
                protected Object doInBackground() throws Exception {
                    return manager.getInstalledWallets();
                }

            }.execute();
        }
    }

    private void showEmpty() {
        if (dataSet == null || dataSet.isEmpty()) {
            rootView.findViewById(R.id.empty).setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            rootView.findViewById(R.id.empty).setVisibility(View.VISIBLE);
        } else if (dataSet.size() > 0) {
            rootView.findViewById(R.id.empty).setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            rootView.findViewById(R.id.empty).setVisibility(View.GONE);
        }
    }

    private InstalledWallet projectToClone;

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_clone && projectToClone != null) {
            final GetProjectNameDialog getProjectName = new GetProjectNameDialog();
            getProjectName.setCancelable(true);
            getProjectName.setCallBack(new GetProjectNameDialog.DialogChooseNameListener() {
                @Override
                public void onCompleteInfo(final String name) {
                    getProjectName.dismiss();
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setCancelable(false);
                    dialog.setMessage("Please wait...");
                    dialog.show();
                    new FermatWorker(getActivity(), new FermatWorkerCallBack() {
                        @Override
                        public void onPostExecute(Object... result) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "The current project has been cloned...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onErrorOccurred(Exception ex) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Fermat has detected an exception", Toast.LENGTH_SHORT).show();
                            CommonLogger.exception(TAG, ex.getMessage(), ex);
                        }
                    }) {
                        @Override
                        protected Object doInBackground() throws Exception {
                            manager.cloneInstalledWallets(projectToClone, name);
                            projectToClone = null;
                            return true;
                        }
                    }.execute();
                }
            });
            getProjectName.show(getActivity().getFragmentManager(), null);
        }
        return false;
    }
}
