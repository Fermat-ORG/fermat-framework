package unit.CryptoBrokerStockTransactionImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerStockTransactionImpl;

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

    BigDecimal runningBookBalance = BigDecimal.ONE;
    BigDecimal runningAvailableBalance = BigDecimal.ONE;
    BigDecimal previousBookBalance = BigDecimal.ONE;
    BigDecimal previousAvailableBalance = BigDecimal.ONE;
    UUID transactionId = UUID.randomUUID();
    BalanceType balanceType = BalanceType.AVAILABLE;
    TransactionType transactionType = TransactionType.CREDIT;
    CurrencyType currencyType = CurrencyType.BANK_MONEY;
    FermatEnum merchandise = BalanceType.BOOK;
    String walletPublicKey = new String();
    String brokerPublicKey = new String();
    BigDecimal amount = BigDecimal.ONE;
    long timestamp = 1l;
    String memo = new String();
    BigDecimal priceReference = BigDecimal.ONE;
    OriginTransaction originTransaction;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        CryptoBrokerStockTransactionImpl cryptoBrokerStockTransaction = new CryptoBrokerStockTransactionImpl(
                this.runningBookBalance,
                this.runningAvailableBalance,
                this.previousBookBalance,
                this.previousAvailableBalance,
                this.transactionId,
                this.balanceType,
                this.transactionType,
                this.currencyType,
                this.merchandise,
                this.walletPublicKey,
                this.brokerPublicKey,
                this.amount,
                this.timestamp,
                this.memo,
                this.priceReference,
                this.originTransaction
        );
        assertThat(cryptoBrokerStockTransaction).isNotNull();
    }
}
