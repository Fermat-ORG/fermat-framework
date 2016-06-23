package unit.UnholdCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.UnHoldCryptoMoneyTransactionPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoUnholdTransactionStatusTest {

    @Test
    public void getCryptoUnholdTransactionStatus() throws Exception{
        UnHoldCryptoMoneyTransactionPluginRoot unHoldCryptoMoneyTransactionPluginRoot = mock(UnHoldCryptoMoneyTransactionPluginRoot.class);
        when(unHoldCryptoMoneyTransactionPluginRoot.getCryptoUnholdTransactionStatus(Mockito.any(UUID.class))).thenReturn(CryptoTransactionStatus.ACKNOWLEDGED);
        assertThat(unHoldCryptoMoneyTransactionPluginRoot.getCryptoUnholdTransactionStatus(Mockito.any(UUID.class))).isNotNull();
    }

}
