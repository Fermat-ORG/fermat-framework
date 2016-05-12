package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import android.util.Log;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class StockStatisticsData {
    private List<CryptoBrokerStockTransaction> stockTransactions;
    private Currency currency;
    private float balance;

    public StockStatisticsData(CryptoBrokerWalletAssociatedSetting associatedWallet, CryptoBrokerWalletSession session) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);

        stockTransactions = null;

        ErrorManager errorManager = session.getErrorManager();
        try {
            final String walletPublicKey = session.getAppPublicKey();
            final CryptoBrokerWalletModuleManager walletManager = session.getModuleManager();

            currency = associatedWallet.getMerchandise();

            balance = walletManager.getAvailableBalance(currency, walletPublicKey);

            if (currency.getType() == CurrencyTypes.CRYPTO && CryptoCurrency.BITCOIN.getCode().equals(currency.getCode()))
                balance = (float) BitcoinConverter.convert(balance, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

            final int offset = 31;
            stockTransactions = walletManager.getStockHistory(
                    associatedWallet.getMerchandise(),
                    associatedWallet.getMoneyType(),
                    offset,
                    calendar.getTimeInMillis(),
                    walletPublicKey);

        } catch (FermatException e) {
            if (errorManager == null)
                Log.e("StockStatisticsData", e.getMessage(), e);
            else
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    // TODO: Solo es para pruebas, eliminar despues
    public StockStatisticsData(Currency currency, int days) {
        this.currency = currency;
        stockTransactions = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int actualDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        Random random = new Random();
        for (int i = days; i > 0; i--) {
            random.setSeed(i);
            calendar.set(Calendar.DAY_OF_MONTH, actualDayOfMonth - i);
            CryptoBrokerStockTransactionTestData transaction = new CryptoBrokerStockTransactionTestData(random, currency, calendar);
            stockTransactions.add(transaction);
        }
    }

    public float getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<CryptoBrokerStockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}