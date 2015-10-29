package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OpenContractsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.ContractBasicInformationImpl;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenContractsTabFragment extends FermatWalletFragment implements FermatListItemListeners<ContractBasicInformation> {

    private static final String TAG = "OpenContractsTabFragment";

    // UI
    private RecyclerView recyclerView;
    private ExpandableRecyclerAdapter adapter;

    // MANAGERS
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // DATA
    private ArrayList<GrouperItem<ContractBasicInformation>> openContracts;
    private CryptoBrokerWallet cryptoBrokerWallet;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static OpenContractsTabFragment newInstance() {
        return new OpenContractsTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoBrokerWalletSession) walletSession).getModuleManager();
            errorManager = walletSession.getErrorManager();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);

            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT,
                        ex);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_open_contracts_tab, container, false);
        initViews(layout);
        return layout;
    }

    private void initViews(View layout) {
        recyclerView = (RecyclerView) layout.findViewById(R.id.open_contracts_recycler_view);
        View emptyView = layout.findViewById(R.id.empty);

        adapter = getAdapter();

        if (adapter != null) {
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape));
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * @return the adapter
     */
    private ExpandableRecyclerAdapter getAdapter() {
        ExpandableRecyclerAdapter adapter = null;

        openContracts = getOpenContracts();

        if (openContracts != null) {
            adapter = new OpenContractsExpandableAdapter(getActivity(), openContracts);
            adapter.setChildItemFermatEventListeners(this);
        }

        return adapter;
    }

    /**
     * @return the list of open negotiations grouped in negotiations waiting for the broker and those wating for the customer
     */
    private ArrayList<GrouperItem<ContractBasicInformation>> getOpenContracts() {
        ArrayList<GrouperItem<ContractBasicInformation>> data = new ArrayList<>();
        String grouperText;

        if (moduleManager != null) {
            // TODO hay que pensar esto
        } else {
            ContractBasicInformation child;

            grouperText = getActivity().getString(R.string.waiting_for_you);
            List<ContractBasicInformation> waitingForBroker = new ArrayList<>();
            child = new ContractBasicInformationImpl("adrianasupernova", "USD", "Crypto Transfer", "BTC", ContractStatus.PAUSED);
            waitingForBroker.add(child);
            GrouperItem<ContractBasicInformation> waitingForBrokerGrouper = new GrouperItem<>(grouperText, waitingForBroker, true);
            data.add(waitingForBrokerGrouper);

            grouperText = getActivity().getString(R.string.waiting_for_the_customer);
            List<ContractBasicInformation> waitingForCustomer = new ArrayList<>();
            child = new ContractBasicInformationImpl("yalayn", "BTC", "Bank Transfer", "USD", ContractStatus.PENDING_PAYMENT);
            waitingForCustomer.add(child);
            child = new ContractBasicInformationImpl("vzlangel", "BsF", "Cash Delivery", "BsF", ContractStatus.PENDING_PAYMENT);
            waitingForCustomer.add(child);
            GrouperItem<ContractBasicInformation> waitingForCustomerGrouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
            data.add(waitingForCustomerGrouper);
        }

        return data;
    }

    @Override
    public void onItemClickListener(ContractBasicInformation data, int position) {
        //TODO abrir actividad de detalle de contrato abierto
    }

    @Override
    public void onLongItemClickListener(ContractBasicInformation data, int position) {
    }
}

