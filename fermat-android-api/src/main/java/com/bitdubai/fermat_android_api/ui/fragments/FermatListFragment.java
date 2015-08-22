package com.bitdubai.fermat_android_api.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.RecyclerListFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RecyclerView Fragment
 */
public abstract class FermatListFragment extends Fragment implements RecyclerListFragment {

    protected final String TAG = "Recycler Base";
    /**
     * FLAGS
     */
    protected boolean isAttached;
    /**
     * Executor
     */
    private ExecutorService _executor;
    /**
     * UI
     */
    protected RecyclerView recyclerView;
    protected FermatAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
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

    /**
     * Setup views with layout root view
     *
     * @param layout View root
     */
    protected void initViews(View layout) {
        Log.i(TAG, "recycler view setup");
        if (layout == null)
            return;
        recyclerView = getRecycler(layout);
        if (recyclerView != null) {
            layoutManager = getLayoutManager();
            if (layoutManager != null)
                recyclerView.setLayoutManager(layoutManager);
            adapter = getAdapter();
            if (adapter != null) {
                recyclerView.setAdapter(adapter);
            }
        }
    }

}
