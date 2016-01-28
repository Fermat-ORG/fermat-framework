package unit.CryptoBrokerStockTransactionRecordImpl;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerStockTransactionRecordImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private UUID transactionId = UUID.randomUUID();
    private KeyPair walletKeyPair = new ECCKeyPair();
    private String ownerPublicKey = new String();
    private BalanceType balanceType = BalanceType.AVAILABLE;
    private TransactionType transactionType = TransactionType.CREDIT;
    private CurrencyType currencyType = CurrencyType.BANK_MONEY;
    private FermatEnum merchandise = BalanceType.BOOK;
    private BigDecimal amount = BigDecimal.ONE;
    private BigDecimal runningBookBalance = BigDecimal.ONE;
    private BigDecimal runningAvailableBalance = BigDecimal.ONE;
    private long timeStamp = 1l;
    private String memo = new String();
    private OriginTransaction originTransaction = OriginTransaction.STOCK_INITIAL;
    private BigDecimal priceReference = BigDecimal.ONE;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        CryptoBrokerStockTransactionRecordImpl cryptoBrokerStockTransactionRecord = new CryptoBrokerStockTransactionRecordImpl(
                this.transactionId,
                this.walletKeyPair,
                this.ownerPublicKey,
                this.balanceType,
                this.transactionType,
                this.currencyType,
                this.merchandise,
                this.amount,
                this.runningBookBalance,
                this.runningAvailableBalance,
                this.timeStamp,
                this.memo,
                this.originTransaction,
                this.priceReference
        );
        assertThat(cryptoBrokerStockTransactionRecord).isNotNull();
    }
}
