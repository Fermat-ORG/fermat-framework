import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningPairState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantTransferEarningsToWalletException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningToWalletTransaction;
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
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_transfer_apliers.EarningToBankWalletTransferApplier;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_transfer_apliers.EarningToCashWalletTransferApplier;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_transfer_apliers.EarningToCryptoWalletTransferApplier;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsPair;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_transfer_apliers.EarningToWalletTransactionImpl;
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
import static org.mockito.Mockito.when;


/**
 * Created by nelsonalfo on 18/04/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class EarningsTransferenceToAssociatedWalletTest {
    final String BANK_ACCOUNT_NUMBER_2 = "987654321";
    final String BANK_ACCOUNT_NUMBER_1 = "123456789";

    @Mock
    EarningToWalletTransaction transaction;

    @Mock
    BankMoneyDestockManager bankMoneyDestockManager;

    @Mock
    CashMoneyDestockManager cashMoneyDestockManager;

    @Mock
    CryptoMoneyDestockManager cryptoMoneyDestockManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    PluginVersionReference pluginVersionReference;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @Mock
    CryptoBrokerWallet cryptoBrokerWallet;

    @Mock
    CryptoBrokerWalletSetting walletSettings;

    UUID earningsPairId, originTransactionId;

    MatchingEngineMiddlewareDao matchingEngineDao;


    @Before
    public void setUp() throws FermatException {
        final ArrayList<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.BANKING_PLATFORM,
                WalletsPublicKeys.BNK_BANKING_WALLET.getCode(), FiatCurrency.ARGENTINE_PESO, BANK_ACCOUNT_NUMBER_1));

        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.BANKING_PLATFORM,
                WalletsPublicKeys.BNK_BANKING_WALLET.getCode(), FiatCurrency.VENEZUELAN_BOLIVAR, BANK_ACCOUNT_NUMBER_2));

        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.CASH_PLATFORM,
                WalletsPublicKeys.CSH_MONEY_WALLET.getCode(), FiatCurrency.US_DOLLAR, null));

        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(Platforms.CRYPTO_CURRENCY_PLATFORM,
                WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode(), CryptoCurrency.BITCOIN, null));

        when(cryptoBrokerWalletManager.loadCryptoBrokerWallet(anyString())).thenReturn(cryptoBrokerWallet);
        when(cryptoBrokerWallet.getCryptoWalletSetting()).thenReturn(walletSettings);
        when(walletSettings.getCryptoBrokerWalletAssociatedSettings()).thenReturn(associatedWallets);

        earningsPairId = UUID.randomUUID();
        originTransactionId = UUID.randomUUID();
        matchingEngineDao = new MatchingEngineMiddlewareDao(pluginDatabaseSystem, UUID.randomUUID());

        transaction = new EarningToWalletTransactionImpl(cryptoBrokerWalletManager);
        transaction.addTransferApplier(new EarningToBankWalletTransferApplier(bankMoneyDestockManager));
        transaction.addTransferApplier(new EarningToCashWalletTransferApplier(cashMoneyDestockManager));
        transaction.addTransferApplier(new EarningToCryptoWalletTransferApplier(cryptoMoneyDestockManager));
    }

    @Test
    public void givenOneEarningTransaction_andEarningWalletIsBank_thenMakeBankMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;
        float amount = 10;

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                earningCurrency,
                amount,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
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
                eq(earningCurrency),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq(BANK_ACCOUNT_NUMBER_1),
                eq(BigDecimal.valueOf(amount)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));
    }

    @Test
    public void givenOneEarningTransaction_andEarningWalletIsCash_thenMakeCashMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CSH_MONEY_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.US_DOLLAR;

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                earningCurrency,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                CryptoCurrency.BITCOIN,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));


        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);

        // assertion
        verify(cashMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                anyString(),
                eq(BigDecimal.valueOf(10.0)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));
    }

    @Test
    public void givenOneEarningTransaction_andEarningWalletIsCrypto_thenMakeCryptoMoneyDestock() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();
        final CryptoCurrency earningCurrency = CryptoCurrency.BITCOIN;
        final float amount = 1.0f;

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                earningCurrency,
                amount,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                FiatCurrency.VENEZUELAN_BOLIVAR,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));

        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);

        // assertion
        verify(cryptoMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq(BigDecimal.valueOf(amount)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()),
                eq(BlockchainNetworkType.getDefaultBlockchainNetworkType()));
    }

    @Test
    public void givenNoEarningTransactions_thenDoNothing() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();
        final double amount = 10.0;

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

        // assertions
        verify(bankMoneyDestockManager, never()).createTransactionDestock(
                anyString(),
                eq(FiatCurrency.ARGENTINE_PESO),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq(BANK_ACCOUNT_NUMBER_1),
                eq(BigDecimal.valueOf(amount)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));
    }

    @Test
    public void givenAParamIsNull_thenThrowException() throws FermatException {
        final String earningWalletPublicKey = WalletsPublicKeys.BNK_BANKING_WALLET.getCode();
        final String brokerWalletPublicKey = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();
        final FiatCurrency earningCurrency = FiatCurrency.ARGENTINE_PESO;

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                earningCurrency,
                10,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
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
        final FiatCurrency earningCurrency = FiatCurrency.VENEZUELAN_BOLIVAR;
        final UUID secondOriginTransactionId = UUID.randomUUID();

        final List<EarningTransaction> earningTransactions = new ArrayList<>();
        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                originTransactionId,
                earningCurrency,
                10.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        earningTransactions.add(new MatchingEngineMiddlewareEarningTransaction(
                secondOriginTransactionId,
                earningCurrency,
                20.0f,
                EarningTransactionState.CALCULATED,
                System.currentTimeMillis(),
                matchingEngineDao));

        final MatchingEngineMiddlewareEarningsPair earningsPair = new MatchingEngineMiddlewareEarningsPair(
                UUID.randomUUID(),
                earningCurrency,
                FiatCurrency.US_DOLLAR,
                new WalletReference(earningWalletPublicKey),
                EarningPairState.ASSOCIATED,
                matchingEngineDao,
                new WalletReference(brokerWalletPublicKey));


        // exercise
        transaction.transferEarningsToEarningWallet(earningsPair, earningTransactions);

        // assertions
        final InOrder inOrder = inOrder(bankMoneyDestockManager);
        inOrder.verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq(BANK_ACCOUNT_NUMBER_2),
                eq(BigDecimal.valueOf(10.0f)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(originTransactionId.toString()));

        inOrder.verify(bankMoneyDestockManager).createTransactionDestock(
                anyString(),
                eq(earningCurrency),
                eq(brokerWalletPublicKey),
                eq(earningWalletPublicKey),
                eq(BANK_ACCOUNT_NUMBER_2),
                eq(BigDecimal.valueOf(20.0f)),
                anyString(),
                any(BigDecimal.class),
                eq(OriginTransaction.EARNING_EXTRACTION),
                eq(secondOriginTransactionId.toString()));
    }
}
