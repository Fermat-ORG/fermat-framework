package unit.UnholdCryptoMoneyTransactionImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils.UnHoldCryptoMoneyTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private CryptoTransactionStatus status = CryptoTransactionStatus.ACKNOWLEDGED;
    private long                    timestampAcknowledged = 1;
    private long                    timestampConfirmedRejected = 1;
    private UUID                    transactionId = UUID.randomUUID();
    private String                  publicKeyWallet = new String();
    private String                  publicKeyActor = new String();
    private String                  publicKeyPlugin = new String();
    private float                   amount = 1;
    private CryptoCurrency          currency = CryptoCurrency.BITCOIN;
    private String                  memo = new String();

    @Test
    public void Construction_ValidParameters_NewObjectCreated(){

        UnHoldCryptoMoneyTransactionImpl unHoldCryptoMoneyTransaction = new UnHoldCryptoMoneyTransactionImpl();
        assertThat(unHoldCryptoMoneyTransaction).isNotNull();
    }
}
