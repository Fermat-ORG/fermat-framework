package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.support.v7.widget.RecyclerView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetIdentity extends FermatListFragment<ActorIdentity> {


    public static WizardPageSetIdentity newInstance() {
        return new WizardPageSetIdentity();
    }

    @Override
    public FermatAdapter getAdapter() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    public void onPostExecute(Object... result) {

    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return 0;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return 0;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return false;
    }
}
