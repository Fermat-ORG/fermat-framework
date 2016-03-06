package unit.HoldCryptoMoneyTransactionImpl;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils.HoldCryptoMoneyTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetTransactionIdTest {

    @Test
    public void getTransactionId(){
        HoldCryptoMoneyTransactionImpl holdCryptoMoneyTransaction = mock(HoldCryptoMoneyTransactionImpl.class);
        when(holdCryptoMoneyTransaction.getTransactionId()).thenReturn(UUID.randomUUID());
        assertThat(holdCryptoMoneyTransaction.getTransactionId()).isNotNull();
    }

}
