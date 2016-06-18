package com.mati.fermat_preference_settings.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.mati.fermat_preference_settings.R;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mati on 2016.02.08..
 */
public abstract class FermatPreferenceFragment<S extends ReferenceAppFermatSession,RE extends ResourceProviderManager> extends AbstractFermatFragment<S,RE> {


    /**
     * CONSTANTS
     */
    protected final String TAG = "Recycler Base";

    /**
     * UI
     */
    protected RecyclerView recyclerView;
    protected FermatAdapterImproved adapter;
    private RecyclerView.LayoutManager layoutManager;
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
        View rootView = inflater.inflate(R.layout.settings_fragment_base_main, container,false);
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
        recyclerView = (RecyclerView) layout.findViewById(R.id.preference_settings_main_recycler_view);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new FermatSettingsAdapter(getActivity(),this,setSettingsItems());
            recyclerView.setAdapter(adapter);

            this.setBackground(layout);

        }
    }

    /**
     * Set settings items
     * @return
     */
    protected abstract List<PreferenceSettingsItem> setSettingsItems();

    /**
     * Get executor service to run threads out side of main thread.
     *
     * @return Fixed Thread Pool Executor (Max Thread per fragment <b>3</b>
     */
    protected ExecutorService getExecutor() {
        return this._executor;
    }

    public FermatViewHolder findItemById(int position){
        return (FermatViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
    }

    public abstract void dialogOptionSelected(String item, int position);

    public abstract void optionSelected(PreferenceSettingsItem preferenceSettingsItem, int position);

    public abstract void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position);

    public abstract void onSettingsTouched(String item, int position);

    public abstract void onSettingsChanged(PreferenceSettingsItem preferenceSettingsItem, int position,boolean isChecked);

    public abstract int getBackgroundColor();

    public abstract int getBackgroundAlpha();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((FermatSettingsAdapter)adapter).clear();
    }

    private void setBackground(View layout)
    {
        if(getBackgroundColor() != 0) {
            LinearLayout rl = (LinearLayout) layout.findViewById(R.id.linearLayout);
            rl.setBackgroundColor(getBackgroundColor());
            if(getBackgroundAlpha() != 0)
                rl.getBackground().setAlpha(getBackgroundAlpha());
        }
    }
}
