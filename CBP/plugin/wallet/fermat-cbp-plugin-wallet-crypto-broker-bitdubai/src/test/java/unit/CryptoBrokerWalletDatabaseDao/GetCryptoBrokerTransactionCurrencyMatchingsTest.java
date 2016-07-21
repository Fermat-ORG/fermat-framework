package unit.CryptoBrokerWalletDatabaseDao;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetTransactionCryptoBrokerWalletMatchingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


/**
 * Created by nelsonalfo on 06/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoBrokerTransactionCurrencyMatchingsTest {
    final String BROKER_WALLET_PUBLIC_KEY = "BROKER_WALLET_PUBLIC_KEY";
    final String BROKER_PUBLIC_KEY = "BROKER_PUBLIC_KEY";
    final String ORIGIN_TRANSACTION_ID_1 = "1as856789qwe23qw879421eqw5q3";
    final String ORIGIN_TRANSACTION_ID_2 = "132456789asd846548asd6asd548";

    @Mock
    private Database database;

    @Mock
    CryptoBrokerWalletPluginRoot pluginRoot;

    List<DatabaseTableRecord> availableSaleRecordsFromTable;
    List<DatabaseTableRecord> creditRecords;
    List<DatabaseTableRecord> debitRecords;

    CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDaoSpy;

    @Before
    public void setUp() {
        creditRecords = new ArrayList<>();
        debitRecords = new ArrayList<>();

        cryptoBrokerWalletDatabaseDaoSpy = Mockito.spy(new CryptoBrokerWalletDatabaseDao(database, pluginRoot));
        availableSaleRecordsFromTable = null;
    }

    @Test
    public void getAvailableSaleCreditRecordsFromTable() throws CantLoadTableToMemoryException {
        addOneSaleDebit();
        addOneSaleCredit();

        Mockito.doReturn(new ArrayList<DatabaseTableRecord>()).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        availableSaleRecordsFromTable = cryptoBrokerWalletDatabaseDaoSpy.getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        assertThat(availableSaleRecordsFromTable).isInstanceOf(ArrayList.class);
        assertThat(availableSaleRecordsFromTable).isEmpty();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        availableSaleRecordsFromTable = cryptoBrokerWalletDatabaseDaoSpy.getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        assertThat(availableSaleRecordsFromTable).isInstanceOf(ArrayList.class);
        assertThat(availableSaleRecordsFromTable).isNotEmpty();
        assertThat(availableSaleRecordsFromTable).hasSize(1);

        DatabaseTableRecord record = availableSaleRecordsFromTable.get(0);
        assertThat(record).isNotNull();

        String memoStringValue = record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME);
        assertThat(memoStringValue).isEqualTo("test memo credit");
    }

    @Test
    public void getAvailableSaleDebitRecordsFromTable() throws CantLoadTableToMemoryException {
        addOneSaleDebit();
        addOneSaleCredit();

        Mockito.doReturn(new ArrayList<DatabaseTableRecord>()).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);
        availableSaleRecordsFromTable = cryptoBrokerWalletDatabaseDaoSpy.getAvailableSaleRecordsFromTable(TransactionType.DEBIT);
        assertThat(availableSaleRecordsFromTable).isInstanceOf(ArrayList.class);
        assertThat(availableSaleRecordsFromTable).isEmpty();

        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);
        availableSaleRecordsFromTable = cryptoBrokerWalletDatabaseDaoSpy.getAvailableSaleRecordsFromTable(TransactionType.DEBIT);
        assertThat(availableSaleRecordsFromTable).isInstanceOf(ArrayList.class);
        assertThat(availableSaleRecordsFromTable).isNotEmpty();
        assertThat(availableSaleRecordsFromTable).hasSize(1);

        DatabaseTableRecord record = availableSaleRecordsFromTable.get(0);
        assertThat(record).isNotNull();

        String memoStringValue = record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME);
        assertThat(memoStringValue).isEqualTo("test memo debit");
    }

    @Test
    public void giveNoSaleCreditAndNoSaleDebit_shouldReturnEmptyList()
            throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        List<CurrencyMatching> currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isEmpty();
    }

    @Test
    public void givenNoSaleCreditOrNoSaleDebit_shouldReturnEmptyList()
            throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {

        List<CurrencyMatching> currencyMatchings;

        debitRecords = new ArrayList<>();
        addManySaleCredits();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isEmpty();

        creditRecords = new ArrayList<>();
        addManySaleDebits();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isEmpty();
    }

    @Test
    public void givenOneSaleCreditAndOneSaleDebit_returnOneItem()
            throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {

        addOneSaleDebit();
        addOneSaleCredit();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        List<CurrencyMatching> currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isNotEmpty();
        assertThat(currencyMatchings).hasSize(1);

        CurrencyMatching currencyMatching = currencyMatchings.get(0);
        assertThat(currencyMatching.getOriginTransactionId()).isEqualTo(ORIGIN_TRANSACTION_ID_1);

        assertThat(currencyMatching.getCurrencyGiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyGiving()).isEqualTo(CryptoCurrency.BITCOIN);
        assertThat(currencyMatching.getAmountGiving()).isEqualTo(1);

        assertThat(currencyMatching.getCurrencyReceiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyReceiving()).isEqualTo(FiatCurrency.VENEZUELAN_BOLIVAR);
        assertThat(currencyMatching.getAmountReceiving()).isEqualTo(1150);
    }

    @Test
    public void givenManySaleCreditsAndManySaleDebits_returnManyItems()
            throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {

        CurrencyMatching currencyMatching;

        addManySaleDebits();
        addManySaleCredits();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        List<CurrencyMatching> currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isNotEmpty();
        assertThat(currencyMatchings).hasSize(2);


        currencyMatching = currencyMatchings.get(0);
        assertThat(currencyMatching.getOriginTransactionId()).isEqualTo(ORIGIN_TRANSACTION_ID_1);

        assertThat(currencyMatching.getCurrencyGiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyGiving()).isEqualTo(CryptoCurrency.BITCOIN);
        assertThat(currencyMatching.getAmountGiving()).isEqualTo(1);

        assertThat(currencyMatching.getCurrencyReceiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyReceiving()).isEqualTo(FiatCurrency.VENEZUELAN_BOLIVAR);
        assertThat(currencyMatching.getAmountReceiving()).isEqualTo(1150);


        currencyMatching = currencyMatchings.get(1);
        assertThat(currencyMatching.getOriginTransactionId()).isEqualTo(ORIGIN_TRANSACTION_ID_2);

        assertThat(currencyMatching.getCurrencyGiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyGiving()).isEqualTo(FiatCurrency.US_DOLLAR);
        assertThat(currencyMatching.getAmountGiving()).isEqualTo(1);

        assertThat(currencyMatching.getCurrencyReceiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyReceiving()).isEqualTo(FiatCurrency.ARGENTINE_PESO);
        assertThat(currencyMatching.getAmountReceiving()).isEqualTo(15.4f);
    }

    @Test
    public void givenOneSaleCreditAndManySaleDebits_returnOneItem()
            throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {

        addOneSaleCredit();
        addManySaleDebits();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        List<CurrencyMatching> currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isNotEmpty();
        assertThat(currencyMatchings).hasSize(1);


        CurrencyMatching currencyMatching = currencyMatchings.get(0);
        assertThat(currencyMatching.getOriginTransactionId()).isEqualTo(ORIGIN_TRANSACTION_ID_1);

        assertThat(currencyMatching.getCurrencyGiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyGiving()).isEqualTo(CryptoCurrency.BITCOIN);
        assertThat(currencyMatching.getAmountGiving()).isEqualTo(1);

        assertThat(currencyMatching.getCurrencyReceiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyReceiving()).isEqualTo(FiatCurrency.VENEZUELAN_BOLIVAR);
        assertThat(currencyMatching.getAmountReceiving()).isEqualTo(1150.0f);
    }

    @Test
    public void givenManySaleCreditsAndManySaleDebit_noOriginTransactionID_returnEmptyList()
            throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {

        addManySaleCreditsNoTransactionOriginID();
        addManySaleDebitsNoTransactionOriginID();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        List<CurrencyMatching> currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isEmpty();

        addManySaleCredits();
        addManySaleDebitsNoTransactionOriginID();

        currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isEmpty();

        addManySaleCreditsNoTransactionOriginID();
        addManySaleDebits();

        currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isEmpty();
    }

    @Test
    public void givenACreditAndADebitWithSameCurrency_thenShouldReturnListWithoutThisPair()
            throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        CryptoCurrency.BITCOIN,
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        MoneyType.CRYPTO,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1),
                        System.currentTimeMillis(),
                        "test memo debit",
                        new BigDecimal(5),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_1,
                        false), 10, 10));

        creditRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        CryptoCurrency.BITCOIN,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.CRYPTO,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1),
                        System.currentTimeMillis(),
                        "test memo credit",
                        new BigDecimal(5),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_1,
                        false), 10, 10));

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        CryptoCurrency.BITCOIN,
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        MoneyType.CRYPTO,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1),
                        System.currentTimeMillis(),
                        "test memo debit",
                        BigDecimal.ZERO,
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_2,
                        false), 10, 10));

        creditRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.US_DOLLAR,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.BANK,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(5),
                        System.currentTimeMillis(),
                        "test memo credit",
                        BigDecimal.ZERO,
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_2,
                        false), 10, 10));

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        doNothing().when(pluginRoot).reportError(eq(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT), any(InvalidParameterException.class));

        // exercise
        List<CurrencyMatching> currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();

        // assert
        assertThat(currencyMatchings).hasSize(1);

        final CurrencyMatching currencyMatching = currencyMatchings.get(0);
        assertThat(currencyMatching.getOriginTransactionId()).isEqualTo(ORIGIN_TRANSACTION_ID_2);
        assertThat(currencyMatching.getCurrencyGiving()).isEqualTo(CryptoCurrency.BITCOIN);
        assertThat(currencyMatching.getCurrencyReceiving()).isEqualTo(FiatCurrency.US_DOLLAR);
        assertThat(currencyMatching.getAmountGiving()).isEqualTo(1);
        assertThat(currencyMatching.getAmountReceiving()).isEqualTo(5);

        Mockito.verify(pluginRoot).reportError(eq(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT), any(InvalidParameterException.class));
    }

    private void addOneSaleDebit() {
        debitRecords.clear();

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        CryptoCurrency.BITCOIN,
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        MoneyType.CRYPTO,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1),
                        System.currentTimeMillis(),
                        "test memo debit",
                        new BigDecimal(5),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_1,
                        false), 10, 10));
    }

    private void addOneSaleCredit() {
        creditRecords.clear();

        creditRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1150),
                        System.currentTimeMillis(),
                        "test memo credit",
                        new BigDecimal(5),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_1,
                        false), 10, 10));
    }

    private void addManySaleDebits() {
        debitRecords.clear();

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        CryptoCurrency.BITCOIN,
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        MoneyType.CRYPTO,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1.0),
                        System.currentTimeMillis(),
                        "test memo debit 1",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_1,
                        false), 10, 10));

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.US_DOLLAR,
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1.0),
                        System.currentTimeMillis(),
                        "test memo debit 2",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_2,
                        false), 10, 10));
    }

    private void addManySaleCredits() {
        creditRecords.clear();

        creditRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1150.0),
                        System.currentTimeMillis(),
                        "test memo credit 1",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_1,
                        false), 10, 10));

        creditRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.ARGENTINE_PESO,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(15.4),
                        System.currentTimeMillis(),
                        "test memo credit 2",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_2,
                        false), 10, 10));
    }

    private void addManySaleDebitsNoTransactionOriginID() {
        debitRecords.clear();

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        CryptoCurrency.BITCOIN,
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        MoneyType.CRYPTO,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1.0),
                        System.currentTimeMillis(),
                        "test memo debit 1",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        null,
                        false), 10, 10));

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.US_DOLLAR,
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1.0),
                        System.currentTimeMillis(),
                        "test memo debit 2",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        null,
                        false), 10, 10));
    }

    private void addManySaleCreditsNoTransactionOriginID() {
        creditRecords.clear();

        creditRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.VENEZUELAN_BOLIVAR,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1150.0),
                        System.currentTimeMillis(),
                        "test memo credit 1",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        null,
                        false), 10, 10));

        creditRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.ARGENTINE_PESO,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(15.4),
                        System.currentTimeMillis(),
                        "test memo credit 2",
                        new BigDecimal(5.0),
                        OriginTransaction.SALE,
                        null,
                        false), 10, 10));
    }

    private DatabaseTableRecord constructStockWalletTransactionRecord(
            final CryptoBrokerStockTransactionRecord stockTransactionRecord,
            final float availableRunningBalance,
            final float bookRunningBalance) {
        DatabaseTableRecord record = Mockito.mock(DatabaseTableRecord.class);

        when(record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME)).thenReturn(stockTransactionRecord.getTransactionId());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME)).thenReturn(stockTransactionRecord.getBalanceType().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME)).thenReturn(stockTransactionRecord.getTransactionType().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME)).thenReturn(stockTransactionRecord.getAmount().toPlainString());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME)).thenReturn(stockTransactionRecord.getMerchandise().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME)).thenReturn(stockTransactionRecord.getMoneyType().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME)).thenReturn(stockTransactionRecord.getTransactionType().getCode());
        when(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME)).thenReturn(availableRunningBalance);
        when(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME)).thenReturn(bookRunningBalance);
        when(record.getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME)).thenReturn(stockTransactionRecord.getTimestamp());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME)).thenReturn(stockTransactionRecord.getMemo());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME)).thenReturn(stockTransactionRecord.getOriginTransaction().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME)).thenReturn(stockTransactionRecord.getPriceReference().toPlainString());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME)).thenReturn(stockTransactionRecord.getOriginTransactionId());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME)).thenReturn(String.valueOf(stockTransactionRecord.getSeen()));

        return record;
    }
}
