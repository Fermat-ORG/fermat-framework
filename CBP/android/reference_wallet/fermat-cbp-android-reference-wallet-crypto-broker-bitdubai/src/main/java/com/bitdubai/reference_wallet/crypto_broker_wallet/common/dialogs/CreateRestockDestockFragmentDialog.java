package com.bitdubai.reference_wallet.crypto_broker_wallet.common.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.NumberInputFilter;

import java.math.BigDecimal;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.FERMAT;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by Alejandro Bicelis on 15/12/2015.
 * Updated by Nelson Ramirez on 20/05/2016.
 */
public class CreateRestockDestockFragmentDialog extends Dialog implements View.OnClickListener {
    private static final String RESTOCK_OPTION = "restock";
    private static final String DESTOCK_OPTION = "destock";
    public static final String TRANSACTION_APPLIED = "transaction_applied";

    private Activity activity;

    private ReferenceAppFermatSession session;
    private CryptoBrokerWalletAssociatedSetting setting;

    /**
     * UI components
     */
    FermatEditText amountText;


    public CreateRestockDestockFragmentDialog(Activity activity, ReferenceAppFermatSession session, CryptoBrokerWalletAssociatedSetting setting) {
        super(activity);

        this.activity = activity;
        this.session = session;
        this.setting = setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.cbw_create_stock_transaction_dialog);

            amountText = (FermatEditText) findViewById(R.id.cbw_ctd_amount);

            //If working with BIC, allow a max of 999,999,999.99999999 BTC
            if (Platforms.CRYPTO_CURRENCY_PLATFORM.equals(setting.getPlatform()))
                amountText.setFilters(new InputFilter[]{new NumberInputFilter(17, 8)});
            else
                amountText.setFilters(new InputFilter[]{new NumberInputFilter(11, 2)});


            final View restockBtn = findViewById(R.id.cbw_ctd_restock_transaction_btn);
            final View destockBtn = findViewById(R.id.cbw_ctd_destock_transaction_btn);
            final View cancelBtn = findViewById(R.id.cbw_ctd_cancel_btn);

