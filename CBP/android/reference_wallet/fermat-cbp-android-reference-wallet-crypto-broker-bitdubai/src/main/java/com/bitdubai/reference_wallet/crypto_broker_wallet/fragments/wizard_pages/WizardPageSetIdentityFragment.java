package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.IdentitiesAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetIdentityFragment extends FermatWalletListFragment<CryptoBrokerIdentity> implements FermatListItemListeners<CryptoBrokerIdentity> {

    private List<CryptoBrokerIdentity> identities;
    private CryptoBrokerIdentity selectedIdentity;

    private ErrorManager errorManager;
    private CryptoBrokerWalletManager walletManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            identities = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (CantGetCryptoBrokerWalletException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static FermatWalletFragment newInstance() {
        return new WizardPageSetIdentityFragment();
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIdentity != null) {
                    walletManager.associateIdentity(selectedIdentity.getPublicKey());
                    appSession.setData(CryptoBrokerWalletSession.CONFIGURED_DATA, true);
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
                } else {
                    Toast.makeText(getActivity(), R.string.select_identity_warning_msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public FermatAdapter getAdapter() {
        IdentitiesAdapter adapter = new IdentitiesAdapter(getActivity(), identities);
        adapter.setFermatListEventListener(this);
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        return layoutManager;
    }

    @Override
    public void onItemClickListener(CryptoBrokerIdentity data, int position) {
        selectedIdentity = data;
        IdentitiesAdapter identitiesAdapter = (IdentitiesAdapter) this.adapter;
        identitiesAdapter.selectItem(position);
    }

    @Override
    public void onLongItemClickListener(CryptoBrokerIdentity data, int position) {

    }

    @Override
    public List<CryptoBrokerIdentity> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoBrokerIdentity> data = new ArrayList<>();

        try {
            data.addAll(walletManager.getListOfIdentities());

        } catch (CantGetCryptoBrokerIdentityListException ex) {

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT,
                        ex);
            }
        }

        return data;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                identities = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(identities);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.cbw_wizard_step_set_identity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.cbw_identities_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }
}
