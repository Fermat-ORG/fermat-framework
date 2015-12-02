package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.ElementsWithAnimation;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.BitcoinWalletConstants;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.ReceivetransactionsExpandableAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.models.GrouperItem;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Matias Furszyfer
 * @since 7/10/2015
 */
public class ReceiveTransactionFragment2 extends FermatWalletExpandableListFragment<GrouperItem>
        implements FermatListItemListeners<CryptoWalletTransaction>,ElementsWithAnimation {

    private int MAX_TRANSACTIONS = 20;

    // Fermat Managers
    private CryptoWallet moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<GrouperItem> openNegotiationList;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private long balanceAvailable;
    private View rootView;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsAvailable;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsBook;
    private int available_offset=0;
    private int book_offset=0;
    //private CryptoBrokerWallet cryptoBrokerWallet;

    ReferenceWalletSession referenceWalletSession;

    private Typeface tf;
    private View emptyListViewsContainer;
    private int[] emptyOriginalPos = new int[2];


    public static ReceiveTransactionFragment2 newInstance() {
        return new ReceiveTransactionFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");

        setHasOptionsMenu(true);

        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        lstCryptoWalletTransactionsBook = new ArrayList<>();

        try {
            referenceWalletSession = (ReferenceWalletSession) walletSession;
            moduleManager = referenceWalletSession.getModuleManager().getCryptoWallet();
            errorManager = walletSession.getErrorManager();
        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openNegotiationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            setUp(inflater);
        } catch (CantGetActiveLoginIdentityException e) {
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
    private void setUp(LayoutInflater inflater) throws CantGetActiveLoginIdentityException {

        //setUpHeader(inflater);
        //setUpDonut(inflater);
        //WalletUtils.setNavigatitDrawer(getPaintActivtyFeactures(),referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity());
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPaintActivtyFeactures().addCollapseAnimation(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();

        menu.add(0, BitcoinWalletConstants.IC_ACTION_SEND, 0, "send").setIcon(R.drawable.ic_actionbar_send)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {

            int id = item.getItemId();

            if (id == BitcoinWalletConstants.IC_ACTION_SEND) {
                    changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY,walletSession.getAppPublicKey());
                    return true;
                }

            }catch(Exception e){
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
                makeText(getActivity(), "Oooops! recovering from system error",
                        Toast.LENGTH_SHORT).show();
            }
            return super.onOptionsItemSelected(item);
        }


    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer = layout.findViewById(R.id.empty);
            FermatAnimationsUtils.showEmpty(getActivity(), true, emptyListViewsContainer);
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ReceivetransactionsExpandableAdapter(getActivity(), openNegotiationList,getResources());
            adapter.setChildItemFermatEventListeners(this);
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity());

        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.home_receive_main;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.open_contracts_recycler_view;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    public List<GrouperItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<GrouperItem> data = new ArrayList<>();

        try {
            IntraUserLoginIdentity intraUserLoginIdentity = referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity();
            if(intraUserLoginIdentity!=null) {

                String intraUserPk = intraUserLoginIdentity.getPublicKey();

                List<CryptoWalletTransaction> list = moduleManager.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.CREDIT, referenceWalletSession.getAppPublicKey(), intraUserPk, MAX_TRANSACTIONS, available_offset);

                lstCryptoWalletTransactionsAvailable.addAll(list);

                available_offset = lstCryptoWalletTransactionsAvailable.size();

                lstCryptoWalletTransactionsBook.addAll(moduleManager.listLastActorTransactionsByTransactionType(BalanceType.BOOK, TransactionType.CREDIT, referenceWalletSession.getAppPublicKey(), intraUserPk, MAX_TRANSACTIONS, book_offset));

                book_offset = lstCryptoWalletTransactionsBook.size();

                //get transactions from actor public key to send me btc
                for (CryptoWalletTransaction cryptoWalletTransaction : list) {
                    List<CryptoWalletTransaction> lst = new ArrayList<>();
                    lst = moduleManager.listTransactionsByActorAndType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.CREDIT, referenceWalletSession.getAppPublicKey(), cryptoWalletTransaction.getActorFromPublicKey(), intraUserPk,MAX_TRANSACTIONS, 0);
                    GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction> grouperItem = new GrouperItem<CryptoWalletTransaction, CryptoWalletTransaction>(lst, false, cryptoWalletTransaction);
                    data.add(grouperItem);
                }
                if(!data.isEmpty()){
                    FermatAnimationsUtils.showEmpty(getActivity(),false,emptyListViewsContainer);
                }

            }
        } catch (CantListTransactionsException e) {
            e.printStackTrace();
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(CryptoWalletTransaction data, int position) {
        //TODO abrir actividad de detalle de contrato abierto
    }

    @Override
    public void onLongItemClickListener(CryptoWalletTransaction data, int position) {
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                openNegotiationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openNegotiationList);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,ex);
        }
    }

    private void changeAmountType(TextView txt_balance_amount){
        //referenceWalletSession.setTypeAmount((referenceWalletSession.getTypeAmount()== ShowMoneyType.BITCOIN.getCode()) ? ShowMoneyType.BITS : ShowMoneyType.BITCOIN);
        updateBalances();
    }



    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {

        try {
            if (((ReferenceWalletSession)walletSession).getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                //txt_balance_amount.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.book_balance);
               // referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            //} else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                //bookBalance = loadBalance(BalanceType.BOOK);
            //    txt_balance_amount.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
           //     txt_type_balance.setText(R.string.available_balance);
               // referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
           // referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
//        try {
//            balance = cryptoWallet.getBalance(balanceType,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
//        } catch (CantGetBalanceException e) {
//            e.printStackTrace();
//        }
        return balance;
    }


    private void updateBalances(){
        //bookBalance = loadBalance(BalanceType.BOOK);
        balanceAvailable = loadBalance(BalanceType.AVAILABLE);
//        txt_balance_amount.setText(
//                WalletUtils.formatBalanceString(
//                        (referenceWalletSession.getBalanceTypeSelected() == BalanceType.AVAILABLE.getCode())
//                                ? balanceAvailable : bookBalance,
//                        referenceWalletSession.getTypeAmount())
//        );
    }

    @Override
    public void startCollapseAnimation(int verticalOffset) {
        moveViewToScreenCenter(emptyListViewsContainer);
    }

    @Override
    public void startExpandAnimation(int verticalOffSet) {
        moveViewToOriginalPosition(emptyListViewsContainer);
    }

    private void moveViewToOriginalPosition(View view) {
        if(Build.VERSION.SDK_INT>17) {
            if(view!=null) {
                int position[] = new int[2];
                view.getLocationOnScreen(position);
                float centreY = rootView.getY() + rootView.getHeight() / 2;
                TranslateAnimation anim = new TranslateAnimation(emptyOriginalPos[0], 0, centreY - 250, 0);
                anim.setDuration(1000);
                anim.setFillAfter(true);
                view.startAnimation(anim);
            }
        }
    }

    private void moveViewToScreenCenter( View view ) {
        if (Build.VERSION.SDK_INT > 17) {
            if(view!=null) {
                DisplayMetrics dm = new DisplayMetrics();
                rootView.getDisplay().getMetrics(dm);
                float centreY = rootView.getY() + rootView.getHeight() / 2;
                TranslateAnimation anim = new TranslateAnimation(0, emptyOriginalPos[0], 0, centreY - 250);
                anim.setDuration(1000);
                anim.setFillAfter(true);
                view.startAnimation(anim);
            }
        }
    }
}