            destockBtn.setOnClickListener(this);
            restockBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);

        } catch (Exception e) {
            final ErrorManager errorManager = session.getErrorManager();

            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_THIS_FRAGMENT, e);
        }
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.cbw_ctd_restock_transaction_btn) {
            applyTransaction(RESTOCK_OPTION);
        } else if (viewId == R.id.cbw_ctd_destock_transaction_btn) {
            applyTransaction(DESTOCK_OPTION);
        } else if (viewId == R.id.cbw_ctd_cancel_btn) {
            dismiss();
        }
    }


    private void applyTransaction(String option) {
        try {
            String amountText = this.amountText.getText().toString();

            if (amountText.isEmpty()) {
                Toast.makeText(activity.getApplicationContext(), "The amount cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            final BigDecimal amount = new BigDecimal(amountText);
            final double amountAsDouble = amount.doubleValue();

            if (amountAsDouble == 0) {
                Toast.makeText(activity.getApplicationContext(), "The amount can't be zero", Toast.LENGTH_SHORT).show();
                return;
            }

            final Platforms walletPlatform = setting.getPlatform();
            final CryptoBrokerWalletModuleManager moduleManager = (CryptoBrokerWalletModuleManager) session.getModuleManager();

            boolean transactionApplied = false;
            switch (option) {
                case RESTOCK_OPTION:
                    transactionApplied = applyRestock(amount, walletPlatform, moduleManager);
                    break;

                case DESTOCK_OPTION:
                    transactionApplied = applyDestock(amount, walletPlatform, moduleManager);
                    break;
            }

            if (transactionApplied) {
                session.setData(TRANSACTION_APPLIED, true);
                dismiss();
            }

        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("There's been an error, please try again ").append(e.getMessage());
            Toast.makeText(activity.getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();

            final ErrorManager errorManager = session.getErrorManager();
            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_THIS_FRAGMENT, e);
        }
    }

    private boolean applyDestock(BigDecimal amount, Platforms walletPlatform, CryptoBrokerWalletModuleManager moduleManager) throws Exception {

        final float availableBalance = moduleManager.getAvailableBalance(setting.getMerchandise(), WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode());
        if (amount.floatValue() > availableBalance) {
            Toast.makeText(activity.getApplicationContext(), "The amount is higher that the available balance for the selected merchandise", Toast.LENGTH_LONG).show();
            return false;
        }

        final String memo = "Unheld funds, destocked from the Broker Wallet";

        switch (walletPlatform) {
            case BANKING_PLATFORM:
                moduleManager.createTransactionDestockBank(
                        setting.getBrokerPublicKey(),
                        (FiatCurrency) setting.getMerchandise(),
                        setting.getBrokerPublicKey(),
                        setting.getWalletPublicKey(),
                        setting.getBankAccount(),
                        amount,
                        memo,
                        BigDecimal.ZERO,
                        OriginTransaction.DESTOCK,
                        setting.getBrokerPublicKey());
                break;

            case CASH_PLATFORM:
                moduleManager.createTransactionDestockCash(
                        setting.getBrokerPublicKey(),
                        (FiatCurrency) setting.getMerchandise(),
                        setting.getBrokerPublicKey(),
                        setting.getWalletPublicKey(),
                        "Cash Destock in Broker Wallet",
                        amount,
                        memo,
                        BigDecimal.ZERO,
                        OriginTransaction.DESTOCK,
                        setting.getBrokerPublicKey());
                break;

            case CRYPTO_CURRENCY_PLATFORM:
                final double satoshi = BitcoinConverter.convert(amount.doubleValue(), BITCOIN, SATOSHI);

                moduleManager.createTransactionDestockCrypto(
                        setting.getBrokerPublicKey(),
                        (CryptoCurrency) setting.getMerchandise(),
                        setting.getBrokerPublicKey(),
                        setting.getWalletPublicKey(),
                        new BigDecimal(satoshi),
                        memo,
                        BigDecimal.ZERO,
                        OriginTransaction.DESTOCK,
                        setting.getBrokerPublicKey(),
                        //TODO:Revisar como vamos a sacar el BlochChainNetworkType
                        BlockchainNetworkType.getDefaultBlockchainNetworkType());
                break;
        }

        return true;
    }

    private boolean applyRestock(BigDecimal amount, Platforms walletPlatform, CryptoBrokerWalletModuleManager moduleManager) throws Exception {

        final double availableBalance = getStockWalletBalance(walletPlatform, moduleManager);
        final double amountAsDouble = amount.doubleValue();
        if (amountAsDouble > availableBalance) {
            Toast.makeText(activity.getApplicationContext(), "The selected wallet doesn't have enough money to restock the defined amount", Toast.LENGTH_LONG).show();
            return false;
        }

        final String memo = "Held funds, used to restock the Broker Wallet";

        switch (walletPlatform) {
            case BANKING_PLATFORM:
                moduleManager.createTransactionRestockBank(
                        setting.getBrokerPublicKey(),
                        (FiatCurrency) setting.getMerchandise(),
                        session.getAppPublicKey(),
                        setting.getWalletPublicKey(),
                        setting.getBankAccount(),
                        amount,
                        memo,
                        new BigDecimal(0),
                        OriginTransaction.RESTOCK,
                        setting.getBrokerPublicKey());
                break;

            case CASH_PLATFORM:
                moduleManager.createTransactionRestockCash(
                        setting.getBrokerPublicKey(),
                        (FiatCurrency) setting.getMerchandise(),
                        session.getAppPublicKey(),
                        setting.getWalletPublicKey(),
                        "Cash Restock in Broker Wallet",
                        amount,
                        memo,
                        BigDecimal.ZERO,
                        OriginTransaction.RESTOCK,
                        setting.getBrokerPublicKey());
                break;

            case CRYPTO_CURRENCY_PLATFORM:
                CryptoCurrency merchandise = (CryptoCurrency) setting.getMerchandise();
                long satoshi = getCryptoAmountInSatoshi(amount, merchandise);

                moduleManager.createTransactionRestockCrypto(
                        setting.getBrokerPublicKey(),
                        merchandise,
                        session.getAppPublicKey(),
                        setting.getWalletPublicKey(),
                        new BigDecimal(satoshi),
                        memo,
                        BigDecimal.ZERO,
                        OriginTransaction.RESTOCK,
                        setting.getBrokerPublicKey(),
                        BlockchainNetworkType.getDefaultBlockchainNetworkType());
                break;
        }

        return true;
    }

    private long getCryptoAmountInSatoshi(BigDecimal amount, CryptoCurrency merchandise) {
        double doubleValue = amount.doubleValue();
        switch (merchandise) {
            case BITCOIN:
                doubleValue = BitcoinConverter.convert(doubleValue, BITCOIN, SATOSHI);
                break;
            case FERMAT:
                doubleValue = BitcoinConverter.convert(doubleValue, FERMAT, SATOSHI);
                break;
        }

        return (long) doubleValue;
    }

    private double getStockWalletBalance(Platforms walletPlatform, CryptoBrokerWalletModuleManager moduleManager) throws Exception {
        double balance = 0;

        if (walletPlatform == Platforms.BANKING_PLATFORM) {
            balance = moduleManager.getBalanceBankWallet(setting.getWalletPublicKey(), setting.getBankAccount()).doubleValue();
        }
        if (walletPlatform == Platforms.CASH_PLATFORM) {
            balance = moduleManager.getBalanceCashWallet(setting.getWalletPublicKey()).doubleValue();
        }
        if (walletPlatform == Platforms.CRYPTO_CURRENCY_PLATFORM) {
            long balanceBitcoinWallet = moduleManager.getBalanceBitcoinWallet(setting.getWalletPublicKey());
            balance = BitcoinConverter.convert(balanceBitcoinWallet, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);
        }

        return balance;
    }
}