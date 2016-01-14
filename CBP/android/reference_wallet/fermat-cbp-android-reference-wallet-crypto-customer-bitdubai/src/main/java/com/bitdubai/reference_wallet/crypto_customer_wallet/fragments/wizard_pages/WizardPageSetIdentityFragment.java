package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.IdentitiesAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetIdentityFragment extends FermatWalletListFragment<CryptoCustomerIdentity>
        implements FermatListItemListeners<CryptoCustomerIdentity> {

    private List<CryptoCustomerIdentity> identities;
    private CryptoCustomerIdentity selectedIdentity;

    private LinearLayout container;

    private ErrorManager errorManager;
    private CryptoCustomerWalletManager walletManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CryptoCustomerWalletModuleManager moduleManager = ((CryptoCustomerWalletSession) appSession).getModuleManager();
            walletManager = moduleManager.getCryptoCustomerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            identities = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static AbstractFermatFragment newInstance() {
        return new WizardPageSetIdentityFragment();
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        container = (LinearLayout) layout.findViewById(R.id.ccw_wizard_set_identity_container);

        View nextStepButton = layout.findViewById(R.id.ccw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedIdentity != null) {
                    walletManager.associateIdentity(selectedIdentity.getPublicKey());

                    changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SET_BITCOIN_WALLET_AND_PROVIDERS, appSession.getAppPublicKey());
                } else {
                    Toast.makeText(getActivity(), R.string.ccw_select_identity_warning_msg, Toast.LENGTH_LONG).show();
                }

                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SET_BITCOIN_WALLET_AND_PROVIDERS, appSession.getAppPublicKey());
            }
        });

        verifyIfWalletConfigured();
    }

    private void verifyIfWalletConfigured() {
        new Handler().postDelayed(new Runnable() {
            boolean walletConfigured;

            @Override
            public void run() {
                try {
                    walletConfigured = walletManager.isWalletConfigured(appSession.getAppPublicKey());

                } catch (Exception ex) {
                    Object data = appSession.getData(CryptoCustomerWalletSession.CONFIGURED_DATA);
                    walletConfigured = (data != null);

                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                }

                if (walletConfigured) {
                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
                } else {
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                    container.setVisibility(View.VISIBLE);
                    container.startAnimation(fadeInAnimation);
                }

            }
        }, 500);
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
    public void onItemClickListener(CryptoCustomerIdentity data, int position) {
        selectedIdentity = data;
        IdentitiesAdapter identitiesAdapter = (IdentitiesAdapter) this.adapter;
        identitiesAdapter.selectItem(position);
    }

    @Override
    public void onLongItemClickListener(CryptoCustomerIdentity data, int position) {

    }

    @Override
    public List<CryptoCustomerIdentity> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoCustomerIdentity> data = new ArrayList<>();

        try {
            data.addAll(walletManager.getListOfIdentities());

        } catch (FermatException ex) {

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
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
        return R.layout.ccw_wizard_step_set_identity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.ccw_identities_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onDetach() {
        layoutManager = null;
        super.onDetach();
    }
}
