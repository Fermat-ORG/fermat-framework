package com.bitdubai.sub_app.wallet_factory.ui.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.adapters.WalletFactoryProjectsAdapter;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.bitdubai.sub_app.wallet_factory.utils.CommonLogger;
import com.bitdubai.sub_app.wallet_factory.utils.Utils;
import com.bitdubai.sub_app.wallet_factory.workers.GetAvailableProjectsAsync;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * Developer Projects Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class MainFragment extends AbstractFermatFragment
        implements FermatWorkerCallBack, SwipeRefreshLayout.OnRefreshListener {

    /**
     * CONSTANTS
     */
    private static final String TAG = "MainFactory";

    /**
     * THREAD POOL EXECUTOR
     */
    private ExecutorService executor;

    /**
     * MANAGER
     */
    private WalletFactoryManager manager;

    /**
     * DATA
     */
    private ArrayList<WalletFactoryProject> dataSet;

    /**
     * FLAGS
     */
    private boolean isRefreshing;

    /**
     * UI
     */
    private View rootView;
    private ProgressDialog dialog;

    private LinearLayout empty;
    private ActionButton createAction;

    private SwipeRefreshLayout swipe;
    private RecyclerView recycler;
    private LinearLayoutManager layoutManager;
    private WalletFactoryProjectsAdapter adapter;

    /**
     * Get new developer projects fragment instance
     *
     * @return DevProjectsFragment Instance
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((WalletFactorySubAppSession) appSession).getWalletFactoryManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.factory_developer_projects_fragment, container, false);
        // empty view
        empty = (LinearLayout) rootView.findViewById(R.id.empty);
        Utils.setVisibility(getActivity(), empty, false, false);
        //action button
        createAction = (ActionButton) rootView.findViewById(R.id.create);
        createAction.setVisibility(View.INVISIBLE);
        // swipe down to refresh
        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipe.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
        swipe.setOnRefreshListener(this);
        swipe.setRefreshing(false);
        isRefreshing = false;
        // recycler view
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        //layout manager
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        // adapter
        adapter = new WalletFactoryProjectsAdapter(getActivity());
        recycler.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get Available Projects Asynchronously
        showOrHideAction(false);
        isRefreshing = true;
        swipe.setRefreshing(true);
        executor = new GetAvailableProjectsAsync(getActivity(), this, manager).execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseExecutor();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseExecutor();
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            showOrHideAction(false);
            isRefreshing = true;
            swipe.setRefreshing(true);
            executor = new GetAvailableProjectsAsync(getActivity(), this, manager).execute();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onPostExecute(Object... result) {
        if (isAttached) {
            isRefreshing = false;
            if (swipe != null)
                swipe.setRefreshing(false);
            dismissDialog();
            if (result != null && result.length > 0) {
                dataSet = (ArrayList<WalletFactoryProject>) result[0];
                if (adapter != null)
                    adapter.changeDataSet(dataSet);
                checkEmptyDataSet();
            }
            showOrHideAction(true);
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        if (isAttached) {
            isRefreshing = false;
            if (swipe != null)
                swipe.setRefreshing(false);
            dismissDialog();
            checkEmptyDataSet();
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            showOrHideAction(true);
        }
    }

    /**
     * Release executor service
     */
    private void releaseExecutor() {
        if (executor != null)
            executor.shutdownNow();
        executor = null;
    }

    public void showOrHideAction(boolean show) {
        if (createAction == null)
            return;
        if (!isAttached)
            return;
        createAction.setAnimation(AnimationUtils.loadAnimation(getActivity(),
                show ? R.anim.fab_roll_from_right : R.anim.fab_roll_to_right));
        createAction.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * Analyze dataSet to show or hide empty view
     */
    public void checkEmptyDataSet() {
        if (isAttached) {
            if (dataSet == null || dataSet.isEmpty())
                Utils.setVisibility(getActivity(), empty, true, true);
            else if (dataSet.size() > 0)
                Utils.setVisibility(getActivity(), empty, false, true);
        }
    }

    /**
     * Show Progress Dialog
     *
     * @param cancelable Boolean
     */
    public void showDialog(boolean cancelable) {
        try {
            dismissDialog();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please wait...");
            dialog.setCancelable(cancelable);
            dialog.show();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    /**
     * Dismiss and release Progress Dialog in memory
     */
    public void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

}
