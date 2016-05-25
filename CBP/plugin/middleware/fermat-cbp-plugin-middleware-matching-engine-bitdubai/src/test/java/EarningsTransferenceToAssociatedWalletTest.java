import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningPairState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractorManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsPair;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.BankEarningExtractor;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.CashEarningExtractor;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.CryptoEarningExtractor;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.EarningExtractorManagerImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;


/**
 * Created by nelsonalfo on 18/04/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class EarningsTransferenceToAssociatedWalletTest {
    private static final String BANK_ACCOUNT_NUMBER_2 = "987654321";
    private static final String BANK_ACCOUNT_NUMBER_1 = "123456789";
    private static final String BROKER_WALLET_PUBLIC_KEY = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

    @Mock
    EarningExtractorManager transaction;

    @Mock
    BankMoneyDestockManager bankMoneyDestockManager;

    @Mock
    CashMoneyDestockManager cashMoneyDestockManager;

    @Mock
    CryptoMoneyDestockManager cryptoMoneyDestockManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @Mock
    CryptoBrokerWallet cryptoBrokerWallet;

    @Mock
    CryptoBrokerWalletSetting walletSettings;


    @Mock
    MatchingEngineMiddlewareDao dao;


    @Before
    public void setUp() throws FermatException {
        final List<CryptoBrokerWalletAssociatedSetting> associatedWallets = getAssociatedWalletsTestData();

        when(cryptoBrokerWalletManager.loadCryptoBrokerWallet(anyString())).thenReturn(cryptoBrokerWallet);
        when(cryptoBrokerWallet.getCryptoWalletSetting()).thenReturn(walletSettings);
        when(walletSettings.getCryptoBrokerWalletAssociatedSettings()).thenReturn(associatedWallets);

        doNothing().when(dao).markEarningTransactionAsExtracted(any(UUID.class));

        transaction = new EarningExtractorManagerImpl(cryptoBrokerWalletManager, dao);
        transaction.addEarningExtractor(new BankEarningExtractor(bankMoneyDestockManager));
        transaction.addEarningExtractor(new CashEarningExtractor(cashMoneyDestockManager));
        transaction.addEarningExtractor(new CryptoEarningExtractor(cryptoMoneyDestockManager));
    }

    @Test
    public void givenEarningsAmountIsPositive_andEarningWalletIsBank_thenShouldMakeBankMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;
        final FiatCurrency linkedCurrency = FiatCurrency.US_DOLLAR;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                10.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                -5.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                linkedCurrency,
                -5.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isTrue();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        earningTransaction = earningTransactions.get(1);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        earningTransaction = earningTransactions.get(2);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(BROKER_WALLET_PUBLIC_KEY),
                eq(earningWalletPublicKey),
                eq(BANK_ACCOUNT_NUMBER_1),
                eq(BigDecimal.valueOf(5.0f)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(earningsPair.getId().toString()));
    }

    @Test
    public void givenEarningsAmountIsNegative_andEarningWalletIsBank_thenShouldDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;
        final FiatCurrency linkedCurrency = FiatCurrency.US_DOLLAR;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                10.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                -15.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isFalse();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        earningTransaction = earningTransactions.get(1);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        verifyZeroInteractions(bankMoneyDestockManager);
    }

    @Test
    public void givenEarningsAmountIsPositive_andEarningWalletIsCash_thenShouldMakeCashMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CSH_MONEY_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.US_DOLLAR;
        final CryptoCurrency linkedCurrency = CryptoCurrency.BITCOIN;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isTrue();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        verify(cashMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(BROKER_WALLET_PUBLIC_KEY),
                eq(earningWalletPublicKey),
                anyString(),
                eq(BigDecimal.valueOf(10.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(earningsPair.getId().toString()));
    }

    @Test
    public void givenEarningsAmountIsNegative_andEarningWalletIsCash_thenShouldDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CSH_MONEY_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.US_DOLLAR;
        final CryptoCurrency linkedCurrency = CryptoCurrency.BITCOIN;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                -10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isFalse();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        verifyZeroInteractions(cashMoneyDestockManager);
    }

    @Test
    public void givenEarningsAmountIsPositive_andEarningWalletIsCrypto_thenShouldMakeCryptoMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode();
        final FiatCurrency linkedCurrency = FiatCurrency.VENEZUELAN_BOLIVAR;
        final CryptoCurrency earningCurrency = CryptoCurrency.BITCOIN;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                1.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isTrue();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        verify(cryptoMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(BROKER_WALLET_PUBLIC_KEY),
                eq(earningWalletPublicKey),
                eq(BigDecimal.valueOf(1.0f)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(earningsPair.getId().toString()),
                eq(BlockchainNetworkType.getDefaultBlockchainNetworkType()));
    }

    @Test
    public void givenEarningsAmountIsNegative_andEarningWalletIsCrypto_thenShouldDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode();
        final FiatCurrency linkedCurrency = FiatCurrency.VENEZUELAN_BOLIVAR;
        final CryptoCurrency earningCurrency = CryptoCurrency.BITCOIN;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                -1.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isFalse();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        verifyZeroInteractions(cryptoMoneyDestockManager);
    }

    @Test
    public void givenEarningTransactionsWereExtracted_thenShouldDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;
        final FiatCurrency linkedCurrency = FiatCurrency.US_DOLLAR;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                10.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                -5.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                linkedCurrency,
                -5.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isTrue();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        earningTransaction = earningTransactions.get(1);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        earningTransaction = earningTransactions.get(2);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(BROKER_WALLET_PUBLIC_KEY),
                eq(earningWalletPublicKey),
                eq(BANK_ACCOUNT_NUMBER_1),
                eq(BigDecimal.valueOf(5.0f)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(earningsPair.getId().toString()));


        // exercise
        earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isFalse();

        earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        earningTransaction = earningTransactions.get(1);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.EXTRACTED);

        earningTransaction = earningTransactions.get(2);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        verifyZeroInteractions(bankMoneyDestockManager);
    }

    @Test
    public void givenNoEarningTransactions_thenShouldDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;
        final FiatCurrency linkedCurrency = FiatCurrency.US_DOLLAR;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isFalse();

        verifyZeroInteractions(bankMoneyDestockManager);
    }

    @Test
    public void givenMatchingEarningTransactions_thenShouldDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CSH_MONEY_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.US_DOLLAR;
        final FiatCurrency otherEarningCurrency = FiatCurrency.ARGENTINE_PESO;
        final CryptoCurrency linkedCurrency = CryptoCurrency.BITCOIN;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                otherEarningCurrency,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                otherEarningCurrency,
                -1,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        boolean earningsExtracted = transaction.extractEarnings(earningsPair, earningTransactions);

        // assertion
        assertThat(earningsExtracted).isFalse();

        EarningTransaction earningTransaction = earningTransactions.get(0);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        earningTransaction = earningTransactions.get(1);
        assertThat(earningTransaction.getState()).isEqualTo(EarningTransactionState.CALCULATED);

        verifyZeroInteractions(cashMoneyDestockManager);
    }

    @Test
    public void givenAParamIsNull_thenShouldThrowException() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;
        final FiatCurrency linkedCurrency = FiatCurrency.US_DOLLAR;

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        // exercise
        catchException(transaction).extractEarnings(earningsPair, null);

        // assertions
        assertThat(caughtException()).isInstanceOf(CantExtractEarningsException.class);

        // exercise
        catchException(transaction).extractEarnings(null, earningTransactions);

        // assertions
        assertThat(caughtException()).isInstanceOf(CantExtractEarningsException.class);
    }

    @Test
    public void givenThereIsNoAppropriateEarningExtractor_thenShouldThrowException() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;
        final FiatCurrency linkedCurrency = FiatCurrency.US_DOLLAR;

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                UUID.randomUUID(),
                earningCurrency,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                dao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                linkedCurrency,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                new WalletReference(BROKER_WALLET_PUBLIC_KEY));

        transaction = new EarningExtractorManagerImpl(cryptoBrokerWalletManager, dao);

        // exercise
        catchException(transaction).extractEarnings(earningsPair, earningTransactions);

        // assertions
        assertThat(caughtException()).isInstanceOf(CantExtractEarningsException.class);
    }

    private List<CryptoBrokerWalletAssociatedSetting> getAssociatedWalletsTestData() {

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();

        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.BANKING_PLATFORM,
                WalletsPublicKeys.BNK_BANKING_WALLET.getCode(), FiatCurrency.ARGENTINE_PESO, BANK_ACCOUNT_NUMBER_1));

        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.BANKING_PLATFORM,
                WalletsPublicKeys.BNK_BANKING_WALLET.getCode(), FiatCurrency.VENEZUELAN_BOLIVAR, BANK_ACCOUNT_NUMBER_2));

        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.CASH_PLATFORM,
                WalletsPublicKeys.CSH_MONEY_WALLET.getCode(), FiatCurrency.US_DOLLAR, null));

        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.CRYPTO_CURRENCY_PLATFORM,
                WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode(), CryptoCurrency.BITCOIN, null));

        return associatedWallets;
    }
}
