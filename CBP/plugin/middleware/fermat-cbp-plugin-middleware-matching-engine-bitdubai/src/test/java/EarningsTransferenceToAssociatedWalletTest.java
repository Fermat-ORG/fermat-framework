import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningPairState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningTransactionsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningToWalletTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsPair;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


/**
 * Created by nelsonalfo on 18/04/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class EarningsTransferenceToAssociatedWalletTest {

    @Mock
    EarningToWalletTransaction transaction;

    @Mock
    BankMoneyDestockManager bankMoneyDestockManager;

    @Mock
    CashMoneyDestockManager cashMoneyDestockManager;

    @Mock
    private ErrorManager errorManager;

    @Mock
    private PluginVersionReference pluginVersionReference;

    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    private CryptoBrokerWalletManager cryptoBrokerWalletManager;

    UUID earningsPairId, originTransactionId;

    MatchingEngineMiddlewareDao matchingEngineDao;


    @Before
    public void setUp() throws CantListEarningTransactionsException, CryptoBrokerWalletNotFoundException, CantCreateBankMoneyDestockException {
        earningsPairId = UUID.randomUUID();
        originTransactionId = UUID.randomUUID();

        matchingEngineDao = new MatchingEngineMiddlewareDao(pluginDatabaseSystem, UUID.randomUUID());
        transaction = new EarningToWalletTransactionImpl(bankMoneyDestockManager);
    }

    @Test
    public void givenOneEarningTransaction_andEarningWalletIsBank_thenMakeBankMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                FiatCurrency.ARGENTINE_PESO,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                FiatCurrency.ARGENTINE_PESO,
                FiatCurrency.US_DOLLAR,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));


        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);


        // assertion
        verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq("123456789"),
                eq(BigDecimal.valueOf(10.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));
    }

    @Test
    public void givenOneEarningTransaction_andEarningWalletIsCash_thenMakeCashMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CSH_MONEY_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                FiatCurrency.US_DOLLAR,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                FiatCurrency.US_DOLLAR,
                CryptoCurrency.BITCOIN,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));


        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);


        // assertion
        verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq("123456789"),
                eq(BigDecimal.valueOf(10.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));
    }

    @Test
    public void givenNoEarningTransactions_thenDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

        final List<EarningTransaction> earningTransactions = new ArrayList<>();

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                FiatCurrency.ARGENTINE_PESO,
                FiatCurrency.US_DOLLAR,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));

        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);

        verify(bankMoneyDestockManager, never()).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq("123456789"),
                eq(BigDecimal.valueOf(10.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));
    }

    @Test
    public void givenOneParamIsNull_thenThrowException() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                FiatCurrency.ARGENTINE_PESO,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                FiatCurrency.ARGENTINE_PESO,
                FiatCurrency.US_DOLLAR,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));

        // exercise
        catchException(transaction).transferEarningsToEarningWallet(earningsPair, null);

        // assertions
        assertThat(caughtException()).isInstanceOf(CantTransferEarningsToWalletException.class);

        // exercise
        catchException(transaction).transferEarningsToEarningWallet(null, earningTransactions);

        // assertions
        assertThat(caughtException()).isInstanceOf(CantTransferEarningsToWalletException.class);
    }

    @Test
    public void givenManyEarningTransactionOfSameCurrency_andEarningWalletIsBank_thenMakeBankMoneyDestocks() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                FiatCurrency.ARGENTINE_PESO,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final UUID originTransactionId2 = UUID.randomUUID();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId2,
                FiatCurrency.ARGENTINE_PESO,
                20,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                FiatCurrency.ARGENTINE_PESO,
                FiatCurrency.US_DOLLAR,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));


        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);

        InOrder inOrder = inOrder(bankMoneyDestockManager);

        // assertions
        inOrder.verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq("123456789"),
                eq(BigDecimal.valueOf(10.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));

        inOrder.verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq("123456789"),
                eq(BigDecimal.valueOf(20.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId2.toString()));
    }

    public void givenManyEarningTransactionWithDifferentCurrencies_andEarningWalletIsBank_thenMakeBankMoneyDestocks() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                FiatCurrency.ARGENTINE_PESO,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        UUID originTransactionId2 = UUID.randomUUID();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId2,
                FiatCurrency.ARGENTINE_PESO,
                20,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                FiatCurrency.ARGENTINE_PESO,
                FiatCurrency.US_DOLLAR,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));


        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);

        InOrder inOrder = inOrder(bankMoneyDestockManager);

        // assertion
        inOrder.verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq("123456789"),
                eq(BigDecimal.valueOf(10.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));

        inOrder.verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq("123456789"),
                eq(BigDecimal.valueOf(20.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId2.toString()));
    }

}
