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
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterNew;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.interfaces.RecyclerListFragmentNew;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created Matias Furszyfer 22/09/2015
 */

/**
 * RecyclerView Fragment
 */
public abstract class FermatListFragmentNew<M> extends AbstractFermatFragment
        implements RecyclerListFragmentNew, SwipeRefreshLayout.OnRefreshListener, FermatWorkerCallBack {

    /**
     * CONSTANTS
     */
    protected final String TAG = "Recycler Base";
    /**
     * FLAGS
     */
    protected boolean isRefreshing;
    /**
     * Executor
     */
    private ExecutorService _executor;
    /**
     * UI
     */
    protected RecyclerView recyclerView;
    protected FermatAdapterNew adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected SwipeRefreshLayout swipeRefreshLayout;


    /**
     *
     * @param savedInstanceState
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasMenu());
        _executor = Executors.newFixedThreadPool(2);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // WalletResourcesProviderManager walletResourcesProviderManager = (WalletResourcesProviderManager) walletSession.getResourceProviderManager();
        String layout = null;

        layout = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<FrameLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:id=\"@+id/activity_container\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    >\n" +
                "\n" +
                "    <android.support.v4.widget.SwipeRefreshLayout\n" +
                "        android:id=\"@+id/swipe_refresh\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"match_parent\">\n" +
                "\n" +
                "        <android.support.v7.widget.RecyclerView\n" +
                "            android:id=\"@+id/payment_request_recycler_view\"\n" +
                "            android:layout_width=\"match_parent\"\n" +
                "            android:layout_height=\"match_parent\"\n" +
                "            android:layout_marginEnd=\"16dp\"\n" +
                "            android:layout_marginLeft=\"16dp\"\n" +
                "            android:layout_marginRight=\"16dp\"\n" +
                "            android:layout_marginStart=\"16dp\"\n" +
                "            android:layout_marginTop=\"8dp\" />\n" +
                "\n" +
                "    </android.support.v4.widget.SwipeRefreshLayout>\n" +
                "\n" +
                "\n" +
                "</FrameLayout>";
//        try {
//           layout = walletResourcesProviderManager.getLayoutResource(getLayoutResourceName(), ScreenOrientation.PORTRAIT, UUID.fromString("f39421a2-0b63-4d50-aba6-51b70d492c3e"),walletSession.getWalletSessionType().getWalletPublicKey());
//        } catch (CantGetResourcesException e) {
//            e.printStackTrace();
//        }
        View rootView = viewInflater.inflate(layout, null);  //inflater.inflate(getLayoutResource(), container, false);
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
     * @return String layout resource Ex: "fragment_view"
     */
    protected abstract String getLayoutResourceName();

    protected abstract String getSwipeRefreshLayoutName();

    protected abstract String getRecyclerLayoutName();

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
        recyclerView = (RecyclerView) layout.findViewById(viewInflater.getIdFromName(getRecyclerLayoutName()));
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(recyclerHasFixedSize());
            layoutManager = getLayoutManager();
            if (layoutManager != null)
                recyclerView.setLayoutManager(layoutManager);
            adapter = getAdapter();
            if (adapter != null) {
                recyclerView.setAdapter(adapter);
            }
            swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(viewInflater.getIdFromName(getSwipeRefreshLayoutName()));
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
    public List<M> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        return null;
    }

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
            if (getExecutor() != null)
                getExecutor().execute(worker);
        }
    }

}
