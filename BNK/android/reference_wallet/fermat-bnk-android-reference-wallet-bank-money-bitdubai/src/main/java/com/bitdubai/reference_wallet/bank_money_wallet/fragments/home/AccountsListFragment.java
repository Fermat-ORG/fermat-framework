package com.bitdubai.reference_wallet.bank_money_wallet.fragments.home;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.common.adapters.AccountListAdapter;
import com.bitdubai.reference_wallet.bank_money_wallet.common.holders.AccountListViewHolder;
import com.bitdubai.reference_wallet.bank_money_wallet.common.navigationDrawer.BankMoneyWalletNavigationViewPainter;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;
import com.bitdubai.reference_wallet.bank_money_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.bank_money_wallet.util.ReferenceWalletConstants;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Created by guillermo on 04/12/15.
 */
public class AccountsListFragment extends FermatWalletListFragment<BankAccountNumber> implements FermatListItemListeners<BankAccountNumber>{

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<BankAccountNumber> accountsList;


    private static final String TAG = "AccountListActivityFragment";

    public static AccountsListFragment newInstance() {
        return new AccountsListFragment();
    }

    private View emtyView;
    private FermatTextView header;

    private PresentationDialog presentationDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        accountsList = new ArrayList<>();
        try {
            moduleManager = ((BankMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
        accountsList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }
    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        configureToolbar();
        this.emtyView =  layout.findViewById(R.id.bw_empty_accounts_view);
        header = (FermatTextView)layout.findViewById(R.id.textView_header_text);
        header.setText(moduleManager.getBankName());
        presentationDialog = new PresentationDialog.Builder(getActivity(),appSession)
                .setBannerRes(R.drawable.bw_banner_1).setIconRes(R.drawable.bw_icon)
                .setBody(R.string.bnk_bank_money_wallet_account_body)
                .setTitle("prueba Title")
               .setSubTitle(R.string.bnk_bank_money_wallet_account_subTitle)
               .setTextFooter(R.string.bnk_bank_money_wallet_account_footer).setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
               .build();
        showOrHideNoAccountListView(accountsList.isEmpty());
        /*presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                System.out.println("presentation dialog dismiss");
            }
        });*/
        boolean showDialog;
        try{
            showDialog = moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isHomeTutorialDialogEnabled();
            if(showDialog){
                presentationDialog.show();
            }
        }catch (FermatException e){
            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
    }

    private void configureToolbar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background,null));
        else
            getToolbar().setBackground(getResources().getDrawable(R.drawable.bw_header_gradient_background));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            BankMoneyWalletNavigationViewPainter navigationViewPainter = new BankMoneyWalletNavigationViewPainter(getActivity());
//            getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
        } catch (Exception e) {
            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        menu.add(0, ReferenceWalletConstants.ADD_ACCOUNT_ACTION, 0, "Add Account").setIcon(R.drawable.bw_add_icon_action_bar)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(1, ReferenceWalletConstants.HELP_ACTION, 1, "help").setIcon(R.drawable.bw_help_icon_action_bar)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ReferenceWalletConstants.ADD_ACCOUNT_ACTION) {
            changeActivity(Activities.BNK_BANK_MONEY_WALLET_ADD_ACCOUNT, appSession.getAppPublicKey());
            return true;
        }
        if (item.getItemId() == ReferenceWalletConstants.HELP_ACTION) {
            presentationDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public FermatAdapter getAdapter() {
	    if(adapter == null){
            adapter = new AccountListAdapter(getActivity(), accountsList,this.moduleManager);
            adapter.setFermatListEventListener(this);
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
    public void onItemClickListener(BankAccountNumber data, int position) {
        appSession.setData("account_data", data);
        appSession.setData("account_image", AccountListViewHolder.getResource(position));
        changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, appSession.getAppPublicKey());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.bw_fragment_accounts_list;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.bw_swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.account_list_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public void onLongItemClickListener(BankAccountNumber data, int position) {
    }

    @Override
    public List<BankAccountNumber> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<BankAccountNumber> bankAccountNumbers = new ArrayList<>();
        String grouperText="accounts";
        if(moduleManager!=null) {
            try {
                bankAccountNumbers = moduleManager.getAccounts();
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(
                            Wallets.BNK_BANKING_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
                }
            }

        }
        System.out.println("size list account = "+bankAccountNumbers.size());
        return bankAccountNumbers;
    }

    private void showOrHideNoAccountListView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            emtyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emtyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            if (result != null && result.length > 0) {
                accountsList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(accountsList);
                showOrHideNoAccountListView(accountsList.isEmpty());
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

}
