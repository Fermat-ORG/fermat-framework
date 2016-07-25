package BankMoneyRestockTransactionImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.BankMoneyRestockTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    UUID transactionId = UUID.randomUUID();
    String actorPublicKey = new String();
    FiatCurrency fiatCurrency = FiatCurrency.ARGENTINE_PESO;
    String cbpWalletPublicKey = new String();
    String cryWalletPublicKey = new String();
    String memo = new String();
    String concept = new String();
    String cashReference = new String();
    BigDecimal amount = BigDecimal.ONE;
    Timestamp timeStamp = new Timestamp(1);
    TransactionStatusRestockDestock transactionStatus = TransactionStatusRestockDestock.INIT_TRANSACTION;
    BigDecimal priceReference = BigDecimal.ONE;
    OriginTransaction originTransaction = OriginTransaction.STOCK_INITIAL;
    String originTransactionId = new String();

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        BankMoneyRestockTransactionImpl bankMoneyRestockTransaction = new BankMoneyRestockTransactionImpl(
                this.transactionId,
                this.actorPublicKey,
                this.fiatCurrency,
                this.cbpWalletPublicKey,
                this.cryWalletPublicKey,
                this.memo,
                this.concept,
                this.cashReference,
                this.amount,
                this.timeStamp,
                this.transactionStatus,
                this.priceReference,
                this.originTransaction,
                this.originTransactionId
        );
        assertThat(bankMoneyRestockTransaction).isNotNull();
    }
}
