package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.NegotiationBasicInformation;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OpenNegotiationsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationBasicInformationImpl;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 20/10/2015
 */
public class OpenNegotiationsTabFragment extends FermatWalletFragment implements FermatListItemListeners<NegotiationBasicInformation> {

    private static final String TAG = "OpenNegotiationsTabFragment";

    // UI
    private RecyclerView recyclerView;
    private ExpandableRecyclerAdapter adapter;

    // MANAGERS
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // DATA
    private ArrayList<GrouperItem<NegotiationBasicInformation>> openNegotiations;
    private CryptoBrokerWallet cryptoBrokerWallet;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static OpenNegotiationsTabFragment newInstance() {
        return new OpenNegotiationsTabFragment();
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
        View layout = inflater.inflate(R.layout.fragment_open_negotiations_tab, container, false);
        initViews(layout);
        return layout;
    }

    private void initViews(View layout) {
        recyclerView = (RecyclerView) layout.findViewById(R.id.open_negotiations_recycler_view);
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

        openNegotiations = getOpenNegotiations();

        if (openNegotiations != null) {
            adapter = new OpenNegotiationsExpandableAdapter(getActivity(), openNegotiations);
            adapter.setChildItemFermatEventListeners(this);
        }

        return adapter;
    }

    /**
     * @return the list of open negotiations grouped in negotiations waiting for the broker and those wating for the customer
     */
    private ArrayList<GrouperItem<NegotiationBasicInformation>> getOpenNegotiations() {
        ArrayList<GrouperItem<NegotiationBasicInformation>> data = new ArrayList<>();
        String grouperText;

        if (moduleManager != null) {
            try {
                cryptoBrokerWallet = moduleManager.getCryptoBrokerWallet("crypto_broker_wallet");

                grouperText = getActivity().getString(R.string.waiting_for_you);
                List<NegotiationBasicInformation> waitingForBroker = cryptoBrokerWallet.getNegotiationsWaitingForBroker(0, 10);
                GrouperItem<NegotiationBasicInformation> waitingForBrokerGrouper = new GrouperItem<>(grouperText, waitingForBroker, true);
                data.add(waitingForBrokerGrouper);

                grouperText = getActivity().getString(R.string.waiting_for_the_customer);
                List<NegotiationBasicInformation> waitingForCustomer = cryptoBrokerWallet.getNegotiationsWaitingForCustomer(0, 10);
                GrouperItem<NegotiationBasicInformation> waitingForCustomerGrouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
                data.add(waitingForCustomerGrouper);

            } catch (CantGetCryptoBrokerWalletException | CantGetNegotiationsWaitingForBrokerException | CantGetNegotiationsWaitingForCustomerException ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                }
            }

        } else {
            NegotiationBasicInformationImpl child;

            grouperText = getActivity().getString(R.string.waiting_for_you);
            List<NegotiationBasicInformation> waitingForBroker = new ArrayList<>();
            child = new NegotiationBasicInformationImpl("nelsonalfo", "USD", "Crypto Transfer", "BTC", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForBroker.add(child);
            child = new NegotiationBasicInformationImpl("jorgeegonzalez", "BTC", "Cash in Hand", "USD", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForBroker.add(child);
            child = new NegotiationBasicInformationImpl("neoperol", "USD", "Cash in Hand", "BsF", NegotiationStatus.WAITING_FOR_BROKER);
            waitingForBroker.add(child);
            GrouperItem<NegotiationBasicInformation> waitingForBrokerGrouper = new GrouperItem<>(grouperText, waitingForBroker, true);
            data.add(waitingForBrokerGrouper);

            grouperText = getActivity().getString(R.string.waiting_for_the_customer);
            List<NegotiationBasicInformation> waitingForCustomer = new ArrayList<>();
            child = new NegotiationBasicInformationImpl("Nelson Orlando", "USD", "Bank Transfer", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForCustomer.add(child);
            child = new NegotiationBasicInformationImpl("Customer 5", "BsF", "Cash Delivery", "BTC", NegotiationStatus.WAITING_FOR_CUSTOMER);
            waitingForCustomer.add(child);
            GrouperItem<NegotiationBasicInformation> waitingForCustomerGrouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
            data.add(waitingForCustomerGrouper);
        }

        return data;
    }

    /**
     * Save the instance state of the adapter to keep expanded/collapsed states when rotating or
     * pausing the activity.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        adapter.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClickListener(NegotiationBasicInformation data, int position) {
        //TODO abrir actividad de detalle de negocacion abierta
    }

    @Override
    public void onLongItemClickListener(NegotiationBasicInformation data, int position) {
    }
}
