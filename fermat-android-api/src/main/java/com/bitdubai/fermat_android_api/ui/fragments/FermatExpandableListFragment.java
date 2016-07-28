package com.bitdubai.fermat_android_api.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentListItem;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.interfaces.RecyclerExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nelson on 28/10/15.
 */
public abstract class FermatExpandableListFragment<M extends ParentListItem> extends AbstractFermatFragment
        implements RecyclerExpandableListFragment, SwipeRefreshLayout.OnRefreshListener, FermatWorkerCallBack {

    /**
     * CONSTANTS
     */
    protected final String TAG = "ExpandableListFragment";
    /**
     * FLAGS
     */
    protected boolean isRefreshing;
    /**
     * UI
     */
    protected RecyclerView recyclerView;
    protected ExpandableRecyclerAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected SwipeRefreshLayout swipeRefreshLayout;
    /**
     * Executor
     */
    private ExecutorService _executor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasMenu());
        _executor = Executors.newFixedThreadPool(2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (_executor != null) {
            _executor.shutdown();
            _executor = null;
        }
    }

    /**
     * Determine if this fragment use menu
     *
     * @return true if this fragment has menu, otherwise false
     */
    protected abstract boolean hasMenu();

    /**
     * Get layout resource
     *
     * @return int layout resource Ex: R.layout.fragment_view
     */
    protected abstract int getLayoutResource();

    protected abstract int getSwipeRefreshLayoutId();

    protected abstract int getRecyclerLayoutId();

    protected abstract boolean recyclerHasFixedSize();

    /**
     * <p>Setup views with layout root view
     * Override this function and write the code after call super.initViews(layout) method if you
     * want to initializer your others views reference on your own class derived of this
     * base class<p/>
     *
     * @param layout View root
     */
    protected void initViews(View layout) {
        Log.i(TAG, "recycler view setup");
        if (layout == null)
            return;
        recyclerView = (RecyclerView) layout.findViewById(getRecyclerLayoutId());
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(recyclerHasFixedSize());
            layoutManager = getLayoutManager();
            if (layoutManager != null)
                recyclerView.setLayoutManager(layoutManager);
            adapter = getAdapter();
            if (adapter != null) {
                recyclerView.setAdapter(adapter);
            }
            swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(getSwipeRefreshLayoutId());
            if (swipeRefreshLayout != null) {
                isRefreshing = false;
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
                swipeRefreshLayout.setOnRefreshListener(this);
            }
        }
    }

    /**
     * Get executor service to run threads out side of main thread.
     *
     * @return Fixed Thread Pool Executor (Max Thread per fragment <b>3</b>
     */
    protected ExecutorService getExecutor() {
        return this._executor;
    }

    /**
     * Implement this function for get more data asynchronously.
     * <b>WARNING: DO NOT CALL UI REFERENCES INSIDE THIS METHOD THIS WILL CAUSE IllegalStateException</b>
     *
     * @param refreshType Fermat Refresh Enum Type
     */
    public abstract List<M> getMoreDataAsync(FermatRefreshTypes refreshType, int pos);

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            FermatWorker worker = new FermatWorker(getActivity(), this) {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreDataAsync(FermatRefreshTypes.NEW, 0);
                }
            };
            worker.execute(getExecutor());
        }
    }
}
