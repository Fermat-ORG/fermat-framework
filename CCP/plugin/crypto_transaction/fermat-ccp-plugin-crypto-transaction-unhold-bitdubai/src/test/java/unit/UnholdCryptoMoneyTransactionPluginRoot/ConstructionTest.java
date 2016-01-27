package unit.UnholdCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.UnHoldCryptoMoneyTransactionPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @Test
    public void Construction_ValidParameters_NewObjectCreated(){

        UnHoldCryptoMoneyTransactionPluginRoot unholdCryptoMoneyTransactionPluginRoot = new UnHoldCryptoMoneyTransactionPluginRoot();
        assertThat(unholdCryptoMoneyTransactionPluginRoot).isNotNull();
    }

}
