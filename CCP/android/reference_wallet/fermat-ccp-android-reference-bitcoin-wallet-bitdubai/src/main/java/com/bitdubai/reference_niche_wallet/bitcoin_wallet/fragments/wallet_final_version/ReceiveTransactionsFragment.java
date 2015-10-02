package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.PaintActivtyFeactures;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.TransactionNewAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by mati on 2015.09.28..
 */
public class ReceiveTransactionsFragment extends FermatWalletListFragment<CryptoWalletTransaction> implements FermatListItemListeners<CryptoWalletTransaction>{

    /**
     * MANAGERS
     */
    private CryptoWallet cryptoWallet;

    /**
     * Session
     */
    ReferenceWalletSession referenceWalletSession;

    /**
     * DATA
     */
    private List<CryptoWalletTransaction> lstCryptoWalletTransactions;
    private CryptoWalletTransaction selectedItem;

    /**
     * Executor Service
     */
    private ExecutorService executor;

    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;

    View rootView;
    private LinearLayout linear_layout_receive_form;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ReceiveTransactionsFragment newInstance() {
        ReceiveTransactionsFragment requestPaymentFragment = new ReceiveTransactionsFragment();
        return new ReceiveTransactionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSession)walletSession;

        lstCryptoWalletTransactions = new ArrayList<CryptoWalletTransaction>();
        try {
            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

            lstCryptoWalletTransactions = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
        } catch (Exception ex) {
            ex.printStackTrace();
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstCryptoWalletTransactions = new ArrayList<CryptoWalletTransaction>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = super.onCreateView(inflater,container, savedInstanceState);
//
        RelativeLayout container_header_balance = getActivityHeader();

        inflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        container_header_balance.setVisibility(View.VISIBLE);

        View balance_header = inflater.inflate(R.layout.balance_header, container_header_balance, true);

        final TextView txt_type_balance = (TextView) balance_header.findViewById(R.id.txt_type_balance);


        TextView txt_touch_to_change = (TextView) balance_header.findViewById(R.id.txt_touch_to_change);
        txt_touch_to_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
                txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
            }
        });

        TextView txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);

        try {
            long balance = cryptoWallet.getBalance(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()),referenceWalletSession.getWalletSessionType().getWalletPublicKey());
            txt_balance_amount.setText(formatBalanceString(balance, referenceWalletSession.getTypeAmount()));
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        }

        //container_header_balance.invalidate();

        //((PaintActivtyFeactures)getActivity()).invalidate();

        //rootView    = inflater.inflate(R.layout.receive_button, container, false);


        linear_layout_receive_form = (LinearLayout) rootView.findViewById(R.id.receive_form);

        ((com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.fab_action)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isShow = linear_layout_receive_form.isShown();
                //linear_layout_send_form.setVisibility(isShow?View.GONE:View.VISIBLE);
                if (isShow) {
                    Fx.slide_up(getActivity(), linear_layout_receive_form);
                    linear_layout_receive_form.setVisibility(View.GONE);
                } else {
                    linear_layout_receive_form.setVisibility(View.VISIBLE);
                    Fx.slide_down(getActivity(), linear_layout_receive_form);
                }

            }
        });






        return rootView;
    }


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.transaciotn_main_fragment_receive;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.transactions_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            //WalletStoreItemPopupMenuListener listener = getWalletStoreItemPopupMenuListener();
            adapter = new TransactionNewAdapter(getActivity(), lstCryptoWalletTransactions,cryptoWallet,referenceWalletSession);
            adapter.setFermatListEventListener(this); // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public List<CryptoWalletTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoWalletTransaction> lstTransactions  = new ArrayList<CryptoWalletTransaction>();

//        try {
//            lstTransactions = cryptoWallet.listLastActorTransactionsByTransactionType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.CREDIT,referenceWalletSession.getWalletSessionType().getWalletPublicKey(),MAX_TRANSACTIONS,offset);
//            offset+=MAX_TRANSACTIONS;
//        } catch (Exception e) {
//            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(e,
//                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        //    e.printStackTrace();
            // data = RequestPaymentListItem.getTestData(getResources());
        //}
        CryptoWalletTransactionsTest cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
         cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
         cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
        return lstTransactions;
    }

    @Override
    public void onItemClickListener(CryptoWalletTransaction item, int position) {
        selectedItem = item;
        //showDetailsActivityFragment(selectedItem);
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(CryptoWalletTransaction data, int position) {

    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lstCryptoWalletTransactions = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstCryptoWalletTransactions);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }



    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }

}
