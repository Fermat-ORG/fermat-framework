package unit.CryptoBrokerWalletDatabaseDao;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetTransactionCryptoBrokerWalletMatchingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CurrencyMatching;
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
import static org.mockito.Mockito.when;


/**
 * Created by nelsonalfo on 06/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoBrokerTransactionCurrencyMatchingsTest {
    final String BROKER_WALLET_PUBLIC_KEY = "BROKER_WALLET_PUBLIC_KEY";
    final String BROKER_PUBLIC_KEY = "BROKER_PUBLIC_KEY";
    final String ORIGIN_TRANSACTION_ID_1 = "132456789";

    @Mock
    private Database database;

    List<DatabaseTableRecord> availableSaleRecordsFromTable;
    List<DatabaseTableRecord> creditRecords;
    List<DatabaseTableRecord> debitRecords;

    CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDaoSpy;

    @Before
    public void setUp() {
        creditRecords = new ArrayList<>();
        debitRecords = new ArrayList<>();

        cryptoBrokerWalletDatabaseDaoSpy = Mockito.spy(new CryptoBrokerWalletDatabaseDao(database));
        availableSaleRecordsFromTable = null;
    }

    @Test
    public void getAvailableSaleCreditRecordsFromTable() throws CantLoadTableToMemoryException {
       addOneSaleDebitAndOneSaleCredit();

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
        addOneSaleDebitAndOneSaleCredit();

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
    public void onOneSaleCreditAndOneSaleDebit_returnListWithOneCurrencyMatchingObject() throws CantLoadTableToMemoryException, CantGetTransactionCryptoBrokerWalletMatchingException {
        addOneSaleDebitAndOneSaleCredit();

        Mockito.doReturn(creditRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.CREDIT);
        Mockito.doReturn(debitRecords).when(cryptoBrokerWalletDatabaseDaoSpy).getAvailableSaleRecordsFromTable(TransactionType.DEBIT);

        List<CurrencyMatching> currencyMatchings = cryptoBrokerWalletDatabaseDaoSpy.getCryptoBrokerTransactionCurrencyMatchings();
        assertThat(currencyMatchings).isNotEmpty();
        assertThat(currencyMatchings).hasSize(1);

        CurrencyMatching currencyMatching = currencyMatchings.get(0);
        assertThat(currencyMatching.getOriginTransactionId()).isEqualTo(ORIGIN_TRANSACTION_ID_1);

        assertThat(currencyMatching.getCurrencyGiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyGiving().getCode()).isEqualTo(FiatCurrency.US_DOLLAR.getCode());
        assertThat(currencyMatching.getAmountGiving()).isEqualTo(1);

        assertThat(currencyMatching.getCurrencyReceiving()).isNotNull();
        assertThat(currencyMatching.getCurrencyReceiving().getCode()).isEqualTo(FiatCurrency.VENEZUELAN_BOLIVAR.getCode());
        assertThat(currencyMatching.getAmountReceiving()).isEqualTo(1150);
    }


    private DatabaseTableRecord constructStockWalletTransactionRecord(final CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, final float availableRunningBalance, final float bookRunningBalance) {
        DatabaseTableRecord record = Mockito.mock(DatabaseTableRecord.class);

        when(record.getUUIDValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getTransactionId());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getBalanceType().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getTransactionType().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getAmount().toPlainString());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getMerchandise().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MONEY_TYPE_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getMoneyType().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getTransactionType().getCode());
        when(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME)).thenReturn(availableRunningBalance);
        when(record.getFloatValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME)).thenReturn(bookRunningBalance);
        when(record.getLongValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getTimestamp());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getMemo());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getOriginTransaction().getCode());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getPriceReference().toPlainString());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_ID_COLUMN_NAME)).thenReturn(cryptoBrokerStockTransactionRecord.getOriginTransactionId());
        when(record.getStringValue(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_SEEN_COLUMN_NAME)).thenReturn(String.valueOf(cryptoBrokerStockTransactionRecord.getSeen()));

        return record;
    }

    public void addOneSaleDebitAndOneSaleCredit() {
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
                        false),
                10,
                10));

        debitRecords.add(constructStockWalletTransactionRecord(
                new CryptoBrokerStockTransactionRecordMock(
                        UUID.randomUUID(),
                        FiatCurrency.US_DOLLAR,
                        BalanceType.AVAILABLE,
                        TransactionType.CREDIT,
                        MoneyType.CASH_ON_HAND,
                        BROKER_WALLET_PUBLIC_KEY,
                        BROKER_PUBLIC_KEY,
                        new BigDecimal(1),
                        System.currentTimeMillis(),
                        "test memo debit",
                        new BigDecimal(5),
                        OriginTransaction.SALE,
                        ORIGIN_TRANSACTION_ID_1,
                        false),
                10,
                10));
    }

    private class CryptoBrokerStockTransactionRecordMock implements CryptoBrokerStockTransactionRecord {
        private final UUID transactionId;
        private final Currency merchandise;
        private final BalanceType balanceType;
        private final TransactionType transactionType;
        private final MoneyType moneyType;
        private final String walletPublicKey;
        private final String brokerPublicKey;
        private final BigDecimal amount;
        private final long timeStamp;
        private final String memo;
        private final BigDecimal priceReference;
        private final OriginTransaction originTransaction;
        private String originTransactionId;
        private boolean seen;

        /**
         * Constructor for CryptoBrokerStockTransactionRecordMock
         */
        public CryptoBrokerStockTransactionRecordMock(UUID transactionId,
                                                      Currency merchandise,
                                                      BalanceType balanceType,
                                                      TransactionType transactionType,
                                                      MoneyType moneyType,
                                                      String walletPublicKey,
                                                      String brokerPublicKey,
                                                      BigDecimal amount,
                                                      long timeStamp,
                                                      String memo,
                                                      BigDecimal priceReference,
                                                      OriginTransaction originTransaction,
                                                      String originTransactionId,
                                                      boolean seen) {

            this.transactionId = transactionId;
            this.merchandise = merchandise;
            this.balanceType = balanceType;
            this.transactionType = transactionType;
            this.moneyType = moneyType;
            this.walletPublicKey = walletPublicKey;
            this.brokerPublicKey = brokerPublicKey;
            this.amount = amount;
            this.timeStamp = timeStamp;
            this.memo = memo;
            this.priceReference = priceReference;
            this.originTransaction = originTransaction;
            this.originTransactionId = originTransactionId;
            this.seen = seen;
        }

        @Override
        public UUID getTransactionId() {
            return transactionId;
        }

        @Override
        public BalanceType getBalanceType() {
            return balanceType;
        }

        @Override
        public TransactionType getTransactionType() {
            return transactionType;
        }

        @Override
        public MoneyType getMoneyType() {
            return moneyType;
        }

        @Override
        public Currency getMerchandise() {
            return merchandise;
        }

        @Override
        public String getWalletPublicKey() {
            return walletPublicKey;
        }

        @Override
        public String getBrokerPublicKey() {
            return brokerPublicKey;
        }

        @Override
        public BigDecimal getAmount() {
            return amount;
        }

        @Override
        public long getTimestamp() {
            return timeStamp;
        }

        @Override
        public String getMemo() {
            return memo;
        }

        @Override
        public BigDecimal getPriceReference() {
            return priceReference;
        }

        @Override
        public OriginTransaction getOriginTransaction() {
            return originTransaction;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getOriginTransactionId() {
            return this.originTransactionId;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getSeen() {
            return this.seen;
        }

        @Override
        public BigDecimal getRunningBookBalance() {
            return new BigDecimal(0);
        }

        @Override
        public BigDecimal getRunningAvailableBalance() {
            return new BigDecimal(0);
        }
    }
}
