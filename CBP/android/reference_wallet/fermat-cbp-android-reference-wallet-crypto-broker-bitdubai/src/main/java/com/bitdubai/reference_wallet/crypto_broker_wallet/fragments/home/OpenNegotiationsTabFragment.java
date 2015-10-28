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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
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
public class OpenNegotiationsTabFragment extends FermatWalletFragment implements ExpandableRecyclerAdapter.ExpandCollapseListener {
    private static final String TAG = "OpenNegotiationsTabFragment";

    // UI
    private RecyclerView mRecyclerView;
    private OpenNegotiationsExpandableAdapter adapter;

    // MANAGERS
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // DATA
    private ArrayList<GrouperItem> openNegotiations;
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
            openNegotiations = getOpenNegotiations();

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
        View layout = inflater.inflate(R.layout.fragment_open_negotiations, container, false);
        initViews(layout);

        return layout;
    }

    private void initViews(View layout) {
        ArrayList<GrouperItem> openNegotiations = getOpenNegotiations();
        adapter = new OpenNegotiationsExpandableAdapter(getActivity(), openNegotiations);
        adapter.setExpandCollapseListener(this);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.open_negotiations_recycler_view);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onListItemExpanded(int position) {
        Toast.makeText(getActivity(), "Item expanded: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemCollapsed(int position) {
        Toast.makeText(getActivity(), "Item collapsed: " + position, Toast.LENGTH_SHORT).show();
    }

    /**
     * Save the instance state of the adapter to keep expanded/collapsed states when rotating or
     * pausing the activity.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        adapter.onSaveInstanceState(outState);
    }

    /**
     * @return the list of open negotiations grouped in negotiations waiting for the broker and those wating for the customer
     */
    public ArrayList<GrouperItem> getOpenNegotiations() {
        ArrayList<GrouperItem> data = new ArrayList<>();
        String grouperText;

        if (moduleManager != null) {
            try {
                cryptoBrokerWallet = moduleManager.getCryptoBrokerWallet("crypto_broker_wallet");

                grouperText = "Waiting for you";
                List<NegotiationBasicInformation> waitingForBroker = cryptoBrokerWallet.getNegotiationsWaitingForBroker(0, 10);
                GrouperItem<NegotiationBasicInformation> waitingForBrokerGrouper = new GrouperItem<>(grouperText, waitingForBroker, true);
                data.add(waitingForBrokerGrouper);

                grouperText = "Waiting for the customer";
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

            grouperText = "Waiting for you";
            List<NegotiationBasicInformation> waitingForBroker = new ArrayList<>();
            child = new NegotiationBasicInformationImpl("Customer 1", "USD", "Crypto Transfer", "BTC");
            waitingForBroker.add(child);
            child = new NegotiationBasicInformationImpl("Customer 2", "BTC", "Cash in Hand", "USD");
            waitingForBroker.add(child);
            child = new NegotiationBasicInformationImpl("Customer 3", "USD", "Cash in Hand", "BsF");
            waitingForBroker.add(child);
            GrouperItem<NegotiationBasicInformation> waitingForBrokerGrouper = new GrouperItem<>(grouperText, waitingForBroker, true);
            data.add(waitingForBrokerGrouper);

            grouperText = "Waiting for the customer";
            List<NegotiationBasicInformation> waitingForCustomer = new ArrayList<>();
            child = new NegotiationBasicInformationImpl("Customer 4", "USD", "Bank Transfer", "BTC");
            waitingForCustomer.add(child);
            child = new NegotiationBasicInformationImpl("Customer 5", "BsF", "Cash Delivery", "BTC");
            waitingForCustomer.add(child);
            GrouperItem<NegotiationBasicInformation> waitingForCustomerGrouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
            data.add(waitingForCustomerGrouper);
        }

        return data;
    }
}
