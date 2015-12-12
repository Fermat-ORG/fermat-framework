package com.bitdubai.reference_wallet.cash_money_wallet.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions.CantGetCashMoneyWalletBalancesException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class BalanceSummaryFragment extends FermatWalletFragment {

    protected final String TAG = "BalanceSummaryFragment";
    protected final String walletPublicKey = "publicKeyWalletMock";

    // Fermat Managers
    private CashMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    //Data
    CashWalletBalances walletBalances;
    FiatCurrency walletCurrency;

    public BalanceSummaryFragment() {}

    public static BalanceSummaryFragment newInstance() {return new BalanceSummaryFragment();}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CashMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
        updateWalletBalances();
        updateWalletCurrency();

        Toast.makeText(getActivity(), "Hello, im an onCreate toast", Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //String buyText = view.getResources().getString(R.string.buy_text_and_price, buy);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.balance_summary, container, false);

        FermatTextView bookTextView = (FermatTextView) view.findViewById(R.id.textView_book_amount);
        FermatTextView availableTextView = (FermatTextView) view.findViewById(R.id.textView_available_amount);

        bookTextView.setText(String.valueOf(this.walletBalances.getBookBalance() + " " + this.walletCurrency.getCode()));
        availableTextView.setText(String.valueOf(this.walletBalances.getAvailableBalance() + " " + this.walletCurrency.getCode()));

        return view;
    }

    private void updateWalletBalances() {
        try {
            this.walletBalances = moduleManager.getWalletBalances(walletPublicKey);
        } catch (CantGetCashMoneyWalletBalancesException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }
    private void updateWalletCurrency() {
        try {
            this.walletCurrency = moduleManager.getWalletCurrency(walletPublicKey);
        } catch (CantGetCashMoneyWalletCurrencyException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }
}
